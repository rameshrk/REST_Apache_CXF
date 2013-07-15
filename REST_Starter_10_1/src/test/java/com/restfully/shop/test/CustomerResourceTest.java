package com.restfully.shop.test;

import javax.ws.rs.core.Response;

import com.restfully.shop.domain.Customer;

import org.apache.cxf.jaxrs.client.WebClient;
//import org.jboss.resteasy.client.ClientRequest;
//import org.jboss.resteasy.client.ClientResponse;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CustomerResourceTest {
	@Test
	public void testCustomerResource() throws Exception {
		// RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
		String url = "http://localhost:9095/customers/1";
		WebClient request = WebClient.create(url);
		// ClientRequest request = new ClientRequest("");
		Response response = request.path(url).get();
		Assert.assertEquals(200, response.getStatus());
		Customer cust = response.readEntity(Customer.class);
		// ClientResponse<Customer> response = request.get(Customer.class);
		// Customer cust = response.getEntity();

		String etag = (String) response.getHeaders().getFirst("ETag");
		System.out.println("Doing a conditional GET with ETag: " + etag);
		request.reset();
		// request.clear();
		request = WebClient.create(url);
		request.header("If-None-Match", etag);
		response = request.path(url).accept("application/xml").get();
//		System.out.println(response.readEntity(Customer.class));
		Assert.assertEquals(304, response.getStatus());

		// Update and send a bad etag with conditional PUT
		cust.setCity("Bedford");
		request.reset();
		// request.clear();
		request.header("If-Match", "JUNK");
		// request.body("application/xml", cust);
		Response response2 = request.accept("application/xml").put(cust);
		Assert.assertEquals(412, response2.getStatus());
	}
}
