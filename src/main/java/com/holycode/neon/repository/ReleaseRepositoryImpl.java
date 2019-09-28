package com.holycode.neon.repository;

import com.holycode.neon.models.Release;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ReleaseRepositoryImpl<T> implements ReleaseRepositoryCustom<T> {

    private static Logger log = LoggerFactory.getLogger(ReleaseRepositoryImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Release> findUsingSearchFilter(T searchFilter) {

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Release> criteriaQuery = criteriaBuilder.createQuery(Release.class);
        Root<Release> release = criteriaQuery.from(Release.class);

        List<Predicate> predicates = new ArrayList<>();

        Field[] fields = searchFilter.getClass().getDeclaredFields();

        Arrays.asList(fields).stream().forEach( field -> {
            try {
                Object value = new PropertyDescriptor(field.getName(), searchFilter.getClass()).getReadMethod().invoke(searchFilter);
                if (value != null) {
                    Class<?> type = field.getType();

                    if (type.equals(Date.class)) {
                        String fieldName = field.getName();
                        if (fieldName.endsWith("From")) {
                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(release.get(fieldName.substring(0,fieldName.indexOf("From"))), (Date)value));
                        } else if (field.getName().endsWith("To")) {
                            predicates.add(criteriaBuilder.lessThanOrEqualTo(release.get(fieldName.substring(0,fieldName.indexOf("To"))), (Date)value));
                        } else {
                            predicates.add(criteriaBuilder.equal(release.get(fieldName), value));
                        }
                    }

                    if (type.equals(List.class)){
//                        ParameterizedType parameterizedType = (ParameterizedType) field.getGenericType();
//                        Class<?> someClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                        //this assumes any List<T> field in searchFilter with xxxIn can be used with Predicate 'in'
                        //TODO: improve
                        predicates.add(release.get(field.getName().substring(0,field.getName().indexOf("In"))).in(value));
                    }
                }

            } catch (IllegalAccessException | InvocationTargetException | IntrospectionException e) {
                log.error(e.getLocalizedMessage());
            }
        });

        Predicate where =  criteriaBuilder.and(predicates.toArray(new Predicate[0]));

        criteriaQuery.select(release);
        criteriaQuery.where(where);

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
