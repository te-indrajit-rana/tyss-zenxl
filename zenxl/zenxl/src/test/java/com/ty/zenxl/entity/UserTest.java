package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class UserTest {

	private ObjectMapper mapper = new ObjectMapper();

	User user = User.builder().userId(1).username("abc").email("abc@gmail.com")
			.gender("MALE").password("abc").role(Role.builder().roleId(1).roleName("TEST").build()).active(true).build();

	String jsonUser = "{\"userId\":1,\"username\":\"abc\",\"email\":\"abc@gmail.com\",\"gender\":\"MALE\",\"password\":\"abc\",\"active\":true,\"role\":{\"roleId\":1,\"roleName\":\"TEST\",\"user\":null}}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {
		String writeValueAsString = mapper.writeValueAsString(user);
		JSONAssert.assertEquals(jsonUser, writeValueAsString, false);
	}

	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		User readValue = mapper.readValue(jsonUser, User.class);
		assertEquals(user.getUsername(), readValue.getUsername());
	}

}
