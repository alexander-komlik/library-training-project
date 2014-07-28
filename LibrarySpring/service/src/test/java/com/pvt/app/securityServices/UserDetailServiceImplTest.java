package com.pvt.app.securityServices;

import com.pvt.app.dao.MyDao;
import com.pvt.app.daoImpl.AccessDaoImpl;
import com.pvt.app.entity.AccessData;
import com.pvt.app.entity.MyEntity;
import com.pvt.app.entity.User;
import com.pvt.app.util.OrderManager;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.mockito.Mockito.*;

public class UserDetailServiceImplTest {

    private static MyDao<AccessData> accessDao;

    private static List<AccessData> adminList;
    private static List<AccessData> guestList;
    private static List<AccessData> emptyList;
    private static AccessData adminData;
    private static AccessData guestData;
    private static User admin;
    private static User guest;

    private static UserDetailsServiceImpl userDetailsService;

    @BeforeClass
    public static void beforeClass() {

        admin = new User();
        admin.setName("admin");
        admin.setRole("admin");
        adminData = new AccessData();
        adminData.setUser(admin);
        adminData.setPass("pass");

        guest = new User();
        guest.setName("guest");
        guest.setRole("guest");
        guestData = new AccessData();
        guestData.setUser(guest);
        guestData.setPass("pass");

        adminList = new ArrayList<AccessData>();
        guestList = new ArrayList<AccessData>();
        emptyList = new ArrayList<AccessData>();

        adminList.add(adminData);
        guestList.add(guestData);

        accessDao = mock(AccessDaoImpl.class);
        userDetailsService = new UserDetailsServiceImpl();
        userDetailsService.setAccessDataDao(accessDao);
    }

    @Test
    public void testAdmin() {
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(accessDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList())).thenReturn(adminList);
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin@gmail.com");
        Collection<GrantedAuthority> gaCollection = ((org.springframework.security.core.userdetails.User)userDetails).getAuthorities();
        assert (gaCollection.size() == 3);
        assert (((AccessData)(listCaptor.getValue().get(0))).getUser().getEmail().equals("admin@gmail.com"));

    }

    @Test
    public void testGuest() {
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(accessDao.getAll(anyInt(), anyInt(), listCaptor.capture(), anyList())).thenReturn(guestList);
        UserDetails userDetails = userDetailsService.loadUserByUsername("guest@gmail.com");
        Collection<GrantedAuthority> gaCollection = ((org.springframework.security.core.userdetails.User)userDetails).getAuthorities();
        assert (gaCollection.iterator().next().getAuthority().equals("ROLE_ANONYMOUS"));
        assert (((AccessData)(listCaptor.getValue().get(0))).getUser().getEmail().equals("guest@gmail.com"));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void testUserNotFound() {
        ArgumentCaptor<List> listCaptor = ArgumentCaptor.forClass(List.class);
        when(accessDao.getAll(anyInt(), anyInt(), anyList(), anyList())).thenReturn(emptyList);
        userDetailsService.loadUserByUsername("empty");
    }
}
