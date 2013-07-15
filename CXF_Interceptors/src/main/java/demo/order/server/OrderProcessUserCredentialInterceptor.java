package demo.order.server;

import javax.xml.namespace.QName;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class OrderProcessUserCredentialInterceptor extends AbstractSoapInterceptor {

	private String userName;
	private String password;

	public OrderProcessUserCredentialInterceptor() {
		super(Phase.PRE_INVOKE);
	}

	public void handleMessage(SoapMessage message) throws Fault {

		System.out.println("OrderProcessUserCredentialInterceptor handleMessage invoked");
		QName qnameCredentials = new QName("OrderCredentials");

		// Get header based on QNAME
		if (message.hasHeader(qnameCredentials )) {
			Header header = message.getHeader(qnameCredentials);

			Element elementOrderCredential= (Element) header.getObject();
			Node nodeUser = elementOrderCredential.getFirstChild();
			Node nodePassword = elementOrderCredential.getLastChild();

			if (nodeUser != null) {
				userName = nodeUser.getTextContent();
			}
			if (nodePassword != null) {
				password = nodePassword.getTextContent();
			}
		}

		System.out.println("userName reterived from SOAP Header is " + userName);
		System.out.println("password reterived from SOAP Header is " + password);

		// Perform dummy validation for John
		if ("John".equalsIgnoreCase(userName) && "password".equalsIgnoreCase(password)) {
			System.out.println("Authentication successful for John");
		} else {
			throw new RuntimeException("Invalid user or password");
		}
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
