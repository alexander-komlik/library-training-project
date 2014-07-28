package com.pvt.app.testDao;

import com.pvt.app.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class TestDaoImpl implements TestDao {

    @PersistenceContext
    private EntityManager em;

    public User getUser(long id) {
        System.out.println("Inside");
        System.out.println(em.getClass().getName());
        return em.find(User.class, id);
    }
}
