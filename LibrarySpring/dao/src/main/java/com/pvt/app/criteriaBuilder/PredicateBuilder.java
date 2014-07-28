package com.pvt.app.criteriaBuilder;

import com.pvt.app.entity.MyEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public interface PredicateBuilder<T extends MyEntity> {

    public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root<T> from, MyEntity entity);

}

