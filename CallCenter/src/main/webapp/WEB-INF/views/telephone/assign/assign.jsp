<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post"></form:form>
<script type="text/javascript">
var telephoneSourceData =<sys:dictList type = "17"/>;
var userTypeData = <sys:dictList type = "19"/>;

//覆盖本页面grid的loading效果
LG.overrideGridLoading();

//创建表单结构
var mainform = $("#mainform");
mainform.ligerForm({
    inputWidth: 250,
    labelWidth: 100,
    space: 30,
    fields: [
        {display: "话务来源", name: "fldSource", newline: false, type: "hidden", attr:{value:"0"}},
//        {display: "话务来源", name: "fldSource", newline: false, type: "hidden", attr: {readonly: "readonly"}, validate: {required: true}, cssClass: "field",
//            options: {
//                valueFieldID: "fldSource",
//                valueField: "value",
//                textField: "text",
//                data: telephoneSourceData,
//                initValue: 0
//            }, attr: {"op": "equal", "vt": "int"}, comboboxName: "telephoneSource"
//        },
        {display: "话单名称", name: "importId", newline: true, type: "select", attr: {readonly: "readonly"}, validate: {required: true},cssClass: "field",
            options: {
                valueFieldID: "importId",
                validate: {required: true}
            },comboboxName: "importName", group: "<label style=white-space:nowrap;>1. 选择话单</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'
        },
        {display: "可分配话务数", name: "taskCount", newline: false, type: "text", attr: {readonly: "readonly"}},
        {display: "话务员", name: "fldCallUserNo", newline: true, type: "select", validate: {required: true}, group: "<label style=white-space:nowrap;>2. 选择话务员</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>',
            comboboxName: "callUserNo", options: {valueFieldID: "callUserNo"}},
        {display: "选中话务员数", name: "fldCallUserNumber", newline: false, type: "text", attr: {readonly: "readonly"}},
        {display: "话务开始时间", name: "fldBeginDate", newline: true, type: "date", attr: {readonly: "readonly"}, validate: {required: true}, group: "<label style=white-space:nowrap;>3. 选择任务时间段</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
        {display: "话务结束时间", name: "fldEndDate", newline: false, type: "date", attr: {readonly: "readonly"}, validate: {required: true}},
        {display: "合计天数", name: "fldDayNumber", newline: true, type: "text", attr: {readonly: "readonly"}},
        {display: "单日单人话务数", name: "fldAverageNumber", newline: true, type: "text", validate: {required: true}, group: "<label style=white-space:nowrap;>4. 录入单日话务员话务数</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
        {display: "合计话务总数", name: "fldAssignNumber", newline: false, type: "text", attr: {readonly: "readonly"}}
    ]
});

mainform.attr("action", '<c:url value="/telephone/assign/save"/>');

$.ligerui.get("importName").openSelect({
    grid: {
        rownumbers: true,
        checkbox: false,
        columnWidth: 180,
        columns: [
            {display: "话单名称", name: "fldName"},
            {display: "导入记录数", name: "fldImportTotalNumber"},
            {display: "已分配记录数", name: "fldAssignTotalNumber"},
            {display: "导入人", name: "createUserName"},
            {display: "导入时间", name: "fldCreateDate"}
        ], pageSize: 20, heightDiff: -10,
        url: '<c:url value="/telephone/import/listForAssign"/>', sortName: 'fldCreateDate'
    },
    search: {
        fields: [
            {display: "话单名称", name: "fldName", newline: true, type: "text", cssClass: "field"}
        ]
    },
    valueField: 'fldId', textField: 'fldName', top: 30
});

$.ligerui.get("fldBeginDate").bind("changedate", function () {
    var fldBeginDate = $("#fldBeginDate").val();
    var fldEndDate = $("#fldEndDate").val();
    if (fldBeginDate == "" || fldEndDate == "") return;

    if (fldBeginDate > fldEndDate) {
        LG.showError("开始时间不能晚于结束时间!");
        return;
    }

    var days = (Date.parse(fldEndDate) - Date.parse(fldBeginDate)) / (1000 * 60 * 60 * 24) + 1;
    $("#fldDayNumber").val(days);

    var dayNumber = $("#fldDayNumber").val();
    var averageNumber = $("#fldAverageNumber").val();
    var callUserNumber = $("#fldCallUserNumber").val();

    if (averageNumber != "" && callUserNumber != "") {
        $("#fldAssignNumber").val(dayNumber * averageNumber * callUserNumber);
    }
});

$.ligerui.get("fldEndDate").bind("changedate", function () {
    var fldBeginDate = $("#fldBeginDate").val();
    var fldEndDate = $("#fldEndDate").val();
    if (fldBeginDate == "" || fldEndDate == "") return;

    if (fldBeginDate > fldEndDate) {
        LG.showError("开始时间不能晚于结束时间!");
        return;
    }

    var days = (Date.parse(fldEndDate) - Date.parse(fldBeginDate)) / (1000 * 60 * 60 * 24) + 1;
    $("#fldDayNumber").val(days);

    var dayNumber = $("#fldDayNumber").val();
    var averageNumber = $("#fldAverageNumber").val();
    var callUserNumber = $("#fldCallUserNumber").val();

    if (averageNumber != "" && callUserNumber != "") {
        $("#fldAssignNumber").val(dayNumber * averageNumber * callUserNumber);
    }
});

$.ligerui.get("callUserNo").openSelect({
    grid: {
        rownumbers: true,
        checkbox: true,
        columnWidth: 238,
        columns: [
            {display: "用户名称", name: "userName"},
            {display: "登录名称", name: "loginName"},
            {display: "部门", name: "deptName"}
        ], pageSize: 20, heightDiff: -10,
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
            }
        ]
    },
    valueField: 'loginName', textField: 'userName', top: 30,
    handleSelect: function (data) {
        $("#fldCallUserNumber").val(data.length);

        var fldAssignNumber = $("#fldAssignNumber").val();
        if (fldAssignNumber == "" || fldAssignNumber == 0) return;

        var fldCallUserNumber = $("#fldCallUserNumber").val();
        if (fldCallUserNumber == "" || fldCallUserNumber == 0) return;

        $("#fldAverageNumber").val(parseFloat(fldAssignNumber) / parseFloat(fldCallUserNumber));
    }
});

$("#fldAverageNumber").change(function () {
    var averageNumber = $("#fldAverageNumber").val();
    if (averageNumber == "") return;

    var dayNumber = $("#fldDayNumber").val();
    var averageNumber = $("#fldAverageNumber").val();
    var callUserNumber = $("#fldCallUserNumber").val();

    if (callUserNumber != "") {
        $("#fldAssignNumber").val(dayNumber * averageNumber * callUserNumber);
    }
});

$("#importName").change(function () {
    if ($("#importId").val() == "") {
        $("#taskCount").val("0");
    } else {
        LG.ajax({
            url: '<c:url value="/telephone/assign/countImportCustomerById"/>' + '?id=' + $("#importId").val(),
            data: {},
            beforeSend: function () {

            },
            complete: function () {
            },
            success: function (message) {
                $("#taskCount").val(message);
            },
            error: function (message) {
            }
        });
    }
});

$("#telephoneSource").change(function () {
    var source = $("#fldSource").val();
    if (source == "") return;
    if (source == 0) {
        $.ligerui.get("importName").setEnabled();

        if ($("#importId").val() == "") {
            $("#taskCount").val("0");
        } else {
            LG.ajax({
                url: '<c:url value="/telephone/assign/countImportCustomerById"/>' + '?id=' + $("#importId").val(),
                data: {},
                beforeSend: function () {

                },
                complete: function () {
                },
                success: function (message) {
                    $("#taskCount").val(message);
                },
                error: function (message) {
                }
            });
        }
    } else {
        $.ligerui.get("importName").setDisabled();
        LG.ajax({
            url: '<c:url value="/telephone/assign/countImportCustomer"/>',
            data: {},
            beforeSend: function () {

            },
            complete: function () {
            },
            success: function (message) {
                $("#taskCount").val(message);
            },
            error: function (message) {
            }
        });
    }
});

$("#fldAssignNumber").change(function () {
    var fldAssignNumber = $("#fldAssignNumber").val();
    if (fldAssignNumber == "" || fldAssignNumber == 0) return;

    var fldCallUserNumber = $("#fldCallUserNumber").val();
    if (fldCallUserNumber == "" || fldCallUserNumber == 0) return;

    $("#fldAverageNumber").val(parseFloat(fldAssignNumber) / parseFloat(fldCallUserNumber));
});

//表单底部按钮
LG.setFormDefaultBtn(f_cancel, f_save);

function f_save() {
    LG.validate(mainform);

    if ($("#fldCallUserNo").val() == "") {
        LG.showError("请选择话务员!");
        return;
    }

    if ($("#fldSource").val() == "") {
        LG.showError("请选择话务来源!");
        return;
    } else if ($("#fldSource").val() == '0' && $("#importId").val() == "") {
        LG.showError("请选择话单!");
        return;
    }

    var taskCount = $("#taskCount").val();
    var fldAssignNumber = $("#fldAssignNumber").val();
    if (parseInt(taskCount) < parseInt(fldAssignNumber)) {
        LG.showError("话务数不能大于可分配话务数!");
        return;
    }

    var fldBeginDate = $("#fldBeginDate").val();
    var fldEndDate = $("#fldEndDate").val();

    if (fldBeginDate != "" && fldEndDate != "" && fldBeginDate > fldEndDate) {
        LG.showError("开始时间不能晚于结束时间!");
        return;
    }

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
</script>
</body>
</html>
