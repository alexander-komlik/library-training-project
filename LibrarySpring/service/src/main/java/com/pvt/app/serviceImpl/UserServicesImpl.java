package com.pvt.app.serviceImpl;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.AccessData;
import com.pvt.app.entity.User;
import com.pvt.app.exception.ServiceException;
import com.pvt.app.service.UserServices;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UserServicesImpl implements UserServices{

    private static final int DEFAULT_NUMBER_VALUE = 5;
    private static final String ROLE_USER = "user";
    private static final Logger log = Logger.getLogger(UserServicesImpl.class);

    private MyDao<User> userDao;
    private MyDao<AccessData> accessDataDao;

    @Autowired
    @Qualifier("userDao")
    public void setUserDao(MyDao<User> userDao) {
        this.userDao = userDao;
    }

    @Autowired
    @Qualifier("accessDataDao")
    public void setAccessDataDao(MyDao<AccessData> accessDataDao) {
        this.accessDataDao = accessDataDao;
    }

    @Override
    public List<User> getAllUsers(Map<String, Object> map, int from, int number, String order) throws ServiceException {
        if(from<0) from = 0;
        if(number<1) number = DEFAULT_NUMBER_VALUE;
        try {
            return userDao.getAll(from, number, null, UtilityServices.getOrders(order));
        } catch (Exception e) {
            log.error("get all users error");
            throw new ServiceException("error.service.getAllUsers");
        }
    }

    @Override
    public User getUser(long id) throws ServiceException {
        try {
            return userDao.read(id);
        } catch (Exception e) {
            log.error("get user error");
            throw new ServiceException("error.service.getUser");
        }
    }

    @Override
    public long register(AccessData accessData) throws ServiceException {
        try{
            accessData.getUser().setRole(ROLE_USER);
            return accessDataDao.create(accessData);
        } catch (Exception e) {
            log.error("register error");
            throw new ServiceException("error.service.register");
        }
    }

    @Override
    public boolean setRole(long id, String role) throws ServiceException {
        User user = new User();
        user.setId(id);
        try{
            user.setRole(role);
            return userDao.update(user) != 0;
        } catch (Exception e) {
            log.error("set role error");
            throw new ServiceException("error.service.setRole");
        }
    }

    @Override
    public int getLastPageNumber(int usersPerPage) throws ServiceException {

        if(usersPerPage<1)
            usersPerPage = DEFAULT_NUMBER_VALUE;
        long number = 0;
        try {
            number = userDao.getCount(null);
        } catch (Exception e) {
            log.info("get count error");
            throw new ServiceException("error.service.userNumberError");
        }

        long lastPage = number/usersPerPage;
        if(number%usersPerPage != 0) lastPage++;
        return (int) lastPage;
    }
}
