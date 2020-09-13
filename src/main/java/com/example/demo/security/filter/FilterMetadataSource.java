package com.example.demo.security.filter;

import com.example.demo.domain.model.Auth;
import com.example.demo.security.service.ResourceMetaService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:james
 * day:2020-09-13
 */
@Component
public class FilterMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {

    @Autowired
    private ResourceMetaService resourceMetaService;



    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();
        AntPathMatcher pathMatcher = new AntPathMatcher();
        String[] anyOnlUrl = new String[]{"/","/login","/**/*.js", "/**/*.js.map", "/**/*.ts", "/**/*.css"
                ,"/**/*.css.map", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.fco"
                ,"/**/*.woff", "/**/*.woff2", "/**/*.font", "/**/*.svg", "/**/*.ttf"
                ,  "/**/*.pdf","/*.ico", "/admin/api/**", "/404", "/401","/403", "/error"};
        List<String> anyUrlList = Arrays.asList(anyOnlUrl);
        for(String pattern : anyUrlList) {
            if (pathMatcher.match(pattern, url)) return null;
        }
//
//        if(url.equals("/login")) return null;
        List<Auth> auths = resourceMetaService.getAuths();
        //System.out.println(auths);

        //String[] roles = new String[] { "ROLE_ADMIN", "ROLE_USER" };
        List<String> roles = new ArrayList<>();
        for(Auth auth : auths){
            if (pathMatcher.match(auth.getUrl(), url)) {
                roles.add(auth.getAuthority());
            }
        }

        System.out.println(roles);
        if(roles.size()<1) return null;

        String[] rolesArray = roles.toArray(new String[roles.size()]);

        return SecurityConfig.createList(rolesArray);
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        resourceMetaService.findAllResources();
    }
}