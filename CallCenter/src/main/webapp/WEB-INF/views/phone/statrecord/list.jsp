<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding: 10px; height: 100%; text-align: center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>

<div id="mainsearch" style="width: 98%">
    <div class="searchtitle">
        <span>搜索</span><img
            src='<c:url value="/static/ligerUI/icons/32X32/searchtool.gif"/>'/>

        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom: 4px; margin-top: 4px;"></div>
    <div class="searchbox">
        <form id="formsearch" class="l-form"></form>
    </div>
</div>
<div id="maingrid"></div>
<script type="text/javascript">
    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        labelWidth: 120,
        inputWidth: 150,
        space: 30,
        fields: [
            {
                display: "分机号",
                name: "extno",
                newline: true,
                type: "text",
                cssClass: "field"
            },
            {
                display: "话务员",
                name: "operateUser.name",
                newline: false,
                type: "text",
                cssClass: "field"
            },
            {
                display: "开始时间",
                name: "startDate",
                newline: true,
                type: "date",
                cssClass: "field",
                attr: {
                    op: 'greaterorequal',
                    vt: 'date',
                    field: "starttime"
                }
            },
            {
                display: "结束时间",
                name: "endDate",
                newline: false,
                type: "date",
                cssClass: "field",
                attr: {
                    op: 'lessorequal',
                    vt: 'date',
                    field: "starttime"
                }
            }
        ],
        toJSON: JSON2.stringify
    });

    //列表结构
    var grid = $("#maingrid").ligerGrid({
        checkbox: false,
        rownumbers: true,
        delayLoad: true,
        columnWidth: 180,
        columns: [
            {
                display: "分机号",
                name: "extno"
            },
            {
                display: "话务员",
                name: "operateUserName"
            },
            {
                display: "操作",
                name: "statusName"
            },
            {
                display: "开始时间",
                name: "starttime"
            },
            {
                display: "结束时间",
                name: "endtime"
            }
        ],
        dataAction: 'server',
        pageSize: 20,
        url: '<c:url value="/phone/statrecord/list"/>',
        sortName: 'starttime',
        sortOrder: 'desc',
        width: '98%',
        height: '98%',
        toJSON: JSON2.stringify,
        onReload: f_reload
    });

    //增加搜索按钮,并创建事件
    LG.appendSearchButtons("#formsearch", grid, true, true);

    function f_reload() {
        grid.loadData();
    }

    resizeDataGrid(grid);
</script>
</body>
</html>