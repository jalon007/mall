package com.qutalk.mall.db.service;

import com.github.pagehelper.PageHelper;
import com.qutalk.mall.db.dao.LitemallUserMapper;
import com.qutalk.mall.db.dao.PptmMapper;
import com.qutalk.mall.db.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PPTMService {
    @Resource
    private PptmMapper pptmMapper;

    public Pptm findById(Integer id) {
        return pptmMapper.selectByPrimaryKey(id);
    }

    public void add(Pptm pptm) {
        pptm.setCreatedTime(LocalDateTime.now());
        pptm.setUpdatedTime(LocalDateTime.now());
        pptmMapper.insertSelective(pptm);
    }

    public int updateById(Pptm pptm) {
        pptm.setUpdatedTime(LocalDateTime.now());
        return pptmMapper.updateByPrimaryKeySelective(pptm);
    }

    public List<Pptm> querySelective(String name, Integer page, Integer size, String sort, String order) {
        PptmExample example = new PptmExample();
        PptmExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        PageHelper.startPage(page, size);
        return pptmMapper.selectByExample(example);
    }

    public int countSeletive(String name, Integer page, Integer size, String sort, String order) {
        PptmExample example = new PptmExample();
        PptmExample.Criteria criteria = example.createCriteria();

        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }
        return (int) pptmMapper.countByExample(example);
    }

    public int count() {
        PptmExample example = new PptmExample();
        return (int) pptmMapper.countByExample(example);
    }
}
