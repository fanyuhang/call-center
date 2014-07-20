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
            {display: "产品全称", name: "fldFullName", newline: false, type: "text", attr:{value:"${customerProduct.fldFullName}"},validate: {required: true}},
            {display: "产品简称", name: "fldShortName", newline: true, type: "text",attr:{value:"${customerProduct.fldShortName}"}},
            {display: "产品描述", name: "fldDescription", newline: false, type: "text", attr:{value:"${customerProduct.fldDescription}"},validate: { maxlength: 64}},
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
    	
        var detailMainform = $("#detailMainform");
        detailMainform.ligerForm({
            labelWidth:100,
            inputWidth:180,
            fields:[
                {display:"产品明细编号", name:"dtlId", newline:true, type:"text", validate:{required:true,maxlength:40}},
                {display:"业绩系数", name:"dtlPerformanceRadio", newline:false, type:"text", validate:{required:true}},
                {display:"天数单位", name:"dtlDayUnit", newline:true, type:"select", comboboxName:"dayUnit", validate:{required:true},
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
                {display:"最低认购金额", name:"dtlMinPurchaseMoney", newline:false, type:"text", validate:{required:true}},
                {display:"最高认购金额", name:"dtlMaxPurchaseMoney", newline:true, type:"text", validate:{required:true}},
                {display:"年化收益率", name:"dtlAnnualizedRate", newline:false, type:"text", validate:{required:true}},
                {display:"年化7天存款率", name:"dtlDepositRate", newline:true, type:"text", validate:{required:true}},
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
        	var dayUnit = $("#dtlDayUnit").val();
        	if(dayUnit == "") {
	        	$("#dtlClearDays").val("");
	        	LG.showError("请先选择天数单位");
	        	return;
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
        }
        
	    //创建表单结构
        detailWin = $.ligerDialog.open({
        	title:"产品明细",
            target:$("#detail"),
            width:700, height:400, top:30,
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
	
	function saveProductDetail(type) {
	    var data = detailGrid.getData();
	    var dtlId = $("#dtlId").val();
		if(type == 'add') {
	        LG.ajax({
                url: '<c:url value="/customer/product/isDetailExist"/>',
                data: {fldId:dtlId},
                beforeSend: function () {
                	
                },
                complete: function () {
                },
                success: function () {
			        showLoading();
			        
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
        }
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
    
    var detailGrid = $("#contactgrid").ligerGrid({
    	checkbox:true,
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
    	], dataAction: 'server', pageSize: 20, toolbar:toolbarOptions, url: '/customer/product/listDetail', sortName: 'operateDate', sortOrder: 'desc',
	    width:'99%',height: '98%', toJSON: JSON2.stringify
    });
    
    updateGridHeight();
</script>
</body>
</html>
