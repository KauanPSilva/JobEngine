package com.kauan.jobengine.jobs;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private JobStatus status = JobStatus.PENDING;

    @Column(nullable = false)
    private Instant createdAt = Instant.now();

    private Instant startedAt;
    private Instant finishedAt;

    @Column(nullable = false)
    private int attempts = 0;

    @Column(length = 4000)
    private String lastError;

    @Column(length = 8000, nullable = false)
    private String payload;

    public Job() {}

    public UUID getId() { return id; }

    public JobType getType() { return type; }
    public void setType(JobType type) { this.type = type; }

    public JobStatus getStatus() { return status; }
    public void setStatus(JobStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }

    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }

    public Instant getFinishedAt() { return finishedAt; }
    public void setFinishedAt(Instant finishedAt) { this.finishedAt = finishedAt; }

    public int getAttempts() { return attempts; }
    public void setAttempts(int attempts) { this.attempts = attempts; }

    public String getLastError() { return lastError; }
    public void setLastError(String lastError) { this.lastError = lastError; }

    public String getPayload() { return payload; }
    public void setPayload(String payload) { this.payload = payload; }
}
