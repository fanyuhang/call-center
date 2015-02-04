package com.phone.service;

import com.common.core.grid.GridPageRequest;
import com.common.core.util.GenericPageHQLQuery;
import com.common.core.util.GenericPageHQLQueryForPhone;
import com.common.security.entity.User;
import com.phone.constants.PhoneTypeEnum;
import com.phone.dao.OperatorDao;
import com.phone.dao.RolerightDao;
import com.phone.dao.UidroleDao;
import com.phone.entity.Operator;
import com.phone.entity.Roleright;
import com.phone.entity.Uidrole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * .
 * User: Administrator
 * Date: 12-9-6
 * Time: 上午12:09
 */
@Component
@Transactional(readOnly = true)
public class OperatorManager extends GenericPageHQLQueryForPhone<Operator> {
    private static Logger logger = LoggerFactory.getLogger(OperatorManager.class);
    @Autowired
    private OperatorDao operatorDao;

    @Autowired
    private UidroleDao uidroleDao;

    public List<Operator> findAll() {
        return (List<Operator>) operatorDao.findAll();
    }

    public Page<Operator> find(String where, GridPageRequest page) {
        return (Page<Operator>) super.findAll(where, page);
    }

    public Operator find(int id) {
        return operatorDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(List<Operator> jiFenExchanges) {
        operatorDao.save(jiFenExchanges);
    }

    @Transactional(readOnly = false)
    public void save(Operator entity) {
        operatorDao.save(entity);
    }

    public Operator findByUid(String uid){
        return operatorDao.findByUid(uid);
    }

    //@TODO 必须指定transaction manager，否则不能使用自定义dao的事务
    @Transactional(value="phoneTransactionManager",readOnly = false)
    public void save(User user) {
        if (user.getPhoneType() == null || PhoneTypeEnum.NONE.getCode().equals(user.getPhoneType()) == true) {
            return;
        }

        Operator operator = operatorDao.findByUid(user.getLoginName());
        if (operator == null) {
            operator = new Operator();
            operator.setUid(user.getLoginName());
        }
        operator.setPwd(user.getPhonePassword());
        operator.setName(user.getUserName());
        operator.setPausedir(3);

        operatorDao.save(operator);
        uidroleDao.deleteByUid(operator.getUid());

        Uidrole uidrole = new Uidrole();
        uidrole.setUid(operator.getUid());
        if (PhoneTypeEnum.ORDINARY.getCode().equals(user.getPhoneType()) == true) {
            uidrole.setRoleid(3);
        } else {
            uidrole.setRoleid(2);
        }
        uidroleDao.save(uidrole);
    }

    @Transactional(value="phoneTransactionManager",readOnly = false)
    public void delete(String loginName){
        uidroleDao.deleteByUid(loginName);
        operatorDao.deleteByUid(loginName);
    }

    @Transactional(readOnly = false)
    public void delete(int id) {
        operatorDao.delete(id);
    }
}
