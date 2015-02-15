<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="mainsearch" style=" width:98%">
    <div class="searchtitle">
        <span>搜索</span><img src='<c:url value="/static/ligerUI/icons/32X32/searchtool.gif"/>'/>

        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom:4px; margin-top:4px;"></div>
    <div class="searchbox">
        <form id="formsearch" class="l-form"></form>
    </div>
</div>
<div id="maingrid">
</div>
<script type="text/javascript">

    var statusType = <sys:dictList type="32" nullable="true"/>;

    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        labelWidth:100,
        inputWidth:200,
        fields:[
            {display:"标题", name:"fldTitle", newline:true, type:"text", cssClass:"field"},
            {display:"状态", name:"fldStatus", newline:false, cssClass:"field", attr:{"op":'equal', "vt":"int"}, type:"select", comboboxName:"statusName", options:{
                valueFieldID:"fldStatus",
                valueField:"value",
                textField:"text",
                initValue:'0',
                data:statusType
            }},
            {display:"发布时间", name:"executeDateBegin", newline:true, cssClass:"field", type:"date", attr:{"op":'greaterorequal', field:"fldOperateDate", vt:"date"}},
            {display:"至", name:"executeDateEnd", newline:false, cssClass:"field", type:"date", attr:{"op":'less', field:"fldOperateDate", vt:"date"}}
        ],
        toJSON:JSON2.stringify
    });

    var grid = $("#maingrid").ligerGrid({
        columnWidth:180,
        delayLoad:true,
        columns:[
            { display:"标题", name:"fldTitle", type:"text", align:"center", width:300 },
            { display:'状态', name:'fldStatus', align:'center', width: 120,
                render:function (item) {
                    return renderLabel(statusType, item.fldStatus);
                }},
            { display:'发布人', name:'operateName', align:'center'},
            { display:'发布时间', name:'fldOperateDate', align:'center'}
        ], dataAction:'server', toolbar:{}, url:'<c:url value="/system/notice/list"/>', sortName:'fldOperateDate', sortOrder:'desc',
        width:'98%', height:'98%',pageSize:20, rowHeight:20, checkbox:false, rownumbers:true, usePager:true
    });
    LG.setGridDoubleClick(grid, 'modify', toolbarBtnItemClick);

    //增加搜索按钮,并创建事件
    LG.appendSearchButtons("#formsearch", grid);

    //加载toolbar
    LG.loadToolbar(grid, toolbarBtnItemClick);

    //验证
    //工具条事件
    function toolbarBtnItemClick(item) {
        switch (item.id) {
            case "add":
                top.f_addTab(null, '增加公告内容', '<c:url value="/system/notice/add"/>' + '?menuNo=${menuNo}');
                break;
            case "modify":
                if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                    LG.tip('请选择一行数据!');
                    return;
                }
                var selected = grid.getSelected();
                top.f_addTab(null, '修改公告内容', '<c:url value="/system/notice/edit"/>' + '?menuNo=${menuNo}&id=' + selected.fldId);
                break;
            case "delete":
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
            LG.ajax({
                url:'<c:url value="/system/notice/delete"/>',
                loading:'正在删除中...',
                data:{ id:selected.fldId },
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

    resizeDataGrid(grid);

</script>
</body>
</html>