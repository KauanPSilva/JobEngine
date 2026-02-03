package com.kauan.jobengine.jobs.retry;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class RetryPolicy {

    private static final int MAX_ATTEMPTS = 5;
    private static final long BASE_DELAY_SECONDS = 5;
    private static long MAX_DELAY_SECONDS = 600;

    public boolean shouldRetry(int attempts) {
        return attempts < MAX_ATTEMPTS;
    }

    public Instant nextRunAt(int attempts) {
        long delay = BASE_DELAY_SECONDS * (1L << Math.max(0, attempts - 1));
        long capped = Math.min(delay, MAX_DELAY_SECONDS);
        return Instant.now().plusSeconds(capped);
    }

    public int maxAttempts() {
        return MAX_ATTEMPTS;
    }
}
