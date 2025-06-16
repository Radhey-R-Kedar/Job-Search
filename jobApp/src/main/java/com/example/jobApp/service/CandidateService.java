package com.example.jobApp.service;

import com.example.jobApp.entity.Candidate;
import com.example.jobApp.repository.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Candidate> allCandidates() {
        return candidateRepository.findAll();
    }

    public Optional<Candidate> singleCandidate(String email) {
        return candidateRepository.findByEmail(email);
    }

    public Candidate createCandidate(Candidate candidate) {
        return candidateRepository.save(candidate);
    }
}
