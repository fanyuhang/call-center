<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<title>数据字典</title>
<style type="text/css">
    .l-panel td.l-grid-row-cell-editing {
        padding-bottom: 2px;
        padding-top: 2px;
    }
</style>
</head>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${MenuNo}"/>

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
<div id="maingrid" style="margin:2px;"></div>
<div id="detail" style="display:none;">
    <form id="mainform" method="post"></form>
</div>
<script type="text/javascript">
    //列表结构
    var grid = $("#maingrid").ligerGrid({
        columns:[
            { display:"类型", name:"type", width:120, type:"text", validate:{ required:true }, editor:{ type:'text' }},
            { display:"名称", name:"name", width:200, type:"text", editor:{ type:'text'} },
            { display:"值", name:"value", width:200, type:"text", editor:{ type:'text'} },
            { display:"状态", name:"status", width:100, type:"text", editor:{ type:'text'},
                render:function (item) {
                    if (parseInt(item.status) == 0) return '启用';
                    return '禁用';
                }},
            { display:"备注", name:"comment", width:440, type:"text", editor:{ type:'text'} }
        ], dataAction:'server', pageSize:20, toolbar:{},
        url:'<c:url value="/system/dictionary/list"/>', sortName:'type', sortOrder:'asc',
        width:'98%', height:'100%', heightDiff:-10, checkbox:false, enabledEdit:false, clickToEdit:false
    });

    //验证
    var maingform = $("#mainform");

    LG.validate(maingform, { debug:true });

    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        fields:[
            {display:"类型", name:"type", newline:false, labelWidth:100, width:220, space:30, type:"text", cssClass:"field", attr:{op:'equal', vt:'int'}},
            {display:"名称", name:"name", attr:{op:"like"}, newline:false, labelWidth:100, width:220, space:30, type:"text", cssClass:"field"}
        ],
        toJSON:JSON2.stringify
    });
    //增加搜索按钮,并创建事件
    LG.appendSearchButtons("#formsearch", grid, true, false);

    //加载toolbar
    LG.loadToolbar(grid, toolbarBtnItemClick);

    //工具条事件
    function toolbarBtnItemClick(item) {
        var editingrow = grid.getEditingRow();
        switch (item.id) {
            case "add":
                showDetail({
                    id:'',
                    type:'',
                    name:'',
                    value:'',
                    status:0,
                    comment:''
                }, true);
                break;
            case "modify":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                showDetail({
                    id:selected.id,
                    type:selected.type,
                    name:selected.name,
                    value:selected.value,
                    status:selected.status,
                    comment:selected.comment
                }, false);
                break;
            case "delete":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                jQuery.ligerDialog.confirm('确定删除吗?', function (confirm) {
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
            if (!selected.id) {
                grid.deleteRow(selected);
                return;
            }
            LG.ajax({
                url:'<c:url value="/system/dictionary/delete"/>',
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

    function validate() {
        if (!LG.validator.form()) {
            LG.showInvalid();
            return false;
        }
        return true;
    }

    var detailWin = null, currentData = null, currentIsAddNew;
    function showDetail(data, isAddNew) {
        currentData = data;
        currentIsAddNew = isAddNew;
        if (detailWin) {
            detailWin.show();
        }
        else {
            //创建表单结构
            var mainform = $("#mainform");
            mainform.ligerForm({
                inputWidth:280,
                fields:[
                    {name:"id_", type:"hidden"},
                    { display:"类型", name:"type_", newline:true, labelWidth:100, width:220, space:30, type:"text", align:"left", validate:{ required:true, digits:true}, editor:{ type:'int' }},
                    { display:"名称", name:"name_", newline:true, labelWidth:100, width:220, space:30, type:"text", align:"left", validate:{ required:true, maxlength:50}, editor:{ type:'text'} },
                    { display:"值", name:"value_", newline:true, labelWidth:100, width:220, space:30, type:"text", align:"left", validate:{ required:true, maxlength:100}, editor:{ type:'text'} },
                    {display:"状态", name:"status_", newline:true, labelWidth:100, width:220, space:30, type:"select", comboboxName:"statusName_", options:{
                        valueFieldID:"status_",
                        valueField:"value",
                        textField:"text",
                        data:[
                            {"value":0, "text":"启用"},
                            {"value":1, "text":"禁用"}
                        ]}, groupicon:"<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>"},
                    { display:"备注", name:"comment_", newline:true, labelWidth:100, width:220, space:30, type:"text", align:"left", validate:{ maxlength:200}, editor:{ type:'text'} }
                ],
                toJSON:JSON2.stringify
            });

            detailWin = $.ligerDialog.open({
                target:$("#detail"),
                width:450, height:230, top:90,
                buttons:[
                    { text:'确定', onclick:function () {
                        save();
                    } },
                    { text:'取消', onclick:function () {
                        detailWin.hide();
                    } }
                ]
            });
        }
        if (currentData) {
            $("#id_").val(currentData.id);
            $("#type_").val(currentData.type);
            $("#name_").val(currentData.name);
            $("#value_").val(currentData.value);
            $.ligerui.get("statusName_")._setValue(currentData.status);
            $("#comment_").val(currentData.comment);
        }
    }

    function save() {
        if (!validate()) {
            return;
        }
        currentData = currentData || {};
        currentData.id = $("#id_").val();
        currentData.type = $("#type_").val();
        currentData.name = $("#name_").val();
        currentData.value = $("#value_").val();
        currentData.status = $("#status_").val();
        currentData.comment = $("#comment_").val();

        LG.ajax({
            url:currentIsAddNew ? "<c:url value="/system/dictionary/save"/>" : "<c:url value="/system/dictionary/update"/>",
            loading:'正在保存数据中...',
            data:currentData,
            success:function () {
                detailWin.hide();
                grid.loadData();
                LG.tip('保存成功!');
            },
            error:function (message) {
                LG.tip(message);
            }
        });
    }
</script>
</body>
</html>
