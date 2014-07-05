<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="customer">
</form:form>
<script type="text/javascript">
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
            {display: "集团商户编号",name: "code", newline: true, type: "text", validate: {required: true, maxlength: 64}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "全称", name: "fullName", newline: true, type: "text", validate: {required: true, maxlength: 128}},
            {display: "简称", name: "shortName", newline: false, type: "text", validate: {required: true, maxlength: 64}},
            {display: "地址", name: "address", newline: true, width: 630, type: "text", validate: {maxlength: 128}},
            {display: "联系人", name: "linkMan", newline: true, type: "text", validate: {maxlength: 32}, group: "<label style=white-space:nowrap;>联系人信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "联系人电话", name: "linkManPhone", newline: false, type: "text", validate: { maxlength: 32}},
            {display: "联系人手机号", name: "fldMobiles", newline: true, type: "text", validate: { maxlength: 1000}},
            {display: "联系人邮箱", name: "fldEmails", newline: false, type: "text", validate: { maxlength: 1000}}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/customer/save"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_check);
    
    function f_check() {
    	var code = $("#code").val();
        if (code != '') {
            LG.ajax({
                url: '<c:url value="/merchant/group/isExist"/>',
                data: { code: code},
                beforeSend: function () {
                	
                },
                complete: function () {
                },
                success: function () {
                	f_save();
                },
                error: function (message) {
		            LG.showError(message);
                }
            });
        } else {
        	f_save();
        }
    }
    
    function f_save() {
    	LG.validate(mainform);
    	
    	LG.submitForm(mainform, function (data) {
	        var win = parent || window;
	        if (data.IsError == false) {
	            win.LG.showSuccess('保存成功', function () {
	                win.LG.closeAndReloadParent(null, "${menuNo}");
	            });
	        } else {
	            win.LG.showError('错误:' + data.Message);
	        }
    	});
    }
    
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
</script>
</body>
</html>
