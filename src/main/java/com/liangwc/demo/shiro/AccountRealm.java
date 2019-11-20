package com.liangwc.demo.shiro;

import com.liangwc.demo.base.lang.Constants;
import com.liangwc.demo.dao.UserMapper;
import com.liangwc.demo.domain.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author liangweicheng
 * @date 2019/11/19
 */
public class AccountRealm extends AuthorizingRealm {
    @Autowired
    UserMapper userMapper;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        String username = token.getUsername();
        User user = userMapper.selectByUserName(username);
        if (null == user) {
            throw new UnknownAccountException(token.getUsername());
        }
        if (user.getStatus() == Constants.STATUS_CLOSED) {
            throw new LockedAccountException(user.getName());
        }
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, token.getCredentials(), getName());
        Session session = SecurityUtils.getSubject().getSession();
        session.setAttribute("user", user);
        return info;
    }
}
