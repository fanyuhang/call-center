<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="./include/taglib.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title>欢迎使用聚金金融呼叫中心</title>
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

    <script src='<c:url value="/static/ligerUI/jquery-validation/jquery.validate.min.js"/>'
            type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/additional-methods.min.js"/>'
            type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/messages_zh.js"/>' type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/jquery.form.js"/>" type="text/javascript"></script>

    <script src="<c:url value="/static/ligerUI/js/common.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/LG.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/addfavorite.js"/>" type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/json2.js"/>' type="text/javascript"></script>
</head>
<body style="padding:10px; overflow:auto; text-align:center;background:#FFFFFF;">
<div class="navbar">
    <div class="navbar-l"></div>
    <div class="navbar-r"></div>
    <div class="navbar-icon"><img src="<c:url value="/static/ligerUI/icons/32X32/hire_me.gif"/>"/></div>
    <div class="navbar-inner">
        <b><span id="labelusername"></span><span>，</span><span id="labelwelcome"></span><span>欢迎使用聚金金融呼叫中心</span></b>
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
    <div class="navbar-inner">
        <span><b>公告栏</b></span>
    </div>
</div>

<div id="maingrid"></div>

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
                    $("img", item).attr("src", GLOBAL_CTX + data.icon);
                    $("a", item).html(data.title);
                    item.attr("id", data.id);
                    item.attr("url", GLOBAL_CTX + data.url + '?menuNo=' + data.nodeCode);
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
            url: '<c:url value="/user/getCurrentUser"/>',
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

    var grid = $("#maingrid").ligerGrid({
        rownumbers: true,
        columnWidth: 160,
        columns: [
            {display: "标题", name: "fldTitle", width: 300, render: function (item) {
                return "<a href='javascript:find_content(\"" + item.fldId + "\");' alt='点击查看详情' >" + item.fldTitle + "</a>";
            }},
            {display: "发布人", name: "operateName"},
            {display: "发布时间", name: "fldOperateDate", width: 240}
        ], dataAction: 'server', pageSize: 20, url: '<c:url value="/system/notice/monitor"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
        width: '99%', height: '99%', toJSON: JSON2.stringify, onSuccess:function(data, grid){
            if (oldId&&data.Rows.length > 0) {
                newId = data.Rows[0].fldId;
                var hasNew = false;
                for(var i=0;i<data.Rows.length;i++){
                    var newId = data.Rows[i].fldId;
                    hasNew = true;
                    for(var j=0;j<oldId.length;j++){
                        if(newId==data.Rows[j].fldId){
                            hasNew = false;
                            break;
                        }
                    }
                    if(hasNew){
                        break;
                    }
                }

                if(hasNew){
                    alert("您有新的公告需要查看");
                }
            }
        }
    });

    function find_content(id) {
        LG.ajax({
            url: '<c:url value="/system/notice/find"/>',
            data: { id: id },
            success: function (data) {
                go = open('', data[0].fldTitle, 'top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
                go.document.open();
                go.document.write(data[0].fldContent);
                go.document.close();
            },
            error: function () {

            }
        });
    }

    var oldId;
    function f_reload() {
        oldId = [];
        if (grid.data.Rows.length > 0) {
            for(var i=0;i<grid.data.Rows.length;i++){
                oldId[i] = grid.data.Rows[i].fldId;
            }
        }
        grid.loadData();

    }

    setInterval("f_reload()", 10000);

</script>
</body>
</html>
