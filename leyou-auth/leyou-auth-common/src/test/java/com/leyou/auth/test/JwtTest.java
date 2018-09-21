package com.leyou.auth.test;

import com.leyou.auth.pojo.UserInfo;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.auth.utils.RsaUtils;
import org.junit.Before;
import org.junit.Test;

import java.security.PrivateKey;
import java.security.PublicKey;

public class JwtTest {

    private static final String pubKeyPath = "C:\\tem\\rsa\\rsa.pub";

    private static final String priKeyPath = "C:\\tem\\rsa\\rsa.pri";

    private PublicKey publicKey;

    private PrivateKey privateKey;

    @Test
    public void testRsa() throws Exception {
        RsaUtils.generateKey(pubKeyPath, priKeyPath, "234");
    }

    @Before
    public void testGetRsa() throws Exception {
        this.publicKey = RsaUtils.getPublicKey(pubKeyPath);
        this.privateKey = RsaUtils.getPrivateKey(priKeyPath);
    }

    @Test
    public void testGenerateToken() throws Exception {
        // 生成token
        String token = JwtUtils.generateToken(new UserInfo(30L, "xuhao"), privateKey, 5);
        System.out.println("token = " + token);
    }

    @Test
    public void testParseToken() throws Exception {
        String token = "eyJhbGciOiJSUzI1NiJ9.eyJpZCI6MzAsInVzZXJuYW1lIjoieHVoYW8iLCJleHAiOjE1MzcyNzM2NjR9.E71p9m-5j-rFnC0n50Vq-ST7WBgct5qIahTKd_I--GnNdyHpAdF4L7wHZaqXl31uBiypOmKl2PdLkKi3Q7I8d8v0vFuAoPMt7a0hQNXJqf10V-CzmfHbC6spinWy-hbVK-nrGa2amFjoVkLrEdyEV3Lf5Hh7uH4uJcmEDBb-dQs";
        // 解析token
        UserInfo user = JwtUtils.getInfoFromToken(token, publicKey);
        System.out.println("id: " + user.getId());
        System.out.println("userName: " + user.getUsername());
    }
}