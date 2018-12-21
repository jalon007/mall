package com.qutalk.mall.wx.tbService;

import com.taobao.api.internal.util.StringUtils;

/**
 * @author Jalon
 * @Description:
 * @create 2018/12/21 - 18:20
 * @package com.qutalk.mall.wx.tbService
 */
public class TbConfigService {

    public ItemcatsGetResponse getCats(){
        ItemcatsGetRequest req = new ItemcatsGetRequest();
        req.setCids("18957,19562");
        req.setDatetime(StringUtils.parseDateTime("2000-01-01 00:00:00"));
        req.setFields("cid,parent_cid,name,is_parent");
        req.setParentCid(50011999L);
        ItemcatsGetResponse rsp = client.execute(req);
        System.out.println(rsp.getBody());
        return rsp
    }
}
