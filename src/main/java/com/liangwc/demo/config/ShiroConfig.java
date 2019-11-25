package com.liangwc.demo.config;

import com.liangwc.demo.shiro.AccountRealm;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liangweicheng
 * @date 2019/11/19
 */
@Configuration
public class ShiroConfig {
    @Bean
    public Realm accountRealm() {
        return new AccountRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(accountRealm());
        return securityManager;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        shiroFilter.setLoginUrl("/login");
        shiroFilter.setSuccessUrl("/");
        shiroFilter.setUnauthorizedUrl("/error/reject.html");

        Map<String, String> hashMap = new LinkedHashMap<>();
        hashMap.put("/dist/**", "anon");
        hashMap.put("/error/**", "anon");
        hashMap.put("/login", "anon");
        hashMap.put("/register", "anon");
        hashMap.put("/", "anon");
        // 其他路径均需要身份认证，一般位于最下面，优先级最低
        hashMap.put("/**", "authc");

        shiroFilter.setFilterChainDefinitionMap(hashMap);
        return shiroFilter;
    }
}
