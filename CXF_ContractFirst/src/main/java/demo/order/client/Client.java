package demo.order.client;

import demo.order.OrderProcess;
import demo.order.Order;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class Client {

	public Client() {
	}

	public static void main(String args[]) throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "demo/order/client/client-beans.xml" });
		OrderProcess client = (OrderProcess) context.getBean("orderClient");
		Order order = new Order();
		order.setCustomerID("C001");
		order.setItemID("I001");
		order.setQty(100);
		order.setPrice(200.00);

		String orderID = client.processOrder(order);
		String message = (orderID == null) ? "Order not approved"
				: "Order approved; order ID is " + orderID;
		System.out.println(message);

	}
}
