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
	var callTypeData = <sys:dictList type = "28"/>;
	var resultTypeData = <sys:dictList type = "27"/>;
	
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "话务员", name: "callUser.userName", newline: true, type: "text", cssClass: "field"},
	       	{display: "呼叫类型", name: "fldCallType", newline: false, type: "select", cssClass: "field",
	        	options: {
	                valueFieldID: "fldCallType",
	                valueField: "value",
	                textField: "text",
	                data: callTypeData
	            }, attr: {"op": "equal", "vt": "int"}
        	},
        	{display: "跟踪结果", name: "fldResultType", newline: false, type: "select", cssClass: "field",
	        	options: {
	                valueFieldID: "fldResultType",
	                valueField: "value",
	                textField: "text",
	                data: resultTypeData
	            }, attr: {"op": "equal", "vt": "int"}
        	},
        	{display: "拨打号码", name: "fldPhone", newline: false, type: "text", cssClass: "field"},
        	{display: "客户姓名", name: "fldCustomerName", newline: true, type: "text", cssClass: "field"},
	        {display: "拨打起始时间", name: "startDate", newline: false, type: "date", cssClass: "field",
	        	attr:{op:'greaterorequal', vt:'date', field:"fldCallBeginTime"}},
	        {display: "拨打结束时间", name: "endDate", newline: false, type: "date", cssClass: "field",
	        	attr:{op:'lessorequal', vt:'date', field:"fldCallBeginTime"}},
            {display: "通话时长 > ", name: "fldCallLongStart", newline: true, cssClass: "field", type: "text", attr: {"op": 'greater',"vt":'int', field:"fldCallLong"}},
            {display: '通话时长 < ', name: "fldCallLongEnd", newline: false, cssClass: "field", type: "text", attr: {"op": 'less',"vt":'int', field:"fldCallLong"}}
        ],
	    toJSON: JSON2.stringify
	});
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: false,
	    columnWidth: 160,
	    columns: [
	        {display: "话务员", name: "callUserName"},
	        {display: "呼叫类型", name: "fldCallType",
	        	render:function(item) {
	        		return renderLabel(callTypeData,item.fldCallType);
	        	}
	        },
	        {display: "通话时间", name: "fldCallDate"},
            {display: "总时长(秒)", name: "fldTotalDuration",width:"100"},
            {display: "通话时长(秒)", name: "fldCallLong",width:"100"},
            {display: "拨打号码", name: "fldPhone",
                render:function(item) {
                    return LG.hiddenPhone(item.fldPhone);
                }},
            {display: "客户姓名", name: "fldCustomerName"},
            {display: "跟踪结果", name: "fldResultType",
	        	render:function(item) {
	        		return renderLabel(resultTypeData,item.fldResultType);
	        	}
	        },
	        {display: "备注", name: "fldComment",width:170}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/record/list"/>',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	    case "export":
        	f_export('<c:url value="/telephone/record/export"/>');
        	break;
	    case "view":
	    	if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
        	LG.tip('请选择一行数据!');
          return;
        }
        var selected = grid.getSelected();
        top.f_addTab(null, '查看通话详细', '<c:url value="/telephone/record/view"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId);
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