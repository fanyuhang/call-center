package com.redcard.message.web.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/message/template")
public class MessageTemplateController {

	private static Logger log = LoggerFactory
			.getLogger(MessageTemplateController.class);

	@RequestMapping(value = "init")
	public String init(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "message/template/list";
	}
}