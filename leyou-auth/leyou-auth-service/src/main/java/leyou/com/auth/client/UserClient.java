package leyou.com.auth.client;

import com.leyou.user.api.UserAPI;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author XuHao
 * @Title: UserClient
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1810:35
 */
@FeignClient(value = "user-service")
public interface UserClient extends UserAPI {
}
