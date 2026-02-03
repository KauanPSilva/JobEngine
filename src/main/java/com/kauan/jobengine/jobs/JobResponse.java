package com.kauan.jobengine.jobs;

import java.time.OffsetDateTime;
import java.time.ZoneId;

import java.util.UUID;

public record JobResponse(
        UUID id,
        JobType type,
        JobStatus status,
        OffsetDateTime createdAt,
        OffsetDateTime startedAt,
        OffsetDateTime finishedAt,
        OffsetDateTime nextRunAt,
        int attempts,
        String lastError,
        String payload
) {
    private static final ZoneId BR = ZoneId.of("America/Sao_Paulo");

    private static OffsetDateTime br(java.time.Instant i) {
        return i == null ? null : i.atZone(BR).toOffsetDateTime();
    }

    public static JobResponse from(Job job) {
        return new JobResponse(
                job.getId(),
                job.getType(),
                job.getStatus(),
                br(job.getCreatedAt()),
                br(job.getStartedAt()),
                br(job.getFinishedAt()),
                br(job.getNextRunAt()),
                job.getAttempts(),
                job.getLastError(),
                job.getPayload()
        );
    }
}
