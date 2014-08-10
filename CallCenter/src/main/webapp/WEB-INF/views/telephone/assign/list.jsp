<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
    .ui-autocomplete-loading {
        background: white url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>') right center no-repeat;
    }
</style>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
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
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "话务分配人", name: "createUser.userName", newline: true, type: "text", cssClass: "field"},
	        {display: "分配时间", name: "startDate", newline: true, type: "date", cssClass: "field",
	        	attr:{op:'greaterorequal', vt:'date', field:"fldOperateDate"}},
	        {display: "至", name: "endDate", newline: false, type: "date", cssClass: "field",
	        	attr:{op:'lessorequal', vt:'date', field:"fldOperateDate"}}
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
	        {display: "话务分配人", name: "createUserName"},
	        {display: "分配时间", name: "fldCreateDate"},
	        {display: "使用话务数", name: "fldAssignNumber"},
	        {display: "话务员数", name: "fldCallUserNumber"},
	        {display: "任务开始时间", name: "fldBeginDate"},
	        {display: "任务结束时间", name: "fldEndDate"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/assign/list"/>',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	        case "assign":
	        	top.f_addTab(null, '分配话务', '<c:url value="/telephone/assign/assign"/>' + '?menuNo=${menuNo}');
	            break;
	       	case "detail":
	       		if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '话务明细', '<c:url value="/telephone/assign/assignDetail"/>' + '?menuNo=${menuNo}&id='+selected.fldId);
	       		break;
	       	case "recover":
	       		if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	          var selected = grid.getSelected();
	          recover(selected.fldId);
	       		break;
	    }
	}

	function f_reload() {
	    grid.loadData();
	}
	
	function recover(id) {
		LG.ajax({
        loading:'正在回收中...',
        url:'<c:url value="/telephone/assign/recover"/>',
        data:{fldId:id},
        success:function (data, message) {
            LG.tip(message);
        },
        error:function (message) {
            LG.tip(message);
        }
    });
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>