package com.chenyi.redisson;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Arrays;

@ConfigurationProperties(
        prefix = "redisson.redis"
)
public class RedissonProperties {

    private Logger logger = LoggerFactory.getLogger(RedissonProperties.class);


    private String model;
    private String host;
    private String sentinelMasterName = "my-sentinel-name";
    private String[] SentinelAddress;
    private String master;
    private String[] salve;
    private String[] nodeIp;
    private Integer poolSize = 500;
    private Integer idleConnectionTimeout = 1000;
    private Integer connectionTimeout = 60000;
    private Integer timeOut = 3000;
    private Integer pingTimeOut = 30000;
    private Integer reconnectionTimeout = 3000;
    private Integer masterConnectionPoolSize = 500;
    private Integer slaveConnectionPoolSize = 500;
    private Integer scanInterval = 2000;
    private String webCachKey = "webCach";
    private Integer webCachTtl;
    private Integer webCachMaxIdleTime;
    private String[] password;

    public Integer getMasterConnectionPoolSize() {
        return this.masterConnectionPoolSize;
    }

    public void setMasterConnectionPoolSize(Integer masterConnectionPoolSize) {
        this.masterConnectionPoolSize = masterConnectionPoolSize;
    }

    public Integer getSlaveConnectionPoolSize() {
        return this.slaveConnectionPoolSize;
    }

    public void setSlaveConnectionPoolSize(Integer slaveConnectionPoolSize) {
        this.slaveConnectionPoolSize = slaveConnectionPoolSize;
    }

    public Integer getPoolSize() {
        return this.poolSize;
    }

    public void setPoolSize(Integer poolSize) {
        this.poolSize = poolSize;
    }

    public Integer getIdleConnectionTimeout() {
        return this.idleConnectionTimeout;
    }

    public void setIdleConnectionTimeout(Integer idleConnectionTimeout) {
        this.idleConnectionTimeout = idleConnectionTimeout;
    }

    public Integer getConnectionTimeout() {
        return this.connectionTimeout;
    }

    public void setConnectionTimeout(Integer connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public Integer getTimeOut() {
        return this.timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getPingTimeOut() {
        return this.pingTimeOut;
    }

    public void setPingTimeOut(Integer pingTimeOut) {
        this.pingTimeOut = pingTimeOut;
    }

    public Integer getReconnectionTimeout() {
        return this.reconnectionTimeout;
    }

    public void setReconnectionTimeout(Integer reconnectionTimeout) {
        this.reconnectionTimeout = reconnectionTimeout;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getModel() {
        return this.model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSentinelMasterName() {
        return this.sentinelMasterName;
    }

    public void setSentinelMasterName(String sentinelMasterName) {
        this.sentinelMasterName = sentinelMasterName;
    }

    public String[] getSentinelAddress() {
        return this.SentinelAddress;
    }

    public void setSentinelAddress(String[] sentinelAddress) {
        this.SentinelAddress = sentinelAddress;
    }

    public String getMaster() {
        return this.master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String[] getSalve() {
        return this.salve;
    }

    public void setSalve(String[] salve) {
        this.salve = salve;
    }

    public String[] getNodeIp() {
        return this.nodeIp;
    }

    public void setNodeIp(String[] nodeIp) {
        this.nodeIp = nodeIp;
    }

    public Integer getScanInterval() {
        return this.scanInterval;
    }

    public void setScanInterval(Integer scanInterval) {
        this.scanInterval = scanInterval;
    }

    public String getWebCachKey() {
        return this.webCachKey;
    }

    public void setWebCachKey(String webCachKey) {
        this.webCachKey = webCachKey;
    }

    public Integer getWebCachTtl() {
        return this.webCachTtl;
    }

    public void setWebCachTtl(Integer webCachTtl) {
        this.webCachTtl = webCachTtl;
    }

    public Integer getWebCachMaxIdleTime() {
        return this.webCachMaxIdleTime;
    }

    public void setWebCachMaxIdleTime(Integer webCachMaxIdleTime) {
        this.webCachMaxIdleTime = webCachMaxIdleTime;
    }

    public String[] getPassword() {
        return this.password;
    }

    public void setPassword(String[] password) {
        this.password = password;
    }

    public String toString() {
        return "RedissonProperties{model='" + this.model + '\'' + ", host='" + this.host + '\'' + ", sentinelMasterName='" + this.sentinelMasterName + '\'' + ", SentinelAddress=" + Arrays.toString(this.SentinelAddress) + ", master='" + this.master + '\'' + ", salve=" + Arrays.toString(this.salve) + ", nodeIp=" + Arrays.toString(this.nodeIp) + ", poolSize=" + this.poolSize + ", idleConnectionTimeout=" + this.idleConnectionTimeout + ", connectionTimeout=" + this.connectionTimeout + ", timeOut=" + this.timeOut + ", pingTimeOut=" + this.pingTimeOut + ", reconnectionTimeout=" + this.reconnectionTimeout + ", masterConnectionPoolSize=" + this.masterConnectionPoolSize + ", slaveConnectionPoolSize=" + this.slaveConnectionPoolSize + ", scanInterval=" + this.scanInterval + ", webCachKey='" + this.webCachKey + '\'' + ", webCachTtl='" + this.webCachTtl + '\'' + ", webCachMaxIdleTime='" + this.webCachMaxIdleTime + '\'' + '}';
    }

    public RedissonProperties() {
        this.logger.info("===》Redisson配置文件对象创建");
    }

}
