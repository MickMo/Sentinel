package com.alibaba.csp.sentinel.dashboard.dao;


import com.alibaba.csp.sentinel.dashboard.bean.sentinel.SentinelRule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
@Mapper
public interface SentinelRuleDao {

    int add(List<SentinelRule> rules);

    int delete(SentinelRule rule);

    int modify(SentinelRule rule);

    List<SentinelRule> select(SentinelRule rule);

    List<SentinelRule> seleteAll();

}
