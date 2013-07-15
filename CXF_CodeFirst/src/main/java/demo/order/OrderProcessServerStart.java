package demo.order;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;

public class OrderProcessServerStart {

	public static void main(String[] args) {

		// Service instance

		OrderProcess orderProcess = new OrderProcessImpl();
		//AddressVerifyProcess orderProcess = new AddressVerifyProcessImpl();
		
		JaxWsServerFactoryBean restServer = new JaxWsServerFactoryBean();

		restServer.setServiceBean(orderProcess);

		restServer.setAddress("http://localhost:8080/");

		restServer.create();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		try {
			br.readLine();
		} catch (IOException e) {

		}
		System.out.println("Server Stopped");
		System.exit(0);

	}

}
