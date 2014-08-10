<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<title>用户</title>
<script src='<c:url value="/static/ligerUI/js/changepassword.js"/>' type="text/javascript"></script>
</head>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="mainsearch" style=" width:98%">
    <div class="searchtitle">
        <span>搜索</span><img src="<c:url value="/static/ligerUI/icons/32X32/searchtool.gif"/>"/>

        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom:4px; margin-top:4px;"></div>
    <div class="searchbox">
        <form id="formsearch" class="l-form"></form>
    </div>
</div>
<div id="maingrid"></div>
<div id="syncDetail" style="display:none;"><form:form id="syncDetailForm" method="post"></form:form></div>
<script type="text/javascript">
var loginStatusData = <sys:dictList type="3"/>;
var userStatusData = <sys:dictList type="2" nullable="true"/>;
var genderStatusData = <sys:dictList type="1"/>;
var statusSystemData = <sys:dictList type="81" nullable="true" nullValue=""/>;
var typeData = <sys:dictList type="19" nullable="true"/>;
var phoneTypeData = <sys:dictList type="20" nullable="true"/>;
//列表结构
var grid = $("#maingrid").ligerGrid({
    columns:[
        { display:"登陆名称", name:"loginName", width:180 },
        { display:"用户名称", name:"userName", width:180 },
        { display:"分机号", name:"phoneExtension", width:180 },
        { display:"登陆状态", name:"loginStatus", width:100,
            render:function (item) {
                for (var i = 0; i < loginStatusData.length; i++) {
                    if (loginStatusData[i].value == item.loginStatus)
                        return loginStatusData[i].text;
                }
            }},
        { display:"用户状态", name:"userStatus", width:100,
            render:function (item) {
                return renderLabel(userStatusData, item.userStatus);
            }},
        { display:"职位类型", name:"type", width:100,
            render:function (item) {
                return renderLabel(typeData, item.type);
            }},
        { display:"话务类型", name:"phoneType", width:100,
            render:function (item) {
                return renderLabel(phoneTypeData, item.phoneType);
            }},
        { display:"部门", name:"deptName", type:'text', width:180 },
        { display:"用户组", name:"roleNames", type:'text', width:180 },
        { display:"性别", name:"gender", type:'date', width:100,
            render:function (item) {
                for (var i = 0; i < genderStatusData.length; i++) {
                    if (genderStatusData[i].value == item.gender)
                        return genderStatusData[i].text;
                }
            } },
        { display:"邮箱", name:"email", type:'date', width:180 },
        { display:"电话", name:"phone", width:180 },
        { display:"手机", name:"mobile", width:180 },
        { display:"地址", name:"address", width:180 }
    ], dataAction:'server', pageSize:20, toolbar:{},
    url:'<c:url value="/security/user/list"/>', sortName:'operateDate', sortOrder:'desc',
    width:'98%', height:'100%', heightDiff:-10, checkbox:false
});

//搜索表单应用ligerui样式
$("#formsearch").ligerForm({
    labelWidth:100, inputWidth:150, space:30,
    fields:[
        {display:"登陆名称", name:"loginName", newline:false,  type:"text", cssClass:"field"
        },
        { display:"用户名称", name:"userName", newline:false,  type:"text", cssClass:"field"
        },
        { display:"部门名称", name:"userDept.dept.deptName", newline:false,  type:"text", cssClass:"field"
        },
        {display:"用户状态", name:"userStatus", cssClass:"field", newline:true,attr:{"op":'equal', "vt":"int"},   type:"select",
            comboboxName:"userStatusName", options:{
            valueFieldID:"userStatus", valueField:"value", textField:"text",
            data:userStatusData
        }},
        {display:"职位类型", name:"type", cssClass:"field", newline:false,   type:"select",
            comboboxName:"typeName", options:{
            valueFieldID:"type", valueField:"value", textField:"text",
            data:typeData
        }},
        {display:"话务类型", name:"phoneType", cssClass:"field", newline:false,attr:{"op":'equal', "vt":"int"},   type:"select",
            comboboxName:"phoneTypeName", options:{
            valueFieldID:"phoneType", valueField:"value", textField:"text",
            data:phoneTypeData
        }}
    ],
    toJSON:JSON2.stringify
});

//增加搜索按钮,并创建事件
LG.appendSearchButtons("#formsearch", grid, true, false);

//加载toolbar
LG.loadToolbar(grid, toolbarBtnItemClick);

//工具条事件
function toolbarBtnItemClick(item) {
    switch (item.id) {
        case "add":
            top.f_addTab(null, '增加用户信息', '<c:url value="/security/user/add?menuNo=${menuNo}"/>');
            break;
        case "view":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行!');
                return;
            }
            top.f_addTab(null, '查看用户信息', '<c:url value="/security/user/view?id="/>' + selected.id);
            break;
        case "modify":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行!');
                return;
            }
            top.f_addTab(null, '修改用户信息', '<c:url value="/security/user/edit/?menuNo=${menuNo}&id="/>' + selected.id);
            break;
        case "delete":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行!');
                return;
            }
            jQuery.ligerDialog.confirm('确定删除吗?', function (confirm) {
                if (confirm)
                    f_delete();
            });
            break;
        case "reset":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行!');
                return;
            }
            LG.resetpassword(selected.id);
            break;
        case "active":
            f_active();
            break;
        case "unlock":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行!');
                return;
            }
            f_unlock();
            break;
        case "signoff":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行!');
                return;
            }
            f_signoff();
            break;
        case "cancel":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行!');
                return;
            }
            f_cancel();
            break;
        case "sync":
            f_sync();
            break;
    }
}
function f_reload() {
    grid.loadData();
}
function f_delete() {
    var selected = grid.getSelected();
    if (selected) {
        LG.ajax({
            url:"<c:url value="/security/user/delete"/>",
            loading:'正在删除中...',
            data:{ id:selected.id },
            success:function () {
                LG.showSuccess('删除成功');
                f_reload();
            },
            error:function (message) {
                LG.showError(message);
            }
        });
    }
    else {
        LG.tip('请选择行!');
    }
}
function f_active() {
    LG.ajax({
        url:'<c:url value="/security/user/getCertCN"/>',
        success:function (cn) {
            $.ligerDialog.confirm('确定激活CN=' + cn[0] + "的USB Key吗？", function (yes) {
                if (yes) {
                    LG.ajax({
                        url:"<c:url value="/security/user/activeCert"/>",
                        loading:'正在激活...',
                        data:{ certCN:cn[0] },
                        success:function () {
                            LG.showSuccess('激活成功');
                            f_reload();
                        },
                        error:function (message) {
                            LG.showError(message);
                        }
                    });
                }
            });
        },
        error:function () {
            LG.tip('读取证书信息失败');
        }
    });
}
function f_unlock() {
    var selected = grid.getSelected();
    if (selected) {
        LG.ajax({
            url:"<c:url value="/security/user/unlock"/>",
            loading:'正在解锁中...',
            data:{ id:selected.id },
            success:function () {
                LG.showSuccess('解锁成功');
                f_reload();
            },
            error:function (message) {
                LG.showError(message);
            }
        });
    }
    else {
        LG.tip('请选择行!');
    }
}
function f_signoff() {
    var selected = grid.getSelected();
    if (selected) {
        LG.ajax({
            url:'<c:url value="/security/user/signoff"/>',
            loading:'正在签退中...',
            data:{ id:selected.id },
            success:function () {
                LG.showSuccess('签退成功');
                f_reload();
            },
            error:function (message) {
                LG.showError(message);
            }
        });
    }
    else {
        LG.tip('请选择行!');
    }
}
function f_cancel() {
    var selected = grid.getSelected();
    if (selected) {
        LG.ajax({
            url:'<c:url value="/security/user/cancel"/>',
            loading:'正在注销中...',
            data:{ id:selected.id },
            success:function () {
                LG.showSuccess('注销成功');
                f_reload();
            },
            error:function (message) {
                LG.showError(message);
            }
        });
    }
    else {
        LG.tip('请选择行!');
    }
}

var syncDetailWin = null;
function f_sync(){
    if (syncDetailWin) {
        syncDetailWin.show();
    }
    else {
        //创建表单结构
        var mainform = $("#syncDetailForm");
        mainform.ligerForm({
            inputWidth:120,
            labelWidth:110,
            space:30,
            fields:[
                {display: "同步系统",name:"systemType", newline:false, type:"select",
                    comboboxName:"systemTypeName", options:{
                    valueFieldID:"systemTypeStatus", valueField:"value", textField:"text",
                    data:statusSystemData
                }, cssClass:"field", attr:{"op":"equal", "vt":"int"}
                },
                { display:'操作开始日期', name:'syncStartDate', newline:true, align:'left', type:'date', validate:{required:true, date:true}, format:'yyyy-MM-dd', showTime:false},
                { display:'结束日期', name:'syncEndDate', newline:true, align:'left', type:'date', validate:{required:true, date:true}, format:'yyyy-MM-dd', showTime:false}
            ]
        });

        syncDetailWin = $.ligerDialog.open({
            title:'订单同步',
            target:$("#syncDetail"),
            width:400, height:180, top:90,
            buttons:[
                { text:'确定', onclick:function () {
                    syncDetailWin.hide();
                    save();
                } },
                { text:'取消', onclick:function () {
                    syncDetailWin.hide();
                } }
            ]
        });
    }

    function save() {

        var url = '<c:url value="/user/user_33/sync"/>';

        var systemType = $("#systemType").val();

        if(systemType=='1'){
            url = '<c:url value="/user/user_33/sync"/>';
        }else if(systemType=='2'){
            url = '<c:url value="/user/user_97/sync"/>';
        }else if(systemType=='3'){
            url = '<c:url value="/user/user_11/sync"/>';
        }

        LG.ajax({
            loading:'正在同步数据中...',
            url:url,
            data:{startDate:$("#syncStartDate").val(),endDate: $("#syncEndDate").val()},
            success:function () {
                syncDetailWin.hide();
                LG.tip('同步成功!');
                f_reload();
            },
            error:function (message) {
                LG.tip(message);
            }
        });
    }
}

</script>
</body>
</html>
