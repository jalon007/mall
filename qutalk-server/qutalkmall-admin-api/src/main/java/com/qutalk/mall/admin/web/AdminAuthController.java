package com.qutalk.mall.admin.web;

import com.qutalk.mall.admin.annotation.LoginAdmin;
import com.qutalk.mall.admin.dao.AdminToken;
import com.qutalk.mall.admin.service.AdminTokenManager;
import com.qutalk.mall.core.util.JacksonUtil;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.core.util.bcrypt.BCryptPasswordEncoder;
import com.qutalk.mall.db.domain.LitemallAdmin;
import com.qutalk.mall.db.service.LitemallAdminService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/login")
@Validated
public class AdminAuthController {
    private final Log logger = LogFactory.getLog(AdminAuthController.class);

    @Autowired
    private LitemallAdminService adminService;

    /*
     *  { username : value, password : value }
     */
    @PostMapping("/login")
    public Object login(@RequestBody String body) {
        String username = JacksonUtil.parseString(body, "username");
        String password = JacksonUtil.parseString(body, "password");

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return ResponseUtil.badArgument();
        }

        List<LitemallAdmin> adminList = adminService.findAdmin(username);
        Assert.state(adminList.size() < 2, "同一个用户名存在两个账户");
        if (adminList.size() == 0) {
            return ResponseUtil.badArgumentValue();
        }
        LitemallAdmin admin = adminList.get(0);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(password, admin.getPassword())) {
            return ResponseUtil.fail(403, "账号密码不对");
        }

        Integer adminId = admin.getId();
        // token
        AdminToken adminToken = AdminTokenManager.generateToken(adminId);

        return ResponseUtil.ok(adminToken.getToken());
    }

    /*
     *
     */
    @PostMapping("/logout")
    public Object login(@LoginAdmin Integer adminId) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        return ResponseUtil.ok();
    }
}
