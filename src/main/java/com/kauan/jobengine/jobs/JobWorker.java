package com.kauan.jobengine.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class JobWorker {

    private static final Logger log = LoggerFactory.getLogger(JobWorker.class);

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

    private final JobRepository repo;

    public JobWorker(JobRepository repo) {
        this.repo = repo;
    }

    @Scheduled(fixedDelay = 3000)
    public void tick() {
        executor.submit(this::tryProcessOne);
    }

    @Transactional
    public void tryProcessOne() {
        var opt = repo.lockNextPending();
        if(opt.isEmpty()) return;

        Job job = opt.get();

        job.setStatus(JobStatus.RUNNING);
        job.setStartedAt(Instant.now());
        job.setAttempts(job.getAttempts() + 1);

        repo.save(job);

        try {
            handle(job);

            job.setStatus(JobStatus.DONE);
            job.setFinishedAt(Instant.now());
            job.setLastError(null);
            repo.save(job);

            log.info("Job {} Done (type={})", job.getId(), job.getType());

        } catch (Exception e) {
            job.setStatus(JobStatus.FAILED);
            job.setFinishedAt(Instant.now());
            job.setLastError(e.getMessage());
            repo.save(job);

            log.error("Job {} FAILED: {}", job.getId(), e.getMessage());
        }
    }

    private void handle(Job job) throws Exception {
        if(job.getType() == JobType.SEND_EMAIL) {
            log.info("SEND_EMAIL payload={}", job.getPayload());
            return;
        }

        throw new IllegalArgumentException(("Unsupported job type: " + job.getType()));
    }
}
