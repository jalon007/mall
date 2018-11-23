package com.qutalk.mall.admin.web;

import com.qutalk.mall.admin.annotation.LoginAdmin;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.core.validator.Order;
import com.qutalk.mall.core.validator.Sort;
import com.qutalk.mall.db.domain.LitemallTopic;
import com.qutalk.mall.db.service.LitemallTopicService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/topic")
@Validated
public class AdminTopicController {
    private final Log logger = LogFactory.getLog(AdminTopicController.class);

    @Autowired
    private LitemallTopicService topicService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String title, String subtitle,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        List<LitemallTopic> topicList = topicService.querySelective(title, subtitle, page, limit, sort, order);
        int total = topicService.countSelective(title, subtitle, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", topicList);

        return ResponseUtil.ok(data);
    }

    private Object validate(LitemallTopic topic) {
        String title = topic.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ResponseUtil.badArgument();
        }
        String content = topic.getContent();
        if (StringUtils.isEmpty(content)) {
            return ResponseUtil.badArgument();
        }
        BigDecimal price = topic.getPrice();
        if (price == null) {
            return ResponseUtil.badArgument();
        }
        return null;
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody LitemallTopic topic) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(topic);
        if (error != null) {
            return error;
        }
        topicService.add(topic);
        return ResponseUtil.ok(topic);
    }

    @GetMapping("/read")
    public Object read(@LoginAdmin Integer adminId, @NotNull Integer id) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        LitemallTopic brand = topicService.findById(id);
        return ResponseUtil.ok(brand);
    }

    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody LitemallTopic topic) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(topic);
        if (error != null) {
            return error;
        }
        if (topicService.updateById(topic) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(topic);
    }

    @PostMapping("/delete")
    public Object delete(@LoginAdmin Integer adminId, @RequestBody LitemallTopic topic) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        topicService.deleteById(topic.getId());
        return ResponseUtil.ok();
    }

}
