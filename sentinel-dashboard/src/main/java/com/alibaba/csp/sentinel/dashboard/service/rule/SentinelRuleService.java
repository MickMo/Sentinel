package com.alibaba.csp.sentinel.dashboard.service.rule;

import com.alibaba.csp.sentinel.dashboard.bean.sentinel.SentinelRule;
import com.alibaba.csp.sentinel.dashboard.dao.SentinelRuleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <内容说明>
 *
 * @author Monan
 * created on 2/22/2019 4:23 PM
 */
@Service
public class SentinelRuleService {

    @Autowired
    private SentinelRuleDao sentinelRuleDao;

    public void add(SentinelRule rule) {
        if (select(rule) != null) {
            return;
        }
        List<SentinelRule> temp = new ArrayList();
        temp.add(rule);
        sentinelRuleDao.add(temp);
    }

    public void add(List<SentinelRule> rules) {
        sentinelRuleDao.add(rules);
    }

    public List<SentinelRule> selectAll() {
        return sentinelRuleDao.seleteAll();
    }


    public int delete(SentinelRule rule) {
        return sentinelRuleDao.delete(rule);
    }

    public int delete(int id) {
        SentinelRule rule = new SentinelRule();
        rule.setId(id);
        return sentinelRuleDao.delete(rule);
    }

    public int modify(SentinelRule rule) {
        return sentinelRuleDao.modify(rule);
    }

    public List<SentinelRule> select(SentinelRule rule) {
        return sentinelRuleDao.select(rule);
    }
}
