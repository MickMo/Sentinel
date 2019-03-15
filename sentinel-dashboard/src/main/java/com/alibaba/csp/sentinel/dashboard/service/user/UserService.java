package com.alibaba.csp.sentinel.dashboard.service.user;

import com.alibaba.csp.sentinel.dashboard.bean.auth.Roles;
import com.alibaba.csp.sentinel.dashboard.bean.auth.User;
import com.alibaba.csp.sentinel.dashboard.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <内容说明>
 *
 * @author Monan
 * created on 2/22/2019 4:23 PM
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public User findByLoginname(String loginname) {
        return userDao.findByLoginname(loginname);
    }

    public List<Roles> findRolesByUserId(String userid) {
        return userDao.findRolesByUserId(userid);
    }

    public List<Map> findAuthByUserId(String userid) {
        return userDao.findAuthByUserId(userid);
    }

    public Map<String, String> findAllAuth() {
        String authid = "";
        List<Map<String, String>> chainsList = new ArrayList<Map<String, String>>();
        List<Map> authlist = userDao.findAllAuth();
        for (int i = 0; i < authlist.size(); i++) {
            Map<String, String> chainsMap = new HashMap<String, String>();
            Map authMap = authlist.get(i);
            String tmpauthid = (String) authMap.get("AUTHID");
            if (authid.equals(tmpauthid)) {
                chainsMap = chainsList.get(chainsList.size() - 1);
                chainsMap.put("ROLESCODE", chainsMap.get("ROLESCODE") + "," + (String) authMap.get("ROLESCODE"));
            } else {
                authid = tmpauthid;
                chainsMap.put("AUTHURL", (String) authMap.get("AUTHURL"));
                chainsMap.put("ROLESCODE", (String) authMap.get("ROLESCODE"));
                chainsList.add(chainsMap);

            }
        }
        Map<String, String> otherChains = new LinkedHashMap<String, String>();
        for (Map<String, String> map : chainsList) {
            otherChains.put(map.get("AUTHURL"), "authc,roleOrFilter[\"" + map.get("ROLESCODE") + "\"]");
        }

        return otherChains;
    }


}
