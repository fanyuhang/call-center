<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	var callStatusData = <sys:dictList type = "23"/>;
	var resultTypeData = <sys:dictList type = "27"/>;

	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: false,
	    columnWidth: 180,
	    columns: [
	    	{display: "ID", name: "fldId", hide:1,width:1},
	        {display: "话务员", name: "callUserName"},
	        {display: "任务日期", name: "fldTaskDate"},
	        {display:"分配时间",name:"fldAssignDate"},
	        {display:"客户姓名",name:"fldCustomerName"},
	        {display:"拨打状态",name:"fldCallStatus",render:function(item){
	        	return renderLabel(callStatusData,item.fldCallStatus);
	        }},
	        {display:"任务结果",name:"fldResultType",render:function(item){
	        	return renderLabel(resultTypeData,item.fldResultType);
	        }}
	    ], dataAction: 'server', pageSize: 20, url: '<c:url value="/telephone/assign/listTaskByAssignDtlid"/>'+'?fldAssignDetailId=${detailId}',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload, usePager:false
	});

	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>