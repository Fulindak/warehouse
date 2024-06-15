package danila.mediasoft.test.warehouse.dto.order;

import danila.mediasoft.test.warehouse.dto.customer.CustomerInfo;
import danila.mediasoft.test.warehouse.enums.OrderStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderInfo (UUID id, CustomerInfo customer, OrderStatus status, String deliverAddress, Long quantity){
}
