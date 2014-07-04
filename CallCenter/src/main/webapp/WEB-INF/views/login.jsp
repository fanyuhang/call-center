<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
<title></title>
<script src="<c:url value="/static/ligerUI/jquery/jquery-1.3.2.min.js" />" type="text/javascript"></script>
<link href="<c:url value="/static/ligerUI/ligerUI/skins/Aqua/css/ligerui-dialog.css" />" rel="stylesheet"
      type="text/css"/>
<link href="<c:url value="/static/ligerUI/ligerUI/skins/Gray/css/dialog.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/static/ligerUI/ligerUI/js/core/base.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerDialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/js/LG.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/redcard.js" />" type="text/javascript"></script>
<style type="text/css">
    * {
        padding: 0;
        margin: 0;
    }

    body {
        text-align: center;
        background: #4974A4;
    }

    #login {
        width: 740px;
        margin: 0 auto;
        font-size: 12px;
    }

    #loginlogo {
        width: 700px;
        height: 100px;
        overflow: hidden;
        background: url('') no-repeat;
        margin-top: 50px;
    }

    #loginpanel {
        width: 729px;
        position: relative;
        height: 300px;
    }

    .panel-h {
        width: 729px;
        height: 20px;
        background: url('<c:url value="/static/ligerUI/images/login/panel-h.gif" />') no-repeat;
        position: absolute;
        top: 0px;
        left: 0px;
        z-index: 3;
    }

    .panel-f {
        width: 729px;
        height: 13px;
        background: url('<c:url value="/static/ligerUI/images/login/panel-f.gif" />') no-repeat;
        position: absolute;
        bottom: 0px;
        left: 0px;
        z-index: 3;
    }

    .panel-c {
        z-index: 2;
        background: url('<c:url value="/static/ligerUI/images/login/panel-c.gif" />') repeat-y;
        width: 729px;
        height: 300px;
    }

    .panel-c-l {
        position: absolute;
        left: 60px;
        top: 40px;
    }

    .panel-c-r {
        position: absolute;
        right: 20px;
        top: 50px;
        width: 222px;
        line-height: 200%;
        text-align: left;
    }

    .panel-c-l h3 {
        color: #556A85;
        margin-bottom: 10px;
    }

    .panel-c-l td {
        padding: 7px;
    }

    .login-text {
        height: 24px;
        left: 24px;
        border: 1px solid #e9e9e9;
        background: #f9f9f9;
    }

    .login-text-focus {
        border: 1px solid #E6BF73;
    }

    .login-btn {
        width: 114px;
        height: 29px;
        color: #E9FFFF;
        line-height: 29px;
        background: url('<c:url value="/static/ligerUI/images/login/login-btn.gif" />') no-repeat;
        border: none;
        overflow: hidden;
        cursor: pointer;
    }

    #txtUsername, #txtPassword {
        width: 191px;
    }

    #logincopyright {
        text-align: center;
        color: White;
        margin-top: 50px;
    }
</style>
<script type="text/javascript">
    var fromUrl = getQueryStringByName("fromUrl");
    if (!fromUrl) {
        fromUrl = encodeURIComponent("${ctx}/main");
    }
    $(function () {
        $(".login-text").focus(
                function () {
                    $(this).addClass("login-text-focus");
                }).blur(function () {
                    $(this).removeClass("login-text-focus");
                });

        $(document).keydown(function (e) {
            if (e.keyCode == 13) {
                dologin();
            }
        });

        $("#txtUsername").focus();

        $("#btnLogin").click(function () {
            dologin();
        });


        function dologin() {
            var loginName = $("#txtUsername").val();
            var password = $("#txtPassword").val();
            if (loginName == "") {
                alert('账号不能为空!');
                $("#txtUsername").focus();
                return;
            }
            if (password == "") {
                alert('密码不能为空!');
                $("#txtPassword").focus();
                return;
            }
            var reg = /^[-\da-zA-Z`=\\\[\];',.\/~!@#$%^&*()_+|{}:\"<>?]{8,12}/;
            if( !reg.test(password) ){
                alert('密码只能由8-12位字母、数字、特殊字符组成！');
                $("#txtPassword").focus();
                return;
            }
            $.ajax({
                type:'post', cache:false, dataType:'json',
                url:'${ctx}/login',
                data:[
                    { name:'loginName', value:loginName },
                    { name:'password', value:password }
                ],
                success:function (result) {
                    if (!result) {
                        location.href = decodeURIComponent(fromUrl);
                    }
                    if (!result.IsError) {
                        location.href = decodeURIComponent(fromUrl);
                    } else {
                        $.ligerDialog.closeWaitting();
                        alert(result.Message);
                        $("#txtUsername").focus();
                        return;
                    }
                },
                error:function () {
                    alert('发送系统错误,请与系统管理员联系!');
                },
                beforeSend:function () {
                    $.ligerDialog.waitting("正在登陆中,请稍后...");
                    $("#btnLogin").attr("disabled", true);
                },
                complete:function () {
                    $.ligerDialog.closeWaitting();
                    $("#btnLogin").attr("disabled", false);
                }
            });
        }
    });
</script>
</head>
<body style="padding:10px">
<div id="login">
    <div id="loginlogo"></div>
    <div id="loginpanel">
        <div class="panel-h"></div>
        <div class="panel-c">
            <div class="panel-c-l">

                <table cellpadding="0" cellspacing="0">
                    <tbody>
                    <tr>
                        <td align="left" colspan="2">
                            <h3>请使用聚金理财呼叫中心账号登陆</h3>
                        </td>
                    </tr>
                    <tr>
                        <td align="right">账号：</td>
                        <td align="left"><input type="text" name="loginName" id="txtUsername" value="admin" class="login-text" />
                        </td>
                    </tr>
                    <tr>
                        <td align="right">密码：</td>
                        <td align="left"><input type="password" name="password" id="txtPassword" value="admin888" class="login-text"/></td>
                    </tr>
                    <tr>
                        <td align="center" colspan="2">
                            <input type="submit" id="btnLogin" value="登录" class="login-btn"/>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="panel-c-r">
            </div>
        </div>
        <div class="panel-f"></div>
    </div>
    <div id="logincopyright">Copyright © 2014 聚金理财</div>
</div>
</body>
</html>
