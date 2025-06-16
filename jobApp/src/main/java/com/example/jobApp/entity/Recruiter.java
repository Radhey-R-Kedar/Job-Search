package com.example.jobApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "recruiters")
public class Recruiter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String password;
    private String company;
    private String location;

    @OneToMany(mappedBy = "recruiter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Job> jobs;

    public Recruiter() {
    }

    public Recruiter(String name, String email, String password, String company, String location, List<Job> jobs) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.company = company;
        this.location = location;
        this.jobs = jobs;
    }

    @JsonProperty("id")
    public String getIdString() {
        return id != null ? id.toString() : null;
    }

    @JsonProperty("jobIds")
    public List<String> getJobIdsString() {
        if (jobs != null) {
            return jobs.stream().map(job -> job.getId().toString()).collect(Collectors.toList());
        } else {
            return null;
        }
    }

    public void addJob(Job job) {
        this.jobs.add(job);
        job.setRecruiter(this);
    }

    public void removeJob(Job job) {
        this.jobs.remove(job);
        job.setRecruiter(null);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    @Override
    public String toString() {
        return "Recruiter{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", jobs=" + jobs +
                '}';
    }
}
