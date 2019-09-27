package com.holycode.neon;

import com.holycode.neon.models.Release;
import com.holycode.neon.models.ReleaseSearchFilter;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class ReleaseRepositoryImpl implements ReleaseRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Release> findUsingSearchFilter(ReleaseSearchFilter releaseSearchFilter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Release> criteriaQuery = criteriaBuilder.createQuery(Release.class);
        Root<Release> release = criteriaQuery.from(Release.class);

        List<Predicate> predicates = new ArrayList<>();
        if (releaseSearchFilter.getStatusIn() != null) {
            predicates.add(release.get("status").in(releaseSearchFilter.getStatusIn()));
        }

        if (releaseSearchFilter.getReleaseDateFrom() != null){
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(release.get("releaseDate"), releaseSearchFilter.getReleaseDateFrom()));
        }

        if (releaseSearchFilter.getReleaseDateTo() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(release.get("releaseDate"), releaseSearchFilter.getReleaseDateTo()));
        }

        if (releaseSearchFilter.getCreatedAtFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(release.get("createdAt"), releaseSearchFilter.getCreatedAtFrom()));
        }

        if (releaseSearchFilter.getCreatedAtTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(release.get("createdAt"), releaseSearchFilter.getCreatedAtTo()));
        }

        if (releaseSearchFilter.getLastUpdateAtFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(release.get("lastUpdateAt"), releaseSearchFilter.getLastUpdateAtFrom()));
        }

        if (releaseSearchFilter.getLastUpdateAtTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(release.get("lastUpdateAt"), releaseSearchFilter.getLastUpdateAtTo()));
        }

        Predicate where =  criteriaBuilder.and(predicates.toArray(new Predicate[0])); //and(statusIn, releaseDateFrom, releaseDateTo, createdAtFrom, createdAtTo, lastUpdateFrom, lastUpdateTo);

        criteriaQuery.select(release);
        criteriaQuery.where(where);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
