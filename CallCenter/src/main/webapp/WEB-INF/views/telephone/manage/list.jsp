<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
    .ui-autocomplete-loading {
        background: white url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>') right center no-repeat;
    }
</style>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<input type="hidden" id="fileName"/>
<div id="mainsearch" style=" width:98%">
    <div class="searchtitle">
        <span>搜索</span><img src='<c:url value="/static/ligerUI/icons/32X32/searchtool.gif"/>'/>
        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom:4px; margin-top:4px;"></div>
    <div class="searchbox">
        <form id="formsearch" class="l-form"></form>
    </div>
</div>
<div id="maingrid"></div>
<script type="text/javascript">
	var finishStatusData = <sys:dictList type = "18" nullable="true"/>;
	
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "话务员", name: "callUser.userName", newline: true, type: "text", cssClass: "field"},
	        {display: "分配人", name: "assignUser.userName", newline: false, type: "text", cssClass: "field"},
	       	{display: "完成状态", name: "fldFinishStatus", newline: false, type: "select", cssClass: "field",
	        	options: {
	                valueFieldID: "fldFinishStatus",
	                valueField: "value",
	                textField: "text",
	                data: finishStatusData
	            }, attr: {"op": "equal", "vt": "int"}
        	},
	        {display: "任务开始时间", name: "startDate", newline: true, type: "date", cssClass: "field",
	        	attr:{op:'greaterorequal', vt:'date', field:"fldTaskDate"}},
	        {display: "任务结束时间", name: "endDate", newline: false, type: "date", cssClass: "field",
	        	attr:{op:'lessorequal', vt:'date', field:"fldTaskDate"}}
	    ],
	    toJSON: JSON2.stringify
	});
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: true,
	    columnWidth: 180,
	    columns: [
	    	{display: "ID", name: "fldId", hide:1,width:1},
	        {display: "话务员", name: "callUserName"},
	        {display: "任务时间", name: "fldTaskDate"},
	        {display: "话务数", name: "fldTaskNumber"},
	        {display: "已拨打数", name: "fldFinishNumber"},
	        {display: "完成状态", name: "fldFinishStatus",
	        	render:function(item) {
	        		return renderLabel(finishStatusData,item.fldFinishStatus);
	        	}
	        },
	        {display: "分配人", name: "assignUserName"},
	        {display: "分配时间", name: "fldAssignDate"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/manage/list"/>',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	        case "exchange":
	        	top.f_addTab(null, '任务交接', '<c:url value="/telephone/manage/exchange"/>' + '?menuNo=${menuNo}');
	          	break;
	        case "detail":
	        	if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '任务明细', '<c:url value="/telephone/manage/listTask"/>' + '?menuNo=${menuNo}&id='+selected.fldId);
	       		break;
	       	case "recover":
	            top.f_addTab(null, '任务回收', '<c:url value="/telephone/manage/recover"/>' + '?menuNo=${menuNo}');
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