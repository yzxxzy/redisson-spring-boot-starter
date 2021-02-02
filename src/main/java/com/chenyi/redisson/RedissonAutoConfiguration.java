package com.chenyi.redisson;


import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.SerializationCodec;
import org.redisson.config.*;
import org.redisson.spring.cache.CacheConfig;
import org.redisson.spring.cache.RedissonSpringCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableConfigurationProperties({RedissonProperties.class})
@ConditionalOnClass({RedissonService.class})
public class RedissonAutoConfiguration {

    private static Logger logger = LoggerFactory.getLogger(RedissonAutoConfiguration.class);

    public RedissonAutoConfiguration() {
        System.out.println("\n  ____                   _                   ____                    _                 ____               _   _                                           ____    _             _                 \n / ___|   _ __    _ __  (_)  _ __     __ _  | __ )    ___     ___   | |_              |  _ \\    ___    __| | (_)  ___   ___    ___    _ __               / ___|  | |_    __ _  | |_    ___   _ __ \n \\___ \\  | '_ \\  | '__| | | | '_ \\   / _` | |  _ \\   / _ \\   / _ \\  | __|    _____    | |_) |  / _ \\  / _` | | | / __| / __|  / _ \\  | '_ \\     _____    \\___ \\  | __|  / _` | | __|  / _ \\ | '__|\n  ___) | | |_) | | |    | | | | | | | (_| | | |_) | | (_) | | (_) | | |_    |_____|   |  _ <  |  __/ | (_| | | | \\__ \\ \\__ \\ | (_) | | | | |   |_____|    ___) | | |_  | (_| | | |_  |  __/ | |   \n |____/  | .__/  |_|    |_| |_| |_|  \\__, | |____/   \\___/   \\___/   \\__|             |_| \\_\\  \\___|  \\__,_| |_| |___/ |___/  \\___/  |_| |_|             |____/   \\__|  \\__,_|  \\__|  \\___| |_|   \n         |_|                         |___/                                                                                                                                                        \n                                                         Spring Boot === Redisson === Stater Version:3.15.0  By:Chenyi");
    }

    @Autowired
    private RedissonProperties redissonProperties;

    @Autowired
    private RedissonClient redissonClient;


    @ConditionalOnProperty(
            name = {"redisson.redis.model"},
            havingValue = "0"
    )
    @Bean
    public RedissonClient redissonStandalone() {
        logger.info("===》创建Redisson单机模式");
        logger.info("===》Redisson配置驱动:" + this.redissonProperties.toString());
        Config config = new Config();
        config.setCodec(new SerializationCodec());
        config.useSingleServer().setAddress(this.redissonProperties.getHost()).setPassword(this.redissonProperties.getPassword()[0]);
        config.useSingleServer().setConnectionPoolSize(this.redissonProperties.getConnectionTimeout());
        config.useSingleServer().setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
        config.useSingleServer().setConnectTimeout(this.redissonProperties.getConnectionTimeout());
        config.useSingleServer().setTimeout(this.redissonProperties.getTimeOut());
        //config.useSingleServer().setPingTimeout(this.redissonProperties.getPingTimeOut());
        //config.useSingleServer().setReconnectionTimeout(this.redissonProperties.getReconnectionTimeout());
        return Redisson.create(config);
    }


    @ConditionalOnProperty(
            name = {"redisson.redis.model"},
            havingValue = "1"
    )
    @Bean
    public RedissonClient redissonSentinel() {
        logger.info("===》创建Redisson哨兵模式");
        logger.info("===》Redisson配置驱动:" + this.redissonProperties.toString());
        Config config = new Config();
        config.setCodec(new SerializationCodec());
        SentinelServersConfig sentinelServersConfig = config.useSentinelServers().setMasterName(this.redissonProperties.getSentinelMasterName());

        for(int i = 0; i < this.redissonProperties.getSentinelAddress().length; ++i) {
            sentinelServersConfig.addSentinelAddress(new String[]{this.redissonProperties.getSentinelAddress()[i]}).setPassword(this.redissonProperties.getPassword()[i]);
        }

        config.useSentinelServers().setMasterConnectionPoolSize(this.redissonProperties.getMasterConnectionPoolSize());
        config.useSentinelServers().setSlaveConnectionPoolSize(this.redissonProperties.getSlaveConnectionPoolSize());
        config.useSentinelServers().setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
        config.useSentinelServers().setConnectTimeout(this.redissonProperties.getConnectionTimeout());
        config.useSentinelServers().setTimeout(this.redissonProperties.getTimeOut());
        //config.useSentinelServers().setPingTimeout(this.redissonProperties.getPingTimeOut());
        //config.useSentinelServers().setReconnectionTimeout(this.redissonProperties.getReconnectionTimeout());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @ConditionalOnProperty(
            name = {"redisson.redis.model"},
            havingValue = "2"
    )
    @Bean
    public RedissonClient redissonCluster() {
        logger.info("===》创建Redisson集群模式");
        logger.info("===》Redisson配置驱动:" + this.redissonProperties.toString());
        Config config = new Config();
        config.setCodec(new SerializationCodec());
        ClusterServersConfig clusterServersConfig = config.useClusterServers();
        //集群扫描间隔时间
        clusterServersConfig.setScanInterval(this.redissonProperties.getScanInterval());
        String[] split = this.redissonProperties.getHost().split(",");
        if (StringUtil.isNotBlank(split[0])) {
            this.redissonProperties.setNodeIp(split);
        }
        logger.info("===》Redisson配置IP:" + Arrays.toString(this.redissonProperties.getNodeIp()));

        //添加节点地址
        for(int i = 0; i < this.redissonProperties.getNodeIp().length; ++i) {
            logger.info("地址:" + this.redissonProperties.getNodeIp()[i]);
            clusterServersConfig.addNodeAddress(new String[]{this.redissonProperties.getNodeIp()[i]});
        }

        //密码
        clusterServersConfig.setPassword(this.redissonProperties.getPassword()[0]);
        //主节点连接池大小
        clusterServersConfig.setMasterConnectionPoolSize(this.redissonProperties.getMasterConnectionPoolSize());
        //从节点连接池大小
        clusterServersConfig.setSlaveConnectionPoolSize(this.redissonProperties.getSlaveConnectionPoolSize());
        //连接空闲超时，单位：毫秒
        clusterServersConfig.setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
        //连接超时，单位：毫秒
        clusterServersConfig.setConnectTimeout(this.redissonProperties.getConnectionTimeout());
        //命令等待超时，单位：毫秒
        clusterServersConfig.setTimeout(this.redissonProperties.getTimeOut());
        //config.useClusterServers().setPingTimeout(this.redissonProperties.getPingTimeOut());
        //config.useClusterServers().setReconnectionTimeout(this.redissonProperties.getReconnectionTimeout());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @ConditionalOnProperty(
            name = {"redisson.redis.model"},
            havingValue = "3"
    )
    @Bean
    public RedissonClient redissonMasterAndSlave() {
        logger.info("===》创建Redisson主从模式");
        logger.info("===》Redisson配置驱动:" + this.redissonProperties.toString());
        Config config = new Config();
        config.setCodec(new SerializationCodec());
        MasterSlaveServersConfig masterSlaveServersConfig = config.useMasterSlaveServers().setMasterAddress(this.redissonProperties.getMaster());

        for(int i = 0; i < this.redissonProperties.getSalve().length; ++i) {
            masterSlaveServersConfig.addSlaveAddress(new String[]{this.redissonProperties.getSalve()[i]}).setPassword(this.redissonProperties.getPassword()[i]);
        }

        config.useMasterSlaveServers().setMasterConnectionPoolSize(this.redissonProperties.getMasterConnectionPoolSize());
        config.useMasterSlaveServers().setSlaveConnectionPoolSize(this.redissonProperties.getSlaveConnectionPoolSize());
        config.useMasterSlaveServers().setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
        config.useMasterSlaveServers().setConnectTimeout(this.redissonProperties.getConnectionTimeout());
        config.useMasterSlaveServers().setTimeout(this.redissonProperties.getTimeOut());
        //config.useMasterSlaveServers().setPingTimeout(this.redissonProperties.getPingTimeOut());
        //config.useMasterSlaveServers().setReconnectionTimeout(this.redissonProperties.getReconnectionTimeout());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @ConditionalOnProperty(
            name = {"redisson.redis.model"},
            havingValue = "4"
    )
    @Bean
    public RedissonClient redissonClusterCloud() {
        logger.info("===》创建Redisson云部署模式");
        logger.info("===》Redisson配置驱动:" + this.redissonProperties.toString());
        Config config = new Config();
        config.setCodec(new SerializationCodec());
        ReplicatedServersConfig replicatedServersConfig = config.useReplicatedServers().setScanInterval(this.redissonProperties.getScanInterval());

        for(int i = 0; i < this.redissonProperties.getNodeIp().length; ++i) {
            replicatedServersConfig.addNodeAddress(this.redissonProperties.getNodeIp());
        }

        config.useClusterServers().setMasterConnectionPoolSize(this.redissonProperties.getMasterConnectionPoolSize());
        config.useClusterServers().setSlaveConnectionPoolSize(this.redissonProperties.getSlaveConnectionPoolSize());
        config.useClusterServers().setIdleConnectionTimeout(this.redissonProperties.getIdleConnectionTimeout());
        config.useClusterServers().setConnectTimeout(this.redissonProperties.getConnectionTimeout());
        config.useClusterServers().setTimeout(this.redissonProperties.getTimeOut());
        //config.useClusterServers().setPingTimeout(this.redissonProperties.getPingTimeOut());
        //config.useClusterServers().setReconnectionTimeout(this.redissonProperties.getReconnectionTimeout());
        RedissonClient redisson = Redisson.create(config);
        return redisson;
    }

    @Bean
    @ConditionalOnProperty(
            name = {"redisson.cache.model"},
            havingValue = "1"
    )
    public CacheManager RedisCacheManager(RedissonClient redissonClient) throws WebCachTimeNull {
        logger.info("创建由Redisson集成的SpringCache::::::CacheManager");
        Map<String, CacheConfig> config = new HashMap<>();
        if (this.redissonProperties.getWebCachMaxIdleTime() != null && !"".equals(this.redissonProperties.getWebCachMaxIdleTime()) && !(this.redissonProperties.getWebCachTtl() == null | "".equals(this.redissonProperties.getWebCachTtl()))) {
            config.put(this.redissonProperties.getWebCachKey(), new CacheConfig((long)(this.redissonProperties.getWebCachTtl() * 60 * 1000), (long)(this.redissonProperties.getWebCachMaxIdleTime() * 60 * 1000)));
            return new RedissonSpringCacheManager(redissonClient, config);
        } else {
            throw new WebCachTimeNull("过期时间/最长空闲时间未指定");
        }
    }



    @Bean
    public RedissonService createRedissonService(RedissonClient redissonClient) {
        RedissonService redissonService = new RedissonService(redissonClient);
        logger.info("redissonService:" + redissonService.toString());
        return redissonService;
    }

    @Bean(name = "redissonClient")
    public RedissonClient redissonClient() {
        return redissonClient;
    }


}
