package com.ty.zenxl.response;

import java.util.Date;

import org.springframework.core.io.Resource;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response DTO for {@code Certificate}
 * 
 * Ignores null valued properties.
 * 
 * @author Abhishek
 * @version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(Include.NON_DEFAULT)
public class CertificateResponse {

	private String certificateFormat;

	private Resource resource;

	private String certificateNumber;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date expiryDate;

	private String certificateType;

}
