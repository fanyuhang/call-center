<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid">
</div>
<script type="text/javascript">

    var statusType = <sys:dictList type="32" nullable="true"/>;

    function find_content(id) {
        LG.ajax({
            url: '<c:url value="/telephone/product/find"/>',
            data: { id: id },
            success: function (data) {
                go = open('', data[0].fldTitle, 'top=0, left=0, toolbar=no, menubar=no, scrollbars=no, resizable=no,location=n o, status=no');
                go.document.open();
                go.document.write(data[0].fldContent);
                go.document.close();
            },
            error: function () {

            }
        });
    }

    var grid = $("#maingrid").ligerGrid({
        columnWidth:180,
        delayLoad:false,
        columns:[
            {display: "产品名称", name: "fldTitle", width: 300, render: function (item) {
                return "<a href='javascript:find_content(\"" + item.fldId + "\");' alt='点击查看详情' >" + item.fldTitle + "</a>";
            }},
            {display: "操作人", name: "operateName"},
            {display: "操作时间", name: "fldOperateDate", width: 240}
        ], dataAction:'server', toolbar:{}, url:'<c:url value="/telephone/product/query"/>',pageSizeOptions: [10, 20, 30, 40, 50, 100, 200], sortName:'fldOperateDate', sortOrder:'desc',
        width:'98%', height:'98%',pageSize:50, rowHeight:20, checkbox:false, rownumbers:true, usePager:true
    });

    function f_reload() {
        grid.loadData();
    }

    setInterval("f_reload()", 10000);

</script>
</body>
</html>