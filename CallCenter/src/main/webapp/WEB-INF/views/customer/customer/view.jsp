<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="customer"></form:form>
<script type="text/javascript">
	var genderData =<sys:dictList type = "1"/>;
	
	//覆盖本页面grid的loading效果
	LG.overrideGridLoading();

	//表单底部按钮
	LG.setFormDefaultBtn(f_cancel);

	//创建表单结构
	var mainform = $("#mainform");
	mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
            {display: "客户姓名",name: "fldName", newline: true, type: "text", attr: {value: "${customer.fldName}", readonly: "readonly"}, validate: {required: true, maxlength: 64}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "客户来源", name: "fldSource", newline: false, type: "text", attr:{value:"${customer.fldSource}", readonly: "readonly"}},
            {display:"性别",name:"fldGender",newline:true,type:"select",
                options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:genderData,
                    initValue: '${customer.fldGender}',
                    valueFieldID:"fldGender"
                }},
            {display: "出生日期", name: "fldBirthdayStr", newline: false, type: "date", attr:{value: "${customer.fldBirthdayStr}", readonly: "readonly"}},
            {display: "身份证号", name: "fldIdentityNo", newline: true, type: "text", attr: {value: "${customer.fldIdentityNo}", readonly: "readonly"}, validate: {maxlength: 32}},
            {display: "固定电话", name: "fldPhone", newline: true, type: "text", attr: {value: "${customer.fldPhone}", readonly: "readonly"}, validate: { maxlength: 32}, group: "<label style=white-space:nowrap;>联系信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "手机", name: "fldMobile", newline: false, type: "text", attr: {value: "${customer.fldMobile}", readonly: "readonly"}, validate: { maxlength: 100}},
            {display: "地址", name: "fldAddress", newline: true, type: "text", attr: {value: "${customer.fldAddress}", readonly: "readonly"}, validate: { maxlength: 64}},
            {display: "邮箱", name: "fldEmail", newline: false, type: "text", attr: {value: "${customer.fldEmail}", readonly: "readonly"}, validate: { maxlength: 100}},
            {display: "所属理财经理", name: "fldFinancialUserNo", newline: true, type: "text", attr: {value: "${customer.fldFinancialUserNo}", readonly: "readonly"}, validate: { maxlength: 32}, group: "<label style=white-space:nowrap;>其他信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "所属客户经理", name: "fldCustomerUserNo", newline: false, type: "text", attr: {value: "${customer.fldCustomerUserNo}", readonly: "readonly"}, validate: { maxlength: 32}},
            {display: "所属客服", name: "fldServiceUserNo", newline: true, type: "text", attr: {value: "${customer.fldServiceUserNo}", readonly: "readonly"}, validate: { maxlength: 32}},
            {display: "瑞得卡", name: "fldCardNo", newline: false, type: "text", attr: {value: "${customer.fldCardNo}", readonly: "readonly"}, validate: { maxlength: 32}},
            {display: "瑞得卡等级", name: "fldCardLevel", newline: true, type: "text", attr: {value: "${customer.fldCardLevel}", readonly: "readonly"}},
            {display: "备注", name: "fldComment", newline: false, type: "text", attr: {value: "${customer.fldComment}", readonly: "readonly"}, validate: { maxlength: 64}}
        ]
    });

	function f_cancel() {
	    var win = parent || window;
	    win.LG.closeCurrentTab(null);
	}
</script>
</body>
</html>