package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author XuHao
 * @Title: UserAPI
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1810:38
 */
public interface UserAPI {

    /**
     * 查询用户（是否注册）
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public User queryUser(@RequestParam("username")String username, @RequestParam("password")String password);
}
