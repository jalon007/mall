package com.qutalk.mall.db.service;

import com.qutalk.mall.db.dao.CouponCateMapper;
import com.qutalk.mall.db.dao.LitemallGrouponMapper;
import com.qutalk.mall.db.domain.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jalon
 * @Description:
 * @create 2018/12/24 - 17:26
 * @package com.qutalk.mall.db.service
 */
@Service
public class CouponCateService {
    @Resource
    private CouponCateMapper mapper;

    /**
     * @return
     */
    public List<CouponCate> queryCouponCates(Integer type) {
       CouponCateExample example =new CouponCateExample();
       example.or().andTypeEqualTo(type);
        return mapper.selectByExample(example);
    }
}
