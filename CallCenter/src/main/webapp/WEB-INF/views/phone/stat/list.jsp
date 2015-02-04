<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding: 10px; height: 100%; text-align: center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
    //列表结构
    var grid = $("#maingrid").ligerGrid({
        checkbox: false,
        rownumbers: true,
        delayLoad: false,
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
            }
        ],
        dataAction: 'server',
        pageSize: 100,
        url: '<c:url value="/phone/stat/list"/>',
        sortName: 'starttime',
        sortOrder: 'desc',
        width: '98%',
        height: '98%',
        toJSON: JSON2.stringify,
        onReload: f_reload
    });

    function f_reload() {
        grid.loadData();
    }

    setInterval("f_reload()", 5000);


</script>
</body>
</html>