<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../../include/formHeader.jsp"%>
<body style="padding: 10px; height: 100%; text-align: center;">
	<input type="hidden" id="MenuNo" value="${menuNo}" />
	<div id="mainsearch" style="width: 98%">
		<div class="searchtitle">
			<span>搜索</span><img
				src='<c:url value="/static/ligerUI/icons/32X32/searchtool.gif"/>' />

			<div class="togglebtn"></div>
		</div>
		<div class="navline" style="margin-bottom: 4px; margin-top: 4px;"></div>
		<div class="searchbox">
			<form id="formsearch" class="l-form"></form>
		</div>
	</div>
	<div id="maingrid"></div>
	<script type="text/javascript">
		var emailTemplateStatusData = <sys:dictList type = "27"/>;
		//搜索表单应用ligerui样式
		$("#formsearch").ligerForm({
			labelWidth : 100,
			inputWidth : 150,
			space : 30,
			fields : [ {
				display : "模板名称",
				name : "fldName",
				newline : true,
				type : "text",
				cssClass : "field"
			}, {
				display : "模板状态",
				name : "fldStatus",
				newline : false,
				type : "select",
				cssClass : "field",
				options : {
					valueFieldID : "fldStatus",
					valueField : "value",
					textField : "text",
					data : emailTemplateStatusData,
					initValue : 0
				},
				attr : {
					"op" : "equal",
					"vt" : "int"
				}
			}, {
				display : "开始创建时间",
				name : "createStartDate",
				newline : true,
				type : "date",
				cssClass : "field",
				attr : {
					op : 'greaterorequal',
					vt : 'date',
					field : "fldCreateDate"
				}
			}, {
				display : "结束创建时间",
				name : "createEndDate",
				newline : false,
				type : "date",
				cssClass : "field",
				attr : {
					op : 'lessorequal',
					vt : 'date',
					field : "fldCreateDate"
				}
			} ],
			toJSON : JSON2.stringify
		});

		//列表结构
		var grid = $("#maingrid").ligerGrid(
				{
					checkbox : true,
					rownumbers : true,
					delayLoad : true,
					columnWidth : 180,
					columns : [
							{
								display : "模板名称",
								name : "fldName"
							},
							{
								display : "模板状态",
								name : "fldStatus",
								render : function(item) {
									return renderLabel(emailTemplateStatusData,
											item.fldStatus);
								}
							}, {
								display : "操作人",
								name : "operateUserName"
							}, {
								display : "操作时间",
								name : "fldOperateDate"
							}, {
								display : "创建人",
								name : "createUserName"
							}, {
								display : "创建时间",
								name : "fldCreateDate"
							} ],
					dataAction : 'server',
					pageSize : 20,
					toolbar : {},
					url : '<c:url value="/email/template/list"/>',
					sortName : 'operateDate',
					sortOrder : 'desc',
					width : '98%',
					height : '98%',
					toJSON : JSON2.stringify,
					onReload : f_reload
				});

		//增加搜索按钮,并创建事件
		LG.appendSearchButtons("#formsearch", grid, true, true);

		//加载toolbar
		LG.loadToolbar(grid, toolbarBtnItemClick);

		//工具条事件
		function toolbarBtnItemClick(item) {
			switch (item.id) {
			case "add":
				top.f_addTab(null, '新增邮件模板',
						'<c:url value="/email/template/add"/>'
								+ '?menuNo=${menuNo}');
				break;
			case "view":
				if (grid.getSelectedRows().length > 1
						|| grid.getSelectedRows().length == 0) {
					LG.tip('请选择一行数据!');
					return;
				}
				var selected = grid.getSelected();
				top.f_addTab(null, '查看邮件模板',
						'<c:url value="/email/template/view"/>'
								+ '?menuNo=${menuNo}&fldId=' + selected.fldId);
				break;
			case "modify":
				if (grid.getSelectedRows().length > 1
						|| grid.getSelectedRows().length == 0) {
					LG.tip('请选择一行数据!');
					return;
				}
				var selected = grid.getSelected();
				top.f_addTab(null, '修改邮件模板',
						'<c:url value="/email/template/edit"/>'
								+ '?menuNo=${menuNo}&fldId=' + selected.fldId);
				break;
			case "delete":
				if (grid.getSelectedRows().length > 1
						|| grid.getSelectedRows().length == 0) {
					LG.tip('请选择一行数据!');
					return;
				}
				jQuery.ligerDialog.confirm('确定删除吗?', function(confirm) {
					if (confirm)
						f_delete();
				});
				break;
			}
		}

		function f_reload() {
			grid.loadData();
		}

		function f_delete() {
			var selected = grid.getSelected();
			if (selected) {
				LG.ajax({
					url : '<c:url value="/email/template/delete"/>',
					loading : '正在删除中...',
					data : {
						fldId : selected.fldId
					},
					success : function() {
						LG.showSuccess('删除邮件模板成功！');
						f_reload();
					},
					error : function(message) {
						LG.showError(message);
					}
				});
			} else {
				LG.tip('请选择行!');
			}
		}

		resizeDataGrid(grid);
	</script>
</body>
</html>