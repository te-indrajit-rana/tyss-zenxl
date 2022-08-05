package com.ty.zenxl.request;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code User} for update purpose.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlAuthController}
 * 
 * @author Indrajit
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

	@NotNull(message = "username cannot be null")
	@NotBlank(message = "username cannot be blank")
	@Size(min = 2, max = 20, message = "username must be in between 2 to 20 characters.")
	private String username;
	@Email(message = "Please provide a valid email.")
	@NotNull(message = "email must not be null.")
	@NotBlank(message = "email must not be blank.")
	private String email;
	@Past(message = "dateOfBirth must be in the past.")
	@NotNull(message = "dateOfBirth must not be null.")
	private Date dateOfBirth;
	@NotNull(message = "gender must not be null.")
	@NotBlank(message = "gender must not be blank.")
	private String gender;
	@NotNull(message = "role must not be null.")
	@NotBlank(message = "role must not be blank.")
	private String role;
}
