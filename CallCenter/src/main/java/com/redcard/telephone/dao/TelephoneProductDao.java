package com.redcard.telephone.dao;

import com.redcard.system.entity.Notice;
import com.redcard.telephone.entity.TelephoneProduct;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * User: Allen
 * Date: 11/25/12
 */
public interface TelephoneProductDao extends PagingAndSortingRepository<TelephoneProduct, Integer> {
}
