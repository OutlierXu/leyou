package com.leyou.user.service;

/**
 * @author XuHao
 * @Title: UserService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1614:00
 */
public interface IUserService {
    Boolean checkUserData(String data, Integer type);

    Boolean sendVerifyCode(String phone);
}
