package com.mdhp.service;


import jakarta.servlet.http.HttpServletRequest;

public abstract class IRateLimitingService {

    public static final int MAX_REQUESTS = 5;  // Max requests allowed per period
    public static final long TIME_WINDOW = 15_000; // 1 minute in milliseconds

    protected String getClientIp(final HttpServletRequest request) {
        String clientIp = request.getHeader("X-Forwarded-For"); // Handles proxies
        if (clientIp == null || clientIp.isEmpty()) {
            clientIp = request.getRemoteAddr(); // Direct client IP
        }
        return clientIp;
    }

    public abstract boolean isRateLimited(final HttpServletRequest request);
}
