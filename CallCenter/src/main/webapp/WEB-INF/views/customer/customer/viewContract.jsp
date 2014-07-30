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
	var statusData =<sys:dictList type = "8"/>;
	var cardLevelData =<sys:dictList type = "13"/>;
	
	var where = '{"op":"and","rules":[{"op":"like","field":"fldCustomerId","value":"${customerId}","type":"string"}]}';
	
	var toolbarOptions = {
    	items:[
        {
            id:'add', text:'导出',
            img:'<c:url value="/static/ligerUI/icons/miniicons/action_save.gif"/>',
            click:function (item) {
            	var url = '<c:url value="/customer/customer/exportContract?where='+where+'"/>';
            	exportContract(url);
            }
        }
    	]
	};
	
	function exportContract(url) {
		LG.ajax({
            loading:'正在导出数据中...',
            url:url,
            data:{where:where},
            success:function (data, message) {
                window.location.href = '<c:url value="/customer/common/download?filepath="/>' + encodeURIComponent(data[0]);
                LG.tip(message);
            },
            error:function (message) {
                LG.tip(message);
            }
        });
	}
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: false,
	    rownumbers: true,
	    delayLoad: false,
	    columnWidth: 180,
	    columns: [
	        {display: "客户姓名", name: "customerName"},
	        {display: "身份证号", name: "identityNo"},
	        {display: "合同编号", name: "fldId"},
	        {display: "产品全称", name: "productFullName"},
	        {display: "产品实际天数", name: "productClearDays"},
	        {display: "所属理财经理", name: "financialUserName"},
	        {display: "银行卡号", name: "fldBankNo"},
	        {display: "瑞得卡号", name: "fldCardNo"},
	        {display: "瑞得卡等级", name: "fldCardLevel",
	        	render:function(item) {
	        		return renderLabel(cardLevelData,item.fldCardLevel);
	        	}
	        },
	        {display: "合同状态", name: "fldStatus",
	        	render:function(item) {
	        		return renderLabel(statusData,item.fldStatus);
	        	}
	        },
	        {display: "签订日期", name: "fldSignDate"},
	        {display: "是否已到期", name: "fldFinishStatus"},
	        {display: "打款日期", name: "fldMoneyDate"},
	        {display: "购买金额(万元)", name: "fldPurchaseMoney"},
	        {display: "预期收益", name: "fldAnnualizedMoney"},
	        {display: "业绩系数", name: "fldPerformanceRadio"},
	        {display: "业绩额度", name: "fldPerformanceMoney"},
	        {display: "操作人", name: "operateUserName"},
	        {display: "操作时间", name: "fldOperateDate"}
	    ], dataAction: 'server', pageSize: 50, toolbar: toolbarOptions, url: '<c:url value="/customer/contract/list?where='+where+'"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});
	
	function f_reload() {
	    grid.loadData();
	}

	resizeDataGrid(grid);
</script>
</body>
</html>