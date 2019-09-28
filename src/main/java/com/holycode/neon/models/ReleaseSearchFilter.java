package com.holycode.neon.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReleaseSearchFilter {

    private String name;
    private List<String> statusIn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date releaseDateFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date releaseDateTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAtFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdAtTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date lastUpdateAtFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date lastUpdateAtTo;

    public ReleaseSearchFilter() {
    }

    public ReleaseSearchFilter(List<String> statusIn) {
        this.statusIn = statusIn;
    }

    public ReleaseSearchFilter(String nameLike, List<String> statusIn, Date releaseDateFrom, Date releaseDateTo, Date createdAtFrom, Date createdAtTo, Date lastUpdateAtFrom, Date lastUpdateAtTo) {
        this.name = nameLike;
        this.statusIn = statusIn;
        this.releaseDateFrom = releaseDateFrom;
        this.releaseDateTo = releaseDateTo;
        this.createdAtFrom = createdAtFrom;
        this.createdAtTo = createdAtTo;
        this.lastUpdateAtFrom = lastUpdateAtFrom;
        this.lastUpdateAtTo = lastUpdateAtTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getStatusIn() {
        return statusIn;
    }

    public void setStatusIn(List<String> statusIn) {
        this.statusIn = statusIn;
    }

    public Date getReleaseDateFrom() {
        return releaseDateFrom;
    }

    public void setReleaseDateFrom(Date releaseDateFrom) {
        this.releaseDateFrom = releaseDateFrom;
    }

    public Date getReleaseDateTo() {
        return releaseDateTo;
    }

    public void setReleaseDateTo(Date releaseDateTo) {
        this.releaseDateTo = releaseDateTo;
    }

    public Date getCreatedAtFrom() {
        return createdAtFrom;
    }

    public void setCreatedAtFrom(Date createdAtFrom) {
        this.createdAtFrom = createdAtFrom;
    }

    public Date getCreatedAtTo() {
        return createdAtTo;
    }

    public void setCreatedAtTo(Date createdAtTo) {
        this.createdAtTo = createdAtTo;
    }

    public Date getLastUpdateAtFrom() {
        return lastUpdateAtFrom;
    }

    public void setLastUpdateAtFrom(Date lastUpdateAtFrom) {
        this.lastUpdateAtFrom = lastUpdateAtFrom;
    }

    public Date getLastUpdateAtTo() {
        return lastUpdateAtTo;
    }

    public void setLastUpdateAtTo(Date lastUpdateAtTo) {
        this.lastUpdateAtTo = lastUpdateAtTo;
    }

    @Override
    public boolean equals(Object obj) {
        ReleaseSearchFilter compared = (ReleaseSearchFilter) obj;
        boolean name = equalsWithNulls(this.name, compared.getName());
        boolean statusIn = equalsWithNulls(this.getStatusIn(), compared.getStatusIn()) && Arrays.equals(this.getStatusIn() != null ? this.getStatusIn().toArray() : null, compared.getStatusIn()!= null ? compared.getStatusIn().toArray() : null);
        boolean releaseDateFrom = equalsWithNulls(this.releaseDateFrom, compared.getReleaseDateFrom());
        boolean releaseDateTo = equalsWithNulls(this.releaseDateTo, compared.getReleaseDateTo());
        boolean createdAtFrom = equalsWithNulls(this.createdAtFrom, compared.getCreatedAtFrom());
        boolean createdAtTo = equalsWithNulls(this.createdAtTo,compared.getCreatedAtTo());
        boolean lastUpdateAtFrom = equalsWithNulls(this.lastUpdateAtFrom, compared.getLastUpdateAtFrom());
        boolean lastUpdateAtTo = equalsWithNulls(this.lastUpdateAtTo, compared.getLastUpdateAtTo());
        return name && statusIn && releaseDateFrom && releaseDateTo && createdAtFrom && createdAtTo && lastUpdateAtFrom && lastUpdateAtTo;
    }

    public static boolean equalsWithNulls(Object first, Object second) {
        if (first == null && second == null) return true;
        if (first == second) return true;
        if ((first == null)||(second == null)) return false;
        return first.equals(second);
    }
}
