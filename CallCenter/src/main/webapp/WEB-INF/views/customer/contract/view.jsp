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
            	comboboxName: "customerName", options: {valueFieldID: "customerName"},attr:{readonly: "readonly"}},
            {display: "产品", name: "fldProductDetailId", newline: false, type: "select", comboboxName:"productId", 
            	options:{valueFieldID:'productId'}, validate: {required: true},attr:{readonly: "readonly"}},
            {display: "合同编号", name: "fldId", newline: true, type: "text", validate: {required: true, maxlength: 40}, attr:{value:"${customerContract.fldId}", readonly: "readonly"}},
            {display: "合同签订日期", name: "fldSignDate", newline: false, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerContract.fldSignDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }},
            {display: "打款日期", name: "fldMoneyDate", newline: true, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerContract.fldMoneyDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }},
            {display: "购买金额(万元)", name: "fldPurchaseMoney", newline: false, type: "text",attr:{value:"${customerContract.fldPurchaseMoney}",readonly: "readonly"}},
            {display: "成立日期", name: "fldEstablishDate", newline: true, type: "text", attr:{value:"<fmt:formatDate value='${customerContract.establishDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' },group: "<label style=white-space:nowrap;>产品信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "天数单位", name: "fldDayUnit", newline: false, type: "text", attr:{readonly: "readonly"}},
            {display: "实际天数", name: "fldClearDays", newline: false, type: "text", attr:{value:"${customerContract.clearDays}",readonly: "readonly"}},
            {display: "年化收益率", name: "fldAnnualizedRate", newline: true, type: "text", attr:{value:"${customerContract.fldAnnualizedRate}",readonly: "readonly"},group: "<label style=white-space:nowrap;>收益信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "预期收益", name: "fldAnnualizedMoney", newline: false, type: "text", attr:{value:"${customerContract.fldAnnualizedMoney}",readonly: "readonly"}},
            {display: "年化7天存款率", name: "fldDepositRate", newline: true, type: "text", attr:{value:"${customerContract.fldDepositRate}",readonly: "readonly"}},
            {display: "募集期天数", name: "fldCollectDays", newline: false, type: "text", attr:{value:"${customerContract.fldCollectDays}",readonly: "readonly"}},
            {display: "募集期贴息", name: "fldCollectMoney", newline: false, type: "text", attr:{value:"${customerContract.fldCollectMoney}",readonly: "readonly"}},
            {display: "业绩系数", name: "fldPerformanceRadio", newline: true, type: "text", attr:{value:"${customerContract.fldPerformanceRadio}",readonly: "readonly"}},
            {display: "业绩额度", name: "fldPerformanceMoney", newline: false, type: "text", attr:{value:"${customerContract.fldPerformanceMoney}",readonly: "readonly"}},
            {display: "佣金系数", name: "fldCommissionRadio", newline: true, type: "text", attr:{value:"${customerContract.fldCommissionRadio}",readonly: "readonly"}},
            {display: "佣金金额", name: "fldCommissionMoney", newline: false, type: "text", attr:{value:"${customerContract.fldCommissionMoney}",readonly: "readonly"}},
            {display: "银行卡号", name: "fldBankNo", newline: true, type: "text", attr:{value:"${customerContract.fldBankNo}",readonly: "readonly"},validate: { maxlength: 64},group: "<label style=white-space:nowrap;>银行卡信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "开户银行", name: "fldBankName", newline: false, type: "text", attr:{value:"${customerContract.fldBankName}",readonly: "readonly"},validate: { maxlength: 64}},
            {display: "理财经理", name: "fldFinancialUserNo", newline: true, type: "select",group: "<label style=white-space:nowrap;>其他信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "financialUserNo", options: {valueFieldID: "financialUserNo"}},
            {display: "瑞得卡金额", name: "fldCardMoney", newline: false, attr:{value:"${customerContract.fldCardMoney}",readonly: "readonly"}, type: "text"},
            {display: "瑞得卡卡号", name: "fldCardNo", newline: true, type: "text", attr:{value:"${customerContract.fldCardNo}",readonly: "readonly"}, validate: { maxlength: 32}},
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
    
    $("#fldDayUnit").val(renderLabel(dayUnitData,${customerContract.dayUnit}));;
    
	$.ligerui.get("customerName")._changeValue('${customerContract.fldCustomerId}', '${customerContract.customer.fldName}');
    
	$.ligerui.get("productId")._changeValue('${customerContract.fldProductDetailId}', '${customerContract.productDetail.customerProduct.fldFullName}');
	
	$.ligerui.get("financialUserNo")._changeValue('${customerContract.fldFinancialUserNo}', '${customerContract.financialUserName}');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel);
    
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
</script>
</body>
</html>
