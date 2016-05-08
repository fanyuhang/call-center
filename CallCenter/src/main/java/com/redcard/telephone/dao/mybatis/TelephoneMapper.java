package com.redcard.telephone.dao.mybatis;

import com.redcard.report.entity.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface TelephoneMapper {

    public void updateTelephoneAssignDetailFinishNumber(Map<String, Object> param);

    public void updateTelephoneAssignDetailFollowNumber(Map<String, Object> param);

    public void updateTelephoneAssignDetailFinishStatus(Map<String, Object> param);

}
