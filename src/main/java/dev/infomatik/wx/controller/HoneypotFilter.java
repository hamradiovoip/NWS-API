package dev.infomatik.wx.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class HoneypotFilter implements Filter {

    // Simple blocklist cache that keeps an IP banned for 24 hours
    private final Cache<String, Boolean> banList = CacheBuilder.newBuilder()
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String ipAddress = getClientIp(httpRequest);

        // If they are already banned, drop them instantly
        if (banList.getIfPresent(ipAddress) != null) {
            httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        String uri = httpRequest.getRequestURI().toLowerCase();

        // Common paths scanned by script kiddies
        if (uri.contains(".env") || 
            uri.contains("wp-") || 
            uri.contains("actuator") || 
            uri.contains(".git")) {
            
            banList.put(ipAddress, true); // Ban them for 24 hours
            httpResponse.setStatus(HttpStatus.FORBIDDEN.value());
            return;
        }

        chain.doFilter(request, response);
    }
    
    private String getClientIp(HttpServletRequest request) {
        String xff = request.getHeader("X-Forwarded-For");
        return (xff != null && !xff.isEmpty()) ? xff.split(",")[0].trim() : request.getRemoteAddr();
    }


	
		
	}

