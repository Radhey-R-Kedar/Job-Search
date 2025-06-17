package com.example.jobApp.controller;

import com.example.jobApp.entity.JobApplication;
import com.example.jobApp.entity.JobApplicationRequestDTO;
import com.example.jobApp.service.JobApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/applications")
@CrossOrigin(origins = "*")
public class JobApplicationController {

    private final List<String> VALID_STATUS_OPTIONS = Arrays.asList("Pending", "Accepted", "Rejected");

    @Autowired
    private JobApplicationService jobApplicationService;

    @GetMapping
    public ResponseEntity<List<JobApplication>> getAllJobApplications() {
        List<JobApplication> applicationList = jobApplicationService.allJobApplications();
        System.out.println("application List => "+ applicationList);
        return new ResponseEntity<>(applicationList, HttpStatus.OK);
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<Optional<JobApplication>> getSingleJobApplication(@PathVariable Long applicationId) {
        return new ResponseEntity<>(jobApplicationService.singleJobApplication(applicationId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<JobApplication> applyForJob(@RequestBody JobApplicationRequestDTO request) {
        System.out.println("apply for job =>" + request);
        JobApplication jobApplication = jobApplicationService.createJobApplication(request);
        return new ResponseEntity<>(jobApplication, HttpStatus.CREATED);
    }


    @PostMapping("/{applicationId}")
    public ResponseEntity<?> updateJobApplicationStatus(@PathVariable Long applicationId, @RequestBody String newStatusRaw) {
        String newStatus = newStatusRaw.replace("\"", ""); // remove quotes
        System.out.println("updateJobApplicationStatus => " + applicationId + " " + newStatus);

        if (VALID_STATUS_OPTIONS.contains(newStatus)) {
            return new ResponseEntity<>(jobApplicationService.updateStatus(applicationId, newStatus), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid option", HttpStatus.BAD_REQUEST);
        }
    }
}
