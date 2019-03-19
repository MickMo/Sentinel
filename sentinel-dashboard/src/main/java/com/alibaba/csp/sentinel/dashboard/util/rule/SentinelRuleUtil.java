package com.alibaba.csp.sentinel.dashboard.util.rule;

import com.alibaba.csp.sentinel.dashboard.bean.sentinel.SentinelRule;
import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.FlowRuleEntity;
import com.alibaba.csp.sentinel.dashboard.emun.SentinelRuleStatusEnum;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;

/**
 * <内容说明>
 *
 * @author Monan
 * created on 3/12/2019 4:44 PM
 */
public class SentinelRuleUtil {

    public static SentinelRule toSentinelRule(FlowRuleEntity flowRuleEntity) {
        SentinelRule sentinelRule = new SentinelRule();
        sentinelRule.setAppName(flowRuleEntity.getApp());
        sentinelRule.setHost(flowRuleEntity.getIp());
        sentinelRule.setPort(flowRuleEntity.getPort());
        sentinelRule.setRuleContext(JSON.toJSONString(flowRuleEntity));
        sentinelRule.setStatus(SentinelRuleStatusEnum.enable.getValue());
        return sentinelRule;
    }

    public static FlowRuleEntity toFlowRuleEntity(SentinelRule sentinelRule) {
        if (sentinelRule == null || StringUtils.isBlank(sentinelRule.getRuleContext())) {
            return null;
        }
        return JSON.parseObject(sentinelRule.getRuleContext(), FlowRuleEntity.class);
    }
}
