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
<div id="detail" style="display:none;"><form:form id="mainform" method="post"></form:form></div>
<script type="text/javascript">
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "产品全称", name: "fldFullName", newline: true, type: "text", cssClass: "field"},
	        {display: "产品简称", name: "fldShortName", newline: false, type: "text", cssClass: "field"},
	        {display: "产品状态", name: "fldStatus", newline: false, type: "text", cssClass: "field"},
	        {display: "实际天数", name: "fldEstablishDate", newline: true, type: "text", cssClass: "field"}
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
	        {display: "商户姓名", name: "customer.fldName"},
	        {display: "身份证号", name: "customer.fldIdentityNo"},
	        {display: "合同编号", name: "fldId"},
	        {display: "产品编号", name: "fldId"},
	        {display: "产品全称", name: "fldId"},
	        {display: "产品实际天数", name: "fldId"},
	        {display: "所属理财经理", name: "fldFinancialUserNo"},
	        {display: "银行卡号", name: "fldBankNo"},
	        {display: "瑞得卡号", name: "fldCardNo"},
	        {display: "瑞得卡等级", name: "fldCardLevel"},
	        {display: "合同状态", name: "fldStatus"},
	        {display: "签订日期", name: "fldSignDate"},
	        {display: "是否已到期", name: "fldFinishStatus"},
	        {display: "打款日期", name: "fldMoneyDate"},
	        {display: "购买金额", name: "fldPurchaseMoney"},
	        {display: "预期收益", name: "fldAnnualizedMoney"},
	        {display: "业绩系数", name: "fldPerformanceRadio"},
	        {display: "业绩额度", name: "fldPerformanceMoney"},
	        {display: "操作人", name: "fldOperateUserNo"},
	        {display: "操作时间", name: "fldOperateDate"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/customer/product/list"/>', sortName: 'operateDate', sortOrder: 'desc',
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
	            top.f_addTab(null, '新增合同信息', '<c:url value="/customer/contract/add"/>' + '?menuNo=${menuNo}');
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
	        case "upload":
	            f_upload();
	            break;
	        case "template":
	            f_template(merchantTemplate);
	            break;
	        case "export":
	            f_export('<c:url value="/merchant/merchant/export"/>');
	            break;
	        case "settle":
	            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
	                LG.tip('请选择一行数据!');
	                return;
	            }
	            var selected = grid.getSelected();
	            top.f_addTab(null, '设置商户结算信息', '<c:url value="/merchant/merchant/settle"/>' + '?menuNo=01030108&code=' + selected.code);
	            break;
	    }
	}


	$("#uploader").plupload({
	    runtimes: 'flash',
	    url: '<c:url value="/merchant/common/upload/0"/>',
	    max_file_size: '100mb',
	    max_file_count: 1,
	    chunk_size: '1mb',
	    rename: true,
	    multiple_queues: false,
	    resize: {width: 600, height: 500, quality: 30},
	    rename: true,
	    sortable: true,
	    filters: [
	        {title: "Excel files", extensions: "xls"}
	    ],
	    flash_swf_url: '<c:url value="/static/plupload/plupload.flash.swf" />'
	});

	var uploader = $('#uploader').plupload('getUploader');

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

	var detailWin = null;
	function f_upload() {
	    if (detailWin) {
	        detailWin.show();
	    } else {
	        detailWin = $.ligerDialog.open({
	            title: '商户导入',
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