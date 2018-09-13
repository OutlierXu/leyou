package com.leyou.search.client;

import com.leyou.item.api.SpecificationAPI;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author XuHao
 * @Title: SpecificationClient
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1010:39
 */
@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationAPI {
}
