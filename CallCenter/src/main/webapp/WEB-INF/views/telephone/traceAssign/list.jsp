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
<div id="addDetail" style="display:none;"><form:form id="addform" method="post"></form:form></div>
<div id="updateDetail" style="display:none;"><form:form id="updateform" method="post"></form:form></div>

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
        {display: "状态", name: "fldStatus", newline: false, type: "select", cssClass: "field",
            options: {
                valueFieldID: "fldStatus",
                valueField: "value",
                textField: "text",
                data: statusData,
                initValue: '0'
            }, attr: {"op": "equal", "vt": "int"}
        },
        {display: "分配状态", name: "fldAssignStatus", newline: false, type: "select", cssClass: "field",
            options: {
                valueFieldID: "fldAssignStatus",
                valueField: "value",
                textField: "text",
                data: assignStatusData,
            }, attr: {"op": "equal", "vt": "int"}
        },
        {display: "审核状态", name: "fldAuditStatus", newline: false, type: "select", cssClass: "field",
            options: {
                valueFieldID: "fldAuditStatus",
                valueField: "value",
                textField: "text",
                data: auditStatusData
            }, attr: {"op": "equal", "vt": "int"}
        },
        {display: "客户姓名", name: "fldCustomerName", newline: true, type: "text", cssClass: "field"},
        {display: "完成状态", name: "fldResultStatus", newline: false, type: "select", cssClass: "field",
            options: {
                valueFieldID: "fldResultStatus",
                valueField: "value",
                textField: "text",
                data: finishStatusData
            }, attr: {"op": "equal", "vt": "int"}
        },
        {display: "创建时间", name: "startDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'greaterorequal', vt: 'date', field: "fldCreateDate"}},
        {display: "至", name: "endDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'lessorequal', vt: 'date', field: "fldCreateDate"}},
        {display: "理财经理", name: "financialUserName", newline: true, type: "text", cssClass: "field", attr: {field: "financialUser.userName"}},
        {display: "分配时间", name: "startAssignDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'greaterorequal', vt: 'date', field: "fldAssignDate"}},
        {display: "至", name: "endAssignDate", newline: false, type: "date", cssClass: "field",
            attr: {op: 'lessorequal', vt: 'date', field: "fldAssignDate"}}
    ],
    toJSON: JSON2.stringify
});

//列表结构
var grid = $("#maingrid").ligerGrid({
    checkbox: true,
    rownumbers: true,
    delayLoad: true,
    columnWidth: 160,
    columns: [
        {display: "创建时间", name: "fldCreateDate"},
        {display: "话单", name: "importName"},
        {display: "话务员", name: "createName"},
        {display: "客户姓名", name: "fldCustomerName"},
        {display: "理财经理", name: "financialName"},
        {display: "分配状态", name: "fldAssignStatus",
            render: function (item) {
                return renderLabel(assignStatusData, item.fldAssignStatus);
            }
        },
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
        {display: "分配时间", name: "fldAssignDate"},
        {display: "审核人", name: "auditName"},
        {display: "审核时间", name: "fldAuditDate"}
    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/traceAssign/list"/>',sortName:'fldOperateDate', sortOrder:'desc',
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

            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldAssignStatus == 1) {
                    LG.showError("选择的数据中不能包含已分配的记录");
                    return;
                }
            }
            f_add();
            break;
        case "edit":

            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldAuditStatus == 9) {
                    LG.showError("选择的记录中只能包含未审核的记录");
                    return;
                }
            }
            f_edit();
            break;
        case "reset":
            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldAuditStatus == 9) {
                    LG.showError("选择的数据中不能包含审核通过的记录");
                    return;
                }
            }

            $.ligerDialog.confirm('确定进行重置操作？', function (yes) {
                if (yes) {
                    f_reset();
                    addWin.hide();
                }
            });
            break;
    }
}

function f_reload() {
    grid.loadData();
}

resizeDataGrid(grid);

function f_reset() {
    var rows = grid.getSelectedRows();
    var ids = "";
    for (var i = 0; i < rows.length; i++) {
        ids = ids + rows[i].fldId + ",";
    }

    if (ids.length > 0) {
        ids = ids.substring(0, ids.length - 1);
    }

    LG.ajax({
        loading: '正在重置中...',
        url: '<c:url value="/telephone/traceAssign/reset"/>',
        data: {ids: ids, financialUserNo: $("#fldFinancialUserNo").val()},
        success: function () {
            LG.tip('重置成功!');
            f_reload();
        },
        error: function (message) {
            LG.tip(message);
        }
    });
}

var addWin = null;
function f_add() {
    if (addWin) {
        addWin.show();
    }
    else {
        //创建表单结构
        var mainform = $("#addform");
        mainform.ligerForm({
            inputWidth: 200,
            labelWidth: 110,
            space: 30,
            fields: [
                {display: "理财经理", name: "fldFinancialUserNo", newline: true, type: "select", validate: {required: true}, cssClass: "field", comboboxName: "financialUserNo",
                    options: {valueFieldID: "financialUserNo"}
                }
            ]
        });

        addWin = $.ligerDialog.open({
            title: '分配信息',
            target: $("#addDetail"),
            width: 400, height: 120, top: 90,
            buttons: [
                { text: '确定', onclick: function () {
                    $.ligerDialog.confirm('确定进行分配操作？', function (yes) {
                        if (yes) {
                            save();
                            addWin.hide();
                        }
                    });
                } },
                { text: '取消', onclick: function () {
                    addWin.hide();
                } }
            ]
        });
        var financialWhere = '{"op":"and","rules":[{"field":"type","value":"10","op":"equal","type":"string"}]}';
        $.ligerui.get("financialUserNo").openSelect({
            grid: {
                rownumbers: true,
                checkbox: false,
                columnWidth: 200,
                columns: [
                    {display: "用户名称", name: "userName"},
                    {display: "登录名称", name: "loginName"},
                    {display: "部门", name: "deptName"}
                ], pageSize: 20, heightDiff: -10,
                url: '<c:url value="/security/user/list"/>?where=' + financialWhere, sortName: 'userName'
            },
            search: {
                fields: [
                    {display: "用户名称", name: "userName", newline: true, type: "text", cssClass: "field"}
                ]
            },
            valueField: 'loginName', textField: 'userName', top: 30
        });
    }

    function save() {
        var rows = grid.getSelectedRows();
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            ids = ids + rows[i].fldId + ",";
        }

        if (ids.length > 0) {
            ids = ids.substring(0, ids.length - 1);
        }

        LG.ajax({
            loading: '正在分配中...',
            url: '<c:url value="/telephone/traceAssign/save"/>',
            data: {ids: ids, financialUserNo: $("#fldFinancialUserNo").val()},
            success: function () {
                addWin.hide();
                LG.tip('分配成功!');
                f_reload();
            },
            error: function (message) {
                LG.tip(message);
            }
        });
    }
}

var updateWin = null;
function f_edit() {
    if (updateWin) {
        updateWin.show();
    }
    else {
        //创建表单结构
        var mainform = $("#updateform");
        mainform.ligerForm({
            inputWidth: 200,
            labelWidth: 110,
            space: 30,
            fields: [
                {display: "理财经理", name: "fldFinancialUserNo1", newline: true, type: "select", validate: {required: true}, cssClass: "field",
                    comboboxName: "financialUserNo1",
                    options: {valueFieldID: "financialUserNo1"}
                }
            ]
        });

        updateWin = $.ligerDialog.open({
            title: '更新分配信息',
            target: $("#updateDetail"),
            width: 400, height: 120, top: 90,
            buttons: [
                { text: '确定', onclick: function () {
                    $.ligerDialog.confirm('确定进行分配操作？', function (yes) {
                        if (yes) {
                            save();
                            updateWin.hide();
                        }
                    });
                } },
                { text: '取消', onclick: function () {
                    updateWin.hide();
                } }
            ]
        });
        var financialWhere = '{"op":"and","rules":[{"field":"type","value":"10","op":"equal","type":"string"}]}';
        $.ligerui.get("financialUserNo1").openSelect({
            grid: {
                rownumbers: true,
                checkbox: false,
                columnWidth: 200,
                columns: [
                    {display: "用户名称", name: "userName"},
                    {display: "登录名称", name: "loginName"},
                    {display: "部门", name: "deptName"}
                ], pageSize: 20, heightDiff: -10,
                url: '<c:url value="/security/user/list"/>?where=' + financialWhere, sortName: 'userName'
            },
            search: {
                fields: [
                    {display: "用户名称", name: "userName", newline: true, type: "text", cssClass: "field"}
                ]
            },
            valueField: 'loginName', textField: 'userName', top: 30
        });
    }

    function save() {
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
            url: '<c:url value="/telephone/traceAssign/update"/>',
            data: {ids: ids, financialUserNo: $("#fldFinancialUserNo1").val()},
            success: function () {
                updateWin.hide();
                LG.tip('更新成功!');
                f_reload();
            },
            error: function (message) {
                LG.tip(message);
            }
        });
    }
}

$(".button2").trigger("click");

</script>
</body>
</html>