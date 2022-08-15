package com.roms.authservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.roms.authservice.payload.LoginRequest;

public class AuthControllerTests extends AuthServiceApplicationTests {

	@Override
	@Before
	public void setUp() {
		super.setUp();
	}

	@Test
	public void loginTest() throws Exception {
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

		String uri = "/auth/signin";
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setUsername("User1");
		loginRequest.setPassword("User@123");
		String inputJson = super.mapToJson(loginRequest);
		System.out.println(inputJson);
		MvcResult mvcResult = mvc.perform(
				MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

}
