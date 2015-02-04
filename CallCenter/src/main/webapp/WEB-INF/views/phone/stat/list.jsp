<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding: 10px; height: 100%; text-align: center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
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
    <div id="maingrid">
    </div>
</div>
<script type="text/javascript">
    var snocx = parent.document.getElementById("snocx");

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
                display: "状态",
                name: "statusName"
            },
            {
                display: "时间",
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
        usePager: false,
        toJSON: JSON2.stringify,
        onReload: f_reload,
        onSelectRow: function (rowdata, rowid, rowobj) {
            $("#extPhone").val(rowdata.extno);
        }
    });

    function f_reload() {
        grid.loadData();
    }

    setInterval("f_reload()", 2000);
</script>
</body>
</html>