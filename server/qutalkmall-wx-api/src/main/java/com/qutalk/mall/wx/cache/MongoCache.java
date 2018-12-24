package com.qutalk.mall.wx.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Component;

/**
 * @author Jalon
 * @Description:
 * @create 2018/12/24 - 17:18
 * @package com.qutalk.mall.wx.cache
 */
@Component
public class MongoCache {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MongoOperations mongoTemplate;

    public void set(Object o) {
        mongoTemplate.insert(o);
        logger.info("insert medical_examination_info into mongodb doc:{}", o.toString());
    }
}
