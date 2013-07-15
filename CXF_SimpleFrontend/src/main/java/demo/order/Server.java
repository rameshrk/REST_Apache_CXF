package demo.order;

import org.apache.cxf.frontend.ServerFactoryBean;

public class Server {

	public static void main(String[] arg) {
		// Create service implementation   
		OrderProcessImpl orderProcessImpl = new OrderProcessImpl();   
  
		// Create Server   
		ServerFactoryBean svrFactory = new ServerFactoryBean();   
		svrFactory.setServiceClass(OrderProcess.class);   
		svrFactory.setAddress("http://localhost:8080/SimpleOrderProcess");   
		svrFactory.setServiceBean(orderProcessImpl );   
		svrFactory.create();  
	}

}
