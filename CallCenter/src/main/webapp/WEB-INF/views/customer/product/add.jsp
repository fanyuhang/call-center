<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="contract">
</form:form>
<script type="text/javascript">
	var genderData =<sys:dictList type = "1"/>;
	
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
            {display: "客户姓名",name: "fldCustomerId", newline: true, type: "text", validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "产品", name: "fldProductId", newline: false, type: "text", validate: {required: true}},
            {display: "合同签订日期", name: "fldSignDateStr", newline: true, type: "date", attr:{readonly: "readonly"}},
            {display: "打款日期", name: "fldMoneyDateStr", newline: false, type: "date", attr:{readonly: "readonly"}},
            {display: "购买金额", name: "fldPurchaseMoney", newline: true, type: "text"},
            {display: "银行卡号", name: "fldBankNo", newline: false, type: "text", validate: { maxlength: 64}},
            {display: "开户银行", name: "fldBankName", newline: true, type: "text", validate: { maxlength: 64}},
            {display: "理财经理", name: "fldFinancialUserNo", newline: false, type: "text"},
            {display: "瑞得卡金额", name: "fldCardMoney", newline: true, type: "text"},
            {display: "瑞得卡卡号", name: "fldCardNo", newline: false, type: "text", validate: { maxlength: 32}},
            {display: "瑞德卡等级", name: "fldCardLevel", newline: true, type: "text"},
            {display: "瑞得卡", name: "fldCardNo", newline: false, type: "text", validate: { maxlength: 32}},
            {display: "瑞得卡等级", name: "fldCardLevel", newline: true, type: "text"}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/customer/save"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_check);
    
    function f_check() {
    	var fldName = $("#fldName").val();
        if (fldName != '') {
        	var phone = $("#fldPhone").val();
        	var mobile = $("#fldMobile").val();
            if (!phone && !mobile) {
        		LG.showError("请录入固定电话或手机");
        		return;
    		}
        
            LG.ajax({
                url: '<c:url value="/customer/customer/isExist"/>',
                data: {fldName:fldName,phone:phone,mobile:mobile},
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
