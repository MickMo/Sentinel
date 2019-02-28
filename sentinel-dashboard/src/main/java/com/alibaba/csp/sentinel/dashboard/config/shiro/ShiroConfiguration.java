package com.alibaba.csp.sentinel.dashboard.config.shiro;

import com.alibaba.csp.sentinel.dashboard.service.user.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zrhong
 * @date 2017/12/27 17:05
 * @description Shiro配置，相当于SpringMVC中的xml配置
 */
@Configuration
public class ShiroConfiguration {

    private Logger logger = LoggerFactory.getLogger(ShiroConfiguration.class);


    @Bean
    public EhCacheManager ehcacheManager() {
        EhCacheManager ehcacheManager = new EhCacheManager();
        ehcacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
        return ehcacheManager;
    }

    @Bean(name = "myShiroRealm")
    public MyShiroRealm myShiroRealm(EhCacheManager ehCacheManager) {
        MyShiroRealm realm = new MyShiroRealm();
        realm.setCacheManager(ehCacheManager);
        return realm;
    }

    @Bean(name = "lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
        creator.setProxyTargetClass(true);
        return creator;
    }

    @Bean(name = "securityManager")
    public DefaultWebSecurityManager defaultWebSecurityManager(MyShiroRealm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        securityManager.setCacheManager(ehcacheManager());
        return securityManager;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }


    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager, UserService userService, CustomRolesAuthorizationFilter
            roleOrFilter) {
        ShiroFilterFactoryBean factoryBean = new MyShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);
//        factoryBean.setLoginUrl("/login/toLogin");
        factoryBean.setLoginUrl("/app/views/dashboard/login");
        factoryBean.setSuccessUrl("/app/views/dashboard/home");
        factoryBean.setUnauthorizedUrl("/403");
        loadShiroFilterChain(factoryBean, userService);
        Map filterMap = new HashMap();
        filterMap.put("roleOrFilter", roleOrFilter);
        factoryBean.setFilters(filterMap);
        SecurityUtils.setSecurityManager(securityManager);
        return factoryBean;

    }

    @Bean(name = "roleOrFilter")
    public CustomRolesAuthorizationFilter roleOrFilter() {
        CustomRolesAuthorizationFilter filter = new CustomRolesAuthorizationFilter();
        return filter;
    }

    /**
     * 加载ShiroFilter权限控制规则
     */
    private void loadShiroFilterChain(ShiroFilterFactoryBean factoryBean, UserService userService) {
        /**下面这些规则配置最好配置到配置文件中*/
        Map<String, String> filterChainMap = new LinkedHashMap<String, String>();
        filterChainMap.put("/**", "anon");
        filterChainMap.put("/app/views/dashboard/home.html", "anon");
//        filterChainMap.putAll(userService.findAllAuth());

        //filterChainMap.put("/cmpp-manage/logout", "logout");
        factoryBean.setFilterChainDefinitionMap(filterChainMap);
    }

}
