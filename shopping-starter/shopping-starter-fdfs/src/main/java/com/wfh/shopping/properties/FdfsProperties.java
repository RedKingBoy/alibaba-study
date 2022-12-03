package com.wfh.shopping.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "shopping.fdfs")
public class FdfsProperties {
    /**
     * 连接超时时间(s)
     */
    private int connectionTimeout = 5;
    /**
     * 网络超时时间(s)
     */
    private int networkTimeout = 30;
    /**
     * 字符集
     */
    private String charset = "UTF-8";

    private boolean httpAntiStealToken = false;
    /**
     * 安全密钥
     */
    private String httpSecretKey = "FastDFS1234567890";
    /**
     * tracker的http通信端口
     */
    private int httpTrackerHttpPort = 80;
    /**
     * tracker服务器列表，多个服务器之间使用逗号隔开
     */
    private String trackerServers;

    private ConnectionPool connectionPool = new ConnectionPool();

    @Data
    public
    class ConnectionPool {
        /**
         * 是否启用连接池
         */
        private boolean enabled = true;
        /**
         * 每个主机的最大连接数
         */
        private int maxCountPerEntry = 500;
        /**
         * 最大空闲时间(s)
         */
        private int maxIdleTime = 3600;
        /**
         * 最大等待时间(ms)
         */
        private long maxWaitTimeInMs = 1000;
    }
}
