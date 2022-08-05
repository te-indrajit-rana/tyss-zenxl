package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class RoleTest {

	private ObjectMapper mapper = new ObjectMapper();

	Role role = Role.builder().roleId(1).roleName("TEST").build();

	String jsonRole = "{\"roleId\":1,\"roleName\":\"TEST\",\"user\":null}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {

		String writeValueAsString = mapper.writeValueAsString(role);
		JSONAssert.assertEquals(jsonRole, writeValueAsString, false);
	}
	
	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		
		Role readValue = mapper.readValue(jsonRole, Role.class);
		assertEquals(role.getRoleName(), readValue.getRoleName());
	}

}
