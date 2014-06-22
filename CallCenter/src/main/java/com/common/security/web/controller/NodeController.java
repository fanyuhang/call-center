package com.common.security.web.controller;

import com.common.AppContext;
import com.common.Constant;
import com.common.core.grid.AsyncResponse;
import com.common.core.grid.DataResponse;
import com.common.security.entity.Node;
import com.common.security.service.NodeManager;
import com.common.security.util.SecurityUtil;
import com.common.security.util.TreeUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/security/node")
public class NodeController {

    private static Logger logger = LoggerFactory.getLogger(NodeController.class);

    @Autowired
    private NodeManager nodeManager;

    @RequestMapping(value = "init")
    public String init() {
        return "security/node/menu";
    }

    @RequestMapping(value = "getNextChild")
    @ResponseBody
    public Node getNextChild(String parent) {
        Node node = new Node();
        String maxChildCode = nodeManager.findMaxChildCode(parent);
        node.setCode(TreeUtil.getNodeChildCode(parent, maxChildCode));
        return node;
    }

    @RequestMapping(value = "tree")
    @ResponseBody
    public List<Node> tree(HttpServletRequest request, String code, String rooticon) {
        String context = SecurityUtil.getContext(request);
        List<Node> nodes = nodeManager.findChildren(Constant.NODE_ROOT_CODE);

        List<Node> menu = new ArrayList<Node>();
        Node parent = new Node();
        parent.setCode(code);
        parent.setName(AppContext.getInstance().getSysParameterValue(Constant.PARAM_NODE_ROOT_NAME));
        parent.setIcon(rooticon);
        for (Node node : nodes) {
            node.setIcon(context + node.getIcon());
            parent.addChild(node);
        }
        menu.add(parent);
        return menu;
    }

    @RequestMapping(value = "list")
    @ResponseBody
    public DataResponse<Node> list(String code) {
        List<Node> nodes;
        if (StringUtils.isEmpty(code)) {
            nodes = nodeManager.findAllNode();
        } else {
            nodes = nodeManager.findChildren(code);
        }

        DataResponse dataResponse = new DataResponse<Node>(nodes);
        dataResponse.setTotal(nodes.size());
        return dataResponse;
    }

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(Node node) {
        AsyncResponse result = new AsyncResponse(false, "保存模块成功");
        nodeManager.saveNode(node);
        return result;
    }

    @RequestMapping(value = "update")
    @ResponseBody
    public AsyncResponse update(Node node) {
        AsyncResponse result = new AsyncResponse(false, "保存模块成功");
        nodeManager.saveNode(node);
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(String code) {
        AsyncResponse result = new AsyncResponse(false, "删除模块成功");
        nodeManager.deleteNode(code);
        return result;
    }

    @RequestMapping(value = "initButtons")
    public String initButtons(String parent, Model model) {
        model.addAttribute("parent", parent);
        return "security/node/buttons";
    }

    @RequestMapping(value = "listButton")
    @ResponseBody
    public DataResponse<Node> listButton(String parent) {
        List<Node> nodes;
        if (StringUtils.isEmpty(parent)) {
            nodes = new ArrayList<Node>();
        } else {
            nodes = nodeManager.findChildren(parent);
        }

        DataResponse dataResponse = new DataResponse<Node>(nodes);
        dataResponse.setTotal(nodes.size());
        return dataResponse;
    }

    @RequestMapping(value = "treeGrid")
    @ResponseBody
    public AsyncResponse treeGrid() {
        //构造模块节点树形列表
        List<Node> nodes = nodeManager.findAllNode();
        Node tree = TreeUtil.buildNodeTree(nodes);

        AsyncResponse asyncResponse = new AsyncResponse(false, "加载菜单成功");
        asyncResponse.addData(tree.getChildren());
        return asyncResponse;
    }
}
