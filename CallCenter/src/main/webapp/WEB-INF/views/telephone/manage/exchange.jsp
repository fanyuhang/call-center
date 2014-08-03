<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post"></form:form>
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
            {display: "原话务员", name: "fldOldCallUserNo", newline: true, type: "select", group: "<label style=white-space:nowrap;>交接信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	comboboxName: "oldCallUserNo", options: {valueFieldID: "oldCallUserNo"},validate: {required: true}},
            {display: "新话务员", name: "fldNewCallUserNo", newline: false, type: "select", validate: {required: true},
            	comboboxName: "newCallUserNo", options: {valueFieldID: "newCallUserNo"}},
            {display:"交接数量",name:"fldExchangeNumber",newline:true,type:"text",validate: {required: true}},
            {display:"原话务员话务数",name:"fldOldBeforeNumber",newline:true,type:"text",group: "<label style=white-space:nowrap;>原话务员信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	attr:{readonly: "readonly"}},
            {display:"已完成数",name:"oldFinishNumber",newline:false,type:"text",attr:{readonly: "readonly"}},
            {display:"待完成数",name:"oldUnFinishNumber",newline:false,type:"text",attr:{readonly: "readonly"}},
            {display:"新话务员话务数",name:"fldNewBeforeNumber",newline:true,type:"text",group: "<label style=white-space:nowrap;>新话务员信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            	attr:{readonly: "readonly"}},
           	{display:"已完成数",name:"newFinishNumber",newline:false,type:"text",attr:{readonly: "readonly"}},
            {display:"待完成数",name:"newUnFinishNumber",newline:false,type:"text",attr:{readonly: "readonly"}}
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
    					
    					$("#fldExchangeNumber").val(parseInt(count.fldTaskNumber)-parseInt(count.fldFinishNumber));
            },
            error: function (message) {
          		
            }
        });
    });
    
    $("#newCallUserNo").change(function(){
    	var newCallUserNo = $("#fldNewCallUserNo").val();
    	if(newCallUserNo == "") {
    		$("#fldNewBeforeNumber").val(0);
    		$("#newFinishNumber").val(0);
    		$("#newUnFinishNumber").val(0);
    		return;
    	}
    	
    	LG.ajax({
            url: '<c:url value="/telephone/manage/getCount"/>',
            data: {fldCallUserNo:newCallUserNo},
            beforeSend: function () {
            	
            },
            complete: function () {
            },
            success: function (data) {
            	var count = data[0];
            	$("#fldNewBeforeNumber").val(count.fldTaskNumber);
    					$("#newFinishNumber").val(count.fldFinishNumber);
    					$("#newUnFinishNumber").val(parseInt(count.fldTaskNumber)-parseInt(count.fldFinishNumber));
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
	            {display:"用户名称", name:"userName", newline:true, type:"text", cssClass:"field"}
	        ]
	    },
	    valueField:'loginName', textField:'userName', top:30
	});
	
	$.ligerui.get("newCallUserNo").openSelect({
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

    mainform.attr("action", '<c:url value="/telephone/manage/saveExchange"/>');

	function f_save() {
	    LG.validate(mainform);
	
	    var oldCallUserNo = $("#oldCallUserNo").val();
           if (!oldCallUserNo) {
       		LG.showError("请选择原话务员");
       		return;
   		}
   		
   		var newCallUserNo = $("#newCallUserNo").val();
           if (!newCallUserNo) {
       		LG.showError("请选择新话务员");
       		return;
   		}
           
      var fldExchangeNumber = $("#fldExchangeNumber").val();
      if(fldExchangeNumber == "" || fldExchangeNumber == 0) {
    	  LG.showError("请输入交接数量");
     		return;
      } else if(parseInt(fldExchangeNumber) > parseInt($("#oldUnFinishNumber").val())) {
    	  LG.showError("交接数量不能大于原话务员待完成数");
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