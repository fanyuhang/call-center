package com.redcard.ws.service;

import com.redcard.customer.entity.CustomerContract;
import com.redcard.ws.WSResult;

import javax.jws.WebService;

/**
 * Created by thinkpad on 14-7-20.
 */
@WebService
public interface IService {

    public String findCustomerContractByMobile(String mobile);
}
