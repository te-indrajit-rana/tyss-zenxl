package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class AddressTest {
	
	ObjectMapper mapper = new ObjectMapper();
	Address address = Address.builder().addressId(0).address1("blr1").address2("blr2").city("blr").state("krntk").country("india")
			.zipCode((long) 12345).build();
	String jsonAddress = "{\"addressId\":0,\"address1\":\"blr1\",\"address2\":\"blr2\",\"city\":\"blr\",\"state\":\"krntk\",\"zipCode\":12345,\"country\":\"india\",\"customer\":null,\"billingDetails\":null}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {

		String writeValueAsString = mapper.writeValueAsString(address);
		JSONAssert.assertEquals(jsonAddress, writeValueAsString, false);
	}

	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		
		Address readValue = mapper.readValue(jsonAddress, Address.class);
		assertEquals(address.getAddress1(), readValue.getAddress1());
	}

}
