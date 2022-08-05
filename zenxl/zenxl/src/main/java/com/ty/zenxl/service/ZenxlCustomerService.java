package com.ty.zenxl.service;

import static com.ty.zenxl.pojos.ZenxlConstantData.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ty.zenxl.entity.Address;
import com.ty.zenxl.entity.BillingDetails;
import com.ty.zenxl.entity.Customer;
import com.ty.zenxl.exception.CustomerException;
import com.ty.zenxl.repository.CustomerRepository;
import com.ty.zenxl.request.CustomerRequest;
import com.ty.zenxl.request.UpdateCustomerRequest;
import com.ty.zenxl.response.AddressResponse;
import com.ty.zenxl.response.BillingDetailsResponse;
import com.ty.zenxl.response.CustomerResponse;
import com.ty.zenxl.response.ViewCustomerResponse;

import lombok.RequiredArgsConstructor;

/**
 * Defines the mechanisms implemented for CRUD opeartions for a
 * {@code Customer}.
 *
 * @author Indrajit
 * @version 1.0
 */

@Service
@Transactional
@RequiredArgsConstructor
public class ZenxlCustomerService {

	private final CustomerRepository customerRepository;

	public CustomerResponse addCustomer(CustomerRequest request) {

		if (Boolean.TRUE.equals(customerRepository.existsByCustomerName(request.getCustomerName()))) {
			throw new CustomerException(CUSTOMER_ALREADY_EXISTS);
		}

		Address address = Address.builder().address1(request.getAddressRequest().getAddress1())
				.address2(request.getAddressRequest().getAddress2()).city(request.getAddressRequest().getCity())
				.state(request.getAddressRequest().getState()).zipCode(request.getAddressRequest().getZipCode())
				.country(request.getAddressRequest().getCountry()).build();

		BillingDetails billingDetails = BillingDetails.builder()
				.billingName(request.getBillingDetailsRequest().getBillingName())
				.billingContactNumber(request.getBillingDetailsRequest().getBillingContactNumber())
				.billingEmail(request.getBillingDetailsRequest().getBillingEmail())
				.address(
						Address.builder().address1(request.getBillingDetailsRequest().getAddressRequest().getAddress1())
								.address2(request.getBillingDetailsRequest().getAddressRequest().getAddress2())
								.city(request.getBillingDetailsRequest().getAddressRequest().getCity())
								.state(request.getBillingDetailsRequest().getAddressRequest().getState())
								.zipCode(request.getBillingDetailsRequest().getAddressRequest().getZipCode())
								.country(request.getBillingDetailsRequest().getAddressRequest().getCountry()).build())
				.build();

		Customer customer = Customer.builder().customerName(request.getCustomerName())
				.contactPerson(request.getContactPerson()).phoneNumber(request.getPhoneNumber())
				.email(request.getEmail()).address(address).billingDetails(billingDetails).build();

		Customer savedCustomer = customerRepository.save(customer);

		return CustomerResponse.builder().customerId(savedCustomer.getCustomerId())
				.customerName(savedCustomer.getCustomerName()).city(savedCustomer.getAddress().getCity())
				.state(savedCustomer.getAddress().getState()).country(savedCustomer.getAddress().getCountry()).build();
	}

	public List<CustomerResponse> findAllCustomers() {

		List<Customer> findAllCustomer = customerRepository.findAll();
		return findAllCustomer.stream().map(customer ->

		CustomerResponse.builder().customerId(customer.getCustomerId()).customerName(customer.getCustomerName())
				.city(customer.getAddress().getCity()).state(customer.getAddress().getState())
				.country(customer.getAddress().getCountry()).build()).collect(Collectors.toList());
	}

	public String updateCustomer(int customerId, UpdateCustomerRequest request) {

		Customer initialCustomerDetails = customerRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND_WITH_CUSTOMER_ID + customerId));

		Address updatedAddress = Address.builder().address1(request.getAddress1()).address2(request.getAddress2())
				.city(request.getCity()).state(request.getState()).zipCode(request.getZipCode())
				.country(request.getCountry()).build();

		BillingDetails updatedBillingDetails = BillingDetails.builder().billingName(request.getBillingName())
				.billingContactNumber(request.getBillingContactNumber()).billingEmail(request.getBillingEmail())
				.address(Address.builder().address1(request.getBillingAddress1()).address2(request.getBillingAddress2())
						.city(request.getBillingCity()).state(request.getBillingState())
						.zipCode(request.getBillingZipCode()).country(request.getBillingCountry()).build())
				.build();

		initialCustomerDetails.setCustomerName(request.getCustomerName());
		initialCustomerDetails.setContactPerson(request.getContactPerson());
		initialCustomerDetails.setPhoneNumber(request.getPhoneNumber());
		initialCustomerDetails.setEmail(request.getEmail());
		initialCustomerDetails.setAddress(updatedAddress);
		initialCustomerDetails.setBillingDetails(updatedBillingDetails);

		customerRepository.save(initialCustomerDetails);
		return UPDATED_SUCCESSFULLY;
	}

	public String deleteCustomer(int customerId) {
		Customer customer = customerRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND_WITH_CUSTOMER_ID + customerId));
		customerRepository.deleteCustomer(customer.getCustomerId());
		return DELETED_SUCCESSFULLY;
	}

	public ViewCustomerResponse viewCustomer(int customerId) {

		Customer customer = customerRepository.findByCustomerId(customerId)
				.orElseThrow(() -> new CustomerException(CUSTOMER_NOT_FOUND_WITH_CUSTOMER_ID + customerId));
		return ViewCustomerResponse.builder().customerName(customer.getCustomerName())
				.customerAddressResponse(AddressResponse.builder().address1(customer.getAddress().getAddress1())
						.address2(customer.getAddress().getAddress2()).city(customer.getAddress().getCity())
						.state(customer.getAddress().getState()).zipCode(customer.getAddress().getZipCode())
						.country(customer.getAddress().getCountry()).build())
				.contactPerson(customer.getContactPerson()).phoneNumber(customer.getPhoneNumber())
				.email(customer.getEmail())
				.billingDetailsResponse(BillingDetailsResponse.builder()
						.billingName(customer.getBillingDetails().getBillingName())
						.billingContactNumber(customer.getBillingDetails().getBillingContactNumber())
						.billingEmail(customer.getBillingDetails().getBillingEmail())
						.billingDetailsAddress(AddressResponse.builder()
								.address1(customer.getBillingDetails().getAddress().getAddress1())
								.address2(customer.getBillingDetails().getAddress().getAddress2())
								.city(customer.getBillingDetails().getAddress().getCity())
								.state(customer.getBillingDetails().getAddress().getState())
								.zipCode(customer.getBillingDetails().getAddress().getZipCode())
								.country(customer.getBillingDetails().getAddress().getCountry()).build())
						.build())
				.build();
	}
}
