package com.common.security.web.controller;

import com.common.core.grid.AsyncResponse;
import com.common.security.entity.Favorite;
import com.common.security.entity.Node;
import com.common.security.service.FavoriteManager;
import com.common.security.service.NodeManager;
import com.common.security.util.SecurityUtil;
import com.common.security.util.TreeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/security/favorite")
public class FavoriteController {

    private static Logger logger = LoggerFactory.getLogger(FavoriteController.class);

    @Autowired
    private FavoriteManager favoriteManager;

    @Autowired
    private NodeManager nodeManager;

    @RequestMapping(value = "save")
    @ResponseBody
    public AsyncResponse save(Favorite favorite) {
        AsyncResponse result = new AsyncResponse(false, "收藏成功");
        favorite.setUserId(SecurityUtil.getCurrentUserId());
        favorite.setAddTime(new Date());
        favoriteManager.save(favorite);
        return result;
    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public AsyncResponse delete(Integer id) {
        AsyncResponse result = new AsyncResponse(false, "删除用户成功");
        favoriteManager.delete(id);
        return result;
    }

    @RequestMapping(value="getMyFavorite")
    @ResponseBody
    public AsyncResponse getMyFavorite() {
        return (new AsyncResponse(false, "", favoriteManager.findByUserId(SecurityUtil.getCurrentUserId())));
    }

    @RequestMapping(value = "getMyMenu")
    @ResponseBody
    public List<Node> getMyMenu(HttpServletRequest request) {
        Integer userId = SecurityUtil.getCurrentUser(request).getId();
        List<Node> nodes;
        if (userId == 1) {
            //TODO 如果是系统管理员，那么不过滤权限，用角色ID来判断而不是用户ID
            nodes = nodeManager.findAllNode();
        } else {
            nodes = nodeManager.findAuthorisedMenu(userId);
        }

        List<Node> menu = TreeUtil.buildMenuTree(nodes);
        for(Node first : menu){
            for(Node second: first.getChildren()){
                second.setChildren(null);
            }
        }

        return menu;
    }
}
