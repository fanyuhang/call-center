package com.redcard.message.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.google.gson.Gson;
import com.redcard.message.dao.MessageOperateDao;
import com.redcard.message.entity.MessageOperate;

import java.util.List;

@Component
public class MessageOperateManager extends GenericPageHQLQuery<MessageOperate> {

    @Value("#{settingsMap['messageEndPointAddress']}")
    private String serviceEndpotintAddress;

    @Value("#{settingsMap['messageAccountName']}")
    private String sendMessageAccount;

    @Value("#{settingsMap['messageAccountPassword']}")
    private String sendMessagePassword;

    @Value("#{settingsMap['messageMethodName']}")
    private String sendMessageMethod;

    private static Logger logger = LoggerFactory.getLogger(MessageOperateManager.class);

    @Autowired
    private MessageOperateDao messageOperateDao;

    @Transactional(readOnly = false)
    public void save(MessageOperate messageOperate) {
        messageOperateDao.save(messageOperate);
    }

    public MessageOperate find(String fldId) {
        return messageOperateDao.findOne(fldId);
    }

    public Page<MessageOperate> queryMessageOperates(GridPageRequest page, String where) {
        return (Page<MessageOperate>) super.findAll(where, page);
    }

    public int sendMessages(String destmobile, String msgText) {
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        org.apache.cxf.endpoint.Client client = dcf.createClient(serviceEndpotintAddress);
        Object[] objects = null;
        logger.info(String
                .format("call sendBatchMessage of BusinessService!SEND_MESSAGE_METHOD:[%s],SEND_MESSAGE_ACCOUNT:[%s],SEND_MESSAGE_PASSWORD:[%s],destmobile:[%s],msgText:[%s]",
                        sendMessageMethod, sendMessageAccount, sendMessagePassword, destmobile, msgText));
        try {
            objects = client.invoke(sendMessageMethod, sendMessageAccount, sendMessagePassword, destmobile, msgText);
        } catch (Exception e) {
            logger.error("call sendBatchMessage catched exception!", e);
        }
        logger.info("call sendBatchMessage of BusinessService result is :" + new Gson().toJson(objects));
        if (ArrayUtils.isEmpty(objects)) {
            return -100;// 表示系统接口调用异常
        } else {
            return (Integer) objects[0];
        }
    }

    public void setServiceEndpotintAddress(String serviceEndpotintAddress) {
        this.serviceEndpotintAddress = serviceEndpotintAddress;
    }

    public void setSendMessagePassword(String sendMessagePassword) {
        this.sendMessagePassword = sendMessagePassword;
    }

    public void setSendMessageMethod(String sendMessageMethod) {
        this.sendMessageMethod = sendMessageMethod;
    }

    public void setSendMessageAccount(String sendMessageAccount) {
        this.sendMessageAccount = sendMessageAccount;
    }
}
