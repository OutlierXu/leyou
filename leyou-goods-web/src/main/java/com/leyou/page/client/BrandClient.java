package com.leyou.page.client;

import com.leyou.item.api.BrandAPI;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author XuHao
 * @Title: BrandClient
 * @ProjectName leyou
 * @Description: 调用微服务“item-service”的BrandAPI接口
 * @date 2018/9/921:21
 */
@FeignClient(value = "item-service")
public interface BrandClient extends BrandAPI {
}
