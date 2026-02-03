package com.kauan.jobengine.jobs;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.time.Instant;
import java.util.UUID;
import java.util.List;


@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobRepository repo;

    public JobController(JobRepository repo) {
        this.repo = repo;
    }

    @GetMapping
    public List<JobResponse> get(
            @RequestParam(required=false) JobStatus status,
            @RequestParam(required=false) JobType type) {

        List<Job> jobs;

        if(status != null && type != null){
            jobs = repo.findByStatusAndType(status, type);
        } else if(status != null) {
            jobs = repo.findByStatus(status);
        } else if(type != null) {
            jobs = repo.findByType(type);
        } else{
            jobs = repo.findAll();
        }

        return jobs.stream()
                .map(JobResponse::from)
                .toList();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobResponse create(@RequestBody @Valid CreateJobRequest req) {
        Job job = new Job();
        job.setType(req.type());
        job.setPayload(req.payload());
        job.setStatus(JobStatus.PENDING);
        job.setNextRunAt(Instant.now());

        return JobResponse.from(repo.save(job));
    }

    @GetMapping("/{id}")
    public JobResponse get(@PathVariable UUID id) {
        Job job = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Job not found"));
        return JobResponse.from(job);

    }

}
