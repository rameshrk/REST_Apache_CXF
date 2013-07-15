package demo.order.broker;

import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.store.memory.MemoryPersistenceAdapter;

public final class MessageBroker {
    private MessageBroker() {
    }

    public static void main(String[] args) throws Exception {
        BrokerService broker = new BrokerService();
        broker.setPersistenceAdapter(new MemoryPersistenceAdapter());
        broker.addConnector("tcp://localhost:61616");
        broker.start();
        System.out.println("JMS broker ready ...");
//        Thread.sleep(125 * 60 * 1000);
//        System.out.println("JMS broker exiting");
//        broker.stop();
//        System.exit(0);
    }
}

