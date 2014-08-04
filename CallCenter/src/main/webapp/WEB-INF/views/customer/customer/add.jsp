<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="customer">
</form:form>
<script type="text/javascript">
	var genderData =<sys:dictList type = "1"/>;
	var sourceData =<sys:dictList type = "10"/>;
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
            {display: "客户姓名",name: "fldName", newline: true, type: "text", validate: {required: true, maxlength: 64}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "客户来源", name: "fldSource", newline: false, type: "select",
                options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:sourceData,
                    valueFieldID:"fldSource",
                    initValue:0
                }},
            {display:"性别",name:"fldGender",newline:true,type:"select",
                options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:genderData,
                    valueFieldID:"fldGender",
                    initValue:1
                }},
            {display: "出生日期", name: "fldBirthday", newline: false, type: "date", attr:{readonly: "readonly"}},
            {display: "身份证号", name: "fldIdentityNo", newline: true, type: "text", validate: {maxlength: 32}},
            {display: "固定电话", name: "fldPhone", newline: true, type: "text", validate: { maxlength: 32}, group: "<label style=white-space:nowrap;>联系信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "手机", name: "fldMobile", newline: false, type: "text", validate: { maxlength: 100}},
            {display: "地址", name: "fldAddress", newline: true, type: "text", validate: { maxlength: 64}},
            {display: "邮箱", name: "fldEmail", newline: false, type: "text", validate: { maxlength: 100}},
            {display: "理财经理", name: "fldFinancialUserNo", newline: true, type: "select", group: "<label style=white-space:nowrap;>归属信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
				comboboxName: "financialUserNo", options: {valueFieldID: "financialUserNo"}},
            {display: "客户经理", name: "fldCustomerUserNo", newline: false, type: "select",
            	comboboxName: "customerUserNo", options: {valueFieldID: "customerUserNo"}},
            {display: "客服经理", name: "fldServiceUserNo", newline: true, type: "select",
            	comboboxName: "serviceUserNo", options: {valueFieldID: "serviceUserNo"}},
            {display: "瑞得卡", name: "fldCardNo", newline: true, type: "text", validate: { maxlength: 32}, group: "<label style=white-space:nowrap;>瑞得卡</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "瑞得卡等级", name: "fldCardLevel", newline: false, type: "select",
            	options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:cardLevelData,
                    initValue:'0',
                    valueFieldID:"fldCardLevel"
                }},
            {display: "备注", name: "fldComment", newline: true, type: "text", width:630, validate: { maxlength: 64}, group: "<label style=white-space:nowrap;>备注</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/customer/save"/>');
    
    $.ligerui.get("financialUserNo").openSelect({
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
	
	$.ligerui.get("customerUserNo").openSelect({
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
	
	$.ligerui.get("serviceUserNo").openSelect({
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
