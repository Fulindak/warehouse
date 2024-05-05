package danila.mediasoft.test.warehouse.controllers.order;

import danila.mediasoft.test.warehouse.dto.order.CreateOrderRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RequestMapping("/order")
public interface OrderController {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    void createOrder(CreateOrderRequest orderRequest);
}
