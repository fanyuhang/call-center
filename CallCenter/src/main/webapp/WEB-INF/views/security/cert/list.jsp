<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
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
    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        fields:[
            {display:"证书CN", name:"certCN", newline:false, labelWidth:100, width:220, space:30, type:"text", cssClass:"field"}
        ],
        toJSON:JSON2.stringify
    });

    var maingrid = $("#maingrid").ligerGrid({
        columns:[
            { display:"证书CN", name:"certCN", width:180, type:"text", align:"left" },
            { display:"公钥值", name:"publicKey", width:180, type:"text", align:"left" },
            { display:"操作人", name:"operateId", width:180, type:"text", align:"left" },
            { display:"操作时间", name:"operateDate", width:180, type:"text", align:"left"}
        ], dataAction:'server', toolbar:{}, url:'<c:url value="/security/cert/list"/>', sortName:'operateDate', sortOrder:'desc',
        width:'98%', height:'98%', pageSize:20, rowHeight:20, checkbox:true, rownumbers:true, usePager:true
    });

    //增加搜索按钮,并创建事件
    LG.appendSearchButtons("#formsearch", maingrid,true,false);

    //加载toolbar
    LG.loadToolbar(maingrid, toolbarBtnItemClick);

    //验证
    //工具条事件
    function toolbarBtnItemClick(item) {
        switch (item.id) {
            case "add":
                top.f_addTab(null, '增加品牌信息', '<c:url value="/security/cert/add"/>' + '?menuNo=${menuNo}');
                break;
            case "modify":
                if (maingrid.getSelectedRows().length > 1 || maingrid.getSelectedRows().length == 0) {
                    LG.tip('请选择一行数据!');
                    return;
                }
                var selected = maingrid.getSelected();
                top.f_addTab(null, '修改品牌信息', '<c:url value="/security/cert/edit"/>' + '?menuNo=${menuNo}&brandCode=' + selected.brandCode);
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
        maingrid.loadData();
    }
    function f_delete() {
        var rows = maingrid.getCheckedRows();
        if (rows.length > 0) {

            var ids = "";
            for (var i = 0; i < rows.length; i++) {
                ids = ids + rows[i].certCN + ",";
            }

            if (ids.length > 0) {
                ids = ids.substring(0, ids.length - 1);
            }

            LG.ajax({
                url:'<c:url value="/security/cert/delete"/>',
                loading:'正在删除中...',
                data:{ ids:ids },
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