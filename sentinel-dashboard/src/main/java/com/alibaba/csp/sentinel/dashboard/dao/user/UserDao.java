package com.alibaba.csp.sentinel.dashboard.dao.user;


import com.alibaba.csp.sentinel.dashboard.bean.Roles;
import com.alibaba.csp.sentinel.dashboard.bean.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
@Repository
public interface UserDao {

    User findByLoginname(@Param("loginname") String loginname);

    List<Roles> findRolesByUserId(@Param("userid") String userid);

    List<Map> findAuthByUserId(@Param("userid") String userid);

    List<Map> findAllAuth();

}
