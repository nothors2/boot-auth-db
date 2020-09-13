package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.RoleHierarchyVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * author:james
 * day:2020-09-13
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    @Autowired
//    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private FilterInvocationSecurityMetadataSource filterInvocationSecurityMetadataSource;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/**/*.js", "/**/*.js.map", "/**/*.ts", "/**/*.css"
                        ,"/**/*.css.map", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.fco"
                        ,"/**/*.woff", "/**/*.woff2", "/**/*.font", "/**/*.svg", "/**/*.ttf"
                        ,  "/**/*.pdf","/*.ico", "/admin/api/**", "/404", "/401","/403", "/error"
                ).permitAll()
                .antMatchers("/","/login","/denied").permitAll()
                .antMatchers("/**").permitAll()
                .and()
                .addFilter(filterSecurityInterceptor())//필터추가할때 위에 일반 예외 antMatcher가 안먹힘 ..??
                ;
        http
                .exceptionHandling().accessDeniedPage("/denied")
                .and()
                .formLogin().permitAll()
                .loginPage("/login")
                .defaultSuccessUrl("/home")
                .and()
                .logout()
                .logoutSuccessUrl("/login").permitAll()
        ;
//        http
//                .authorizeRequests().antMatchers("/**").permitAll()
//                ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

    }

    @Bean//무조건 찾음
    public PasswordEncoder getPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();//non secrit
    }



    //https://newvid.tistory.com/entry/spring-boot-20-%EC%97%90%EC%84%9C-security-%EC%82%AC%EC%9A%A9%EC%8B%9C-AuthenticationManager-Autowired-%EC%95%88%EB%90%A0%EB%95%8C
    //spring boot 1.5 버전에서 잘 되던
    //AuthenticationManager 를 못가져 올 수 있다.
    //WebSecurityConfigurerAdapter 의 authenticationManagerBean 를 오버라이드

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public FilterSecurityInterceptor filterSecurityInterceptor() throws Exception {
        FilterSecurityInterceptor filterSecurityInterceptor = new FilterSecurityInterceptor();
        filterSecurityInterceptor.setAuthenticationManager(authenticationManagerBean());
        filterSecurityInterceptor.setSecurityMetadataSource(filterInvocationSecurityMetadataSource);
        filterSecurityInterceptor.setAccessDecisionManager(affirmativeBased());
        return filterSecurityInterceptor;
    }

    @Bean
    public AffirmativeBased affirmativeBased() {
        List<AccessDecisionVoter<? extends Object>> accessDecisionVoters = new ArrayList<>();
        accessDecisionVoters.add(roleVoter());
        AffirmativeBased affirmativeBased = new AffirmativeBased(accessDecisionVoters);
        return affirmativeBased;
    }

    @Bean
    public RoleHierarchyVoter roleVoter() {
        RoleHierarchyVoter roleHierarchyVoter = new RoleHierarchyVoter(roleHierarchy());
        roleHierarchyVoter.setRolePrefix("ROLE_");
        return roleHierarchyVoter;
    }

    //RoleHierarchy 설정
    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_MANAGE\nROLE_MANAGE > ROLE_USER");
        return roleHierarchy;
    }
}
