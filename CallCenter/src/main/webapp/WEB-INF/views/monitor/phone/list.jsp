<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="mainsearch" style="width:100%">
    <div class="searchtitle">
        <span>坐席监控</span><img
            src='<c:url value="/static/ligerUI/icons/32X32/clock.gif"/>'/>

        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom: 4px; margin-top: 4px;"></div>
    <div class="searchbox">
        <form id="formsearch" class="l-form"></form>
    </div>
</div>
<div style="width:100%">
    <div class="searchtitle">
        <span>分机</span><img
            src='<c:url value="/static/ligerUI/icons/32X32/contact.gif"/>'/>

        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom: 4px; margin-top: 4px;width:98%"></div>
    <div id="extGrid">
    </div>
</div>
<script type="text/javascript">
var statusData = <sys:dictList type = "22"/>;

var snocx = parent.document.getElementById("snocx");

var extGridData = {"Rows": []};

var trunkGridData = {"Rows": []};

function startUpdateTime() {
    updateExttime();
//        updateTrutime();
}

function loadExtData() {
    LG.ajax({
        url: '<c:url value="/monitor/phone/list"/>',
        datType: "json",
        success: function (result) {
            for (var i = 0; i < result.length; i++) {
                extGridData.Rows[result[i].nPos] = {
                    nPos: result[i].nPos,
                    nStatus: result[i].nStatus,
                    szExtension: result[i].szExtension,
                    szRxDTMF: result[i].szRxDTMF,
                    szPhoneNumber: result[i].szPhoneNumber,
                    szAgentID: result[i].szAgentID,
                    szAgentName: result[i].szAgentName,
                    nStatusTime: formatSeconds(result[i].nStatusTime),
                    sStatusTime: result[i].nStatusTime.toString()
                };
                extGrid.reload();
            }
//            setInterval("startUpdateTime()", 10000);
        },
        error: function (message) {
            LG.showError(message);
        }
    });
}

loadExtData();
//
//更新分机时间
//
function updateExttime() {
    for (var i = 0; i < extGridData.Rows.length; i++) {
        var time = parseInt(extGridData.Rows[i].sStatusTime);
        time = time + 10;
        extGridData.Rows[i].sStatusTime = time;
        extGridData.Rows[i].nStatusTime = formatSeconds(time);
    }
    if (extGridData.Rows.length > 0) {
        extGrid.reload();
    }
}

//
//更新外线时间
//
function updateTrutime() {
    for (var i = 0; i < trunkGridData.Rows.length; i++) {
        var time = parseInt(trunkGridData.Rows[i].sStatusTime);
        time = time + 10;
        trunkGridData.Rows[i].sStatusTime = time;
        trunkGridData.Rows[i].nStatusTime = formatSeconds(time);
    }
    if (trunkGridData.Rows.length > 0) {
//            trunkGrid.reload();
    }
}

try {
    snocx.attachEvent("snlExtensionInfoEvent", function (nPos, nStatus, szExtension, szRxDTMF, szPhoneNumber, szAgentID, szAgentName, nStatusTime) {
        extGridData.Rows[nPos] = {
            nPos: nPos,
            nStatus: nStatus,
            szExtension: szExtension,
            szRxDTMF: szRxDTMF,
            szPhoneNumber: szPhoneNumber,
            szAgentID: szAgentID,
            szAgentName: szAgentName,
            nStatusTime: formatSeconds(nStatusTime),
            sStatusTime: nStatusTime.toString()
        };
        extGrid.reload();
    });
} catch (ex) {

}
//        snocx.attachEvent("snlTrunkInfoEvent", function (nCh, nStatus, szIVR, szRxDTMF, szPhoneNumber, szAgentID, szAgentName, nStatusTime) {
//            trunkGridData.Rows[nCh] = {
//                nCh: nCh,
//                nStatus: nStatus,
//                szIVR: szIVR,
//                szRxDTMF: szRxDTMF,
//                szPhoneNumber: szPhoneNumber,
//                szAgentID: szAgentID,
//                szAgentName: szAgentName,
//                nStatusTime: formatSeconds(nStatusTime),
//                sStatusTime: nStatusTime.toString()
//            };
//            trunkGrid.reload();
//        });

//搜索表单应用ligerui样式
var formsearch = $("#formsearch");
formsearch.ligerForm({
    labelWidth: 100, inputWidth: 180, space: 30,
    fields: [
        {display: "分机号", name: "extPhone", newline: true, type: "text", cssClass: "field",
            validate: {
                required: true,
                maxlength: 5
            }}
    ],
    toJSON: JSON2.stringify
});

LG.validate(formsearch);

var container1 = $('<li style="margin-right:8px"></li>').appendTo($("#formsearch").find("ul:last"));
LG.createButton({appendTo: container1,
    text: '监听',
    click: function () {
        if (formsearch.valid()) {
            try {
                var sextNum = $("#extPhone").val();
                snocx.snlListenCall(sextNum);
            }
            catch (ex) {
                alert(ex.description);
            }
        }
    }});
var container2 = $('<li style="margin-right:8px"></li>').appendTo($("#formsearch").find("ul:last"));
LG.createButton({appendTo: container2,
    text: '强拆',
    click: function () {
        if (formsearch.valid()) {
            try {
                var sextNum = $("#extPhone").val();
                snocx.snlReplaceCall(sextNum);
            }
            catch (ex) {
                alert(ex.description);
            }
        }
    }});
var container3 = $('<li style="margin-right:8px"></li>').appendTo($("#formsearch").find("ul:last"));
LG.createButton({appendTo: container3,
    text: '强插',
    click: function () {
        if (formsearch.valid()) {
            try {
                var sextNum = $("#extPhone").val();
                snocx.snlIntrudeCall(sextNum);
            }
            catch (ex) {
                alert(ex.description);
            }
        }
    }});

var extGrid = $("#extGrid").ligerGrid({
    columnWidth: 180,
    columns: [
        { display: "坐席状态", name: "nStatus", type: "text", align: "center",
            render: function (item) {
                return renderLabel(
                        statusData,
                        item.nStatus);
            }},
        { display: "分机号", name: "szExtension", type: "text", align: "center" },
        { display: '收号', name: 'szRxDTMF', align: 'left', type: 'text'},
        { display: '主叫号码', name: 'szPhoneNumber', align: 'center', render: function (item) {
            if(!item.szPhoneNumber){
                return "";
            }
            if (item.szPhoneNumber == '-1'||item.szPhoneNumber.length<5) {
                return "";
            }
            return LG.hiddenPhone(item.szPhoneNumber);
        } },
        { display: "登录用户", name: "szAgentName", type: "text", width: 130, align: "center" },
        { display: '工号', name: 'szAgentID', width: 130, align: 'center' },
        { display: '时长', name: 'nStatusTime', align: 'center' }
    ], width: '98%', data: extGridData, height: '98%', pageSize: 20, rowHeight: 20, checkbox: false,
    rownumbers: true, usePager: false, onSelectRow: function (rowdata, rowid, rowobj) {
        $("#extPhone").val(rowdata.szExtension);
    }
});


//    var trunkGrid = $("#trunkGrid").ligerGrid({
//        columnWidth: 180,
//        columns: [
//            { display: "通道状态", name: "nStatus", type: "text", align: "center",
//                render: function (item) {
//                    return renderLabel(
//                            statusData,
//                            item.nStatus);
//                }},
//            { display: "按键信息", name: "szRxDTMF", type: "text", align: "left" },
//            { display: '流程节点', name: 'szIVR', align: 'left', type: 'text'},
//            { display: "来电号码", name: "szPhoneNumber", type: "text", align: "left" },
//            { display: '登录用户', name: 'szAgentName', width: 130, align: 'center' },
//            { display: '工号', name: 'szAgentID', width: 130, align: 'center' },
//            { display: '时长', name: 'nStatusTime', align: 'center' }
//        ], width: '98%', data: trunkGridData, height: '25%', pageSize: 20, rowHeight: 20, checkbox: false,
//        rownumbers: true, usePager: false
//    });

resizeDataGrid(extGrid);
//    resizeDataGrid(trunkGrid);


</script>
</body>
</html>