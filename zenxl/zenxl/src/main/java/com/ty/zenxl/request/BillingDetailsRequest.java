package com.ty.zenxl.request;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code BillingDetails}.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlCustomerController}
 * 
 * @author Indrajit
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingDetailsRequest {

	@NotNull(message = "billingName must not be null.")
	@NotBlank(message = "billingName must not be blank.")
	private String billingName;
	@NotNull(message = "billingContactNumber must not be null.")
	private Long billingContactNumber;
	@Email(message = "Please provide a valid email.")
	@NotNull(message = "billingEmail must not be null.")
	@NotBlank(message = "billingEmail must not be blank.")
	private String billingEmail;
	@Valid
	@NotNull(message = "addressRequest must not be null.")
	private AddressRequest addressRequest;

}
