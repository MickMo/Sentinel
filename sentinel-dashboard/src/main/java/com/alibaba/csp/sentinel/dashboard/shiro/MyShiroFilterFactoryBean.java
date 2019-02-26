package com.alibaba.csp.sentinel.dashboard.shiro;

import com.alibaba.csp.sentinel.dashboard.service.user.UserService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author zrhong
 * @date 2017/12/27 17:20
 * @description 继承ShiroFilterFactoryBean处理拦截资源文件问题
 */
public class MyShiroFilterFactoryBean extends ShiroFilterFactoryBean {
    // ShiroFilter将直接忽略的请求
    private Set<String> ignoreExt;

    @Autowired
    private UserService userService;

    /**
     * 记录配置中的过滤链
     */
    public static String filterChainDefinitions = "";//这个要和配置文件中的名称要一样

    public MyShiroFilterFactoryBean() {
        super();
        ignoreExt = new HashSet<String>();
        ignoreExt.add(".jpg");
        ignoreExt.add(".png");
        ignoreExt.add(".gif");
        ignoreExt.add(".bmp");
        ignoreExt.add(".js");
        ignoreExt.add(".css");
    }

    @Override
    protected AbstractShiroFilter createInstance() throws Exception {
        SecurityManager securityManager = getSecurityManager();
        if (securityManager == null) {
            throw new BeanInitializationException("SecurityManager property must be set.");
        }

        if (!(securityManager instanceof WebSecurityManager)) {
            throw new BeanInitializationException("The security manager does not implement the WebSecurityManager interface.");
        }

        PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver();
        FilterChainManager chainManager = createFilterChainManager();
        chainResolver.setFilterChainManager(chainManager);
        return new MySpringShiroFilter((WebSecurityManager) securityManager, chainResolver);
    }


    /**
     * 初始化设置过滤链
     */
//	@Override
//	public void setFilterChainDefinitions(String definitions) {
//		filterChainDefinitions = definitions;// 记录配置的静态过滤链
//		Map<String, String> otherChains = new HashMap<String, String>();
////		List<Resource> list = (List) resourceDao.findAll();
////		for (Resource resource : list) {
////			if (StringUtils.isNotBlank(resource.getValue()) && StringUtils.isNotBlank(resource.getPermission())) {
////				otherChains.put(resource.getValue(), MessageFormat.format(PREMISSION_STRING, resource.getPermission()));
////			}
////		}
//
//
//
////		otherChains.put("/cmpp-manage/order/reopen", "roles[\"admin\"]");
//		// 加载配置默认的过滤链
//		Ini ini = new Ini();
//		ini.load(filterChainDefinitions);
//		Ini.Section section = ini.getSection(IniFilterChainResolverFactory.URLS);
//		if (CollectionUtils.isEmpty(section)) {
//			section = ini.getSection(Ini.DEFAULT_SECTION_NAME);
//		}
//		// 加上数据库中过滤链
//		if(otherChains!= null&& otherChains.size() > 0){
//			section.putAll(otherChains);
//		}
//		section.put("/**", "anon");
//		setFilterChainDefinitionMap(section);
//
//	}


    private class MySpringShiroFilter extends AbstractShiroFilter {
        public MySpringShiroFilter(
                WebSecurityManager securityManager, PathMatchingFilterChainResolver chainResolver) {
            super();
            if (securityManager == null) {
                throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
            }
            setSecurityManager(securityManager);
            if (chainResolver != null) {
                setFilterChainResolver(chainResolver);
            }
        }

        @Override
        protected void doFilterInternal(ServletRequest servletRequest, ServletResponse servletResponse,
                                        FilterChain chain)
                throws ServletException, IOException {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            String str = request.getRequestURI().toLowerCase();
            boolean flag = true;
            int idx = 0;
            if ((idx = str.indexOf(".")) > 0) {
                str = str.substring(idx);
                if (ignoreExt.contains(str.toLowerCase())) {
                    flag = false;
                }
            }
            if (flag) {
                super.doFilterInternal(servletRequest, servletResponse, chain);
            } else {
                chain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
