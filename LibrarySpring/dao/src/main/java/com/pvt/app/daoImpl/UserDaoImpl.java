package com.pvt.app.daoImpl;

import com.pvt.app.criteriaBuilder.PredicateBuilder;
import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.Map;

@Repository("userDao")
public class UserDaoImpl extends AbstractDaoImpl<User> implements MyDao<User> {

    private static Map<Class, PredicateBuilder> builderMap;

    static {
        builderMap = new HashMap<Class, PredicateBuilder>();
        builderMap.put(User.class, new PredicateBuilder() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root from, MyEntity entity) {
                Path path = from.get("name");
                String pattern = ((User)entity).getName();
                return criteriaBuilder.like(path, pattern);
            }
        });
    }

    private EntityManager em;

    @PersistenceContext
    public void setEntityManager(EntityManager em) {
        this.em = em;
    }

    public EntityManager getEntityManager() {
        return em;
    }

    @Override
    protected void preCreate(User item) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void doUpdate(User persistItem, User item) {
        persistItem.setRole(item.getRole());
    }

    @Override
    protected void preDelete(User item) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Query getReadByIdNamedQuery(EntityManager em) {
        return em.createNamedQuery("User.getById", User.class);
    }

    @Override
    protected Map<Class, PredicateBuilder> getBuilderMap() {
        return builderMap;
    }
}
