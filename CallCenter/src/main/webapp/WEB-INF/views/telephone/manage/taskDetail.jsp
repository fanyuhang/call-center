<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	var callStatusData = <sys:dictList type = "23" nullable="true"/>;
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: false,
	    columnWidth: 180,
	    columns: [
	        {display: "话务员", name: "callUserName"},
	        {display: "客户姓名", name: "fldCustomerName"},
	        {display: "任务时间", name: "fldTaskDate"},
	        {display: "拨打状态", name: "fldCallStatus",
	        	render: function (item) {
                    return renderLabel(callStatusData, item.fldCallStatus);
                }
            },
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/manage/listTaskDetail"/>'+'?fldAssignDetailId=${assignDetailId}',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>