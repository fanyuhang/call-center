<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
    .ui-autocomplete-loading {
        background: white url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>') right center no-repeat;
    }
</style>
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
    var finishStatusData = <sys:dictList type = "18" nullable="true"/>;
    var taskTypeData = <sys:dictList type = "33" nullable="true"/>;
    var recoverStatusData = <sys:dictList type = "38" nullable="true"/>;

    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        labelWidth: 100,
        inputWidth: 150,
        space: 30,
        fields: [
            {display: "话务员", name: "callUser.userName", newline: true, type: "text", cssClass: "field"},
            {display: "分配人", name: "assignUser.userName", newline: false, type: "text", cssClass: "field"},
            {display: "完成状态", name: "fldFinishStatus", newline: false, type: "select", cssClass: "field",
                options: {
                    valueFieldID: "fldFinishStatus",
                    valueField: "value",
                    textField: "text",
                    data: finishStatusData
                }, attr: {"op": "equal", "vt": "int"}
            },
            {display: "任务开始时间", name: "startDate", newline: true, type: "date", cssClass: "field",
                attr: {op: 'greaterorequal', vt: 'date', field: "fldTaskDate"}},
            {display: "任务结束时间", name: "endDate", newline: false, type: "date", cssClass: "field",
                attr: {op: 'lessorequal', vt: 'date', field: "fldTaskDate"}},
            {display: "任务类型", name: "fldTaskType", newline: false, type: "select", cssClass: "field",
                options: {
                    valueFieldID: "fldTaskType",
                    valueField: "value",
                    textField: "text",
                    data: taskTypeData
                }, attr: {"op": "equal", "vt": "int"}
            },
            {display: "话单名称", name: "telephoneImport.fldName", newline: true, type: "text", cssClass: "field"},
            {display: "回收状态", name: "fldRecoverStatus", newline: false, type: "select", cssClass: "field",
                options: {
                    valueFieldID: "fldRecoverStatus",
                    valueField: "value",
                    textField: "text",
                    initValue: '0',
                    data: recoverStatusData
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
            {display: "话单名称", name: "importName"},
            {display: "话务员", name: "callUserName"},
            {display: "任务类型", name: "fldTaskType",
                render: function (item) {
                    return renderLabel(taskTypeData, item.fldTaskType);
                }
            },
            {display: "任务时间", name: "fldTaskDate"},
            {display: "回收状态", name: "fldRecoverStatus",
                render: function (item) {
                    return renderLabel(recoverStatusData, item.fldRecoverStatus);
                }
            },
            {display: "话务数", name: "fldTaskNumber"},
            {display: "已完成数", name: "fldFinishNumber"},
            {display: "待跟踪数", name: "fldFollowNumber"},
            {display: "完成状态", name: "fldFinishStatus",
                render: function (item) {
                    return renderLabel(finishStatusData, item.fldFinishStatus);
                }
            },
            {display: "分配人", name: "assignUserName"},
            {display: "分配时间", name: "fldAssignDate"}
        ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/manage/list"/>',
        width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
    });

    //增加搜索按钮,并创建事件
    LG.appendSearchButtons("#formsearch", grid, true, true);

    //加载toolbar
    LG.loadToolbar(grid, toolbarBtnItemClick);

    //工具条事件
    function toolbarBtnItemClick(item) {
        switch (item.id) {
            case "exchange":
                top.f_addTab(null, '任务交接', '<c:url value="/telephone/manage/exchange"/>' + '?menuNo=${menuNo}');
                break;
            case "detail":
                if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                    LG.tip('请选择一行数据!');
                    return;
                }
                var selected = grid.getSelected();
                top.f_addTab(null, '任务明细', '<c:url value="/telephone/manage/listTask"/>' + '?menuNo=${menuNo}&id=' + selected.fldId);
                break;
            case "recover":
                if (grid.getSelectedRows().length == 0) {
                    LG.tip('请至少选择一行数据!');
                    return;
                }
                $.ligerDialog.confirm('确定回收所选中的话务分配记录吗？', function (yes) {
                    if (yes) {
                        recover();
                    }
                });
                break;
            case "export":
                var columns = grid.getColumns();
                var columnNames = [];
                var propertyNames = [];
                for (var i = 2; i < columns.length; i++) {
                    if (columns[i].name == 'fldRecoverStatus'
                            ||columns[i].name == 'assignUserName') {
                        continue;
                    }
                    columnNames.push(columns[i].display);
                    if (columns[i].name == 'fldTaskType') {
                        propertyNames.push("taskTypeLabel");
                    } else if (columns[i].name == 'fldFinishStatus') {
                        propertyNames.push("resultStatusLabel");
                    } else {
                        propertyNames.push(columns[i].name);
                    }
                }
                f_export(columnNames, propertyNames);
                break;
        }
    }

    function f_reload() {
        grid.loadData();
    }

    function recover() {

        var rows = grid.getSelectedRows();

        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            ids = ids + rows[i].fldId + ",";
        }

        if (ids.length > 0) {
            ids = ids.substring(0, ids.length - 1);
        }

        LG.ajax({
            loading: '正在回收中...',
            url: '<c:url value="/telephone/manage/recoverByIds"/>',
            data: {ids: ids},
            success: function (data, message) {
                f_reload();
                LG.showSuccess(message);
            },
            error: function (message) {
                LG.showError(message);
            }
        });
    }

    resizeDataGrid(grid);

    function f_export(columnNames, propertyNames) {
        var rule = LG.bulidFilterGroup("#formsearch");
        LG.ajax({
            loading: '正在导出数据中...',
            url: '<c:url value="/telephone/manage/exportForSearch"/>',
            data: {where: JSON2.stringify(rule), columnNames: JSON2.stringify(columnNames), propertyNames: JSON2.stringify(propertyNames)},
            success: function (data, message) {
                window.location.href = '<c:url value="/common/download?filepath="/>' + encodeURIComponent(data[0]);
            },
            error: function (message) {
                LG.tip(message);
            }
        });
    }

</script>
</body>
</html>