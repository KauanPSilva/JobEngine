package com.kauan.jobengine.jobs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateJobRequest(
        @NotNull JobType type,
        @NotBlank String payload
) {}
