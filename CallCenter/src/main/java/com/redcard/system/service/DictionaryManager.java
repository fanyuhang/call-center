package com.redcard.system.service;

import com.common.core.util.GenericPageHQLQuery;
import com.redcard.system.dao.DictionaryDao;
import com.redcard.system.entity.Dictionary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: Allen
 * Date: 10/28/12
 */

@Component
public class DictionaryManager extends GenericPageHQLQuery<Dictionary> {

    private static Logger logger = LoggerFactory.getLogger(DictionaryManager.class);

    @Autowired
    private DictionaryDao dictionaryDao;

    public void setDictionaryDao(DictionaryDao dictionaryDao) {
        this.dictionaryDao = dictionaryDao;
    }

    public List<Dictionary> findAllDictionary() {
        return this.dictionaryDao.findAllDictionary();
    }

    public List<Dictionary> findCachedDictionary() {
        return this.dictionaryDao.findCachedDictionary();
    }

    public List<Dictionary> findDictionaryByType(Integer type) {
        return this.dictionaryDao.findDictionaryByType(type);
    }

    public Dictionary find(Integer id) {
        return dictionaryDao.findOne(id);
    }

    @Transactional(readOnly = false)
    public void save(Dictionary entity) {
        dictionaryDao.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(Integer id) {
        dictionaryDao.delete(id);
    }

    /**
     * 校验入参dictionary是否已在数据库中有相同type和name的记录
     *
     * @param dictionary 待校验dictionary
     * @return true 表示有相同type和name的记录
     */
    public boolean isExistDictionary(Dictionary dictionary) {
        List<Dictionary> list = dictionaryDao.findDictionaryByTypeAndValue(dictionary.getType(), dictionary.getValue());
        if (list.size() > 0) {
            Dictionary dbDictionary = list.get(0);
            if (dbDictionary.getValue().equals(dictionary.getValue()) && dbDictionary.getType().equals(dictionary.getType()) && !dbDictionary.getId().equals(dictionary.getId())) {
                return true;
            }
        }
        return false;
    }
}
