package com.holycode.neon;

import com.holycode.neon.models.Release;
import com.holycode.neon.models.ReleaseSearchFilter;

import java.util.List;

public interface ReleaseRepositoryCustom {
    List<Release> findUsingSearchFilter(ReleaseSearchFilter releaseSearchFilter);
}
