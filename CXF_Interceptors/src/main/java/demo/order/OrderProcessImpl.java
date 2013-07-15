package demo.order;

import javax.jws.WebService;

@org.apache.cxf.interceptor.InInterceptors (interceptors = {"demo.order.server.OrderProcessUserCredentialInterceptor" })
@WebService
public class OrderProcessImpl implements OrderProcess {

    public String processOrder(Order order) {
		System.out.println("Processing order...");
		String orderID = validate(order);
        return orderID;
    }

	/**
	 * Validates the order and returns the order ID
	**/
	private String validate(Order order) {
		String custID = order.getCustomerID();
		String itemID = order.getItemID();
		int qty = order.getQty();
		double price = order.getPrice();

		if (custID != null && itemID != null && qty > 0 && price > 0.0) {
			return "ORD1234";
		}

		return null;
	}
}

