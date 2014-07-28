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
   	 	bottomHeight:$(window).height() * 0.65,
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
            {display: "产品编号",name: "fldId", newline: true, type: "hidden", attr:{value:"${customerProduct.fldId}",readonly: "readonly"},validate: {required: true}},
            {display: "产品全称", name: "fldFullName", newline: true, type: "text", attr:{value:"${customerProduct.fldFullName}"},validate: {required: true},group: "<label style=white-space:nowrap;>产品基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "产品简称", name: "fldShortName", newline: false, type: "text",attr:{value:"${customerProduct.fldShortName}"}},
            {display: "产品描述", name: "fldDescription", newline: true, type: "text", attr:{value:"${customerProduct.fldDescription}"},validate: { maxlength: 64}},
            {display: "产品类型", name: "fldType", newline: false, type: "select",validate: {required: true},
            	options:{
                    valueField: 'value',
                    textField: 'text',
                    isMultiSelect:false,
                    data:productTypeData,
                    initValue: '${customerProduct.fldType}',
                    valueFieldID:"fldType"
            }},
            {display: "成立日期", name: "fldEstablishDate", newline: true, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerProduct.fldEstablishDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }},
            {display: "起息日期", name: "fldValueDate", newline: false, type: "date", validate: {required: true}, attr:{value:"<fmt:formatDate value='${customerProduct.fldValueDate}' pattern='yyyy-MM-dd'/>",readonly: "readonly"},format:'yyyy-MM-dd',editor:{ type:'date' }}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/product/update"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);
    
    function f_save() {
    	LG.validate(mainform);
    	
    	var win = parent || window;
    	
    	var detailData = detailGrid.getData();
    	if(detailData.length < 1) {
    		$.ligerDialog.error('请添加产品明细');
    		return;
    	}
    	
    	LG.submitForm(mainform, function (data) {
	        if (data.IsError == false) {
	            win.LG.showSuccess(data.Message, function () {
	                win.LG.closeAndReloadParent(null, "${menuNo}");
	            });
	        }
	        else {
	            win.LG.showError('错误:' + data.Message);
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
    function showDetail(type) {
    	var editData = null;
    	try
    	{
    		editData = currentEditRow[0];
    	} catch(e){
    		editData = null;
    	}
    	
    	if(detailWin) {
    		detailWin.show();
    	} else {
    	
        var detailMainform = $("#detailMainform");
        detailMainform.ligerForm({
            labelWidth:130,
            inputWidth:180,
            fields:[
                {display:"产品明细编号", name:"dtlId", newline:true, type:"hidden"},
                {display:"天数单位", name:"dtlDayUnit", newline:true, type:"select", comboboxName:"dayUnit", validate:{required:true},group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
	                	options:{
                    	valueField: 'value',
                    	textField: 'text',
                    	isMultiSelect:false,
                    	data:dayUnitData,
                    	initValue: editData!=null ? editData.fldDayUnit : '',
                    	valueFieldID:"dtlDayUnit"
                	}},
                {display:"实际天数", name:"dtlClearDays", newline:false, type:"text", validate:{required:true}},
                {display:"到期日期", name:"dtlDueDate", newline:true, type: "text", attr:{readonly: "readonly"},format:'yyyy-MM-dd'},
                {display:"最低认购金额(万元)", name:"dtlMinPurchaseMoney", newline:false, type:"text", validate:{required:true}},
                {display:"最高认购金额(万元)", name:"dtlMaxPurchaseMoney", newline:true, type:"text", validate:{required:true}},
                {display:"年化收益率(%)", name:"dtlAnnualizedRate", newline:true, type:"text", validate:{required:true}, group: "<label style=white-space:nowrap;>收益信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
                {display:"年化7天存款率(%)", name:"dtlDepositRate", newline:false, type:"text", validate:{required:true}},
                {display:"业绩系数", name:"dtlPerformanceRadio", newline:true, type:"text", validate:{required:true}},
                {display:"佣金系数", name:"dtlCommissionRadio", newline:false, type:"text", validate:{required:true}}
            ]
        });
        
        $("#dayUnit").change(function(){
	       	//到期日期=成立日期+实际天数
	       	var dayUnit = $("#dtlDayUnit").val();
	       	if(dayUnit == "") {
	       		$("#dtlClearDays").val("");
	        	$("#dtlDueDate").val("");
	       		return;
	       	}
	       	var clearDays = $("#dtlClearDays").val();
	       	if(clearDays == "") {
	       		return;
	       	}
	       	
	       	if(dayUnit == '0') {
	       		var dueDate = new Date(Date.parse($("#fldEstablishDate").val()));
	       		dueDate.setDate(dueDate.getDate()+parseInt($("#dtlClearDays").val()));
	       		$("#dtlDueDate").val(dueDate.getFullYear()+"-"+parseInt(parseInt(dueDate.getMonth())+1)+"-"+dueDate.getDate());
	       	} else {
	       		var dueDate = new Date(Date.parse($("#fldEstablishDate").val()));
	       		dueDate.setMonth(dueDate.getMonth()+parseInt($("#dtlClearDays").val()));
	       		$("#dtlDueDate").val(dueDate.getFullYear()+"-"+parseInt(parseInt(dueDate.getMonth())+1)+"-"+dueDate.getDate());
	       	}
	    });
        
        $.ligerui.get("dtlClearDays").bind("blur",function(){
        	//到期日期=成立日期+实际天数
        	var dayUnit = $("#dayUnit").val();
        	if(dayUnit == "") {
	        	$("#dtlClearDays").val("");
	        	LG.showError("请先选择天数单位");
	        	return;
	        } else {
	        		if(dayUnit == "天") {
	        		 $("#dtlDayUnit").val(0);
	        		 dayUnit = 0;
	        		} else {
	        		 $("#dtlDayUnit").val(1);
	        		 dayUnit = 1;
	        		}
	        	}
        	if($("#dtlClearDays").val()=="") {
        		$("#dtlDueDate").val("");
        		return;
        	}
        	
	        	
        	if(dayUnit == 0) {
        		var dueDate = new Date(Date.parse($("#fldEstablishDate").val()));
        		dueDate.setDate(dueDate.getDate()+parseInt($("#dtlClearDays").val()));
        		$("#dtlDueDate").val(dueDate.getFullYear()+"-"+parseInt(parseInt(dueDate.getMonth())+1)+"-"+dueDate.getDate());
        	} else {
        		var dueDate = new Date(Date.parse($("#fldEstablishDate").val()));
        		dueDate.setMonth(dueDate.getMonth()+parseInt($("#dtlClearDays").val()));
        		$("#dtlDueDate").val(dueDate.getFullYear()+"-"+parseInt(parseInt(dueDate.getMonth())+1)+"-"+dueDate.getDate());
        	}
        });
        
      	//创建表单结构
        detailWin = $.ligerDialog.open({
        	title:"产品明细",
            target:$("#detail"),
            width:750, height:400, top:30,
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
        	$("#dtlId").val(editData.fldId);
        	$("#dtlPerformanceRadio").val(editData.fldPerformanceRadio);
        	//$("#dayUnit").val(editData.fldDayUnit);
        	$("#dtlClearDays").val(editData.fldClearDays);
        	$("#dtlMinPurchaseMoney").val(editData.fldMinPurchaseMoney);
        	$("#dtlMaxPurchaseMoney").val(editData.fldMaxPurchaseMoney);
        	$("#dtlAnnualizedRate").val(editData.fldAnnualizedRate);
        	$("#dtlDepositRate").val(editData.fldDepositRate);
        	$("#dtlCommissionRadio").val(editData.fldCommissionRadio);
        	
        	var dayUnit = $("#dtlDayUnit").val();
        	if(dayUnit == 0) {
        		var dueDate = new Date(Date.parse($("#fldEstablishDate").val()));
        		dueDate.setDate(dueDate.getDate()+parseInt($("#dtlClearDays").val()));
        		$("#dtlDueDate").val(dueDate.getFullYear()+"-"+parseInt(parseInt(dueDate.getMonth())+1)+"-"+dueDate.getDate());
        	} else {
        		var dueDate = new Date(Date.parse($("#fldEstablishDate").val()));
        		dueDate.setMonth(dueDate.getMonth()+parseInt($("#dtlClearDays").val()));
        		$("#dtlDueDate").val(dueDate.getFullYear()+"-"+parseInt(parseInt(dueDate.getMonth())+1)+"-"+dueDate.getDate());
        	}
        	
        	$("#dtlId", detailMainform).attr("readonly", "readonly");
        } else {
        	$("#dtlId").val("");
        	$("#dtlPerformanceRadio").val("");
        	$("#dtlDayUnit").val(0);
        	$("#dayUnit").val('天');
        	$("#dtlClearDays").val("");
        	$("#dtlMinPurchaseMoney").val("");
        	$("#dtlMaxPurchaseMoney").val("");
        	$("#dtlAnnualizedRate").val("");
        	$("#dtlDepositRate").val("");
        	$("#dtlCommissionRadio").val("");
        	$("#dtlDueDate").val("");
        }
        
	}
	
	function saveProductDetail(type) {
		showLoading();
	    var data = detailGrid.getData();
	    var dtlId = $("#dtlId").val();
	    if(dtlId != "") {
	    	type = "edit";
	    } else {
	    	typed = "add";
	    }
	    
	    //实际天数+天数单位+年化收益率唯一确定一个产品明细
	    var dtlDayUnit = $("#dtlDayUnit").val();
		var dtlClearDays = $("#dtlClearDays").val();
		var dtlAnnualizedRate = $("#dtlAnnualizedRate").val();
		//if(type == 'add') {
	        LG.ajax({
                url: '<c:url value="/customer/product/isDetailExist"/>',
                data: {dayUnit:dtlDayUnit,clearDays:dtlClearDays,annualizedRate:dtlAnnualizedRate},
                beforeSend: function () {
                	
                },
                complete: function () {
                },
                success: function () {
			        var detailData = {};
			        detailData.fldProductId = $("#fldId").val();
			        detailData.fldId = dtlId;
			        detailData.fldPerformanceRadio = $("#dtlPerformanceRadio").val();
			        detailData.fldDayUnit = $("#dtlDayUnit").val();
			        detailData.fldClearDays = $("#dtlClearDays").val();
			        detailData.fldDueDate = $("#dtlDueDate").val();
			        detailData.fldMinPurchaseMoney = $("#dtlMinPurchaseMoney").val();
			        detailData.fldMaxPurchaseMoney = $("#dtlMaxPurchaseMoney").val();
			        detailData.fldAnnualizedRate = $("#dtlAnnualizedRate").val();
			        detailData.fldDepositRate = $("#dtlDepositRate").val();
			        detailData.fldCommissionRadio = $("#dtlCommissionRadio").val();
			        
			        if(type == 'add') {
			        	LG.ajax({
				            url:'<c:url value="/customer/product/saveProductDetail"/>',
				            data:{productDetail:JSON.stringify(detailData)},
				            dataType:'json', type:'post',
				            success:function (data) {
				               LG.showSuccess('保存成功');
				               detailGrid.loadData();
				            },
				            error:function (message) {
				                LG.showError(message);
				            }
				        });
			        } else {
			        	LG.ajax({
				            url:'<c:url value="/customer/product/updateProductDetail"/>',
				            data:{productDetail:JSON.stringify(detailData)},
				            dataType:'json', type:'post',
				            success:function (data) {
				               LG.showSuccess('保存成功');
				               detailGrid.loadData();
				            },
				            error:function (message) {
				                LG.showError(message);
				            }
				        });
			        }
			        
			        detailWin.hide();
			        
			        hideLoading();
                },
                error: function (message) {
		            LG.showError(message);
		            return;
                }
            });
        //}
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
		LG.ajax({
            url:'<c:url value="/customer/product/deleteProductDetail"/>',
            data:{dtlId:row.fldId},
            dataType:'json', type:'post',
            success:function (data) {
               LG.showSuccess('删除成功');
    		   detailGrid.loadData();
            },
            error:function (message) {
                LG.showError(message);
            }
        });
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
    
    var where = '{"op":"and","rules":[{"op":"equal","field":"fldStatus","value":"0","type":"int"},{"op":"equal","field":"fldProductId","value":"${customerProduct.fldId}","type":"string"}]}';
    var detailGrid = $("#contactgrid").ligerGrid({
    	checkbox:true,
    	columnWidth: 130,
    	columns:[
    		{display: "产品明细编号", name: "fldId",hide:1,width:1,render:function(item){
    			return item.fldId;
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
            {display:"最低认购金额(万元)", name:"dtlMinPurchaseMoney",render:function(item){
    			return item.fldMinPurchaseMoney;
    		}},
            {display:"最高认购金额(万元)", name:"dtlMaxPurchaseMoney",render:function(item){
    			return item.fldMaxPurchaseMoney;
    		}},
            {display:"年化收益率(%)", name:"dtlAnnualizedRate",render:function(item){
    			return item.fldAnnualizedRate;
    		}},
            {display:"年化7天存款率(%)", name:"dtlDepositRate",render:function(item){
    			return item.fldDepositRate;
    		}},
    		{display:"业绩系数", name:"dtlPerformanceRadio",render:function(item){
    			return item.fldPerformanceRadio;
    		}},
    		{display:"佣金系数", name:"dtlCommissionRadio",render:function(item){
    			return item.fldCommissionRadio;
    		}}
    	], dataAction: 'server', pageSize: 20, toolbar:toolbarOptions, url: '/customer/product/listDetail?where='+where,
	    width:'99%',height: '98%', toJSON: JSON2.stringify
    });
    
    updateGridHeight();
</script>
</body>
</html>
