package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class HsCodeTest {

	ObjectMapper mapper = new ObjectMapper();
	HsCode hsCode = HsCode.builder().hsCodeId(1).hsCodeType("Test").build();
	String jsonHsCode = "{\"hsCodeId\":1,\"hsCodeType\":\"Test\"}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {
		String writeValueAsString = mapper.writeValueAsString(hsCode);
		JSONAssert.assertEquals(jsonHsCode, writeValueAsString, false);
	}

	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		HsCode readValue = mapper.readValue(jsonHsCode, HsCode.class);
		assertEquals(hsCode.getHsCodeType(), readValue.getHsCodeType());
	}
}
