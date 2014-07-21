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
                            display : "发送状态",
                            name : "fldSendStatus",
                            newline : true,
                            type : "select",
                            options : {
                                valueField : 'value',
                                textField : 'text',
                                isMultiSelect : false,
                                data : messageSendStatusData,
                                initValue : '${messageOperate.fldSendStatus}',
                                valueFieldID : "fldSendStatus"
                            },
                            group : "<label style=white-space:nowrap;>状态信息</label>",
                            groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
                        }, {
                            display : "发送数量",
                            name : "fldMessageNum",
                            newline : false,
                            type : "text",
                            attr : {
                                value : "${messageOperate.fldMessageNum}",
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
                            display : "发送任务编号",
                            name : "fldTaskId",
                            newline : false,
                            type : "text",
                            attr : {
                                value : "${messageOperate.fldTaskId}",
                                readonly : "readonly"
                            }
                        }, {
                                display : "发送人",
                                name : "createUserName",
                                type : "text",
                                newline : true,
                                attr : {
                                    value : "${messageOperate.createUserName}",
                                    readonly : "readonly"
                                }
                            }, {
                                display : "发送时间",
                                name : "fldCreateDate",
                                type : "text",
                                newline : false,
                                attr : {
                                    value : "${messageOperate.fldCreateDate}",
                                    readonly : "readonly"
                                }
                            },{
                                display : "使用模板",
                                name : "messageTemplateName",
                                newline : true,
                                type : "text",
                                attr : {
                                    value : "${messageOperate.messageTemplateName}",
                                    readonly : "readonly"
                                },
                            group : "<label style=white-space:nowrap;>基本信息</label>",
                            groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
                            } , {
                                display : "手机列表",
                                name : "fldMobiles",
                                newline : true,
                                width:"630",
                                type : "textarea",
                                attr : {
                                    readonly : "readonly"
                                }
                            }, {
								display : "内容",
								name : "fldContent",
                                width:"630",
                                newline : true,
								type : "textarea",
								attr : {
									readonly : "readonly"
								}
							},
							 {
								display : "备注",
								name : "fldComment",
                                width:"630",
                                newline : true,
								type : "textarea",
								attr : {
									readonly : "readonly"
								},
                            group : "<label style=white-space:nowrap;>备注</label>",
                            groupicon : '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
							}]
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