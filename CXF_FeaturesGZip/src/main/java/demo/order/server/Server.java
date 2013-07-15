package demo.order.server;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.common.gzip.GZIPFeature;

import demo.order.OrderProcess;
import demo.order.OrderProcessImpl;

public class Server {

	public static void main(String[] args) throws Exception {

		OrderProcess orderProcess = new OrderProcessImpl();
		GZIPFeature gzip = new GZIPFeature();
		gzip.setThreshold(1);
		JaxWsServerFactoryBean server = new JaxWsServerFactoryBean();
		server.setServiceBean(orderProcess);
		server.setAddress("http://localhost:8080/feature/OrderProcessGZIP");
		server.getFeatures().add(gzip);
		server.create();
		System.out.println("Server ready....");

		Thread.sleep(5 * 60 * 1000);
        System.out.println("Server exiting");
        System.exit(0);
	}
}
