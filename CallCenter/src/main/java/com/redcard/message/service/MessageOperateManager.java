package com.redcard.message.service;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.google.gson.Gson;
import com.redcard.message.dao.MessageOperateDao;
import com.redcard.message.entity.MessageOperate;

@Component
public class MessageOperateManager extends GenericPageHQLQuery<MessageOperate> {

	private static final String SERVICE_ENDPOTINT_ADDRESS = "http://www.jianzhou.sh.cn/JianzhouSMSWSServer/services/BusinessService?wsdl";

	public static final String SEND_MESSAGE_ACCOUNT = "sdk_jujin";

	public static final String SEND_MESSAGE_PASSWORD = "123456";

	public static final String SEND_MESSAGE_METHOD = "sendBatchMessage";

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
		org.apache.cxf.endpoint.Client client = dcf.createClient(SERVICE_ENDPOTINT_ADDRESS);
		Object[] objects = null;
		logger.info(String
				.format("call sendBatchMessage of BusinessService!SEND_MESSAGE_METHOD:[%s],SEND_MESSAGE_ACCOUNT:[%s],SEND_MESSAGE_PASSWORD:[%s],destmobile:[%s],msgText:[%s]",
						SEND_MESSAGE_METHOD, SEND_MESSAGE_ACCOUNT, SEND_MESSAGE_PASSWORD, destmobile, msgText));
		try {
			objects = client.invoke(SEND_MESSAGE_METHOD, SEND_MESSAGE_ACCOUNT, SEND_MESSAGE_PASSWORD, destmobile,
					msgText);
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
}
