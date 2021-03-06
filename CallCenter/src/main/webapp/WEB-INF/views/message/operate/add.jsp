<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@include file="../../include/formHeader.jsp"%>
<body style="padding-bottom: 31px;">
	<form:form id="mainform" name="mainform" method="post"
		modelAttribute="messageOperate">
	</form:form>
	<script type="text/javascript">
		var messageTemplateStatusData = <sys:dictList type = "12"/>;

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
								display : "模板",
								name : "fldMessageTemplateId",
								newline : true,
								type : "select",
								comboboxName : "messageTemplateId",
								options : {
									valueFieldID : "messageTemplateId"
								},
								group : "<label style=white-space:nowrap;>短信发送</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							},
							{
								display : "手机列表<br/>（多个手机号以';'隔开，头尾不加分号！）",
								name : "fldMobiles",
								newline : true,
								width : "630",
								type : "textarea",
								validate : {
									required : true
								}
							},
							{
								display : "内容",
								name : "fldContent",
								newline : true,
								type : "textarea",
								width : "630",
								validate : {
									required : true,
									maxlength : 500
								},
								group : "<label style=white-space:nowrap;>内容</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							},
							{
								display : "备注",
								name : "fldComment",
								newline : true,
								width : "630",
								type : "textarea",
								validate : {
									maxlength : 500
								},
								group : "<label style=white-space:nowrap;>备注</label>",
								groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							} ]
				});

		mainform.attr("action", '<c:url value="/message/operate/save"/>');

		$("#fldContent").blur(function() {
			f_operateMessageSignFlag($(this));
		});

		$("#messageTemplateId")
				.change(
						function() {
							var chosedMessageTemplateId = $(
									"#fldMessageTemplateId").val();
							if (chosedMessageTemplateId == ""
									|| chosedMessageTemplateId == null) {
								$("#fldContent").val('');
								return;
							}
							LG
									.ajax({
										url : '<c:url value="/message/template/findMessageTemplateDetail"/>',
										data : {
											fldId : chosedMessageTemplateId
										},
										beforeSend : function() {
										},
										complete : function() {
										},
										success : function(data) {
											$("#fldContent").val(
													data[0].fldContent);
											f_operateMessageSignFlag($("#fldContent"));
										},
										error : function(message) {
										}
									});
						});

		$.ligerui.get("messageTemplateId").openSelect({
			grid : {
				columnWidth : 255,
				columns : [ {
					display : "ID",
					name : "fldId",
					hide : 1,
					width : 1
				}, {
					display : "短信模板名称",
					name : "fldName"
				}, {
					display : "短信模板内容",
					name : "fldContent"
				}, {
					display : "短信模板备注",
					name : "fldComment"
				} ],
				pageSize : 20,
				heightDiff : -10,
				url : '<c:url value="/message/template/conditionalList"/>',
				sortName : 'fldName',
				checkbox : false
			},
			search : {
				fields : [ {
					display : "短信模板名称",
					name : "fldName",
					newline : true,
					type : "text",
					cssClass : "field"
				} ]
			},
			valueField : 'fldId',
			textField : 'fldName',
			top : 30
		});

		//表单底部按钮
		LG.setFormDefaultBtn(f_cancel, f_check);

		//校验短信内容必须包含签名目前为【聚金理财】、【瑞得支付】
		//校验手机列表的格式必须是以;隔开的一系列的手机号，否则提示具体的第几个手机号格式有误！
		function f_check() {
			var mobileNos = $("#fldMobiles").val();//手机号必须以;隔开
			if (mobileNos.indexOf(";") < 0) { //如果只是一个手机号则没有问题；
				if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobileNos))) {
					alert("不是完整的11位手机号或者正确的手机号前七位");
					return;
				}
			} else {
				var mobileNoArray = mobileNos.split(";");
				for (var i = 0; i < mobileNoArray.length; i++) {
					if (!(/^1[3|4|5|8][0-9]\d{4,8}$/.test(mobileNoArray[i]))) {
						alert("第" + (parseInt(i) + 1)
								+ "个不是完整的11位手机号或者正确的手机号前七位");
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

		function f_operateMessageSignFlag(obj) {//处理是否需要在短信内容里加短信签名
			var messageContent = obj.val();
			if (messageContent.indexOf("${sendMessageSignFlag}") < 0) {
				obj.val(messageContent + '${sendMessageSignFlag}');
			} else {
				var signIndex = messageContent
						.lastIndexOf("${sendMessageSignFlag}");
				if (signIndex != messageContent.length - 6) {
					obj.val(messageContent + '${sendMessageSignFlag}');
				}
			}
		}
	</script>
</body>
</html>
