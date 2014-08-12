<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
<div style="width:100%">
    <div class="searchtitle">
        <span>排队监控</span><img
            src='<c:url value="/static/ligerUI/icons/32X32/user.gif"/>'/>

        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom: 4px; margin-top: 4px;width:98%"></div>
    <div id="queueGrid">
    </div>
</div>
<script type="text/javascript">
var statusData = <sys:dictList type = "22"/>;

var snocx = parent.document.getElementById("snocx");

if (!snocx) {
    snocx.attachEvent("snlInAcdCallEvent", function (szPhoneNumber,szTrunkCh,szUserCh,szGroup) {
        queueGrid.addRow({
            szPhoneNumber:szPhoneNumber,
            szTrunkCh:szTrunkCh,
            szUserCh:szUserCh,
            szGroup:szGroup
        });
    });

    snocx.attachEvent("snlOutAcdCallEvent", function (szPhoneNumber,szTrunkCh,szUserCh,szGroup) {
        var rows = queueGrid.rows;
        for(var i=0;i<rows.length;i++){
            if(rows[i].szPhoneNumber == szPhoneNumber){
                queueGrid.deleteRow(rows[i]);
                break;
            }
        }
    });
}

var queueGrid = $("#queueGrid").ligerGrid({
    columnWidth: 180,
    columns: [
        { display: "来电号码", name: "szExtension", type: "text", align: "center" },
        { display: '中继通道', name: 'szRxDTMF', align: 'center', type: 'text'},
        { display: '等待坐席组号', name: 'szPhoneNumber', align: 'center' },
        { display: "外线通道", name: "szAgentName", type: "text", align: "center" }
    ], width: '98%', height: '98%', pageSize: 20, rowHeight: 20, checkbox: false,
    rownumbers: true, usePager: false
});

resizeDataGrid(queueGrid);

</script>
</body>
</html>