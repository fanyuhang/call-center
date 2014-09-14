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
        	{display: "跟踪结果", name: "fldResultType", newline: false, type: "select", cssClass: "field",
	        	options: {
	                valueFieldID: "fldResultType",
	                valueField: "value",
	                textField: "text",
	                data: resultTypeData
	            }, attr: {"op": "equal", "vt": "int"}
        	},
        	{display: "拨打号码", name: "fldPhone", newline: true, type: "text", cssClass: "field"},
        	{display: "客户姓名", name: "fldCustomerName", newline: false, type: "text", cssClass: "field"},
	        {display: "拨打起始时间", name: "startDate", newline: true, type: "date", cssClass: "field",
	        	attr:{op:'greaterorequal', vt:'date', field:"fldCallBeginTime"}},
	        {display: "拨打结束时间", name: "endDate", newline: false, type: "date", cssClass: "field",
	        	attr:{op:'lessorequal', vt:'date', field:"fldCallBeginTime"}}
	    ],
	    toJSON: JSON2.stringify
	});
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: true,
	    columnWidth: 160,
	    columns: [
	        {display:"ID",name:"fldId",width:1,hide:1},
	        {display: "话务员", name: "callUserName"},
	        {display: "呼叫类型", name: "fldCallType",
	        	render:function(item) {
	        		return renderLabel(callTypeData,item.fldCallType);
	        	}
	        },
	        {display: "呼叫时间", name: "fldCallBeginTime"},
	        {display: "跟踪结果", name: "fldResultType",
	        	render:function(item) {
	        		return renderLabel(resultTypeData,item.fldResultType);
	        	}
	        },
	        {display: "拨打号码", name: "fldPhone"},
	        {display: "客户姓名", name: "fldCustomerName"},
	        {display: "备注", name: "fldComment",width:170}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/record/list"/>?type=1',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	    case "audit":
	    	if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
        		LG.tip('请选择一行数据!');
          		return;
        	}
        	var selected = grid.getSelected();
        	top.f_addTab(null, '呼叫审查', '<c:url value="/telephone/record/view"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId);
        	break;
	    }
	}

	function f_reload() {
	    grid.loadData();
	}
</script>
</body>
</html>