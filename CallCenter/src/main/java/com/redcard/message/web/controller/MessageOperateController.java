package com.redcard.message.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.redcard.message.service.MessageOperateManager;

@Controller
@RequestMapping(value = "/message/operate")
public class MessageOperateController {

	// private static Logger log =
	// LoggerFactory.getLogger(MessageOperateController.class);

	@Autowired
	private MessageOperateManager messageOperateManager;

	@RequestMapping(value = "init")
	public String init(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "message/operate/list";
	}

	/**
	 * 批量发送短信
	 * */
	@RequestMapping(value = "add")
	public String add(String menuNo, Model model) {
		model.addAttribute("menuNo", menuNo);
		return "message/operate/add";
	}
}