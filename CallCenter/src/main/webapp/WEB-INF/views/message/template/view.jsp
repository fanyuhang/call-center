<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../../include/formHeader.jsp"%>
<body style="padding-bottom: 31px;">
	<form:form id="mainform" name="mainform" method="post"
		modelAttribute="messageTemplate"></form:form>
	<script type="text/javascript">
		
		var messageTemplateStatusData = <sys:dictList type = "12"/>;

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
								display : "短信模板名称",
								name : "fldName",
								newline : true,
								type : "text",
								attr : {
									value : "${messageTemplate.fldName}",
									readonly : "readonly"
								},
								group : "<label style=white-space:nowrap;>短信模板基本信息</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							}, {
								display : "短信模板状态",
								name : "fldStatus",
								newline : false,
								type : "select",
								options : {
									valueField : 'value',
									textField : 'text',
									isMultiSelect : false,
									data : messageTemplateStatusData,
									initValue : '${messageTemplate.fldStatus}',
									valueFieldID : "fldStatus",
								}
							}, {
								display : "短信模板内容",
								name : "fldContent",
								newline : true,
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							}, {
								display : "短信模板备注",
								name : "fldComment",
								newline : false,
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							}, ]
				});

		$("#fldContent").text('${messageTemplate.fldContent}');	
		$("#fldComment").text('${messageTemplate.fldComment}');	
		
		function f_cancel() {
			var win = parent || window;
			win.LG.closeCurrentTab(null);
		}
	</script>
</body>
</html>