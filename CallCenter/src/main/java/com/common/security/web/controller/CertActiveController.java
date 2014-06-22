package com.common.security.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.SystemEnum;
import com.common.security.entity.CertActive;
import com.common.security.service.CertActiveManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/security/cert")
public class CertActiveController {

    private static Logger logger = LoggerFactory.getLogger(CertActiveController.class);

    @Autowired
    private CertActiveManager certActiveManager;

    @RequestMapping(value = "init")
    public String init(String MenuNo, Model model) {
        model.addAttribute("MenuNo", MenuNo);
        return "security/cert/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<CertActive> list(String where, GridPageRequest pageRequest) {
        return new DataResponse<CertActive>(certActiveManager.findAll(where, pageRequest));
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(CertActive CertActive) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        CertActive.setSystem(SystemEnum.SYSTEM_SELF.toString());
        certActiveManager.saveCertActive(CertActive);
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(CertActive CertActive) {
        AsyncResponse result = new AsyncResponse(false, "更新成功");
        certActiveManager.saveCertActive(CertActive);
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(String ids) {
        AsyncResponse result = new AsyncResponse(false, "删除成功");
        certActiveManager.deleteCertActives(ids);
        return result;
    }
}
