package com.lanbui.quarkus.address.api;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.lanbui.quarkus.address.model.Address;
import com.lanbui.quarkus.address.service.AddressService;

@Path("/addresses")
public class AddressApi {
	AddressService service;
	
	@Inject
	public AddressApi(AddressService addressService) {
		this.service = addressService;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAddresses() {
		return Response.ok(service.getAddresses()).build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAddress(Address address) {
		List<Address> addAddress = service.addAddress(address);
		return Response.status(201).entity(addAddress).build();
	}
}
