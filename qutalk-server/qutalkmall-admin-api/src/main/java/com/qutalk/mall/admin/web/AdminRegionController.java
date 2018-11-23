package com.qutalk.mall.admin.web;

import com.qutalk.mall.admin.annotation.LoginAdmin;
import com.qutalk.mall.core.util.ResponseUtil;
import com.qutalk.mall.core.validator.Order;
import com.qutalk.mall.core.validator.Sort;
import com.qutalk.mall.db.domain.LitemallRegion;
import com.qutalk.mall.db.service.LitemallRegionService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/region")
@Validated
public class AdminRegionController {
    private final Log logger = LogFactory.getLog(AdminRegionController.class);

    @Autowired
    private LitemallRegionService regionService;

    @GetMapping("/clist")
    public Object clist(@LoginAdmin Integer adminId, @NotNull Integer id) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        List<LitemallRegion> regionList = regionService.queryByPid(id);

        return ResponseUtil.ok(regionList);
    }

    @GetMapping("/list")
    public Object list(@LoginAdmin Integer adminId,
                       String name, Integer code,
                       @RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "10") Integer limit,
                       @Sort(accepts = {"id"}) @RequestParam(defaultValue = "id") String sort,
                       @Order @RequestParam(defaultValue = "desc") String order) {
        if (adminId == null) {
            return ResponseUtil.unlogin();
        }

        List<LitemallRegion> regionList = regionService.querySelective(name, code, page, limit, sort, order);
        int total = regionService.countSelective(name, code, page, limit, sort, order);
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", regionList);

        return ResponseUtil.ok(data);
    }
}
