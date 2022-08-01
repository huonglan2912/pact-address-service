package com.lanbui.quarkus.address.producer;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.lanbui.quarkus.address.model.Address;
import com.lanbui.quarkus.address.service.AddressService;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;

@PactFolder(value = "../address-service-consumer/target/pacts")
@Provider(value = "AddressProvider")
@QuarkusTest
@ExtendWith(value = MockitoExtension.class)
public class AddressServiceProducerPactTest {
	@InjectMock
	private AddressService addressService;
	
	@BeforeEach
	public void setUp(PactVerificationContext context) {
		List<Address> addresses = new ArrayList<>();
		Address address = new Address();
		address.setHouseNo("39B1");
		address.setStreet("Truong Son");
		addresses.add(address);
		Mockito.when(addressService.getAddresses()).thenReturn(addresses);

		Mockito.when(addressService.addAddress(Mockito.any())).thenReturn(addresses);
	}
	
	@TestTemplate
	@ExtendWith(value = PactVerificationInvocationContextProvider.class)
	void pactVeriificationTestTemplate(PactVerificationContext context) {
		context.verifyInteraction();
	}
	
	@State("all addresses")
	void getAllAddresses() {
	}
	
	@State("add address")
	void addAddress() {
	}
}
