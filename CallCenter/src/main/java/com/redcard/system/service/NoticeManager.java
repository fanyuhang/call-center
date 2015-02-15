package com.redcard.system.service;

import com.common.core.util.GenericPageHQLQuery;
import com.common.security.util.SecurityUtil;
import com.redcard.system.common.NoticeStatusEnum;
import com.redcard.system.dao.NoticeDao;
import com.redcard.system.entity.Notice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * User: Allen
 * Date: 10/28/12
 */

@Component
public class NoticeManager extends GenericPageHQLQuery<Notice> {

    private static Logger logger = LoggerFactory.getLogger(NoticeManager.class);

    @Autowired
    private NoticeDao noticeDao;

    public Notice find(Integer id) {
        return noticeDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(Notice entity) {
        if (entity.getFldId() != null) {
            Notice oldNotice = noticeDao.findOne(entity.getFldId());
            oldNotice.setFldStatus(entity.getFldStatus());
            oldNotice.setFldTitle(entity.getFldTitle());
            oldNotice.setFldContent(entity.getFldContent());
            oldNotice.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            oldNotice.setFldOperateDate(new Date());
            noticeDao.save(oldNotice);
        } else {
            entity.setFldOperateDate(new Date());
            entity.setFldOperateUserNo(SecurityUtil.getCurrentUserLoginName());
            entity.setFldCreateDate(new Date());
            entity.setFldCreateUserNo(SecurityUtil.getCurrentUserLoginName());
            noticeDao.save(entity);
        }
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        Notice notice = noticeDao.findOne(id);
        notice.setFldStatus(NoticeStatusEnum.DISABLE.getCode());
        noticeDao.save(notice);
    }
}
