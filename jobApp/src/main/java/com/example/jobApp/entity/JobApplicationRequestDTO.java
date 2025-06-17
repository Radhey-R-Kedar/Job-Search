package com.example.jobApp.entity;

import java.util.List;

public class JobApplicationRequestDTO {
    public String name;
    public String email;
    public String phone;
    public String qualification;
    public String resumeLink;
    public String status;
    public List<String> skills;
    public Long jobId;

    public JobApplicationRequestDTO(String name, String email, String phone, String qualification, String resumeLink, String status, List<String> skills, Long jobId) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.qualification = qualification;
        this.resumeLink = resumeLink;
        this.status = status;
        this.skills = skills;
        this.jobId = jobId;
    }

    public JobApplicationRequestDTO() {
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

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Long getJobId() {
        return jobId;
    }

    public void setJobId(Long jobId) {
        this.jobId = jobId;
    }

    @Override
    public String toString() {
        return "JobApplicationRequestDTO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", qualification='" + qualification + '\'' +
                ", resumeLink='" + resumeLink + '\'' +
                ", status='" + status + '\'' +
                ", skills=" + skills +
                ", jobId=" + jobId +
                '}';
    }
}
