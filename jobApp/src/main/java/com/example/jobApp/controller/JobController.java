package com.example.jobApp.controller;

import com.example.jobApp.entity.Job;
import com.example.jobApp.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/jobs")
@CrossOrigin(origins = "*")
public class JobController {

    @Autowired
    private JobService jobService;

    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs() {
        List<Job> alljobs = jobService.allJobs();
        System.out.println("jobList => " + alljobs);
        return new ResponseEntity<>(alljobs, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Job>> getSingleJob(@PathVariable Long id) {
        System.out.println("get job by id => " + id);
        return new ResponseEntity<>(jobService.singleJob(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        System.out.println("create Job => " + job.toString());
        return new ResponseEntity<>(jobService.createJob(job), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJob(@PathVariable String  id) {
        System.out.println("deletejob =>"+ id);
        jobService.deleteJob(Long.valueOf(id));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
