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
            {display: "客户姓名",name: "fldCustomerId", newline: true, type: "select", validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "customerName", options: {valueFieldID: "customerName"}},
            {display: "产品", name: "fldProductDetailId", newline: false, type: "select", comboboxName:"productId", options:{valueFieldID:'productId'}, validate: {required: true}},
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
    
    $.ligerui.get("customerName")._changeValue('${custId}', '${custName}');
    
    $.ligerui.get("productId").openSelect({
	    grid:{
	    	columnWidth: 100,
	        columns:[
	        	{display: "ID", name: "fldId", hide:1,width:1},
	            {display: "产品全称", name: "fldFullName"},
		        {display: "产品简称", name: "fldShortName"},
		        {display: "成立日期", name: "fldEstablishDate"},
		        {display: "起息日期", name: "fldValueDate"},
		        {display: "实际天数", name: "fldClearDays"},
		        {display: "到期日期", name: "fldDueDate"},
		        {display: "最低认购金额", name: "fldMinPurchaseMoney"},
		        {display: "最高认购金额", name: "fldMaxPurchaseMoney"},
		        {display: "年化收益率", name: "fldAnnualizedRate"},
		        {display: "年化7天存款率", name: "fldDepositRate"},
		        {display: "业绩系数", name: "fldPerformanceRadio"}
	        ], pageSize:20,heightDiff:-10,
	        url:'<c:url value="/customer/product/listDetail"/>',checkbox:false
	    },
	    search:{
	        fields:[
	            {display:"产品全称", name:"customerProduct.fldFullName", newline:true, type:"text", cssClass:"field"}
	        ]
	    },
	    valueField:'fldId', textField:'fldFullName', top:30
	});

    mainform.attr("action", '<c:url value="/customer/contract/save"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);
    
    function f_save() {
    	LG.validate(mainform);
    	
    	if(!$("#fldCustomerId").val()){
        	LG.showError("请选择客户");
        	return;
    	}
    	
    	if(!$("#fldProductDetailId").val()){
        	LG.showError("请选择产品");
        	return;
    	}
    	
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
