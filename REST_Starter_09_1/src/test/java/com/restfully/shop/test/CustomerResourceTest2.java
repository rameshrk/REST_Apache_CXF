package com.restfully.shop.test;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;

import com.restfully.shop.domain.Customers;

/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CustomerResourceTest2 {
	@Test
	public void testQueryCustomers() throws Exception {
		String url = "http://localhost:9095/customers";
		WebClient client = WebClient.create(url);
		// client.path("customers/1	");
		while (url != null) {
			System.out.println(url);
			Customers customers  = client.path(url).accept(
					"application/xml").get(Customers.class);
			System.out.println(customers.getCustomers());
			
			url = customers.getNext();
		}
	}
}
