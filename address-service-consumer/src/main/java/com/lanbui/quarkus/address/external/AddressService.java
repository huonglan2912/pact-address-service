package com.lanbui.quarkus.address.external;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.lanbui.quarkus.address.model.Address;

@RegisterRestClient
@Path("/addresses")
public interface AddressService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	Response getAddresses();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	Response addAddress(Address address);
}
