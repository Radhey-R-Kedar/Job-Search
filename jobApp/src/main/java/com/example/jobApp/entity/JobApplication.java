package com.example.jobApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "job_applications")
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phone;
    private String qualification;

    @Column(length = 1000)
    private String resumeLink;

    private String status;

    @ElementCollection
    private List<String> skills;

    // Job reference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "job_id")
    private Job job;

    public JobApplication() {
    }

    public JobApplication(String name, String email, String phone, String qualification,
                          String resumeLink, String status, List<String> skills, Job job) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.qualification = qualification;
        this.resumeLink = resumeLink;
        this.status = status;
        this.skills = skills;
        this.job = job;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("id")
    public String getIdString() {
        return id != null ? id.toString() : null;
    }

    @JsonProperty("jobId")
    public String getJobIdString() {
        return job != null ? job.getIdString() : null;
    }

    @Override
    public String toString() {
        return "JobApplication{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", qualification='" + qualification + '\'' +
                ", resumeLink='" + resumeLink + '\'' +
                ", status='" + status + '\'' +
                ", skills=" + skills +
                ", job=" + job +
                '}';
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getResumeLink() {
        return resumeLink;
    }

    public void setResumeLink(String resumeLink) {
        this.resumeLink = resumeLink;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }
}
