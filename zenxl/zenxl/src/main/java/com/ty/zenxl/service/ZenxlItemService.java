package com.ty.zenxl.service;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.zenxl.entity.Certificate;
import com.ty.zenxl.entity.CertificateTypeDetails;
import com.ty.zenxl.entity.Code;
import com.ty.zenxl.entity.CodeTypeDetails;
import com.ty.zenxl.entity.ItemDetails;
import com.ty.zenxl.exception.CertificateException;
import com.ty.zenxl.exception.DataException;
import com.ty.zenxl.exception.ItemException;
import com.ty.zenxl.repository.CertificateRepository;
import com.ty.zenxl.repository.CertificateTypeRepository;
import com.ty.zenxl.repository.CodeTypeRepository;
import com.ty.zenxl.repository.ItemRepository;
import com.ty.zenxl.request.CertificateUpdateRequest;
import com.ty.zenxl.request.CertificateUploadRequest;
import com.ty.zenxl.request.ItemRequest;
import com.ty.zenxl.request.ItemUpdateRequest;
import com.ty.zenxl.response.CertificateResponse;
import com.ty.zenxl.response.CodeResponse;
import com.ty.zenxl.response.ItemResponse;
import com.ty.zenxl.response.ZenxlResponseBody;

import lombok.RequiredArgsConstructor;

/**
 * Zenxl Item Service For Master Data
 * 
 * <p>
 * Item or part details CRUD apis in master data.
 * </p>
 * 
 * <p>
 * Upload certificate, update certificate and view certificate using
 * {@link MultipartFile}, {@link Resource} and {@link Path}
 * </p>
 * 
 * @author Abhishek
 * @version 1.0
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ZenxlItemService {

	private final Validator validator;

	private final ItemRepository itemRepository;

	private final CertificateRepository certificateRepository;

	private final CertificateTypeRepository certificateTypeRepository;

	private final CodeTypeRepository codeTypeRepository;

	private final ObjectMapper mapper = new ObjectMapper();

	private Path directory;

	@Value("${item.upload}")
	private String pathInfo;

	/**
	 * To get certificate file path details using {@code Path}
	 * 
	 * @param certificatename
	 * @return {@link Path}
	 */
	private Path getPath(String certificateName) {
		String dir = pathInfo + "\\" + certificateName;
		directory = Paths.get(dir).toAbsolutePath().normalize();
		return directory;
	}

	/**
	 * To read details of certificate upload
	 * 
	 * @param data
	 * @return {@link CertificateUploadRequest}
	 * @throws JsonProcessingException
	 */
	private CertificateUploadRequest uploadCertificateDetails(String data) throws JsonProcessingException {
		return mapper.readValue(data, CertificateUploadRequest.class);
	}

	/**
	 * To read details of certificate update
	 * 
	 * @param data
	 * @return {@link CertificateUpdateRequest}
	 * @throws JsonProcessingException
	 */
	private CertificateUpdateRequest updateCertificateDetails(String data) throws JsonProcessingException {
		return mapper.readValue(data, CertificateUpdateRequest.class);
	}

	/**
	 * Upload certificate for item in master data
	 * 
	 * @param file
	 * @param data
	 * @return {@link ZenxlResponseBody}
	 * @throws IOException
	 */
	public ZenxlResponseBody uploadCertificate(MultipartFile file, String data) throws IOException {
		if (file.isEmpty()) {
			throw new CertificateException(PLEASE_SELECT_DOCUMENT);
		} else if (data.isEmpty()) {
			throw new DataException(DATA_SHOULD_NOT_BE_EMPTY);
		} else {
			CertificateUploadRequest certificateUpload = null;
			try {
				certificateUpload = uploadCertificateDetails(data);

				Set<ConstraintViolation<CertificateUploadRequest>> violations = validator.validate(certificateUpload);

				if (!violations.isEmpty()) {
					throw new ConstraintViolationException(violations);
				}
				if (Boolean.TRUE.equals(
						certificateRepository.existsByCertificateNumber(certificateUpload.getCertificateNumber()))) {
					throw new CertificateException(CERTIFICATE_ALREADY_EXISTS);
				}
				directory = getPath(file.getOriginalFilename());
				Path path = directory.resolve(file.getOriginalFilename());
				Files.createDirectories(directory);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				ItemDetails itemDetails = itemRepository.findBySerialNumber(certificateUpload.getSerialNumber())
						.orElseThrow(() -> new ItemException(NO_ITEM_FOUND));

				CertificateTypeDetails certificateType = certificateTypeRepository
						.findByCertificateType(certificateUpload.getCertificateType()).orElse(CertificateTypeDetails.builder()
								.certificateType(certificateUpload.getCertificateType()).build());

				Certificate certificate = Certificate.builder().certificateName(file.getOriginalFilename())
						.certificateFormat(file.getContentType()).certificateUrl(path.toString())
						.certificateNumber(certificateUpload.getCertificateNumber())
						.expiryDate(certificateUpload.getExpiryDate()).certificateType(certificateType)
						.itemDetails(itemDetails).build();

				certificateRepository.save(certificate);
			} catch (IOException exception) {
				throw new IOException(exception.getMessage());
			}
		}
		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(CERTIFICATE_UPLOADED_SUCCESSFULLY).build();
	}

	/**
	 * View certificate details in master data
	 * 
	 * @param certificateNumber
	 * @return {@link CertificateResponse}
	 * @throws MalformedURLException
	 */
	public CertificateResponse viewCertificate(String certificateNumber) throws MalformedURLException {
		if (!certificateNumber.isEmpty()) {
			Certificate certificateInfo = certificateRepository.findByCertificateNumber(certificateNumber)
					.orElseThrow(() -> new CertificateException(CERTIFICATE_NOT_FOUND));
			directory = Paths.get(certificateInfo.getCertificateUrl()).toAbsolutePath().normalize();
			if (Files.notExists(directory)) {
				throw new CertificateException(FILE_NOT_PRESENT);
			}
			Resource resource = null;
			try {
				resource = new UrlResource(directory.toUri());
			} catch (MalformedURLException exception) {
				throw new MalformedURLException(exception.getMessage());
			}

			return CertificateResponse.builder().certificateFormat(certificateInfo.getCertificateFormat())
					.resource(resource).build();
		}
		throw new CertificateException(PLEASE_ENTER_CERTIFICATE_NUMBER);
	}

	/**
	 * Update certificate details of item in master data
	 * 
	 * @param file
	 * @param data
	 * @return {@link ZenxlResponseBody}
	 * @throws IOException
	 */
	public ZenxlResponseBody updateCertificate(MultipartFile file, String data) throws IOException {
		if (file.isEmpty()) {
			throw new CertificateException(PLEASE_SELECT_DOCUMENT);
		} else if (data.isEmpty()) {
			throw new DataException(DATA_SHOULD_NOT_BE_EMPTY);
		} else {
			CertificateUpdateRequest certificateUpdate = null;
			try {
				certificateUpdate = updateCertificateDetails(data);

				Set<ConstraintViolation<CertificateUpdateRequest>> violations = validator.validate(certificateUpdate);

				if (!violations.isEmpty()) {
					throw new ConstraintViolationException(violations);
				}

				Certificate certificate = certificateRepository
						.findByCertificateNumber(certificateUpdate.getCertificateNumber())
						.orElseThrow(() -> new CertificateException(CERTIFICATE_NOT_FOUND));

				directory = getPath(file.getOriginalFilename());
				Path path = directory.resolve(file.getOriginalFilename());
				Files.createDirectories(directory);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				certificate.setCertificateName(file.getOriginalFilename());
				certificate.setCertificateFormat(file.getContentType());
				certificate.setCertificateUrl(path.toString());
				certificate.setExpiryDate(certificateUpdate.getExpiryDate());

				certificateRepository.save(certificate);
			} catch (IOException exception) {
				throw new IOException(exception.getMessage());
			}
		}
		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(CERTIFICATE_UPDATED_SUCCESSFULLY).build();
	}

	/**
	 * Add item in master data
	 * 
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody addItem(ItemRequest request) {
		if (Boolean.TRUE.equals(itemRepository.existsBySerialNumber(request.getSerialNumber())))
			throw new ItemException(ITEM_ALREADY_EXISTS);

		ItemDetails itemDetails = ItemDetails.builder().serialNumber(request.getSerialNumber()).build();

		Set<Code> listOfCodes = request.getCodeRequestList().stream().map(codeRequest -> {
			CodeTypeDetails codeType = codeTypeRepository.findByCodeType(codeRequest.getCodeType())
					.orElse(CodeTypeDetails.builder().codeType(codeRequest.getCodeType()).build());
			return Code.builder().codeType(codeType).codeValue(codeRequest.getCodeValue()).item(itemDetails).build();
		}).collect(Collectors.toSet());

		itemDetails.setManufacturerName(request.getManufacturerName());
		itemDetails.setPartNumber(request.getPartNumber());
		itemDetails.setQuantity(request.getQuantity());
		itemDetails.setDescription(request.getDescription());
		itemDetails.setUnitOfMeasure(request.getUnitOfMeasure());
		itemDetails.setUnitPrice(request.getUnitPrice());
		itemDetails.setCountryOfOrigin(request.getCountryOfOrigin());
		itemDetails.setCodeList(listOfCodes);

		itemRepository.save(itemDetails);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ITEM_ADDED_SUCCESSFULLY).build();
	}

	/**
	 * View item details in master data
	 * 
	 * @param serialNumber
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewItemDetails(Long serialNumber) {
		ItemDetails itemDetails = itemRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new ItemException(ITEM_NOT_FOUND_WITH_SERIAL + serialNumber));

		Set<CodeResponse> codeList = itemDetails.getCodeList().stream().map(code -> CodeResponse.builder()
				.codeType(code.getCodeType().getCodeType()).codeValue(code.getCodeValue()).build())
				.collect(Collectors.toSet());

		Set<CertificateResponse> certificateList = itemDetails.getCertificateList().stream()
				.map(certificate -> CertificateResponse.builder().certificateNumber(certificate.getCertificateNumber())
						.expiryDate(certificate.getExpiryDate())
						.certificateType(certificate.getCertificateType().getCertificateType()).build())
				.collect(Collectors.toSet());

		ItemResponse itemResponse = ItemResponse.builder().manufacturerName(itemDetails.getManufacturerName())
				.serialNumber(itemDetails.getSerialNumber()).partNumber(itemDetails.getPartNumber())
				.quantity(itemDetails.getQuantity()).description(itemDetails.getDescription())
				.unitOfMeasure(itemDetails.getUnitOfMeasure()).unitPrice(itemDetails.getUnitPrice())
				.countryOfOrigin(itemDetails.getCountryOfOrigin()).codeList(codeList).certificateList(certificateList)
				.build();

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ITEM_FETCHED_SUCCESSFULLY).data(itemResponse)
				.build();
	}

	/**
	 * View all items in master data
	 * 
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewAllItems() {
		List<ItemDetails> allItems = itemRepository.findAll();
		if (allItems.isEmpty()) {
			throw new ItemException(NO_ITEM_FOUND);
		}
		Set<ItemResponse> itemList = allItems.stream().map(itemDetails -> {

			Set<CodeResponse> listOfCode = itemDetails
					.getCodeList().stream().map(code -> CodeResponse.builder()
							.codeType(code.getCodeType().getCodeType()).codeValue(code.getCodeValue()).build())
					.collect(Collectors.toSet());

			Set<CertificateResponse> listOfCertificate = itemDetails.getCertificateList().stream()
					.map(certificate -> CertificateResponse.builder()
							.certificateNumber(certificate.getCertificateNumber())
							.expiryDate(certificate.getExpiryDate())
							.certificateType(certificate.getCertificateType().getCertificateType()).build())
					.collect(Collectors.toSet());

			return ItemResponse.builder().manufacturerName(itemDetails.getManufacturerName())
					.serialNumber(itemDetails.getSerialNumber()).partNumber(itemDetails.getPartNumber())
					.quantity(itemDetails.getQuantity()).description(itemDetails.getDescription())
					.unitOfMeasure(itemDetails.getUnitOfMeasure()).unitPrice(itemDetails.getUnitPrice())
					.countryOfOrigin(itemDetails.getCountryOfOrigin()).codeList(listOfCode)
					.certificateList(listOfCertificate).build();
		}).collect(Collectors.toSet());

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ALL_ITEMS_FETCHED).data(itemList).build();
	}

	/**
	 * Delete item from master data
	 * 
	 * @param serialNumber
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody deleteItem(Long serialNumber) {
		ItemDetails itemDetails = itemRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new ItemException(ITEM_NOT_FOUND_WITH_SERIAL + serialNumber));

		if (itemDetails.getCertificateList().isEmpty()) {
			itemRepository.delete(itemDetails);
		} else {
			itemDetails.getCertificateList().stream().forEach(certificate -> {
				directory = getPath(certificate.getCertificateName());
				certificateRepository.deleteByCertificateNumber(certificate.getCertificateNumber());
				try {
					if (Files.exists(directory)) {
						FileUtils.forceDelete(new File(directory.toString()));
					}
				} catch (IOException exception) {
					throw new ItemException(exception.getMessage());
				}
			});
			itemRepository.delete(itemDetails);
		}
		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ITEM_DELETED_SUCCESSFULLY).build();
	}

	/**
	 * Update item details in master data
	 * 
	 * @param serialNumber
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody updateItem(ItemUpdateRequest request, Long serialNumber) {

		ItemDetails itemDetails = itemRepository.findBySerialNumber(serialNumber)
				.orElseThrow(() -> new ItemException(ITEM_NOT_FOUND_WITH_SERIAL + serialNumber));

		itemDetails.setManufacturerName(request.getManufacturerName());
		itemDetails.setPartNumber(request.getPartNumber());
		itemDetails.setQuantity(request.getQuantity());
		itemDetails.setDescription(request.getDescription());
		itemDetails.setUnitOfMeasure(request.getUnitOfMeasure());
		itemDetails.setUnitPrice(request.getUnitPrice());
		itemDetails.setCountryOfOrigin(request.getCountryOfOrigin());

		request.getCodeRequestList().stream()
				.forEach(codeRequest -> itemDetails.getCodeList().stream()
						.filter(code -> code.getCodeType().getCodeType().equals(codeRequest.getCodeType()))
						.forEach(code -> code.setCodeValue(codeRequest.getCodeValue())));

		itemRepository.save(itemDetails);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ITEM_UPDATED_SUCCESSFULLY).build();
	}
}
