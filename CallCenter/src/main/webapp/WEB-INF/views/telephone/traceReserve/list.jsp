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

<div id="goingDetail" style="display:none;"><form:form id="goingform" method="post"></form:form></div>

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
        {display: "客户姓名", name: "fldCustomerName", newline: false, type: "text", cssClass: "field"},
        {display: "理财经理", name: "financialUserName", newline: false, type: "text", cssClass: "field", attr: {field: "financialUser.userName"}},
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
            attr: {op: 'lessorequal', vt: 'date', field: "fldAssignDate"}}
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
        {display: "审核时间", name: "fldAuditDate"},
        {display: "话单", name: "importName"},
        {display: "话务员", name: "createName"},
        {display: "客户姓名", name: "fldCustomerName"},
        {display: "理财经理", name: "financialName"},
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
        {display: "创建时间", name: "fldCreateDate"},
        {display: "审核人", name: "auditName"}
    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/traceReserve/list"/>',sortName:'fldOperateDate', sortOrder:'desc',
    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
});

//增加搜索按钮,并创建事件
LG.appendSearchButtons("#formsearch", grid, true, true);

//加载toolbar
LG.loadToolbar(grid, toolbarBtnItemClick);

//工具条事件
function toolbarBtnItemClick(item) {
    switch (item.id) {
        case "on":

            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldResultStatus != 0) {
                    LG.showError("选择的数据只能包含待跟踪的记录");
                    return;
                }
            }

            $.ligerDialog.confirm('确定进行该操作？', function (yes) {
                if (yes) {
                    f_updateStatus(1);
                }
            });
            break;
        case "sign":

            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldResultStatus == 5) {
                    LG.showError("选择的数据不能包含已签约的记录");
                    return;
                }
            }

            $.ligerDialog.confirm('确定进行该操作？', function (yes) {
                if (yes) {
                    f_updateStatus(5);
                }
            });
            break;
        case "notsign":

            if (grid.getSelectedRows().length == 0) {
                LG.tip('请至少选择一行数据!');
                return;
            }

            var rows = grid.getSelectedRows();
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].fldResultStatus == 9) {
                    LG.showError("选择的数据不能包含拒签约的记录");
                    return;
                }
            }

            $.ligerDialog.confirm('确定进行该操作？', function (yes) {
                if (yes) {
                    f_updateStatus(9);
                }
            });
            break;
        case "dialHis":
            if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                LG.tip('请选择一行数据!');
                return;
            }
            var selected = grid.getSelected();
            top.f_addTab(null, '拨打记录明细', '/telephone/customer/dialHis' + '?menuNo=010504&customerName='+selected.fldCustomerName+'&phone='+selected.fldPhone+'&mobile='+selected.fldMobile);
            break;
    }
}

function f_reload() {
    grid.loadData();
}

resizeDataGrid(grid);
var goingWin = null;
var status;
function f_updateStatus(resultStatus) {

    status = resultStatus;

    if (goingWin) {
        goingWin.show();
    }
    else {
        //创建表单结构
        var mainform = $("#goingform");
        mainform.ligerForm({
            inputWidth: 200,
            labelWidth: 110,
            space: 30,
            fields: [
                {display: "备注", name: "comment1", newline: true, type: "text", validate: {required: true}, cssClass: "field" }
            ]
        });

        goingWin = $.ligerDialog.open({
            title: '备注信息',
            target: $("#goingDetail"),
            width: 400, height: 120, top: 90,
            buttons: [
                { text: '确定', onclick: function () {
                    save();
                } },
                { text: '取消', onclick: function () {
                    goingWin.hide();
                } }
            ]
        });
    }

    $("#comment1").val("");

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
            url: '<c:url value="/telephone/traceReserve/updateResultStatus"/>',
            data: {status: status,ids: ids, comment: $("#comment1").val()},
            success: function () {
                goingWin.hide();
                LG.tip('更新成功!');
                f_reload();
            },
            error: function (message) {
                LG.tip(message);
            }
        });
    }
}

</script>
</body>
</html>