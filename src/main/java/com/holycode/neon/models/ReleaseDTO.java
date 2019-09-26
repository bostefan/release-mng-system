package com.holycode.neon.models;

import javax.validation.constraints.Pattern;
import java.util.Date;

public class ReleaseDTO {

    private Long id;
    private String name;
    private String description;
    private String status;
    private Date releaseDate;
    private Date createdAt;
    private Date lastUpdateAt;

    public ReleaseDTO() {
    }

    public ReleaseDTO(Long id, String name, String description, String status, Date releaseDate, Date createdAt, Date lastUpdateAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
        this.releaseDate = releaseDate;
        this.createdAt = createdAt;
        this.lastUpdateAt = lastUpdateAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Pattern(regexp = "(Created)|(In Development)|(On DEV)|(QA Done on DEV)|(On staging)|(QA done on STAGING)|(On PROD)|(Done)", message = "Please specify a valid status value!")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getLastUpdateAt() {
        return lastUpdateAt;
    }

    public void setLastUpdateAt(Date lastUpdateAt) {
        this.lastUpdateAt = lastUpdateAt;
    }
}