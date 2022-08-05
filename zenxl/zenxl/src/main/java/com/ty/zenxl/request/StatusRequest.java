package com.ty.zenxl.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the request DTO for {@code Status}.
 * 
 * Contains bean validation properties, to validate the request object using
 * {@code @Valid} in {@code ZenxlUtilityController}
 * 
 * @author Indrajit
 * @version 1.0
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatusRequest {

	@NotNull(message = "statusName must not be null.")
	@NotBlank(message = "statusName must not be blank.")
	private String statusName;
	@NotNull(message = "statusCategory must not be null.")
	@NotBlank(message = "statusCategory must not be blank.")
	private String statusCategory;
	@NotNull(message = "description must not be null.")
	@NotBlank(message = "description must not be blank.")
	private String description;

}
