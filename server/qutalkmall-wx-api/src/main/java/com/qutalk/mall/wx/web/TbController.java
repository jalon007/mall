package com.qutalk.mall.wx.web;

import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.wx.config.TBConstant;
import com.qutalk.mall.wx.tbService.TbService;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.request.TbkItemGetRequest;
import com.taobao.api.response.TbkItemGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Jalon
 * @Description:
 * @create 2018/11/26 - 14:41
 * @package com.qutalk.mall.wx.web
 */
public class TbController {

    @Autowired
    private TbService tbService;

    @GetMapping("search")
    public Object search(){
        TbkItemGetResponse r =tbService.searchGoods();
        Map<String, Object> data = new HashMap<>();
        data.put("count", r.getTotalResults());
        data.put("goods", r.getResults());

        return ResponseUtil.ok(data);
    }

    @GetMapping("search")
    public Object search(){
        TbkItemGetResponse r =tbService.searchGoods();
        Map<String, Object> data = new HashMap<>();
        data.put("count", r.getTotalResults());
        data.put("goods", r.getResults());

        return ResponseUtil.ok(data);
    }
    @GetMapping("search")
    public Object search(){
        TbkItemGetResponse r =tbService.searchGoods();
        Map<String, Object> data = new HashMap<>();
        data.put("count", r.getTotalResults());
        data.put("goods", r.getResults());

        return ResponseUtil.ok(data);
    }
    @GetMapping("search")
    public Object search(){
        TbkItemGetResponse r =tbService.searchGoods();
        Map<String, Object> data = new HashMap<>();
        data.put("count", r.getTotalResults());
        data.put("goods", r.getResults());

        return ResponseUtil.ok(data);
    }
    @GetMapping("search")
    public Object search(){
        TbkItemGetResponse r =tbService.searchGoods();
        Map<String, Object> data = new HashMap<>();
        data.put("count", r.getTotalResults());
        data.put("goods", r.getResults());

        return ResponseUtil.ok(data);
    }
    @GetMapping("search")
    public Object search(){
        TbkItemGetResponse r =tbService.searchGoods();
        Map<String, Object> data = new HashMap<>();
        data.put("count", r.getTotalResults());
        data.put("goods", r.getResults());

        return ResponseUtil.ok(data);
    }
}
