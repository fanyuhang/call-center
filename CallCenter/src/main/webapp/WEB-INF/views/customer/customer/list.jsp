<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
    .ui-autocomplete-loading {
        background: white url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>') right center no-repeat;
    }
</style>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
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
    <div id="uploader">
        <p>Your browser doesn't have Flash, Silverlight, Gears, BrowserPlus or HTML5 support.</p>
    </div>
</div>
<div id="initupload" style="display:none;">
    <div id="inituploader">
        <p>Your browser doesn't have Flash, Silverlight, Gears, BrowserPlus or HTML5 support.</p>
    </div>
</div>
<script type="text/javascript">
	var statusData =<sys:dictList type = "6"/>;
	var cardLevelData =<sys:dictList type = "13"/>;
	
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "商户姓名", name: "fldName", newline: true, type: "text", cssClass: "field"},
	        {display: "身份证号", name: "fldIdentityNo", newline: false, type: "text", cssClass: "field"},
	        {display: "手机号", name: "fldMobile", newline: false, type: "text", cssClass: "field"},
	        {display: "固定电话", name: "fldPhone", newline: true, type: "text", cssClass: "field"},
	        {display: "所属理财经理", name: "fldFinancialUserNo", newline: false, type: "select", 
	        	comboboxName: "financialUserNo", options: {valueFieldID: "financialUserNo"}},
	        {display: "瑞得卡号", name: "fldCardNo", newline: false, type: "text", cssClass: "field"},
	        {display: "瑞得卡等级", name: "fldCardLevel", newline: true, type: "select", cssClass: "field", 
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
	                textField: "text",
	                data: statusData
	            }, attr: {"op": "equal", "vt": "int"}
        	}
	    ],
	    toJSON: JSON2.stringify
	});
	
	$.ligerui.get("financialUserNo").openSelect({
	    grid:{
	    	columnWidth: 255,
	        columns:[
	            {display:"用户名称", name:"userName"},
	            {display:"登录名称", name:"loginName"},
	            {display:"部门", name:"deptName"},
	        ], pageSize:20,heightDiff:-10,
	        url:'<c:url value="/security/user/list"/>', sortName:'userName', checkbox:false
	    },
	    search:{
	        fields:[
	            {display:"用户名称", name:"userName", newline:true, type:"text", cssClass:"field"}
	        ]
	    },
	    valueField:'loginName', textField:'userName', top:30
	});

	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: true,
	    rownumbers: true,
	    delayLoad: true,
	    columnWidth: 180,
	    columns: [
	    	{display: "ID", name: "fldId", hide:1,width:1},
	        {display: "商户姓名", name: "fldName"},
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
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/customer/customer/list"/>', sortName: 'operateDate', sortOrder: 'desc',
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
	        case "export":
	        	f_export('<c:url value="/customer/customer/export"/>');
	        	break;
	       	case "initimport":
	       		f_initupload();
	            break;
	    }
	}

	$("#uploader").plupload({
	    runtimes: 'flash',
	    url: '<c:url value="/customer/common/upload/0"/>',
	    //max_file_size: '5mb',
	    max_file_count: 1,
	    //chunk_size: '1mb',
	    rename: true,
	    multiple_queues: false,
	    resize: {width: 600, height: 500, quality: 30},
	    sortable: true,
	    filters: [
	        {title: "Excel files", extensions: "xls"}
	    ],
	    flash_swf_url: '<c:url value="/static/plupload/plupload.flash.swf" />'
	});
	
	$("#inituploader").plupload({
	    runtimes: 'flash',
	    url: '<c:url value="/customer/common/upload/1"/>',
	    //max_file_size: '5mb',
	    max_file_count: 1,
	    //chunk_size: '1mb',
	    rename: true,
	    multiple_queues: false,
	    resize: {width: 600, height: 500, quality: 30},
	    sortable: true,
	    filters: [
	        {title: "Excel files", extensions: "xls"}
	    ],
	    flash_swf_url: '<c:url value="/static/plupload/plupload.flash.swf" />'
	});

	var uploader = $('#uploader').plupload('getUploader');
	var inituploader = $('#inituploader').plupload('getUploader');

	uploader.bind('ChunkUploaded', function (uploader, file, response) {
	    if (response.response) {
	        var rep = JSON.parse(response.response);
	        if (rep.IsError == false) {
	            uploader.removeFile(file);
	            uploader.stop();
	            detailWin.hide();
	            LG.showSuccess(rep.Message);
	            f_reload();
	        } else {
	            LG.showError(rep.Message);
	        }
	    }
	});
	
	inituploader.bind('ChunkUploaded', function (inituploader, file, response) {
	    if (response.response) {
	        var rep = JSON.parse(response.response);
	        if (rep.IsError == false) {
	            inituploader.removeFile(file);
	            inituploader.stop();
	            initdetailWin.hide();
	            LG.showSuccess(rep.Message);
	            f_reload();
	        } else {
	            LG.showError(rep.Message);
	        }
	    }
	});
	
	var detailWin = null;
	function f_upload() {
	    if (detailWin) {
	        detailWin.show();
	    } else {
	        detailWin = $.ligerDialog.open({
	            title: '客户导入',
	            target: $("#upload"),
	            width: 600, height: 420, top: 90,
	            buttons: [
	                { text: '取消', onclick: function () {
	                    detailWin.hide();
	                } }
	            ]
	        });
	    }
	}

	var initdetailWin = null;
	function f_initupload() {
	    if (initdetailWin) {
	        initdetailWin.show();
	    } else {
	        initdetailWin = $.ligerDialog.open({
	            title: '客户初始导入',
	            target: $("#initupload"),
	            width: 600, height: 420, top: 90,
	            buttons: [
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