package com.qutalk.mall.admin.web;

import com.qutalk.mall.admin.annotation.LoginAdmin;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.core.validator.Order;
import com.qutalk.mall.core.validator.Sort;
import com.qutalk.mall.db.domain.LitemallFeedback;
import com.qutalk.mall.db.service.LitemallFeedbackService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Yogeek
 * @date 2018/8/26 1:11
 */
@RestController
@RequestMapping("/admin/feedback")
@Validated
public class AdminFeedbackController {
    private final Log logger = LogFactory.getLog(AdminFeedbackController.class);

    @Autowired
    private LitemallFeedbackService feedbackService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       Integer userId, String username,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "add_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        List<LitemallFeedback> feedbackList = feedbackService.querySelective(userId, username, page, limit, sort, order);
        int total = feedbackService.countSelective(userId, username, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", feedbackList);

        return ResponseUtil.ok(data);
    }
}
