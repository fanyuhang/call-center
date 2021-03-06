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
</div>
<div id="detail" style="display:none;">
    <form:form id="detailMainform" name="detailMainform" method="post" modelAttribute="productDetail" ></form:form>
</div>
<script type="text/javascript">
	var dayUnitData =<sys:dictList type = "14"/>;
	var productTypeData =<sys:dictList type = "7"/>;
	
	function updateGridHeight() {
    	var topHeight = $("#layout > .l-layout-center").height();
    	var bottomHeight = $("#layout > .l-layout-bottom").height();
	}

	var layout = $("#layout").ligerLayout({
   	 	bottomHeight:$(window).height() * 0.60,
    	heightDiff:-10,
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
            {display: "产品编号",name: "fldId", newline: true, type: "hidden"},
            {display: "产品全称", name: "fldFullName", newline: true, type: "text", validate: {required: true},group: "<label style=white-space:nowrap;>产品基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "产品类型", name: "fldType", newline: false, type: "select",validate: {required: true},
                options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:productTypeData,
                    valueFieldID:"fldType",
                    initValue:0
                }},
            {display: "产品简称", name: "fldShortName", newline: true, type: "text"},
            {display: "产品描述", name: "fldDescription", newline: false, type: "text", validate: { maxlength: 64}}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/product/save"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_check);
    
    function f_check() {
    	var fldFullName = $("#fldFullName").val();
        if (fldFullName != '') {
            LG.ajax({
                url: '<c:url value="/customer/product/isExist"/>',
                data: {fldFullName:fldFullName},
                beforeSend: function () {
                	
                },
                complete: function () {
                },
                success: function () {
                	f_save();
                },
                error: function (message) {
		            LG.showError(message);
                }
            });
        } else {
        	f_save();
        }
    }
    
    function f_save() {
    	LG.validate(mainform);
    	if(!mainform.valid()) {
			LG.showInvalid();
			return;
		}
    	
    	var win = parent || window;
    	
    	var product = JSON.stringify(convertToRequestParam($("#mainform").formToArray()));
    	
    	var detailData = detailGrid.getData();
    	if(detailData.length < 1) {
    		$.ligerDialog.error('请添加产品明细');
    		return;
    	}
    	
    	var productDetailArr = [];
    	for(var i = 0;i < detailData.length;i++) {
    		var detail = {};
    		detail.fldId = detailData[i].dtlId;
        	detail.fldPerformanceRadio = detailData[i].dtlPerformanceRadio;
        	detail.fldDayUnit = detailData[i].dtlDayUnit;
        	detail.fldClearDays = detailData[i].dtlClearDays;
        	detail.fldMinPurchaseMoney = detailData[i].dtlMinPurchaseMoney;
        	detail.fldMaxPurchaseMoney = detailData[i].dtlMaxPurchaseMoney;
        	detail.fldAnnualizedRate = detailData[i].dtlAnnualizedRate;
        	detail.fldDepositRate = detailData[i].dtlDepositRate;
        	detail.fldCommissionRadio = detailData[i].dtlCommissionRadio;
        	
        	productDetailArr.push(detail);
    	}
    	
    	var productDetail = JSON.stringify(productDetailArr);
    	
    	LG.ajax({
            url:'<c:url value="/customer/product/save"/>',
            data:{product:product,productDetail:productDetail},
            dataType:'json', type:'post',
            success:function (data) {
               win.LG.closeAndReloadParent(null, '${menuNo}');
               LG.tip('保存成功');
            },
            error:function (message) {
                LG.showError(message);
            }
        });
    }
    
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
    
    function convertToRequestParam(form) {
    	var obj = {};
    	for (var i = 0; i < form.length; i++) {
        	obj[form[i].name] = form[i].value ? form[i].value : "";
    	}
    	return obj;
	}
    
    var currentEditRow = null;
    var currentEditRowDom = null;
    var detailWin = null;
    var detailMainform = null;
    function showDetail(type) {
    	var win = parent || window;
    	
    	if(detailWin) {
    		detailWin.show();
    		
    		$("#dtlId", detailMainform).removeAttr("readonly");
    	} else {
	        detailMainform = $("#detailMainform");
	        detailMainform.ligerForm({
	            labelWidth:130,
	            inputWidth:180,
	            fields:[
	                {display:"产品明细编号", name:"dtlId", newline:true, type:"hidden"},
                    {display:"实际天/月数", name:"dtlClearDays", newline:true, type:"text", validate:{required:true}},
                    {display:"天/月数单位", name:"dtlDayUnit", newline:false, type:"select", comboboxName:"dayUnit", validate:{required:true},
	                	options:{
                    	valueField: 'value',
                    	textField: 'text',
                    	isMultiSelect:false,
                    	data:dayUnitData,
                    	valueFieldID:"dtlDayUnit"
                	}},
	                {display:"最低认购金额(万元)", name:"dtlMinPurchaseMoney", newline:true, type:"text", validate:{required:true}},
	                {display:"最高认购金额(万元)", name:"dtlMaxPurchaseMoney", newline:false, type:"text", validate:{required:true}},
	                {display:"年化收益率(%)", name:"dtlAnnualizedRate", newline:true, type:"text", validate:{required:true}, group: "<label style=white-space:nowrap;>收益信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
	                {display:"年化7天存款率(%)", name:"dtlDepositRate", newline:false, type:"text", validate:{required:true}, group: "<label style=white-space:nowrap;>募集期信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
	                {display:"业绩系数", name:"dtlPerformanceRadio", newline:true, type:"text", validate:{required:true}, group: "<label style=white-space:nowrap;>其他信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
	                {display:"佣金系数", name:"dtlCommissionRadio", newline:false, type:"text", validate:{required:true}}
	            ]
	        });

	      	//创建表单结构
	        detailWin = $.ligerDialog.open({
	        	title:"产品明细",
	            target:$("#detail"),
	            width:750, height:420, top:30,
	            buttons:[
	                { text:'确定', onclick:function () {
	                	LG.validate(detailMainform);
	                	if(!detailMainform.valid()) {
	    					LG.showInvalid();
	    					return;
	    				}
	                    
	                    saveProductDetail(type);
	                  } 
	                },
	                { text:'取消', onclick:function () {
	                    detailWin.hide();
	                  } 
	                }
	            ]
	        });
    	}
        
        if(type == 'edit') {
        	var editData = currentEditRow[0];
        	$("#dtlId").val(editData.dtlId);
        	$("#dtlPerformanceRadio").val(editData.dtlPerformanceRadio);
        	$("#dtlDayUnit").val(editData.dtlDayUnit);
        	$("#dayUnit").val(renderLabel(dayUnitData,editData.dtlDayUnit));
        	$("#dtlClearDays").val(editData.dtlClearDays);
        	$("#dtlMinPurchaseMoney").val(editData.dtlMinPurchaseMoney);
        	$("#dtlMaxPurchaseMoney").val(editData.dtlMaxPurchaseMoney);
        	$("#dtlAnnualizedRate").val(editData.dtlAnnualizedRate);
        	$("#dtlDepositRate").val(editData.dtlDepositRate);
        	$("#dtlCommissionRadio").val(editData.dtlCommissionRadio);

        	$("#dtlId", detailMainform).attr("readonly", "readonly");
        } else {
        	$("#dtlId").val((new Date()).getTime());
        	$("#dtlPerformanceRadio").val("");
        	$("#dtlDayUnit").val(0);
        	$("#dayUnit").val('天');
        	$("#dtlClearDays").val("");
        	$("#dtlMinPurchaseMoney").val("");
        	$("#dtlMaxPurchaseMoney").val("");
        	$("#dtlAnnualizedRate").val("");
        	$("#dtlDepositRate").val("");
        	$("#dtlCommissionRadio").val("");
        }
	}
	
	function saveProductDetail(type) {
	    var data = detailGrid.getData();
	    var dtlId = $("#dtlId").val();
		//if($("#dtlId", detailMainform).attr("readonly") != "readonly") {
			//实际天数+天数单位+年化收益率唯一确定一个产品明细
			var dtlDayUnit = $("#dtlDayUnit").val();
			var dtlClearDays = $("#dtlClearDays").val();
			var dtlAnnualizedRate = $("#dtlAnnualizedRate").val();
	        for (var i = 0; i < data.length; i++) {
	            if (dtlId != data[i].dtlId && dtlDayUnit == data[i].dtlDayUnit && dtlClearDays == data[i].dtlClearDays && dtlAnnualizedRate == data[i].dtlAnnualizedRate) {
	                LG.showError("产品明细已存在");
	                return;
	            }
	        }
        //}
        showLoading();
        
        var detailData = {};
        detailData.dtlId = dtlId;
        detailData.dtlPerformanceRadio = $("#dtlPerformanceRadio").val();
        detailData.dtlDayUnitText = renderLabel(dayUnitData,$("#dtlDayUnit").val());
        detailData.dtlDayUnit = $("#dtlDayUnit").val();
        detailData.dtlClearDays = $("#dtlClearDays").val();
        detailData.dtlMinPurchaseMoney = $("#dtlMinPurchaseMoney").val();
        detailData.dtlMaxPurchaseMoney = $("#dtlMaxPurchaseMoney").val();
        detailData.dtlAnnualizedRate = $("#dtlAnnualizedRate").val();
        detailData.dtlDepositRate = $("#dtlDepositRate").val();
        detailData.dtlCommissionRadio = $("#dtlCommissionRadio").val();
        
        if($("#dtlId", detailMainform).attr("readonly") != "readonly") {
        	detailGrid.addRow(detailData);
        } else {
        	detailGrid.updateRow(currentEditRowDom, detailData);
        }
        
        detailWin.hide();
        
        hideLoading();
	}
	
	function showLoading(){
	    $("#loadingBlock").show();
	}
	
	function hideLoading(){
    	$("#loadingBlock").hide();
	}
	
	function f_delete() {
    	var row = detailGrid.getSelectedRow();
    	detailGrid.deleteRow(row.__index);
	}
	
	function addNewRow() {
		LG.validate(mainform);
    	if(!mainform.valid()) {
			LG.showInvalid();
			return;
		}
	
		showDetail('add');
	}
	
	function editRow() {
    	showDetail('edit');
	}
    
    var toolbarOptions = {
    	items:[
        {
            id:'add', text:'新增',
            img:'<c:url value="/static/ligerUI/icons/silkicons/add.png"/>',
            click:function (item) {
                currentEditRow = null;
                currentEditRowDom = null;
                addNewRow();
            }
        },
        {
            id:'modify', text:'修改',
            img:'<c:url value="/static/ligerUI/icons/miniicons/page_edit.gif"/>',
            click:function (item) {
                currentEditRow = detailGrid.getSelectedRows();
                currentEditRowDom = detailGrid.getSelectedRowObj();
                if (currentEditRow.length > 1 || currentEditRow.length == 0) {
                	LG.tip('请选择一行数据');
                } else {
                    editRow();
                }
            }
        },
        {
            id:'modify', text:'删除',
            img:'<c:url value="/static/ligerUI/icons/miniicons/page_delete.gif"/>',
            click:function (item) {
                var editingrow = detailGrid.getSelectedRows();
                if (editingrow.length > 1 || editingrow.length == 0) {
                    LG.tip('请选择一行数据');
                } else {
                    $.ligerDialog.confirm('确定删除吗?', function (confirm) {
                        if (confirm)
                            f_delete();
                    });
                }
            }
        }
    	]
	};
    var detailGrid = $("#contactgrid").ligerGrid({
    	checkbox:true,
    	columnWidth: 130,
    	columns:[
    		{display: "产品明细编号", name: "dtlId",hide:1,width:1},
            {display:"实际天数", name:"dtlClearDays"},
            {display:"天数单位", name:"dtlDayUnitText"},
    		{display:"天数单位VALUE", name: "dtlDayUnit",hide:1,width:1},
            {display:"最低认购金额(万元)", name:"dtlMinPurchaseMoney"},
            {display:"最高认购金额(万元)", name:"dtlMaxPurchaseMoney"},
            {display:"年化收益率(%)", name:"dtlAnnualizedRate"},
            {display:"年化7天存款率(%)", name:"dtlDepositRate"},
    		{display:"业绩系数", name:"dtlPerformanceRadio"},
            {display:"佣金系数", name:"dtlCommissionRadio"}
    	], dataAction: 'server', pageSize: 50, toolbar:toolbarOptions, url: '',
	    width:'99%',height: '98%', toJSON: JSON2.stringify
    });
    
    updateGridHeight();
</script>
</body>
</html>
