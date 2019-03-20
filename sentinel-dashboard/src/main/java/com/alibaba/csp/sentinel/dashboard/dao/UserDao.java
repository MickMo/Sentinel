package com.alibaba.csp.sentinel.dashboard.dao;


import com.alibaba.csp.sentinel.dashboard.bean.auth.Roles;
import com.alibaba.csp.sentinel.dashboard.bean.auth.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
@Mapper
public interface UserDao {

    User findByLoginname(@Param("loginname") String loginname);

    List<Roles> findRolesByUserId(@Param("userid") String userid);

    List<Map> findAuthByUserId(@Param("userid") String userid);

    List<Map> findAllAuth();

}