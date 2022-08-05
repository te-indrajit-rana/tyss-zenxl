package com.ty.zenxl.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code PasscodeDetails}.
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
public class ChangePasswordRequest {

	@NotNull(message = "passcode must not be null.")
	private Integer passcode;
	@NotNull(message = "password cannot be null")
	@NotBlank(message = "password cannot be blank")
	@Size(min = 4, max = 16, message = "password must be within 4 to 16 characters.")
	private String password;
	@NotNull(message = "confirmPassword cannot be null")
	@NotBlank(message = "confirmPassword cannot be blank")
	@Size(min = 4, max = 16, message = "confirmPassword must be within 4 to 16 characters.")
	private String confirmPassword;
}
