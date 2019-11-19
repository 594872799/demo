package com.liangwc.demo.config;

import com.liangwc.demo.shiro.AccountRealm;
import org.apache.shiro.realm.Realm;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
}
