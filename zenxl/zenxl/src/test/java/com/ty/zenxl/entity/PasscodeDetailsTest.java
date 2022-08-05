package com.ty.zenxl.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

class PasscodeDetailsTest {

	ObjectMapper mapper = new ObjectMapper();

	PasscodeDetails passcode = PasscodeDetails.builder().id(1).email("test@gmail.com").passcode(123456)
			.build();

	String jsonPasscode = "{\"id\":1,\"passcode\":123456,\"email\":\"test@gmail.com\"}";

	@Test
	@DisplayName("Should Successfully Serialize")
	void shouldSuccessfullySerialize() throws JsonProcessingException, JSONException {

		String writeValueAsString = mapper.writeValueAsString(passcode);
		JSONAssert.assertEquals(jsonPasscode, writeValueAsString, false);
	}
	
	@Test
	@DisplayName("Should Successfully Deserialize")
	void shouldSuccessfullyDeserialize() throws JsonMappingException, JsonProcessingException {
		PasscodeDetails readPasscodeValue = mapper.readValue(jsonPasscode, PasscodeDetails.class);
		assertEquals(passcode.getPasscode(), readPasscodeValue.getPasscode());
	}
}
