<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<title>数据权限</title>
<script src="<c:url value="/static/ligerUI/js/DataPrivilageSysParm.js"/>" type="text/javascript"></script>
<script src="<c:url value="/static/ligerUI/ligerUI/js/plugins/ligerFilter.js"/>" type="text/javascript"></script>
<style type="text/css">
    .filterpanle {
        margin: 10px;
    }

    .l-panel td.l-filter-column, .l-panel td.l-filter-value, .l-panel td.l-filter-op {
        padding: 2px;
    }

    .l-selected .l-grid-row-cell, .l-selected {
        background: #F5F5F5;
    }

    .l-panel td .l-filter-rowlast {
        padding-top: 1px;
    }

    .l-panel td .l-filter-rowlast {
        padding: 3px;
    }

    .l-panel td .groupopsel {
        margin: 1px;
    }

    .l-panel td .l-filter-cellgroup {
        padding: 2px;
    }

    .l-panel td .l-filter-rowlastcell {
        padding: 2px;
    }
</style>
</head>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="mainsearch" style=" width:98%">
    <div id="maingrid"></div>
</div>
<script type="text/javascript">
    var resources = [];
    $.getJSON('<c:url value="/security/dataPrivilege/getResources?rnd="/>' + Math.random(), function (data) {
        resources = data;
    }, function () {
        LG.showError("加载资源失败");
    });

    var grid = $("#maingrid").ligerGrid({
        columns:[
            { display:"资源", name:"master", width:120, type:"text", align:"left" },
            { display:"条件规则", name:"privilegeRule", width:680, type:"textarea", align:"left", render:ruleRender }
        ], dataAction:'server', pageSize:20, toolbar:{},
        url:'<c:url value="/security/dataPrivilege/list"/>', sortName:'id',
        width:'98%', height:'100%', heightDiff:-10, checkbox:false, usePager:false, mouseoverRowCssClass:null, alternatingRow:false
    });

    //加载toolbar
    LG.loadToolbar(grid, toolbarBtnItemClick);

    var filterCounter = 0;
    var AllfilterData = {};
    function ruleRender(rowdata) {
        var rule = rowdata.privilegeRule;
        var master = rowdata.master;
        if (!rule || rule == "{}") return "";
        var ruleData = JSON2.parse(rule);
        AllfilterData['filter' + ++filterCounter] = ruleData;
        return "<div id='filter" + filterCounter + "' master='" + master + "' class='filterpanle'></div>";
    }
    grid.bind('afterShowData', function () {
        $("div.filterpanle", maingrid).each(function () {
            var ruleData = AllfilterData[this.id];
            var master = $(this).attr("master");
            var filter = $(this).ligerFilter({fields:getFields(master)});
            filter.setData(ruleData);
        });
        $(":button,.deleterole", maingrid).remove();
        $("select,input", maingrid).attr("disabled", "disabled");
    });


    //工具条事件
    function toolbarBtnItemClick(item) {
        switch (item.id) {
            case "add":
                top.f_addTab(null, '增加数据权限信息', '<c:url value="/security/dataPrivilege/add?menuNo=${menuNo}"/>');
                break;
            case "view":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                top.f_addTab(null, '查看数据权限信息', '<c:url value="/security/dataPrivilege/view?menuNo=${menuNo}&id="/>' + selected.id);
                break;
            case "modify":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                top.f_addTab(null, '修改数据权限信息', '<c:url value="/security/dataPrivilege/edit?menuNo=${menuNo}&id="/>' + selected.id);
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
            LG.ajax({
                url:'<c:url value="/security/dataPrivilege/delete"/>',
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
