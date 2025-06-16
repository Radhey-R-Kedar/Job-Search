package com.example.jobApp.configuration;

import com.example.jobApp.entity.Candidate;
import com.example.jobApp.entity.Recruiter;
import com.example.jobApp.repository.CandidateRepository;
import com.example.jobApp.repository.RecruiterRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private final RecruiterRepository recruiterRepository;
    private final CandidateRepository candidateRepository;

    public AppUserDetailsService(RecruiterRepository recruiterRepository, CandidateRepository candidateRepository) {
        this.recruiterRepository = recruiterRepository;
        this.candidateRepository = candidateRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // First try recruiter
        Optional<Recruiter> recruiter = recruiterRepository.findByEmail(email);
        if (recruiter.isPresent()) {
            return new User(recruiter.get().getEmail(), recruiter.get().getPassword(), List.of(new SimpleGrantedAuthority("ROLE_RECRUITER")));
        }

        // Then try candidate
        Optional<Candidate> candidate = candidateRepository.findByEmail(email);
        if (candidate.isPresent()) {
            return new User(candidate.get().getEmail(), candidate.get().getPassword(), List.of(new SimpleGrantedAuthority("ROLE_CANDIDATE")));
        }

        throw new UsernameNotFoundException("User not found with email: " + email);
    }
}
