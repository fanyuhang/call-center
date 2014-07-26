<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html>
<head runat="server">
<title>上海聚金理财管理服务有限公司</title>
<meta charset="utf-8" />
<meta content="上海聚金理财管理服务有限公司" name="keywords" />
<meta content="上海聚金理财管理服务有限公司" name="description" />
<link rel="stylesheet" href="<c:url value="/static/ligerUI/css/login.css" />" type="text/css" />

<script src="<c:url value="/static/ligerUI/jquery/jquery-1.8.3.min.js" />"></script>
<link href="<c:url value="/static/ligerUI/ligerUI/skins/Aqua/css/ligerui-dialog.css" />" rel="stylesheet"
      type="text/css"/>
<link href="<c:url value="/static/ligerUI/ligerUI/skins/Gray/css/dialog.css" />" rel="stylesheet" type="text/css"/>
<script src="<c:url value="/static/ligerUI/ligerUI/js/core/base.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerDialog.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/js/common.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/js/LG.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/redcard.js" />" type="text/javascript"></script>
</head>
<body>
    <div class="userlogin wrapper">
        <div class="userlogin-hd wrapper">
            <div class="img-logo fl"><img src="/static/ligerUI/images/login/img-logo.png" /></div>
            <div class="img-title fl"><img src="/static/ligerUI/images/login/img-callsystem-title.png" /></div>
        </div>
        <div class="userlogin-bd">
            <div class="img-figure fl"><img src="/static/ligerUI/images/login/img-figure.png" /></div>
            <div class="img-devide-line fl"><img src="/static/ligerUI/images/login/img-devide-line.png" /></div>
            <div class="login-box fl" id="login-box">
                <!-- <form  name="form1" method="post" id="J_login" class="form-horizontal" > -->
                    <div class="control-group">
                        <label class="control-label"  for="txtUsername">用户名：</label>
                        <div class="controls">
                            <input type="text" class="login-input required login-text" id="txtUsername" name="loginName" value="" />
                        </div>
                    </div>
                    <!--n_login_username end-->             
                    <div class="control-group">
                        <label class="control-label" for="txtPassword">密&#12288;码：</label>
                        <div class="controls">
                            <input type="password" class="login-input required login-text" id="txtPassword" maxlength="32" name="password" value="" />
                        </div>
                    </div>
                    <!--n_login_password end-->             

                    <div class="control-group">
                        <label class="control-label" ></label>
                        <div class="controls">
                            <button type="submit" class="ui-btn01 submit" id="btnLogin">提交</button>
                           <!--  <button type="reset" class="ui-btn01">重置</button> -->
                        </div>
                    </div>
                    <!--n_login_button end-->               
                <!-- </form> -->
            </div>
        </div>
    </div>
    <div class="copyright"><p>2014 &copy; 版权所有 上海聚金理财管理服务有限公司</p></div>
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
</body>
</html>