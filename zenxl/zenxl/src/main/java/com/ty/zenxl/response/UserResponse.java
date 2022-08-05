package com.ty.zenxl.response;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response DTO for {@code User}
 * 
 * @author Indrajit
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_DEFAULT)
public class UserResponse {

	private Integer userId;
	private String username;
	private String email;
	private String role;
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateOfBirth;
	private String gender;
	private Boolean status;
}
