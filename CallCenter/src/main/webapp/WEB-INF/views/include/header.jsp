<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../include/taglib.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link href='<c:url value="/static/ligerUI/ligerUI/skins/Aqua/css/ligerui-all.css"/>' rel="stylesheet"
          type="text/css"/>
    <link href='<c:url value="/static/ligerUI/ligerUI/skins/Gray/css/all.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/static/ligerUI/css/common.css"/>' rel="stylesheet" type="text/css"/>
    <link href='<c:url value="/static/ligerUI/jquery/themes/base/jquery-ui.css"/>' rel="stylesheet" type="text/css"/>
    <style>
        .ui-autocomplete {
            max-height: 150px;
            overflow-y: auto;
            /* prevent horizontal scrollbar */
            overflow-x: hidden;
        }

            /* IE 6 doesn't support max-height
            * we use height instead, but this forces the menu to always be this tall
            */
        html .ui-autocomplete {
            height: 150px;
        }
    </style>
    <script type="text/javascript">
        var GLOBAL_CTX = '${ctx}';
        var IMAGE_WEBSITE="http://www.cnredcard.com/cuponpic/";
    </script>
    <script src='<c:url value="/static/ligerUI/jquery/jquery-1.8.3.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery/jquery-ui.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/ligerUI/js/ligerui.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/js/common.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerForm.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerGrid.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/js/LG.js" />' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/js/ligerui.expand.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/json2.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/accounting.min.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/redcard.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/Format.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/js/ajaxfileupload.js"/>' type="text/javascript"></script>

    <script type="text/javascript">
        //禁止后退键 作用于Firefox、Opera
        document.onkeypress = forbidBackSpace;
        //禁止后退键  作用于IE、Chrome
        document.onkeydown = forbidBackSpace;
        var groupPic = "<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>";
    </script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/jquery.validate.min.js"/>'
            type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/additional-methods.min.js"/>'
            type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery-validation/messages_zh.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/jquery.form.js"/>' type="text/javascript"></script>
    <script src='<c:url value="/static/ligerUI/js/validator.js"/>' type="text/javascript"></script>
