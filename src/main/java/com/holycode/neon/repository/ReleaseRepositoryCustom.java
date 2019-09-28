package com.holycode.neon.repository;

import com.holycode.neon.models.Release;

import java.util.List;

public interface ReleaseRepositoryCustom<T> {
    List<Release> findUsingSearchFilter(T searchFilter);
}
