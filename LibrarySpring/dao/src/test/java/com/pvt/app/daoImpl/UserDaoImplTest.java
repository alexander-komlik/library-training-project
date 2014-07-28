package com.pvt.app.daoImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test-context.xml", "/spring-test-context-beans.xml"})
public class UserDaoImplTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    @Qualifier("userDao")
    MyDao<User> userDao;

    @Test(expected = UnsupportedOperationException.class)
    public void testCreate() {
        userDao.create(new User());
    }

    @Test
    public void testUpdate() {
        User user = new User();
        user.setId(5);
        user.setRole("user");
        userDao.update(user);
        User user2 = userDao.read(5);
        assert (user2.getRole().equals("user"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete() {
        userDao.delete(1);
    }

    @Test
    public void testGetAllNoFilters() {
        List<User> userList = userDao.getAll(0, 3, null, null);
        assert (userList.size()==3);
    }

    @Test
    public void testGetAllByName() {
        User filter = new User();
        filter.setName("user1");
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(filter);
        List<User> userList = userDao.getAll(0, 3, filters, null);
        assert (userList.size()==1);
    }

    @Test
    public void testGetCount() {
        long result = userDao.getCount(null);
        assert (result>=5);
    }
}

