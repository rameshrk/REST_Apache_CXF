package com.restfully.shop.test;

import com.restfully.shop.domain.Customers;
import org.apache.cxf.jaxrs.client.WebClient;
//import org.jboss.resteasy.client.ClientRequest;
import org.junit.Test;


/**
 * @author <a href="mailto:bill@burkecentral.com">Bill Burke</a>
 * @version $Revision: 1 $
 */
public class CustomerResourceTest
{
   @Test
   public void testQueryCustomers() throws Exception
   {
      String url = "http://localhost:9095/customers";
      while (url != null)
      {
//         WebClient request = new WebClient(url);
//         String output = request.getTarget(String.class);
         System.out.println("** XML from " + url);
//         System.out.println(output);

//         Customers customers = request.getTarget(Customers.class);
//         url = customers.getNext();
      }
   }
}
