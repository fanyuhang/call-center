<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
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
        left: 650px;
        top: 300px;
        height: 48px;
        width: 48px;
        z-index: 10001;
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
<div id="upload" style="display:none;">
	<form id="uploadForm" method="post"></form>
</div>
<div id="initupload" style="display:none;">
    <form id="initdetailForm" method="post">
    </form>
</div>
<script type="text/javascript">
	var statusData =<sys:dictList type = "6" nullable="true"/>;
	var cardLevelData =<sys:dictList type = "13" nullable="true"/>;
	
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "客户姓名", name: "fldName", newline: true, type: "text", cssClass: "field"},
	        {display: "手机号", name: "fldMobile", newline: false, type: "text", cssClass: "field"},
	        {display: "固定电话", name: "fldPhone", newline: false, type: "text", cssClass: "field"},
	        {display: "理财经理", name: "financialUser.userName", newline: true, type: "text", cssClass: "field"},
            {display: "客服经理", name: "serviceUser.userName", newline: false, type: "text", cssClass: "field"},
            {display: "客户经理", name: "customerUser.userName", newline: false, type: "text", cssClass: "field"},
            {display: "瑞得卡号", name: "fldCardNo", newline: true, type: "text", cssClass: "field"},
	        {display: "瑞得卡等级", name: "fldCardLevel", newline: false, type: "select", cssClass: "field",
	        	options: {
	                valueFieldID: "fldCardLevel",
	                valueField: "value",
	                textField: "text",
	                data: cardLevelData
	            }, attr: {"op": "equal", "vt": "int"}
        	},
	        {display: "客户状态", name: "fldStatus", newline: false, type: "select", cssClass: "field",
	            options: {
	                valueFieldID: "fldStatus",
	                valueField: "value",
                    initValue:'0',
	                textField: "text",
	                data: statusData
	            }, attr: {"op": "equal", "vt": "int"}
        	}
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
	        {display: "客户姓名", name: "fldName"},
	        {display: "身份证号", name: "fldIdentityNo"},
	        {display: "手机号", name: "fldMobile",
                render:function(item) {
                    return LG.hiddenPhone(item.fldMobile);
                }},
	        {display: "固定电话", name: "fldPhone",
                render:function(item) {
                    return LG.hiddenPhone(item.fldPhone);
                }},
	        {display: "客户状态", name: "fldStatus",
	        	render:function(item) {
	        		return renderLabel(statusData,item.fldStatus);
	        	}
	        },
	        {display: "理财经理", name: "financialUserName"},
            {display: "客服经理", name: "serviceUserName"},
            {display: "客户经理", name: "customerUserName"},
            {display: "瑞得卡等级", name: "fldCardLevel",
                render:function(item) {
                    return renderLabel(cardLevelData,item.fldCardLevel);
                }
            },
            {display: "瑞得卡号", name: "fldCardNo"},
            {display: "瑞得卡金额", name: "fldCardTotalMoney",
                render:function(item) {
                    return formatCurrency(item.fldCardTotalMoney);
                }},
            {display: "操作人", name: "operateUserName"},
	        {display: "操作时间", name: "fldOperateDate"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/customer/customer/list"/>',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	        case "add":
	            top.f_addTab(null, '新增客户信息', '<c:url value="/customer/customer/add"/>' + '?menuNo=${menuNo}');
	            break;
	        case "view":
	            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '查看客户信息', '<c:url value="/customer/customer/view"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId);
	            break;
	        case "modify":
	            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '修改客户信息', '<c:url value="/customer/customer/edit"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId);
	            break;
	        case "delete":
	            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            jQuery.ligerDialog.confirm('确定删除吗?', function (confirm) {
	                if (confirm)
	                    f_delete();
	            });
	            break;
	        case "addContract":
	        	if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            if(selected.fldStatus != 0) {
	            	LG.tip('客户状态无效!');
	            	return;
	            }
	            top.f_addTab(null, '新增合同', '<c:url value="/customer/customer/addContract"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId+'&custName='+selected.fldName);
	        	break;
	        case "viewContract":
	        	if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '查看合同', '<c:url value="/customer/customer/viewContract"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId+'&custName='+selected.fldName);
	        	break;
	        case "exchange":
	        	if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '客户交接', '<c:url value="/customer/customer/exchange"/>' + '?menuNo=${menuNo}&fldId=' + selected.fldId);
	        	break;
	        case "import":
	            f_upload();
	            break;
	        case "template":
	            f_template(customerTemplate);
	            break;
	        case "initimporttemplate":
	        	f_template(initCustomerTemplate);
	            break;
	        case "export":
	        	f_export('<c:url value="/customer/customer/export"/>');
	        	break;
	       	case "initimport":
	       		f_initupload();
	            break;
	    }
	}

	var detailWin = null;
	var uploadForm = null;
	function f_upload() {
	    if (detailWin) {
	        detailWin.show();
	    } else {
	    		uploadForm = $("#uploadForm");
	    		uploadForm.ligerForm({
	                labelWidth:100,
	                inputWidth:250,
	                fields:[
	                    { display:"选择文件", name:"custFile", type:"file"}
	                ],
	                toJSON:JSON2.stringify});
	    		
	        detailWin = $.ligerDialog.open({
	            title: '客户导入',
	            target: $("#upload"),
	            width: 500, height: 130, top: 30,
	            buttons: [
	                { text: '上传', onclick: function () {
                        var url = '<c:url value="/customer/common/fileUpload/0"/>';
                        LG.showLoading("正在上传...");
                        $.ajaxFileUpload({
                                    url:url,
                                    secureuri:false,
                                    fileElementId:'custFile',
                                    dataType: 'json',
                                    success: function (data, status){
                                    		detailWin.hide();
                                        LG.hideLoading();
                                        LG.showSuccess(data.Message);
                                    },
                                    error: function (data, status, e){
                                        LG.showError(data.Message);
                                    }
                                }
                        );

                    }},
                    { text: '取消', onclick: function () {
                    	detailWin.hide();
	                } }
	            ]
	        });
	    }
	}

	var initdetailWin = null;
    var initdetailForm = null;
	function f_initupload() {
	    if (initdetailWin) {
	        initdetailWin.show();
	    } else {
            initdetailForm = $("#initdetailForm");
            initdetailForm.ligerForm({
                labelWidth:100,
                inputWidth:250,
                fields:[
                    { display:"选择文件", name:"fldFile", type:"file"}
                ],
                toJSON:JSON2.stringify});

            initdetailWin = $.ligerDialog.open({
	            title: '客户初始导入',
	            target: $("#initupload"),
	            width: 500, height: 130, top: 30,
	            buttons: [
                    { text: '上传', onclick: function () {
                        var url = '<c:url value="/customer/common/fileUpload/1"/>';
                        LG.showLoading("正在上传...");
                        $.ajaxFileUpload({
                                    url:url,
                                    secureuri:false,
                                    fileElementId:'fldFile',
                                    dataType: 'json',
                                    success: function (data, status){
                                        initdetailWin.hide();
                                        LG.hideLoading();
                                        LG.showSuccess(data.Message);
                                    },
                                    error: function (data, status, e){
                                        LG.showError(data.Message);
                                    }
                                }
                        );

                    }},
                    { text: '取消', onclick: function () {
	                    initdetailWin.hide();
	                } }
	            ]
	        });
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