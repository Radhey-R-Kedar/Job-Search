package com.example.jobApp.service;

import com.example.jobApp.entity.Recruiter;
import com.example.jobApp.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecruiterService {
    @Autowired
    private RecruiterRepository recruiterRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Recruiter> allRecruiters() {
        return recruiterRepository.findAll();
    }

    public Optional<Recruiter> singleRecruiter(String email) {
        return recruiterRepository.findByEmail(email);
    }

    public Recruiter createRecruiter(Recruiter recruiter) {
        return recruiterRepository.save(recruiter);
    }

    public Recruiter addJobToRecruiter(Long id, Long jobId) {
        System.out.println("add job to recuritor => "+ id +" " + jobId);
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        recruiter.getJobIds().add(jobId);
        System.out.println("Updated recuritor =>" + recruiter);
        return recruiterRepository.save(recruiter);
    }

    public Recruiter removeJobFromRecruiter(Long id, Long jobId) {
        Recruiter recruiter = recruiterRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recruiter not found"));

        recruiter.getJobIds().remove(jobId);
        return recruiterRepository.save(recruiter);
    }

    public Optional<Recruiter> singleRecruiterById(Long id) {
        return recruiterRepository.findById(id);
    }

    public Optional<Recruiter> singleRecruiterByEmail(String email) {
        return recruiterRepository.findByEmail(email);
    }
}
