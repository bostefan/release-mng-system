package com.holycode.neon.models;

public enum ReleaseStatus {
    CREATED("Created"),
    IN_DEVELOPMENT("In Development"),
    NORTH_AMERICA("North America"),
    ON_DEV("On DEV"),
    QA_DONE("QA Done"),
    QA_DONE_ON_DEV("QA Done on DEV"),
    ON_STAGING("On staging"),
    QA_DONE_ON_STAGING("QA Done on staging"),
    ON_PROD("On PROD"),
    DONE("Done");

    private String name;

    ReleaseStatus(String name) {
        this.name = name;
    }

    public String displayName() { return name; }

    @Override public String toString() { return name; }
}
