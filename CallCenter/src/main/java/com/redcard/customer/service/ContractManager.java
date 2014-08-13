package com.redcard.customer.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.redcard.customer.entity.CustomerProductDetail;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.common.Constant;
import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.customer.dao.ContractDao;
import com.redcard.customer.dao.CustomerDao;
import com.redcard.customer.dao.CustomerProductDetailDao;
import com.redcard.customer.entity.Customer;
import com.redcard.customer.entity.CustomerContract;

@Component
@Transactional(readOnly = true)
public class ContractManager extends GenericPageHQLQuery<CustomerContract> {
    @Autowired
    private ContractDao contractDao;
    @Autowired
    private CustomerProductDetailDao customerProductDetailDao;
    @Autowired
    private CustomerDao customerDao;

    public Page<CustomerContract> findAllContract(GridPageRequest page, String where) {
        return (Page<CustomerContract>) super.findAll(where, page);
    }

    public List<CustomerContract> findAllContractByCustomerId(List<String> customerId) {
        return contractDao.findAllContractByCustomerId(customerId);
    }

    @Transactional(readOnly = false)
    public void save(CustomerContract customerContract) {
        CustomerProductDetail customerProductDetail = customerProductDetailDao.findOne(customerContract.getFldProductDetailId());

        Double oldContractCardMoney = 0d;
        if (StringUtils.isNotBlank(customerContract.getFldId())) {
            CustomerContract oldCustomerContract = contractDao.findOne(customerContract.getFldId());
            oldContractCardMoney = oldCustomerContract.getFldCardMoney()==null?0:oldCustomerContract.getFldCardMoney();
        }
        String fldProductId = customerProductDetail.getFldProductId();
        customerContract.setFldProductId(fldProductId);
        //更新是否到期
        if (DateUtils.truncatedCompareTo(customerProductDetail.getFldDueDate(), new Date(), Calendar.DATE) > 0) {
            customerContract.setFldFinishStatus(Constant.CONTRACT_FINISH_STATUS_NO);
        } else {
            customerContract.setFldFinishStatus(Constant.CONTRACT_FINISH_STATUS_YES);
        }
        contractDao.save(customerContract);

        //更新客户的卡相关信息
        Customer customer = customerDao.findOne(customerContract.getFldCustomerId());
        customer.setFldCardLevel(customerContract.getFldCardLevel());
        customer.setFldCardNo(customerContract.getFldCardNo());
        if (null != customerContract.getFldCardMoney()) {
            if (customer.getFldCardTotalMoney() == null) {
                customer.setFldCardTotalMoney(customerContract.getFldCardMoney());
            } else {
                customer.setFldCardTotalMoney(customer.getFldCardTotalMoney() + customerContract.getFldCardMoney() - oldContractCardMoney);
            }
        }
        customerDao.save(customer);
    }

    @Transactional(readOnly = false)
    public void saveOnly(CustomerContract customerContract) {
        contractDao.save(customerContract);
    }

    @Transactional(readOnly = false)
    public void updateFinishStatus() {
        List<CustomerContract> customerContractList = contractDao.queryByDueDate(DateUtils.truncate(new Date(), Calendar.DATE));
        for(CustomerContract customerContract:customerContractList){
            customerContract.setFldFinishStatus(1);
        }
        contractDao.save(customerContractList);
    }

    public CustomerContract find(String fldId) {
        return contractDao.findOne(fldId);
    }

    @Transactional(readOnly = false)
    public void delete(String fldId) {
        CustomerContract customerContract = contractDao.findOne(fldId);
        customerContract.setFldStatus(Constant.CONTRACT_STATUS_DIABLED);
        customerContract.setFldOperateDate(new Date());
        contractDao.save(customerContract);
    }
}
