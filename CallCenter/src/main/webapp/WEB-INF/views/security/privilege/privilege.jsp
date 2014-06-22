<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<title>功能权限</title>
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
<body style=" overflow:hidden;">
<div id="layout" style="margin:2px; margin-right:3px;">
    <div position="center" id="mainmenu" title="用户角色">
        <div id="tabcontainer" style="margin:2px;">
            <div title="角色" tabid="rolelist">
                <div id="rolegrid" style="margin:2px auto;"></div>
            </div>
            <div title="用户" tabid="userlist">
                <div id="usergrid" style="margin:2px auto;"></div>
            </div>
        </div>
    </div>
    <div position="bottom" title="权限控制">
        <div id="rightgrid" style="margin:2px auto;"></div>
    </div>
</div>
<script type="text/javascript">

//覆盖本页面grid的loading效果
LG.overrideGridLoading();

var layout = $("#layout").ligerLayout({
    //5分之3的高度
    bottomHeight:1 * $(window).height() / 2,
    heightDiff:-6,
    onEndResize:updateGridHeight,
    onHeightChanged:updateGridHeight
});
var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");

var tab = $("#tabcontainer").ligerTab();

var selectedUserID, selectedRoleID;
//屏蔽系统管理员角色
var gridRoleFilter = {
    op:'and',
    rules:[
        //{ field:'id', value:'1', op:'notequal', type:'int' }
    ]
};

var userlistLoaded = false;
var gridRole, gridUser;

gridRole = $("#rolegrid").ligerGrid({
    columns:[
        { display:'角色名', name:'roleName', width:150 },
        { display:'角色描述', name:'description', width:450 }
    ], showToggleColBtn:false, width:'99%', height:200, rowHeight:20, fixedCellHeight:true,
    columnWidth:100, frozen:false, sortName:'id', usePager:false, checkbox:false, rownumbers:true,
    url:'<c:url value="/security/role/list"/>', parms:{ where:JSON2.stringify(gridRoleFilter) }
});
gridRole.bind('SelectRow', function (rowdata) {
    selectedUserID = null;
    selectedRoleID = rowdata.id;
    //隐藏禁止权限列
    gridRight.toggleCol('Forbid', false);

    bottomHeader.html("设置角色【" + rowdata.roleName + "】的权限");

    LG.ajax({
        url:'<c:url value="/security/privilege/getRolePermission"/>',
        loading:'正在加载角色权限中...',
        data:{ roleId:selectedRoleID },
        success:function (data) {
            var rows = gridRight.rows;
            for (var i = 0, l = rows.length; i < l; i++) {
                rows[i].Permit = checkPermit(rows[i], data);
            }
            gridRight.reRender();
        }
    });

    //判断是否有权限
    function checkPermit(rowdata, data) {
        if (!data || !data.length) return false;
        for (var i = 0, l = data.length; i < l; i++) {
            if (data[i].accessValue == rowdata.code)
                return true;
        }
        return false;
    }

    collapseTree();
});

//权限
var gridRight = $("#rightgrid").ligerGrid({
    columns:[
        { display:'分配权限', name:'Permit', align:'left', width:60, minWidth:60, isAllowHide:false, render:function (rowdata) {
            var iconHtml = '<div class="access-icon access-permit';
            if (rowdata.Permit) iconHtml += " access-icon-selected";
            iconHtml += '"';
            iconHtml += ' rowid = "' + rowdata['__id'] + '"';
            iconHtml += '></div>';
            return iconHtml;
        }
        },
        { display:'禁止权限', name:'Forbid', align:'left', width:60, minWidth:60, isAllowHide:false, render:function (rowdata) {
            var iconHtml = '<div class="access-icon access-forbid';
            if (rowdata.Forbid) iconHtml += " access-icon-selected";
            iconHtml += '"';
            iconHtml += ' rowid = "' + rowdata['__id'] + '"';
            iconHtml += '></div>';
            return iconHtml;
        }
        },
        { display:'菜单-按钮', name:'name', align:'left', width:200, minWidth:60 },
        { display:'编码', name:'code', align:'left', width:200, minWidth:60 },
        { display:'图标', name:'icon', align:'left', width:100, minWidth:60,
            render:function (item) {
                return "<div style='width:100%;height:100%;'><img src='" + GLOBAL_CTX + item.icon + "' /></div>";
            } }
    ], showToggleColBtn:false, width:'99%', height:200, rowHeight:20, fixedCellHeight:true,
    columnWidth:100, frozen:false, usePager:false, checkbox:false, rownumbers:true, toolbar:{},
    tree:{ columnName:'name' },
    data:[], onAfterShowData:function (grid) {
        collapseTree();
    }
});

LG.loadToolbar(gridRight, f_save);

//隐藏禁止权限列
gridRight.toggleCol('Forbid', false);

//分配权限、禁止权限按钮的事件
$("div.access-icon").live('click', function () {
    var selected = !$(this).hasClass("access-icon-selected");
    var ispermit = $(this).hasClass("access-permit");
    var rowid = $(this).attr("rowid");
    var rowdata = gridRight.records[rowid];
    if (ispermit) {
        f_Permit(rowdata, selected);
    }
    else {
        f_Forbid(rowdata, selected);
    }
});

//为当前选择记录 分配权限
//1,同时分配给下级记录
function f_Permit(rowdata, selected) {
    selected = selected ? true : false;
    rowdata.Permit = rowdata.Permit ? true : false;
    if (rowdata.Permit == selected) return;
    rowdata.Permit = selected;
    var children = gridRight.getChildren(rowdata);
    if (children) {
        for (var i = 0, l = children.length; i < l; i++) {
            f_Permit(children[i], selected);
        }
    }
    gridRight.reRender({ rowdata:rowdata });
}
//为当前选择记录 禁止权限
//1,同时对下级记录做禁止权限操作
function f_Forbid(rowdata, selected) {
    selected = selected ? true : false;
    rowdata.Forbid = rowdata.Forbid ? true : false;
    if (rowdata.Forbid == selected) return;
    rowdata.Forbid = selected;
    //if (selected) {
    var children = gridRight.getChildren(rowdata);
    if (children) {
        for (var i = 0, l = children.length; i < l; i++) {
            f_Forbid(children[i], selected);
        }
    }
    //}
    gridRight.reRender({ rowdata:rowdata });
}


//加载 菜单-按钮数据
LG.ajax({
    url:'<c:url value="/security/node/treeGrid"/>',
    loading:'正在加载菜单按钮中...',
    success:function (data) {
        gridRight.set('data', { Rows:data });
    }
});


function collapseTree() {
    var rows = gridRight.rows;
    for (var i = 0, l = rows.length; i < l; i++) {
        gridRight.collapse(gridRight.getRowObj(i));
    }
}

//角色,在tab选择的时候才加载表格和表格数据
tab.bind('afterSelectTabItem', function (tabid) {
    if (tabid != "userlist" || userlistLoaded) return;
    userlistLoaded = true;
    //用户
    gridUser = $("#usergrid").ligerGrid({
        columns:[
            { display:'登陆名称', name:'loginName', width:200, minWidth:60 },
            { display:'用户名称', name:'userName', width:200, minWidth:60 },
            { display:"部门", name:"deptName", type:'text', width:180 },
            { display:"用户状态", name:"userStatus", width:100,
                render:function (item) {
                    if (parseInt(item.userStatus) == 0) return '正常';
                    return item.userStatus;
                }}
        ], showToggleColBtn:false, width:'99%', height:200, rowHeight:20, fixedCellHeight:true,
        columnWidth:100, frozen:false, sortName:'id', usePager:false, checkbox:false, rownumbers:true,
        url:'<c:url value="/security/user/list"/>', parms:{}
    });
    gridUser.bind('SelectRow', function (rowdata) {
        selectedUserID = rowdata.id;
        selectedRoleID = null;
        //显示禁止权限列
        gridRight.toggleCol('Forbid', true);

        bottomHeader.html("给用户【" + rowdata.loginName + "】 单独设置权限");

        LG.ajax({
            url:'<c:url value="/security/privilege/getUserPermission"/>',
            loading:'正在加载用户权限中...',
            data:{ userId:selectedUserID },
            success:function (data) {
                var rows = gridRight.rows;
                for (var i = 0, l = rows.length; i < l; i++) {
                    var hasPermit = checkPermit(rows[i], data);
                    if (hasPermit) {
                        rows[i].Permit = isPermit;
                        rows[i].Forbid = !isPermit;
                    } else {
                        rows[i].Permit = rows[i].Forbid = false;
                    }
                }
                gridRight.reRender();
            }
        });

        //是否 分配权限 ，或者是禁止权限
        var isPermit;

        //判断是否有权限控制(按钮的，或菜单的)
        function checkPermit(rowdata, data) {
            if (!data || !data.length) return false;
            for (var i = 0, l = data.length; i < l; i++) {
                if (data[i].accessValue == rowdata.code) {
                    isPermit = data[i].operation == 1 ? true : false;
                    return true;
                }
            }
            return false;
        }
    });

    collapseTree();
    updateGridHeight();
});


function f_save() {
    if (!selectedRoleID && !selectedUserID) return;
    var data = [];
    for (var i = 0, l = gridRight.rows.length; i < l; i++) {
        var o = {};
        o["code"] = gridRight.rows[i].code;
        o["permit"] = gridRight.rows[i].Permit ? true : false;
        o["forbid"] = gridRight.rows[i].Forbid ? true : false;
        data.push(o);
    }
    LG.ajax({
        url:selectedRoleID ? '<c:url value="/security/privilege/saveRolePrivilege"/>' : '<c:url value="/security/privilege/saveUserPrivilege"/>',
        loading:'正在保存权限设置中...',
        data:{
            privilegeData:JSON2.stringify(data),
            roleId:selectedRoleID,
            userId:selectedUserID
        },
        success:function () {
            LG.showSuccess("保存成功!");
        },
        error:function (message) {
            LG.showError(message);
        }
    });
}

function updateGridHeight() {
    var topHeight = $("#layout > .l-layout-center").height();
    var bottomHeight = $("#layout > .l-layout-bottom").height();
    if (gridUser)
        gridUser.set('height', topHeight - 65);
    if (gridRole)
        gridRole.set('height', topHeight - 65);
    if (gridRight)
        gridRight.set('height', bottomHeight - 35);
}
updateGridHeight();
</script>
</body>
</html>
