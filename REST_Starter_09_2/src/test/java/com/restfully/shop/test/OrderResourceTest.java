package com.restfully.shop.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Assert;
import org.junit.Test;

import com.restfully.shop.domain.Customer;
import com.restfully.shop.domain.LineItem;
import com.restfully.shop.domain.Link;
import com.restfully.shop.domain.Order;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class OrderResourceTest {
	protected Map<String, Link> processLinkHeaders(Response response) {
		System.out.println(response.getHeaders());
		List<Object> linkHeaders = (List<Object>) response.getHeaders().get(
				"Link");
		Map<String, Link> links = new HashMap<String, Link>();
		for (Object header : linkHeaders) {
			Link link = Link.valueOf(header.toString());
			links.put(link.getRelationship(), link);
			System.out.println("link:"+link.getRelationship()+" "+link);
		}
		return links;
	}

	@Test
	public void testCreateCancelPurge() throws Exception {
		String url = "http://localhost:9095/shop";
		WebClient request = WebClient.create(url);
		Response response = request.path(url).head();
		System.out.println("response.getHeaders() "+response.getHeaders());
		System.out.println("");
		Map<String, Link> shoppingLinks = processLinkHeaders(response);
		Link customers = shoppingLinks.get("customers");
		System.out.println("** Create a customer through this URL: "
				+ customers.getHref());

		Customer customer = new Customer();
		customer.setFirstName("Bill");
		customer.setLastName("Burke");
		customer.setStreet("10 Somewhere Street");
		customer.setCity("Westford");
		customer.setState("MA");
		customer.setZip("01711");
		customer.setCountry("USA");

		request = WebClient.create(customers.getHref());
		response = request.path(customers.getHref()).accept("application/xml")
				.post(customer);
		Assert.assertEquals(201, response.getStatus());

		Link orders = shoppingLinks.get("orders");
		Order order = new Order();
		order.setTotal("$199.99");
		order.setCustomer(customer);
		order.setDate(new Date().toString());
		
		LineItem item = new LineItem();
		item.setCost("$199.99");
		item.setProduct("iPhone");
		order.setLineItems(new ArrayList<LineItem>());
		order.getLineItems().add(item);

		System.out.println();
		System.out.println("** Create an order through this URL: "
				+ orders.getHref());
		request = WebClient.create(orders.getHref());
		response = request.path(orders.getHref()).accept("application/xml")
				.post(order);
		// request.body("application/xml", order);
		// response = request.post();
		Assert.assertEquals(201, response.getStatus());
		String createdOrderUrl = (String) response.getHeaders().getFirst(
				"Location");

				 System.out.println();
		 System.out.println("** New list of orders");
		 request = WebClient.create(orders.getHref());
		 response = request.path(orders.getHref()).get();
		 System.out.println(response.readEntity(String.class));
		 Map<String, Link> ordersLinks = processLinkHeaders(response);
		
		 request = WebClient.create(createdOrderUrl);
		 response = request.path(createdOrderUrl).head();
		 Map<String, Link> orderLinks = processLinkHeaders(response);
		
		 Link cancel = orderLinks.get("cancel");
		 if (cancel != null) {
		 System.out.println("** Canceling the order at URL: "
		 + cancel.getHref());
		 request = WebClient.create(cancel.getHref());
		 response = request.path(cancel.getHref()).post(null);
		 Assert.assertEquals(204, response.getStatus());
		 }
		
		 System.out.println();
		 System.out.println("** New list of orders after cancel: ");
		 request = WebClient.create(orders.getHref());
		 response = request.path(orders.getHref()).get();
		 System.out.println(response.readEntity(String.class));
		
		 System.out.println();
		 Link purge = ordersLinks.get("purge");
		 System.out.println("** Purge cancelled orders at URL: "
		 + purge.getHref());
		 request = WebClient.create(purge.getHref());
		 response = request.path(purge.getHref()).post(null);
		 Assert.assertEquals(204, response.getStatus());
		
		 System.out.println();
		 System.out.println("** New list of orders after purge: ");
		 request = WebClient.create(orders.getHref());
		 response = request.path(orders.getHref()).get();
		String s = (String) (response.readEntity(String.class));
		 System.out.println( s);
	}
}

