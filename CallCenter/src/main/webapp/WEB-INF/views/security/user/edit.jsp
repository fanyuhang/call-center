<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<title>用户 明细</title>
<script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerForm.js"/>" type="text/javascript"></script>
</head>
<body style="padding-bottom:31px;">
<form id="mainform" method="post"></form>
<script type="text/javascript">
    var typeData = <sys:dictList type="19"/>;
    var phoneTypeData = <sys:dictList type="20"/>;
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);

    var roleids = '${roleIds}';

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth:280,
        fields:[
            {name:"id", type:"hidden", attr:{value:"${user.id}"}},
            {name:"loginStatus", type:"hidden", attr:{value:"${user.loginStatus}"}},
            {name:"userStatus", type:"hidden", attr:{value:"${user.userStatus}"}},
            {name:"password", type:"hidden", attr:{value:"${user.password}"}},
            {display:"登陆名称", name:"loginName", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{readonly:"readonly", value:"${user.loginName}"}, validate:{required:true, maxlength:30}, group:"用户基本信息", groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"用户名称", name:"userName", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.userName}"}, validate:{required:true, maxlength:30}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"性别", name:"gender", newline:true, labelWidth:100, width:220, space:30, type:"select", comboboxName:"genderName", options:{
                valueFieldID:"gender",
                valueField:"value",
                textField:"text",
                initValue:'${user.gender}',
                data:<sys:dictList type="1" />
            }, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"部门", name:"deptCode", newline:false, labelWidth:100, width:220, space:30, type:"select", comboboxName:"deptName", options:{
                valueFieldID:"deptCode"}},
            {display:"角色", name:"roleIds", newline:true, labelWidth:100, width:220, space:30, type:"select", attr:{value:"${roleIds}"}, comboboxName:"roleName", options:{
                valueFieldID:"roleIds",
                valueField:"id",
                textField:"roleName",
                width:220,
                url:"<c:url value="/security/select/selectRole"/>",
                isMultiSelect:true,
                split:','
            }},
            {display:"分机号", name:"phoneExtension", newline:true, labelWidth:100, width:220, space:30, type:"text",attr:{value:"${user.phoneExtension}"}, validate:{maxlength:32}, group:"话务信息", groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"职位类型", name:"type", newline:true, labelWidth:100, width:220, space:30, type:"select", comboboxName:"typeName", options:{
                valueFieldID:"type",
                valueField:"value",
                textField:"text",
                initValue:"${user.type}",
                data:typeData
            }, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"话务类型", name:"phoneType", newline:false, labelWidth:100, width:220, space:30, type:"select", comboboxName:"phoneTypeName", options:{
                valueFieldID:"phoneType",
                valueField:"value",
                textField:"text",
                initValue:"${user.phoneType}",
                data:phoneTypeData
            }, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"邮箱", name:"email", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.email}"}, validate:{maxlength:60, email:true}, group:"联系信息", groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"地址", name:"address", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.address}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"电话", name:"phone", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.phone}"}, validate:{maxlength:24, telephone:true}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"手机", name:"mobile", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.mobile}"}, validate:{maxlength:24, cellphone:true}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"传真", name:"fax", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.fax}"}, validate:{maxlength:15}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"}
        ],
        toJSON:JSON2.stringify
    });

    $("#roleIds").val(roleids);

    $.ligerui.get("deptName").openSelect({
        grid:{
            columns:[
                {display:"部门编码", name:"deptCode", width:352},
                {display:"部门名称", name:"deptName", width:420}
            ], pageSize:20, heightDiff:-10,
            url:'<c:url value="/security/dept/listDept"/>', sortName:'deptCode', checkbox:false
        },
        search:{
            fields:[
                {display:"部门编码", name:"deptCode", newline:true, type:"text", cssClass:"field"}
            ]
        },
        valueField:'deptCode', textField:'deptName', top:30
    });

    $.ligerui.get("deptName")._changeValue('${userDept.deptCode}', '${userDept.dept.deptName}');

    var actionRoot = "<c:url value="/security/user/update"/>";
    mainform.attr("action", actionRoot);

    //验证

    LG.validate(mainform);

    function f_loaded() {
        return;
    }

    function f_save() {
        LG.submitForm(mainform, function (data) {
            var win = parent || window;
            if (data.IsError) {
                win.LG.showError('错误:' + data.Message);
            }
            else {
                win.LG.showSuccess('保存成功', function () {
                    win.LG.closeAndReloadParent(null, '${menuNo}');
                });
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

