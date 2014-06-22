package com.common.security.service;

import com.common.core.util.GenericPageHQLQuery;
import com.common.security.dao.PrimaryKeySequenceDao;
import com.common.security.entity.PrimaryKeySequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * User: xqingli
 * Date: 1/29/13
 * Time: 10:21 PM
 */
@Component
@Transactional(readOnly = true)
public class PrimaryKeyManager extends GenericPageHQLQuery<PrimaryKeySequence> {

    private String appNode;

    @Value("${app.node}")
    public void setAppNode(String appNode) {
        this.appNode = appNode;
    }

    private static Logger logger = LoggerFactory.getLogger(PrimaryKeyManager.class);

    @Autowired
    private PrimaryKeySequenceDao primaryKeySequenceDao;

    public PrimaryKeySequence findOne(Integer id) {
        return primaryKeySequenceDao.findOne(id);
    }

    public List<PrimaryKeySequence> findAll() {
        return (List<PrimaryKeySequence>) primaryKeySequenceDao.findAll();
    }

    @Transactional(readOnly = false)
    public void save(PrimaryKeySequence sequence) {
        primaryKeySequenceDao.save(sequence);
    }

    public synchronized Long getOrderId(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
        String orderIdPrefix = sdf.format(date);
        String orderIdSuffix =  appNode+"00001";
        return new Long(orderIdPrefix+orderIdSuffix);
    }

    @Transactional(readOnly = false)
    public synchronized Long getNextOrderId() {
        Long curOrderId;
        PrimaryKeySequence sequence = primaryKeySequenceDao.findOne(1);
        if (sequence != null) {
            curOrderId = sequence.getOrderSequence();
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
            String orderIdPrefix = sdf.format(new Date());
            String orderIdSuffix = appNode+"00000";
            curOrderId = new Long(orderIdPrefix + orderIdSuffix);
        }
        System.out.println("PrimaryKeyManager get order Id:"+curOrderId);
        curOrderId = curOrderId+1;
        primaryKeySequenceDao.save(new PrimaryKeySequence(1, curOrderId));

        return curOrderId;
    }
}
