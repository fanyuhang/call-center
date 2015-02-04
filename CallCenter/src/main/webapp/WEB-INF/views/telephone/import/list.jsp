<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
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
    <form id="importFormSearch" method="post"></form>
</div>
<script type="text/javascript">
var telephoneTemplate = "话单导入模板";

var radioData = <sys:dictList type = "26"/>;

var importFormSearch = $("#importFormSearch");
importFormSearch.ligerForm({
    labelWidth: 70,
    inputWidth: 200,
    space: 30,
    fields: [
        {display: "话单名称", name: "importName", newline: true, validate: {required: true, maxlength: 32}, type: "text", cssClass: "field"},
        {display: "是否去重", name: "fldDuplicateStatus", newline: true, type: "radiolist", cssClass: "field", options: {
            data: radioData, textField: 'text', valueField: 'value', valueFieldID: "fldDuplicateStatus", value: '0'
        }},
        {display: "选择文件", name: "fldFile", type: "file"}
    ],
    toJSON: JSON2.stringify
});

//搜索表单应用ligerui样式
$("#formsearch").ligerForm({
    labelWidth: 100,
    inputWidth: 150,
    space: 30,
    fields: [
        {display: "话单名称", name: "fldName", newline: true, type: "text", cssClass: "field"},
        {display: "导入人", name: "createUser.userName", newline: false, type: "text", cssClass: "field"},
        {display: "导入时间", name: "startDate", newline: true, type: "date", cssClass: "field",
            attr: {op: 'greaterorequal', vt: 'date', field: "fldOperateDate"}},
        {display: "至", name: "endDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'lessorequal', vt: 'date', field: "fldOperateDate"}}
    ],
    toJSON: JSON2.stringify
});

//列表结构
var grid = $("#maingrid").ligerGrid({
    checkbox: false,
    rownumbers: true,
    delayLoad: true,
    columnWidth: 180,
    columns: [
        {display: "话单名称", name: "fldName"},
        {display: "是否去重", name: "fldDuplicateStatus", width: 100,
            render: function (item) {
                return renderLabel(radioData, item.fldDuplicateStatus);
            }},
        {display: "导入记录数", name: "fldImportTotalNumber", width: 100, render: function (item) {
            if (item.fldImportTotalNumber == 0)
                return '' + item.fldImportTotalNumber + '';
            else
                return "<span><a href='javascript:void(0);' title='查看明细' onclick=\"javascript:showImportDtl('" + item.fldId + "');\">" + item.fldImportTotalNumber + "</a>" + "</span>";
        }},
        {display: "原始记录数", name: "fldTotalNumber", width: 100,
            render: function (item) {
                if (item.fldTotalNumber == 0)
                    return '' + item.fldTotalNumber + '';
                else
                    return "<span><a href='javascript:void(0);' title='下载' onclick=\"javascript:exportData('/telephone/import/origexport?id=" + item.fldId + "');\">" + item.fldTotalNumber + "</a>" + "</span>";
            }
        },
        {display: "重复记录数", name: "fldDuplicateTotalNumber", width: 100,
            render: function (item) {
                if (item.fldDuplicateTotalNumber == 0)
                    return '' + item.fldDuplicateTotalNumber + '';
                else
                    return "<span><a href='javascript:void(0);' title='下载' onclick=\"javascript:exportData('/telephone/import/dupexport?id=" + item.fldId + "');\">" + item.fldDuplicateTotalNumber + "</a>" + "</span>";
            }
        },
        {display: "非重复记录数", name: "fldNoDuplicateTotalNumber", width: 100,
            render: function (item) {
                if ((item.fldTotalNumber - item.fldDuplicateTotalNumber) == 0)
                    return '' + (item.fldTotalNumber - item.fldDuplicateTotalNumber) + '';
                else
                    return "<span><a href='javascript:void(0);' title='下载' onclick=\"javascript:exportData('/telephone/import/nodupexport?id=" + item.fldId + "');\">" + (item.fldTotalNumber - item.fldDuplicateTotalNumber) + "</a>" + "</span>";
            }
        },
        {display: "已分配记录数", name: "fldAssignTotalNumber", width: 100},
        {display: "导入人", name: "createUserName"},
        {display: "导入时间", name: "fldCreateDate"},
        {display: "最近操作时间", name: "fldOperateDate"}
    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/import/list"/>',
    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
});

//增加搜索按钮,并创建事件
LG.appendSearchButtons("#formsearch", grid, true, true);

//加载toolbar
LG.loadToolbar(grid, toolbarBtnItemClick);

function exportData(url) {
    f_export(url);
}

function showImportDtl(id) {
    top.f_addTab(null, '查看导入明细信息', '<c:url value="/telephone/import/viewDtl"/>' + '?menuNo=${menuNo}&fldId=' + id);
}

//工具条事件
function toolbarBtnItemClick(item) {
    switch (item.id) {
        case "import":
            $("#fileName").val("");
            f_upload();
            break;
        case "template":
            f_template(telephoneTemplate);
            break;
        case "origexport":
            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                LG.tip('请选择一行数据!');
                return;
            }
            var selected = grid.getSelected();
            f_export('<c:url value="/telephone/import/origexport"/>' + '?id=' + selected.fldId);
            break;
        case "nodupexport":
            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                LG.tip('请选择一行数据!');
                return;
            }
            var selected = grid.getSelected();
            f_export('<c:url value="/telephone/import/nodupexport"/>' + '?id=' + selected.fldId);
            break;
        case "dupexport":
            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                LG.tip('请选择一行数据!');
                return;
            }
            var selected = grid.getSelected();
            f_export('<c:url value="/telephone/import/dupexport"/>' + '?id=' + selected.fldId);
            break;
    }
}

LG.validate(importFormSearch);

var detailWin = null;
function f_upload() {
    var win = parent || window;

    $("#importName").val("");
    if (detailWin) {
        detailWin.show();
    } else {
        detailWin = $.ligerDialog.open({
            title: '话单导入',
            target: $("#upload"),
            width: 400, height: 200, top: 50,
            buttons: [
                {
                    text: "上传", onclick: function () {

                    if (!importFormSearch.valid()) {
                        return;
                    }

                    var fileName = $("#fldFile").val();
                    if (fileName == "") {
                        win.LG.showError("请上传文件!");
                        return;
                    }
                    LG.showLoading("正在上传...");
                    var url = '<c:url value="/telephone/import/fileUpload"/>?importName=' + $("#importName").val() + '&fldDuplicateStatus=' + $("#fldDuplicateStatus").val();
                    $.ajaxFileUpload({
                        url: url,
                        secureuri: false,
                        fileElementId: 'fldFile',
                        dataType: 'text/html',
                        success: function (data, status) {
                            ;
                            detailWin.hide();
                            LG.hideLoading();
                            LG.showSuccess('导入话单成功!');
                        },
                        error: function (data, status, e) {
                            LG.showError("导入话单失败!");
                        }
                    });

                }
                },
                { text: '取消', onclick: function () {
                    detailWin.hide();
                }
                }
            ]
        });
    }
}

function f_template(filename) {
    window.location.href = '<c:url value="/telephone/import/template"/>' + '/' + filename;
}

function f_reload() {
    grid.loadData();
}

resizeDataGrid(grid);

</script>
</body>
</html>