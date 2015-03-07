package com.redcard.telephone.web.controller;

import com.common.Constant;
import com.common.core.filter.FilterTranslator;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.EntityUtil;
import com.common.security.util.SecurityUtil;
import com.redcard.message.entity.MessageOperate;
import com.redcard.message.entity.MessageSendReturnCodeEnum;
import com.redcard.message.service.MessageOperateManager;
import com.redcard.telephone.common.TelephoneTraceAssignStatusEnum;
import com.redcard.telephone.entity.TelephoneTrace;
import com.redcard.telephone.service.TelephoneTraceManager;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;

/**
 * 数据管理
 * User: Allen
 * Date: 10/28/12
 */
@Controller
@RequestMapping(value = "/telephone/traceAudit")
public class TelephoneTraceAuditController {

    private static Logger logger = LoggerFactory.getLogger(TelephoneTraceAuditController.class);

    @Autowired
    private TelephoneTraceManager telephoneTraceManager;

    @Autowired
    private MessageOperateManager messageOperateManager;

    @Value("#{settingsMap['messageSignFlag']}")
    private String sendMessageSignFlag;

    @RequestMapping(value = "init")
    public String init(String menuNo, Model model) {
        model.addAttribute("menuNo", menuNo);
        return "telephone/traceAudit/list";
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<TelephoneTrace> list(String where, GridPageRequest pageRequest) {
        FilterTranslator filterTranslator = telephoneTraceManager.createFilter(where);
        filterTranslator.addFilterRule("fldAssignStatus", TelephoneTraceAssignStatusEnum.DONE_ASSIGN.getCode(), Constant.FILTER_OP_EQUAL, Constant.FILTER_TYPE_INT);
        Page<TelephoneTrace> dictionaries = telephoneTraceManager.findAll(filterTranslator, pageRequest);
        DataResponse dataResponse = new DataResponse<TelephoneTrace>(dictionaries);
        return dataResponse;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(String ids) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            List<TelephoneTrace> telephoneTraceList = telephoneTraceManager.pass(ids);

            for (TelephoneTrace telephoneTrace : telephoneTraceList) {

                if (StringUtils.isBlank(telephoneTrace.getFinancialUser().getMobile())
                        || telephoneTrace.getFinancialUser().getMobile().length() != 11) {
                    continue;
                }

                MessageOperate messageOperate = new MessageOperate();
                messageOperate.setFldId(EntityUtil.getId());
                messageOperate.setFldContent(String.format("您有一条新的约访:客户姓名[%s],联系电话[%s],备注[%s],客户经理[%s].", telephoneTrace.getFldCustomerName(), StringUtils.isBlank(telephoneTrace.getFldMobile()) == true ? telephoneTrace.getFldPhone() : telephoneTrace.getFldMobile(), telephoneTrace.getFldComment(), telephoneTrace.getCreateName()));
                messageOperate.setFldMessageNum(1);
                int sendResultCode = messageOperateManager.sendMessages(telephoneTrace.getFinancialUser().getMobile(), messageOperate.getFldContent() + sendMessageSignFlag);
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
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

    @RequestMapping(value = "cancel")
    @ResponseBody
    public AsyncResponse update(String ids) {
        AsyncResponse result = new AsyncResponse(false, "保存成功");
        try {
            telephoneTraceManager.nopass(ids);
        } catch (Exception e) {
            e.printStackTrace();
            return new AsyncResponse(true, "系统内部错误，请联系管理员");
        }
        return result;
    }

}
