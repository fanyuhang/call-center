<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<title>系统日志</title>
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
        rownumbers:true,
        columns:[
            { display:"操作时间", name:"operateDate", width:200, type:"text", editor:{ type:'text' }},
            { display:"操作人", name:"loginName", width:120, type:"text", editor:{ type:'text'} },
//            { display:"操作类型", name:"action", width:120, type:"text", editor:{ type:'text'} },
            { display:"操作资源", name:"resource", width:325, type:"text", editor:{ type:'text'}},
            { display:"操作明细", name:"comment", width:310, type:"text", editor:{ type:'text'} }
        ], dataAction:'server', pageSize:20,
        url:'<c:url value="/system/sysLog/list"/>', sortName:'operateDate', sortOrder:'desc',
        width:'98%', height:'100%', heightDiff:-10, checkbox:false, enabledEdit:false, clickToEdit:false
    });

    //验证
    var maingform = $("#formsearch");

    LG.validate(maingform, { debug:true });

    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        labelWidth:100, width:220, space:30,
        fields:[
            {display:"开始时间", name:"startDate", newline:true,  type:"date", cssClass:"field",
                attr:{op:'greaterorequal', vt:'date', field:"operateDate",value:"${startDate}"},
                validate:{required:true}},
            {display:"结束时间", name:"endDate", newline:false, type:"date", cssClass:"field", validate:{required:true},
                attr:{op:'lessorequal', vt:'date', field:"operateDate",value:"${endDate}"}},
            {display:"操作资源", name:"resource", newline:false, type:"text", cssClass:"field"}
        ]
//        toJSON:JSON2.stringify
    });
    //增加搜索按钮,并创建事件
    LG.appendSearchButtons("#formsearch", grid, true, false);

    function f_reload() {
        grid.loadData();
    }

    function validate() {
        if (!LG.validator.form()) {
            LG.showInvalid();
            return false;
        }
        return true;
    }

    grid.bind('onAfterShowData', function () {
        return validate();
    });
</script>
</body>
</html>
