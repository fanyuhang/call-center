<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<input type="hidden" id="fileName"/>

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

<script type="text/javascript">

var assignStatusData = <sys:dictList type = "35" nullable="true"/>;
var auditStatusData = <sys:dictList type = "36" nullable="true"/>;
var finishStatusData = <sys:dictList type = "37" nullable="true"/>;
var statusData = <sys:dictList type = "34" nullable="true"/>;

//搜索表单应用ligerui样式
$("#formsearch").ligerForm({
    labelWidth: 80,
    inputWidth: 140,
    fields: [
        {display: "话务员", name: "createUser.userName", newline: true, type: "text", cssClass: "field"},
        {display: "审核状态", name: "fldAuditStatus", newline: false, type: "select", cssClass: "field",
            options: {
                valueFieldID: "fldAuditStatus",
                valueField: "value",
                textField: "text",
                data: auditStatusData,
                initValue: '0'
            }, attr: {"op": "equal", "vt": "int"}
        },
        {display: "客户姓名", name: "fldCustomerName", newline: false, type: "text", cssClass: "field"},
        {display: "完成状态", name: "fldResultStatus", newline: false, type: "select", cssClass: "field",
            options: {
                valueFieldID: "fldResultStatus",
                valueField: "value",
                textField: "text",
                data: finishStatusData
            }, attr: {"op": "equal", "vt": "int"}
        },
        {display: "创建时间", name: "startDate", newline: true, type: "date", cssClass: "field",
            attr: {op: 'greaterorequal', vt: 'date', field: "fldCreateDate"}},
        {display: "至", name: "endDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'lessorequal', vt: 'date', field: "fldCreateDate"}},
        {display: "分配时间", name: "startAssignDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'greaterorequal', vt: 'date', field: "fldAssignDate"}},
        {display: "至", name: "endAssignDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'lessorequal', vt: 'date', field: "fldAssignDate"}},
        {display: "理财经理", name: "financialUserName", newline: true, type: "text", cssClass: "field", attr: {field: "financialUser.userName"}}
    ],
    toJSON: JSON2.stringify
});

//列表结构
var grid = $("#maingrid").ligerGrid({
    checkbox: true,
    rownumbers: true,
    delayLoad: false,
    columnWidth: 160,
    columns: [
        {display: "分配时间", name: "fldAssignDate"},
        {display: "话单", name: "importName"},
        {display: "话务员", name: "createName"},
        {display: "客户姓名", name: "fldCustomerName"},
        {display: "理财经理", name: "financialName"},
        {display: "审核状态", name: "fldAuditStatus",
            render: function (item) {
                return renderLabel(auditStatusData, item.fldAuditStatus);
            }
        },
        {display: "完成状态", name: "fldResultSta,tus",
            render: function (item) {
                return renderLabel(finishStatusData, item.fldResultStatus);
            }
        },
        {display: "手机", name: "fldMobile"},
        {display: "固定电话", name: "fldPhone"},
        {display: "备注", name: "fldComment"},
        {display: "状态", name: "fldStatus",
            render: function (item) {
                return renderLabel(statusData, item.fldStatus);
            }
        },
        {display: "分配人", name: "assignName"},
        {display: "创建时间", name: "fldCreateDate"},
        {display: "审核人", name: "auditName"},
        {display: "审核时间", name: "fldAuditDate"}
    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/traceAudit/list"/>',sortName:'fldOperateDate', sortOrder:'desc',
    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
});

//增加搜索按钮,并创建事件
LG.appendSearchButtons("#formsearch", grid, true, true);

//加载toolbar
LG.loadToolbar(grid, toolbarBtnItemClick);

//工具条事件
function toolbarBtnItemClick(item) {
    switch (item.id) {
        case "pass":

            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldAuditStatus != 0) {
                    LG.showError("选择的数据只能包含未审核的记录");
                    return;
                }
            }

            $.ligerDialog.confirm('确定进行审核通过操作？', function (yes) {
                if (yes) {
                    f_pass();
                }
            });
            break;
        case "nopass":

            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldAuditStatus != 0) {
                    LG.showError("选择的数据只能包含未审核的记录");
                    return;
                }
            }

            $.ligerDialog.confirm('确定进行审核退回操作？', function (yes) {
                if (yes) {
                    f_nopass();
                }
            });
            break;
    }
}

function f_reload() {
    grid.loadData();
}

resizeDataGrid(grid);

function f_pass() {
    var rows = grid.getSelectedRows();
    var ids = "";
    for (var i = 0; i < rows.length; i++) {
        ids = ids + rows[i].fldId + ",";
    }

    if (ids.length > 0) {
        ids = ids.substring(0, ids.length - 1);
    }

    LG.ajax({
        loading: '正在更新中...',
        url: '<c:url value="/telephone/traceAudit/save"/>',
        data: {ids: ids},
        success: function () {
            LG.tip('更新成功!');
            f_reload();
        },
        error: function (message) {
            LG.tip(message);
        }
    });
}

function f_nopass() {
    var rows = grid.getSelectedRows();
    var ids = "";
    for (var i = 0; i < rows.length; i++) {
        ids = ids + rows[i].fldId + ",";
    }

    if (ids.length > 0) {
        ids = ids.substring(0, ids.length - 1);
    }

    LG.ajax({
        loading: '正在更新中...',
        url: '<c:url value="/telephone/traceAudit/cancel"/>',
        data: {ids: ids},
        success: function () {
            LG.tip('更新成功!');
            f_reload();
        },
        error: function (message) {
            LG.tip(message);
        }
    });
}
</script>
</body>
</html>