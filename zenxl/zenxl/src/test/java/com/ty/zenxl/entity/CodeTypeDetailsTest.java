package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class CodeTypeDetailsTest {

	ObjectMapper mapper = new ObjectMapper();
	CodeTypeDetails codeType = CodeTypeDetails.builder().codeTypeId(1).codeType("Test").build();
	String jsonCodeType = "{\"codeTypeId\":1,\"codeType\":\"Test\"}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {
		String writeValueAsString = mapper.writeValueAsString(codeType);
		JSONAssert.assertEquals(jsonCodeType, writeValueAsString, false);
	}

	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		CodeTypeDetails readValue = mapper.readValue(jsonCodeType, CodeTypeDetails.class);
		assertEquals(codeType.getCodeType(), readValue.getCodeType());
	}
}
