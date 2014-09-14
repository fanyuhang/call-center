<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post"></form:form>
<script type="text/javascript">
	var userTypeData = <sys:dictList type = "19"/>;
	
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
            {display: "话务员", name: "fldOldCallUserNo", newline: true, type: "select", group: "<label style=white-space:nowrap;>交接信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "oldCallUserNo", options: {valueFieldID: "oldCallUserNo"},validate: {required: true}},
            {display:"话务员话务数",name:"fldOldBeforeNumber",newline:true,type:"text",group: "<label style=white-space:nowrap;>话务员信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	attr:{readonly: "readonly"}},
            {display:"已完成数",name:"oldFinishNumber",newline:false,type:"text",attr:{readonly: "readonly"}},
            {display:"待完成数",name:"oldUnFinishNumber",newline:false,type:"text",attr:{readonly: "readonly"}}
        ]
    });
    
    $("#oldCallUserNo").change(function(){
    	var oldCallUserNo = $("#fldOldCallUserNo").val();
    	if(oldCallUserNo == "") {
    		$("#fldOldBeforeNumber").val(0);
    		$("#oldFinishNumber").val(0);
    		$("#oldUnFinishNumber").val(0);
    		return;
    	}
    	
    	LG.ajax({
            url: '<c:url value="/telephone/manage/getCount"/>',
            data: {fldCallUserNo:oldCallUserNo},
            beforeSend: function () {
            	
            },
            complete: function () {
            },
            success: function (data) {
            	var count = data[0];
            	$("#fldOldBeforeNumber").val(count.fldTaskNumber);
    			$("#oldFinishNumber").val(count.fldFinishNumber);
    			$("#oldUnFinishNumber").val(parseInt(count.fldTaskNumber)-parseInt(count.fldFinishNumber));
            },
            error: function (message) {
          		
            }
        });
    });
    
	$.ligerui.get("oldCallUserNo").openSelect({
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
	            {display:"用户名称", name:"userName", newline:true, type:"text", cssClass:"field"},
	            {display:"用户类型",name:"type",newline:false,type:"select",comboboxName:"userType",cssClass: "field",
	            	options:{
	                    valueField: 'value',
	                    textField: 'text',
	                    isMultiSelect:false,
	                    data:userTypeData,
	                    valueFieldID:"type"
	                }
	            }
	        ]
	    },
	    valueField:'loginName', textField:'userName', top:30
	});
	
    mainform.attr("action", '<c:url value="/telephone/manage/saveRecover"/>');

	function f_save() {
	    LG.validate(mainform);
	
	    var oldCallUserNo = $("#oldCallUserNo").val();
        if (!oldCallUserNo) {
       		LG.showError("请选择话务员");
       		return;
   		}
   		
   		var oldUnFinishNumber = $("#oldUnFinishNumber").val();
   		if(oldUnFinishNumber < 1) {
   			LG.showError("没有话务需要回收");
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