package demo.order.client;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.binding.soap.interceptor.SoapPreProtocolOutInterceptor;
import org.apache.cxf.headers.Header;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.Phase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class OrderProcessClientHandler extends AbstractSoapInterceptor {

	public String userName;

	public String password;;

	public OrderProcessClientHandler() {
		super(Phase.WRITE);
		addAfter(SoapPreProtocolOutInterceptor.class.getName());
	}

	public void handleMessage(SoapMessage message) throws Fault {
		
			System.out.println("OrderProcessClientHandler handleMessage invoked");
		
			DocumentBuilder builder = null;
			try {
				builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
				throw new Fault(e);
			}
			Document doc = builder.newDocument();
			Element credentialsel = doc.createElement("OrderCredentials");
			Element unameel = doc.createElement("username");
			unameel.setTextContent(getUserName());
			Element pwdel = doc.createElement("password");
			pwdel.setTextContent(getPassword());
			credentialsel.appendChild(unameel);
			credentialsel.appendChild(pwdel);
			
			//Create Header object
			QName orderqname =  new QName("OrderCredentials");
			
					
			Header header = new Header(orderqname,credentialsel);
			
			message.getHeaders().add(header);
			
			

	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
