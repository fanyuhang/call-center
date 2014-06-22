package com.redcard.system.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * User: Allen
 * Date: 11/24/12
 */
@Component
@Transactional(readOnly = true)
public class SignatureManager {

    private static Logger logger = LoggerFactory.getLogger(SignatureManager.class);
}
