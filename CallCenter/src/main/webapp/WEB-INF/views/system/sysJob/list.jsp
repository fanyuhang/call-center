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

    var statusType = <sys:dictList type="4" nullable="true"/>;

    var triggerType = <sys:dictList type="5" nullable="true"/>;

    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        labelWidth:100,
        width:220,
        space:30,
        fields:[
            {display:"Job名称", name:"jobName", newline:true, type:"text", cssClass:"field"},
            {display:"Trigger名称", name:"triggerName", newline:false, type:"text", cssClass:"field"},
            {display:"Trigger类型", name:"triggerType", newline:true, cssClass:"field", attr:{"op":'equal', "vt":"int"}, type:"select", comboboxName:"triggerTypeName", options:{
                valueFieldID:"triggerType",
                valueField:"value",
                textField:"text",
                data:triggerType
            }},
            {display:"状态", name:"status", newline:false, cssClass:"field", attr:{"op":'equal', "vt":"int"}, type:"select", comboboxName:"statusName", options:{
                valueFieldID:"status",
                valueField:"value",
                textField:"text",
                data:statusType
            }},
            {display:"执行起始时间", name:"executeDateBegin", newline:true, cssClass:"field", type:"date", attr:{"op":'greaterorequal', field:"lastSuccessDate", vt:"date"}},
            {display:"结束时间", name:"executeDateEnd", newline:false, cssClass:"field", type:"date", attr:{"op":'less', field:"lastSuccessDate", vt:"date"}}
        ],
        toJSON:JSON2.stringify
    });

    var grid = $("#maingrid").ligerGrid({
        columnWidth:180,
        columns:[
            { display:"Job名称", name:"jobName", type:"text", align:"left" },
            { display:"Job组", name:"jobGroup", type:"text", align:"left" },
            { display:"Trigger名称", name:"triggerName", type:"text", align:"left" },
            { display:'Trigger组', name:'triggerGroup', align:'left' },
            { display:'表达式', name:'cronExpression', align:'left' },
            { display:'执行时间间隔', name:'intervalInSeconds', align:'left' },
            { display:'重复次数', name:'repeatCount', align:'left' },
            { display:'Job类名', name:'jobClass', align:'left' },
            { display:'Trigger类型', name:'triggerType', align:'left',
                render:function (item) {
                    return renderLabel(triggerType, item.triggerType);
                }},
            { display:'状态', name:'status', align:'left',
                render:function (item) {
                    return renderLabel(statusType, item.status);
                }},
            { display:'最近成功时间', name:'lastSuccessDate', align:'left'},
            { display:'下次执行时间', name:'nextFireTime', align:'left'},
            { display:'开始时间', name:'startTime', align:'left'},
            { display:'结束时间', name:'endTime', align:'left'},
            { display:'执行次数', name:'executeCount', align:'left'}
        ], dataAction:'server', toolbar:{}, url:'<c:url value="/system/sysJob/list"/>', sortName:'id',
        width:'98%', height:'98%', rowHeight:20, checkbox:false, rownumbers:true, usePager:true
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
                top.f_addTab(null, '增加定时任务', '<c:url value="/system/sysJob/add"/>' + '?menuNo=${menuNo}');
                break;
            case "modify":
                if (grid.getSelectedRows().length > 1 || grid.getSelectedRows().length == 0) {
                    LG.tip('请选择一行数据!');
                    return;
                }
                var selected = grid.getSelected();
                top.f_addTab(null, '修改定时任务', '<c:url value="/system/sysJob/edit"/>' + '?menuNo=${menuNo}&id=' + selected.id);
                break;
            case "delete":
                jQuery.ligerDialog.confirm('确定删除吗?', function (confirm) {
                    if (confirm)
                        f_delete();
                });
                break;
            case "view":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                top.f_addTab(null, '查看定时任务', '<c:url value="/system/sysJob/find"/>' + '?menuNo=${menuNo}&id=' + selected.id);
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
                url:'<c:url value="/system/sysJob/delete"/>',
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
</script>
</body>
</html>