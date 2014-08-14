<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="../../include/formHeader.jsp"%>
<link
	href='<c:url value="/static/plupload/jquery.ui.plupload/css/jquery.ui.plupload.css"/>'
	rel="stylesheet" type="text/css" />
<script src='<c:url value="/static/plupload/plupload.js" />'
	type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.flash.js" />'
	type="text/javascript"></script>
<script src='<c:url value="/static/plupload/plupload.browserplus.js" />'
	type="text/javascript"></script>
<script src='<c:url value="/static/plupload/i18n/zh.js" />'
	type="text/javascript"></script>
<script
	src='<c:url value="/static/plupload/jquery.ui.plupload/jquery.ui.plupload.js" />'
	type="text/javascript"></script>
<style>
.ui-autocomplete-loading {
	background: white
		url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>')
		right center no-repeat;
}
</style>
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
	<div id="detail" style="display: none;">
		<form:form id="mainform" method="post"></form:form>
	</div>
	<div id="upload" style="display: none;">
		<div id="uploader">
			<p>Your browser doesn't have Flash, Silverlight, Gears,
				BrowserPlus or HTML5 support.</p>
		</div>
	</div>
	<script type="text/javascript">
		var messageSendStatusData = <sys:dictList type = "16"/>;
		//搜索表单应用ligerui样式
		$("#formsearch").ligerForm({
			labelWidth : 120,
			inputWidth : 150,
			space : 30,
			fields : [ {
				display : "发送者邮箱",
				name : "fldSenderEmail",
				newline : true,
				type : "text",
				cssClass : "field"
			}, {
				display : "发送人",
				name : "createUser.userName",
				newline : false,
				type : "text",
				cssClass : "field"
			}, {
				display : "发送开始时间",
				name : "sendEmailStartDate",
				newline : true,
				type : "date",
				cssClass : "field",
				attr : {
					op : 'greaterorequal',
					vt : 'date',
					field : "fldCreateDate"
				}
			}, {
				display : "发送结束时间",
				name : "sendEmailEndDate",
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
		var grid = $("#maingrid").ligerGrid({
			checkbox : false,
			rownumbers : true,
			delayLoad : true,
			columnWidth : 180,
			columns : [ {
				display : "ID",
				name : "fldId",
				hide : 1,
				width : 1
			}, {
				display : "发送者邮箱",
				name : "fldSenderEmail"
			}, {
				display : "发送邮件数量",
				name : "fldEmailNum"
			}, {
				display : "发送人",
				name : "createUserName"
			}, {
				display : "发送时间",
				name : "fldCreateDate"
			} ],
			dataAction : 'server',
			pageSize : 20,
			toolbar : {},
			url : '<c:url value="/email/operate/list"/>',
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
				top.f_addTab(null, '邮件发送',
						'<c:url value="/email/operate/add"/>'
								+ '?menuNo=${menuNo}');
				break;
			case "view":
				if (grid.getSelectedRows().length > 1
						|| grid.getSelectedRows().length == 0) {
					LG.tip('请选择一行数据!');
					return;
				}
				var selected = grid.getSelected();
				top.f_addTab(null, '查看邮件发送详情',
						'<c:url value="/email/operate/view"/>'
								+ '?menuNo=${menuNo}&fldId=' + selected.fldId);
				break;
			}
		}

		function f_reload() {
			grid.loadData();
		}

		resizeDataGrid(grid);
	</script>
</body>
</html>