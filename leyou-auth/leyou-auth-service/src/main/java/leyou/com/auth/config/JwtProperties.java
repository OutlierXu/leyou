package leyou.com.auth.config;

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
 * @Description: 配置类 读取配置文件并生成公钥、私钥
 * @date 2018/9/1810:00
 */
@ConfigurationProperties(prefix = "leyou.jwt")
public class JwtProperties {

    //登录校验的密钥
    private String secret;
    //公钥地址
    private String pubKeyPath;
    //私钥地址
    private String priKeyPath;
    //过期时间,单位分钟
    private Integer expire;

    private String cookieName;

    private Integer cookieMaxAge;

    private PublicKey publicKey;

    private PrivateKey privateKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtProperties.class);

    /**
     * 构造方法执行之后执行该方法：生成公钥和私钥
     */
    @PostConstruct
    public void init(){

        try {
            File pubKey = new File(pubKeyPath);
            File priKey = new File(priKeyPath);

            if(!pubKey.exists() || !priKey.exists()){
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }

            //获取公钥和私钥
            this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
            this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
        } catch (Exception e) {
            logger.error("初始化公钥和私钥失败！", e);
            throw new RuntimeException();
        }
    }

    public String getCookieName() {
        return cookieName;
    }

    public void setCookieName(String cookieName) {
        this.cookieName = cookieName;
    }

    public Integer getCookieMaxAge() {
        return cookieMaxAge;
    }

    public void setCookieMaxAge(Integer cookieMaxAge) {
        this.cookieMaxAge = cookieMaxAge;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getPubKeyPath() {
        return pubKeyPath;
    }

    public void setPubKeyPath(String pubKeyPath) {
        this.pubKeyPath = pubKeyPath;
    }

    public String getPriKeyPath() {
        return priKeyPath;
    }

    public void setPriKeyPath(String priKeyPath) {
        this.priKeyPath = priKeyPath;
    }

    public Integer getExpire() {
        return expire;
    }

    public void setExpire(Integer expire) {
        this.expire = expire;
    }

    public static Logger getLogger() {
        return logger;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }
}
