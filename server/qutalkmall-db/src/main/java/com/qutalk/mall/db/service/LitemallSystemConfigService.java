package com.qutalk.mall.db.service;

import com.qutalk.mall.db.dao.LitemallSystemMapper;
import com.qutalk.mall.db.domain.LitemallSystem;
import com.qutalk.mall.db.domain.LitemallSystemExample;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LitemallSystemConfigService {
    @Resource
    private LitemallSystemMapper systemMapper;

    public List<LitemallSystem> queryAll() {
        LitemallSystemExample example = new LitemallSystemExample();
        example.or();
        return systemMapper.selectByExample(example);
    }
}
