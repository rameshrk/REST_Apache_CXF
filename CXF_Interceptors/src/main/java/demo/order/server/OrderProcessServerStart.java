package demo.order.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import demo.order.OrderProcess;
import demo.order.OrderProcessImpl;

public class OrderProcessServerStart {

	public static void main(String[] args) throws Exception {

		OrderProcess orderProcess = new OrderProcessImpl();
		JaxWsServerFactoryBean server = new JaxWsServerFactoryBean();
		server.setServiceBean(orderProcess);
		server.setAddress("http://localhost:8080/OrderProcess");
		server.create();
		System.out.println("Server ready....");

		Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
	}
}
