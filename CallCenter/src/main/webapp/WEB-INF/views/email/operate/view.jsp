<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../../include/formHeader.jsp"%>
<body style="padding-bottom: 31px;">
	<form:form id="mainform" name="mainform" method="post"
		modelAttribute="emailOperate"></form:form>
	<script type="text/javascript">
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
								display : "发送时间",
								name : "fldCreateDate",
								type : "text",
								newline : false,
								attr : {
									value : "${emailOperate.fldCreateDate}",
									readonly : "readonly"
								},
								group : "<label style=white-space:nowrap;>状态信息</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							},
							{
								display : "发送数量",
								name : "fldEmailNum",
								newline : false,
								type : "text",
								attr : {
									value : "${emailOperate.fldEmailNum}",
									readonly : "readonly"
								}
							},
							{
								display : "发送人",
								name : "createUserName",
								type : "text",
								newline : true,
								attr : {
									value : "${emailOperate.createUserName}",
									readonly : "readonly"
								}
							},
							{
								display : "发送邮箱",
								name : "fldSenderEmail",
								type : "text",
								newline : false,
								attr : {
									value : "${emailOperate.fldSenderEmail}",
									readonly : "readonly"
								}
							},
							{
								display : "收件邮箱",
								name : "fldReceiverEmails",
								newline : true,
								width : "630",
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							},
							{
								display : "使用邮件模板",
								name : "emailTemplateName",
								newline : true,
								type : "text",
								attr : {
									value : "${emailOperate.emailTemplateName}",
									readonly : "readonly"
								},
								group : "<label style=white-space:nowrap;>基本信息</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							}, {
								display : "邮件标题",
								name : "fldTitle",
								newline : false,
								type : "text",
								attr : {
									value : "${emailOperate.fldTitle}",
									readonly : "readonly"
								}
							}, {
								display : "邮件内容",
								name : "fldContent",
								width : "630",
								newline : true,
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							} ]
				});

		$("#fldReceiverEmails").text('${emailOperate.fldReceiverEmails}');
		$("#fldContent").text('${emailOperate.fldContent}');

		function f_cancel() {
			var win = parent || window;
			win.LG.closeCurrentTab(null);
		}
	</script>
</body>
</html>