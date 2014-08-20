<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../include/formHeader.jsp" %>
<script type="text/javascript">
    var customerTemplate = '客户上传模板';
    var initCustomerTemplate = '初始导入模板';

    function f_template(filename) {
        window.location.href = '<c:url value="/customer/common/template"/>' + '/' +filename;
    }

    function f_export(exportUrl) {
        var rule = LG.bulidFilterGroup("#formsearch");
        LG.ajax({
            loading:'正在导出数据中...',
            url:exportUrl,
            data:{where:JSON2.stringify(rule)},
            success:function (data, message) {
                window.location.href = '<c:url value="/customer/common/download?filepath="/>' + encodeURIComponent(data[0]);
                LG.tip(message);
            },
            error:function (message) {
                LG.tip(message);
            }
        });
    }
</script>