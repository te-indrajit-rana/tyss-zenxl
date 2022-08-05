package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class IncotermTypeDetailsTest {

	ObjectMapper mapper = new ObjectMapper();

	IncotermTypeDetails incotermType = IncotermTypeDetails.builder().incotermTypeId(1).incotermType("TEST").build();
	String jsonIncotermType = "{\"incotermTypeId\":1,\"incotermType\":\"TEST\"}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {
		String writeValueAsString = mapper.writeValueAsString(incotermType);
		JSONAssert.assertEquals(jsonIncotermType, writeValueAsString, false);
	}

	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		IncotermTypeDetails readValue = mapper.readValue(jsonIncotermType, IncotermTypeDetails.class);
		assertEquals(incotermType.getIncotermType(), readValue.getIncotermType());
	}
}
