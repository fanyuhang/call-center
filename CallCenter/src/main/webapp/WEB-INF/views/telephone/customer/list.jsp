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
	var sourceData = <sys:dictList type = "25" nullable="true"/>;
	var assignStatusData = <sys:dictList type = "24" nullable="true"/>;
	
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "客户名称", name: "fldCustomerName", newline: true, type: "text", cssClass: "field"},
	        {display: "手机", name: "fldMobile", newline: false, type: "text", cssClass: "field"},
	        {display: "电话号码", name: "fldPhone", newline: false, type: "text", cssClass: "field"},
	        {display: "话务来源", name: "fldSource", newline: true, type: "select", cssClass: "field",
	        	options: {
	                valueFieldID: "fldSource",
	                valueField: "value",
	                textField: "text",
	                data: sourceData
	            }, attr: {"op": "equal", "vt": "int"}
        	},
        	{display: "分配状态", name: "fldAssignStatus", newline: false, type: "select", cssClass: "field",
	        	options: {
	                valueFieldID: "fldAssignStatus",
	                valueField: "value",
	                textField: "text",
	                data: assignStatusData
	            }, attr: {"op": "equal", "vt": "int"}
        	}
	    ],
	    toJSON: JSON2.stringify
	});
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: true,
	    rownumbers: true,
	    delayLoad: true,
	    columnWidth: 180,
	    columns: [
	    	{display: "ID", name: "fldId", hide:1,width:1},
	        {display: "客户名称", name: "fldCustomerName"},
	        {display: "手机", name: "fldMobile"},
	        {display: "电话号码", name: "fldPhone"},
	        {display: "分配状态", name: "fldAssignStatus",
                render:function(item) {
                    return renderLabel(assignStatusData,item.fldAssignStatus);
                }
            },
	        {display: "最近通话时间", name: "fldLatestCallDate"},
	        {display: "分配日期", name: "fldAssignDate"},
	        {display: "话务来源", name: "fldSource",
                render:function(item) {
                    return renderLabel(sourceData,item.fldSource);
                }
            }
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/customer/list"/>',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	       	case "ascustomer":
	       		if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            ascustomer(selected.fldId);
	       		break;
	        case "export":
	        	f_export('<c:url value="/telephone/customer/export"/>');
	        	break;
	    }
	}
	
	function ascustomer(id) {
		LG.ajax({
        	loading:'正在处理中...',
        	url:'<c:url value="/telephone/customer/ascustomer"/>',
        	data:{fldId:id},
        	success:function (data, message) {
            	LG.tip(message);
        	},
        	error:function (message) {
            	LG.tip(message);
        	}
    	});
	}

	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>