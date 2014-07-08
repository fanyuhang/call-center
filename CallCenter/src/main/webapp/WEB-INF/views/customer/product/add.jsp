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
            {display: "产品编号",name: "fldId", newline: true, type: "text", validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "产品全称", name: "fldFullName", newline: false, type: "text", validate: {required: true}},
            {display: "产品简称", name: "fldShortName", newline: true, type: "date", attr:{readonly: "readonly"}},
            {display: "成立日期", name: "fldEstablishDate", newline: false, type: "date", attr:{readonly: "readonly"}},
            {display: "起息日期", name: "fldValueDate", newline: true, type: "text"},
            {display: "产品描述", name: "fldDescription", newline: false, type: "text", validate: { maxlength: 64}}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/product/save"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_check);
    
    function f_check() {
    	var fldName = $("#fldName").val();
        if (fldName != '') {
            LG.ajax({
                url: '<c:url value="/customer/customer/isExist"/>',
                data: {fldName:fldName},
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
    	
    	LG.submitForm(mainform, function (data) {
	        var win = parent || window;
	        if (data.IsError == false) {
	            win.LG.showSuccess('保存成功', function () {
	                win.LG.closeAndReloadParent(null, "${menuNo}");
	            });
	        } else {
	            win.LG.showError('错误:' + data.Message);
	        }
    	});
    }
    
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
    
    var currentEditRow = null;
    var currentEditRowDom = null;
    var detailWin = null;
    //var detailMainform;
    function addNewRow() {
        var detailMainform = $("#detailMainform");
        detailMainform.ligerForm({
            labelWidth:100,
            inputWidth:180,
            fields:[
                {display:"产品明细编号", name:"dtlId", newline:true, type:"text", validate:{required:true,maxlength:40}},
                {display:"业绩系数", name:"dtlPerformanceRadio", newline:false, type:"text", validate:{required:true}},
                {display:"实际天数", name:"dtlClearDays", newline:true, type:"text", validate:{required:true}},
                {display:"到期日期", name:"dtlDueDate", newline:false, type:"text"},
                {display:"最低认购金额", name:"dtlMinPurchaseMoney", newline:true, type:"text", validate:{required:true}},
                {display:"最高认购金额", name:"dtlMaxPurchaseMoney", newline:false, type:"text", validate:{required:true}},
                {display:"年化收益率", name:"dtlAnnualizedRate", newline:true, type:"text", validate:{required:true}},
                {display:"年化7天存款率", name:"dtlDepositRate", newline:false, type:"text", validate:{required:true}}
            ]
        });
        
        detailMainform.attr("action", '<c:url value="/customer/product/save"/>');
        
	    //创建表单结构
        detailWin = $.ligerDialog.open({
        	title:"产品明细",
            target:$("#detail"),
            width:700, height:400, top:30,
            buttons:[
                { text:'确定', onclick:function () {
                	LG.validate(detailMainform);
                	//saveProduct();
                  } 
                },
                { text:'取消', onclick:function () {
                    detailWin.hide();
                  } 
                }
            ]
        });
	}
	
	function saveProduct() {
		
    	
        /*var data = detailGrid.getData();
        var fldId = $("#fldId").val();
        for (var i = 0; i < data.length; i++) {
            if (fldId == data[i].fldId) {
                LG.showError("产品明细编号已存在");
                return;
            }
        }
        showLoading();
        saveProductDetail(data);*/
	}
	
	function showLoading(){
	    $("#loadingBlock").show();
	}
	
	function saveProductDetail(data) {
		//alert('save');
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
                currentEditRow = grid.getSelectedRow();
                currentEditRowDom = grid.getSelectedRowObj();
                if (currentEditRow != null) {
                    editRow();
                }
            }
        },
        {
            id:'modify', text:'删除',
            img:'<c:url value="/static/ligerUI/icons/miniicons/page_delete.gif"/>',
            click:function (item) {
                var editingrow = grid.getSelectedRow();
                if (editingrow != null) {
                    $.ligerDialog.confirm('确定删除吗?', function (confirm) {
                        if (confirm)
                            f_delete();
                    });
                } else {
                    LG.tip('请选择行');
                }
            }
        }
    	]
	};
    
    var detailGrid = $("#contactgrid").ligerGrid({
    	checkbox:false,
    	columns:[
    		{display: "产品明细编号", name: "fldId"}
    	], dataAction: 'server', pageSize: 20, toolbar:toolbarOptions, url: '<c:url value="/customer/product/list"/>', sortName: 'operateDate', sortOrder: 'desc',
	    height: '98%', toJSON: JSON2.stringify
    });
    
    updateGridHeight();
</script>
</body>
</html>
