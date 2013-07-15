/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package demo.order.client;


import demo.order.OrderProcess;
import demo.order.Order;

import org.apache.cxf.frontend.ClientProxy;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public final class Client {

    public Client() {
    }

    public static void main(String args[]) throws Exception {
        // START SNIPPET: client
        ClassPathXmlApplicationContext context 
            = new ClassPathXmlApplicationContext(new String[] {"demo/order/client/client-beans.xml"});

        OrderProcess client = (OrderProcess) context.getBean("orderClient");
        OrderProcessClientHandler clientInterceptor = new OrderProcessClientHandler();
        clientInterceptor.setUserName("John");
        clientInterceptor.setPassword("password");
        org.apache.cxf.endpoint.Client cxfClient = ClientProxy.getClient(client);
        cxfClient.getOutInterceptors().add(clientInterceptor); 

        
		Order order = new Order();
		order.setCustomerID("C001");
		order.setItemID("I001");
		order.setQty(100);
		order.setPrice(200.00);

        String orderID = client.processOrder(order);
        String message = (orderID == null) ? "Order not approved" : "Order approved; order ID is " + orderID;
		System.out.println(message);
        System.exit(0);
    }
}
