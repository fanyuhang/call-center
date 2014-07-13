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
        	{display: "ID", name: "fldId", type:"hidden", attr:{value:"${customerContract.fldId}"}},
            {display: "客户姓名",name: "fldCustomerId", newline: true, type: "select", validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "customerName", options: {valueFieldID: "customerName"},attr:{readonly: "readonly"}},
            {display: "产品", name: "fldProductDetailId", newline: false, type: "select", comboboxName:"productId", 
            	options:{valueFieldID:'productId'}, validate: {required: true},attr:{readonly: "readonly"}},
            {display: "合同签订日期", name: "fldSignDate", newline: true, type: "date", attr:{value:"${customerContract.fldSignDate}",readonly: "readonly"}},
            {display: "打款日期", name: "fldMoneyDate", newline: false, type: "date", attr:{value:"${customerContract.fldMoneyDate}",readonly: "readonly"}},
            {display: "购买金额", name: "fldPurchaseMoney", newline: true, type: "text",attr:{value:"${customerContract.fldPurchaseMoney}",readonly: "readonly"}},
            {display: "银行卡号", name: "fldBankNo", newline: false, type: "text", attr:{value:"${customerContract.fldBankNo}",readonly: "readonly"},validate: { maxlength: 64}},
            {display: "开户银行", name: "fldBankName", newline: true, type: "text", attr:{value:"${customerContract.fldBankName}",readonly: "readonly"},validate: { maxlength: 64}},
            {display: "理财经理", name: "fldFinancialUserNo", newline: false, attr:{value:"${customerContract.fldFinancialUserNo}",readonly: "readonly"}, type: "text"},
            {display: "瑞得卡金额", name: "fldCardMoney", newline: true, attr:{value:"${customerContract.fldCardMoney}",readonly: "readonly"}, type: "text"},
            {display: "瑞得卡卡号", name: "fldCardNo", newline: false, type: "text", attr:{value:"${customerContract.fldCardNo}",readonly: "readonly"}, validate: { maxlength: 32}},
            {display: "瑞德卡等级", name: "fldCardLevel", newline: true, type: "text", attr:{value:"${customerContract.fldCardLevel}",readonly: "readonly"}},
            {display: "瑞得卡", name: "fldCardNo", newline: false, type: "text", attr:{value:"${customerContract.fldCardNo}",readonly: "readonly"},validate: { maxlength: 32}}
        ]
    });
    
	$.ligerui.get("customerName")._changeValue('${customerContract.fldCustomerId}', '${customerContract.customer.fldName}');
    
	$.ligerui.get("productId")._changeValue('${customerContract.fldProductDetailId}', '${customerContract.productDetail.customerProduct.fldFullName}');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel);
    
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
</script>
</body>
</html>
