package com.holycode.neon;

import com.holycode.neon.models.Release;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReleaseRepository extends JpaRepository<Release, Long>, ReleaseRepositoryCustom {


}
