<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="exchangeCustomer"></form:form>
<script type="text/javascript">
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
            {display: "原客服经理", name: "fldOldUserNo", newline: true, type: "select", group: "<label style=white-space:nowrap;>交接信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "serviceUserNo", options: {valueFieldID: "serviceUserNo"},validate: {required: true}},
            {display: "新客服经理", name: "fldNewUserNo", newline: false, type: "select", validate: {required: true},
            	comboboxName: "newServiceUser", options: {valueFieldID: "newServiceUser"}},
            {display:"客户数量",name:"fldOldCustomerNum",newline:true,type:"text",group: "<label style=white-space:nowrap;>原客服经理信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	attr:{readonly: "readonly"}},
            {display:"合同数量",name:"fldOldContractNum",newline:false,type:"text",attr:{readonly: "readonly"}},
            {display:"客户数量",name:"fldNewCustomerNum",newline:true,type:"text",group: "<label style=white-space:nowrap;>新客服经理信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	attr:{readonly: "readonly"}},
            {display:"合同数量",name:"fldNewContractNum",newline:false,type:"text",attr:{readonly: "readonly"}}
        ]
    });
    
    $("#serviceUserNo").change(function(){
    	var serviceUserNo = $("#fldOldUserNo").val();
    	if(serviceUserNo == "") {
    		$("#fldOldContractNum").val(0);
    		$("#fldOldCustomerNum").val(0);
    		return;
    	}
    	
    	LG.ajax({
            url: '<c:url value="/customer/exchange/getCount"/>',
            data: {serviceUserNo:serviceUserNo},
            beforeSend: function () {
            	
            },
            complete: function () {
            },
            success: function (data) {
            	var count = data[0];
            	$("#fldOldContractNum").val(count.contractNum);
    			$("#fldOldCustomerNum").val(count.customerNum);
            },
            error: function (message) {
          		
            }
        });
    });
    
    $("#newServiceUser").change(function(){
    	var newServiceUser = $("#fldNewUserNo").val();
    	if(newServiceUser == "") {
    		$("#fldNewContractNum").val('0');
    		$("#fldNewCustomerNum").val('0');
    		return;
    	}
    	
    	LG.ajax({
            url: '<c:url value="/customer/exchange/getCount"/>',
            data: {serviceUserNo:newServiceUser},
            beforeSend: function () {
            	
            },
            complete: function () {
            },
            success: function (data) {
            	var count = data[0];
            	$("#fldNewContractNum").val(count.contractNum);
    			$("#fldNewCustomerNum").val(count.customerNum);
            },
            error: function (message) {
          		
            }
        });
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

    mainform.attr("action", '<c:url value="/customer/exchange/save"/>');

	function f_save() {
	    LG.validate(mainform);
	
	    var serviceUserNo = $("#serviceUserNo").val();
           if (!serviceUserNo) {
       		LG.showError("请选择原客服");
       		return;
   		}
   		
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