package com.pvt.app.daoImpl;

import com.pvt.app.criteriaBuilder.PredicateBuilder;
import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.Author;
import com.pvt.app.entity.Book;
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
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

@Repository("bookDao")
public class BookDaoImpl extends AbstractDaoImpl<Book> implements MyDao<Book> {

    private static Map<Class, PredicateBuilder> builderMap;

    static {
        builderMap = new HashMap<Class, PredicateBuilder>();
        builderMap.put(Author.class, new PredicateBuilder() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root from, MyEntity entity) {
                Path path = from.join("authors").get("id");
                return criteriaBuilder.equal(path, entity.getId());
            }
        });
        builderMap.put(User.class, new PredicateBuilder() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root from, MyEntity entity) {
                Path path = from.join("user").get("id");
                return criteriaBuilder.equal(path, entity.getId());
            }
        });
        builderMap.put(Book.class, new PredicateBuilder() {
            @Override
            public Predicate getPredicate(CriteriaBuilder criteriaBuilder, Root from, MyEntity entity) {
                Path path = from.get("title");
                String pattern = "%"+((Book)entity).getTitle()+"%";
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
    protected void preCreate(Book item) {
        item.setLastUpdate(new GregorianCalendar());
    }

    @Override
    protected void doUpdate(Book persistItem, Book item) {
        persistItem.setTitle(item.getTitle());
        persistItem.setAuthors(item.getAuthors());
        persistItem.getText().setText(item.getText().getText());
        persistItem.setLastUpdate(new GregorianCalendar());
    }

    @Override
    protected void preDelete(Book item) {
        item.getAuthors().clear();
    }

    @Override
    protected Query getReadByIdNamedQuery(EntityManager em) {
        return em.createNamedQuery("Book.getById");
    }

    @Override
    protected Map<Class, PredicateBuilder> getBuilderMap() {
        return builderMap;
    }
}
