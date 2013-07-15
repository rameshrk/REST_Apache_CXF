package demo.order.server;

import java.util.List;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapHeader;
import org.apache.cxf.common.util.ClassHelper;
import org.apache.cxf.headers.Header;
import org.apache.cxf.message.Exchange;
import org.apache.cxf.service.invoker.AbstractInvoker;
import org.apache.cxf.service.model.OperationInfo;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import demo.order.OrderProcessImpl;

public class AuthenticationInvoker  extends AbstractInvoker {

	private String userName;
	private String password;
	
	private Object bean;

	public AuthenticationInvoker(Object bean) {
	        this.bean = bean;
	}
	 
	@Override
	public Object invoke(Exchange exchange, Object o) {
		
		// Get method and class details from the request 
		OperationInfo opInfo = exchange.get(OperationInfo.class);
		String methodName = opInfo.getInputName();
		Class<?> realClass = ClassHelper.getRealClass(bean);
		QName qnameOrderCredential = new QName("OrderCredentials");
	     
	    // Perform security check only if the service class is OrderProcessImpl
		// and method name is processOrder
		if (realClass == OrderProcessImpl.class && "processOrder".equals(methodName)) {
			List list = (List) exchange.getInMessage().get(Header.HEADER_LIST);
			for (int  i = 0 ; i< list.size() ; i++  ) {
				// Get the SOAP header
				SoapHeader header = (SoapHeader) list.get(i);
				if(header.getName().equals(qnameOrderCredential)) {
								 
					Element orderCredential= (Element) header.getObject();
					Node usernamel = orderCredential.getFirstChild();
					Node passwordel = orderCredential.getLastChild();

					if (usernamel != null) {
						userName = usernamel.getTextContent();
					}
					if (passwordel != null) {
						password = passwordel.getTextContent();
					}
				} else {
					 throw new RuntimeException("Request doesn't contain OrderCredentials namespace");
				}
			}
		     
			System.out.println("userName retrieved from SOAP Header is " + userName);
			System.out.println("password retrieved from SOAP Header is " + password);

			// Perform dummy validation for John
			if ("John".equalsIgnoreCase(userName) && "password".equalsIgnoreCase(password)) {
				System.out.println("Authentication successful for John");
			} else {
				throw new RuntimeException("Invalid user or password");
			}
		}	
	     
	     // Call super class invoke method
		 // This will invoke processOrder method
		 return super.invoke(exchange,o);

	}

	@Override
	public Object getServiceObject(Exchange arg0) {
		// TODO Auto-generated method stub
		return bean;
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
