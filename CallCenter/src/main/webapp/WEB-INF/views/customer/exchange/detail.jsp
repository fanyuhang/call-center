<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
    .ui-autocomplete-loading {
        background: white url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>') right center no-repeat;
    }
</style>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<input type="hidden" id="customerId" value="${customerId}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	var statusData =<sys:dictList type = "6"/>;
	var cardLevelData =<sys:dictList type = "13"/>;
	
	var exchangeId = '${exchangeId}';
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: false,
	    columnWidth: 180,
	    columns: [
	    	{display: "ID", name: "fldId", hide:1,width:1},
	        {display: "客户姓名", name: "fldName"},
	        {display: "身份证号", name: "fldIdentityNo"},
	        {display: "手机号", name: "fldMobile"},
	        {display: "固定电话", name: "fldPhone"},
	        {display: "客户状态", name: "fldStatus",
	        	render:function(item) {
	        		return renderLabel(statusData,item.fldStatus);
	        	}
	        },
	        {display: "所属理财经理", name: "financialUserName"},
	        {display: "瑞得卡号", name: "fldCardNo"},
	        {display: "瑞得卡等级", name: "fldCardLevel",
	        	render:function(item) {
	        		return renderLabel(cardLevelData,item.fldCardLevel);
	        	}
	        },
	        {display: "操作人", name: "operateUserName"},
	        {display: "操作时间", name: "fldOperateDate"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/customer/exchange/listCustomer?exchangeId='+exchangeId+'"/>', 
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});
	
	function f_reload() {
	    grid.loadData();
	}

	resizeDataGrid(grid);
</script>
</body>
</html>