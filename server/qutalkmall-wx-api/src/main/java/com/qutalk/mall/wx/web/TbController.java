package com.qutalk.mall.wx.web;

import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.db.domain.CouponCate;
import com.qutalk.mall.db.service.CouponCateService;
import com.qutalk.mall.wx.config.TBConstant;
import com.qutalk.mall.wx.tbService.TbService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;
import com.taobao.api.response.TbkItemGetResponse;
import com.taobao.api.response.TbkTpwdCreateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jalon
 * @Description:
 * @create 2018/11/26 - 14:41
 * @package com.qutalk.mall.wx.web
 */
@RestController
@RequestMapping("/wx/goods")
public class TbController {

    @Autowired
    private TbService tbService;
    @Autowired
    CouponCateService couponCateService;

    @GetMapping("search")
    public Object search(){
        //TbkItemGetRequest req = new TbkItemGetRequest();


        TbkItemGetResponse r =tbService.searchGoods();
        //Map<String, Object> data = new HashMap<>();
        //data.put("count", r.getTotalResults());
        //data.put("goods", r.getResults());

        return r;
    }
    @GetMapping("coupons")
    public Object getCouponList(){
        List<CouponCate> couponCates =couponCateService.queryCouponCates(TBConstant.coupon_cate_type_normal);
        Map<String, Object> data = new HashMap<>();
        List<Map> categoryList = new ArrayList<>();
        if(couponCates!=null){
            for(CouponCate couponCate:couponCates){
                TbkDgItemCouponGetResponse r =tbService.getCouponList(couponCate.getAdzoneId());
                Map<String, Object> catGoods = new HashMap<String, Object>();
                catGoods.put("id", couponCate.getAdzoneId());
                catGoods.put("name", couponCate.getAdzoneName());
                catGoods.put("goodsList", r.getResults());
               // catGoods.put("childCategory",catL2List);
                categoryList.add(catGoods);
            }
            data.put("floorGoodsList", categoryList);
        }

        return ResponseUtil.ok(data);
    }
    @GetMapping("pwd")
    public Object getTbpwd(){
        TbkTpwdCreateResponse r =tbService.getTbpwd();
        //Map<String, Object> data = new HashMap<>();
        //data.put("count", r.getTotalResults());
        //data.put("goods", r.getResults());

        return r;
    }

}
