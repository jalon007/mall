package com.qutalk.mall.db.service;

import com.qutalk.mall.db.dao.LitemallGoodsProductMapper;
import com.qutalk.mall.db.domain.LitemallGoodsProduct;
import com.qutalk.mall.db.domain.LitemallGoodsProductExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class LitemallGoodsProductService {
    @Resource
    private LitemallGoodsProductMapper goodsProductMapper;

    public List<LitemallGoodsProduct> queryByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsProductMapper.selectByExample(example);
    }

    public LitemallGoodsProduct findById(Integer id) {
        return goodsProductMapper.selectByPrimaryKey(id);
    }

    public int updateById(LitemallGoodsProduct goodsProduct) {
        goodsProduct.setUpdateTime(LocalDateTime.now());
        return goodsProductMapper.updateByPrimaryKeySelective(goodsProduct);
    }

    public void deleteById(Integer id) {
        goodsProductMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGoodsProduct goodsProduct) {
        goodsProduct.setAddTime(LocalDateTime.now());
        goodsProduct.setUpdateTime(LocalDateTime.now());
        goodsProductMapper.insertSelective(goodsProduct);
    }

    public int count() {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andDeletedEqualTo(false);
        return (int) goodsProductMapper.countByExample(example);
    }

    public void deleteByGid(Integer gid) {
        LitemallGoodsProductExample example = new LitemallGoodsProductExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsProductMapper.logicalDeleteByExample(example);
    }
}