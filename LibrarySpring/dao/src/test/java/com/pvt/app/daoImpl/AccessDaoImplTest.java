package com.pvt.app.daoImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.AccessData;
import com.pvt.app.entity.MyEntity;
import org.junit.Ignore;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-test-context.xml", "/spring-test-context-beans.xml"})
public class AccessDaoImplTest {

    @Autowired
    ApplicationContext context;

    @Autowired
    @Qualifier("accessDao")
    MyDao<AccessData> accessDataDao;

    @Test
    public void testRegisterSuccess() {
        AccessData accessData = (AccessData) context.getBean("testUser1AD");
        long result = accessDataDao.create(accessData);
        System.out.println(result);
        assert (result>0);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRegisterNotUniqueEmail() {
        AccessData accessData = (AccessData) context.getBean("testUser2AD");
        accessData.getUser().setEmail("user1@gmail.com");
        accessDataDao.create(accessData);
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void testRegisterNotUniqueName() {
        AccessData accessData = (AccessData) context.getBean("testUser2AD");
        accessData.getUser().setName("user1");
        accessDataDao.create(accessData);
    }

    @Test
    public void testLoginFilterFound() {
        AccessData accessData = (AccessData) context.getBean("adminLoginData");
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(accessData);
        List<AccessData> adList = accessDataDao.getAll(0, Integer.MAX_VALUE, filters, null);
        assert (adList.size() == 1);
    }

    @Test
    public void testLoginFilterNotFound() {
        AccessData accessData = (AccessData) context.getBean("emptyUserLoginData");
        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(accessData);
        List<AccessData> adList = accessDataDao.getAll(0, Integer.MAX_VALUE, filters, null);
        assert (adList.size() == 0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRead() {
        accessDataDao.read(1);
    }

    @Ignore
    @Test
    public void testUpdate() {
        //Not ready yet
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testDelete() {
        accessDataDao.delete(1);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testGetCount() {
        accessDataDao.getCount(new ArrayList<MyEntity>());
    }
}
