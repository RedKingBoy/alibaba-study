package com.wfh.shopping;

import com.wfh.shopping.properties.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

public class JwtService {
    //公钥
    private PublicKey publicKey;
    //私钥
    private PrivateKey privateKey;
    //配置属性
    private JwtProperties properties;

    public JwtService() {
    }

    public JwtService(JwtProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void init() throws NoSuchAlgorithmException {
        //rsa加密
        KeyPairGenerator rsa = KeyPairGenerator.getInstance("RSA");
        rsa.initialize(1024,new SecureRandom(properties.getSecure().getBytes()));
        KeyPair keyPair = rsa.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }
    public String getToken(Map<String,Object> infos){
        long timeMillis = System.currentTimeMillis();
        long expire = properties.getExpireTime() * 60 * 1000 + timeMillis;
        return Jwts.builder().setIssuer(properties.getIssuer())
                .setIssuedAt(new Date(timeMillis))
                .setSubject("token")
                .setExpiration(new Date(expire)).addClaims(infos)
                .signWith(SignatureAlgorithm.RS256,privateKey).compact();
    }

    public Claims parseToken(String token){
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token).getBody();
    }
    public String aesEncrypt(String str) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        //aes对称加密专用秘钥
        SecretKeySpec secretKeySpec = new SecretKeySpec(properties.getAesSecure().getBytes(),"AES");
        //获取密码器
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //密码器初始化,开启加密模式,并注入秘钥
        cipher.init(Cipher.ENCRYPT_MODE,secretKeySpec);
        //对字节数组加密
        byte[] bytes = cipher.doFinal(str.getBytes(StandardCharsets.UTF_8));
        //基于base64加密算法还原成字符串
        return Base64.getEncoder().encodeToString(bytes);
    }

    public String aesDecrypt(String str) throws Exception{
        //对于加密过的先基于base64解密成字节数组
        byte[] decode = Base64.getDecoder().decode(str);
        //aes对称加密专用秘钥
        SecretKeySpec secretKeySpec = new SecretKeySpec(properties.getAesSecure().getBytes(),"AES");
        //获取密码器
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        //密码器初始化,开启加密模式,并注入秘钥
        cipher.init(Cipher.DECRYPT_MODE,secretKeySpec);
        byte[] bytes = cipher.doFinal(decode);
        return new String(bytes,StandardCharsets.UTF_8);
    }
}
