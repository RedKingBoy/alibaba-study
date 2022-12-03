package com.wfh.shopping.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "gateway.dynamic.route")
public class DynamicRouteProperties {
    private String serverAddr;

    private String nameSpace;

    private String dataId;

    private String group;
}
