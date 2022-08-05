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
 * Represents the request DTO for {@code Customer}.
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
public class CustomerRequest {

	@NotNull(message = "customerName must not be null.")
	@NotBlank(message = "customerName must not be blank.")
	private String customerName;
	@NotNull(message = "contactPerson must not be null.")
	@NotBlank(message = "contactPerson must not be blank.")
	private String contactPerson;
	@NotNull(message = "phoneNumber must not be null.")
	private Long phoneNumber;
	@Email(message = "Please provide a valid email.")
	@NotNull(message = "email must not be null.")
	@NotBlank(message = "email must not be blank.")
	private String email;
	@Valid
	@NotNull(message = "addressRequest must not be null.")
	private AddressRequest addressRequest;
	@Valid
	@NotNull(message = "billingDetailsRequest must not be null.")
	private BillingDetailsRequest billingDetailsRequest;
}
