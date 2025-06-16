package com.example.jobApp.repository;

import com.example.jobApp.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {
    Optional<JobApplication> findByJob_Id(Long jobId);
    List<JobApplication> findAllByJob_Id(Long jobId); // If multiple applications per job

}
