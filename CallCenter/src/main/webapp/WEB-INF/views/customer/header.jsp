<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../include/formHeader.jsp" %>
<link href='<c:url value="/static/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css"/>' rel="stylesheet" type="text/css"/>
<script src='<c:url value="/static/plupload/plupload.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.flash.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.browserplus.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/i18n/zh.js" />' type="text/javascript"></script>
<script src='<c:url value="/static/plupload/jquery.ui.plupload/jquery.ui.plupload.js" />' type="text/javascript"></script>


<script type="text/javascript">
    var statusData = <sys:dictList type="17"  nullable="true"/>;

    var clearingWayData = <sys:dictList type="18"  nullable="true"/>;

    var clearingPeriodData = <sys:dictList type="19"  nullable="true"/>;

    var clearingStatus = <sys:dictList type="20"  nullable="true"/>;

    var clearingItem = <sys:dictList type="21"  nullable="true"/>;

    var payWay = <sys:dictList type="13"  nullable="true"/>;

    var posUsedWay = <sys:dictList type="22"  nullable="true"/>;

    var posStatus =<sys:dictList type="23"  nullable="true"/>;

    var posType = <sys:dictList type="24"  nullable="true"/>;

    var receivedFeeWay = <sys:dictList type="25"  nullable="true"/>;

    var feeWay = <sys:dictList type="26"  nullable="true"/>;

    var industryType =<sys:dictList type="27"  nullable="true"/>;

    var posFeeStatus = <sys:dictList type="28" nullable="true"/>;

    var riskLevel = <sys:dictList type="34" nullable="true"/>;

    var brandTypeData = <sys:dictList type="67" nullable="true"/>;

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