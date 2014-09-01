<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<script src="/static/jPlayer/jquery.jplayer.min.js" type="text/javascript"></script>
<body style="padding-bottom:31px;">
<div id="formBtn" style="display:none; text-align:left; float:left; clear:both;">
	<input id="playRecord" onclick="javascript:listenRecord();" type="button" value="播放录音" style="height:20px;width:69px;background:url(/static/ligerUI/icons/silkicons/control_play_blue.png) no-repeat left bottom;text-indent:17px;border:1px solid;border-color:#b1b1b1;cursor:pointer;"/>
	<input id="pauseRecord" onclick="javascript:stopRecord();" type="button" value="停止播放" style="height:20px;width:69px;background:url(/static/ligerUI/icons/silkicons/control_pause_blue.png) no-repeat left bottom;text-indent:17px;border:1px solid;border-color:#b1b1b1;cursor:pointer;"/>
</div>
<div id="listenRecord"></div>
<form:form id="mainform" name="mainform" method="post"></form:form>
<script type="text/javascript">
	var callTypeData = <sys:dictList type = "28"/>;
	var resultTypeData = <sys:dictList type = "27"/>;
	
	//覆盖本页面grid的loading效果
	LG.overrideGridLoading();

	//表单底部按钮
	LG.setFormDefaultBtn(f_cancel,f_save);
	
	var fields = [
	              {display:"ID",name:"fldId",type:"hidden",attr:{value:"${telephoneRecord.fldId}", readonly: "readonly"}},
	              {display: "客户姓名",name: "fldName", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCustomerName}", readonly: "readonly"},group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
	              {display: "拨打号码", name: "fldPhone", newline: false, type: "text",attr:{value:"${telephoneRecord.fldPhone}", readonly: "readonly"}},
	              {display:"呼叫类型",name:"fldCallType",newline:true,type:"select",
	                  options:{
	                      valueField: 'value',
	                      textField: 'text',
	                      isMultiSelect:false,
	                      data: callTypeData,
	                      initValue: '${telephoneRecord.fldCallType}',
	                      valueFieldID:"fldCallType"
	                  }},
	              {display:"跟踪结果",name:"fldResultType",newline:false,type:"select",
	                  options:{
	                      valueField: 'value',
	                      textField: 'text',
	                      isMultiSelect:false,
	                      data: resultTypeData,
	                      initValue: '${telephoneRecord.fldResultType}',
	                      valueFieldID:"fldResultType"
	                  }},
	              {display: "备注", name: "fldComment", newline: true, type: "text",attr:{value:"${telephoneRecord.fldComment}", readonly: "readonly"}},
	              {display: "拨打/呼入时间",name: "fldCallDate", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCallDate}", readonly: "readonly"},group: "<label style=white-space:nowrap;>通话信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
	              {display: "通话时长(秒)", name: "fldCallLong", newline: false, type: "date", attr:{value:"${telephoneRecord.fldCallLong}", readonly: "readonly"}},
	              {display: "通话开始时间", name: "fldCallBeginTime", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCallBeginTime}", readonly: "readonly"}},
	              {display: "通话结束时间", name: "fldCallEndTime", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCallEndTime}", readonly: "readonly"}},
	              {display: "录音", name: "fldRecordFilePath", newline: false, type: "text",width:71,fieldCss:"border:10px;"},
	              {display: "审查分数",name: "fldAuditFraction", newline: true, type: "text",validate: {required: true},group: "<label style=white-space:nowrap;>审查信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
	              {display: "审查备注",name:"fldAuditComment",newline:false,type:"text"}
	          ];
	
	//创建表单结构
	var mainform = $("#mainform");
	mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: fields
    });

	var panle = $("#fldRecordFilePath").parent();
	$("#fldRecordFilePath").parent().hide();
	$("#pauseRecord").hide();
	$("#formBtn").insertAfter(panle).show();
	
	$(document).ready(function(){
		var recordFile = "${phoneRecordAddress}${telephoneRecord.fldRecordFilePath}";
	    $("#listenRecord").jPlayer({  
	        ready: function () {   
	           $(this).jPlayer("setMedia", {
	        	   mp3: recordFile,
	        	   wav: recordFile
	        	 }); },   
	        swfPath: "/static/jPlayer",   
	        supplied: "mp3,wav",
	        wmode: "window"
	   })
	}); 
	
	function listenRecord() {
	  $("#listenRecord").jPlayer("play");
	  $("#playRecord").hide();
	  $("#pauseRecord").show();
	}
	
	function stopRecord() {
		$("#listenRecord").jPlayer("stop");
		$("#playRecord").show();
		$("#pauseRecord").hide();
	}
	
	function f_save() {
		var fldAuditFraction = $("#fldAuditFraction").val();
		var fldAuditComment = $("#fldAuditComment").val();
		
		if ($("#fldAuditFraction").val() == "") {
	  	LG.showError("请录入审查分数!");
	    return;
	  }
		
		LG.ajax({
        url: '<c:url value="/telephone/record/saveAudit"/>' + '?id=${telephoneRecord.fldId}&taskId=${telephoneRecord.fldTaskId}'+'&fldAuditFraction='+fldAuditFraction+'&fldAuditComment='+fldAuditComment,
        data: {},
        beforeSend: function () {

        },
        complete: function () {
        },
        success: function (message) {
        	f_cancel();
        },
        error: function (message) {
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