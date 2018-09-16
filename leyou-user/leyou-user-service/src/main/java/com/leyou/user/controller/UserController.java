package com.leyou.user.controller;

import com.leyou.user.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author XuHao
 * @Title: UserController
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1613:59
 */
@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @GetMapping("/check/{data}/{type}")
    public ResponseEntity<Boolean>checkUserData(@PathVariable("data")String data,@PathVariable("type") Integer type){

        Boolean boo = userService.checkUserData(data,type);

        if(boo == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }

    @PostMapping("code/{phone}")
    public ResponseEntity<Boolean> sendVerifyCode(@PathVariable("phone") String phone){

        Boolean boo = userService.sendVerifyCode(phone);

        if(boo == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
    }
}
