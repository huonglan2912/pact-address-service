package com.lanbui.quarkus.address.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.lanbui.quarkus.address.external.AddressService;
import com.lanbui.quarkus.address.model.Address;

import au.com.dius.pact.consumer.MockServer;
import au.com.dius.pact.consumer.dsl.DslPart;
import au.com.dius.pact.consumer.dsl.LambdaDsl;
import au.com.dius.pact.consumer.dsl.PactDslWithProvider;
import au.com.dius.pact.consumer.junit5.PactConsumerTestExt;
import au.com.dius.pact.consumer.junit5.PactTestFor;
import au.com.dius.pact.core.model.RequestResponsePact;
import au.com.dius.pact.core.model.annotations.Pact;
import io.quarkus.test.junit.QuarkusTest;

@QuarkusTest
@ExtendWith(PactConsumerTestExt.class)
public class AddressServiceConsumerPactTest {
	
	@Inject
	@RestClient
	AddressService addressService;

	@Pact(provider = "AddressProvider", consumer = "AddressConsumer")
	public RequestResponsePact getAddresses(PactDslWithProvider builder) {
		DslPart body = LambdaDsl.newJsonArray(array -> {
			array.object(address -> {
				address.stringType("houseNo", "39B");
				address.stringType("street", "Truong Son");
			});
		}).build();
		return builder.given("all addresses")
				.uponReceiving("get all addresses")
				.path("/addresses")
				.method("GET")
				.willRespondWith()
				.status(200)
				.body(body)
				.toPact();
	}
	
	@Pact(provider = "AddressProvider", consumer = "AddressConsumer")
	public RequestResponsePact addAddress(PactDslWithProvider builder) {
		Map<String, String> header = new HashMap<>();
		header.put("Content-Type", MediaType.APPLICATION_JSON);
		DslPart requestBody = LambdaDsl.newJsonBody(address -> {
			address.stringType("houseNo", "7");
			address.stringType("street", "Am Rank");
		}).build();
		DslPart responseBody = LambdaDsl.newJsonArray(array -> {
			array.object(address -> {
				address.stringType("houseNo", "7");
				address.stringType("street", "Am Rank");
			});
		}).build();
		return builder.given("add address")
				.uponReceiving("add an address")
				.path("/addresses")
				.method("POST")
				.headers(header)
				.body(requestBody)
				.willRespondWith()
				.status(201)
				.headers(header)
				.body(responseBody)
				.toPact();
	}
	
	@Test
	@PactTestFor(pactMethod = "getAddresses", port = "8080")
	public void getAddresses_whenRunSucessful_thenReturnCorrectData(MockServer mockServer) {
		List<Address> expectedAddresses = new ArrayList<>();
		Address expectedAddress = new Address();
		expectedAddress.setHouseNo("39B");
		expectedAddress.setStreet("Truong Son");
		expectedAddresses.add(expectedAddress);
		
		Response response = addressService.getAddresses();
		List<Address> addresses = response.readEntity(new GenericType<List<Address>>() {});
		
		Assertions.assertEquals(200, response.getStatus());
		Assertions.assertEquals(1, addresses.size());
		Assertions.assertEquals(expectedAddress, addresses.get(0));
		
	}
	
	@Test
	@PactTestFor(pactMethod = "addAddress", port = "8080")
	public void addAddress_whenRunSucessful_thenReturnCorrectData(MockServer mockServer) {
		List<Address> expectedAddresses = new ArrayList<>();
		Address expectedAddress = new Address();
		expectedAddress.setHouseNo("7");
		expectedAddress.setStreet("Am Rank");
		expectedAddresses.add(expectedAddress);
		
		Response response = addressService.addAddress(expectedAddress);
		List<Address> addresses = response.readEntity(new GenericType<List<Address>>() {});
		
		Assertions.assertEquals(201, response.getStatus());
		Assertions.assertEquals(1, addresses.size());
		Assertions.assertEquals(expectedAddress, addresses.get(0));
		
	}
}
