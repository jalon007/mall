package com.qutalk.mall.admin.web;

import com.qutalk.mall.admin.annotation.LoginAdmin;
import com.qutalk.mall.core.util.RegexUtil;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.core.util.bcrypt.BCryptPasswordEncoder;
import com.qutalk.mall.core.validator.Order;
import com.qutalk.mall.core.validator.Sort;
import com.qutalk.mall.db.domain.LitemallUser;
import com.qutalk.mall.db.domain.Pptm;
import com.qutalk.mall.db.service.LitemallUserService;
import com.qutalk.mall.db.service.PPTMService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/pptm")
@Validated
public class AdminPPTMController {
    private final Log logger = LogFactory.getLog(AdminPPTMController.class);

    @Autowired
    private PPTMService pptmService;

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String name,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort @RequestParam(defaultValue = "updated_time") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        List<Pptm> pptmList = pptmService.querySelective(name, page, limit, sort, order);
        int total = pptmService.countSeletive(name, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", pptmList);

        return ResponseUtil.ok(data);
    }
    private Object validate(Pptm pptm) {
        if (StringUtils.isEmpty(pptm)) {
            return ResponseUtil.badArgument();
        }
        if (StringUtils.isEmpty(pptm.getName())) {
            return ResponseUtil.fail(402, "品牌名称不能空");
        }
        if (StringUtils.isEmpty(pptm.getUrl())) {
            return ResponseUtil.fail(402, "H5链接不能空");
        }
        return null;
    }

    @PostMapping("/create")
    public Object create(@LoginAdmin Integer adminId, @RequestBody Pptm pptm) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(pptm);
        if (error != null) {
            return error;
        }
        pptmService.add(pptm);

        return ResponseUtil.ok(pptm);
    }
    @PostMapping("/update")
    public Object update(@LoginAdmin Integer adminId, @RequestBody Pptm pptm) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }
        Object error = validate(pptm);
        if (error != null) {
            return error;
        }
       pptmService.updateById(pptm);
        return ResponseUtil.ok(pptm);
    }

}
