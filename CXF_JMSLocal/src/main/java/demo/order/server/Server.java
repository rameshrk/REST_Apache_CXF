package demo.order.server;

import javax.xml.ws.Endpoint;

import demo.order.OrderProcessImpl;

public class Server {

	public Server() throws Exception {
		OrderProcessImpl orderProcessImpl = new OrderProcessImpl();
//		String address = "http://localhost:8080/OrderProcess";
		String address = "local://OrderProcess";
        Endpoint.publish(address, orderProcessImpl);
	}

	public static void main(String args[]) throws Exception {
		new Server();
		System.out.println("Server ready...");

//		Thread.sleep(100000000);
//		System.out.println("Server exiting");
//		System.exit(0);
	}
}