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
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "原客服", name: "oldUser.userName", newline: true, type: "text", cssClass: "field"},
	        {display: "新客服", name: "newUser.userName", newline: false, type: "text", cssClass: "field"},
	        {display: "交接开始时间", name: "startDate", newline: true, type: "date", cssClass: "field",
	        	attr:{op:'greaterorequal', vt:'date', field:"fldOperateDate"}},
	        {display: "交接结束时间", name: "endDate", newline: false, type: "date", cssClass: "field",
	        	attr:{op:'lessorequal', vt:'date', field:"fldOperateDate"}}
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
	        {display: "原客服", name: "oldUserName"},
	        {display: "新客服", name: "newUserName"},
	        {display: "交接时间", name: "fldOperateDate"},
	        {display: "原客服客户数量", name: "fldOldCustomerNum"},
	        {display: "新客服客户数量", name: "fldNewCustomerNum"},
	        {display: "交接客户数量", name: "fldCustomerNum"},
	        {display: "交接合同数量", name: "fldContractNum"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/customer/exchange/list"/>',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	        case "exchange":
	            top.f_addTab(null, '交接客户', '<c:url value="/customer/exchange/exchange"/>' + '?menuNo=${menuNo}');
	            break;
	        case "detail":
	        	if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '查看客户交接信息', '<c:url value="/customer/exchange/detail"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId);
	            break;
	    }
	}

	function f_reload() {
	    grid.loadData();
	}

	function f_delete() {
	    var selected = grid.getSelected();
	    if (selected) {
	        LG.ajax({
	            url: '<c:url value="/customer/customer/delete"/>',
	            loading: '正在删除中...',
	            data: { fldId: selected.fldId},
	            success: function () {
	                LG.showSuccess('删除客户成功');
	                f_reload();
	            },
	            error: function (message) {
	                LG.showError(message);
	            }
	        });
	    } else {
	        LG.tip('请选择行!');
	    }
	}

	resizeDataGrid(grid);
</script>
</body>
</html>