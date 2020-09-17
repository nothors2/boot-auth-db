package com.example.demo.security;

import com.example.demo.security.filter.MyNewFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyFilterConfig {

    @Bean
    public FilterRegistrationBean<MyNewFilter> registrationBean() {

        FilterRegistrationBean<MyNewFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new MyNewFilter());
        registrationBean.addUrlPatterns("/user/*","/manage/*","/admin/*");

        System.out.println("FilterRegistratio  nBean ............");

        return registrationBean;
    }

//    @Autowired
//    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;
//    @Autowired
//    protected AuthenticationManager authenticationManager;
//
//    @Bean
//    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
//        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
//        filterSecurityInterceptor.setAuthenticationManager(authenticationManager);
//        filterSecurityInterceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
//        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
//
//        System.out.println("FilterSecurityInterceptor  nBean ............");
//        return filterSecurityInterceptor;
//    }
//
//    @Bean
//    public AffirmativeBased affirmativeBased() {
//        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
//        accessDecisionVoters.add(roleVoter());
//        AffirmativeBased affirmativeBased = new AffirmativeBased(accessDecisionVoters);
//        return affirmativeBased;
//    }
//
//    @Bean
//    public RoleHierarchyVoter roleVoter() {
//        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
//        roleHierarchyVoter.setRolePrefix("ROLE_");
//        return roleHierarchyVoter;
//    }
//
//    //RoleHierarchy 설정
//    @Bean
//    public RoleHierarchy roleHierarchy() {
//        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
//        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGE\nROLE_MANAGE > ROLE_USER");
//        return roleHierarchy;
//    }

}
