package demo.order.client;

import org.apache.cxf.frontend.ClientProxyFactoryBean;

import demo.order.Order;
import demo.order.OrderProcess;

	public class SimpleClient {
		public static void main(String[] args) {
			ClientProxyFactoryBean factory = new ClientProxyFactoryBean();
			factory.setServiceClass(OrderProcess.class);
			factory.setAddress("http://localhost:8080/SimpleOrderProcess");
			OrderProcess client = (OrderProcess) factory.create(); 
			
			Order order = new Order();
			order.setCustomerID("C001");
			order.setItemID("I001");
			order.setPrice(100.00);
			order.setQty(20);

	        String result = client.processOrder(order);
	        System.out.println("The order ID is " + result);


		}
	

    
}
