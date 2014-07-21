<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="contract">
</form:form>
<script type="text/javascript">
	var genderData =<sys:dictList type = "1"/>;
	var cardLevelData =<sys:dictList type = "13"/>;
	var dayUnitData =<sys:dictList type = "14"/>;
	
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
            {display: "产品", name: "fldProductDetailId", newline: false, type: "select", comboboxName:"productId", 
            	options:{valueFieldID:'productId'}, validate: {required: true}},
            {display: "合同编号", name: "fldId", newline: true, type: "text", validate: {required: true, maxlength: 40}, attr:{value:"${customerContract.fldId}", readonly: "readonly"}},
            {display: "合同签订日期", name: "fldSignDate", newline: false, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerContract.fldSignDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }},
            {display: "打款日期", name: "fldMoneyDate", newline: true, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerContract.fldMoneyDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }},
            {display: "购买金额", name: "fldPurchaseMoney", newline: false, validate: {required: true}, type: "text",attr:{value:"${customerContract.fldPurchaseMoney}"}},
            {display: "募集期天数", name: "fldCollectDays", newline: true, type: "text", attr:{value:"${customerContract.fldCollectDays}",readonly: "readonly"}},
            {display: "年化收益率", name: "fldAnnualizedRate", newline: true, type: "text", attr:{value:"${customerContract.fldAnnualizedRate}",readonly: "readonly"},group: "<label style=white-space:nowrap;>产品信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "预期收益", name: "fldAnnualizedMoney", newline: false, type: "text", attr:{value:"${customerContract.fldAnnualizedMoney}",readonly: "readonly"}},
            {display: "年化7天存款率", name: "fldDepositRate", newline: true, type: "text", attr:{value:"${customerContract.fldDepositRate}",readonly: "readonly"}},
            {display: "募集期贴息", name: "fldCollectMoney", newline: false, type: "text", attr:{value:"${customerContract.fldCollectMoney}",readonly: "readonly"}},
            {display: "业绩系数", name: "fldPerformanceRadio", newline: true, type: "text", attr:{value:"${customerContract.fldPerformanceRadio}",readonly: "readonly"}},
            {display: "业绩额度", name: "fldPerformanceMoney", newline: false, type: "text", attr:{value:"${customerContract.fldPerformanceMoney}",readonly: "readonly"}},
            {display: "佣金系数", name: "fldCommissionRadio", newline: true, type: "text", attr:{value:"${customerContract.fldCommissionRadio}",readonly: "readonly"}},
            {display: "佣金金额", name: "fldCommissionMoney", newline: false, type: "text", attr:{value:"${customerContract.fldCommissionMoney}",readonly: "readonly"}},
            {display: "成立日期", name: "fldEstablishDate", newline: true, type: "text", attr:{value:"${customerContract.establishDate}",readonly: "readonly"}},
            {display: "天数单位", name: "fldDayUnitText", newline: false, type: "text", attr:{readonly: "readonly"},
            	render:function(item) {
	        		return renderLabel(dayUnitData,item.dayUnit);
	        	}
            },
            {display: "天数单位", name: "fldDayUnit", newline: false, type: "hidden", attr:{value:"${customerContract.dayUnit}",readonly: "readonly"}},
            {display: "实际天数", name: "fldClearDays", newline: true, type: "text", attr:{value:"${customerContract.clearDays}",readonly: "readonly"}},
            {display: "银行卡号", name: "fldBankNo", newline: true, type: "text", attr:{value:"${customerContract.fldBankNo}"},validate: { maxlength: 64},group: "<label style=white-space:nowrap;>银行卡信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "开户银行", name: "fldBankName", newline: false, type: "text", attr:{value:"${customerContract.fldBankName}"},validate: { maxlength: 64}},
            {display: "理财经理", name: "fldFinancialUserNo", newline: true, type: "select",group: "<label style=white-space:nowrap;>其他信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "financialUserNo", options: {valueFieldID: "financialUserNo"}},
            {display: "瑞得卡金额", name: "fldCardMoney", newline: false, attr:{value:"${customerContract.fldCardMoney}"}, type: "text"},
            {display: "瑞得卡卡号", name: "fldCardNo", newline: true, type: "text", attr:{value:"${customerContract.fldCardNo}"}, validate: { maxlength: 32}},
            {display: "瑞得卡等级", name: "fldCardLevel", newline: false, type: "select", 
            	options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:cardLevelData,
                    initValue: '${customerContract.fldCardLevel}',
                    valueFieldID:"fldCardLevel"
            }}
        ]
    });
    
    $.ligerui.get("fldMoneyDate").bind("changedate",function(){
    	var fldMoneyDate = $("#fldMoneyDate").val();
    	if(fldMoneyDate == "") {
    		$("#fldCollectDays").val(0);
    		$("#fldCollectMoney").val(0);
    		return;
    	}
    	
    	var fldPurchaseMoney = $("#fldPurchaseMoney").val();
    	var fldEstablishDate = $("#fldEstablishDate").val();
    	if(Date.parse(fldEstablishDate) > Date.parse(fldMoneyDate)) {//成立日期晚于打款日期
    		var collectDays = (new Date(Date.parse(fldEstablishDate)) - new Date(Date.parse(fldMoneyDate)))/(1000*60*60*24);
    		$("#fldCollectDays").val(collectDays);
    		if(fldPurchaseMoney!="") {
    			//募集期贴息=购买金额*(年化7天存款率*募集期天数/365)
    			var fldCollectMoney = parseFloat(fldPurchaseMoney)*(parseFloat($("#fldDepositRate").val())*collectDays/365);
    			$("#fldCollectMoney").val(fldCollectMoney);
    		} else {
    			$("#fldCollectMoney").val(0);
    		}
    	} else {
    		$("#fldCollectMoney").val(0);
    	}
    });
    
    $("#fldPurchaseMoney").change(function(){
    	var fldPurchaseMoney = $("#fldPurchaseMoney").val();
    	if(fldPurchaseMoney == "") {
    		$("#fldAnnualizedMoney").val(0);
    		$("#fldCollectMoney").val(0);
    		$("#fldPerformanceMoney").val(0);
    		$("#fldCommissionMoney").val(0);
    		return;
    	}
    	
    	if($("#fldProductDetailId").val() == "") {
    		return;
    	}
    	
    	//预期收益=购买金额*(年化收益率*实际天数/365)
		var days = parseInt($("#fldClearDays").val());
		var dayUnit = parseInt($("#fldDayUnit").val());
		var fldAnnualizedMoney = 0;
		if(dayUnit == 0) {
			fldAnnualizedMoney = parseFloat(fldPurchaseMoney)*($("#fldAnnualizedRate").val()*days/365);
		} else if(dayUnit == 1) {
			fldAnnualizedMoney = parseFloat(fldPurchaseMoney)*($("#fldAnnualizedRate").val()*days/12);
		}
    	$("#fldAnnualizedMoney").val(fldAnnualizedMoney);
    	
    	//募集期贴息=购买金额*(年化7天存款率*募集期天数/365)
    	var collectDays = parseInt($("#fldCollectDays").val());
    	var fldCollectMoney = parseFloat(fldPurchaseMoney)*(parseFloat($("#fldDepositRate").val())*collectDays/365);
    	$("#fldCollectMoney").val(fldCollectMoney);
    	
    	//佣金金额=购买金额*佣金系数
		var fldCommissionMoney = parseFloat(fldPurchaseMoney)*parseFloat(parseFloat($("#fldCommissionRadio").val()));
		$("#fldCommissionMoney").val(fldCommissionMoney);
		
		//业绩额度=购买金额*业绩系数
		var fldPerformanceMoney = parseFloat(fldPurchaseMoney)*parseFloat(parseFloat($("#fldPerformanceRadio").val()));
		$("#fldPerformanceMoney").val(fldPerformanceMoney);
    });
    
    $("#productId").change(function(){
    	var productId = $("#fldProductDetailId").val();
    	if(productId == "") {
    		$("#fldAnnualizedRate").val("");
    		$("#fldAnnualizedMoney").val("");
    		$("#fldDepositRate").val("");
    		$("#fldCollectMoney").val("");
    		$("#fldCommissionRadio").val("");
    		$("#fldCommissionMoney").val("");
    		$("#fldPerformanceRadio").val("");
    		$("#fldPerformanceMoney").val("");
    		return;
    	}
    	
    	LG.ajax({
            url: '<c:url value="/customer/product/findProductDetail"/>',
            data: {fldId:productId},
            beforeSend: function () {
            	
            },
            complete: function () {
            },
            success: function (data) {
		    	var productDetail = data[0];
		    	var fldAnnualizedRate = productDetail.fldAnnualizedRate;
		    	$("#fldAnnualizedRate").val(fldAnnualizedRate);
		    	$("#fldClearDays").val(productDetail.fldClearDays);
		    	$("#fldDayUnit").val(productDetail.fldDayUnit);
		    	$("#fldDayUnitText").val(renderLabel(dayUnitData,productDetail.fldDayUnit));
		    	$("#fldEstablishDate").val(productDetail.fldEstablishDate);
		    	
		    	var fldPurchaseMoney = $("#fldPurchaseMoney").val();
		    	if(fldPurchaseMoney != "") {
		    		//预期收益=购买金额*(年化收益率*实际天数/365)
		    		var days = parseInt(productDetail.fldClearDays);
		    		var dayUnit = parseInt(productDetail.fldDayUnit);
		    		var fldAnnualizedMoney = 0;
		    		if(dayUnit == 0) {
		    			fldAnnualizedMoney = parseFloat(fldPurchaseMoney)*(fldAnnualizedRate*days/365);
		    		} else if(dayUnit == 1) {
		    			fldAnnualizedMoney = parseFloat(fldPurchaseMoney)*(fldAnnualizedRate*days/12);
		    		}
    				$("#fldAnnualizedMoney").val(fldAnnualizedMoney);
    			} else {
    				$("#fldAnnualizedMoney").val(0);
    			}
    			$("#fldDepositRate").val(productDetail.fldDepositRate);
    			if($("#fldMoneyDate").val()!="" && productDetail.fldEstablishDate > $("#fldMoneyDate").val()) {//成立日期晚于打款日期
    				var collectDays = (new Date(Date.parse(productDetail.fldEstablishDate)) - new Date(Date.parse($("#fldMoneyDate").val())))/(1000*60*60*24);
    				$("#fldCollectDays").val(collectDays);
    				if(fldPurchaseMoney!="") {
    					//募集期贴息=购买金额*(年化7天存款率*募集期天数/365)
    					var fldCollectMoney = parseFloat(fldPurchaseMoney)*(parseFloat(productDetail.fldDepositRate)*collectDays/365);
    					$("#fldCollectMoney").val(fldCollectMoney);
    				} else {
    					$("#fldCollectMoney").val(0);
    				}
    			} else {
    				$("#fldCollectMoney").val(0);
    				$("#fldCollectDays").val(0);
    			}
    			$("#fldCommissionRadio").val(productDetail.fldCommissionRadio);
    			//佣金金额=购买金额*佣金系数
    			if(fldPurchaseMoney != "") {
    				var fldCommissionMoney = parseFloat(fldPurchaseMoney)*parseFloat(productDetail.fldCommissionRadio);
    				$("#fldCommissionMoney").val(fldCommissionMoney);
    			} else {
    				$("#fldCommissionMoney").val(0);
    			}
    			$("#fldPerformanceRadio").val(productDetail.fldPerformanceRadio);
    			//业绩额度=购买金额*业绩系数
    			if(fldPurchaseMoney != "") {
    				var fldPerformanceMoney = parseFloat(fldPurchaseMoney)*parseFloat(productDetail.fldPerformanceRadio);
    				$("#fldPerformanceMoney").val(fldPerformanceMoney);
    			} else {
    				$("#fldPerformanceMoney").val(0);
    			}
            },
            error: function (message) {
          		
            }
        });
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
    $.ligerui.get("financialUserNo")._changeValue('${customer.fldFinancialUserNo}', '${customer.financialUserName}');
    
    $.ligerui.get("customerName").openSelect({
	    grid:{
	    	columnWidth: 110,
	        columns:[
	        	{display: "ID", name: "fldId", hide:1,width:1},
	        	{display: "商户姓名", name: "fldName", newline: true, type: "text", cssClass: "field"},
		        {display: "身份证号", name: "fldIdentityNo", newline: false, type: "text", cssClass: "field"},
		        {display: "手机号", name: "fldMobile", newline: false, type: "text", cssClass: "field"},
		        {display: "固定电话", name: "fldPhone", newline: true, type: "text", cssClass: "field"},
		        {display: "所属理财经理", name: "fldFinancialUserNo", newline: false, type: "text", cssClass: "field"},
		        {display: "瑞得卡号", name: "fldCardNo", newline: false, type: "text", cssClass: "field"},
		        {display: "瑞得卡等级", name: "fldCardLevel", newline: true, type: "text", cssClass: "field"}
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
	$.ligerui.get("customerName")._changeValue('${customerContract.fldCustomerId}', '${customerContract.customer.fldName}');
    
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
	$.ligerui.get("productId")._changeValue('${customerContract.fldProductDetailId}', '${customerContract.productDetail.customerProduct.fldFullName}');

    mainform.attr("action", '<c:url value="/customer/contract/update"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);
    
    function f_save() {
    	LG.validate(mainform);
    	
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
