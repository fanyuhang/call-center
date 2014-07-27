<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="customer"></form:form>
<script type="text/javascript">
	var genderData =<sys:dictList type = "1"/>;
	var sourceData =<sys:dictList type = "10"/>;
	
	//覆盖本页面grid的loading效果
	LG.overrideGridLoading();

	//表单底部按钮
	LG.setFormDefaultBtn(f_cancel, f_save);

	//创建表单结构
	var mainform = $("#mainform");
	mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
        	{display: "ID", name: "fldId", type:"hidden", attr:{value:"${customer.fldId}"}},
            {display: "客户姓名",name: "fldName", newline: true, type: "text", attr: {value: "${customer.fldName}", readonly: "readonly"}, validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "客户来源", name: "fldSource", newline: false, type: "select",
                options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:sourceData,
                    initValue: '${customer.fldSource}',
                    valueFieldID:"fldSource"
                }},
            {display:"性别",name:"fldGender",newline:true,type:"select",
                options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:genderData,
                    initValue: '${customer.fldGender}',
                    valueFieldID:"fldGender"
                }},
            {display: "出生日期", name: "fldBirthday", newline: false, type: "date", attr:{value:"<fmt:formatDate value='${customer.fldBirthday}' pattern='yyyy-MM-dd'/>", readonly: "readonly"}},
            {display: "身份证号", name: "fldIdentityNo", newline: true, type: "text", attr: {value: "${customer.fldIdentityNo}", readonly: "readonly"}},
            {display: "固定电话", name: "fldPhone", newline: true, type: "text", attr: {value: "${customer.fldPhone}", readonly: "readonly"}, group: "<label style=white-space:nowrap;>联系信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "手机", name: "fldMobile", newline: false, type: "text", attr: {value: "${customer.fldMobile}", readonly: "readonly"}},
            {display: "地址", name: "fldAddress", newline: true, type: "text", attr: {value: "${customer.fldAddress}", readonly: "readonly"}},
            {display: "邮箱", name: "fldEmail", newline: false, type: "text", attr: {value: "${customer.fldEmail}", readonly: "readonly"}},
            {display: "所属理财经理", name: "fldFinancialUserNo", newline: true, type: "select", group: "<label style=white-space:nowrap;>其他信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "financialUserNo", options: {valueFieldID: "financialUserNo"}},
            {display: "所属客户经理", name: "fldCustomerUserNo", newline: false, type: "select", 
				comboboxName: "customerUserNo", options: {valueFieldID: "customerUserNo"}},
            {display: "所属客服", name: "fldServiceUserNo", newline: true, type: "select", 
            	comboboxName: "serviceUserNo", options: {valueFieldID: "serviceUserNo"}},
            {display: "新客服", name: "newServiceUserNo", newline: false, type: "select", validate: {required: true},
            	comboboxName: "newServiceUser", options: {valueFieldID: "newServiceUser"}},
            {display: "瑞得卡", name: "fldCardNo", newline: true, type: "text", attr: {value: "${customer.fldCardNo}", readonly: "readonly"}},
            {display: "瑞得卡等级", name: "fldCardLevel", newline: false, type: "text", attr: {value: "${customer.fldCardLevel}", readonly: "readonly"}},
            {display: "备注", name: "fldComment", newline: true, type: "text", attr: {value: "${customer.fldComment}"}, readonly: "readonly"}
        ]
    });
    
	$.ligerui.get("financialUserNo")._changeValue('${customer.fldFinancialUserNo}', '${customer.financialUserName}');
	
	$.ligerui.get("customerUserNo")._changeValue('${customer.fldCustomerUserNo}', '${customer.customerUserName}');
	
	$.ligerui.get("serviceUserNo")._changeValue('${customer.fldServiceUserNo}', '${customer.serviceUserName}');
	
	$.ligerui.get("newServiceUser").openSelect({
	    grid:{
	    	columnWidth: 255,
	        columns:[
	            {display:"用户名称", name:"userName"},
	            {display:"登录名称", name:"loginName"},
	            {display:"部门", name:"deptName"}
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

    mainform.attr("action", '<c:url value="/customer/customer/updateFinancialUser"/>');

	function f_save() {
	    LG.validate(mainform);
	
	    var newServiceUser = $("#newServiceUser").val();
           if (!newServiceUser) {
       		LG.showError("请选择新客服");
       		return;
   		}
	
	    LG.submitForm(mainform, function (data) {
	        var win = parent || window;
	        if (data.IsError == false) {
	            win.LG.showSuccess(data.Message, function () {
	                win.LG.closeAndReloadParent(null, "${menuNo}");
	            });
	        }
	        else {
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