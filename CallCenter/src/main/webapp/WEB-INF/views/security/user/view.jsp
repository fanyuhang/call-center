<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<title>用户 明细</title>
</head>
<body style="padding-bottom:31px;">
<form id="mainform" method="post"></form>
<script type="text/javascript">
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, null);

    var roleids = '${roleIds}';

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth:280,
        fields:[
            {name:"id", type:"hidden"},
            {display:"登陆名称", name:"loginName", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.loginName}"}, group:"用户基本信息", groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"用户名称", name:"userName", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.userName}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"登陆状态", name:"loginStatus", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:<sys:dictName type="3" value="${user.loginStatus}"/>}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"用户状态", name:"userStatus", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:<sys:dictName type="2" value="${user.userStatus}"/>}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"登陆失败次数", name:"loginErrCount", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.loginErrCount}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"修改密码次数", name:"modifyPwdCount", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.modifyPwdCount}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"性别", name:"gender", newline:true, labelWidth:100, width:220, space:30, type:"select", comboboxName:"genderName", options:{
                valueFieldID:"gender",
                valueField:"value",
                textField:"text",
                initValue:'${user.gender}',
                disabled:true,
                data:<sys:dictList type="1" />
            }, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"部门", name:"deptCode", newline:false, attr:{value:"${userDept.dept.deptName}"}, labelWidth:100, width:220, space:30},
            {display:"角色", name:"roleIds", newline:true, labelWidth:100, width:220, space:30, type:"select", attr:{value:"${roleIds}"}, comboboxName:"roleName", options:{
                valueFieldID:"roleIds",
                valueField:"id",
                textField:"roleName",
                width:220,
                url:"<c:url value="/security/select/selectRole"/>",
                isMultiSelect:true,
                split:',',
                disabled:true
            }},
            {display:"邮箱", name:"email", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.email}"}, group:"联系信息", groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"地址", name:"address", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.address}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"电话", name:"phone", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.phone}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"手机", name:"mobile", newline:false, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.mobile}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
            {display:"传真", name:"fax", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{value:"${user.fax}"}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"}

        ],
        toJSON:JSON2.stringify
    });

    $("#roleIds").val(roleids);

    function f_loaded() {
        //查看状态，控制不能编辑
        $("input,select,textarea", mainform).attr("readonly", "readonly");
    }
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
    f_loaded();
</script>
</body>
</html>

