package demo.order.server;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBusFactory;
import javax.xml.ws.Endpoint;

import demo.order.OrderProcessImpl;

public class Server {
	public Server() {
		SpringBusFactory factory = new SpringBusFactory();
		Bus bus = factory.createBus("demo/order/server/server-bean.xml");
		factory.setDefaultBus(bus);
		OrderProcessImpl orderProcessImpl = new OrderProcessImpl();
		Endpoint.publish("https://localhost:9001/OrderProcessSSL", orderProcessImpl);
	}

	public static void main(String[] args) 	{
		new Server();
		System.out.println("Server ready ...");
	}
}
