<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../../include/formHeader.jsp"%>
<body style="padding-bottom: 31px;">
	<form:form id="mainform" name="mainform" method="post"
		modelAttribute="messageTemplate">
	</form:form>
	<script type="text/javascript">
		//覆盖本页面grid的loading效果
		LG.overrideGridLoading();

		//创建表单结构
		var mainform = $("#mainform");
		mainform
				.ligerForm({
					inputWidth : 250,
					labelWidth : 100,
					space : 30,
					fields : [
							{
								display : "模板名称",
								name : "fldName",
								newline : true,
								type : "text",
								validate : {
									required : true,
									maxlength : 25
								},
								group : "<label style=white-space:nowrap;>基本信息</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							}, {
								display : "模板内容",
								name : "fldContent",
								newline : true,
								type : "textarea",
                                width:"630",
								validate : {
									required : true,
									maxlength : 500
								},
                                group : "<label style=white-space:nowrap;>内容</label>",
                                groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							}, {
								display : "模板备注",
								name : "fldComment",
								newline : true,
								type : "textarea",
                                width:"630",
                                validate : {
									maxlength : 500
								},
                                group : "<label style=white-space:nowrap;>备注</label>",
                                groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							} ]
				});

		mainform.attr("action", '<c:url value="/message/template/save"/>');

		//表单底部按钮
		LG.setFormDefaultBtn(f_cancel, f_check);

		function f_check() {
			f_save();
		}

		function f_save() {
			LG.validate(mainform);

			LG.submitForm(mainform, function(data) {
				var win = parent || window;
				if (data.IsError == false) {
					win.LG.showSuccess('保存成功', function() {
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
