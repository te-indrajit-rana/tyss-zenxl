package com.ty.zenxl.service;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ty.zenxl.entity.BillOfEntry;
import com.ty.zenxl.entity.Code;
import com.ty.zenxl.entity.CodeTypeDetails;
import com.ty.zenxl.entity.DocumentType;
import com.ty.zenxl.entity.DutySummary;
import com.ty.zenxl.entity.ExportDocument;
import com.ty.zenxl.entity.ExportInvoice;
import com.ty.zenxl.entity.ExporterDetails;
import com.ty.zenxl.entity.ImporterDetails;
import com.ty.zenxl.entity.IncotermTypeDetails;
import com.ty.zenxl.entity.Inspection;
import com.ty.zenxl.entity.InspectionTypeDetails;
import com.ty.zenxl.entity.ItemDetails;
import com.ty.zenxl.entity.ShipmentDetails;
import com.ty.zenxl.entity.ShipmentItem;
import com.ty.zenxl.exception.BillOfEntryException;
import com.ty.zenxl.exception.DataException;
import com.ty.zenxl.exception.DocumentException;
import com.ty.zenxl.exception.IncotermTypeException;
import com.ty.zenxl.exception.InvoiceException;
import com.ty.zenxl.exception.ItemException;
import com.ty.zenxl.exception.ShipmentDetailsException;
import com.ty.zenxl.exception.ShipmentItemException;
import com.ty.zenxl.repository.BillOfEntryRepository;
import com.ty.zenxl.repository.CodeTypeRepository;
import com.ty.zenxl.repository.DocumentTypeRepository;
import com.ty.zenxl.repository.ExportDocumentRepository;
import com.ty.zenxl.repository.ExportInvoiceRepository;
import com.ty.zenxl.repository.IncotermTypeRepository;
import com.ty.zenxl.repository.InspectionTypeRepository;
import com.ty.zenxl.repository.ItemRepository;
import com.ty.zenxl.repository.ShipmentDetailsRepository;
import com.ty.zenxl.repository.ShipmentItemRepository;
import com.ty.zenxl.request.BillOfEntryRequest;
import com.ty.zenxl.request.DocumentUploadRequest;
import com.ty.zenxl.request.DutySummaryRequest;
import com.ty.zenxl.request.ExportInvoiceRequest;
import com.ty.zenxl.request.ExportInvoiceUpdateRequest;
import com.ty.zenxl.request.ShipmentRequest;
import com.ty.zenxl.request.ShipmentUpdateRequest;
import com.ty.zenxl.response.BillOfEntryResponse;
import com.ty.zenxl.response.CertificateResponse;
import com.ty.zenxl.response.CodeResponse;
import com.ty.zenxl.response.DutySummaryResponse;
import com.ty.zenxl.response.InspectionResponse;
import com.ty.zenxl.response.InvoiceResponse;
import com.ty.zenxl.response.ShipmentItemResponse;
import com.ty.zenxl.response.ZenxlResponseBody;

import lombok.RequiredArgsConstructor;

/**
 * Zenxl Export Service for Invoice, Shipment Item and Bill Of Entry
 * 
 * <p>
 * Invoice, shipment item and bill of entry CRUD apis.
 * </p>
 * 
 * <p>
 * Upload document for an invoice using {@link MultipartFile} and {@link Path}
 * </p>
 * 
 * <p>
 * Used {@link Validator} for handling constraint violation exception
 * </p>
 * 
 * @author Swathi
 * @author Abhishek
 * @author Indrajit
 * @version 1.0
 */

@Service
@RequiredArgsConstructor
@Transactional
public class ZenxlExportService {

	private final ExportInvoiceRepository invoiceRepository;

	private final ShipmentItemRepository shipmentItemRepository;

	private final CodeTypeRepository codeTypeRepository;

	private final ItemRepository itemRepository;

	private final ShipmentDetailsRepository shipmentDetailsRepository;

	private final IncotermTypeRepository incotermTypeRepository;

	private final InspectionTypeRepository inspectionTypeRepository;

	private final BillOfEntryRepository billOfEntryRepository;

	private final ExportDocumentRepository exportDocumentRepository;

	private final DocumentTypeRepository documentTypeRepository;

	private final Validator validator;

	private final ObjectMapper mapper = new ObjectMapper();

	private Path directory;

	@Value("${export.upload}")
	private String pathInfo;

	/**
	 * To get export file path details using {@code Path}
	 * 
	 * @param documentName
	 * @return {@link Path}
	 */
	private Path getPath(String documentName) {
		String dir = pathInfo + "\\" + documentName;
		directory = Paths.get(dir).toAbsolutePath().normalize();
		return directory;
	}

	/**
	 * To read details of export upload
	 * 
	 * @param data
	 * @return {@link DocumentUploadRequest}
	 * @throws JsonProcessingException
	 */
	private DocumentUploadRequest uploadDocumentDetails(String data) throws JsonProcessingException {
		return mapper.readValue(data, DocumentUploadRequest.class);
	}

	/**
	 * Add Export Invoice.
	 * 
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody addInvoice(ExportInvoiceRequest request) {

		if (Boolean.TRUE.equals(invoiceRepository.existsByInvoiceNumber(request.getInvoiceNumber())))
			throw new InvoiceException(INVOICE_ALREADY_EXISTS);

		if (Boolean.TRUE.equals(itemRepository.existsBySerialNumber(request.getItemRequest().getSerialNumber())))
			throw new ItemException(ITEM_ALREADY_EXISTS);

		if (Boolean.TRUE.equals(shipmentDetailsRepository.existsByWayBillNumber(request.getWayBillNumber())))
			throw new ShipmentDetailsException(SHIPMENT_DETAILS_ALREADY_EXISTS);

		if (Boolean.TRUE.equals(shipmentItemRepository.existsByInternalOrderNumber(request.getInternalOrderNumber())))
			throw new ShipmentItemException(SHIPMENT_ITEM_ALREADY_EXISTS);

		ExportInvoice invoice = ExportInvoice.builder().invoiceNumber(request.getInvoiceNumber()).build();

		ShipmentItem shipmentItem = ShipmentItem.builder().internalOrderNumber(request.getInternalOrderNumber())
				.build();

		ItemDetails itemDetails = ItemDetails.builder().serialNumber(request.getItemRequest().getSerialNumber())
				.build();
		Set<Code> codeList = request.getItemRequest().getCodeRequestList().stream().map(codeRequest -> {
			CodeTypeDetails codeType = codeTypeRepository.findByCodeType(codeRequest.getCodeType())
					.orElse(CodeTypeDetails.builder().codeType(codeRequest.getCodeType()).build());
			return Code.builder().codeType(codeType).codeValue(codeRequest.getCodeValue()).item(itemDetails).build();
		}).collect(Collectors.toSet());
		itemDetails.setManufacturerName(request.getItemRequest().getManufacturerName());
		itemDetails.setPartNumber(request.getItemRequest().getPartNumber());
		itemDetails.setQuantity(request.getItemRequest().getQuantity());
		itemDetails.setDescription(request.getItemRequest().getDescription());
		itemDetails.setUnitOfMeasure(request.getItemRequest().getUnitOfMeasure());
		itemDetails.setUnitPrice(request.getItemRequest().getUnitPrice());
		itemDetails.setCountryOfOrigin(request.getItemRequest().getCountryOfOrigin());
		itemDetails.setCodeList(codeList);

		Set<Inspection> inspectionList = request.getInspectionList().stream().map(inspectionRequest -> {
			InspectionTypeDetails inspectionType = inspectionTypeRepository
					.findByInspectionType(inspectionRequest.getInspectionType())
					.orElse(InspectionTypeDetails.builder().inspectionType(inspectionRequest.getInspectionType()).build());
			return Inspection.builder().inspectionType(inspectionType)
					.inspectionValue(inspectionRequest.getInspectionValue()).shipmentItem(shipmentItem).build();
		}).collect(Collectors.toSet());

		shipmentItem.setAmount(request.getAmount());
		shipmentItem.setItemDetails(itemDetails);
		shipmentItem.setInspectionList(inspectionList);
		shipmentItem.setExportInvoice(invoice);

		ExporterDetails exporterDetails = ExporterDetails.builder().exporterName(request.getExporterName())
				.shipperName(request.getShipperName()).countryOfExport(request.getCountryOfExport()).build();

		ImporterDetails importerDetails = ImporterDetails.builder().importerName(request.getImporterName())
				.consigneeName(request.getConsigneeName()).countryOfImport(request.getCountryOfImport()).build();

		IncotermTypeDetails incotermType = incotermTypeRepository.findByIncotermType(request.getIncotermType())
				.orElse(IncotermTypeDetails.builder().incotermType(request.getIncotermType()).build());
		ShipmentDetails shipmentDetails = ShipmentDetails.builder().modeOfTransport(request.getModeOfTransport())
				.shipVia(request.getShipVia()).currency(request.getCurrency()).packageNumber(request.getPackageNumber())
				.grossWeight(request.getGrossWeight()).wayBillNumber(request.getWayBillNumber())
				.incotermType(incotermType).build();

		Set<ShipmentItem> shipmentItemList = new HashSet<>();
		shipmentItemList.add(shipmentItem);

		invoice.setCustomerName(request.getCustomerName());
		invoice.setExportReference(request.getExportReference());
		invoice.setInvoiceDate(request.getInvoiceDate());
		invoice.setShipmentDate(request.getShipmentDate());
		invoice.setRemarks(request.getRemarks());
		invoice.setExporterDetails(exporterDetails);
		invoice.setImporterDetails(importerDetails);
		invoice.setShipmentDetails(shipmentDetails);
		invoice.setShipmentItemList(shipmentItemList);

		invoiceRepository.save(invoice);
		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(INVOICE_ADDED_SUCCESSFULLY).build();
	}

	/**
	 * Upload export document
	 * 
	 * @param file
	 * @param data
	 * @return {@link ZenxlResponseBody}
	 * @throws IOException
	 */
	public ZenxlResponseBody uploadDocument(MultipartFile file, String data) throws IOException {
		if (file.isEmpty()) {
			throw new DocumentException(PLEASE_SELECT_DOCUMENT);
		} else if (data.isEmpty()) {
			throw new DataException(DATA_SHOULD_NOT_BE_EMPTY);
		} else {
			DocumentUploadRequest documentUpload = null;
			try {
				documentUpload = uploadDocumentDetails(data);

				Set<ConstraintViolation<DocumentUploadRequest>> violations = validator.validate(documentUpload);

				if (!violations.isEmpty()) {
					throw new ConstraintViolationException(violations);
				}
				directory = getPath(file.getOriginalFilename());
				Path path = directory.resolve(file.getOriginalFilename());
				Files.createDirectories(directory);
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

				ExportInvoice exportInvoice = invoiceRepository.findByInvoiceNumber(documentUpload.getInvoiceNumber())
						.orElseThrow(() -> new InvoiceException(INVOICE_NOT_FOUND));

				DocumentType documentType = documentTypeRepository
						.findByDocumentTypeName(documentUpload.getDocumentTypeName())
						.orElse(DocumentType.builder().documentTypeName(documentUpload.getDocumentTypeName()).build());

				ExportDocument exportDocument = ExportDocument.builder().documentName(file.getOriginalFilename())
						.documentFormat(file.getContentType()).documentUrl(path.toString()).documentType(documentType)
						.exportInvoice(exportInvoice).build();

				exportDocumentRepository.save(exportDocument);

			} catch (IOException exception) {
				throw new IOException(exception.getMessage());
			}
		}
		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(DOCUMENT_UPLOADED_SUCCESSFULLY).build();
	}

	/**
	 * View export invoice by invoice number.
	 * 
	 * @param invoiceNumber
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewInvoice(Long invoiceNumber) {

		ExportInvoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
				.orElseThrow(() -> new InvoiceException(INVOICE_NOT_FOUND));

		Set<ShipmentItemResponse> shipmentItemList = invoice.getShipmentItemList().stream()
				.map(shipment -> ShipmentItemResponse.builder().partNumber(shipment.getItemDetails().getPartNumber())
						.serialNumber(shipment.getItemDetails().getSerialNumber())
						.quantity(shipment.getItemDetails().getQuantity())
						.description(shipment.getItemDetails().getDescription()).build())
				.collect(Collectors.toSet());

		InvoiceResponse invoiceDetails = InvoiceResponse.builder().invoiceNumber(invoice.getInvoiceNumber())
				.invoiceDate(invoice.getInvoiceDate()).shipmentDate(invoice.getShipmentDate())
				.exporterName(invoice.getExporterDetails().getExporterName())
				.shipperName(invoice.getExporterDetails().getShipperName())
				.countryOfExport(invoice.getExporterDetails().getCountryOfExport())
				.importerName(invoice.getImporterDetails().getImporterName())
				.consigneeName(invoice.getImporterDetails().getConsigneeName())
				.countryOfImport(invoice.getImporterDetails().getCountryOfImport())
				.incotermType(invoice.getShipmentDetails().getIncotermType().getIncotermType())
				.modeOfTransport(invoice.getShipmentDetails().getModeOfTransport())
				.shipVia(invoice.getShipmentDetails().getShipVia()).currency(invoice.getShipmentDetails().getCurrency())
				.packageNumber(invoice.getShipmentDetails().getPackageNumber())
				.grossWeight(invoice.getShipmentDetails().getGrossWeight())
				.wayBillNumber(invoice.getShipmentDetails().getWayBillNumber()).remarks(invoice.getRemarks())
				.shipmentItemList(shipmentItemList).build();

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ITEM_FETCHED_SUCCESSFULLY)
				.data(invoiceDetails).build();
	}

	/**
	 * View Export Invoice List
	 * 
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewAllInvoices() {

		List<ExportInvoice> invoiceList = invoiceRepository.findAll();
		if (invoiceList.isEmpty())
			throw new InvoiceException(INVOICE_NOT_FOUND);

		Set<InvoiceResponse> invoiceListData = invoiceList.stream().map(invoice -> {

			Set<ShipmentItemResponse> shipmentItemList = invoice.getShipmentItemList().stream()
					.map(shipmentItem -> ShipmentItemResponse.builder()
							.partNumber(shipmentItem.getItemDetails().getPartNumber())
							.serialNumber(shipmentItem.getItemDetails().getSerialNumber())
							.quantity(shipmentItem.getItemDetails().getQuantity())
							.description(shipmentItem.getItemDetails().getDescription()).build())
					.collect(Collectors.toSet());

			return InvoiceResponse.builder().invoiceNumber(invoice.getInvoiceNumber())
					.invoiceDate(invoice.getInvoiceDate()).shipmentDate(invoice.getShipmentDate())
					.exporterName(invoice.getExporterDetails().getExporterName())
					.shipperName(invoice.getExporterDetails().getShipperName())
					.countryOfExport(invoice.getExporterDetails().getCountryOfExport())
					.importerName(invoice.getImporterDetails().getImporterName())
					.consigneeName(invoice.getImporterDetails().getConsigneeName())
					.countryOfImport(invoice.getImporterDetails().getCountryOfImport())
					.incotermType(invoice.getShipmentDetails().getIncotermType().getIncotermType())
					.modeOfTransport(invoice.getShipmentDetails().getModeOfTransport())
					.shipVia(invoice.getShipmentDetails().getShipVia())
					.currency(invoice.getShipmentDetails().getCurrency())
					.packageNumber(invoice.getShipmentDetails().getPackageNumber())
					.grossWeight(invoice.getShipmentDetails().getGrossWeight())
					.wayBillNumber(invoice.getShipmentDetails().getWayBillNumber()).remarks(invoice.getRemarks())
					.shipmentItemList(shipmentItemList).build();
		}).collect(Collectors.toSet());

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(INVOICE_LIST_FETCHED_SUCCESSFULLY)
				.data(invoiceListData).build();
	}

	/**
	 * Delete export invoice by invoice number.
	 * 
	 * @param invoiceNumber
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody deleteInvoice(Long invoiceNumber) {

		ExportInvoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
				.orElseThrow(() -> new InvoiceException(INVOICE_NOT_FOUND));

		invoiceRepository.delete(invoice);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(INVOICE_DELETED_SUCCESSFULLY).build();
	}

	/**
	 * Update the export invoice.
	 * 
	 * @param invoiceNumber
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody updateInvoice(Long invoiceNumber, ExportInvoiceUpdateRequest request) {

		ExportInvoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
				.orElseThrow(() -> new InvoiceException(INVOICE_NOT_FOUND));

		if (!request.getWayBillNumber().equals(invoice.getShipmentDetails().getWayBillNumber())) {
			if (Boolean.TRUE.equals(shipmentDetailsRepository.existsByWayBillNumber(request.getWayBillNumber()))) {
				throw new InvoiceException(REQUESTED_WAYBILL_NUMBER_ALREADY_EXISTS);
			}
		} else {
			Optional<IncotermTypeDetails> findAnyIncotermType = incotermTypeRepository.findAll().stream()
					.filter(incoterm -> incoterm.getIncotermType().equals(request.getIncotermType())).findAny();

			invoice.setCustomerName(request.getCustomerName());
			invoice.setInvoiceDate(request.getInvoiceDate());
			invoice.setShipmentDate(request.getShipmentDate());
			invoice.setExportReference(request.getExportReference());
			invoice.setRemarks(request.getRemarks());

			invoice.getExporterDetails().setExporterName(request.getExporterName());
			invoice.getExporterDetails().setShipperName(request.getShipperName());
			invoice.getExporterDetails().setCountryOfExport(request.getCountryOfExport());

			invoice.getImporterDetails().setImporterName(request.getImporterName());
			invoice.getImporterDetails().setConsigneeName(request.getConsigneeName());
			invoice.getImporterDetails().setCountryOfImport(request.getCountryOfImport());

			invoice.getShipmentDetails().setModeOfTransport(request.getModeOfTransport());
			invoice.getShipmentDetails().setShipVia(request.getShipVia());
			invoice.getShipmentDetails().setCurrency(request.getCurrency());
			invoice.getShipmentDetails().setPackageNumber(request.getPackageNumber());
			invoice.getShipmentDetails().setGrossWeight(request.getGrossWeight());
			invoice.getShipmentDetails().setWayBillNumber(request.getWayBillNumber());
			if (findAnyIncotermType.isPresent()) {
				invoice.getShipmentDetails().setIncotermType(findAnyIncotermType.get());
			} else {
				throw new IncotermTypeException(INCOTERM_TYPE_NOT_FOUND);
			}

			invoice.getShipmentItemList().stream().forEach(shipmentItem -> {
				shipmentItem.setAmount(request.getAmount());
				shipmentItem.getItemDetails().setManufacturerName(request.getItemUpdateRequest().getManufacturerName());
				shipmentItem.getItemDetails().setPartNumber(request.getItemUpdateRequest().getPartNumber());
				shipmentItem.getItemDetails().setQuantity(request.getItemUpdateRequest().getQuantity());
				shipmentItem.getItemDetails().setDescription(request.getItemUpdateRequest().getDescription());
				shipmentItem.getItemDetails().setUnitOfMeasure(request.getItemUpdateRequest().getUnitOfMeasure());
				shipmentItem.getItemDetails().setUnitPrice(request.getItemUpdateRequest().getUnitPrice());
				shipmentItem.getItemDetails().setCountryOfOrigin(request.getItemUpdateRequest().getCountryOfOrigin());
			});

			Set<Inspection> inspections = invoice.getShipmentItemList().stream()
					.flatMap(shipmentItem -> shipmentItem.getInspectionList().stream().map(inspection -> inspection))
					.collect(Collectors.toSet());

			request.getInspectionList().stream()
					.forEach(inspectionRequest -> inspections.stream().filter(inspection -> inspection
							.getInspectionType().getInspectionType().equals(inspectionRequest.getInspectionType()))
							.map(inspection -> {
								inspection.setInspectionValue(inspectionRequest.getInspectionValue());
								return inspection;
							}));

			Set<Code> codes = invoice.getShipmentItemList().stream()
					.flatMap(shipmentItem -> shipmentItem.getItemDetails().getCodeList().stream().map(code -> code))
					.collect(Collectors.toSet());

			request.getItemUpdateRequest().getCodeRequestList().stream().forEach(codeRequest -> codes.stream()
					.filter(code -> code.getCodeType().getCodeType().equals(codeRequest.getCodeType())).map(code -> {
						code.setCodeValue(codeRequest.getCodeValue());
						return code;
					}));

			invoiceRepository.save(invoice);

		}

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(INVOICE_UPDATED_SUCCESSFULLY).build();
	}

	/**
	 * Update the remark associated with the export invoice.
	 * 
	 * @param invoiceNumber
	 * @param remark
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody updateInvoiceRemark(Long invoiceNumber, String remark) {

		ExportInvoice invoice = invoiceRepository.findByInvoiceNumber(invoiceNumber)
				.orElseThrow(() -> new InvoiceException(INVOICE_NOT_FOUND));
		invoice.setRemarks(remark);

		invoiceRepository.save(invoice);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(INVOICE_UPDATED_SUCCESSFULLY).build();
	}

	/**
	 * Add Shipment Item
	 * 
	 * @param exportInvoiceNumber
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody addShipmentItem(Long exportInvoiceNumber, ShipmentRequest request) {

		ExportInvoice exportInvoice = invoiceRepository.findByInvoiceNumber(exportInvoiceNumber)
				.orElseThrow(() -> new InvoiceException(INVOICE_NOT_FOUND));

		if (Boolean.TRUE.equals(shipmentItemRepository.existsByInternalOrderNumber(request.getInternalOrderNumber())))
			throw new ShipmentItemException(SHIPMENT_ITEM_ALREADY_EXISTS);

		if (Boolean.TRUE.equals(itemRepository.existsBySerialNumber(request.getItemRequest().getSerialNumber())))
			throw new ItemException(ITEM_ALREADY_EXISTS);

		ShipmentItem shipmentItem = ShipmentItem.builder().internalOrderNumber(request.getInternalOrderNumber())
				.build();

		ItemDetails itemDetails = ItemDetails.builder().serialNumber(request.getItemRequest().getSerialNumber())
				.build();
		Set<Code> codeList = request.getItemRequest().getCodeRequestList().stream().map(codeRequest -> {
			CodeTypeDetails codeType = codeTypeRepository.findByCodeType(codeRequest.getCodeType())
					.orElse(CodeTypeDetails.builder().codeType(codeRequest.getCodeType()).build());
			return Code.builder().codeType(codeType).codeValue(codeRequest.getCodeValue()).item(itemDetails).build();
		}).collect(Collectors.toSet());
		itemDetails.setManufacturerName(request.getItemRequest().getManufacturerName());
		itemDetails.setPartNumber(request.getItemRequest().getPartNumber());
		itemDetails.setQuantity(request.getItemRequest().getQuantity());
		itemDetails.setDescription(request.getItemRequest().getDescription());
		itemDetails.setUnitOfMeasure(request.getItemRequest().getUnitOfMeasure());
		itemDetails.setUnitPrice(request.getItemRequest().getUnitPrice());
		itemDetails.setCountryOfOrigin(request.getItemRequest().getCountryOfOrigin());
		itemDetails.setCodeList(codeList);

		Set<Inspection> inspectionList = request.getInspectionList().stream().map(inspectionRequest -> {
			InspectionTypeDetails inspectionType = inspectionTypeRepository
					.findByInspectionType(inspectionRequest.getInspectionType())
					.orElse(InspectionTypeDetails.builder().inspectionType(inspectionRequest.getInspectionType()).build());
			return Inspection.builder().inspectionType(inspectionType)
					.inspectionValue(inspectionRequest.getInspectionValue()).shipmentItem(shipmentItem).build();
		}).collect(Collectors.toSet());

		shipmentItem.setAmount(request.getAmount());
		shipmentItem.setItemDetails(itemDetails);
		shipmentItem.setInspectionList(inspectionList);
		shipmentItem.setExportInvoice(exportInvoice);

		shipmentItemRepository.save(shipmentItem);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(SHIPMENT_ITEM_ADDED_SUCCESSFULLY).build();
	}

	/**
	 * View Shipment Item
	 * 
	 * @param internalOrderNumber
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewShipmentItem(Long internalOrderNumber) {

		ShipmentItem shipmentItem = shipmentItemRepository.findByInternalOrderNumber(internalOrderNumber)
				.orElseThrow(() -> new ShipmentItemException(SHIPMENT_ITEM_NOT_FOUND));

		Set<CodeResponse> codeList = shipmentItem.getItemDetails().getCodeList().stream()
				.map(codeRequest -> CodeResponse.builder().codeType(codeRequest.getCodeType().getCodeType())
						.codeValue(codeRequest.getCodeValue()).build())
				.collect(Collectors.toSet());

		Set<CertificateResponse> certificateList = shipmentItem.getItemDetails().getCertificateList().stream()
				.map(certificateRequest -> CertificateResponse.builder()
						.certificateNumber(certificateRequest.getCertificateNumber())
						.expiryDate(certificateRequest.getExpiryDate())
						.certificateType(certificateRequest.getCertificateType().getCertificateType()).build())
				.collect(Collectors.toSet());

		Set<InspectionResponse> inspectionList = shipmentItem.getInspectionList().stream()
				.map(inspection -> InspectionResponse.builder()
						.inspectionType(inspection.getInspectionType().getInspectionType())
						.inspectionValue(inspection.getInspectionValue()).build())
				.collect(Collectors.toSet());

		ShipmentItemResponse viewShipmentItem = ShipmentItemResponse.builder()
				.partNumber(shipmentItem.getItemDetails().getPartNumber())
				.internalOrderNumber(shipmentItem.getInternalOrderNumber())
				.serialNumber(shipmentItem.getItemDetails().getSerialNumber())
				.manufacturerName(shipmentItem.getItemDetails().getManufacturerName())
				.description(shipmentItem.getItemDetails().getDescription())
				.quantity(shipmentItem.getItemDetails().getQuantity())
				.countryOfOrigin(shipmentItem.getItemDetails().getCountryOfOrigin())
				.unitOfMeasure(shipmentItem.getItemDetails().getUnitOfMeasure())
				.unitPrice(shipmentItem.getItemDetails().getUnitPrice()).amount(shipmentItem.getAmount())
				.codeList(codeList).certificateList(certificateList).inspectionList(inspectionList).build();

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(SHIPMENT_ITEM_FETCHED_SUCCESSFULLY)
				.data(viewShipmentItem).build();
	}

	/**
	 * View Shipment Item List
	 * 
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewAllShipmentItems() {

		List<ShipmentItem> shipmentItems = shipmentItemRepository.findAll();
		if (shipmentItems.isEmpty())
			throw new ShipmentItemException(SHIPMENT_ITEM_NOT_FOUND);

		Set<ShipmentItemResponse> shipmentItemList = shipmentItems.stream().map(shipmentItem -> {

			Set<CodeResponse> codeList = shipmentItem.getItemDetails().getCodeList().stream()
					.map(codeRequest -> CodeResponse.builder().codeType(codeRequest.getCodeType().getCodeType())
							.codeValue(codeRequest.getCodeValue()).build())
					.collect(Collectors.toSet());

			Set<CertificateResponse> certificateList = shipmentItem.getItemDetails().getCertificateList().stream()
					.map(certificateRequest -> CertificateResponse.builder()
							.certificateNumber(certificateRequest.getCertificateNumber())
							.expiryDate(certificateRequest.getExpiryDate())
							.certificateType(certificateRequest.getCertificateType().getCertificateType()).build())
					.collect(Collectors.toSet());

			Set<InspectionResponse> inspectionList = shipmentItem.getInspectionList().stream()
					.map(inspection -> InspectionResponse.builder()
							.inspectionType(inspection.getInspectionType().getInspectionType())
							.inspectionValue(inspection.getInspectionValue()).build())
					.collect(Collectors.toSet());

			return ShipmentItemResponse.builder().partNumber(shipmentItem.getItemDetails().getPartNumber())
					.internalOrderNumber(shipmentItem.getInternalOrderNumber())
					.serialNumber(shipmentItem.getItemDetails().getSerialNumber())
					.manufacturerName(shipmentItem.getItemDetails().getManufacturerName())
					.description(shipmentItem.getItemDetails().getDescription())
					.quantity(shipmentItem.getItemDetails().getQuantity())
					.countryOfOrigin(shipmentItem.getItemDetails().getCountryOfOrigin())
					.unitOfMeasure(shipmentItem.getItemDetails().getUnitOfMeasure())
					.unitPrice(shipmentItem.getItemDetails().getUnitPrice()).amount(shipmentItem.getAmount())
					.codeList(codeList).certificateList(certificateList).inspectionList(inspectionList).build();
		}).collect(Collectors.toSet());

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ALL_SHIPMENT_ITEMS_FETCHED_SUCCESSFULLY)
				.data(shipmentItemList).build();
	}

	/**
	 * Delete Shipment Item
	 * 
	 * @param internalOrderNumber
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody deleteShipmentItem(Long internalOrderNumber) {

		ShipmentItem shipmentItem = shipmentItemRepository.findByInternalOrderNumber(internalOrderNumber)
				.orElseThrow(() -> new ShipmentItemException(SHIPMENT_ITEM_NOT_FOUND));

		shipmentItemRepository.delete(shipmentItem);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(SHIPMENT_ITEM_DELETED_SUCCESSFULLY).build();
	}

	/**
	 * Update Shipment Item
	 * 
	 * @param internalOrderNumber
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody updateShipmentItem(Long internalOrderNumber, ShipmentUpdateRequest request) {

		ShipmentItem shipmentItem = shipmentItemRepository.findByInternalOrderNumber(internalOrderNumber)
				.orElseThrow(() -> new ShipmentItemException(SHIPMENT_ITEM_NOT_FOUND));

		shipmentItem.setAmount(request.getAmount());
		shipmentItem.getItemDetails().setManufacturerName(request.getItemUpdateRequest().getManufacturerName());
		shipmentItem.getItemDetails().setPartNumber(request.getItemUpdateRequest().getPartNumber());
		shipmentItem.getItemDetails().setQuantity(request.getItemUpdateRequest().getQuantity());
		shipmentItem.getItemDetails().setDescription(request.getItemUpdateRequest().getDescription());
		shipmentItem.getItemDetails().setUnitOfMeasure(request.getItemUpdateRequest().getUnitOfMeasure());
		shipmentItem.getItemDetails().setUnitPrice(request.getItemUpdateRequest().getUnitPrice());
		shipmentItem.getItemDetails().setCountryOfOrigin(request.getItemUpdateRequest().getCountryOfOrigin());

		request.getInspectionList().stream()
				.forEach(inspectionRequest -> shipmentItem.getInspectionList().stream()
						.filter(inspection -> inspection.getInspectionType().getInspectionType()
								.equals(inspectionRequest.getInspectionType()))
						.forEach(inspection -> inspection.setInspectionValue(inspectionRequest.getInspectionValue())));

		request.getItemUpdateRequest().getCodeRequestList().stream()
				.forEach(codeRequest -> shipmentItem.getItemDetails().getCodeList().stream()
						.filter(code -> code.getCodeType().getCodeType().equals(codeRequest.getCodeType()))
						.forEach(code -> code.setCodeValue(codeRequest.getCodeValue())));

		shipmentItemRepository.save(shipmentItem);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(SHIPMENT_ITEM_UPDATED_SUCCESSFULLY).build();
	}

	/**
	 * Add Bill Of Entry
	 * 
	 * @param exportInvoiceNumber
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody addBillOfEntry(Long exportInvoiceNumber, BillOfEntryRequest request) {

		ExportInvoice invoice = invoiceRepository.findByInvoiceNumber(exportInvoiceNumber)
				.orElseThrow(() -> new InvoiceException(INVOICE_NOT_FOUND));

		BillOfEntry billOfEntry = BillOfEntry.builder().invoiceNumber(exportInvoiceNumber)
				.invoiceDate(invoice.getInvoiceDate()).invoiceItems(invoice.getShipmentItemList().size())
				.invoiceAmount(invoice.getShipmentItemList().stream().mapToDouble(ShipmentItem::getAmount).sum())
				.currency(invoice.getShipmentDetails().getCurrency()).exchange(request.getExchange())
				.portCode(request.getPortCode()).boeNumber(request.getBoeNumber()).boeDate(request.getBoeDate())
				.grossWeight(invoice.getShipmentDetails().getGrossWeight()).cHAgent(request.getCHAgent())
				.exportInvoice(invoice)
				.duty(DutySummary.builder().bcd(request.getBcd()).acd(request.getAcd()).sws(request.getSws())
						.nccd(request.getNccd()).add(request.getAdd()).cvd(request.getCvd()).igst(request.getIgst())
						.cess(request.getCess()).assessedvalue(request.getAssessedvalue())
						.totalDuty(request.getTotalDuty()).iNT(request.getINT()).pnlty(request.getPnlty())
						.fine(request.getFine()).totalDutyAmount(request.getTotalDutyAmount()).build())
				.build();

		billOfEntryRepository.save(billOfEntry);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(BILL_OF_ENTRY_ADDED_SUCCESSFULLY).build();
	}

	/**
	 * View Bill Of Entry
	 * 
	 * @param exportInvoiceNumber
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewBillOfEntry(Long exportInvoiceNumber) {

		BillOfEntry billOfEntry = billOfEntryRepository.findByInvoiceNumber(exportInvoiceNumber)
				.orElseThrow(() -> new BillOfEntryException(BILL_OF_ENTRY_NOT_FOUND));

		Set<ShipmentItemResponse> shipmentItemResponse = billOfEntry.getExportInvoice().getShipmentItemList().stream()
				.map(shipment -> ShipmentItemResponse.builder().partNumber(shipment.getItemDetails().getPartNumber())
						.serialNumber(shipment.getItemDetails().getSerialNumber())
						.description(shipment.getItemDetails().getDescription())
						.unitPrice(shipment.getItemDetails().getUnitPrice())
						.quantity(shipment.getItemDetails().getQuantity()).amount(shipment.getAmount()).build())
				.collect(Collectors.toSet());

		DutySummaryResponse dutySummaryResponse = DutySummaryResponse.builder().bcd(billOfEntry.getDuty().getBcd())
				.acd(billOfEntry.getDuty().getAcd()).sws(billOfEntry.getDuty().getSws())
				.nccd(billOfEntry.getDuty().getNccd()).add(billOfEntry.getDuty().getAdd())
				.cvd(billOfEntry.getDuty().getCvd()).igst(billOfEntry.getDuty().getIgst())
				.cess(billOfEntry.getDuty().getCess()).totalDuty(billOfEntry.getDuty().getTotalDuty())
				.assessedvalue(billOfEntry.getDuty().getAssessedvalue()).iNT(billOfEntry.getDuty().getINT())
				.pnlty(billOfEntry.getDuty().getPnlty()).fine(billOfEntry.getDuty().getFine())
				.totalDutyAmount(billOfEntry.getDuty().getTotalDutyAmount()).build();

		BillOfEntryResponse response = BillOfEntryResponse.builder().invoiceNumber(billOfEntry.getInvoiceNumber())
				.invoiceDate(billOfEntry.getInvoiceDate()).invoiceItems(billOfEntry.getInvoiceItems())
				.invoiceAmount(billOfEntry.getInvoiceAmount()).currency(billOfEntry.getCurrency())
				.exchange(billOfEntry.getExchange()).portCode(billOfEntry.getPortCode())
				.boeNumber(billOfEntry.getBoeNumber()).boeDate(billOfEntry.getBoeDate())
				.grossWeight(billOfEntry.getGrossWeight())
				.importerName(billOfEntry.getExportInvoice().getImporterDetails().getImporterName())
				.cHAgent(billOfEntry.getCHAgent()).dutySummary(dutySummaryResponse)
				.shipmentItemList(shipmentItemResponse).build();

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(BILL_OF_ENTRY_FETCHED_SUCCESSFULLY)
				.data(response).build();
	}

	/**
	 * View Bill Of Entry List
	 * 
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody viewAllBillOfEntries() {

		List<BillOfEntry> allBillOfEntries = billOfEntryRepository.findAll();
		if (allBillOfEntries.isEmpty())
			throw new BillOfEntryException(BILL_OF_ENTRY_NOT_FOUND);

		Set<BillOfEntryResponse> billOfEntriesList = allBillOfEntries.stream().map(billOfEntry -> {

			Set<ShipmentItemResponse> shipmentItemResponse = billOfEntry.getExportInvoice().getShipmentItemList()
					.stream()
					.map(shipment -> ShipmentItemResponse.builder()
							.partNumber(shipment.getItemDetails().getPartNumber())
							.serialNumber(shipment.getItemDetails().getSerialNumber())
							.description(shipment.getItemDetails().getDescription())
							.unitPrice(shipment.getItemDetails().getUnitPrice())
							.quantity(shipment.getItemDetails().getQuantity()).amount(shipment.getAmount()).build())
					.collect(Collectors.toSet());

			DutySummaryResponse dutySummaryResponse = DutySummaryResponse.builder().bcd(billOfEntry.getDuty().getBcd())
					.acd(billOfEntry.getDuty().getAcd()).sws(billOfEntry.getDuty().getSws())
					.nccd(billOfEntry.getDuty().getNccd()).add(billOfEntry.getDuty().getAdd())
					.cvd(billOfEntry.getDuty().getCvd()).igst(billOfEntry.getDuty().getIgst())
					.cess(billOfEntry.getDuty().getCess()).totalDuty(billOfEntry.getDuty().getTotalDuty())
					.assessedvalue(billOfEntry.getDuty().getAssessedvalue()).iNT(billOfEntry.getDuty().getINT())
					.pnlty(billOfEntry.getDuty().getPnlty()).fine(billOfEntry.getDuty().getFine())
					.totalDutyAmount(billOfEntry.getDuty().getTotalDutyAmount()).build();

			return BillOfEntryResponse.builder().invoiceNumber(billOfEntry.getInvoiceNumber())
					.invoiceDate(billOfEntry.getInvoiceDate()).invoiceItems(billOfEntry.getInvoiceItems())
					.invoiceAmount(billOfEntry.getInvoiceAmount()).currency(billOfEntry.getCurrency())
					.exchange(billOfEntry.getExchange()).portCode(billOfEntry.getPortCode())
					.boeNumber(billOfEntry.getBoeNumber()).boeDate(billOfEntry.getBoeDate())
					.grossWeight(billOfEntry.getGrossWeight())
					.importerName(billOfEntry.getExportInvoice().getImporterDetails().getImporterName())
					.cHAgent(billOfEntry.getCHAgent()).dutySummary(dutySummaryResponse)
					.shipmentItemList(shipmentItemResponse).build();
		}).collect(Collectors.toSet());

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(ALL_BILL_OF_ENTRIES_ARE_FETCHED_SUCCESSFULLY)
				.data(billOfEntriesList).build();
	}

	/**
	 * Update Bill Of Entry
	 * 
	 * @param billOfEntryId
	 * @param request
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody updateBillOfEntry(Integer billOfEntryId, DutySummaryRequest request) {

		BillOfEntry billOfEntry = billOfEntryRepository.findByBillOfEntryId(billOfEntryId)
				.orElseThrow(() -> new BillOfEntryException(BILL_OF_ENTRY_NOT_FOUND));

		billOfEntry.getDuty().setBcd(request.getBcd());
		billOfEntry.getDuty().setAcd(request.getAcd());
		billOfEntry.getDuty().setSws(request.getSws());
		billOfEntry.getDuty().setNccd(request.getNccd());
		billOfEntry.getDuty().setAdd(request.getAdd());
		billOfEntry.getDuty().setCvd(request.getCvd());
		billOfEntry.getDuty().setIgst(request.getIgst());
		billOfEntry.getDuty().setCess(request.getCess());
		billOfEntry.getDuty().setAssessedvalue(request.getAssessedvalue());
		billOfEntry.getDuty().setTotalDuty(request.getTotalDuty());
		billOfEntry.getDuty().setINT(request.getINT());
		billOfEntry.getDuty().setPnlty(request.getPnlty());
		billOfEntry.getDuty().setFine(request.getFine());
		billOfEntry.getDuty().setTotalDutyAmount(request.getTotalDutyAmount());

		billOfEntryRepository.save(billOfEntry);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(BILL_OF_ENTRY_UPDATED_SUCCESSFULLY).build();
	}

	/**
	 * Delete Bill Of Entry
	 * 
	 * @param billOfEntryId
	 * @return {@link ZenxlResponseBody}
	 */
	public ZenxlResponseBody deleteBillOfEntry(Integer billOfEntryId) {

		BillOfEntry billOfEntry = billOfEntryRepository.findByBillOfEntryId(billOfEntryId)
				.orElseThrow(() -> new BillOfEntryException(BILL_OF_ENTRY_NOT_FOUND));

		billOfEntryRepository.delete(billOfEntry);

		return ZenxlResponseBody.builder().isError(IS_ERROR_FALSE).message(BILL_OF_ENTRY_DELETED_SUCCESSFULLY).build();
	}

}
