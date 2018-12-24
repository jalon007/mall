package com.qutalk.mall.wx.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @author Jalon
 * @Description:
 * @create 2018/12/24 - 17:17
 * @package com.qutalk.mall.wx.cache
 */
public class RedisCache {

    @Autowired
    StringRedisTemplate redisTemplate;

}
