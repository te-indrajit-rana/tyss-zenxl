package com.ty.zenxl.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code Address}.
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
public class AddressRequest {

	@NotNull(message = "address1 must not be null.")
	@NotBlank(message = "address1 must not be blank.")
	private String address1;
	@NotNull(message = "address2 must not be null.")
	@NotBlank(message = "address2 must not be blank.")
	private String address2;
	@NotNull(message = "city name must not be null.")
	@NotBlank(message = "city name must not be blank.")
	private String city;
	@NotNull(message = "state name must not be null.")
	@NotBlank(message = "state name must not be blank.")
	private String state;
	@NotNull(message = "zipCode must not be null.")
	private Long zipCode;
	@NotNull(message = "country name must not be null.")
	@NotBlank(message = "country name must not be blank.")
	private String country;
}
