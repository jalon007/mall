package com.qutalk.mall.wx.web;

import com.qutalk.mall.core.system.SystemConfig;
import com.qutalk.mall.db.domain.*;
import com.qutalk.mall.db.service.*;
import com.qutalk.mall.wx.service.HomeCacheManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.db.domain.*;
import com.qutalk.mall.db.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wx/home")
@Validated
public class WxHomeController {
    private final Log logger = LogFactory.getLog(WxHomeController.class);

    @Autowired
    private LitemallAdService adService;
    @Autowired
    private LitemallGoodsService goodsService;
    @Autowired
    private LitemallBrandService brandService;
    @Autowired
    private LitemallTopicService topicService;
    @Autowired
    private LitemallCategoryService categoryService;
    @Autowired
    private LitemallGrouponRulesService grouponRulesService;


    @GetMapping("/cache")
    public Object cache(@NotNull String key) {
        if (!key.equals("litemall_cache")) {
            return ResponseUtil.fail();
        }

        // 清除缓存
        HomeCacheManager.clearAll();
        return ResponseUtil.ok("缓存已清除");
    }

    /**
     * app首页
     *
     * @return app首页相关信息
     * 成功则
     * {
     * errno: 0,
     * errmsg: '成功',
     * data:
     * {
     * banner: xxx,
     * channel: xxx,
     * newGoodsList: xxx,
     * hotGoodsList: xxx,
     * topicList: xxx,
     * grouponList: xxx,
     * floorGoodsList: xxx
     * }
     * }
     * 失败则 { errno: XXX, errmsg: XXX }
     */
    @GetMapping("/index")
    public Object index() {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.INDEX)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }


        Map<String, Object> data = new HashMap<>();

        List<LitemallAd> banner = adService.queryIndex();
        data.put("banner", banner);

        List<LitemallCategory> channel = categoryService.queryChannel();
        data.put("channel", channel);

        List<LitemallGoods> newGoods = goodsService.queryByNew(0, SystemConfig.getNewLimit());
        data.put("newGoodsList", newGoods);

        List<LitemallGoods> hotGoods = goodsService.queryByHot(0, SystemConfig.getHotLimit());
        data.put("hotGoodsList", hotGoods);

        List<LitemallBrand> brandList = brandService.queryVO(0, SystemConfig.getBrandLimit());
        data.put("brandList", brandList);

        List<LitemallTopic> topicList = topicService.queryList(0, SystemConfig.getTopicLimit());
        data.put("topicList", topicList);

        //团购专区
        List<Map<String, Object>> grouponList = grouponRulesService.queryList(0, 5);
        data.put("grouponList", grouponList);

        List<Map> categoryList = new ArrayList<>();
        List<LitemallCategory> catL1List = categoryService.queryL1WithoutRecommend(0, SystemConfig.getCatlogListLimit());
        for (LitemallCategory catL1 : catL1List) {
            List<LitemallCategory> catL2List = categoryService.queryByPid(catL1.getId());
            List<Integer> l2List = new ArrayList<>();
            for (LitemallCategory catL2 : catL2List) {
                l2List.add(catL2.getId());
            }

            List<LitemallGoods> categoryGoods = null;
            if (l2List.size() == 0) {
                categoryGoods = new ArrayList<>();
            } else {
                categoryGoods = goodsService.queryByCategory(l2List, 0, 11);//4,SystemConfig.getCatlogMoreLimit()
            }

            Map<String, Object> catGoods = new HashMap<String, Object>();
            catGoods.put("id", catL1.getId());
            catGoods.put("name", catL1.getName());
            catGoods.put("goodsList", categoryGoods);
            categoryList.add(catGoods);
        }
        data.put("floorGoodsList", categoryList);

        //缓存数据
        HomeCacheManager.loadData(HomeCacheManager.INDEX, data);
        return ResponseUtil.ok(data);
    }
    @GetMapping("/pindex")
    public Object pindex() {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.INDEX)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }


        Map<String, Object> data = new HashMap<>();

        List<LitemallAd> banner = adService.queryIndex();
        data.put("banner", banner);

        List<LitemallCategory> channel = categoryService.queryChannel();
        data.put("channel", channel);

        List<LitemallGoods> newGoods = goodsService.queryByNew(0, SystemConfig.getNewLimit());
        data.put("newGoodsList", newGoods);

        List<LitemallGoods> hotGoods = goodsService.queryByHot(0, SystemConfig.getHotLimit());
        data.put("hotGoodsList", hotGoods);

        List<LitemallBrand> brandList = brandService.queryVO(0, SystemConfig.getBrandLimit());
        data.put("brandList", brandList);

        List<LitemallTopic> topicList = topicService.queryList(0, SystemConfig.getTopicLimit());
        data.put("topicList", topicList);

        //团购专区
        List<Map<String, Object>> grouponList = grouponRulesService.queryList(0, 5);
        data.put("grouponList", grouponList);

        List<Map> categoryList = new ArrayList<>();
        List<LitemallCategory> catL1List = categoryService.queryL1WithoutRecommend(0, 8);//SystemConfig.getCatlogListLimit()
        for (LitemallCategory catL1 : catL1List) {
            List<LitemallCategory> catL2List = categoryService.queryByPid(catL1.getId());
            List<LitemallGoods> categoryGoods = null;
            for (LitemallCategory catL2 : catL2List) {
                categoryGoods = goodsService.queryByCategory(catL2.getId(), 0, 11);//4,SystemConfig.getCatlogMoreLimit()
                if(!categoryGoods.isEmpty()){
                    break;
                }
            }

            Map<String, Object> catGoods = new HashMap<String, Object>();
            catGoods.put("id", catL1.getId());
            catGoods.put("name", catL1.getName());
            catGoods.put("goodsList", categoryGoods);
            catGoods.put("childCategory",catL2List);
            categoryList.add(catGoods);
        }
        data.put("floorGoodsList", categoryList);

        //缓存数据
        HomeCacheManager.loadData(HomeCacheManager.INDEX, data);
        return ResponseUtil.ok(data);
    }
    @GetMapping("/navList")
    public Object navList() {
        //优先从缓存中读取
        if (HomeCacheManager.hasData(HomeCacheManager.NAVLIST)) {
            return ResponseUtil.ok(HomeCacheManager.getCacheData(HomeCacheManager.INDEX));
        }


        Map<String, Object> data = new HashMap<>();
        List<LitemallCategory> channel = categoryService.queryChannel();

        data.put("channel", channel);
        //缓存数据
        HomeCacheManager.loadData(HomeCacheManager.NAVLIST, data);
        return ResponseUtil.ok(data);
    }
}