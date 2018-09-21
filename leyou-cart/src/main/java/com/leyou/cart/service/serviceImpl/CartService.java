package com.leyou.cart.service.serviceImpl;

import com.leyou.auth.pojo.UserInfo;
import com.leyou.cart.client.GoodsClient;
import com.leyou.cart.interceptor.LoginInterceptor;
import com.leyou.cart.pojo.Cart;
import com.leyou.cart.service.ICartService;
import com.leyou.common.utils.JsonUtils;
import com.leyou.item.pojo.Sku;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author XuHao
 * @Title: CartService
 * @ProjectName leyou
 * @Description: TODO
 * @date 2018/9/1921:28
 */
@Service
public class CartService implements ICartService {

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private StringRedisTemplate redisTemplate;


    private static final String KEY_PREFIX = "ly:cart:uid:";

    private static final Logger logger = LoggerFactory.getLogger(CartService.class);

    @Override
    public Boolean addCart(Cart cart) {
        Long skuId = cart.getSkuId();
        Integer num = cart.getNum();

        try {
            //1.获取当前登录用户信息
            UserInfo loginUser = LoginInterceptor.getLoginUser();

            //2.查询redis缓存中是否含有当前用户的购物车 缓存中的数据类型Map<"userId",Map<"skuId",object>>
            String key = KEY_PREFIX + loginUser.getId();
            BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

            Boolean hasKey = hashOps.hasKey(skuId.toString());
            if (hasKey){
                String cartInfo = hashOps.get(skuId.toString()).toString();
                cart = JsonUtils.parse(cartInfo, Cart.class);
                cart.setNum(cart.getNum() + num);
            }else {
                //3.根据skuId查询商品详情
                Sku sku = this.goodsClient.querySkuBySkuId(skuId);
                BeanUtils.copyProperties(sku, cart);

                cart.setUserId(loginUser.getId());
                cart.setImage(StringUtils.isBlank(sku.getImages())?"":StringUtils.split(sku.getImages(), ",")[0]);
            }

            //4.将cart值保存到缓存中Map<"userId",Map<"skuId",object>>
            hashOps.put(skuId.toString(), JsonUtils.serialize(cart));
            return true;

        } catch (BeansException e) {
            logger.error("保存redis购物车失败！", e);
            return false;
        }
    }

    private BoundHashOperations<String, Object, Object> getHashOperations() {
        //1.获取当前登录用户信息
        UserInfo loginUser = LoginInterceptor.getLoginUser();
        //2.查询redis缓存中是否含有当前用户的购物车 缓存中的数据类型Map<"userId",Map<"skuId",object>>
        String key = KEY_PREFIX + loginUser.getId();
        if(this.redisTemplate.hasKey(key)){
            return this.redisTemplate.boundHashOps(key);
        }
        return null;
    }

    @Override
    public List<Cart> queryCartList() {
        //1.获取当前登录用户信息
        UserInfo loginUser = LoginInterceptor.getLoginUser();
        //2.查询redis缓存中是否含有当前用户的购物车 缓存中的数据类型Map<"userId",Map<"skuId",object>>
        String key = KEY_PREFIX + loginUser.getId();

        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        List<Object> carts = hashOps.values();
        if(CollectionUtils.isEmpty(carts)){
            return null;
        }

        // 查询购物车数据
        return carts.stream().map(o->JsonUtils.parse(o.toString(), Cart.class)).collect(Collectors.toList());

    }

    @Override
    public void deleteCart(Long skuId) {
        //1.获取当前登录用户
        UserInfo loginUser = LoginInterceptor.getLoginUser();

        String key = KEY_PREFIX + loginUser.getId();
        BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

        if(hashOps.hasKey(skuId.toString())){
            hashOps.delete(skuId.toString());
        }
    }

    @Override
    public Boolean updateCart(Cart cart) {
        if(cart == null){
            return null;
        }

        try {
            //获取当前登录用户，查询redis中该用户的购物车信息
            UserInfo loginUser = LoginInterceptor.getLoginUser();
            String key = KEY_PREFIX + loginUser.getId();

            BoundHashOperations<String, Object, Object> hashOps = this.redisTemplate.boundHashOps(key);

            //1.获取当前要修改的购物车信息（从redis中反序列化为Cart对象）
            Cart redisCart = JsonUtils.parse(hashOps.get(cart.getSkuId().toString()).toString(), Cart.class);

            //2.修改数量
            redisCart.setNum(cart.getNum());

            //3.重新序列化到redis中
            hashOps.put(cart.getSkuId().toString(), JsonUtils.serialize(redisCart));

            return true;
        } catch (Exception e) {
            logger.error("更新redis购物车失败！", e);
            return false;
        }
    }

    @Override
    public Boolean addCartList(List<Cart> cartList) {
        try {
            cartList.forEach(cart->{
                this.addCart(cart);
            });
            return true;
        } catch (Exception e) {
            logger.error("批量保存redis购物车失败！", e);
            return false;
        }
    }
}
