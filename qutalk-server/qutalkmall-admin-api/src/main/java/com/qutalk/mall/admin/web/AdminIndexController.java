package com.qutalk.mall.admin.web;

import com.qutalk.mall.core.util.ResponseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/index")
public class AdminIndexController {
    private final Log logger = LogFactory.getLog(AdminIndexController.class);

    @RequestMapping("/index")
    public Object index() {
        return ResponseUtil.ok("hello world, this is admin service");
    }


}
