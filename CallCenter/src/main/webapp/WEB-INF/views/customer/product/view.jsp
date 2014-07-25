<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<style type="text/css">
	.fullscreenBlock{
	    display: none;
	    height: 100%;
	    width: 100%;
	    overflow: hidden;
	    position: absolute;
	    z-index: 10000;
	}
	
	.backgroundBlock{
	    background: gray;
	    opacity: 0.4;
	    height: 100%;
	    width: 100%;
	}
	
	.loading{
	    background: url('/static/ligerUI/images/loading.gif') no-repeat;
	    position: absolute;
	    left: 550px;
	    top: 280px;
	    height: 48px;
	    width: 48px;
	    z-index: 10001;
	}
</style>
<body style="padding-bottom:40px;overflow-y:hidden;">
<div id="layout" style="margin:2px; margin-right:3px;">
	<div id="loadingBlock" class="fullscreenBlock">
        <div class="backgroundBlock"></div>
        <div id="loading" class="loading"></div>
    </div>
	<form:form id="mainform" name="mainform" method="post" modelAttribute="product"></form:form>
	<div position="bottom" title="产品明细">
		<div id="contactgrid"></div>
	</div>
	<div id="detail" style="display:none;">
        <form:form id="detailMainform" name="detailMainform" method="post" modelAttribute="productDetail"></form:form>
    </div>
</div>
<script type="text/javascript">
	var dayUnitData =<sys:dictList type = "14"/>;
	var productTypeData =<sys:dictList type = "7"/>;
	
	function updateGridHeight() {
    	var topHeight = $("#layout > .l-layout-center").height();
    	var bottomHeight = $("#layout > .l-layout-bottom").height();
	}

	var layout = $("#layout").ligerLayout({
   	 	bottomHeight:$(window).height() * 3 / 5,
    	heightDiff:0,
    	onEndResize:updateGridHeight,
    	onHeightChanged:updateGridHeight
	});

	var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");

    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
            {display: "产品编号",name: "fldId", newline: true, type: "text", attr:{value:"${customerProduct.fldId}",readonly: "readonly"},validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "产品全称", name: "fldFullName", newline: false, type: "text", attr:{value:"${customerProduct.fldFullName}",readonly: "readonly"},validate: {required: true}},
            {display: "产品简称", name: "fldShortName", newline: true, type: "text",attr:{value:"${customerProduct.fldShortName}",readonly: "readonly"}},
            {display: "产品描述", name: "fldDescription", newline: false, type: "text", attr:{value:"${customerProduct.fldDescription}",readonly: "readonly"},validate: { maxlength: 64}},
            {display: "成立日期", name: "fldEstablishDate", newline: true, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerProduct.fldEstablishDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }},
            {display: "起息日期", name: "fldValueDate", newline: false, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerProduct.fldValueDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }},
            {display: "产品类型", name: "fldType", newline: true, type: "select",
            	options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:productTypeData,
                    initValue: '${customerProduct.fldType}',
                    valueFieldID:"fldType"
            }}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/product/update"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel);
    
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
    var where = '{"op":"and","rules":[{"op":"like","field":"fldProductId","value":"${customerProduct.fldId}","type":"string"},{"op":"equal","field":"fldStatus","value":"0","type":"int"}]}';
    var detailGrid = $("#contactgrid").ligerGrid({
    	checkbox:false,
    	columnWidth: 130,
    	columns:[
    		{display: "产品明细编号", name: "fldId",render:function(item){
    			return item.fldId;
    		}},
    		{display:"业绩系数", name:"dtlPerformanceRadio",render:function(item){
    			return item.fldPerformanceRadio;
    		}},
    		{display:"天数单位", name:"dtlDayUnit",render:function(item){
    			return renderLabel(dayUnitData,item.fldDayUnit);
    		}},
            {display:"实际天数", name:"dtlClearDays",render:function(item){
    			return item.fldClearDays;
    		}},
            {display:"到期日期", name:"dtlDueDate",render:function(item){
    			return item.fldDueDate;
    		}},
            {display:"最低认购金额", name:"dtlMinPurchaseMoney",render:function(item){
    			return item.fldMinPurchaseMoney;
    		}},
            {display:"最高认购金额", name:"dtlMaxPurchaseMoney",render:function(item){
    			return item.fldMaxPurchaseMoney;
    		}},
            {display:"年化收益率", name:"dtlAnnualizedRate",render:function(item){
    			return item.fldAnnualizedRate;
    		}},
            {display:"年化7天存款率", name:"dtlDepositRate",render:function(item){
    			return item.fldDepositRate;
    		}},
    		{display:"佣金系数", name:"dtlCommissionRadio",render:function(item){
    			return item.fldCommissionRadio;
    		}}
    	], dataAction: 'server', pageSize: 20,url: '/customer/product/listDetail?where='+where, sortName: 'operateDate', sortOrder: 'desc',
	    width:'99%',height: '98%', toJSON: JSON2.stringify
    });
    
    updateGridHeight();
</script>
</body>
</html>
