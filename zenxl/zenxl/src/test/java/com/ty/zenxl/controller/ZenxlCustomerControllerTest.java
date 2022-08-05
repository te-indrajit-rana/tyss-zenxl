package com.ty.zenxl.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.ty.zenxl.pojos.JwtUtils;
import com.ty.zenxl.request.AddressRequest;
import com.ty.zenxl.request.BillingDetailsRequest;
import com.ty.zenxl.request.CustomerRequest;
import com.ty.zenxl.request.UpdateCustomerRequest;
import com.ty.zenxl.response.CustomerResponse;
import com.ty.zenxl.response.ViewCustomerResponse;
import com.ty.zenxl.response.ZenxlResponseBody;
import com.ty.zenxl.service.ZenxlCustomUserDetailsService;

@WebMvcTest(controllers = ZenxlCustomerController.class)
class ZenxlCustomerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ZenxlCustomerController zenxlCustomerController;

	@MockBean
	private ZenxlCustomUserDetailsService customUserDetailsService;

	@MockBean
	private PasswordEncoder encoder;

	@MockBean
	private JwtUtils jwtUtils;

	CustomerRequest customerRequest = CustomerRequest.builder().customerName("test").contactPerson("test_contact")
			.phoneNumber((long) 1234567890).email("test@gmail.com")
			.addressRequest(AddressRequest.builder().address1("blr1").address2("blr2").city("blr").state("krntk")
					.country("india").zipCode((long) 12345).build())
			.billingDetailsRequest(BillingDetailsRequest
					.builder().billingName("test-billing").billingContactNumber((long) 1234567890)
					.billingEmail("billing@gmail.com").addressRequest(AddressRequest.builder().address1("blr1")
							.address2("blr2").city("blr").state("krntk").country("india").zipCode((long) 12345).build())
					.build())
			.build();

	UpdateCustomerRequest updateCustomerRequest = UpdateCustomerRequest.builder().customerName("test")
			.contactPerson("test_contact").phoneNumber((long) 1234567890).email("test@gmail.com").address1("blr1")
			.address2("blr2").city("blr").state("krntk").country("india").zipCode((long) 12345)
			.billingName("test-billing").billingContactNumber((long) 1234567890).billingEmail("billing@gmail.com")
			.billingAddress1("blr1").billingAddress2("blr2").billingCity("blr").billingState("krntk")
			.billingCountry("india").billingZipCode((long) 12345).build();

	String jsonCustomerRequest = "{\"customerName\":\"test\",\"contactPerson\":\"test_contact\",\"phoneNumber\":1234567890,\"email\":\"test@gmail.com\",\"addressRequest\":{\"address1\":\"blr1\",\"address2\":\"blr2\",\"city\":\"blr\",\"state\":\"krntk\",\"zipCode\":12345,\"country\":\"india\"},\"billingDetailsRequest\":{\"billingName\":\"test-billing\",\"billingContactNumber\":1234567890,\"billingEmail\":\"billing@gmail.com\",\"addressRequest\":{\"address1\":\"blr1\",\"address2\":\"blr2\",\"city\":\"blr\",\"state\":\"krntk\",\"zipCode\":12345,\"country\":\"india\"}}}";

	String jsonUpdateCustomerRequest = "{\"customerName\":\"test\",\"contactPerson\":\"test_contact\",\"phoneNumber\":1234567890,\"email\":\"test@gmail.com\",\"address1\":\"blr1\",\"address2\":\"blr2\",\"city\":\"blr\",\"state\":\"krntk\",\"zipCode\":12345,\"country\":\"india\",\"billingName\":\"test-billing\",\"billingContactNumber\":1234567890,\"billingEmail\":\"billing@gmail.com\",\"billingAddress1\":\"blr1\",\"billingAddress2\":\"blr2\",\"billingCity\":\"blr\",\"billingState\":\"krntk\",\"billingZipCode\":12345,\"billingCountry\":\"india\"}";

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Add Customer To Admin Only")
	void shouldAllowAddCustomerToAdminOnly() throws Exception {

		when(zenxlCustomerController.addCustomer(any(CustomerRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.CREATED));

		MvcResult result = mockMvc
				.perform(post("/api/v1/zenxl/customer/add-customer").accept(MediaType.APPLICATION_JSON_VALUE)
						.content(jsonCustomerRequest).contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isCreated()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow View Customer To Admin Only")
	void shouldAllowViewCustomerToAdminOnly() throws Exception {

		when(zenxlCustomerController.viewCustomer(anyInt()))
				.thenReturn(new ResponseEntity<ViewCustomerResponse>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(get("/api/v1/zenxl/customer/view-customer").header("customerId", 1)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Find All Customers To Admin Only")
	void shouldAllowFindAllCustomersToAdminOnly() throws Exception {

		when(zenxlCustomerController.findAllCustomers())
				.thenReturn(new ResponseEntity<List<CustomerResponse>>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(get("/api/v1/zenxl/customer/find-all-customers").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Update Customer To Admin Only")
	void shouldAllowUpdateCustomerToAdminOnly() throws Exception {

		when(zenxlCustomerController.updateCustomer(anyInt(), any(UpdateCustomerRequest.class)))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc
				.perform(put("/api/v1/zenxl/customer/update-customer").header("customerId", 1)
						.accept(MediaType.APPLICATION_JSON_VALUE).content(jsonUpdateCustomerRequest)
						.contentType(MediaType.APPLICATION_JSON_VALUE).with(csrf()))
				.andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

	@Test
	@WithMockUser(roles = "ADMIN")
	@DisplayName("Should Allow Delete Customer To Admin Only")
	void shouldAllowDeleteCustomerToAdminOnly() throws Exception {

		when(zenxlCustomerController.deleteCustomer(anyInt()))
				.thenReturn(new ResponseEntity<ZenxlResponseBody>(HttpStatus.OK));

		MvcResult result = mockMvc.perform(delete("/api/v1/zenxl/customer/delete-customer").header("customerId", 1)
				.accept(MediaType.APPLICATION_JSON_VALUE).with(csrf())).andExpect(status().isOk()).andReturn();

		assertThat(result.getResponse()).isNotNull();
	}

}
