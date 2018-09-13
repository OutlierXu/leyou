package com.leyou.search.client;

import com.leyou.item.api.CategoryAPI;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author XuHao
 * @Title: CategoryClient
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1010:38
 */
@FeignClient(value = "item-service")
public interface CategoryClient extends CategoryAPI {
}
