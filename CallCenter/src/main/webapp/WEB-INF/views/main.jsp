<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="./include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>欢迎使用聚金理财呼叫中心</title>
    <link href="<c:url value="/static/ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css"/>" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/static/ligerUI/ligerUI/skins/Gray/css/all.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<c:url value="/static/ligerUI/jquery/jquery-1.8.3.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/ligerUI/js/ligerui.min.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerTab.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerLayout.js" />" type="text/javascript"></script>
    <link href="<c:url value="/static/ligerUI/css/common.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/static/ligerUI/css/index.css"/>" rel="stylesheet" type="text/css"/>
    <script src="<c:url value="/static/ligerUI/js/common.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/LG.js"/>" type="text/javascript"></script>
    <script type="text/javascript">
        var GLOBAL_CTX = '${ctx}';
    </script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/jquery.validate.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/additional-methods.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/messages_zh.js"/>' type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/changepassword.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerForm.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/validator.js"/>" type="text/javascript"></script>
</head>
<body style="text-align:center; background:#F0F0F0; overflow:hidden;">
<div id="pageloading" style="display:block;"></div>
<div id="topmenu" class="l-topmenu">
    <div class="l-topmenu-logo">聚金理财呼叫中心</div>
    <div class="l-topmenu-welcome">
        <span class="l-topmenu-username"></span>欢迎您 &nbsp;
        [<a href="javascript:f_changepassword()">修改密码</a>] &nbsp;
        [<a href="<c:url value="/logout"/>">退出</a>]
    </div>

</div>
<div id="mainbody" class="l-mainbody" style="width:99.2%; margin:0 auto; margin-top:3px;">
    <div position="left" title="主要菜单" id="mainmenu"></div>
    <div position="center" id="framecenter">
        <div tabid="home" title="我的主页">
            <iframe frameborder="0" name="home" id="home" src="<c:url value="/welcome"/>"></iframe>
        </div>
    </div>
</div>
<script type="text/javascript">
    //几个布局的对象
    var layout, tab, accordion;
    //tabid计数器，保证tabid不会重复
    var tabidcounter = 0;
    //窗口改变时的处理函数
    function f_heightChanged(options) {
        if (tab)
            tab.addHeight(options.diff);
        if (accordion && options.middleHeight - 24 > 0)
            accordion.setHeight(options.middleHeight - 24);
    }
    //增加tab项的函数
    function f_addTab(tabid, text, url) {
        if (!tab) return;
        if (!tabid) {
            tabidcounter++;
            tabid = "tabid" + tabidcounter;
        }

        if (url.indexOf('tabid=') == -1) {
            if (url.indexOf('?') > -1) url += "&";
            else url += "?";
            url += "tabid=" + tabid;
        }

        tab.addTabItem({ tabid: tabid, text: text, url: url });
    }
    //登录
    function f_login() {
        LG.login();
    }
    //修改密码
    function f_changepassword() {
        LG.changepassword();
    }
    $(document).ready(function () {

        //菜单初始化
        $("ul.menulist li").live('click',
                function () {
                    var jitem = $(this);
                    var tabid = jitem.attr("tabid");
                    var url = jitem.attr("url");
                    if (!url) return;
                    if (!tabid) {
                        tabidcounter++;
                        tabid = "tabid" + tabidcounter;
                        jitem.attr("tabid", tabid);

                        //给url附加menuno
                        if (url.indexOf('?') > -1) url += "&";
                        else url += "?";
                        url += "menuNo=" + jitem.attr("menuno");
                        jitem.attr("url", url);
                    }
                    f_addTab(tabid, $("span:first", jitem).html(), url);
                }).live('mouseover',
                function () {
                    var jitem = $(this);
                    jitem.addClass("over");
                }).live('mouseout', function () {
                    var jitem = $(this);
                    jitem.removeClass("over");
                });

        //布局初始化
        //layout
        layout = $("#mainbody").ligerLayout({ height: '100%', heightDiff: -3, leftWidth: 140, onHeightChanged: f_heightChanged, minLeftWidth: 120 });
        var bodyHeight = $(".l-layout-center:first").height();
        //Tab
        tab = $("#framecenter").ligerTab({ height: bodyHeight, contextmenu: true });


        //预加载dialog的背景图片
        LG.prevDialogImage();

        var mainmenu = $("#mainmenu");

        $.getJSON('<c:url value="/user/menu"/>', function (menus) {
            $(menus).each(function (i, menu) {
                var item = $('<div title="' + menu.name + '"><ul class="menulist"></ul></div>');

                $(menu.children).each(function (j, submenu) {
                    var subitem = $('<li><img/><span></span><div class="menuitem-l"></div><div class="menuitem-r"></div></li>');
                    subitem.attr({
                        url: GLOBAL_CTX + submenu.link,
                        menuno: submenu.code
                    });
                    $("img", subitem).attr("src", GLOBAL_CTX + submenu.icon);
                    $("span", subitem).html(submenu.name);
                    $("ul:first", item).append(subitem);
                });
                mainmenu.append(item);
            });

            //Accordion
            accordion = $("#mainmenu").ligerAccordion({ height: bodyHeight - 24, speed: null });


            $("#pageloading").hide();
        });

        LG.ajax({
            url:'<c:url value="/user/getCurrentUser"/>',
            success: function (user) {
                $(".l-topmenu-username").html(user[0].userName + "，");
            },
            error: function () {
                LG.tip('用户信息加载失败');
            }
        });
        $("#pageloading").hide();
    });
</script>
</body>
</html>
