package com.common.security.util;

import com.common.Constant;
import com.common.core.tree.TreeNode;
import com.common.security.entity.Dept;
import com.common.security.entity.Node;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User: Allen
 * Date: 9/16/12
 */
public class TreeUtil {
    /**
     * 构造部门树
     *
     * @param depts 部门集合
     * @return 部门树根节点
     */
    public static Dept buildDeptTree(List<Dept> depts) {
        Map<String, Dept> map = new HashMap<String, Dept>();

        Dept root = null;
        Dept parent;
        //depts已按parent和position asc的方式排序
        for (Dept dept : depts) {
            if (StringUtils.isEmpty(dept.getParent())) {
                //根节点，有且只有一个
                root = dept;
            } else {
                //普通节点，先根据parent找到父节点，在将其加入父节点的children中
                parent = map.get(dept.getParent());
                if (parent != null) {
                    parent.addChild(dept);
                }
            }
            map.put(dept.getDeptCode(), dept);
        }

        return root;
    }

    /**
     * 构造一级菜单树
     *
     * @param nodes
     * @return 一级菜单
     */
    public static List<Node> buildMenuTree(List<Node> nodes) {
        List<Node> menu = new ArrayList<Node>();

        Map<String, Node> map = new HashMap<String, Node>();
        Node parent;
        //nodes已按parent和position asc的方式排序
        for (Node node : nodes) {
            if (StringUtils.isEmpty(node.getParent())) {
                continue;
            } else if (node.getParent().equals(Constant.NODE_ROOT_CODE)) {
                parent = node;
                menu.add(node);
            } else {
                //普通节点，先根据parent找到父节点，在将其加入父节点的children中
                parent = map.get(node.getParent());
                if (parent != null) {
                    parent.addChild(node);
                }
            }
            map.put(node.getCode(), node);
        }

        return menu;
    }

    /**
     * 构造模块树
     *
     * @param nodes 模块节点集合
     * @return 模块树根节点
     */
    public static Node buildNodeTree(List<Node> nodes) {
        Map<String, Node> map = new HashMap<String, Node>();

        Node root = null;
        Node parent;
        //nodes已按parent和position asc的方式排序
        for (Node node : nodes) {
            if (StringUtils.isEmpty(node.getParent())) {
                //根节点，有且只有一个
                root = node;
            } else {
                //普通节点，先根据parent找到父节点，在将其加入父节点的children中
                parent = map.get(node.getParent());
                if (parent != null) {
                    parent.addChild(node);
                }
            }
            map.put(node.getCode(), node);
        }

        return root;
    }

    public static String getDeptChildCode(String parentCode, String maxChildCode) {
        return getChildCode(parentCode, maxChildCode, 3, "%03d", 1);
    }

    public static String getNodeChildCode(String parentCode, String maxChildCode) {
        return getChildCode(parentCode, maxChildCode, 2, "%02d", 1);
    }

    /**
     * 根据父节点和该父节点下Code最大的子节点自动生成下一个子节点的Code
     *
     * @param parentCode   父节点Code
     * @param maxChildCode 最大子节点Code
     * @param len          单级节点对应的序列号的长度
     * @param format       单级节点对应的序列号的格式化规则
     * @param interval     子节点Code之间的间隔
     * @return 下一个子节点的Code
     */
    public static String getChildCode(String parentCode, String maxChildCode, int len, String format, int interval) {
        int nextIndex = 1;
        if (StringUtils.isNotEmpty(maxChildCode)) {
            String currentIndex = StringUtils.substring(maxChildCode, maxChildCode.length() - len);
            nextIndex = Integer.parseInt(currentIndex) + interval;
        }
        return parentCode + String.format(format, nextIndex);
    }

    /**
     * 构造模块树,include parent children
     *
     * @param nodes 模块节点集合
     * @return 模块树根节点
     */
    public static TreeNode buildNodeTreeV2(List<Node> nodes, Map<String, TreeNode> linkNodeMap) {
        Map<String, TreeNode> map = new HashMap<String, TreeNode>();

        TreeNode root = null;
        TreeNode parent;
        //nodes已按parent和position asc的方式排序
        TreeNode currentNode;
        for (Node node : nodes) {
            currentNode = new TreeNode(node);
            if (StringUtils.isEmpty(node.getParent())) {
                //根节点，有且只有一个
                root = currentNode;
                root.setFullPath("");
            } else {
                //普通节点，先根据parent找到父节点，在将其加入父节点的children中
                parent = map.get(node.getParent());
                if (parent != null) {
                    parent.addChild(currentNode);
                    currentNode.setpNode(parent);
                    currentNode.setFullPath(parent.getFullPath() + "/" + currentNode.getName());
                }
            }
            map.put(node.getCode(), currentNode);
            if (StringUtils.isNotBlank(currentNode.getLink())) {
                linkNodeMap.put(currentNode.getLink(), currentNode);
            }
        }

        return root;
    }
}
