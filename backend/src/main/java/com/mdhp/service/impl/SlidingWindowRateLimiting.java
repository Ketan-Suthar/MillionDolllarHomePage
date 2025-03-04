package com.mdhp.service.impl;

import com.mdhp.service.IRateLimitingService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class SlidingWindowRateLimiting extends IRateLimitingService {

    private final ConcurrentHashMap<String, Queue<Long>> ipRequestMap = new ConcurrentHashMap<>();

    public boolean isRateLimited(final HttpServletRequest request) {
        System.out.println("Sliding window rate limiting");
        final var clientIp = getClientIp(request);
        final var userRequests = ipRequestMap.getOrDefault(clientIp, new LinkedList<>());
        final var currentTime = System.currentTimeMillis();

        System.out.println(userRequests);
        while(!userRequests.isEmpty() && (currentTime - userRequests.peek() > TIME_WINDOW)) {
            userRequests.poll();
        }

        final var limitExceeded = userRequests.size() >= MAX_REQUESTS;
        userRequests.add(currentTime);
        ipRequestMap.put(clientIp, userRequests);
        return limitExceeded;
    }
}

