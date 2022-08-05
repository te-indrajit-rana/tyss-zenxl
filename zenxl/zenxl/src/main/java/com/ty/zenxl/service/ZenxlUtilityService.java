package com.ty.zenxl.service;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.ty.zenxl.entity.CertificateTypeDetails;
import com.ty.zenxl.entity.CodeTypeDetails;
import com.ty.zenxl.entity.HsCode;
import com.ty.zenxl.entity.IncotermTypeDetails;
import com.ty.zenxl.entity.InspectionTypeDetails;
import com.ty.zenxl.exception.CertificateTypeException;
import com.ty.zenxl.exception.CodeTypeException;
import com.ty.zenxl.exception.HsCodeException;
import com.ty.zenxl.exception.IncotermTypeException;
import com.ty.zenxl.exception.InspectionTypeException;
import com.ty.zenxl.repository.CertificateTypeRepository;
import com.ty.zenxl.repository.CodeTypeRepository;
import com.ty.zenxl.repository.HsCodeRepository;
import com.ty.zenxl.repository.IncotermTypeRepository;
import com.ty.zenxl.repository.InspectionTypeRepository;
import com.ty.zenxl.request.CertificateTypeRequest;
import com.ty.zenxl.request.CodeTypeRequest;
import com.ty.zenxl.request.HsCodeRequest;
import com.ty.zenxl.request.IncotermTypeRequest;
import com.ty.zenxl.request.InspectionTypeRequest;
import com.ty.zenxl.request.UpdateCertificateTypeRequest;
import com.ty.zenxl.request.UpdateCodeTypeRequest;
import com.ty.zenxl.request.UpdateHsCodeRequest;
import com.ty.zenxl.request.UpdateIncotermTypeRequest;
import com.ty.zenxl.request.UpdateInspectionTypeRequest;
import com.ty.zenxl.response.CertificateTypeResponse;
import com.ty.zenxl.response.CodeTypeResponse;
import com.ty.zenxl.response.HsCodeResponse;
import com.ty.zenxl.response.IncotermTypeResponse;
import com.ty.zenxl.response.InspectionTypeResponse;

import lombok.RequiredArgsConstructor;

/**
 * Defines the mechanisms implemented for CRUD opeartions for all the utilities
 * such as {@code CertificateType}, {@code CodeType}, {@code IncotermType},
 * {@code InspectionType} and {@code HsCode}.
 *
 * @author Indrajit
 * @version 1.0
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ZenxlUtilityService {

	private final CertificateTypeRepository certificateTypeRepository;
	private final CodeTypeRepository codeTypeRepository;
	private final IncotermTypeRepository incotermTypeRepository;
	private final InspectionTypeRepository inspectionTypeRepository;
	private final HsCodeRepository hsCodeRepository;

	// certificate-type crud api logics

	public CertificateTypeResponse addCertificateType(CertificateTypeRequest request) {
		if (Boolean.TRUE.equals(certificateTypeRepository.existsByCertificateType(request.getCertificateType()))) {
			throw new CertificateTypeException(CERTIFICATE_TYPE_ALREADY_EXISTS);
		}
		CertificateTypeDetails certificateType = CertificateTypeDetails.builder()
				.certificateType(request.getCertificateType()).build();
		CertificateTypeDetails savedCertificateType = certificateTypeRepository.save(certificateType);
		return CertificateTypeResponse.builder().certificateTypeId(savedCertificateType.getCertificateTypeId())
				.certificateType(savedCertificateType.getCertificateType()).build();
	}

	public List<CertificateTypeResponse> findAllCertificateTypes() {

		List<CertificateTypeDetails> findAllCertificateTypes = certificateTypeRepository.findAll();
		return findAllCertificateTypes.stream()
				.map(certificateType -> CertificateTypeResponse.builder()
						.certificateTypeId(certificateType.getCertificateTypeId())
						.certificateType(certificateType.getCertificateType()).build())
				.collect(Collectors.toList());
	}

	public String updateCertificateType(int certificateTypeId, UpdateCertificateTypeRequest request) {

		CertificateTypeDetails certificateType = certificateTypeRepository.findByCertificateTypeId(certificateTypeId)
				.orElseThrow(() -> new CertificateTypeException(
						CERTIFICATE_TYPE_NOT_FOUND_WITH_CERTIFICATE_TYPE_ID + certificateTypeId));

		if (!request.getCertificateType().equals(certificateType.getCertificateType()) && Boolean.TRUE
				.equals(certificateTypeRepository.existsByCertificateType(request.getCertificateType()))) {
			throw new CertificateTypeException(CERTIFICATE_TYPE_ALREADY_EXISTS);
		}

		certificateType.setCertificateType(request.getCertificateType());
		certificateTypeRepository.save(certificateType);
		return UPDATED_SUCCESSFULLY;

	}

	public String deleteCertificateType(int certificateTypeId) {

		CertificateTypeDetails certificateType = certificateTypeRepository.findByCertificateTypeId(certificateTypeId)
				.orElseThrow(() -> new CertificateTypeException(
						CERTIFICATE_TYPE_NOT_FOUND_WITH_CERTIFICATE_TYPE_ID + certificateTypeId));
		certificateTypeRepository.deleteCertificateType(certificateType.getCertificateTypeId());
		return DELETED_SUCCESSFULLY;
	}

	// code-type crud api logics

	public CodeTypeResponse addCodeType(CodeTypeRequest request) {
		if (Boolean.TRUE.equals(codeTypeRepository.existsByCodeType(request.getCodeType()))) {
			throw new CodeTypeException(CODE_TYPE_ALREADY_EXISTS);
		}
		CodeTypeDetails codeType = CodeTypeDetails.builder().codeType(request.getCodeType()).build();
		CodeTypeDetails savedCodeType = codeTypeRepository.save(codeType);
		return CodeTypeResponse.builder().codeTypeId(savedCodeType.getCodeTypeId())
				.codeType(savedCodeType.getCodeType()).build();
	}

	public List<CodeTypeResponse> findAllCodeTypes() {

		List<CodeTypeDetails> findAllCodeTypes = codeTypeRepository.findAll();
		return findAllCodeTypes.stream().map(codeType -> CodeTypeResponse.builder().codeTypeId(codeType.getCodeTypeId())
				.codeType(codeType.getCodeType()).build()).collect(Collectors.toList());
	}

	public String updateCodeType(int codeTypeId, UpdateCodeTypeRequest request) {
		CodeTypeDetails codeType = codeTypeRepository.findByCodeTypeId(codeTypeId)
				.orElseThrow(() -> new CodeTypeException(CODE_TYPE_NOT_FOUND_WITH_CODE_TYPE_ID + codeTypeId));

		if (!request.getCodeType().equals(codeType.getCodeType())
				&& Boolean.TRUE.equals(codeTypeRepository.existsByCodeType(request.getCodeType()))) {
			throw new CodeTypeException(CODE_TYPE_ALREADY_EXISTS);
		}

		codeType.setCodeType(request.getCodeType());
		codeTypeRepository.save(codeType);
		return UPDATED_SUCCESSFULLY;
	}

	public String deleteCodeType(int codeTypeId) {
		CodeTypeDetails codeType = codeTypeRepository.findByCodeTypeId(codeTypeId)
				.orElseThrow(() -> new CodeTypeException(CODE_TYPE_NOT_FOUND_WITH_CODE_TYPE_ID + codeTypeId));
		codeTypeRepository.deleteCodeType(codeType.getCodeTypeId());
		return DELETED_SUCCESSFULLY;
	}

	// incoterm-type crud api logics

	public IncotermTypeResponse addIncotermType(IncotermTypeRequest request) {
		if (Boolean.TRUE.equals(incotermTypeRepository.existsByIncotermType(request.getIncotermType()))) {
			throw new IncotermTypeException(INCOTERM_TYPE_ALREADY_EXISTS);
		}
		IncotermTypeDetails incotermType = IncotermTypeDetails.builder().incotermType(request.getIncotermType())
				.build();
		IncotermTypeDetails savedIncotermType = incotermTypeRepository.save(incotermType);
		return IncotermTypeResponse.builder().incotermTypeId(savedIncotermType.getIncotermTypeId())
				.incotermType(savedIncotermType.getIncotermType()).build();
	}

	public List<IncotermTypeResponse> findAllIncotermTypes() {
		List<IncotermTypeDetails> findAllIncotermTypes = incotermTypeRepository.findAll();
		return findAllIncotermTypes.stream().map(incotermType -> IncotermTypeResponse.builder()
				.incotermTypeId(incotermType.getIncotermTypeId()).incotermType(incotermType.getIncotermType()).build())
				.collect(Collectors.toList());
	}

	public String updateIncotermType(int incotermTypeId, @Valid UpdateIncotermTypeRequest request) {
		IncotermTypeDetails incotermType = incotermTypeRepository.findByIncotermTypeId(incotermTypeId)
				.orElseThrow(() -> new IncotermTypeException(
						INCOTERM_TYPE_NOT_FOUND_WITH_INCOTERM_TYPE_ID + incotermTypeId));

		if (!request.getIncotermType().equals(incotermType.getIncotermType())
				&& Boolean.TRUE.equals(incotermTypeRepository.existsByIncotermType(request.getIncotermType()))) {
			throw new IncotermTypeException(INCOTERM_TYPE_ALREADY_EXISTS);
		}

		incotermType.setIncotermType(request.getIncotermType());
		incotermTypeRepository.save(incotermType);
		return UPDATED_SUCCESSFULLY;
	}

	public String deleteIncotermType(int incotermTypeId) {
		IncotermTypeDetails incotermType = incotermTypeRepository.findByIncotermTypeId(incotermTypeId)
				.orElseThrow(() -> new IncotermTypeException(
						INCOTERM_TYPE_NOT_FOUND_WITH_INCOTERM_TYPE_ID + incotermTypeId));
		incotermTypeRepository.deleteIncotermType(incotermType.getIncotermTypeId());
		return DELETED_SUCCESSFULLY;
	}

	// inspection-type crud api logics

	public InspectionTypeResponse addInspectionType(InspectionTypeRequest request) {
		if (Boolean.TRUE.equals(inspectionTypeRepository.existsByInspectionType(request.getInspectionType()))) {
			throw new InspectionTypeException(INSPECTION_TYPE_ALREADY_EXISTS);
		}
		InspectionTypeDetails inspectionType = InspectionTypeDetails.builder()
				.inspectionType(request.getInspectionType()).build();
		InspectionTypeDetails savedInspectionType = inspectionTypeRepository.save(inspectionType);
		return InspectionTypeResponse.builder().inspectionTypeId(savedInspectionType.getInspectionTypeId())
				.inspectionType(savedInspectionType.getInspectionType()).build();
	}

	public List<InspectionTypeResponse> findAllInspectionTypes() {
		List<InspectionTypeDetails> findAllInspectionTypes = inspectionTypeRepository.findAll();
		return findAllInspectionTypes.stream()
				.map(inspectionType -> InspectionTypeResponse.builder()
						.inspectionTypeId(inspectionType.getInspectionTypeId())
						.inspectionType(inspectionType.getInspectionType()).build())
				.collect(Collectors.toList());
	}

	public String updateInspectionType(int inspectionTypeId, UpdateInspectionTypeRequest request) {
		InspectionTypeDetails inspectionType = inspectionTypeRepository.findByInspectionTypeId(inspectionTypeId)
				.orElseThrow(() -> new InspectionTypeException(
						INSPECTION_TYPE_NOT_FOUND_WITH_INSPECTION_TYPE_ID + inspectionTypeId));

		if (!request.getInspectionType().equals(inspectionType.getInspectionType())
				&& Boolean.TRUE.equals(inspectionTypeRepository.existsByInspectionType(request.getInspectionType()))) {
			throw new InspectionTypeException(INSPECTION_TYPE_ALREADY_EXISTS);
		}

		inspectionType.setInspectionType(request.getInspectionType());
		inspectionTypeRepository.save(inspectionType);
		return UPDATED_SUCCESSFULLY;
	}

	public String deleteInspectionType(int inspectionTypeId) {
		InspectionTypeDetails inspectionType = inspectionTypeRepository.findByInspectionTypeId(inspectionTypeId)
				.orElseThrow(() -> new InspectionTypeException(
						INSPECTION_TYPE_NOT_FOUND_WITH_INSPECTION_TYPE_ID + inspectionTypeId));
		inspectionTypeRepository.deleteInspectionType(inspectionType.getInspectionTypeId());
		return DELETED_SUCCESSFULLY;
	}

	// hscode crud api logics

	public HsCodeResponse addHsCode(HsCodeRequest request) {
		if (Boolean.TRUE.equals(hsCodeRepository.existsByHsCodeType(request.getHsCodeType()))) {
			throw new HsCodeException(HS_CODE_ALREADY_EXISTS);
		}
		HsCode hsCode = HsCode.builder().hsCodeType(request.getHsCodeType()).build();
		HsCode savedHsCode = hsCodeRepository.save(hsCode);
		return HsCodeResponse.builder().hsCodeId(savedHsCode.getHsCodeId()).hsCodeType(savedHsCode.getHsCodeType())
				.build();
	}

	public List<HsCodeResponse> findAllHsCodes() {
		List<HsCode> findAllHsCodes = hsCodeRepository.findAll();
		return findAllHsCodes.stream().map(hsCode -> HsCodeResponse.builder().hsCodeId(hsCode.getHsCodeId())
				.hsCodeType(hsCode.getHsCodeType()).build()).collect(Collectors.toList());
	}

	public String updateHsCode(int hsCodeId, @Valid UpdateHsCodeRequest request) {
		HsCode hsCode = hsCodeRepository.findByHsCodeId(hsCodeId)
				.orElseThrow(() -> new HsCodeException(HS_CODE_NOT_FOUND_WITH_HS_CODE_ID + hsCodeId));

		if (!request.getHsCodeType().equals(hsCode.getHsCodeType())
				&& Boolean.TRUE.equals(hsCodeRepository.existsByHsCodeType(request.getHsCodeType()))) {
			throw new HsCodeException(HS_CODE_ALREADY_EXISTS);
		}

		hsCode.setHsCodeType(request.getHsCodeType());
		hsCodeRepository.save(hsCode);
		return UPDATED_SUCCESSFULLY;
	}

	public String deleteHsCode(int hsCodeId) {
		HsCode hsCode = hsCodeRepository.findByHsCodeId(hsCodeId)
				.orElseThrow(() -> new HsCodeException(HS_CODE_NOT_FOUND_WITH_HS_CODE_ID + hsCodeId));
		hsCodeRepository.deleteHsCode(hsCode.getHsCodeId());
		return DELETED_SUCCESSFULLY;
	}

}
