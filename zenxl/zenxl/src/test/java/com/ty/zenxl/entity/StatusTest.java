package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class StatusTest {

	ObjectMapper mapper = new ObjectMapper();
	Status status = Status.builder().statusName("APPROVED").description("For Approval Of Items To Import").statusCategory("Import").isActive(true).build();
	String jsonStatus = "{\"statusName\":\"APPROVED\",\"description\":\"For Approval Of Items To Import\",\"statusCategory\":\"Import\",\"isActive\":true}";
	
	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {
		
		String writeValueAsString = mapper.writeValueAsString(status);
		
		JSONAssert.assertEquals(jsonStatus, writeValueAsString, false);
	}

	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		
		Status readValue = mapper.readValue(jsonStatus, Status.class);
		assertEquals(status.getStatusName(), readValue.getStatusName());
	}

}
