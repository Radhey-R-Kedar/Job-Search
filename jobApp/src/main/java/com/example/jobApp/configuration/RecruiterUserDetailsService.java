package com.example.jobApp.configuration;

import com.example.jobApp.entity.Recruiter;
import com.example.jobApp.repository.RecruiterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecruiterUserDetailsService implements UserDetailsService {

    @Autowired
    private RecruiterRepository recruiterRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Recruiter recruiter = recruiterRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Recruiter not found"));
        return new org.springframework.security.core.userdetails.User(
                recruiter.getEmail(),
                recruiter.getPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_RECRUITER"))
        );
    }
}

