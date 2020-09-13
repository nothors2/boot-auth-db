package com.example.demo.security.service;

import com.example.demo.domain.model.Role;
import com.example.demo.domain.model.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * author:james
 * day:2020-09-13
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByUserName(username);
        if(user==null){
            throw new UsernameNotFoundException("no user");
        }
        SecurityUser userDetails = new SecurityUser(user.getId(), username, user.getPassword(), !user.isDisabled(), true, true, true, grantedAuthorities(user.getUsername()), user.getSalt(), user.getEmail());
        return userDetails;
    }

    protected Collection<GrantedAuthority> grantedAuthorities(String userName){
        List<Role> resources=roleRepository.getRoles(userName);
        if(CollectionUtils.isEmpty(resources)){
            return new ArrayList<>();
        }
        Collection<GrantedAuthority> authorities=new HashSet<>();
        //비활성화 된 역할 무시
        resources.stream().filter(role -> !role.isDisabled()).forEach((resource -> {
            authorities.add(new SimpleGrantedAuthority(resource.getName()));
        }));
        return authorities;
    }

}
