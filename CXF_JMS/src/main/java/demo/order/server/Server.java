
package demo.order.server;

import demo.order.OrderProcessImpl;
import javax.xml.ws.Endpoint;

public class Server {

    protected Server() throws Exception {
        System.out.println("Starting Server");
        Object implementor = new OrderProcessImpl();
        String address = "http://demo.order/jms/OrderProcess";
        Endpoint.publish(address, implementor);
    }

    public static void main(String args[]) throws Exception {
        new Server();
        System.out.println("Server ready...");

    }
}
