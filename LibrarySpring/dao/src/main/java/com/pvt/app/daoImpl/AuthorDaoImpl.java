package com.pvt.app.daoImpl;

import com.pvt.app.criteriaBuilder.PredicateBuilder;
import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
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

@Repository("authorDao")
public class AuthorDaoImpl extends AbstractDaoImpl<Author> implements MyDao<Author>{

    private static Map<Class, PredicateBuilder> builderMap;

    static {
        builderMap = new HashMap<Class, PredicateBuilder>();
        builderMap.put(User.class, new PredicateBuilder() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root from, MyEntity entity) {
                Path path = from.join("user").get("id");
                return criteriaBuilder.equal(path, entity.getId());
            }
        });
        builderMap.put(Author.class, new PredicateBuilder() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root from, MyEntity entity) {
                Path path = from.get("name");
                String pattern = "%"+((Author)entity).getName()+"%";
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
    protected void preCreate(Author item) {
        //NOP
    }

    @Override
    protected void doUpdate(Author persistItem, Author item) {
        persistItem.setName(item.getName());
        persistItem.setName4table(item.getName4table());
        persistItem.getText().setText(item.getText().getText());
    }

    @Override
    protected void preDelete(Author item) {
        //NOP
    }

    @Override
    protected Query getReadByIdNamedQuery(EntityManager em) {
        return em.createNamedQuery("Author.getById");
    }

    @Override
    protected Map<Class, PredicateBuilder> getBuilderMap() {
        return builderMap;
    }
}
