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
<div id="detail" style="display:none;"><form:form id="detailform" method="post"></form:form></div>
<script type="text/javascript">
    var callStatusData = <sys:dictList type = "23" nullable="true"/>;
    var resultTypeData = <sys:dictList type = "27"/>;
    var taskStatusData = <sys:dictList type = "30" nullable="true"/>;
    var taskTypeData = <sys:dictList type = "33" nullable="true"/>;
    var userTypeData = <sys:dictList type = "19"/>;

    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        labelWidth: 100,
        inputWidth: 150,
        space: 30,
        fields: [
            {display: "话务员", name: "callUser.userName", newline: true, type: "text", cssClass: "field"},
            {display: "分配人", name: "assignUser.userName", newline: false, type: "text", cssClass: "field"},
            {display: "客户名称", name: "fldCustomerName", newline: false, type: "text", cssClass: "field"},
            {display: "手机号", name: "telephoneImportDetail.fldMobile", newline: false, type: "text", cssClass: "field"},
            {display: "任务时间", name: "startDate", newline: true, type: "date", cssClass: "field",
                attr:{op:'greaterorequal', vt:'date', field:"fldTaskDate"}},
            {display: "至", name: "endDate", newline: false, type: "date", cssClass: "field",
                attr:{op:'lessorequal', vt:'date', field:"fldTaskDate"}},
            {display: "任务类型", name: "fldTaskType", newline: false, type: "select", cssClass: "field",
                options: {
                    valueFieldID: "fldTaskType",
                    valueField: "value",
                    textField: "text",
                    data: taskTypeData
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
            {display: "任务时间", name: "fldTaskDate"},
            {display: "分配时间", name: "fldAssignDate"},
            {display: "话务员", name: "callUserName"},
            {display: "任务类型", name: "fldTaskType",
                render: function (item) {
                    return renderLabel(taskTypeData, item.fldTaskType);
                }
            },
            {display: "客户姓名", name: "fldCustomerName"},
            {display: "手机号", name: "fldMobile"},
            {display: "联系电话", name: "fldPhone"},
            {display: "任务状态", name: "fldTaskStatus", render: function (item) {
                return renderLabel(taskStatusData, item.fldTaskStatus);
            }},
            {display: "拨打时间", name: "fldCallDate"},
            {display: "拨打状态", name: "fldCallStatus",
                render: function (item) {
                    return renderLabel(callStatusData, item.fldCallStatus);
                }
            },
            {display: "任务结果", name: "fldResultType", render: function (item) {
                return renderLabel(resultTypeData, item.fldResultType);
            }},
            {display: "任务备注", name: "fldComment"}
        ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/task/list"/>',
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
                if (grid.getSelectedRows().length == 0) {
                    LG.tip('请选择至少一行数据!');
                    return;
                }
                f_exchange();
                break;
            case "recover":
                if (grid.getSelectedRows().length == 0) {
                    LG.tip('请选择至少一行数据!');
                    return;
                }

                jQuery.ligerDialog.confirm('确定收回所选中的未完成记录吗?', function (confirm) {
                    if (confirm)
                        f_recover();
                });

                break;
            case "export":
                var columns = grid.getColumns();
                var columnNames = [];
                var propertyNames = [];
                for (var i = 2; i < columns.length; i++) {
                    columnNames.push(columns[i].display);
                    if(columns[i].name=='fldTaskType'){
                        propertyNames.push("fldTaskTypeLabel");
                    }else if(columns[i].name=='fldTaskStatus'){
                        propertyNames.push("fldTaskStatusLabel");
                    }else if(columns[i].name=='fldCallStatus'){
                        propertyNames.push("fldCallStatusLabel");
                    }else if(columns[i].name=='fldResultType'){
                        propertyNames.push("fldResultTypeLabel");
                    }else{
                        propertyNames.push(columns[i].name);
                    }
                }
                f_export(columnNames, propertyNames);
                break;
                break;
        }
    }

    function f_reload() {
        grid.loadData();
    }

    function f_recover() {
        var rows = grid.getSelectedRows();
        var ids = "";

        for (var i = 0; i < rows.length; i++) {
            ids = ids + rows[i].fldId + ",";
        }

        if (ids.length > 0) {
            ids = ids.substring(0, ids.length - 1);
        }

        LG.ajax({
            url: '<c:url value="/telephone/task/recover"/>',
            loading: '正在收回中...',
            data: { ids: ids},
            success: function () {
                LG.showSuccess('收回成功');
                f_reload();
            },
            error: function (message) {
                LG.showError(message);
            }
        });
    }

    function f_export(columnNames, propertyNames) {
        var rule = LG.bulidFilterGroup("#formsearch");
        LG.ajax({
            loading: '正在导出数据中...',
            url: '<c:url value="/telephone/task/export"/>',
            data: {where: JSON2.stringify(rule), columnNames: JSON2.stringify(columnNames), propertyNames: JSON2.stringify(propertyNames)},
            success: function (data, message) {
                window.location.href = '<c:url value="/common/download?filepath="/>' + encodeURIComponent(data[0]);
            },
            error: function (message) {
                LG.tip(message);
            }
        });
    }


    var detailWin = null;
    var ids = null;
    function f_exchange(){
        if (detailWin) {
            detailWin.show();
        }
        else {
            //创建表单结构
            var mainform = $("#detailform");
            mainform.ligerForm({
                inputWidth:280,
                labelWidth:110,
                space:30,
                fields:[
                    { display:'任务日期', name:'date', width:150, newline:true, align:'left', type:'date', validate:{required:true, date:true}, format:'yyyy-MM-dd', showTime:false},
                    {display: "话务员", name: "fldCallUserNo",width:150, newline: true, type: "select", validate: {required: true},
                        comboboxName: "callUserNo", options: {valueFieldID: "callUserNo"}}
                ]
            });

            $.ligerui.get("callUserNo").openSelect({
                grid: {
                    rownumbers: true,
                    checkbox: false,
                    columnWidth: 238,
                    columns: [
                        {display: "用户名称", name: "userName"},
                        {display: "登录名称", name: "loginName"},
                        {display: "部门", name: "deptName"}
                    ], pageSize: 20, heightDiff: -40,
                    url: '<c:url value="/security/user/list"/>', sortName: 'userName'
                },
                search: {
                    fields: [
                        {display: "用户名称", name: "userName", newline: true, type: "text", cssClass: "field"},
                        {display:"用户类型",name:"type",newline:false,type:"select",comboboxName:"userType",cssClass: "field",
                            options:{
                                valueField: 'value',
                                textField: 'text',
                                isMultiSelect:false,
                                data:userTypeData,
                                valueFieldID:"type"
                            }
                        },
                        {display: "部门", name: "deptName", newline: true, type: "text", cssClass: "field",attr:{field:'userDept.dept.deptName'}}
                    ]
                },
                valueField: 'loginName', textField: 'userName', top: 30
            });

            detailWin = $.ligerDialog.open({
                title:'交接信息',
                target:$("#detail"),
                width:400, height:200, top:90,
                buttons:[
                    { text:'确定', onclick:function () {
                        $.ligerDialog.confirm('确定交接所选中的未完成记录吗？', function (yes) {
                            if (yes) {
                                save();
                                detailWin.hide();
                            }
                        });
                    } },
                    { text:'取消', onclick:function () {
                        detailWin.hide();
                    } }
                ]
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
                loading:'正在处理数据中...',
                url:'<c:url value="/telephone/task/exchange"/>',
                data: {ids: ids, date: $("#date").val(), callUserNo: $("#fldCallUserNo").val()},
                success:function () {
                    detailWin.hide();
                    LG.tip('交接成功!');
                    f_reload();
                },
                error:function (message) {
                    LG.tip(message);
                }
            });
        }
    }

    resizeDataGrid(grid);
</script>
</body>
</html>