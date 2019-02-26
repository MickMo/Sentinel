package com.alibaba.csp.sentinel.dashboard.shiro;

import com.alibaba.csp.sentinel.dashboard.bean.Roles;
import com.alibaba.csp.sentinel.dashboard.bean.User;
import com.alibaba.csp.sentinel.dashboard.service.user.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * <内容说明>
 *
 * @author Monan
 * created on 2/22/2019 4:17 PM
 */
public class MyShiroRealm extends AuthorizingRealm {
    @Autowired
    private UserService userService;
//    @Autowired
//    private UserActionLogDao userActionLogDao;
    private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //UsernamePasswordToken用于存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.findByLoginname(token.getUsername());
        logger.warn("Testing granted login request.");

        return new SimpleAuthenticationInfo();
//        if (user != null) {
//            LOG.info("username:" + user.getUsername());
//            List<Roles> roles = userService.findRolesByUserId(Integer.toString(user.getUserid()));
//            if (roles != null && roles.size() > 0) {
//                user.setRoles(roles.get(0));
//            }
//            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
//            return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
//        }
//        return null;
    }

    /**
     * 权限认证（为当前登录的Subject授予角色和权限）
     * <p>
     * 该方法的调用时机为需授权资源被访问时，并且每次访问需授权资源都会执行该方法中的逻辑，这表明本例中并未启用AuthorizationCache，
     * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），
     * 超过这个时间间隔再刷新页面，该方法会被执行
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

        User user = (User) principals.getPrimaryPrincipal();
        System.out.println(user.getUsername());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        List<Roles> userroles = userService.findRolesByUserId(Integer.toString(user.getUserid()));
        if (userroles != null && userroles.size() > 0) {
            for (Roles r : userroles) {
                info.addRole(r.getRolescode());
            }
            // 返回null将会导致用户访问任何被拦截的请求时都会自动跳转到unauthorizedUrl指定的地址
            return info;
        } else {
            return null;
        }
    }
}
