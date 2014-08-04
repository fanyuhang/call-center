<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="customer"></form:form>
<script type="text/javascript">
    var genderData =<sys:dictList type = "1"/>;
    var sourceData =<sys:dictList type = "10"/>;
    var cardLevelData =<sys:dictList type = "13" nullable="true"/>;

    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
            {display: "ID", name: "fldId", type: "hidden", attr: {value: "${customer.fldId}"}},
            {display: "理财经理", name: "fldFinancialUserNoText", attr: {value: '${customer.financialUserName}', readonly: "readonly"}, newline: true, type: "text", group: "<label style=white-space:nowrap;>原归属信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "客户经理", name: "fldCustomerUserNo", attr: {value: '${customer.customerUserName}', readonly: "readonly"}, newline: false, type: "text"},
            {display: "客服经理", name: "fldServiceUserNo", attr: {value: '${customer.serviceUserName}', readonly: "readonly"}, newline: true, type: "text"},
            {display: "新客服经理", name: "newServiceUserNo", newline: false, type: "select", validate: {required: true},
                comboboxName: "newServiceUser", options: {valueFieldID: "newServiceUser"}, group: "<label style=white-space:nowrap;>交接信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "备注", name: "fldComment", newline: true, width: 630, type: "text", attr: {value: "${customer.fldComment}"}, readonly: "readonly"}
        ]
    });

    $.ligerui.get("newServiceUser").openSelect({
        grid: {
            columnWidth: 255,
            columns: [
                {display: "用户名称", name: "userName"},
                {display: "登录名称", name: "loginName"},
                {display: "部门", name: "deptName"}
            ], pageSize: 20, heightDiff: -10,
            url: '<c:url value="/security/user/list"/>', sortName: 'userName', checkbox: false
        },
        search: {
            fields: [
                {display: "用户名称", name: "userName", newline: true, type: "text", cssClass: "field"}
            ]
        },
        valueField: 'loginName', textField: 'userName', top: 30
    });

    mainform.attr("action", '<c:url value="/customer/customer/updateFinancialUser"/>');

    function f_save() {
        LG.validate(mainform);

        var newServiceUser = $("#newServiceUser").val();
        if (!newServiceUser) {
            LG.showError("请选择新客服");
            return;
        }

        LG.submitForm(mainform, function (data) {
            var win = parent || window;
            if (data.IsError == false) {
                win.LG.showSuccess(data.Message, function () {
                    win.LG.closeAndReloadParent(null, "${menuNo}");
                });
            }
            else {
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