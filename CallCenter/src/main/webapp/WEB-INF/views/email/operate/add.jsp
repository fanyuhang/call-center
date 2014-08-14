<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../../include/formHeader.jsp"%>
<body style="padding-bottom: 31px;">
	<form:form id="mainform" name="mainform" method="post"
		modelAttribute="emailOperate">
	</form:form>
	<script type="text/javascript">
		var emailTemplateStatusData = <sys:dictList type = "29"/>;

		//覆盖本页面grid的loading效果
		LG.overrideGridLoading();

		//创建表单结构
		var mainform = $("#mainform");
		mainform
				.ligerForm({
					inputWidth : 250,
					labelWidth : 160,
					space : 10,
					fields : [
							{
								display : "发送者邮箱",
								name : "fldSenderEmail",
								newline : true,
								type : "text",
								validate : {
									required : true,
								},
								attr : {
									value : "${emailSendAccount}",
									readonly : "readonly"
								},
								group : "<label style=white-space:nowrap;>邮件发送</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							},
							{
								display : "接收邮箱列表<br/>（多个邮箱以';'隔开，头尾不加分号！）",
								name : "fldReceiverEmails",
								newline : true,
								width : "630",
								type : "textarea",
								validate : {
									required : true
								}
							},
							{
								display : "邮件模板",
								name : "fldEmailTemplateId",
								newline : true,
								type : "select",
								comboboxName : "emailTemplateId",
								options : {
									valueFieldID : "emailTemplateId"
								},
								group : "<label style=white-space:nowrap;>邮件内容</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							}, {
								display : "邮件标题",
								name : "fldTitle",
								newline : true,
								type : "text",
								validate : {
									required : true,
									maxlength : 128
								}
							}, {
								display : "邮件内容",
								name : "fldContent",
								newline : true,
								type : "textarea",
								width : "630",
								validate : {
									required : true,
									maxlength : 1000
								}
							} ]
				});

		mainform.attr("action", '<c:url value="/email/operate/save"/>');

		$.ligerui.get("emailTemplateId").openSelect({
			grid : {
				columnWidth : 255,
				columns : [ {
					display : "ID",
					name : "fldId",
					hide : 1
				}, {
					display : "邮件模板名称",
					name : "fldName"
				}, {
					display : "邮件模板标题",
					name : "fldTitle"
				}, {
					display : "邮件模板内容",
					name : "fldContent",
					hidden : true
				} ],
				pageSize : 20,
				heightDiff : -10,
				url : '<c:url value="/email/template/conditionalList"/>',
				sortName : 'fldName',
				checkbox : false
			},
			search : {
				fields : [ {
					display : "邮件模板名称",
					name : "fldName",
					newline : true,
					type : "text",
					cssClass : "field"
				}, {
					display : "邮件模板标题",
					name : "fldTitle",
					newline : false,
					type : "text",
					cssClass : "field"
				} ]
			},
			valueField : 'fldId',
			textField : 'fldName',
			top : 30,
			handleSelect : function(data) {
				if (data.length < 1) {
					return;
				} else {
					$("#fldTitle").val(data[0].fldTitle);
					$("#fldContent").val(data[0].fldContent);
				}
			}
		});

		//表单底部按钮
		LG.setFormDefaultBtn(f_cancel, f_check);

		function f_check() {
			var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
			var sendEmail = $("#fldSenderEmail").val();//发送者邮箱
			if (!(reg.test(sendEmail))) {
				jQuery.ligerDialog.alert("发送者邮箱格式不正确！");
				return;
			}
			var emails = $("#fldReceiverEmails").val();//接收邮箱地址必须以;隔开
			if (emails.indexOf(";") < 0) { //如果只是一个邮箱则没有问题；
				if (!(reg.test(emails))) {
					jQuery.ligerDialog.alert("接收者邮箱格式不正确！");
					return;
				}
			} else {
				var emailArray = emails.split(";");
				for (var i = 0; i < emailArray.length; i++) {
					if (!(reg.test(emailArray[i]))) {
						jQuery.ligerDialog.alert("第" + (parseInt(i) + 1)
								+ "个接收者邮箱格式不合法！");
						return;
					}
				}
			}
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
