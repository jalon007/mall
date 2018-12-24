package com.qutalk.mall.wx.tbService;

import com.qutalk.mall.wx.config.TBConstant;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.internal.util.StringUtils;
import com.taobao.api.request.ItemcatsGetRequest;
import com.taobao.api.response.ItemcatsGetResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jalon
 * @Description:
 * @create 2018/12/21 - 18:20
 * @package com.qutalk.mall.wx.tbService
 */
public class TbConfigService {
    private static final Logger logger = LoggerFactory.getLogger(TbConfigService.class);

    private final TaobaoClient client=new DefaultTaobaoClient(TBConstant.url, TBConstant.appkey, TBConstant.secret);
    public ItemcatsGetResponse getCats(){
        ItemcatsGetRequest req = new ItemcatsGetRequest();
        req.setCids("18957,19562");
        req.setDatetime(StringUtils.parseDateTime("2000-01-01 00:00:00"));
        req.setFields("cid,parent_cid,name,is_parent");
        req.setParentCid(50011999L);
        ItemcatsGetResponse rsp = null;
        try {
            rsp = client.execute(req);
        } catch (ApiException e) {
            e.printStackTrace();
        }
        System.out.println(rsp.getBody());
        return rsp;
    }
}
