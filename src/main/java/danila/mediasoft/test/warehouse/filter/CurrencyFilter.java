package danila.mediasoft.test.warehouse.filter;

import danila.mediasoft.test.warehouse.services.currency.provider.CurrencyProvider;
import danila.mediasoft.test.warehouse.enums.CurrencyType;
import danila.mediasoft.test.warehouse.exceptions.IllegalCurrencyTypeException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CurrencyFilter extends OncePerRequestFilter {
    private final CurrencyProvider currencyProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String currency = request.getHeader("currency");
        try {
            if (StringUtils.isNotBlank(currency)) {
                currencyProvider.setCurrency(CurrencyType.formValue(currency));
            }
            filterChain.doFilter(request, response);
        } catch (IllegalCurrencyTypeException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "This operation is not supported: " + currency);
        }
    }
}
