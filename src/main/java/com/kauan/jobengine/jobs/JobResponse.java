package com.kauan.jobengine.jobs;

import java.time.Instant;
import java.util.UUID;

public record JobResponse(
        UUID id,
        JobType type,
        JobStatus status,
        Instant createdAt,
        Instant startedAt,
        Instant finishedAt,
        int attempts,
        String lastError,
        String payload
) {
    public static JobResponse from(Job job) {
        return new JobResponse(
                job.getId(),
                job.getType(),
                job.getStatus(),
                job.getCreatedAt(),
                job.getStartedAt(),
                job.getFinishedAt(),
                job.getAttempts(),
                job.getLastError(),
                job.getPayload()
        );
    }
}
