package com.alibaba.csp.sentinel.dashboard.bean.sentinel;

import com.alibaba.csp.sentinel.dashboard.emun.SentinelRuleStatusEnum;

import java.util.Date;

/**
 * Sentinel Rule Bean
 *
 * @author Monan
 * created on 3/12/2019 2:36 PM
 */
public class SentinelRule {
    private int id;
    private String ruleContext;
    private Date createDate;
    private Date modifiedDate;

    private String ip;
    private int port;
    private String appName;

    private String version;

    /**
     * @see SentinelRuleStatusEnum
     */
    private int status;

    /**
     * 0:effective
     * 1:deleted
     */
    private int deleteFlag;

    public boolean isDelete(){
        return deleteFlag == 1;
    }

    public void deleteRule(){
        deleteFlag=1;
    }

    public void reEffective(){
        deleteFlag=1;
    }
    /**
     * Sets new appName.
     *
     * @param appName New value of appName.
     */
    public void setAppName(String appName) {
        this.appName = appName;
    }

    /**
     * Gets port.
     *
     * @return Value of port.
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets ip.
     *
     * @return Value of ip.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets new ip.
     *
     * @param ip New value of ip.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Gets createDate.
     *
     * @return Value of createDate.
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * Gets version.
     *
     * @return Value of version.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets new id.
     *
     * @param id New value of id.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets new version.
     *
     * @param version New value of version.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets appName.
     *
     * @return Value of appName.
     */
    public String getAppName() {
        return appName;
    }

    /**
     * Sets new createDate.
     *
     * @param createDate New value of createDate.
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * Gets ruleContext.
     *
     * @return Value of ruleContext.
     */
    public String getRuleContext() {
        return ruleContext;
    }

    /**
     * Gets id.
     *
     * @return Value of id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets new modifiedDate.
     *
     * @param modifiedDate New value of modifiedDate.
     */
    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    /**
     * Sets new ruleContext.
     *
     * @param ruleContext New value of ruleContext.
     */
    public void setRuleContext(String ruleContext) {
        this.ruleContext = ruleContext;
    }

    /**
     * Sets new port.
     *
     * @param port New value of port.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Gets modifiedDate.
     *
     * @return Value of modifiedDate.
     */
    public Date getModifiedDate() {
        return modifiedDate;
    }


    /**
     * Sets new @see SentinelRuleStatusEnum.
     *
     * @param status New value of @see SentinelRuleStatusEnum.
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets @see SentinelRuleStatusEnum.
     *
     * @return Value of @see SentinelRuleStatusEnum.
     */
    public int getStatus() {
        return status;
    }
}
