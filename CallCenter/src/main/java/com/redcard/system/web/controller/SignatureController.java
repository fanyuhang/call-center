package com.redcard.system.web.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * 数据有效性校验
 * User: Allen
 * Date: 11/24/12
 */
@Controller
@RequestMapping(value = "/system/signature")
public class SignatureController {

    private static Logger logger = LoggerFactory.getLogger(SignatureController.class);

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "system/signature/list";
    }

}
