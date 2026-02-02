package com.kauan.jobengine.jobs;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;
import java.util.List;


public interface JobRepository extends JpaRepository<Job, UUID> {

    @Query(
            value = """
                select * from jobs
                where status = 'PENDING'
                order by created_at
                for update skip locked
                limit 1
                """,
            nativeQuery = true
    )
    Optional<Job> lockNextPending();

    @Query(
            value = "update jobs set status = :status where id = :id",
            nativeQuery = true
    )
    int updateStatus(@Param("id") UUID id, @Param("status") String status);

    List<Job> findByStatus(JobStatus status);

    List<Job> findByType(JobType type);

    List<Job> findByStatusAndType(JobStatus status, JobType type);
}
