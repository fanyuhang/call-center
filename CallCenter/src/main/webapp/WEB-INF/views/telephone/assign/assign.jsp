<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post"></form:form>
<script type="text/javascript">
	var telephoneSourceData =<sys:dictList type = "17"/>;
	
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
            {display: "话务员",name: "fldCallUserNo", newline: true, type: "select", validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "callUserNo", options: {valueFieldID: "callUserNo"}},
            {display: "话务来源", name: "fldSource", newline: false, type: "select", attr:{readonly: "readonly"},validate: {required: true}, cssClass: "field", 
	        	options: {
	                valueFieldID: "fldSource",
	                valueField: "value",
	                textField: "text",
	                data: telephoneSourceData
	            }, attr: {"op": "equal", "vt": "int"},comboboxName:"telephoneSource"
        	},
            {display: "可分配话务数", name: "taskCount", newline: true, type: "text", attr:{readonly: "readonly"}},
            {display: "话务数", name: "fldTaskNumber", newline: false, type: "text", validate: {required: true}},
            {display: "话务员数", name: "fldCallUserNumber", newline: true, type: "text", attr:{readonly: "readonly"},group: "<label style=white-space:nowrap;>话务分配信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "平均话务数", name: "fldAverageNumber", newline: false, type: "text", attr:{readonly: "readonly"}},
            {display: "话务开始时间", name: "fldBeginDate", newline: true, type: "date", attr:{readonly: "readonly"}, validate: {required: true}},
            {display: "话务结束时间", name: "fldEndDate", newline: false, type: "date", attr:{readonly: "readonly"}, validate: {required: true}},
            {display: "天数", name: "fldDayNumber", newline: true, type: "text", attr:{readonly: "readonly"}}
        ]
    });

    mainform.attr("action", '<c:url value="/telephone/assign/save"/>');
    
    $.ligerui.get("fldBeginDate").bind("changedate",function(){
     	var fldBeginDate = $("#fldBeginDate").val();
     	var fldEndDate = $("#fldEndDate").val();
     	if(fldBeginDate == "" || fldEndDate == "") return;
     	
     	if(fldBeginDate>fldEndDate) {
     		LG.showError("开始时间不能晚于结束时间!");
     		return;
     	}
     	
     	var days = (Date.parse(fldEndDate) - Date.parse(fldBeginDate))/(1000*60*60*24) + 1;
     	$("#fldDayNumber").val(days);
    });
     
    $.ligerui.get("fldEndDate").bind("changedate",function(){
     	var fldBeginDate = $("#fldBeginDate").val();
     	var fldEndDate = $("#fldEndDate").val();
     	if(fldBeginDate == "" || fldEndDate == "") return;
     	
     	if(fldBeginDate>fldEndDate) {
     		LG.showError("开始时间不能晚于结束时间!");
     		return;
     	}
     	
     	var days = (Date.parse(fldEndDate) - Date.parse(fldBeginDate))/(1000*60*60*24) + 1;
     	$("#fldDayNumber").val(days);
    });
    
    $.ligerui.get("callUserNo").openSelect({
	    grid:{
	    	rownumbers: true,
	    	checkbox: true,
	    	columnWidth: 238,
	        columns:[
	            {display:"用户名称", name:"userName"},
	            {display:"登录名称", name:"loginName"},
	            {display:"部门", name:"deptName"}
	        ], pageSize:20,heightDiff:-10,
	        url:'<c:url value="/security/user/list"/>', sortName:'userName'
	    },
	    search:{
	        fields:[
	            {display:"用户名称", name:"userName", newline:true, type:"text", cssClass:"field"}
	        ]
	    },
	    valueField:'loginName', textField:'userName', top:30,
	    handleSelect:function(data){
	    	$("#fldCallUserNumber").val(data.length);
			
			var fldTaskNumber = $("#fldTaskNumber").val();
			if(fldTaskNumber== "" || fldTaskNumber == 0) return;
		
			var fldCallUserNumber = $("#fldCallUserNumber").val();
			if(fldCallUserNumber == "" || fldCallUserNumber == 0) return;
		
			$("#fldAverageNumber").val(parseFloat(fldTaskNumber)/parseFloat(fldCallUserNumber));
	    }
	});
	
	$("#telephoneSource").change(function(){
		var source = $("#fldSource").val();
		if(source == "") return;
		if(source == 0) {
			LG.ajax({
                url: '<c:url value="/telephone/assign/countImport"/>',
                data: {},
                beforeSend: function () {
                	
                },
                complete: function () {
                },
                success: function (message) {
                	$("#taskCount").val(message);
                },
                error: function (message) {
                }
            });
		} else {
			LG.ajax({
                url: '<c:url value="/telephone/assign/countTask"/>',
                data: {},
                beforeSend: function () {
                	
                },
                complete: function () {
                },
                success: function (message) {
                	$("#taskCount").val(message);
                },
                error: function (message) {
                }
            });
		}
	});
	
	$("#fldTaskNumber").change(function(){
		var fldTaskNumber = $("#fldTaskNumber").val();
		if(fldTaskNumber == "" || fldTaskNumber == 0) return;
		
		var fldCallUserNumber = $("#fldCallUserNumber").val();
		if(fldCallUserNumber == "" || fldCallUserNumber == 0) return;
		
		$("#fldAverageNumber").val(parseFloat(fldTaskNumber)/parseFloat(fldCallUserNumber));
	});

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);
    
    function f_save() {
    	LG.validate(mainform);
    	
    	if($("#fldCallUserNo").val()==""){
    		LG.showError("请选择话务员!");
			return;
    	}
    	
    	if($("#fldSource").val()==""){
    		LG.showError("请选择话务来源!");
			return;
    	}
    	
    	var taskCount = $("#taskCount").val();
		var fldTaskNumber = $("#fldTaskNumber").val();
		if(parseInt(taskCount) < parseInt(fldTaskNumber)) {
			LG.showError("话务数不能大于可分配话务数!");
			return;
		}
		
		var fldBeginDate = $("#fldBeginDate").val();
     	var fldEndDate = $("#fldEndDate").val();
     	
     	if(fldBeginDate != "" && fldEndDate != "" && fldBeginDate>fldEndDate) {
     		LG.showError("开始时间不能晚于结束时间!");
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
