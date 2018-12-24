package com.qutalk.mall.wx.tbService;

import com.qutalk.mall.wx.cache.MongoCache;
import com.qutalk.mall.wx.config.TBConstant;
import com.qutalk.mall.wx.cache.bean.CouponGoods;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.NTbkItem;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.*;
import com.taobao.api.response.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 *
 *
 *
 *
 *
 *  *

 *
 * taobao.tbk.uatm.event.get	枚举正在进行中的定向招商的活动列表
 * taobao.tbk.uatm.event.item.get	获取淘宝联盟定向招商的宝贝信息
 * taobao.tbk.uatm.favorites.item.get	获取淘宝联盟选品库的宝贝信息
 * taobao.tbk.uatm.favorites.get	获取淘宝联盟选品库列表
 * taobao.tbk.ju.tqg.get	淘抢购api
 * taobao.ju.items.search	聚划算商品搜索接口
 *
 * 1、taobao.tbk.item.get	淘宝客商品查询
 * @author Jalon
 * @Description:
 * @create 2018/12/21 - 17:43
 * @package com.qutalk.mall.wx.tbService
 */
@Service
public class TbService{
    private static final Logger logger = LoggerFactory.getLogger(TbService.class);

    @Autowired
    MongoCache mongoCache;
    private final TaobaoClient client=new DefaultTaobaoClient(TBConstant.url, TBConstant.appkey, TBConstant.secret);

    public TbkItemGetResponse searchGoods(){
        TbkItemGetRequest req = new TbkItemGetRequest();
        req.setFields("click_url,num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url,seller_id,volume,nick");
        req.setQ("女装");
        /*req.setCat("16,18");
        req.setItemloc("杭州");
        req.setSort("tk_rate_des");
        req.setIsTmall(false);
        req.setIsOverseas(false);
        req.setStartPrice(10L);
        req.setEndPrice(10L);
        req.setStartTkRate(123L);
        req.setEndTkRate(123L);
        req.setPlatform(1L);*/
        req.setPageNo(1L);
        req.setPageSize(20L);
        TbkItemGetResponse rsp =null;
        try {
            rsp = client.execute(req);
            List<NTbkItem> result=rsp.getResults();
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return rsp;
    }

    /**
     * 2、taobao.tbk.item.recommend.get	淘宝客商品关联推荐查询
     * 推荐
     * @return
     * @throws ApiException
     */
    public TbkItemRecommendGetResponse  getRecommends(){
        TbkItemRecommendGetRequest req = new TbkItemRecommendGetRequest();
        req.setFields("num_iid,title,pict_url,small_images,reserve_price,zk_final_price,user_type,provcity,item_url");
        req.setNumIid(123L);
        req.setCount(20L);
        req.setPlatform(1L);
        TbkItemRecommendGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 3、taobao.tbk.item.info.get	淘宝客商品详情（简版）
     * 详情
     * @return
     * @throws ApiException
     */
    public TbkItemInfoGetResponse getItemInfo() {
        TbkItemInfoGetRequest req = new TbkItemInfoGetRequest();
        req.setNumIids("123,456");
        req.setPlatform(1L);
        req.setIp("11.22.33.43");
        TbkItemInfoGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 4、taobao.tbk.shop.get	淘宝客店铺查询
     * 店铺信息
     * @return
     * @throws ApiException
     */
    public TbkShopGetResponse getShop() {

        TbkShopGetRequest req = new TbkShopGetRequest();
        req.setFields("user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
        req.setQ("女装");
        req.setSort("commission_rate_des");
        req.setIsTmall(false);
        req.setStartCredit(1L);
        req.setEndCredit(20L);
        req.setStartCommissionRate(2000L);
        req.setEndCommissionRate(123L);
        req.setStartTotalAction(1L);
        req.setEndTotalAction(100L);
        req.setStartAuctionCount(123L);
        req.setEndAuctionCount(200L);
        req.setPlatform(1L);
        req.setPageNo(1L);
        req.setPageSize(20L);
        TbkShopGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 5、taobao.tbk.shop.recommend.get	淘宝客店铺关联推荐查询
     * 推荐店铺
     * @return
     * @throws ApiException
     */
    public TbkShopRecommendGetResponse getRecommendShop() {
        TbkShopRecommendGetRequest req = new TbkShopRecommendGetRequest();
        req.setFields("user_id,shop_title,shop_type,seller_nick,pict_url,shop_url");
        req.setUserId(123L);
        req.setCount(20L);
        req.setPlatform(1L);
        TbkShopRecommendGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * 母婴：
     * 女装：
     * 网红爆款：
     *
     * taobao.tbk.dg.item.coupon.get	好券清单API【导购】
     * 	mm_xxx_xxx_xxx的第三位
     * 	mm_memberid_siteid_adzoneid。Memberid对应推广者，Siteid对应媒体，Adzoneid对应推广位
     * @return
     * @throws ApiException
     */
    public TbkDgItemCouponGetResponse getCouponList (Long adzoneId){
        TbkDgItemCouponGetRequest req = new TbkDgItemCouponGetRequest();
        req.setAdzoneId(adzoneId);
        req.setPlatform(2L);
        req.setCat("16");
        req.setPageSize(20L);
       // req.setQ("宝宝服");
        req.setPageNo(1L);
        TbkDgItemCouponGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        if(rsp.getResults()!=null){
            CouponGoods couponGoods = new CouponGoods();
            couponGoods.setAdzoneId(req.getAdzoneId());
            for(TbkDgItemCouponGetResponse.TbkCoupon coupon:rsp.getResults()){
                couponGoods.setTbkCoupon(coupon);
                mongoCache.set(couponGoods);
            }
        }


        return rsp;
    }


    /**
     * taobao.tbk.coupon.get	阿里妈妈推广券信息查询
     * @return
     */
    public TbkCouponGetResponse getCoupon(){
        TbkCouponGetRequest req = new TbkCouponGetRequest();
        req.setMe("nfr%2BYTo2k1PX18gaNN%2BIPkIG2PadNYbBnwEsv6mRavWieOoOE3L9OdmbDSSyHbGxBAXjHpLKvZbL1320ML%2BCF5FRtW7N7yJ056Lgym4X01A%3D");
        req.setItemId(123L);
        req.setActivityId("sdfwe3eefsdf");
        TbkCouponGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * taobao.tbk.tpwd.create	淘宝客淘口令
     * @return
     * @throws ApiException
     */
    public TbkTpwdCreateResponse getTbpwd(){
        TbkTpwdCreateRequest req = new TbkTpwdCreateRequest();
        req.setUserId("123456789");
        req.setText("长度大于5个字符");
        req.setUrl("https://uland.taobao.com/");
        req.setLogo("https://uland.taobao.com/");
        req.setExt("{hahahahaq}");
        TbkTpwdCreateResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * taobao.tbk.dg.newuser.order.get	淘宝客新用户订单API--导购
     * @return
     * @throws ApiException
     */
    public TbkDgNewuserOrderGetResponse getNewuserOrder(){
        TbkDgNewuserOrderGetRequest req = new TbkDgNewuserOrderGetRequest();
        req.setPageSize(20L);
        req.setAdzoneId(123L);
        req.setPageNo(1L);
        req.setStartTime(StringUtils.parseDateTime("2018-01-24 00:34:05"));
        req.setEndTime(StringUtils.parseDateTime("2018-01-24 00:34:05"));
        req.setActivityId("119013_2");
        TbkDgNewuserOrderGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * taobao.tbk.sc.newuser.order.get	淘宝客新用户订单API--社交
     * @return
     * @throws ApiException
     */
    public TbkScNewuserOrderGetResponse getScNewuserOrder(){
        TbkScNewuserOrderGetRequest req = new TbkScNewuserOrderGetRequest();
        req.setPageSize(20L);
        req.setAdzoneId(123L);
        req.setPageNo(1L);
        req.setSiteId(123L);
        req.setActivityId("119013_2");
        req.setEndTime(StringUtils.parseDateTime("2018-01-24 00:34:05"));
        req.setStartTime(StringUtils.parseDateTime("2018-01-24 00:34:05"));
        TbkScNewuserOrderGetResponse rsp = null;
        try {
            rsp = client.execute(req, TBConstant.sessionKey);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }

    /**
     * *  *
     *  *  *
     *  *  *
     *  *  *
     *  *  * taobao.tbk.dg.optimus.material	淘宝客物料下行-导购
     *  *  * taobao.tbk.dg.material.optional	通用物料搜索API（导购）
     *  *  * taobao.tbk.dg.newuser.order.sum	拉新活动汇总API--导购
     *  *  * taobao.tbk.sc.newuser.order.sum	拉新活动汇总API--社交
     *  *  * taobao.tbk.sc.optimus.material	淘宝客擎天柱通用物料API - 社交
     */
}
