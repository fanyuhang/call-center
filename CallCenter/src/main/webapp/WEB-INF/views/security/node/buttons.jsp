<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<title>按钮管理</title>
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
<form id="mainform">
    <div id="maingrid" style="margin:2px;"></div>
</form>
<script type="text/javascript">
    var parentCode = '${parent}';


    //验证
    var maingform = $("#mainform");

    LG.validate(maingform, { debig:true });
    //这里覆盖了本页面grid的loading效果
    $.extend($.ligerDefaults.Grid, {
        onloading:function () {
            LG.showLoading('正在加载表格数据中...');
        },
        onloaded:function () {
            LG.hideLoading();
        }
    });

    function itemclick(item) {
        var editingrow = grid.getEditingRow();
        var id = item.id;
        switch (id) {
            case "add":
                if (editingrow == null) {
                    addNewRow();
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
    var toolbarOptions = {
        items:[
            { text:'增加', id:'add', click:itemclick, img:"<c:url value="/static/ligerUI/icons/silkicons/add.png"/>"},
            { line:true },
            { text:'修改', id:'modify', click:itemclick, img:"<c:url value="/static/ligerUI/icons/miniicons/page_edit.gif"/>" },
            { line:true },
            { text:'保存', id:'save', click:itemclick, img:"<c:url value="/static/ligerUI/icons/silkicons/page_save.png"/>" },
            { line:true },
            { text:'取消', id:'cancel', click:itemclick, img:"<c:url value="/static/ligerUI/icons/silkicons/cancel.png"/>" },
            { line:true },
            { text:'删除', id:'delete', click:itemclick, img:"<c:url value="/static/ligerUI/icons/miniicons/page_delete.gif"/>" },
            { line:true }
        ]
    };

    var grid = $("#maingrid").ligerGrid({
        columns:[
            { display:'按钮名称', name:'name', width:220, minWidth:60, validate:{ required:true }, editor:{ type:'text' }
            },
            { display:'按钮编号', name:'code', width:220, minWidth:60, validate:{ required:true }, editor:{ type:'text' }
            },
            { display:'按钮ID', name:'target', width:220, minWidth:60, validate:{ required:true }, editor:{ type:'text' }
            },
            { display:'按钮位置', name:'position', width:100, minWidth:60, validate:{ required:true, digits:true, min:0, max:99 }, editor:{ type:'text' }
            },
            { display:'图标', name:'icon', width:315, minWidth:50, editor:{ type:'text'}, render:function (item) {
                return "<div style='width:100%;height:100%;text-align:center'><img src='" + GLOBAL_CTX + item.icon + "' /></div>";
            }
            }
        ], toolbar:toolbarOptions, sortName:'BtnID',
        width:'98%', height:'100%', heightDiff:-5, checkbox:false,
        usePager:false, enabledEdit:true, clickToEdit:false,
        fixedCellHeight:true, rowHeight:25,
        url:'<c:url value="/security/node/listButton?parent="/>' + parentCode,
        parms:{ where:JSON2.stringify({
            op:'and',
            rules:[
                { field:'parent', value:parentCode, op:'equal'}
            ]
        })
        }
    });


    grid.bind('beforeSubmitEdit', function (e) {
        if (!LG.validator.form()) {
            LG.showInvalid();
            return false;
        }
        return true;
    });
    grid.bind('afterSubmitEdit', function (e) {
        var isAddNew = e.record['__status'] == "add";
        var data = $.extend({ parent:parentCode, type:2 }, e.newdata);
        if (!isAddNew)
            data.code = e.record.code;
        LG.ajax({
            loading:'正在保存数据中...',
            url:'<c:url value="/security/node/save"/>',
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
    function addNewRow() {
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
