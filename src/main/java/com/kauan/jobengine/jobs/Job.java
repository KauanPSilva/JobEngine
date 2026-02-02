package com.kauan.jobengine.jobs;

import jakarta.persistence.*;
import sun.jvm.hotspot.debugger.cdbg.EnumType;

import java.time.Instant;
import java.util.UUID;


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

    // por enquanto string (depois evolui para JSONB)
    @Column(length = 8000, nullable = false)
    private String payload;


}
