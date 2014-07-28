package com.pvt.app.daoImpl;

import com.pvt.app.criteriaBuilder.PredicateBuilder;
import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.AccessData;
import com.pvt.app.entity.Book;
import com.pvt.app.entity.MyEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("accessDataDao")
public class AccessDaoImpl extends AbstractDaoImpl<AccessData> implements MyDao<AccessData> {

    private static Map<Class, PredicateBuilder> builderMap;

    static {
        builderMap = new HashMap<Class, PredicateBuilder>();
        builderMap.put(AccessData.class, new PredicateBuilder() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root from, MyEntity entity) {
                Path path = from.join("user").get("email");
                String pattern = ((AccessData)entity).getUser().getEmail();
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
    protected void preCreate(AccessData item) {
        //NOP
    }

    @Override
    protected void doUpdate(AccessData persistItem, AccessData item) {
        throw new UnsupportedOperationException("Not ready yet.");
    }

    @Override
    protected void preDelete(AccessData item) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    @Override
    protected Query getReadByIdNamedQuery(EntityManager em) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

    @Override
    protected Map<Class, PredicateBuilder> getBuilderMap() {
        return builderMap;
    }

    @Override
    public long getCount(List<MyEntity> filters) {
        throw new UnsupportedOperationException("Unsupported operation.");
    }

}
