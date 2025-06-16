package com.example.jobApp.controller;

import com.example.jobApp.entity.Recruiter;
import com.example.jobApp.service.RecruiterService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/recruiters")
@CrossOrigin(origins = "*")
public class RecruiterController {

    @Autowired
    private RecruiterService recruiterService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping
    public ResponseEntity<List<Recruiter>> getAllRecruiters() {
        return new ResponseEntity<>(recruiterService.allRecruiters(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Recruiter>> getSingleRecruiter(@PathVariable Long id) {
        System.out.println("getSingleRecruiter => id" + id);
        return new ResponseEntity<>(recruiterService.singleRecruiterById(id), HttpStatus.OK);
    }

    @PostMapping("/{id}/appendjob")
    public ResponseEntity<?> appendJob(@PathVariable Long id, @RequestBody Long jobId) {
        System.out.println("appendjob => id" + id + " jobid = "+ jobId);
        try {
            Recruiter updatedRecruiter = recruiterService.addJobToRecruiter(id, jobId);
            return new ResponseEntity<>(updatedRecruiter, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{id}/removejob")
    public ResponseEntity<Recruiter> removeJob(@PathVariable Long id, @RequestBody Long jobId) {
        System.out.println("removejob => id" + id + " jobid = "+ jobId);
        Recruiter updatedRecruiter = recruiterService.removeJobFromRecruiter(id, jobId);
        return new ResponseEntity<>(updatedRecruiter, HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Recruiter recruiter) {
        System.out.println("signup =>" + recruiter.toString());
        Optional<Recruiter> existingRecruiter = recruiterService.singleRecruiterByEmail(recruiter.getEmail());
        if (existingRecruiter.isPresent()) {
            return new ResponseEntity<>("Email already taken", HttpStatus.BAD_REQUEST);
        }
        recruiter.setPassword(passwordEncoder.encode(recruiter.getPassword()));
        return new ResponseEntity<>(recruiterService.createRecruiter(recruiter), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> payload, HttpServletRequest request) {
        System.out.println("login " + payload );
        String email = payload.get("email");
        String password = payload.get("password");

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession(true);

            Optional<Recruiter> recruiter = recruiterService.singleRecruiter(email);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("token", session.getId());
            responseBody.put("recruiter", recruiter.get());

            return new ResponseEntity<>(responseBody, HttpStatus.OK);

        } catch (AuthenticationException e) {
            return new ResponseEntity<>(Map.of("error", "Invalid credentials"), HttpStatus.UNAUTHORIZED);
        }
    }


    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("Logout =>");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }
}
