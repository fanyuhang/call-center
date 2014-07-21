<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../../include/formHeader.jsp"%>
<body style="padding-bottom: 31px;">
	<form:form id="mainform" name="mainform" method="post"
		modelAttribute="messageOperate"></form:form>
	<script type="text/javascript">
		var messageSendStatusData = <sys:dictList type = "16"/>;

		//覆盖本页面grid的loading效果
		LG.overrideGridLoading();

		//表单底部按钮
		LG.setFormDefaultBtn(f_cancel);

		//创建表单结构
		var mainform = $("#mainform");
		mainform
				.ligerForm({
					inputWidth : 250,
					labelWidth : 100,
					space : 30,
					fields : [
							{
								display : "短信模板编号",
								name : "fldMessageTemplateId",
								newline : true,
								type : "text",
								attr : {
									value : "${messageOperate.fldMessageTemplateId}",
									readonly : "readonly"
								},
								group : "<label style=white-space:nowrap;>短信发送记录详情</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							},
							{
								display : "短信模板名称",
								name : "messageTemplateName",
								newline : false,
								type : "text",
								attr : {
									value : "${messageOperate.messageTemplateName}",
									readonly : "readonly"
								}
							},
							{
								display : "短信内容",
								name : "fldContent",
								newline : false,
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							},
							{
								display : "短信发送状态",
								name : "fldSendStatus",
								newline : true,
								type : "select",
								options : {
									valueField : 'value',
									textField : 'text',
									isMultiSelect : false,
									data : messageSendStatusData,
									initValue : '${messageOperate.fldSendStatus}',
									valueFieldID : "fldSendStatus",
								}
							}, {
								display : "短信发送数量",
								name : "fldMessageNum",
								newline : false,
								type : "text",
								attr : {
									value : "${messageOperate.fldMessageNum}",
									readonly : "readonly"
								}
							}, {
								display : "手机列表",
								name : "fldMobiles",
								newline : false,
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							}, {
								display : "发送结果描述",
								name : "fldSendResult",
								newline : true,
								type : "text",
								attr : {
									value : "${messageOperate.fldSendResult}",
									readonly : "readonly"
								}
							}, {
								display : "短信发送任务编号",
								name : "fldTaskId",
								newline : false,
								type : "text",
								attr : {
									value : "${messageOperate.fldTaskId}",
									readonly : "readonly"
								}
							}, {
								display : "短信发送备注",
								name : "fldComment",
								newline : false,
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							}, {
								display : "短信发送人",
								name : "createUserName",
								type : "text",
								newline : true,
								attr : {
									value : "${messageOperate.createUserName}",
									readonly : "readonly"
								}
							}, {
								display : "短信发送时间",
								name : "fldCreateDate",
								type : "text",
								newline : false,
								attr : {
									value : "${messageOperate.fldCreateDate}",
									readonly : "readonly"
								}
							} ]
				});

		$("#fldContent").text('${messageOperate.fldContent}');
		$("#fldMobiles").text('${messageOperate.fldMobiles}');
		$("#fldComment").text('${messageOperate.fldComment}');

		function f_cancel() {
			var win = parent || window;
			win.LG.closeCurrentTab(null);
		}
	</script>
</body>
</html>