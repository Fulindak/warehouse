package danila.mediasoft.test.warehouse.filter;

import danila.mediasoft.test.warehouse.services.customer.provider.CustomerProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomerFilter extends OncePerRequestFilter {
    private final CustomerProvider customerProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String customerId = request.getHeader("customerId");
        Optional.ofNullable(customerId)
                .map(Long::valueOf)
                .ifPresent(customerProvider::setId);
        filterChain.doFilter(request, response);
    }
}
