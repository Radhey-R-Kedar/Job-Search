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
    @ManyToOne(fetch = FetchType.LAZY)
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
}
