package com.example.jobApp.service;

import com.example.jobApp.entity.Job;
import com.example.jobApp.entity.JobApplication;
import com.example.jobApp.entity.JobApplicationRequestDTO;
import com.example.jobApp.repository.JobApplicationRepository;
import com.example.jobApp.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobApplicationService {

    @Autowired
    private JobApplicationRepository jobApplicationRepository;

    @Autowired
    private JobRepository jobRepository;

    public List<JobApplication> allJobApplications() {
        return jobApplicationRepository.findAll();
    }

    public Optional<JobApplication> singleJobApplication(Long jobId) {
        return jobApplicationRepository.findByJob_Id(jobId);
    }

    public JobApplication createJobApplication(JobApplicationRequestDTO request) {
        Job job = jobRepository.findById(request.jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));

        JobApplication jobApplication = new JobApplication();
        jobApplication.setName(request.name);
        jobApplication.setEmail(request.email);
        jobApplication.setPhone(request.phone);
        jobApplication.setQualification(request.qualification);
        jobApplication.setResumeLink(request.resumeLink);
        jobApplication.setStatus(request.status);
        jobApplication.setSkills(request.skills);
        jobApplication.setJob(job);

        System.out.println("job application =>"+ jobApplication);

        return jobApplicationRepository.save(jobApplication);
    }

    public JobApplication updateStatus(Long applicationId, String newStatus) {
        System.out.println("update Status => " + applicationId + " " + newStatus);
        JobApplication application = jobApplicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Job application not found"));

        application.setStatus(newStatus);
        return jobApplicationRepository.save(application);
    }
}
