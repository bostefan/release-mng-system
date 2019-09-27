package com.holycode.neon;

import com.holycode.neon.models.Release;

import java.util.List;

public interface ReleaseRepositoryCustom<T> {
    List<Release> findUsingSearchFilter(T searchFilter);
}
