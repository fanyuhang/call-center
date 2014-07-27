<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	//列表结构
	var where = '{"op":"and","rules":[{"op":"eq","field":"fldAssignId","value":"${detailId}","type":"string"}]}';
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: false,
	    columnWidth: 180,
	    columns: [
	    	{display: "ID", name: "fldId", hide:1,width:1},
	        {display: "话务员", name: "fldCallUserNo"},
	        {display: "任务日期", name: "fldTaskDate"},
	        {display: "话务数", name: "fldTaskNumber"},
	        {display: "已拨打数", name: "fldFinishNumber"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/assign/listDetail"/>'+'?where='+where,
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>