package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class CustomerTest {

	ObjectMapper mapper = new ObjectMapper();

	Customer customer = Customer.builder().customerId(0).customerName("test").contactPerson("test_contact").phoneNumber((long) 1234567890)
			.email("test@gmail.com")
			.address(
					Address.builder().addressId(0).address1("blr1").address2("blr2").city("blr").state("krntk").country("india")
							.zipCode((long) 12345).build())
			.billingDetails(BillingDetails.builder().billingDetailsId(0).billingName("test-billing").billingContactNumber((long) 1234567890)
					.billingEmail("billing@gmail.com").address(Address.builder().addressId(0).address1("blr1").address2("blr2")
							.city("blr").state("krntk").country("india").zipCode((long) 12345).build())
					.build())
			.build();

	String jsonCustomer = "{\"customerId\":0,\"customerName\":\"test\",\"address\":{\"addressId\":0,\"address1\":\"blr1\",\"address2\":\"blr2\",\"city\":\"blr\",\"state\":\"krntk\",\"zipCode\":12345,\"country\":\"india\",\"customer\":null,\"billingDetails\":null},\"contactPerson\":\"test_contact\",\"phoneNumber\":1234567890,\"email\":\"test@gmail.com\",\"billingDetails\":{\"billingDetailsId\":0,\"billingName\":\"test-billing\",\"billingContactNumber\":1234567890,\"billingEmail\":\"billing@gmail.com\",\"customer\":null,\"address\":{\"addressId\":0,\"address1\":\"blr1\",\"address2\":\"blr2\",\"city\":\"blr\",\"state\":\"krntk\",\"zipCode\":12345,\"country\":\"india\",\"customer\":null,\"billingDetails\":null}}}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {
		String writeValueAsString = mapper.writeValueAsString(customer);
		JSONAssert.assertEquals(jsonCustomer, writeValueAsString, false);
	}

	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		Customer readValue = mapper.readValue(jsonCustomer, Customer.class);
		assertEquals(customer.getCustomerName(), readValue.getCustomerName());
	}

}
