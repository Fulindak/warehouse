package danila.mediasoft.test.warehouse.dto.customer;

import lombok.Builder;

import java.util.UUID;

@Builder
public record CustomerInfo(Long id, String accountNumber, String email, String inn) {
}
