package com.leyou.config;

import com.leyou.auth.utils.RsaUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author XuHao
 * @Title: JwtProperities
 * @ProjectName leyou
 * @Description: 配置类 读取配置文件并生成公钥
 * @date 2018/9/1810:00
 */
@ConfigurationProperties(prefix = "leyou.jwt")
public class JwtProperties {

    private String cookieName;

    private String pubKeyPath;

    private PublicKey publicKey;


    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    /**
     * 构造方法执行之后执行该方法：根据配置公钥地址，获取公钥
     */
    @PostConstruct
    public void init(){
        try {
            //获取公钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            logger.error("初始化公钥失败！", e);
            throw new RuntimeException();
        }
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
