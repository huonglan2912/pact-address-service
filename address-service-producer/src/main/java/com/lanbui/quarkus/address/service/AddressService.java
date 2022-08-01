package com.lanbui.quarkus.address.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;

import com.lanbui.quarkus.address.model.Address;

@ApplicationScoped
public class AddressService {
	private static List<Address> addresses = new ArrayList<>();
	
	public List<Address> getAddresses() {
		return addresses;
	}
	
	public List<Address> addAddress(Address address) {
		addresses.add(address);
		return addresses;
	}
}
