package demo.order;

import javax.jws.WebService;

@WebService
public interface OrderProcess {
    String processOrder(Order order); 
}
