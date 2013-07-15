package demo.order.provider;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.dom.DOMSource;
import javax.xml.ws.Provider;
import javax.xml.ws.Service;
import javax.xml.ws.ServiceMode;
import javax.xml.ws.WebServiceProvider;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@WebServiceProvider()
@ServiceMode(value = Service.Mode.MESSAGE)            
public class OrderProcessDOMProvider implements Provider<DOMSource> {

    public OrderProcessDOMProvider() {
     }

    public DOMSource invoke(DOMSource request) {
        DOMSource response = new DOMSource();
        try {
            MessageFactory factory = MessageFactory.newInstance();
            SOAPMessage soapReq = factory.createMessage();
            soapReq.getSOAPPart().setContent(request);
    
            System.out.println("Incoming Client Request as a DOMSource data in MESSAGE Mode");
            soapReq.writeTo(System.out);
            System.out.println("\n");
            
            Node processOrderNode = soapReq.getSOAPBody().getFirstChild();
            System.out.println("processOrderNode");
            System.out.println(processOrderNode.getNodeName()+" "+processOrderNode.getNodeValue());
            //Get arg0 - order element
            Node order = processOrderNode.getChildNodes().item(0);
            
            //Get list of child nodes associated with order and print it
            NodeList list = order.getChildNodes();
            for(int i = 0 ; i<list.getLength() ; i++){
            	//Get the child nodes and value as per the order xml request.
            	System.out.println(list.item(i).getNodeName() + "=" + 
            			list.item(i).getFirstChild().getNodeValue());
            }
            
            
           
            SOAPMessage orderResponse =  factory.createMessage();
            QName processOrderQName = new QName("http://order.demo/", "processOrder");
            QName responseQName = new QName("http://order.demo/", "return");
            //create the element - <http://order.demo/:processOrder></http://order.demo/:processOrder>
            SOAPElement processOrderResponse = orderResponse.getSOAPBody().addChildElement(processOrderQName);
            //create the element inside processOrder - <http://order.demo/:return>ORD1234</http://order.demo/:return>
            processOrderResponse.addChildElement(responseQName).addTextNode("ORD1234");
            
            response.setNode(orderResponse.getSOAPPart());
			
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }
}
