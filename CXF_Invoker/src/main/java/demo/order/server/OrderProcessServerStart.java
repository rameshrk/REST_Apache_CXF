package demo.order.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

import demo.order.OrderProcessImpl;
import demo.order.OrderProcess;

public class OrderProcessServerStart {

	public static void main(String[] args) throws Exception {

		// Service instance
		OrderProcess orderProcess = new OrderProcessImpl();
		JaxWsServerFactoryBean jaxServer = new JaxWsServerFactoryBean();
		jaxServer.setServiceBean(orderProcess);
		jaxServer.setAddress("http://localhost:8080/OrderProcess");
		
		// Set the Invoker
		jaxServer.setInvoker(new AuthenticationInvoker(orderProcess));
		jaxServer.create();
        System.out.println("Server ready...");
		Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
	}
}
