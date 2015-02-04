<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<title>我的外拨</title>
<script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerLayout.js"/>" type="text/javascript"></script>
<style type="text/css">
    .cell-label {
        width: 80px;
    }

    #tabcontainer .l-tab-links {
        border-top: 1px solid #D0D0D0;
        border-left: 1px solid #D0D0D0;
        border-right: 1px solid #D0D0D0;
    }

    #innertabcontainer {
        width: 715px;
        margin-left: 500px;
        margin-top: -179px;
    }

    #innertabcontainer .l-tab-links {
        border-top: 0px solid #D0D0D0;
        border-left: 0px solid #D0D0D0;
        border-right: 0px solid #D0D0D0;
    }

    #innertabcontainer .l-tab-content-item {
        margin-left: -7px;
        margin-top: 0px;
    }

    .projectgrid .l-selected .l-grid-row-cell, .projectgrid .l-selected {
        background: none;
    }

    .access-icon {
        background: url('<c:url value="/static/ligerUI/ligerUI/skins/Aqua/images/controls/checkbox.gif"/>') 0px 0px;
        height: 13px;
        line-height: 13px;
        width: 13px;
        margin: 4px 20px;
        display: block;
        cursor: pointer;
    }

    .access-icon-selected {
        background-position: 0px -13px;
    }

    .l-panel td.l-grid-row-cell-editing {
        padding-bottom: 2px;
        padding-top: 2px;
    }
</style>
</head>
<body style="overflow:hidden;">
<div id="layout" style="margin:2px; margin-right:3px;">
    <div position="center" id="mainmenu" title="我的任务">
        <div id="tasklist" style="margin:2px auto;"></div>
    </div>
    <div position="bottom" title="客户相关信息">
        <div id="tabcontainer" style="margin:2px;">
            <div title="客户信息" tabid="custInfo">
                <!--<form:form id="customerInfo" name="customerInfo" method="post"></form:form>-->
                <div id="customerInfo" style="margin:2px auto;"></div>
                <div id="innertabcontainer">
                    <div title="合同信息" tabid="contractInfo">
                        <div id="contractInfo" style="margin:2px auto;"></div>
                    </div>
                    <div title="拨打历史" tabid="dialHis">
                        <div id="dialHistory" style="margin:2px auto;"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="callDialog" style="display:none;">
    <form:form id="callMainform" name="callMainform" method="post" modelAttribute="call"></form:form>
</div>
<script type="text/javascript">
//覆盖本页面grid的loading效果
LG.overrideGridLoading();

var isTongHua = false;

var genderData = <sys:dictList type = "1"/>;
var resultTypeData = <sys:dictList type = "27"/>;
var statusData = <sys:dictList type = "8"/>;
var finishStatus = <sys:dictList type = "21" nullable="true"/>;
var cardLevelData = <sys:dictList type = "13"/>;
var callTypeData = <sys:dictList type = "28"/>;
var callStatusData = <sys:dictList type = "23"/>;
var taskStatusData = <sys:dictList type = "30"/>;

var layout = $("#layout").ligerLayout({
    bottomHeight: $(window).height() * 0.50,
    heightDiff: -6,
    onEndResize: updateGridHeight,
    onHeightChanged: updateGridHeight
});

var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");

$("#tabcontainer").ligerTab();
$("#innertabcontainer").ligerTab();

var taskListGrid;
var callId;

taskListGrid = $("#tasklist").ligerGrid({
    columns: [
        {display: '任务时间', name: 'fldTaskDate'},
        {display: '客户姓名', name: 'fldCustomerName', width: 200},
        {display: '手机', name: 'fldMobile', width: 120,
            render: function (item) {
                if (null == item.fldMobile || "" == item.fldMobile)
                    return "";
                return '<span>' + item.fldMobile + '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="javascript:makecall(\'' + item.fldMobile + '\',\'' + item.fldCustomerName + '\');" title="拨打" style="background:url(/static/ligerUI/icons/silkicons/phone.png) no-repeat;text-decoration:none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>';
            }
        },
        {display: '固定电话', name: 'fldPhone', width: 120,
            render: function (item) {
                if (null == item.fldPhone || "" == item.fldPhone)
                    return "";
                return '<span>' + item.fldPhone + '&nbsp;&nbsp;<a href="javascript:void(0);" onclick="javascript:makecall(\'' + item.fldPhone + '\',\'' + item.fldCustomerName + '\');" title="拨打" style="background:url(/static/ligerUI/icons/silkicons/telephone.png) no-repeat;text-decoration:none;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></span>';
            }
        },
        {display: '拨打状态', name: 'fldCallStatus', width: 130,
            render: function (item) {
                return renderLabel(callStatusData, item.fldCallStatus);
            }
        },
        {display: "任务结果", name: "fldResultType",
            render: function (item) {
                return renderLabel(resultTypeData, item.fldResultType);
            }
        },
        {display: "备注", name: "fldComment", width: 200},
        {display: "fldAssignDetailId", name: "fldAssignDetailId", hide: 1, width: 1}
    ],
    width: '99%', height: 190, rowHeight: 20, fixedCellHeight: true, sortName:'fldTaskDate', sortOrder:'asc',
    frozen: false, checkbox: false, rownumbers: true,
    url: '<c:url value="/telephone/dial/listTask"/>'
});

var customeForm, taskId;
customeForm = $("#customerInfo");
customeForm.ligerForm({
    labelWidth: 80,
    inputWidth: 150,
    space: 30,
    heightDiff: -100,
    fields: [
        {display: "ID", name: "fldId", newline: true, type: "hidden"},
        {display: "客户名称", name: "teleCustomerName", newline: true, type: "text", validate: {required: true}, cssClass: "field"},
        {display: "性别", name: "teleGender", newline: false, type: "select", cssClass: "field", comboboxName: "gender",
            options: {
                valueField: 'value',
                textField: 'text',
                isMultiSelect: false,
                data: genderData,
                valueFieldID: "teleGender"
            }
        },
        {display: "手机", name: "teleMobile", newline: true, type: "text", cssClass: "field"},
        {display: "电话号码", name: "telePhone", newline: false, type: "text", cssClass: "field"},
        {display: "出生日期", name: "custBirthday", newline: true, type: "date", cssClass: "field"},
        {display: "身份证号", name: "custIdentityNo", newline: false, type: "text", cssClass: "field"},
        {display: "地址", name: "teleAddress", newline: true, type: "text", cssClass: "field"},
        {display: "邮箱", name: "custEmail", newline: false, type: "text", cssClass: "field"},
        {display: "理财经理", name: "fldFinancialUserNo", newline: true, type: "select", validate: {required: true}, cssClass: "field", comboboxName: "financialUserNo",
            options: {valueFieldID: "financialUserNo"}
        },
        {display: "custId", name: "custId", type: "hidden"}
    ],
    toJSON: JSON2.stringify
});

var financialWhere = '{"op":"and","rules":[{"field":"type","value":"10","op":"equal","type":"string"}]}';
$.ligerui.get("financialUserNo").openSelect({
    grid: {
        rownumbers: true,
        checkbox: true,
        columnWidth: 238,
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

$(".l-form-container").css("height", "175");
$(".l-form-container").css("width", "520");
$('<div class="l-dialog-btn" style="margin-right:30px;margin-top:20px;" onclick="javascript:f_savecust();"><div class="l-dialog-btn-l"></div><div class="l-dialog-btn-r"></div><div class="l-dialog-btn-inner">保存</div></div>').appendTo(".l-form-container");

$("#contractInfo").ligerGrid({
    checkbox: false,
    rownumbers: true,
    delayLoad: false,
    columnWidth: 180,
    columns: [
        {display: "签订日期", name: "fldSignDate"},
        {display: "客户姓名", name: "customerName"},
        {display: "产品全称", name: "productFullName"},
        {display: "产品实际天数", name: "productClearDays"},
        {display: "成立日期", name: "establishDate"},
        {display: "到期日期", name: "dueDate"},
        {display: "打款日期", name: "fldMoneyDate"},
        {display: "年化收益率", name: "fldAnnualizedRate",
            render: function (item) {
                return item.fldAnnualizedRate + "%";
            }},
        {display: "购买金额(万元)", name: "fldPurchaseMoney"},
        {display: "预期收益(元)", name: "fldAnnualizedMoney",
            render: function (item) {
                return formatCurrency(item.fldAnnualizedMoney);
            }},
        {display: "合同状态", name: "fldStatus",
            render: function (item) {
                return renderLabel(statusData, item.fldStatus);
            }
        },
        {display: "是否已到期", name: "fldFinishStatus",
            render: function (item) {
                return renderLabel(finishStatus, item.fldFinishStatus);
            }},
        {display: "理财经理", name: "financialUserName"},
        {display: "客服经理", name: "serviceUserName"},
        {display: "客户经理", name: "customerUserName"},
        {display: "银行卡号", name: "fldBankNo"},
        {display: "瑞得卡号", name: "fldCardNo"},
        {display: "瑞得卡等级", name: "fldCardLevel",
            render: function (item) {
                return renderLabel(cardLevelData, item.fldCardLevel);
            }
        },
        {display: "操作人", name: "operateUserName"},
        {display: "操作时间", name: "fldOperateDate"}
    ], dataAction: 'server', pageSize: 50, toolbar: null, url: null,
    width: '98%', height: '33%', toJSON: JSON2.stringify
});

var dialHistory = $("#dialHistory");
var dialHistorGrid = dialHistory.ligerGrid({
    checkbox: false,
    rownumbers: true,
    delayLoad: false,
    columnWidth: 180,
    columns: [
        {display: "呼叫类型", name: "fldCallType",
            render: function (item) {
                return renderLabel(callTypeStatus, item.fldCallType);
            }},
        {display: "拨打号码", name: "fldPhone",
            render: function (item) {
                return LG.hiddenPhone(item.fldPhone);
            }},
        {display: "拨打/呼入时间", name: "fldCallDate"},
        {display: "通话时长(秒)", name: "fldCallLong"},
        {display: "通话开始时间", name: "fldCallBeginTime"},
        {display: "通话结束时间", name: "fldCallEndTime"}
    ], dataAction: 'server', pageSize: 50, toolbar: null, url: null,
    width: '98%', height: '33%', toJSON: JSON2.stringify
});

taskListGrid.bind('SelectRow', function (rowdata) {
    var customerName = rowdata.fldCustomerName;
    var mobile = rowdata.fldMobile;
    var phone = rowdata.fldPhone;
    taskId = rowdata.fldId;

    LG.ajax({
        url: '<c:url value="/telephone/dial/findCustomer"/>',
        data: {customerName: customerName, mobile: mobile, phone: phone},
        dataType: 'json', type: 'post',
        success: function (data) {
            if (null != data && null != data[0]) {
                var customer = data[0];

                var origCustomer = null;
                if (null != data[1]) {
                    origCustomer = data[1];
                }

                $("#fldId").val(customer.fldId);
                $("#teleCustomerName").val(customer.fldCustomerName);
                $("#teleGender").val(customer.fldGender);
                $("#gender").val(renderLabel(genderData, customer.fldGender));
                $("#teleMobile").val(null != customer.fldMobile ? customer.fldMobile : (null != origCustomer && null != origCustomer.fldMobile ? origCustomer.fldMobile : ""));
                $("#telePhone").val(null != customer.fldPhone ? customer.fldPhone : (null != origCustomer && null != origCustomer.fldPhone ? origCustomer.fldPhone : ""));
                $("#custBirthday").val(null != origCustomer && null != origCustomer.fldBirthday ? origCustomer.fldBirthday : customer.fldBirthday);
                $("#custIdentityNo").val(null != origCustomer && null != origCustomer.fldIdentityNo ? origCustomer.fldIdentityNo : customer.fldIdentityNo);
                $("#teleAddress").val(null != origCustomer && null != origCustomer.fldComment ? origCustomer.fldComment : (customer.fldAddress != null ? customer.fldAddress : ""));
                $("#custEmail").val(null != origCustomer && null != origCustomer.fldEmail ? origCustomer.fldEmail : customer.fldEmail);
                $("#fldFinancialUserNo").val(customer.fldFinancialUserNo);
                $("#financialUserNo").val(customer.financialUserName);

                if (null != data[1]) {
                    origCustomer = data[1];
                    $("#custId").val(origCustomer.fldId);

                    var where = '{"op":"and","rules":[{"op":"like","field":"fldCustomerId","value":"' + origCustomer.fldId + '","type":"string"},{"op":"equal","field":"fldStatus","value":"0","type":"int"}]}';
                    $("#contractInfo").ligerGrid({
                        checkbox: false,
                        rownumbers: true,
                        delayLoad: false,
                        columnWidth: 180,
                        columns: [
                            {display: "签订日期", name: "fldSignDate"},
                            {display: "客户姓名", name: "customerName"},
                            {display: "产品全称", name: "productFullName"},
                            {display: "产品实际天数", name: "productClearDays"},
                            {display: "成立日期", name: "establishDate"},
                            {display: "到期日期", name: "dueDate"},
                            {display: "打款日期", name: "fldMoneyDate"},
                            {display: "年化收益率", name: "fldAnnualizedRate",
                                render: function (item) {
                                    return item.fldAnnualizedRate + "%";
                                }},
                            {display: "购买金额(万元)", name: "fldPurchaseMoney"},
                            {display: "预期收益(元)", name: "fldAnnualizedMoney",
                                render: function (item) {
                                    return formatCurrency(item.fldAnnualizedMoney);
                                }},
                            {display: "合同状态", name: "fldStatus",
                                render: function (item) {
                                    return renderLabel(statusData, item.fldStatus);
                                }
                            },
                            {display: "是否已到期", name: "fldFinishStatus",
                                render: function (item) {
                                    return renderLabel(finishStatus, item.fldFinishStatus);
                                }},
                            {display: "理财经理", name: "financialUserName"},
                            {display: "客服经理", name: "serviceUserName"},
                            {display: "客户经理", name: "customerUserName"},
                            {display: "银行卡号", name: "fldBankNo"},
                            {display: "瑞得卡号", name: "fldCardNo"},
                            {display: "瑞得卡等级", name: "fldCardLevel",
                                render: function (item) {
                                    return renderLabel(cardLevelData, item.fldCardLevel);
                                }
                            },
                            {display: "操作人", name: "operateUserName"},
                            {display: "操作时间", name: "fldOperateDate"}
                        ], dataAction: 'server', pageSize: 50, toolbar: null, url: '<c:url value="/customer/contract/list?where='+where+'"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
                        width: '98%', height: '33%', toJSON: JSON2.stringify
                    });
                } else {
                    var where = '{"op":"and","rules":[{"op":"like","field":"fldCustomerId","value":"-1","type":"string"},{"op":"equal","field":"fldStatus","value":"0","type":"int"}]}';
                    $("#contractInfo").ligerGrid({
                        checkbox: false,
                        rownumbers: true,
                        delayLoad: false,
                        columnWidth: 180,
                        columns: [
                            {display: "签订日期", name: "fldSignDate"},
                            {display: "客户姓名", name: "customerName"},
                            {display: "产品全称", name: "productFullName"},
                            {display: "产品实际天数", name: "productClearDays"},
                            {display: "成立日期", name: "establishDate"},
                            {display: "到期日期", name: "dueDate"},
                            {display: "打款日期", name: "fldMoneyDate"},
                            {display: "年化收益率", name: "fldAnnualizedRate",
                                render: function (item) {
                                    return item.fldAnnualizedRate + "%";
                                }},
                            {display: "购买金额(万元)", name: "fldPurchaseMoney"},
                            {display: "预期收益(元)", name: "fldAnnualizedMoney",
                                render: function (item) {
                                    return formatCurrency(item.fldAnnualizedMoney);
                                }},
                            {display: "合同状态", name: "fldStatus",
                                render: function (item) {
                                    return renderLabel(statusData, item.fldStatus);
                                }
                            },
                            {display: "是否已到期", name: "fldFinishStatus",
                                render: function (item) {
                                    return renderLabel(finishStatus, item.fldFinishStatus);
                                }},
                            {display: "理财经理", name: "financialUserName"},
                            {display: "客服经理", name: "serviceUserName"},
                            {display: "客户经理", name: "customerUserName"},
                            {display: "银行卡号", name: "fldBankNo"},
                            {display: "瑞得卡号", name: "fldCardNo"},
                            {display: "瑞得卡等级", name: "fldCardLevel",
                                render: function (item) {
                                    return renderLabel(cardLevelData, item.fldCardLevel);
                                }
                            },
                            {display: "操作人", name: "operateUserName"},
                            {display: "操作时间", name: "fldOperateDate"}
                        ], dataAction: 'server', pageSize: 50, toolbar: null, url: '<c:url value="/customer/contract/list?where='+where+'"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
                        width: '98%', height: '33%', toJSON: JSON2.stringify
                    });

                }
            }
        },
        error: function (message) {
        }
    });

    var where = encodeURI('?customerName=' + customerName + '&phone=' + phone + '&mobile=' + mobile);
    dialHistorGrid = dialHistory.ligerGrid({
        checkbox: false,
        rownumbers: true,
        delayLoad: false,
        columnWidth: 180,
        columns: [
            {display: "呼叫类型", name: "fldCallType",
                render: function (item) {
                    return renderLabel(callTypeData, item.fldCallType);
                }},
            {display: "拨打号码", name: "fldPhone",
                render: function (item) {
                    return LG.hiddenPhone(item.fldPhone);
                }},
            {display: "拨打/呼入时间", name: "fldCallDate"},
            {display: "通话时长", name: "fldCallLong"},
            {display: "通话开始时间", name: "fldCallBeginTime"},
            {display: "通话结束时间", name: "fldCallEndTime"}
        ], dataAction: 'server', pageSize: 50, toolbar: null, url: '<c:url value="/telephone/dial/dialHis'+where+'"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
        width: '98%', height: '33%', toJSON: JSON2.stringify
    });
});

function f_savecust() {
    if ("" == $("#teleCustomerName").val()) {
        LG.showError('请录入客户名称');
        return;
    }

    if ("" == $("#teleMobile").val() && "" == $("#telePhone").val()) {
        LG.showError('请录入客户手机或电话号码');
        return;
    }

    if ("" == $("#financialUserNo").val()) {
        LG.showError('请选择理财经理');
        return;
    }

    if ("" == $("#fldId").val())return;

    var telephoneCustomer = {};
    telephoneCustomer.fldId = $("#fldId").val();
    telephoneCustomer.fldCustomerName = $("#teleCustomerName").val();
    telephoneCustomer.fldGender = $("#teleGender").val();
    telephoneCustomer.fldMobile = $("#teleMobile").val();
    telephoneCustomer.fldPhone = $("#telePhone").val();
    telephoneCustomer.fldAddress = $("#teleAddress").val();
    telephoneCustomer.fldFinancialUserNo = $("#fldFinancialUserNo").val();

    var customer = {};
    customer.fldId = $("#custId").val();
    if ("" != $("#custBirthday").val()) {
        customer.fldBirthday = $("#custBirthday").val();
    }
    customer.fldIdentityNo = $("#custIdentityNo").val();
    customer.fldEmail = $("#custEmail").val();

    LG.ajax({
        url: '<c:url value="/telephone/dial/saveCust"/>',
        data: {taskId: taskId, telephoneCustomer: JSON2.stringify(telephoneCustomer), customer: JSON2.stringify(customer)},
        beforeSend: function () {

        },
        complete: function () {
        },
        success: function () {
            LG.tip("保存成功");
            taskListGrid.loadData();
        },
        error: function (message) {
            LG.showError(message);
        }
    });
}

function f_save() {
    var fldResultType = $("#fldResultType").val();
    if (undefined == fldResultType)return;
    if ("" == fldResultType) {
        LG.showError('请选择任务结果');
        return;
    }

    var fldComment = $("#fldComment").val();
    if ("" == fldComment) {
        LG.showError("请填写备注");
        return;
    }

    var fldTaskStatus = $("#fldTaskStatus").val();
    if ("" == fldTaskStatus) {
        LG.showError("请选择任务状态");
        return;
    }

    var data = {};
    data.fldTaskId = taskId;
    data.fldResultType = fldResultType;
    data.fldPhone = $("#currCallPhone").val();
    data.fldCustomerName = $("#currCallCustomerName").val();
    data.fldCallBeginTime = $("#currCallBeginTime").val();
    data.fldComment = fldComment;
    data.fldAssignDetailId = $("#fldAssignDetailId").val();
    data.callId = callId;
    data.fldTaskStatus = fldTaskStatus;

    LG.ajax({
        url: '<c:url value="/telephone/dial/save"/>',
        data: data,
        beforeSend: function () {

        },
        complete: function () {
        },
        success: function () {
            taskListGrid.loadData();
            callWin.hide();
            LG.tip("保存成功");
            dialHistorGrid.loadData();
        },
        error: function (message) {
            LG.showError(message);
        }
    });
}

var callMainform = $("#callMainform");
callMainform.ligerForm({
    labelWidth: 100,
    inputWidth: 150,
    fields: [
        {display: "拨打号码", name: "currCallPhone", newline: true, type: "hidden", attr: {readonly: "readonly"}},
        {display: "客户名称", name: "currCallCustomerName", newline: false, type: "hidden", attr: {readonly: "readonly"}},
        {display: "通话开始时间", name: "currCallBeginTime", newline: true, type: "hidden", attr: {readonly: "readonly"}, format: 'yyyy-MM-dd hh:mm:ss'},
        {display: "任务结果", name: "fldResultType", newline: true, type: "select", validate: {required: true}, comboboxName: "resultType",
            options: {
                valueField: 'value',
                textField: 'text',
                isMultiSelect: false,
                data: resultTypeData,
                valueFieldID: "fldResultType"
            }
        },
        {display: "任务状态", name: "fldTaskStatus", newline: false, type: "select", validate: {required: true}, comboboxName: "taskStatus",
            options: {
                valueField: 'value',
                textField: 'text',
                isMultiSelect: false,
                data: taskStatusData,
                initValue: '9',
                valueFieldID: "fldTaskStatus"
            }
        },
        {display: "备注", name: "fldComment", newline: true, type: "textarea", width: 400, attr: {"cols": 45}, validate: {required: true}}
    ]
});

var callWin;
function makecall(phone, customerName) {
    if (parent.LG.telephoneStatus != 0) {
        return;
    }
    parent.LG.call(phone);

    $("#fldComment").val("");

    var date = new Date();
    $("#currCallBeginTime").val(date.getFullYear() + "-" + parseInt(parseInt(date.getMonth()) + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
    $("#currCallPhone").val(phone);
    $("#currCallCustomerName").val(customerName);

    callWin = $.ligerDialog.open({
        modal: true,
        showMin: true,
        allowClose: false,
        showToggle: false,
        title: "拨打信息",
        target: $("#callDialog"),
        width: 650, height: 250, top: 30,
        buttons: [
            { text: '确定', onclick: function () {
                if(!isTongHua){
                    f_save();
                }else{
                    LG.showError("请再挂断电话之后保存拨打信息");
                }
            }
            }
        ]
    });
}

function updateGridHeight() {
    var bottomHeight = $(".l-layout-bottom");
    bottomHeight.css("height", 270);
}

updateGridHeight();

try {
    var snocx = parent.document.getElementById("snocx");
    snocx.attachEvent("snlReceiveDeliverCallEvent", function (szPhoneNumber, szPhoneParam, nCallID) {
        callId = nCallID;
        isTongHua = true;
    });

    snocx.attachEvent("snlClearCallEvent", function () {
        isTongHua = false;
    });

} catch (e) {
}
</script>
</body>
</html>