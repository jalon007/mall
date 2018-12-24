package com.qutalk.mall.wx.cache.bean;

import com.taobao.api.request.TbkDgItemCouponGetRequest;
import com.taobao.api.response.TbkDgItemCouponGetResponse;

/**
 * @author Jalon
 * @Description:
 * @create 2018/12/24 - 15:52
 * @package com.qutalk.mall.wx.mongo.bean
 */
public class CouponGoods {
    /**
     * mm_xxx_xxx_xxx的第三位
     */
    private Long adzoneId;

    /**
     * 后台类目ID，用,分割，最大10个，该ID可以通过taobao.itemcats.get接口获取到
     */
    private String cat;

    /**
     * 第几页，默认：1（当后台类目和查询词均不指定的时候，最多出10000个结果，即page_no*page_size不能超过10000；当指定类目或关键词的时候，则最多出100个结果）
     */
    private Long pageNo;

    /**
     * 页大小，默认20，1~100
     */
    private Long pageSize;

    /**
     * 1：PC，2：无线，默认：1
     */
    private Long platform;

    /**
     * 查询词
     */
    private String q;

    TbkDgItemCouponGetResponse.TbkCoupon tbkCoupon;

    public Long getAdzoneId() {
        return adzoneId;
    }

    public void setAdzoneId(Long adzoneId) {
        this.adzoneId = adzoneId;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public Long getPageNo() {
        return pageNo;
    }

    public void setPageNo(Long pageNo) {
        this.pageNo = pageNo;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }

    public Long getPlatform() {
        return platform;
    }

    public void setPlatform(Long platform) {
        this.platform = platform;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }

    public TbkDgItemCouponGetResponse.TbkCoupon getTbkCoupon() {
        return tbkCoupon;
    }

    public void setTbkCoupon(TbkDgItemCouponGetResponse.TbkCoupon tbkCoupon) {
        this.tbkCoupon = tbkCoupon;
    }
}
