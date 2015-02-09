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
	        {display: "话务员", name: "callUserName"},
	        {display: "任务日期", name: "fldTaskDate"},
	        {display: "话务总数", name: "fldTaskNumber",render:function(item){
	        	if(item.fldTaskNumber > 0) {
	        		return "<a href='javascript:void(0);' onclick=\"javascript:showDtl('"+item.fldId+"');\" title='查看分配详情'>"+item.fldTaskNumber+"</a>";
	        	} else 
	        		return ''+item.fldTaskNumber+'';
	        }},
	        {display: "已拨打数", name: "fldFinishNumber",render:function(item){
	        	if(item.fldFinishNumber > 0) {
	        		return "<a href='javascript:void(0);' onclick=\"javascript:showDialDtl('"+item.fldId+"');\" title='查看拨打详情'>"+item.fldFinishNumber+"</a>";
	        	} else 
	        		return ''+item.fldFinishNumber+'';
	        }}
	    ], dataAction: 'server', pageSize: 20, url: '<c:url value="/telephone/assign/listDetail"/>'+'?where='+where,
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});
	
	function showDtl(dtlId) {
		top.f_addTab(null, '查看话务分配详情', '<c:url value="/telephone/assign/viewDtlTask"/>' + '?menuNo=${menuNo}&dtlId='+dtlId);
	}
	
	function showDialDtl(dtlId) {
		top.f_addTab(null, '查看话务拨打详情', '<c:url value="/telephone/assign/viewDialTask"/>' + '?menuNo=${menuNo}&dtlId='+dtlId);
	}

	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>