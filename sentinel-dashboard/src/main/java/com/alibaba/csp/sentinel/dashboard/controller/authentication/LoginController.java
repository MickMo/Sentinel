package com.alibaba.csp.sentinel.dashboard.controller.authentication;


import com.alibaba.csp.sentinel.dashboard.bean.User;
import com.alibaba.csp.sentinel.dashboard.bean.login.VerifyCode;
import com.alibaba.csp.sentinel.dashboard.service.user.UserService;
import com.alibaba.csp.sentinel.dashboard.config.shiro.MyShiroRealm;
import com.alibaba.csp.sentinel.dashboard.util.authentic.CipherUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author zrhong
 * @date 2018/1/2 11:38
 * @description 后台登陆
 **/
@Controller
@RequestMapping(value = "cmpp-manage")
@RefreshScope
public class LoginController {
    private final Logger LOG = LoggerFactory.getLogger(LoginController.class);

    @Value("${Login.ValidateVerificationCode:true}")
    private boolean validateVerificationCode;

    @Autowired
    private UserService userService;

    /**
     * 进入登陆页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/login")
    public String login(Model model) {
        return "/#/dashboard/login.html";
    }

    /**
     * 获取验证码
     *
     * @param response
     * @param session  存入验证码
     */
    @RequestMapping(value = "login/verfyCode", method = RequestMethod.GET)
    public void getVerfyCode(HttpServletResponse response, HttpSession session) {
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        VerifyCode verifyCode = new VerifyCode();
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            verifyCode.drawImage(os);//输出图片方法
            String code = verifyCode.getCode();
            session.setAttribute("code", code);
        } catch (Exception e) {
            LOG.error("获取验证码异常", e);
        } finally {
            try {
                if (os != null) {

                    os.close();
                }
            } catch (IOException e) {
                LOG.info("IO异常", e);
            }
        }
    }

    /**
     * 登陆控制
     *
     * @param model
     * @param username  用户名
     * @param password  密码
     * @param validCode 验证码
     * @return 页面
     */
    @RequestMapping(value = "login/toLogin", method = RequestMethod.POST)
    public String doLogin(Model model, HttpSession session, String username, String password, String validCode) {
        //1.判断用户名和密码是否是有效数据
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            //提示用户名或密码不能为空
            LOG.info("LoginController用户名或密码不能为空");
            //转向到登陆页面
            return "redirect:/views/dashboard/login.html";
        }

        try {
            //解密
            username = new String(Base64.decodeBase64(username.replace(" ", "+")), "UTF-8");
            password = new String(Base64.decodeBase64(password.replace(" ", "+")), "UTF-8");
            if (validateVerificationCode) {
                String rightCode = (String) session.getAttribute("code");
                if (StringUtils.isBlank(rightCode) || StringUtils.isBlank(validCode)) {
                    LOG.info("LoginController验证码不能为空");
                    return "view/login/login";
                } else {
                    if (!rightCode.equalsIgnoreCase(validCode)) {
                        LOG.info("LoginController验证码错误");
                        return "view/login/login";
                    }
                }
            }
            //2.获取subject对象     表示"用户"
            Subject subject = SecurityUtils.getSubject();
            //3.将用户名和密码进行包装
            //CipherUtil.md5(password+"cmpp")
            //LOG.info(CipherUtil.md5(password+"cmpp"));
            UsernamePasswordToken token = new UsernamePasswordToken(username, CipherUtil.md5(password + "cmpp"));
            //4捕获异常信息
            //用户登录
            subject.login(token);
            //获取用户信息
            return "redirect:/main";

        } catch (Exception e) {
            LOG.error("用户名或密码错误", e);
            //转向到登陆页面
            return "/views/dashboard/login.html";
        }
    }


    @RequestMapping("403")
    public String unauthorizedRole() {
        return "view/403";
    }


    @RequestMapping("refreshPerm")
    public Object refreshPerm() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        RealmSecurityManager rsm = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        MyShiroRealm shiroRealm = (MyShiroRealm) rsm.getRealms().iterator().next();
        Subject subject = SecurityUtils.getSubject();
        String realmName = subject.getPrincipals().getRealmNames().iterator().next();

        //shiroRealm.clearAllCachedAuthorizationInfo2();//清楚所有用户权限
        //第一个参数为用户名,第二个参数为realmName,test想要操作权限的用户
        SimplePrincipalCollection principals = new SimplePrincipalCollection(user, realmName);
        subject.runAs(principals);
        shiroRealm.getAuthorizationCache().remove(subject.getPrincipals());
        shiroRealm.getAuthorizationCache().remove(user);
        subject.releaseRunAs();
        return "success";
    }
}
