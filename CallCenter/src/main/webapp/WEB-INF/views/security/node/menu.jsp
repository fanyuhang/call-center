<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<title>模块管理</title>
<script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerDialog.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerTextBox.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/js/iconselector.js"/>" type="text/javascript"></script>
<style type="text/css">
    .l-panel td.l-grid-row-cell-editing {
        padding-bottom: 2px;
        padding-top: 2px;
    }
</style>
</head>
<body style="padding:2px;height:100%; text-align:center;">

<div id="layout">
    <div position="left" title="主菜单模块" id="mainmenu">
        <ul id="maintree"></ul>
    </div>
    <div position="center" title="子菜单列表">
        <form id="mainform">
            <div id="maingrid" style="margin:2px;"></div>
        </form>
    </div>
</div>
<ul class="iconlist">
</ul>
<script type="text/javascript">
//验证
var maingform = $("#mainform");

LG.validate(maingform, { debug:true });
//覆盖本页面grid的loading效果
LG.overrideGridLoading();

function toolbarBtnItemClick(item) {
    var parentRow = tree.getSelected();
    if (!parentRow) {
        LG.tip('请选择父节点');
        return;
    }

    var editingrow = grid.getEditingRow();
    switch (item.id) {
        case "add":
            if (editingrow == null) {
                addNewRow(parentRow.data.code);
            } else {
                LG.tip('请先提交或取消修改');
            }
            break;
        case "modify":
            if (editingrow == null) {
                beginEdit();
            } else {
                LG.tip('请先提交或取消修改');
            }
            break;
        case "save":
            if (editingrow != null) {
                grid.endEdit(editingrow);
            } else {
                LG.tip('现在不在编辑状态!');
            }
            break;
        case "cancel":
            if (editingrow != null) {
                grid.cancelEdit(editingrow);
            } else {
                LG.tip('现在不在编辑状态!');
            }
            break;
        case "delete":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行');
                return;
            }
            $.ligerDialog.confirm('确定删除吗?', function (confirm) {
                if (confirm)
                    f_delete();
            });
            break;
        case "button":
            var selected = grid.getSelected();
            if (!selected) {
                LG.tip('请选择行');
                return;
            }
            if (parentRow.data.code == '01') {
                LG.tip('一级菜单下不能添加按钮');
                return;
            }
            top.f_addTab(null, selected.name + ' 操作按钮管理', '<c:url value="/security/node/initButtons?parent="/>' + selected.code);
            break;
    }
}
function f_reload() {
    grid.loadData();
}
function f_delete() {
    var selected = grid.getSelected();
    if (selected) {
        if (!selected.code) {
            grid.deleteRow(selected);
            return;
        }
        LG.ajax({
            url:'<c:url value="/security/node/delete"/>',
            loading:'正在删除中...',
            data:{ code:selected.code },
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

var treefilter = {
    op:'or',
    rules:[
        { field:'parent', value:'', op:'equal' },
        { field:'parent', op:'isnull' }
    ]
};
var tree = $("#maintree").ligerTree({
    url:'<c:url value="/security/node/tree?"/>' +
            $.param({
                rooticon:'<c:url value="/static/ligerUI/icons/32X32/category.gif"/>',
                iconroot:'../',
                code:'01',
                where:JSON2.stringify(treefilter)
            }),
    checkbox:false,
    textFieldName:"name",
    onClick:function (node) {
        if (!node.data.code) return;
        var where = {
            op:'and',
            rules:[
                { field:'code', value:node.data.MenuNo, op:'equal'}
            ]
        };
        grid.set('parms', { where:JSON2.stringify(where) });
        grid.set('url', '<c:url value="/security/node/list?code="/>' + node.data.code);
    }
});

var layout = $("#layout").ligerLayout({ leftWidth:140 });

var grid = $("#maingrid").ligerGrid({
    columns:[
        { display:'菜单名称', name:'name', width:200, minWidth:60, validate:{ required:true }, editor:{ type:'text' }
        },
        { display:'菜单编号', name:'code', width:130, minWidth:60, validate:{ required:true }, editor:{ type:'text' }
        },
        { display:'链接地址', name:'link', width:270, minWidth:60, editor:{ type:'text' }
        },
        { display:'菜单位置', name:'position', width:100, minWidth:60, validate:{ required:true, digits:true, min:0, max:99 }, editor:{ type:'text' }
        },
        { display:'图标', name:'icon', align:'left', width:230, minWidth:50, editor:{ type:'text'}, render:function (item) {
            return "<div style='width:100%;height:100%;text-align:center'><img src='" + GLOBAL_CTX + item.icon + "' /></div>";
        }
        }
    ], dataAction:'server', pageSize:20, toolbar:{}, sortName:'position',
    width:'98%', height:'100%', heightDiff:-5, checkbox:false, usePager:false, enabledEdit:true, clickToEdit:false,
    fixedCellHeight:true, rowHeight:25
});

//加载toolbar
LG.loadToolbar(grid, toolbarBtnItemClick);

grid.bind('beforeSubmitEdit', function (e) {
    if (!LG.validator.form()) {
        LG.showInvalid();
        return false;
    }
    return true;
});
grid.bind('afterSubmitEdit', function (e) {
    var parentCode = tree.getSelected().data.code;
    var isAddNew = e.record['__status'] == "add";
    var data = $.extend({ parent:parentCode, type:1 }, e.newdata);
    if (!isAddNew)
        data.code = e.record.code;
    LG.ajax({
        url:isAddNew ? '<c:url value="/security/node/save"/>' : '<c:url value="/security/node/update"/>',
        loading:'正在保存数据中...',
        data:data,
        success:function () {
            grid.loadData();
            LG.tip('保存成功!');
        },
        error:function (message) {
            LG.tip(message);
        }
    });
});

function beginEdit() {
    var row = grid.getSelectedRow();
    if (!row) {
        LG.tip('请选择行');
        return;
    }
    grid.beginEdit(row);
}
function addNewRow(parentCode) {
    grid.addEditRow();
    $.getJSON('<c:url value="/security/node/getNextChild?parent="/>' + parentCode, function (data) {
        var editingrow = grid.getEditingRow();
        var rowdata = grid.getRow(editingrow);
        var codeEditor = grid.editors[rowdata['__id']]['c102'];
        codeEditor.input.liger('option', 'value', data.code);
    }, function () {
        LG.tip('获取子节点编号失败');
    });
}
</script>
</body>
</html>
