package com.bank.common.lib.utils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class RequestIdGenerator {
    private static String requestId;
    private static final ConcurrentHashMap<String, AtomicInteger> requestIdCounter = new ConcurrentHashMap<>();

    public static String generate(){
        if (requestId == null || requestId.isEmpty()) {
            requestId = UUID.randomUUID().toString().split("-")[0];
            requestIdCounter.put(requestId, new AtomicInteger(1));
        }
        AtomicInteger counter = requestIdCounter.computeIfAbsent(requestId, k -> new AtomicInteger(1));
        return requestId + "-" + counter.getAndIncrement();
    }
}
