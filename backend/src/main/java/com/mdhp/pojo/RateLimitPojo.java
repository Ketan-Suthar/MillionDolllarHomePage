package com.mdhp.pojo;

import lombok.Data;


@Data
public class RateLimitPojo {
    private long timestamp;
    private int requestCount;

    public RateLimitPojo(long timestamp, int requestCount) {
        this.timestamp = timestamp;
        this.requestCount = requestCount;
    }

    public void increaseCount() {
        this.requestCount++;
    }
}
