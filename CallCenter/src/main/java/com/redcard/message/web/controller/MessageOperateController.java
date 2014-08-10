package com.redcard.message.web.controller;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.message.entity.MessageOperate;
import com.redcard.message.entity.MessageSendReturnCodeEnum;
import com.redcard.message.service.MessageOperateManager;

@Controller
@RequestMapping(value = "/message/operate")
public class MessageOperateController {

    // private static Logger log =
    // LoggerFactory.getLogger(MessageOperateController.class);

    @Autowired
    private MessageOperateManager messageOperateManager;

    @Value("#{settingsMap['messageSignFlag']}")
    private String sendMessageSignFlag;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "message/operate/list";
    }

    /**
     * 短信发送记录查询
     */
    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<MessageOperate> list(GridPageRequest pageRequest, String where) {
        pageRequest.setSort("fldOperateDate", "desc");
        return (new DataResponse<MessageOperate>(messageOperateManager.queryMessageOperates(pageRequest, where)));
    }

    /**
     * 批量发送短信
     */
    @RequestMapping(value = "add")
    public String add(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("sendMessageSignFlag", sendMessageSignFlag);
        return "message/operate/add";
    }

    /**
     * 短信发送
     */
    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(MessageOperate messageOperate) {
        AsyncResponse result = new AsyncResponse(false, "短信发送成功！");
        messageOperate.setFldId(EntityUtil.getId());
        String fldMobileNos = messageOperate.getFldMobiles();// 手机号码列表
        String[] mobileNos = fldMobileNos.split(";");
        if (ArrayUtils.isNotEmpty(mobileNos)) {
            messageOperate.setFldMessageNum(mobileNos.length);
        }
        int sendResultCode = messageOperateManager.sendMessages(fldMobileNos, messageOperate.getFldContent());
        if (sendResultCode > 0) {// 提交成功
            messageOperate.setFldSendStatus(Constant.MESSAGE_SEND_STATUS_SUCCESS);
            messageOperate.setFldSendResult(MessageSendReturnCodeEnum.SUCCESS.getDesc());
            messageOperate.setFldTaskId(String.valueOf(sendResultCode));
        } else {
            messageOperate.setFldSendStatus(Constant.MESSAGE_SEND_STATUS_FAIL);
            messageOperate.setFldSendResult(MessageSendReturnCodeEnum.getDescByCode(sendResultCode));
            messageOperate.setFldTaskId(StringUtils.EMPTY);
            result.setIsError(true);
            result.setMessage("短信发送失败！");
        }
        messageOperate.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
        messageOperate.setFldCreateDate(new Date());
        messageOperate.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
        messageOperate.setFldOperateDate(new Date());
        messageOperateManager.save(messageOperate);
        return result;
    }

    @RequestMapping(value = "view")
    public String view(String menuNo, String fldId, Model model) {
        MessageOperate messageOperate = messageOperateManager.find(fldId);
        model.addAttribute("menuNo", menuNo);
        model.addAttribute("messageOperate", messageOperate);
        return "message/operate/view";
    }

    public String getSendMessageSignFlag() {
        return sendMessageSignFlag;
    }

    public void setSendMessageSignFlag(String sendMessageSignFlag) {
        this.sendMessageSignFlag = sendMessageSignFlag;
    }
}