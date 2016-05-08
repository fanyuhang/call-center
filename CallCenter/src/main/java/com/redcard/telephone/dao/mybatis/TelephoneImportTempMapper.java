package com.redcard.telephone.dao.mybatis;

import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface TelephoneImportTempMapper {

    public void updateDuplicateStatusWithPhone(Map<String, Object> param);

    public void updateDuplicateStatusWithMobile(Map<String, Object> param);

}
