package com.wfh.shopping.config;

import com.wfh.shopping.FdfsService;
import com.wfh.shopping.properties.FdfsProperties;
import org.csource.common.FdfsException;
import org.csource.fastdfs.ClientGlobal;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableConfigurationProperties(FdfsProperties.class)
@ConditionalOnClass(FdfsService.class)
public class FdfsConfig {
    @Bean
    @ConditionalOnMissingBean
    public FdfsService fdfsService(FdfsProperties config) throws IOException, FdfsException {
        FdfsService fdfsService = new FdfsService();
        Properties props = new Properties();
        props.setProperty(ClientGlobal.PROP_KEY_CONNECT_TIMEOUT_IN_SECONDS, Integer.toString(config.getConnectionTimeout()));
        props.setProperty(ClientGlobal.PROP_KEY_NETWORK_TIMEOUT_IN_SECONDS, Integer.toString(config.getNetworkTimeout()));
        props.setProperty(ClientGlobal.PROP_KEY_CHARSET, config.getCharset());
        props.setProperty(ClientGlobal.PROP_KEY_HTTP_ANTI_STEAL_TOKEN, Boolean.toString(config.isHttpAntiStealToken()));
        props.setProperty(ClientGlobal.PROP_KEY_HTTP_SECRET_KEY, config.getHttpSecretKey());
        props.setProperty(ClientGlobal.PROP_KEY_HTTP_TRACKER_HTTP_PORT, Integer.toString(config.getHttpTrackerHttpPort()));
        props.setProperty(ClientGlobal.PROP_KEY_TRACKER_SERVERS, config.getTrackerServers());
        FdfsProperties.ConnectionPool connectionPool = config.getConnectionPool();
        props.setProperty(ClientGlobal.PROP_KEY_CONNECTION_POOL_ENABLED, Boolean.toString(connectionPool.isEnabled()));
        props.setProperty(ClientGlobal.PROP_KEY_CONNECTION_POOL_MAX_COUNT_PER_ENTRY, Integer.toString(connectionPool.getMaxCountPerEntry()));
        props.setProperty(ClientGlobal.PROP_KEY_CONNECTION_POOL_MAX_IDLE_TIME, Integer.toString(connectionPool.getMaxIdleTime()));
        props.setProperty(ClientGlobal.PROP_KEY_CONNECTION_POOL_MAX_WAIT_TIME_IN_MS, Long.toString(connectionPool.getMaxWaitTimeInMs()));
        ClientGlobal.initByProperties(props);
        return fdfsService;
    }
}
