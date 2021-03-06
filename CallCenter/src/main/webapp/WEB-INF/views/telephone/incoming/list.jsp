<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<title>来电弹屏</title>
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
    <div position="center" id="mainmenu" title="客户信息">
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

var genderData = <sys:dictList type = "1"/>;
var resultTypeData = <sys:dictList type = "27"/>;
var statusData = <sys:dictList type = "8"/>;
var finishStatus = <sys:dictList type = "21" nullable="true"/>;
var cardLevelData = <sys:dictList type = "13"/>;
var callTypeData = <sys:dictList type = "28"/>;
var callStatusData = <sys:dictList type = "23"/>;

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

taskListGrid = $("#tasklist").ligerGrid({
    columns: [
        {display: '客户姓名', name: 'fldName', width: 350},
        {display: '性别', name: 'fldGender', width: 60,
            render: function (item) {
                return renderLabel(genderData, item.fldGender);
            }
        },
        {display: '手机', name: 'fldMobile', width: 250,
            render: function (item) {
                return LG.hiddenPhone(item.fldMobile);
            }},
        {display: '固定电话', name: 'fldPhone', width: 250,
            render: function (item) {
                return LG.hiddenPhone(item.fldPhone);
            }},
        {display: '来电反馈', width: 250, render: function (item) {
            return '<a href="javascript:void(0);" onclick="javascript:incomingcall(\'' + item.fldName + '\');" title="反馈">反馈</a>';
        }}
    ],
    width: '99%', height: 190, rowHeight: 20, fixedCellHeight: true,
    frozen: false, checkbox: false, rownumbers: true,
    url: '<c:url value="/telephone/incoming/listCustomer"/>?num=' + '${phone}'
});

var customeForm;
customeForm = $("#customerInfo").ligerForm({
    labelWidth: 80,
    inputWidth: 150,
    space: 30,
    heightDiff: -100,
    fields: [
        {display: "ID", name: "fldId", newline: true, type: "hidden"},
        {display: "客户名称", name: "custName", newline: true, type: "text", validate: {required: true}, cssClass: "field"},
        {display: "性别", name: "custGender", newline: false, type: "select", cssClass: "field", comboboxName: "gender",
            options: {
                valueField: 'value',
                textField: 'text',
                isMultiSelect: false,
                data: genderData,
                valueFieldID: "custGender"
            }
        },
        {display: "手机", name: "custMobile", newline: true, type: "text", cssClass: "field"},
        {display: "电话号码", name: "custPhone", newline: false, type: "text", cssClass: "field"},
        {display: "出生日期", name: "custBirthday", newline: true, type: "date", cssClass: "field"},
        {display: "身份证号", name: "custIdentityNo", newline: false, type: "text", cssClass: "field"},
        {display: "地址", name: "custAddress", newline: true, type: "text", cssClass: "field"},
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
var dialHistoryGrid = dialHistory.ligerGrid({
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
        {display: "通话时长", name: "fldCallLong"},
        {display: "通话开始时间", name: "fldCallBeginTime"},
        {display: "通话结束时间", name: "fldCallEndTime"}
    ], dataAction: 'server', pageSize: 50, toolbar: null, url: null,
    width: '98%', height: '33%', toJSON: JSON2.stringify
});

taskListGrid.bind('SelectRow', function (rowdata) {
    var customerName = rowdata.fldName;
    var mobile = rowdata.fldMobile;
    var phone = rowdata.fldPhone;

    LG.ajax({
        url: '<c:url value="/telephone/incoming/findCustomer"/>',
        data: {customerName: customerName, mobile: mobile, phone: phone},
        dataType: 'json', type: 'post',
        success: function (data) {
            if (null != data) {
                var customer = data[0];
                var origCustomer;
                if (null != data[1]) {
                    origCustomer = data[1];
                }

                if (customer) {
                    $("#fldId").val(customer.fldId);
                    $("#custName").val(customer.fldName);
                    $("#custGender").val(customer.fldGender);
                    $("#gender").val(renderLabel(genderData, customer.fldGender));
                    $("#custMobile").val(null != customer.fldMobile ? customer.fldMobile : "");
                    $("#custPhone").val(null != customer.fldPhone ? customer.fldPhone : "");
                    $("#custBirthday").val(null != customer.fldBirthday ? customer.fldBirthday : "");
                    $("#custIdentityNo").val(null != customer.fldIdentityNo ? customer.fldIdentityNo : "");
                    $("#custAddress").val(null != customer.fldAddress ? customer.fldAddress : "");
                    $("#custEmail").val(null != customer.fldEmail ? customer.fldEmail : "");
                    $("#fldFinancialUserNo").val(customer.fldFinancialUserNo);
                    $("#financialUserNo").val(customer.financialUserName);
                } else if (origCustomer) {
                    $("#custId").val(origCustomer.fldId);
                    $("#custName").val(origCustomer.fldCustomerName);
                    $("#custGender").val(origCustomer.fldGender);
                    $("#gender").val(renderLabel(genderData, origCustomer.fldGender));
                    $("#custMobile").val(null != origCustomer.fldMobile ? origCustomer.fldMobile : "");
                    $("#custPhone").val(null != origCustomer.fldPhone ? origCustomer.fldPhone : "");
                    $("#custBirthday").val(null != origCustomer.fldBirthday ? origCustomer.fldBirthday : "");
                    $("#custIdentityNo").val(null != origCustomer.fldIdentityNo ? origCustomer.fldIdentityNo : "");
                    $("#custAddress").val(null != origCustomer.fldAddress ? origCustomer.fldAddress : "");
                    $("#custEmail").val(null != origCustomer.fldEmail ? origCustomer.fldEmail : "");
                    $("#fldFinancialUserNo").val(origCustomer.fldFinancialUserNo);
                    $("#financialUserNo").val(origCustomer.financialUserName);
                }

                if (null != customer) {
                    var where = '{"op":"and","rules":[{"op":"like","field":"fldCustomerId","value":"' + customer.fldId + '","type":"string"},{"op":"equal","field":"fldStatus","value":"0","type":"int"}]}';
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
    dialHistory.ligerGrid({
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
            {display: "通话时长(秒)", name: "fldCallLong"},
            {display: "通话开始时间", name: "fldCallBeginTime"},
            {display: "通话结束时间", name: "fldCallEndTime"}
        ], dataAction: 'server', pageSize: 50, toolbar: null, url: '<c:url value="/telephone/dial/dialHis'+where+'"/>', sortName: 'fldOperateDate', sortOrder: 'desc',
        width: '98%', height: '33%', toJSON: JSON2.stringify
    });
});

function f_savecust() {
    if ("" == $("#custName").val()) {
        LG.showError('请录入客户名称');
        return;
    }

    if ("" == $("#custMobile").val() && "" == $("#custPhone").val()) {
        LG.showError('请录入客户手机或电话号码');
        return;
    }

    if ("" == $("#financialUserNo").val()) {
        LG.showError('请选择理财经理');
        return;
    }

    var customer = {};
    customer.fldId = $("#fldId").val();
    customer.fldName = $("#custName").val();
    customer.fldGender = $("#custGender").val();
    customer.fldMobile = $("#custMobile").val();
    customer.fldPhone = $("#custPhone").val();
    customer.fldAddress = $("#custAddress").val();

    if ("" != $("#custBirthday").val()) {
        customer.fldBirthday = $("#custBirthday").val();
    }
    customer.fldIdentityNo = $("#custIdentityNo").val();
    customer.fldEmail = $("#custEmail").val();

    if ("" == $("#custId").val())return;

    var telephoneCustomerId = $("#custId").val();

    LG.ajax({
        url: '<c:url value="/telephone/incoming/saveCust"/>',
        data: {customer: JSON2.stringify(customer), telephoneCustomerId: telephoneCustomerId, callId: "${callId}"},
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
        LG.showError('请选择通话内容');
        return;
    }

    var data = {};
    data.fldResultType = fldResultType;
    data.fldPhone = $("#currCallPhone").val();
    data.fldCustomerName = $("#currCallCustomerName").val();
    data.fldComment = $("#fldComment").val();
    //data.fldCallDate = $("#currCallBeginTime").val();
    data.callId = $("#callId").val();

    LG.ajax({
        url: '<c:url value="/telephone/incoming/save"/>',
        data: data,
        beforeSend: function () {

        },
        complete: function () {
        },
        success: function () {
            dialHistoryGrid.loadData();
            callWin.hide();
            LG.tip("保存成功");
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
        {display: "callId", name: "callId", newline: true, type: "hidden", attr: {value: "${callId}"}},
        {display: "来电号码", name: "currCallPhone", newline: true, type: "text", attr: {readonly: "readonly", value: "${phone}"}},
        {display: "客户名称", name: "currCallCustomerName", newline: false, type: "text", attr: {readonly: "readonly"}},
        {display: "来电时间", name: "currCallBeginTime", newline: true, type: "text", attr: {readonly: "readonly"}, format: 'yyyy-MM-dd hh:mm:ss'},
        {display: "通话内容", name: "fldResultType", newline: true, type: "select", validate: {required: true},
            options: {
                valueField: 'value',
                textField: 'text',
                isMultiSelect: false,
                data: resultTypeData,
                valueFieldID: "fldResultType"
            }
        },
        {display: "备注", name: "fldComment", newline: true, type: "textarea", width: 400, attr: {"cols": 64}, validate: {required: true}}
    ]
});

var callWin;
function incomingcall(customerName) {
    LG.ajax({
        url: '<c:url value="/telephone/incoming/findCall"/>?callId=${callId}',
        beforeSend: function () {

        },
        complete: function () {
        },
        success: function (data) {
            if (null != data && null != data[0]) {
                var calllog = data[0];
                $("#currCallBeginTime").val(calllog.inboundCallTime);
            }
        },
        error: function (message) {

        }
    });

    $("#currCallPhone").val('${phone}');
    $("#currCallCustomerName").val(customerName);

    callWin = $.ligerDialog.open({
        allowClose: true,
        title: "来电信息",
        target: $("#callDialog"),
        width: 650, height: 280, top: 80,
        buttons: [
            { text: '确定', onclick: function () {
                f_save();
            }
            },
            { text: '取消', onclick: function () {
                callWin.hide();
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
</script>
</body>
</html>