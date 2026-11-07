/**
 * 
 */
package dev.infomatik.wx;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class ScannerBlockerFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(ScannerBlockerFilter.class);

    // List of common scanner targets to block
    private static final List<String> BLOCKED_EXTENSIONS = List.of(
            ".php", ".env", ".git", ".yaml", ".yml", ".ini", ".bak", ".conf"
    );
    
    private static final List<String> BLOCKED_PATHS = List.of(
            "/wp-admin", "/wp-content", "/wp-includes", "/xmlrpc.php", "/actuator"
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                    HttpServletResponse response, 
                                    FilterChain filterChain) throws ServletException, IOException {
        
        String uri = request.getRequestURI().toLowerCase();

        // Check if the URI matches any malicious scanner patterns
        boolean isScanner = BLOCKED_EXTENSIONS.stream().anyMatch(uri::endsWith) 
                || BLOCKED_PATHS.stream().anyMatch(uri::startsWith);

        if (isScanner) {
            // Log at DEBUG or INFO level depending on how quiet you want your logs to be
            log.debug("Blocked malicious scanner request to: {}", request.getRequestURI());
            
            // Return 404 Not Found (or 400 Bad Request) immediately
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return; 
        }

        // Pass valid requests down the filter chain
        filterChain.doFilter(request, response);
    }
}
