package com.redcard.ws.interceptor;

import com.common.security.entity.User;
import com.common.security.service.UserManager;
import org.apache.commons.lang.StringUtils;
import org.apache.ws.security.WSPasswordCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import java.io.IOException;

/**
 * User: Allen
 * Date: 1/20/13
 */
public class ServerPasswordCallback implements CallbackHandler {

    private static Logger logger = LoggerFactory.getLogger(ServerPasswordCallback.class);

    @Autowired
    private UserManager userManager;

    /**
     * @param userName .
     * @return password from db
     */
    private String getPassword(String userName) {
        User user = userManager.findUserByLoginName(userName);
        return user == null ? "admin888" : user.getPassword();
    }

    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        WSPasswordCallback pc = (WSPasswordCallback) callbacks[0];
        String userName = pc.getIdentifier();
        logger.debug("Get request from user {}", userName);
        String password = getPassword(userName);
        if (StringUtils.isNotBlank(password)) {
            pc.setPassword(password);
        }
    }
}
