<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="contract">
</form:form>
<script type="text/javascript">
	var genderData =<sys:dictList type = "1"/>;
	var cardLevelData =<sys:dictList type = "13"/>;
	
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
            {display: "合同编号", name: "fldId", newline: true, type: "text", validate: {required: true, maxlength: 40}},
            {display: "合同签订日期", name: "fldSignDate", newline: false, type: "date", attr:{readonly: "readonly"}},
            {display: "打款日期", name: "fldMoneyDate", newline: true, type: "date", attr:{readonly: "readonly"}},
            {display: "购买金额", name: "fldPurchaseMoney", newline: false, type: "text"},
            {display: "银行卡号", name: "fldBankNo", newline: true, type: "text", validate: { maxlength: 64},group: "<label style=white-space:nowrap;>银行卡信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "开户银行", name: "fldBankName", newline: false, type: "text", validate: { maxlength: 64}},
            {display: "理财经理", name: "fldFinancialUserNo", newline: true, type: "select", group: "<label style=white-space:nowrap;>其他信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "financialUserNo", options: {valueFieldID: "financialUserNo"}},
            {display: "瑞得卡金额", name: "fldCardMoney", newline: false, type: "text"},
            {display: "瑞得卡卡号", name: "fldCardNo", newline: true, type: "text", validate: { maxlength: 32}},
            {display: "瑞得卡等级", name: "fldCardLevel", newline: false, type: "select",
            	options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:cardLevelData,
                    valueFieldID:"fldCardLevel"
            }}
        ]
    });
    
    $.ligerui.get("customerName").openSelect({
	    grid:{
	    	columnWidth: 110,
	        columns:[
	        	{display: "ID", name: "fldId", hide:1,width:1},
	        	{display: "商户姓名", name: "fldName", newline: true, type: "text", cssClass: "field"},
		        {display: "身份证号", name: "fldIdentityNo", newline: false, type: "text", cssClass: "field"},
		        {display: "手机号", name: "fldMobile", newline: false, type: "text", cssClass: "field"},
		        {display: "固定电话", name: "fldPhone", newline: true, type: "text", cssClass: "field"},
		        {display: "所属理财经理", name: "fldFinancialUserNo", newline: false, type: "select", cssClass:"field"}, 
		        {display: "瑞得卡号", name: "fldCardNo", newline: false, type: "text", cssClass: "field"},
		        {display: "瑞得卡等级", name: "fldCardLevel", newline: true, type: "text", cssClass: "field",
			        render:function(item) {
		        		return renderLabel(cardLevelData,item.fldCardLevel);
		        	}
		        }
	        ], pageSize:20,heightDiff:-10,
	        url:'<c:url value="/customer/customer/list"/>',checkbox:false
	    },
	    search:{
	        fields:[
	            {display:"商户姓名", name:"fldName", newline:true, type:"text", cssClass:"field"}
	        ]
	    },
	    valueField:'fldId', textField:'fldName', top:30
	});
    
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
		        {display: "业绩系数", name: "fldPerformanceRadio"},
		        {display: "佣金系数", name: "fldCommissionRadio"}
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
	
	$.ligerui.get("financialUserNo").openSelect({
	    grid:{
	    	columnWidth: 255,
	        columns:[
	            {display:"用户名称", name:"userName"},
	            {display:"登录名称", name:"loginName"},
	            {display:"部门", name:"deptName"},
	        ], pageSize:20,heightDiff:-10,
	        url:'<c:url value="/security/user/list"/>', sortName:'userName', checkbox:false
	    },
	    search:{
	        fields:[
	            {display:"用户名称", name:"userName", newline:true, type:"text", cssClass:"field"}
	        ]
	    },
	    valueField:'loginName', textField:'userName', top:30
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
    	
    	LG.ajax({
            url: '<c:url value="/customer/contract/isExist"/>',
            data: {fldId:$("#fldId").val()},
            beforeSend: function () {
            	
            },
            complete: function () {
            },
            success: function () {
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
            },
            error: function (message) {
          		LG.showError(message);
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
