package com.alibaba.csp.sentinel.dashboard.emun;

/**
 * <内容说明>
 *
 * @author Monan
 * created on 3/12/2019 2:47 PM
 */
public enum SentinelRuleStatusEnum {

    enable(1),
    disable(11);

    private int status;

    /**
     * 构造器
     *
     * @param desc 描述
     */
    SentinelRuleStatusEnum(int desc) {
        this.status = desc;
    }

    /**
     * 获取描述
     *
     * @return 描述
     */
    public int getValue() {
        return this.status;
    }

}
