package com.redcard.ws.service;

import com.common.AppContext;
import com.common.core.util.JsonHelper;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.service.ContractManager;
import com.redcard.customer.service.CustomerManager;
import com.redcard.ws.WSResult;
import com.redcard.ws.entity.CustomerContract;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by thinkpad on 14-7-20.
 */
@WebService(endpointInterface = "com.redcard.ws.service.IService")
@Component
public class JuJinService implements IService {

    private static Logger logger = LoggerFactory.getLogger(JuJinService.class);

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private ContractManager contractManager;

    @Override
    public String findCustomerContractByMobile(String mobile) {
        WSResult<List<CustomerContract>> result = new WSResult<List<CustomerContract>>("查询成功");
        result.setErrorCode(0);
        if (StringUtils.isBlank(mobile)) {
            result.setErrorMessage("手机号不能为空");
            result.setErrorCode(1);
            return JsonHelper.serialize(result);
        }

        try {

            List<CustomerContract> customerContractList = new ArrayList<CustomerContract>();

            List<Customer> customerList = customerManager.findCustomerByMobile(mobile);

            if (customerList == null || customerList.size() == 0) {
                result.setErrorMessage("不存在对应的客户信息");
                result.setErrorCode(2);
                return JsonHelper.serialize(result);
            }

            List<String> customerIds = new ArrayList<String>();

            for (Customer customer : customerList) {
                customerIds.add(customer.getFldId());
            }

            List<com.redcard.customer.entity.CustomerContract> contractList = contractManager.findAllContractByCustomerId(customerIds);
            CustomerContract customerContract = null;
            for (com.redcard.customer.entity.CustomerContract contract : contractList) {
                customerContract = new CustomerContract();
                customerContract.setCustomerName(contract.getCustomerName());
                customerContract.setIdCard(contract.getCustomer().getFldIdentityNo());
                customerContract.setMobile(contract.getCustomer().getFldMobile());
                customerContract.setPhone(contract.getCustomer().getFldPhone());
                customerContract.setProductType(AppContext.getInstance().getDictName(7, contract.getProductDetail().getCustomerProduct().getFldType()+""));
                customerContract.setAnnualizedRate(contract.getProductDetail().getFldAnnualizedRate());
                customerContract.setAnnualizedMoney(contract.getFldAnnualizedMoney());
                customerContract.setDeadLineUnit(AppContext.getInstance().getDictName(14, contract.getProductDetail().getFldDayUnit() + ""));
                customerContract.setDueDate(DateFormatUtils.format(contract.getProductDetail().getFldDueDate(),"yyyy-MM-dd"));
                customerContract.setSignDate(DateFormatUtils.format(contract.getFldSignDate(),"yyyy-MM-dd"));
                customerContract.setMoneyDate(DateFormatUtils.format(contract.getFldMoneyDate(),"yyyy-MM-dd"));
                customerContract.setProductDeadLine(contract.getProductDetail().getFldClearDays());
                customerContract.setProductName(contract.getProductFullName());
                customerContract.setPurchaseMoney(contract.getFldPurchaseMoney());
                customerContractList.add(customerContract);
            }

            result.setContent(customerContractList);

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("系统内部错误：" + e.getMessage());
            result.setErrorCode(3);
            result.setErrorMessage(e.getMessage());
        }

        return JsonHelper.serialize(result);
    }
}
