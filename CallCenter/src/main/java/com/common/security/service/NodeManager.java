package com.common.security.service;

import com.common.Constant;
import com.common.security.dao.FavoriteDao;
import com.common.security.dao.NodeDao;
import com.common.security.entity.Node;
import com.common.security.entity.Privilege;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Transactional(readOnly = true)
public class NodeManager {

    private static Logger logger = LoggerFactory.getLogger(NodeManager.class);

    @Autowired
    private NodeDao nodeDao;

    @Autowired
    private FavoriteDao favoriteDao;

    @Autowired
    private PrivilegeManager privilegeManager;

    public void setNodeDao(NodeDao nodeDao) {
        this.nodeDao = nodeDao;
    }

    public List<Node> findAllNode() {
        return nodeDao.findAllNode();
    }

    public Node findNode(String code) {
        return nodeDao.findOne(code);
    }

    @Transactional(readOnly = false)
    public void saveNode(Node node) {
        nodeDao.save(node);
    }

    @Transactional(readOnly = false)
    public void deleteNode(String code) {
        favoriteDao.deleteByNodeCode(code);
        nodeDao.delete(code);
    }

    public List<Node> findChildren(String parent) {
        return nodeDao.findChildren(parent);
    }

    public String findMaxChildCode(String parent) {
        return nodeDao.findMaxChildCode(parent);
    }

    public List<Node> findAuthorisedButton(String parent, Integer userId) {
        Map<String, Privilege> privileges = privilegeManager.findUserPermission(userId);
        List<Node> buttons = nodeDao.findChildren(parent);
        List<Node> result = new ArrayList<Node>();
        for (Node button : buttons) {
            if (Constant.NODE_TYPE_MENU == button.getType()) {
                continue;
            }
            if (privileges.containsKey(button.getCode())) {
                result.add(button);
            }
        }
        return result;
    }

    public List<Node> findAuthorisedMenu(Integer userId) {
        Map<String, Privilege> privileges = privilegeManager.findUserPermission(userId);
        List<Node> allNodes = nodeDao.findCachedNode();
        List<Node> result = new ArrayList<Node>();
        for (Node node : allNodes) {
            if (Constant.NODE_TYPE_BUTTON == node.getType()) {
                continue;
            }
            if (privileges.containsKey(node.getCode())) {
                result.add(node);
            }
        }
        return result;
    }

    public List<Node> findCachedNode() {
        return nodeDao.findCachedNode();
    }
}
