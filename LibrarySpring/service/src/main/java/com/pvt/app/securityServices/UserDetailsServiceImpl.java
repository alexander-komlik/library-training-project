package com.pvt.app.securityServices;

import com.pvt.app.dao.MyDao;
import com.pvt.app.entity.AccessData;
import com.pvt.app.entity.MyEntity;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static Logger log = Logger.getLogger(UserDetailsServiceImpl.class);

    private static GrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority("ROLE_ADMIN");
    private static GrantedAuthority ROLE_MODERATOR = new SimpleGrantedAuthority("ROLE_MODERATOR");
    private static GrantedAuthority ROLE_USER = new SimpleGrantedAuthority("ROLE_USER");
    private static GrantedAuthority ROLE_GUEST = new SimpleGrantedAuthority("ROLE_ANONYMOUS");

    private static String ADMIN = "admin";
    private static String MODERATOR = "moderator";
    private static String USER = "user";
    private static String GUEST = "guest";

    private MyDao<AccessData> accessDataDao;

    @Autowired
    @Qualifier("accessDataDao")
    public void setAccessDataDao(MyDao<AccessData> accessDataDao) {
        this.accessDataDao = accessDataDao;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        AccessData accessData = new AccessData();
        com.pvt.app.entity.User user = new com.pvt.app.entity.User();
        user.setEmail(email);
        accessData.setUser(user);

        List<MyEntity> filters = new ArrayList<MyEntity>();
        filters.add(accessData);
        List<AccessData> adList = accessDataDao.getAll(0, 2, filters, null);

        if(adList.size() == 0) {
            log.debug("Incorrect login/pass");
            throw new UsernameNotFoundException("User not found");
        }
        String role = adList.get(0).getUser().getRole();
        String name = adList.get(0).getUser().getName();
        String pass = adList.get(0).getPass();

        if(adList.size()>1 || role == null || name == null || pass == null) {
            log.info("Dao layer is broken");
            throw new UsernameNotFoundException("Something wrong");
        }

        Collection<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        if(role.equals(ADMIN)) {
            roles.add(ROLE_ADMIN);
            roles.add(ROLE_MODERATOR);
            roles.add(ROLE_USER);
        } else if(role.equals(MODERATOR)) {
            roles.add(ROLE_MODERATOR);
            roles.add(ROLE_USER);
        } else if(role.equals(USER)) {
            roles.add(ROLE_USER);
        } else {
            roles.add(ROLE_GUEST);
        }

        return new User(name, pass, roles);
    }
}
