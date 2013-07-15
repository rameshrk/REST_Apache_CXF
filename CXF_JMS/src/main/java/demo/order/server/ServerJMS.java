package demo.order.server;

import java.lang.reflect.Method;

import javax.xml.ws.Endpoint;

import org.apache.cxf.jaxws.JaxWsServerFactoryBean;
import org.apache.cxf.transport.jms.spec.JMSSpecConstants;

import demo.order.OrderProcess;
import demo.order.OrderProcessImpl;


public final class ServerJMS {
    private static final String JMS_ENDPOINT_URI = "jms:queue:test.cxf.jmstransport.queue?timeToLive=1000"
                                  + "&jmsConnectionFactory=org.apache.activemq.ActiveMQConnectionFactory"
//                                  + "&jndiInitialContextFactory"
//                                  + "=org.apache.activemq.jndi.ActiveMQInitialContextFactory"
                                  ;

    private ServerJMS() {
        //
    }

    public static void main(String args[]) throws Exception {

        boolean launchAmqBroker = false;
        boolean jaxws = false;

        for (String arg : args) {
            if ("-activemqbroker".equals(arg)) {
                launchAmqBroker = true;
            } 
            if ("-jaxws".equals(arg)) {
                jaxws = true;
            } 
        }

        if (launchAmqBroker) {
            /*
             * The following make it easier to run this against something other than ActiveMQ. You will have
             * to get a JMS broker onto the right port of localhost.
             */
            Class<?> brokerClass = ServerJMS.class.getClassLoader()
                .loadClass("org.apache.activemq.broker.BrokerService");
            if (brokerClass == null) {
                System.err.println("ActiveMQ is not in the classpath, cannot launch broker.");
                return;
            }
            Object broker = brokerClass.newInstance();
            Method addConnectorMethod = brokerClass.getMethod("addConnector", String.class);
            addConnectorMethod.invoke(broker, "tcp://localhost:61616");
            Method setDataDirectory = brokerClass.getMethod("setDataDirectory", String.class);
            setDataDirectory.invoke(broker, "target/activemq-data");
            Method startMethod = brokerClass.getMethod("start");
            startMethod.invoke(broker);
        }

        if (jaxws) {
            launchJaxwsApi();
        } else {
            launchCxfApi();
        }

        System.out.println("Server ready... Press any key to exit");
        System.in.read();
        System.out.println("Server exiting");
        System.exit(0);
    }

    private static void launchCxfApi() {
        Object implementor = new demo.order.OrderProcessImpl();
        JaxWsServerFactoryBean svrFactory = new JaxWsServerFactoryBean();
        svrFactory.setServiceClass(OrderProcess.class);
        svrFactory.setTransportId(JMSSpecConstants.SOAP_JMS_SPECIFICATION_TRANSPORTID);
        svrFactory.setAddress(JMS_ENDPOINT_URI);
        svrFactory.setServiceBean(implementor);
        svrFactory.create();
    }

    private static void launchJaxwsApi() {
        Endpoint.publish(JMS_ENDPOINT_URI, new OrderProcessImpl());
    }
}