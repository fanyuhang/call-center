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
    <link href="<c:url value="/static/ligerUI/css/base.css"/>" rel="stylesheet" type="text/css"/>
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
    <script src="<c:url value="/static/ligerUI/js/telephone.js"/>" type="text/javascript"></script>
    <script src="<c:url value="/static/ligerUI/js/msm.js"/>" type="text/javascript"></script>
</head>
<body style="text-align:center; background:#F0F0F0; overflow:hidden;">
<div id="pageloading" style="display:block;"></div>
<div class="headertop wrapper" id="header">
    <div class="logo fl"><a href="#" class="icon-logo"></a></div>
    <div class="userinfo-top ui-bar-gray">
        <ul class="wrapper">
            <li><a href="#" class="avatar fl"><div><img src="<c:url value='/static/ligerUI/images/global/icon-avatar.png' />" /></div></a>
                <span id="userName" class="fl name"></span></li>
            <li><div class="i-line"></div></li>
            <li><span class="title">分机号：</span> <b class="hl" id="phoneExtension">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></li>
            <li><div class="i-line"></div></li>
            <li><div class="callnum"><span class="title">号码：</span> <b id="telephone">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></div></li>
            <li><div class="i-line"></div></li>
            <li>
                <div class="status"><span class="title fl" title="显示电话的实时状态">你的状态：</span>
                    <i class="i-offline fl" id="userStatus"></i> <b>不在线</b>
                </div>
            </li>
            <li class="status-box">
                <div class="status status-r">
                    <i class="i-unbusy fl" id="telephoneStatus"></i> <b class="green">示闲</b> <em class="ui-triangle down"></em>
                    <div class="select-status">
                        <ul>
                            <li class="off" data-type="1" title="坐席休息" ><i class="i-test"></i> <b>小休</b></li>
                            <li class="off" data-type="2" title="坐席就餐"><i class="i-meals"></i> <b>就餐</b></li>
                            <li class="off" data-type="3" title="坐席培训"><i class="i-training"></i> <b>培训</b></li>
                            <li class="off" data-type="4" title="坐席会议中..."><i class="i-meeting"></i> <b>会议中</b></li>
                            <li class="off" data-type="5" title="坐席正在处理其它事情"><i class="i-processing"></i> <b>后处理</b></li>
                            <li class="off" data-type="6" title="坐席准备就绪"><i class="i-unbusy"></i> <b class="green">示闲</b></li>
                        </ul>
                    </div>
                </div>

            </li>
        </ul>
    </div>
    <div class="nav-tools " id="main-nav-tools">
        <ul class="wrapper">
            <li><a href="javascript:f_connected();" class="disabled" id="connected" data-name="connected" title="保持当前电话，通话双方临时中断" ><i class="i-connected"></i><b>保持通话</b></a></li>
            <li><div class="i-line"></div></li>
            <li><a href="javascript:f_dial();" id="dial" class="disabled"  data-name="dial" title="通过系统页面进行软外拨"><i class="i-dial"></i><b>拨号</b></a></li>
            <li><div class="i-line"></div></li>
            <li><a href="javascript:f_transfer();" class="disabled" id="transfer" data-name="transfer" title="转接当前电话"><i class="i-transfer"></i><b>转接</b></a></li>
            <li><div class="i-line"></div></li>
            <li><a href="javascript:f_hangup();" class="disabled" id="hangup" data-name="hangup" title="强行中断当前电话"><i class="i-hangup"></i><b>释放</b></a></li>
            <li><div class="i-line"></div></li>
            <li><a href="javascript:f_mute();" class="disabled" id="mute" data-name="mute" title="通话双方不能听到对方声音，但电话未中断"><i class="i-mute"></i><b>静音</b></a></li>
            <li><div class="i-line"></div></li>
            <li><a href="javascript:f_msm();" id="msm" data-name="msm" title="可以发送短消息"><i class="i-msm"></i><b>短消息</b></a></li>
            <li><div class="i-line"></div></li>
            <li><a href="javascript:f_control();" id="control" class="disabled"  data-name="control" title="打开监控页面"><i class="i-control"></i><b>监控</b></a></li>
        </ul>
    </div>
    <div class="nav-tools"  id="right-nav-tools">
        <ul>
            <li><a href="javascript:f_changepassword()"><i class="i-editpasswd"></i><b>修改密码</b></a></li>
            <li><div class="i-line"></div></li>
            <li><a href="javascript:f_logout()"><i class="i-exit"></i><b>退出</b></a></li>
        </ul>
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
<object  classid="clsid:A2B80A6E-42FA-4730-AEB2-B1FB38D2C8D1" id="snocx" codebase="SnellCTI.cab#version=1.0.0.1" style="VISIBILITY:visible; height: 0px; width: 0px;">
</object>
<script type="text/javascript">
    var intervalId;

    var connected_type = 0;
    var mute_type = 0;
    function f_connected(){
        if(LG.telephoneStatus!=0){
            return;
        }

        LG.connected(connected_type);

        if(connected_type!=0){
            $("#connected").find('b').text("保持通话");
            connected_type = 0;
        }
    }

    function f_transfer(){
        if(LG.telephoneStatus!=0){
            return;
        }
        LG.transfer();
    }

    function f_hangup(){
        if(LG.telephoneStatus!=0){
            return;
        }
        LG.hangup();
    }

    function f_mute(){

        if(LG.telephoneStatus!=0){
            return;
        }
        LG.mute(mute_type);
        if(mute_type==0){
            $("#mute").find('b').text("取消静音");
            $("#mute").find("i").addClass("i-sound");
            mute_type = 1;
        }else{
            $("#mute").find('b').text("静音");
            $("#mute").find('i').removeClass("i-sound");
            mute_type = 0;
        }
    }

    function f_msm(){
        LG.msm();
    }

    function f_control(){
        if(LG.telephoneStatus!=0){
            return;
        }
        LG.control();
    }

    function f_connect(name,pwd,phoneNo){
        var snell = document.getElementById("snocx");
        if(snell && $.browser.msie){
            snell.snlSetServer("192.168.0.207",60000);
            snell.snlAgentLogin(name,pwd,phoneNo);

            intervalId = setInterval(function () {
                var snell = document.getElementById("snocx");
                snell.snlSendAliveToServer();
            }, 2000);
        }else{
            f_login_status(1);
        }
    }

    function f_login_status(success){
        if(success == 0){
            $("#userStatus").removeClass("i-offline");
            $("#userStatus").addClass("i-ready");
            $("#userStatus").next('b').addClass("green").text("就绪");
            $("#dial").removeClass("disabled");
            $("#control").removeClass("disabled");
            $(".select-status li").each(function(){
                $(this).removeClass("off");
            });
        }else{
            $("#userStatus").removeClass("i-ready");
            $("#userStatus").addClass("i-offline");
            $("#userStatus").next('b').removeClass("green").text("不在线");
            $("#dial").addClass("disabled");
            $("#control").addClass("disabled");
            $(".select-status li").each(function(){
                $(this).addClass("off");
            });
        }
    }

    function f_dial(){
        if(LG.telephoneStatus!=0){
            return;
        }
        LG.dial();
    }

    function f_logout(){

        $.ligerDialog.confirm('请确认是否退出？', function (yes) {
            if (yes) {
                if(LG.telephoneStatus==0){
                    var snell = document.getElementById("snocx");
                    snell.snlAgentLogout();
                    if(intervalId){
                        clearInterval(intervalId);
                    }
                }
                var url = '<c:url value="/logout"/>';
                window.location.href=url;
            }
        });
    }
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
                $("#userName").html(user[0].loginName + " ( "+user[0].userName+" )");
                $("#phoneExtension").html(user[0].phoneExtension);
                var phoneExtension = user[0].phoneExtension;
                var phoneType = user[0].phoneType;

                $("#pageloading").hide();
                if(phoneExtension&&phoneExtension.length>0){
                    $("#dial").removeClass("disabled");

                    f_connect('admin','admin','200');

                }else{
                    $("#dial").addClass("disabled");
                }

                if(phoneType&&phoneType == '2'){
                    $("#control").removeClass("disabled");
                }else{
                    $("#control").addClass("disabled");
                }

            },
            error: function () {
                $("#pageloading").hide();
                LG.tip('用户信息加载失败');
            }
        });
    });

$(function(){
    // global
    var $header = $('#header');
    $header
        .on('mouseenter', 'li.status-box', function(){
            $(this).find('.select-status').stop(true, true).show();
        })
        .on('mouseleave', '.userinfo-top', function(){
            $(this).find('.select-status').stop(true, true).hide();
        })
        .on('click', '.select-status li', function(){
            var self = $(this);
            self.closest('.select-status').hide();
                var type = $(this).attr("data-type");
                var snell = document.getElementById("snocx");
                switch (type) {
                case '1':
                    //小休
                    snell.snlAgentRest(3);
                    break;
                case '2':
                    //就餐
                    snell.snlAgentDining(3);
                    break;
                case '3':
                    //培训
                    snell.snlAgentTraining(3);
                    break;
                case '4':
                    //会议
                    snell.snlAgentMeeting(3);
                    break;
                case '5':
                    //后处理
                    snell.snlAgentNote(3);
                    break;
                case '6':
                    //示闲
                    snell.snlAgentFree();
                    break;
            }
        })
});
</script>
<script language="javascript" for="snocx" event="snlAgentLoginEvent(nFlag)" type="text/javascript">
    snlAgentLoginEvent(nFlag);
</script>
<script language="javascript" for="snocx" event="snlAgentFreeEvent()" type="text/javascript">
    snlAgentFreeEvent();
</script>

<script language="javascript" for="snocx" event="snlAgentBusyEvent(nFlag)" type="text/javascript">
    snlAgentBusyEvent(nFlag);
</script>


<script language="javascript" for="snocx" event="snlEstablishCallEvent()" type="text/javascript">
    snlEstablishCallEvent();
</script>
<script language="javascript" for="snocx" event="snlClearCallEvent()" type="text/javascript">
    snlClearCallEvent();
</script>
<script language="javascript" for="snocx" event="snlReceiveDeliverCallEvent(szPhoneNumber,szPhoneParam,nCallID)" type="text/javascript">
    snlReceiveDeliverCallEvent(szPhoneNumber,szPhoneParam,nCallID);
</script>
<script language="javascript" for="snocx" event="snlHeldCallEvent()" type="text/javascript">
    snlHeldCallEvent();
</script>
<script language="javascript" for="snocx" event="snlMakeTransferCallFailEvent(nType,szPhoneNumber,nReason)" type="text/javascript">
    snlMakeTransferCallFailEvent(nType,szPhoneNumber,nReason);
</script>

<script language="javascript" for="snocx" event="snlExtensionInfoEvent(nPos, nStatus, szExtension, szRxDTMF, szPhoneNumber, szAgentID, szAgentName, nStatusTime)" type="text/javascript">
    snlExtensionInfoEvent(nPos, nStatus, szExtension, szRxDTMF, szPhoneNumber, szAgentID, szAgentName, nStatusTime);
</script>

<script type="text/javascript">
    function snlAgentLoginEvent(nFlag)
    {
        LG.telephoneStatus = 1;
        switch(nFlag){
            case 0:
                f_login_status(0);
                LG.telephoneStatus = 0;
                alert("坐席登陆成功");
                break;
            case 1:
                alert("无此用户");
                break;
            case 2:
                alert("密码错误");
                break;
            case 3:
                alert("用户已登录");
                break;
            case 4:
                alert("未与计算机绑定");
                break;
            case 5:
                alert("其它错误");
                break;
            case 6:
                alert("错误的分机号");
                break;
            default:
                alert("坐席登陆失败");
                break;
        }
    }

    function snlAgentFreeEvent(){
        $("#telephoneStatus").removeClass();
        $("#telephoneStatus").addClass("i-unbusy");
        $("#telephoneStatus").next("b").removeClass();
        $("#telephoneStatus").next("b").addClass("green");
        $("#telephoneStatus").next("b").text("示闲");
//        alert("坐席示闲成功");
    }

    function snlAgentBusyEvent(nFlag){
        var className = '';
        var text = '';
        $("#telephoneStatus").removeClass();
        $("#telephoneStatus").next("b").removeClass();
        switch(nFlag){
            case 0:
                className = 'i-test';
                text = '小休';
//                alert("坐席小休成功");
                break;
            case 1:
                className = 'i-meals';
                text = '就餐';
//                alert("坐席就餐成功");
                break;
            case 2:
                className = 'i-training';
                text = '培训';
//                alert("坐席培训成功");
                break;
            case 3:
                className = 'i-meeting';
                text = '会议中';
//                alert("坐席会议成功");
                break;
            case 4:
                className = 'i-processing';
                text = '后处理';
//                alert("坐席后处理成功");
                break;
            default:
                alert("操作成功");
                break;
        }
        $("#telephoneStatus").addClass(className);
        $("#telephoneStatus").next("b").text(text);

    }

    function snlEstablishCallEvent(){
        $("#dial").addClass("disabled");
        $("#connected").removeClass("disabled");
        $("#transfer").removeClass("disabled");
        $("#hangup").removeClass("disabled");
        $("#mute").removeClass("disabled");
        $("#userStatus").addClass("i-calling");
        $("#userStatus").next("b").removeClass("green");
        $("#userStatus").next("b").text("通话中");
//        alert("通话建立");
    }
    function snlClearCallEvent(){
        $("#dial").removeClass("disabled");
        $("#connected").addClass("disabled");
        $("#transfer").addClass("disabled");
        $("#hangup").addClass("disabled");
        $("#mute").addClass("disabled");
        $("#telephone").html("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
        $("#userStatus").removeClass("i-calling");
        $("#userStatus").removeClass("i-dialing");
        $("#userStatus").addClass("i-ready");
        $("#userStatus").next("b").addClass("green");
        $("#userStatus").next("b").text("就绪");
//        alert("本机或对方挂机");
    }
    function snlReceiveDeliverCallEvent(szPhoneNumber, szPhoneParam, nCallID) {
        alert("话机振铃或回铃时"+szPhoneNumber+","+szPhoneParam+","+nCallID);
    }
    function snlHeldCallEvent() {
        $("#connected").find('b').text("继续通话");

        connected_type = 1;
    }
    function snlMakeTransferCallFailEvent(nType, szPhoneNumber, nReason) {
        alert("外拨或转接失败"+nType+","+szPhoneNumber+","+nReason);
    }
    function snlExtensionInfoEvent(nPos, nStatus, szExtension, szRxDTMF, szPhoneNumber, szAgentID, szAgentName, nStatusTime){
        if(nStatus == 0){
            return;
        }
        alert(nPos+","+nStatus+","+ szExtension+","+ szRxDTMF+","+ szPhoneNumber+","+ szAgentID+","+ szAgentName+","+ nStatusTime);
    }
</script>
</body>
</html>
