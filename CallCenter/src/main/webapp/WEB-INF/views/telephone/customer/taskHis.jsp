<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	var auditStatusData = <sys:dictList type = "31"/>;
	var resultTypeData = <sys:dictList type = "27"/>;
	
	//列表结构
	var where = encodeURI('?customerName=${customerName}&phone=${phone}&mobile=${mobile}');
	var grid = $("#maingrid").ligerGrid({
	  checkbox: false,
      rownumbers: true,
      delayLoad: false,
      columnWidth: 180,
      columns: [
          {display: "话务员", name: "callUserName"},
          {display: "任务时间", name: "fldTaskDate"},
          {display: "任务结果", name: "fldResultType",
          	render: function (item) {
                return renderLabel(resultTypeData, item.fldResultType);
            }},
          {display: "备注", name: "fldComment"},
          {display: "审查状态", name: "fldAuditStatus",
          	render: function (item) {
                return renderLabel(auditStatusData, item.fldAuditStatus);
            }},
          {display: "审查备注", name: "fldAuditComment"}
      ], dataAction: 'server', pageSize: 50, toolbar: null, url: '<c:url value="/telephone/customer/showTaskHis?custId=${customerId}"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
      width: '98%', height: '98%', toJSON: JSON2.stringify
	});
	
	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>