package com.excilys.formation.cdb.service.impl;

import com.excilys.formation.cdb.login.Privilege;
import com.excilys.formation.cdb.login.User;
import com.excilys.formation.cdb.login.UserRole;
import com.excilys.formation.cdb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private UserService userService;

    @Autowired
    public MyUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserWithName(username);
        if (user == null) {
            return new org.springframework.security.core.userdetails.User(
                    " ",
                    " ",
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(Collections.singletonList(UserRole.USER))
            );
        }

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                getAuthorities(Collections.singletonList(user.getRole()))
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<UserRole> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<UserRole> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();

        for (UserRole role : roles) {
            collection.addAll(privilegeMapper(role));
        }
        for (Privilege privilege : collection) {
            privileges.add(privilege.name());
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }

    private Collection<Privilege> privilegeMapper(UserRole userRole) {
        switch (userRole) {
            case ADMIN:
                return Arrays.asList(Privilege.READ_PRIVILEGE, Privilege.WRITE_PRIVILEGE);
            default:
                return Collections.singletonList(Privilege.READ_PRIVILEGE);
        }
    }
}
