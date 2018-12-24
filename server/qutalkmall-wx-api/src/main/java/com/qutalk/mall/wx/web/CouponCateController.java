package com.qutalk.mall.wx.web;

import com.alibaba.fastjson.JSON;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.db.domain.CouponCate;
import com.qutalk.mall.db.service.CouponCateService;
import com.qutalk.mall.wx.config.CacheConstant;
import com.qutalk.mall.wx.config.TBConstant;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.prefs.Preferences;

/**
 * @author Jalon
 * @Description:
 * @create 2018/12/24 - 17:31
 * @package com.qutalk.mall.wx.web
 */
@RestController
@RequestMapping("/wx/coupon")
public class CouponCateController {
    @Autowired
    CouponCateService couponCateService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @GetMapping("cates")
    public Object getCates(Integer type){
        //String cacheValue = redisTemplate.opsForValue().get(CacheConstant.coupon_cates_key);
        List<CouponCate> couponCates =couponCateService.queryCouponCates(TBConstant.coupon_cate_type_normal);
        Map<String, Object> data = new HashMap<>();
        data.put("count", couponCates.size());
        data.put("couponCates", couponCates);
        return ResponseUtil.ok(data);
    }
}
