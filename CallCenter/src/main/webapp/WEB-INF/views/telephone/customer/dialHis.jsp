<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	var callTypeData = <sys:dictList type = "28"/>;

    var toolbarOptions = {
        items:[
            {
                id:'view', text:'查看详细',
                img:'/static/ligerUI/icons/miniicons/action_save.gif',
                click:function (item) {
                    if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                        LG.tip('请选择一行数据!');
                        return;
                    }
                    var selected = grid.getSelected();
                    top.f_addTab(null, '查看通话详细', '<c:url value="/telephone/record/view"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId);
                }
            }
        ]
    };

    //列表结构
	var where = encodeURI('?customerName=${customerName}&phone=${phone}&mobile=${mobile}');
	var grid = $("#maingrid").ligerGrid({
	  checkbox: false,
      rownumbers: true,
      delayLoad: false,
      columnWidth: 180,
      columns: [
          {display: "呼叫类型", name: "fldCallType",
          	render: function (item) {
                return renderLabel(callTypeData, item.fldCallType);
            }},
          {display: "拨打号码", name: "fldPhone",
              render:function(item) {
                  return LG.hiddenPhone(item.fldPhone);
              }},
          {display: "拨打/呼入时间", name: "fldCallDate"},
          {display: "通话时长", name: "fldCallLong"},
          {display: "通话开始时间", name: "fldCallBeginTime"},
          {display: "通话结束时间", name: "fldCallEndTime"}
      ], dataAction: 'server', pageSize: 50, toolbar: toolbarOptions, url: '<c:url value="/telephone/dial/dialHis"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
      width: '98%', height: '98%', toJSON: JSON2.stringify
	});
	
	function f_reload() {
	    grid.loadData();
	}
	
	resizeDataGrid(grid);
</script>
</body>
</html>