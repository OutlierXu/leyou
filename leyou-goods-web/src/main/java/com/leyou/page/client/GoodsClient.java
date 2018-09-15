package com.leyou.page.client;

import com.leyou.item.api.GoodsAPI;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author XuHao
 * @Title: GoodsClient
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1010:37
 */
@FeignClient(value = "item-service")
public interface GoodsClient extends GoodsAPI {
}
