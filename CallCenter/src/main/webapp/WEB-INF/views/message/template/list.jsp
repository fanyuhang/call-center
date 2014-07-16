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
	<ipnut type="hidden" id="MenuNo" value="${menuNo}" />
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
		var messageTemplateStatusData = <sys:dictList type = "12"/>;
		//搜索表单应用ligerui样式
		$("#formsearch").ligerForm({
			labelWidth : 100,
			inputWidth : 150,
			space : 30,
			fields : [ {
				display : "模板编号",
				name : "fldId",
				newline : true,
				type : "text",
				cssClass : "field"
			}, {
				display : "模板名称",
				name : "fldName",
				newline : false,
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
					data : messageTemplateStatusData
				}
			}, {
				display : "操作人",
				name : "user.fldOperateUserNo",
				newline : true,
				type : "text",
				cssClass : "field"
			}, {
				display : "操作开始时间",
				name : "operateStartDate",
				newline : false,
				type : "date",
				cssClass : "field",
				attr : {
					op : 'greaterorequal',
					vt : 'date',
					field : "fldOperateDate"
				}
			}, {
				display : "操作结束时间",
				name : "operateEndDate",
				newline : false,
				type : "date",
				cssClass : "field",
				attr : {
					op : 'lessorequal',
					vt : 'date',
					field : "fldOperateDate"
				}
			}, {
				display : "创建人",
				name : "user.fldCreateUserNo",
				newline : true,
				type : "text",
				cssClass : "field"
			}, {
				display : "创建开始时间",
				name : "createStartDate",
				newline : false,
				type : "date",
				cssClass : "field",
				attr : {
					op : 'greaterorequal',
					vt : 'date',
					field : "fldCreateDate"
				}
			}, {
				display : "创建结束时间",
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
		var grid = $("#maingrid").ligerGrid({
			checkbox : true,
			rownumbers : true,
			delayLoad : true,
			columnWidth : 180,
			columns : [ {
				display : "ID",
				name : "fldId",
				hide : 1,
				width : 1
			}, {
				display : "模板编号",
				name : "fldId"
			}, {
				display : "模板名称",
				name : "fldName"
			}, {
				display : "模板内容",
				name : "fldContent"
			}, {
				display : "模板状态",
				name : "fldStatus",
				render : function(item) {
					return renderLabel(statusData, item.fldStatus);
				}
			}, {
				display : "操作人",
				name : "fldOperateUserNo"
			}, {
				display : "操作时间",
				name : "fldOperateDate"
			}, {
				display : "创建人",
				name : "fldCreateUserNo"
			}, {
				display : "创建时间",
				name : "fldCreateDate"
			} ],
			dataAction : 'server',
			pageSize : 20,
			toolbar : {},
			url : '<c:url value="/message/template/list"/>',
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

		resizeDataGrid(grid);
	</script>
</body>
</html>