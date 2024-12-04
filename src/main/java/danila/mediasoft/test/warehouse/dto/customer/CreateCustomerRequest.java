package danila.mediasoft.test.warehouse.dto.customer;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateCustomerRequest(@Schema(description = "Customer login", example = "danila")
                                    @NotNull(message = "Login must be not null")
                                    String login,
                                    @Schema(description = "Customer email", example = "danila@email.com")
                                    @NotNull(message = "Products must be not null")
                                    String email) {
}
