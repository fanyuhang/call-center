<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../include/formHeader.jsp" %>
<link href='<c:url value="/static/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css"/>' rel="stylesheet" type="text/css"/>
<script src='<c:url value="/static/plupload/plupload.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.flash.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.browserplus.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/i18n/zh.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/jquery.ui.plupload/jquery.ui.plupload.js" />' type="text/javascript"></script>
<script type="text/javascript">
    function f_export(exportUrl) {
        var rule = LG.bulidFilterGroup("#formsearch");
        LG.ajax({
            loading:'正在导出数据中...',
            url:exportUrl,
            data:{where:JSON2.stringify(rule)},
            success:function (data, message) {
            	if(data!=""){
                	window.location.href = '<c:url value="/customer/common/download?filepath="/>' + encodeURIComponent(data[0]);
                }
                LG.tip(message);
            },
            error:function (message) {
                LG.tip(message);
            }
        });
    }
</script>