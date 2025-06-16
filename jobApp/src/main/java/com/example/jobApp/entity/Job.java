package com.example.jobApp.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String position;
    private String company;
    private String location;
    private String experience;

    @Column(length = 2000)
    private String description;

    @ElementCollection
    private List<String> skills;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private Recruiter recruiter;

    public Job() {}

    public Job(Long id, String position, String company, String location, String experience, String description, List<String> skills, Recruiter recruiter) {
        this.id = id;
        this.position = position;
        this.company = company;
        this.location = location;
        this.experience = experience;
        this.description = description;
        this.skills = skills;
        this.recruiter = recruiter;
    }

    @JsonProperty("id")
    public String getIdString() {
        return id != null ? id.toString() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public Recruiter getRecruiter() {
        return recruiter;
    }

    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    @Override
    public String toString() {
        return "Job{" +
                "id=" + id +
                ", position='" + position + '\'' +
                ", company='" + company + '\'' +
                ", location='" + location + '\'' +
                ", experience='" + experience + '\'' +
                ", description='" + description + '\'' +
                ", skills=" + skills +
                ", recruiter=" + recruiter +
                '}';
    }
}
