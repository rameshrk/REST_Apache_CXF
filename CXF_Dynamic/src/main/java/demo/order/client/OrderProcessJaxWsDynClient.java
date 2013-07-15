package demo.order.client;

import java.beans.PropertyDescriptor;
import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.endpoint.Endpoint;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.service.model.BindingInfo;
import org.apache.cxf.service.model.BindingMessageInfo;
import org.apache.cxf.service.model.BindingOperationInfo;
import org.apache.cxf.service.model.MessagePartInfo;
import org.apache.cxf.service.model.ServiceInfo;

public class OrderProcessJaxWsDynClient {
	public OrderProcessJaxWsDynClient() {
	}

	public static void main(String str[]) throws Exception {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient("http://localhost:8080/OrderProcess?wsdl");
	    Endpoint endpoint = client.getEndpoint();
	    

	   // Make use of CXF service model to introspect the existing WSDL
	    ServiceInfo serviceInfo = endpoint.getService().getServiceInfos().get(0);
	    QName bindingName = new QName("http://order.demo/", "OrderProcessServiceSoapBinding");
	    BindingInfo binding = serviceInfo.getBinding(bindingName);
	    QName opName = new QName("http://order.demo/", "processOrder");
	    BindingOperationInfo boi = binding.getOperation(opName); // Operation name is processOrder
	    BindingMessageInfo inputMessageInfo = null;
	    if(!boi.isUnwrapped()){
	    	//OrderProcess uses document literal wrapped style.
	    	inputMessageInfo = boi.getWrappedOperation().getInput();
	    }else {
	    	inputMessageInfo = boi.getUnwrappedOperation().getInput();
	    }
	    
	    List<MessagePartInfo> parts = inputMessageInfo.getMessageParts();
	    MessagePartInfo partInfo = parts.get(0); // Input class is Order

		// Get the input class Order
		Class<?> orderClass = partInfo.getTypeClass(); 
		Object orderObject = orderClass.newInstance();
	
		// Populate the Order bean
		// Set customer ID, item ID, price and quantity
		PropertyDescriptor custProperty = new PropertyDescriptor("customerID", orderClass);
        custProperty.getWriteMethod().invoke(orderObject, "C001");
        PropertyDescriptor itemProperty = new PropertyDescriptor("itemID", orderClass);
        itemProperty.getWriteMethod().invoke(orderObject, "I001");
        PropertyDescriptor priceProperty = new PropertyDescriptor("price", orderClass);
        priceProperty.getWriteMethod().invoke(orderObject, Double.valueOf(100.00));
        PropertyDescriptor qtyProperty = new PropertyDescriptor("qty", orderClass);
        qtyProperty.getWriteMethod().invoke(orderObject, Integer.valueOf(20));

 		// Invoke the processOrder() method and print the result
		// The response class is String
		Object[] result = client.invoke(opName, orderObject);
        System.out.println("The order ID is " + result[0]);
	}
}
