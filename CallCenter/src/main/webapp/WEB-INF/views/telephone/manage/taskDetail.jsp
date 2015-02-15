<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	var callStatusData = <sys:dictList type = "23" nullable="true"/>;
	var resultTypeData = <sys:dictList type = "27"/>;
    var taskStatusData = <sys:dictList type = "30" nullable="true"/>;
    var taskTypeData = <sys:dictList type = "33" nullable="true"/>;

    var toolbarOptions = {
    	items:[
        {
            id:'export', text:'下载',
            img:'<c:url value="/static/ligerUI/icons/miniicons/action_save.gif"/>',
            click:function (item) {
                exportDtl();
            }
        }
    	]
	};
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: false,
	    columnWidth: 180,
	    columns: [
	        {display: "话务员", name: "callUserName"},
            {display: "任务类型", name: "fldTaskType",
                render:function(item) {
                    return renderLabel(taskTypeData,item.fldTaskType);
                }
            },
            {display: "客户姓名", name: "fldCustomerName"},
            {display: "任务状态", name: "fldTaskStatus",render:function(item){
                return renderLabel(taskStatusData,item.fldTaskStatus);
            }},
            {display: "任务时间", name: "fldTaskDate"},
            {display: "拨打时间", name: "fldCallDate"},
            {display: "拨打状态", name: "fldCallStatus",
	        	render: function (item) {
                    return renderLabel(callStatusData, item.fldCallStatus);
                }
            },
            {display:"任务结果",name:"fldResultType",render:function(item){
	        	return renderLabel(resultTypeData,item.fldResultType);
	        }},
	        {display:"任务备注",name:"fldComment"},
	        {display:"审查分数",name:"fldAuditFraction"},
	        {display:"审查备注",name:"fldAuditComment"}
	    ], dataAction: 'server', pageSize: 20, toolbar: toolbarOptions, url: '<c:url value="/telephone/manage/listTaskDetail"/>?fldAssignDetailId=${assignDetailId}',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});
	
	function exportDtl() {
		f_export('<c:url value="/telephone/manage/export"/>?fldAssignDetailId=${assignDetailId}');
	}

	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>