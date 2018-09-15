package com.leyou.page.service;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author XuHao
 * @Title: IItemHtmlService
 * @ProjectName leyou
 * @Description: 页面静态化业务类
 * @date 2018/9/1511:02
 */
@Service
public interface IItemHtmlService {


    /**
     *创建静态化页面
     * @param id
     */
    void createHtml(Long id);

    /**
     * 线程池执行页面静态化
     * @param id
     *
     */
    void asyncExcute(Long id);

    /**
     * 删除静态页面
     * @param id
     */
    void deleteHtml(Long id);
}
