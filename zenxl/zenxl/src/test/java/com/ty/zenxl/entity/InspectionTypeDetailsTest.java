package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class InspectionTypeDetailsTest {

	ObjectMapper mapper = new ObjectMapper();
	
	InspectionTypeDetails inspectionType = InspectionTypeDetails.builder().inspectionTypeId(1).inspectionType("Test").build();
	String jsonInspectionType = "{\"inspectionTypeId\":1,\"inspectionType\":\"Test\"}";
	
	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {
		String writeValueAsString = mapper.writeValueAsString(inspectionType);
		JSONAssert.assertEquals(jsonInspectionType, writeValueAsString, false);
	}
	
	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		InspectionTypeDetails readValue = mapper.readValue(jsonInspectionType, InspectionTypeDetails.class);
		assertEquals(inspectionType.getInspectionType(), readValue.getInspectionType());
	}
}
