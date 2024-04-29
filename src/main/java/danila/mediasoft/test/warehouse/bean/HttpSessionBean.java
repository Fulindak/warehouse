package danila.mediasoft.test.warehouse.bean;

import danila.mediasoft.test.warehouse.enums.CurrencyType;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;

@Data
@SessionScope
@Component
public class HttpSessionBean {
    private CurrencyType currency;
}
