<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../include/formHeader.jsp" %>
<link href='<c:url value="/static/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css"/>' rel="stylesheet" type="text/css"/>
<script src='<c:url value="/static/plupload/plupload.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.flash.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.browserplus.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/i18n/zh.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/jquery.ui.plupload/jquery.ui.plupload.js" />' type="text/javascript"></script>
<script type="text/javascript">
    var merchantTemplate = '商户上传模板';
    var compactTemplate = '合同上传模板';
    var brandTemplate = '品牌上传模板';
    var shopTemplate = '门店上传模板';
    var posTemplate = '终端上传模板';
    var posfeeTemplate = '终端费率上传模板';
    var merchantGroupTemplate="集团商户上传模板";

    function f_template(filename) {
        window.location.href = '<c:url value="/merchant/common/template"/>' + '/' +filename;
    }

    function f_export(exportUrl) {
        var rule = LG.bulidFilterGroup("#formsearch");
        LG.ajax({
            loading:'正在导出数据中...',
            url:exportUrl,
            data:{where:JSON2.stringify(rule)},
            success:function (data, message) {
                window.location.href = '<c:url value="/merchant/common/download?filepath="/>' + encodeURIComponent(data[0]);
                LG.tip(message);
            },
            error:function (message) {
                LG.tip(message);
            }
        });
    }

</script>