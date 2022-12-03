package com.wfh.shopping.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "shopping.jwt")
public class JwtProperties {
    //随机安全验证码
    private String secure = "CHENGDU";
    //签发人
    private String issuer = "wfh";
    //主题
    private String subject = "jwt-token";
    //过期时间
    private long expireTime = 10;
    //aes随机安全验证码
    private String aesSecure = "abcdef0123456789";

    public String getAesSecure() {
        return aesSecure;
    }

    public void setAesSecure(String aesSecure) {
        this.aesSecure = aesSecure;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSecure() {
        return secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }
}
