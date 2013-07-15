package demo.order.client;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import demo.order.Order;
import demo.order.OrderProcess;

public class Client {

    private static final QName SERVICE_NAME = new QName("http://order.demo/", "OrderProcessService");
    private static final QName PORT_NAME = new QName("http://order.demo/", "OrderProcessPort");

    private static final String WSDL_LOCATION = "http://localhost:8080/OrderProcess?wsdl";

    public static void main(String args[]) throws Exception {
        URL wsdlURL = new URL(WSDL_LOCATION);
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        OrderProcess port = service.getPort(PORT_NAME, OrderProcess.class);  

		Order order = new Order();
		order.setCustomerID("C001");
		order.setItemID("I001");
		order.setPrice(100.00);
		order.setQty(20);

        String result = port.processOrder(order);
        System.out.println("The order ID is " + result);
    	
    }
    
}
