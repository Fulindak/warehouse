package danila.mediasoft.test.warehouse.dto.camunda;

import java.util.UUID;

public record ComplianceRequest(String login, String inn, UUID businessKey) {
}
