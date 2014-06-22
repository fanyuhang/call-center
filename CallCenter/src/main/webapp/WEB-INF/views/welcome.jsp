<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="./include/taglib.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>欢迎使用聚金理财呼叫中心</title>
    <link href="<c:url value="/static/ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css"/>" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/static/ligerUI/ligerUI/skins/Gray/css/all.css"/>" rel="stylesheet" type="text/css"/>
    <script type="text/javascript">
        var GLOBAL_CTX = '${ctx}';
    </script>
    <script src="<c:url value="/static/ligerUI/jquery/jquery-1.8.3.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/ligerUI/js/ligerui.min.js"/>" type="text/javascript"></script>
    <link href="<c:url value="/static/ligerUI/css/common.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/ligerUI/css/welcome.css"/>" rel="stylesheet" type="text/css"/>

    <script src='<c:url value="/static/ligerUI/jquery-validation/jquery.validate.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/additional-methods.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/messages_zh.js"/>' type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/jquery.form.js"/>" type="text/javascript"></script>

    <script src="<c:url value="/static/ligerUI/js/common.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/LG.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/addfavorite.js"/>" type="text/javascript"></script>
</head>
<body style="padding:10px; overflow:auto; text-align:center;background:#FFFFFF;">
<div class="navbar">
    <div class="navbar-l"></div>
    <div class="navbar-r"></div>
    <div class="navbar-icon"><img src="<c:url value="/static/ligerUI/icons/32X32/hire_me.gif"/>"/></div>
    <div class="navbar-inner">
        <b><span id="labelusername"></span><span>，</span><span id="labelwelcome"></span><span>欢迎使用聚金理财呼叫中心</span></b>
        <a href="javascript:void(0)" id="usersetup" style="display:none">账号设置</a>
    </div>
</div>

<div class="withicon">
    <div class="icon"><img src="<c:url value="/static/ligerUI/images/index/time.gif"/>"/></div>
    <span id="labelLastLoginTime"></span>
</div>


<div class="navline">
</div>

<div class="links">
</div>

<div class="l-clear"></div>

<div class="button" onclick="LG.addfavorite(loadMyFavorite)">
    <div class="button-l"></div>
    <div class="button-r"></div>
    <div class="button-icon"><img src="<c:url value="/static/ligerUI/ligerUI/skins/icons/add.gif"/>"/></div>
    <span>增加快捷方式</span>
</div>


<div class="navbar">
    <div class="navbar-l"></div>
    <div class="navbar-r"></div>
    <div class="navbar-icon"><img src="<c:url value="/static/ligerUI/icons/32X32/collaboration.gif"/>"/></div>
</div>

    <script type="text/javascript">
        $("div.link").live("mouseover",
                function () {
                    $(this).addClass("linkover");

                }).live("mouseout",
                function () {
                    $(this).removeClass("linkover");


                }).live('click', function (e) {
                    var text = $("a", this).html();
                    var url = $(this).attr("url");
                    parent.f_addTab(null, text, url);
                });

        $("div.link .close").live("mouseover",
                function () {
                    $(this).addClass("closeover");
                }).live("mouseout",
                function () {
                    $(this).removeClass("closeover");
                }).live('click', function () {
                    var id = $(this).parent().attr("id");

                    LG.ajax({
                        loading: '正在删除用户收藏中...',
                        url: '<c:url value="/security/favorite/delete"/>',
                        data: { id: id },
                        success: function () {
                            loadMyFavorite();
                        },
                        error: function (message) {
                            LG.showError(message);
                        }
                    });

                    return false;
                });


        var links = $(".links");


        function loadMyFavorite() {
            LG.ajax({
                loading: '正在加载用户收藏中...',
                url: '<c:url value="/security/favorite/getMyFavorite"/>',
                success: function (Favorite) {
                    links.html("");
                    $(Favorite).each(function (i, data) {
                        var item = $('<div class="link"><img src="" /><a href="javascript:void(0)"></a><div class="close"></div></div>');
                        $("img", item).attr("src", GLOBAL_CTX+data.icon);
                        $("a", item).html(data.title);
                        item.attr("id", data.id);
                        item.attr("url", GLOBAL_CTX+data.url+'?menuNo='+data.nodeCode);
                        links.append(item);
                    });
                },
                error: function (message) {
                    LG.showError(message);
                }
            });
        }

        function loadInfo() {
            LG.ajax({
                url:'<c:url value="/user/getCurrentUser"/>',
                success: function (user) {
                    $("#labelusername").html(user[0].userName);
                    var lastlogintime = user[0].lastLoginTime || "从不";
                    $("#labelLastLoginTime").html("您上次的登录时间是：" + lastlogintime);
                },
                error: function () {
                    LG.tip('用户信息加载失败');
                }
            });


            var now = new Date(), hour = now.getHours();
            if (hour > 4 && hour < 6) {
                $("#labelwelcome").html("凌晨好！")
            }
            else if (hour < 9) {
                $("#labelwelcome").html("早上好！")
            }
            else if (hour < 12) {
                $("#labelwelcome").html("上午好！")
            }
            else if (hour < 14) {
                $("#labelwelcome").html("中午好！")
            }
            else if (hour < 17) {
                $("#labelwelcome").html("下午好！")
            }
            else if (hour < 19) {
                $("#labelwelcome").html("傍晚好！")
            }
            else if (hour < 22) {
                $("#labelwelcome").html("晚上好！")
            }
            else {
                $("#labelwelcome").html("夜深了，注意休息！")
            }

            $("#usersetup").click(function () {
                var url = $(this).attr("url");
                if (!url) return;
                var text = "修改用户信息";
                parent.f_addTab(null, text, url);
            });
        }

        loadInfo();
        loadMyFavorite();
    </script>
</body>
</html>
