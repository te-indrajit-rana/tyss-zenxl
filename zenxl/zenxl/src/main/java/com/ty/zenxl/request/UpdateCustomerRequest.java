package com.ty.zenxl.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code Customer} for update purpose.
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
public class UpdateCustomerRequest {

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

	@NotNull(message = "address1 must not be null.")
	@NotBlank(message = "address1 must not be blank.")
	private String address1;
	@NotNull(message = "address2 must not be null.")
	@NotBlank(message = "address2 must not be blank.")
	private String address2;
	@NotNull(message = "city must not be null.")
	@NotBlank(message = "city must not be blank.")
	private String city;
	@NotNull(message = "state must not be null.")
	@NotBlank(message = "state must not be blank.")
	private String state;
	@NotNull(message = "zipCode must not be null.")
	private Long zipCode;
	@NotNull(message = "country must not be null.")
	@NotBlank(message = "country must not be blank.")
	private String country;

	@NotNull(message = "billingName must not be null.")
	@NotBlank(message = "billingName must not be blank.")
	private String billingName;
	@NotNull(message = "billingContactNumber must not be null.")
	private Long billingContactNumber;
	@Email(message = "Please provide a valid email.")
	@NotNull(message = "billingEmail must not be null.")
	@NotBlank(message = "billingEmail must not be blank.")
	private String billingEmail;

	@NotNull(message = "billingAddress1 must not be null.")
	@NotBlank(message = "billingAddress1 must not be blank.")
	private String billingAddress1;
	@NotNull(message = "billingAddress2 must not be null.")
	@NotBlank(message = "billingAddress2 must not be blank.")
	private String billingAddress2;
	@NotNull(message = "billingCity must not be null.")
	@NotBlank(message = "billingCity must not be blank.")
	private String billingCity;
	@NotNull(message = "billingState must not be null.")
	@NotBlank(message = "billingState must not be blank.")
	private String billingState;
	@NotNull(message = "billingZipCode must not be null.")
	private Long billingZipCode;
	@NotNull(message = "billingCountry must not be null.")
	@NotBlank(message = "billingCountry must not be blank.")
	private String billingCountry;
}
