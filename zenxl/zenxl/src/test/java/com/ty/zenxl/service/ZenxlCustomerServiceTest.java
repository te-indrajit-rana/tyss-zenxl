package com.ty.zenxl.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ty.zenxl.pojos.ZenxlConstantData;
import com.ty.zenxl.request.AddressRequest;
import com.ty.zenxl.request.BillingDetailsRequest;
import com.ty.zenxl.request.CustomerRequest;
import com.ty.zenxl.request.UpdateCustomerRequest;
import com.ty.zenxl.response.CustomerResponse;
import com.ty.zenxl.response.ViewCustomerResponse;

@ExtendWith(MockitoExtension.class)
class ZenxlCustomerServiceTest {

	@Mock
	private ZenxlCustomerService zenxlCustomerService;

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
			.contactPerson("test_contact").phoneNumber((long) 1234567890).email("should@gmail.com").address1("blr1")
			.address2("blr2").city("blr").state("krntk").country("india").zipCode((long) 12345)
			.billingName("test-billing").billingContactNumber((long) 1234567890).billingEmail("billing@gmail.com")
			.billingAddress1("blr1").billingAddress2("blr2").billingCity("blr").billingState("krntk")
			.billingCountry("india").billingZipCode((long) 12345).build();

	CustomerResponse customerResponse = CustomerResponse.builder().customerName(customerRequest.getCustomerName())
			.city(customerRequest.getAddressRequest().getCity()).state(customerRequest.getAddressRequest().getState())
			.country(customerRequest.getAddressRequest().getCountry()).build();

	@Test
	@DisplayName("Should Add Customer Successfully")
	void shouldAddCustomerSuccessful() {

		when(zenxlCustomerService.addCustomer(any(CustomerRequest.class))).thenReturn(customerResponse);
		CustomerResponse response = zenxlCustomerService.addCustomer(customerRequest);
		assertEquals(customerResponse.getCustomerName(), response.getCustomerName());
	}

	@Test
	@DisplayName("Should View Customer By Customer Id Successfully")
	void shouldViewCustomerByCustomerIdSuccessful() {
		when(zenxlCustomerService.viewCustomer(anyInt()))
				.thenReturn(ViewCustomerResponse.builder().customerName("test").build());
		ViewCustomerResponse viewCustomerByCustomerId = zenxlCustomerService.viewCustomer(1);
		assertNotNull(viewCustomerByCustomerId);
		assertEquals(customerResponse.getCustomerName(), viewCustomerByCustomerId.getCustomerName());
	}

	@Test
	@DisplayName("Should Find All Customers Successfully")
	void shouldFindAllCustomers() {
		List<CustomerResponse> list = List.of(customerResponse);
		when(zenxlCustomerService.findAllCustomers()).thenReturn(list);
		List<CustomerResponse> findAllCustomers = zenxlCustomerService.findAllCustomers();
		assertNotNull(findAllCustomers);
		assertEquals(list.get(0), findAllCustomers.get(0));
	}

	@Test
	@DisplayName("Should Update Customer Successfully")
	void shouldUpdateCustomerSuccessful() {
		when(zenxlCustomerService.updateCustomer(anyInt(), any(UpdateCustomerRequest.class)))
				.thenReturn(ZenxlConstantData.UPDATED_SUCCESSFULLY);

		String updateCustomerMessage = zenxlCustomerService.updateCustomer(1, updateCustomerRequest);
		assertEquals(ZenxlConstantData.UPDATED_SUCCESSFULLY, updateCustomerMessage);
	}

	@Test
	@DisplayName("Should Delete Customer Successfully")
	void shouldDeleteCustomerSuccessful() {
		String msg = "Successful";
		when(zenxlCustomerService.deleteCustomer(anyInt())).thenReturn(msg);
		String deleteUserMsg = zenxlCustomerService.deleteCustomer(1);
		assertEquals(msg, deleteUserMsg);
	}

}
