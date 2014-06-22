package com.common.core.tree;

import com.common.security.entity.Node;
import org.springframework.beans.BeanUtils;

/**
 * 树形节点
 */
public class TreeNode extends Node {

    private TreeNode pNode;

    private String fullPath;

    public TreeNode() {
    }

    public TreeNode(Node node) {
        BeanUtils.copyProperties(node, this);
    }

    public TreeNode getpNode() {
        return pNode;
    }

    public void setpNode(TreeNode pNode) {
        this.pNode = pNode;
    }

    public String getFullPath() {
        return fullPath;
    }

    public void setFullPath(String fullPath) {
        this.fullPath = fullPath;
    }
}
