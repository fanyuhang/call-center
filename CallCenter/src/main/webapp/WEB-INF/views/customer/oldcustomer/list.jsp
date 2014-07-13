<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<script src='<c:url value="/static/ligerUI/js/ajaxfileupload.js"/>' type="text/javascript"></script>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="mainsearch" style="width:98%">
    <div class="searchtitle">
        <span>搜索</span><img src="<c:url value="/static/ligerUI/icons/32X32/searchtool.gif"/>"/>

        <div class="togglebtn"></div>
    </div>
    <div class="navline" style="margin-bottom:4px; margin-top:4px;"></div>
    <div class="searchbox">
        <form id="formsearch" class="l-form"></form>
    </div>
</div>
<div id="maingrid"></div>
<script type="text/javascript">
    var customerType=<sys:dictList type="52" />;
    $("#layout1").ligerLayout();
    //列表结构
    var grid = $("#maingrid").ligerGrid({
        columnWidth:180,
        rownumbers:true,
        delayLoad:true,
        columns:[
            {display:"是否有效", name:"status", hide:"1", attr:{value:0}},
            {display:"客户名称", name:"fullName", type:"text"},
            {display:"部门名称", name:"contacterDept", type:"text"},
            {display:"联系人", name:"contacterName", type:"text",
                render: function (item){
                    if(item.contacterName.length>1){
                        return "xxx"+item.contacterName.substring(item.contacterName.length-1,item.contacterName.length)
                    }
                    return "";
                }},
            {display:"客户类型", name:"type", type:"text",
                render: function (item){ return renderLabel(customerType, item.type); }
            },
            {display:"客户所属客服", name:"serviceUserName", type:"text"},
            {display:"客户所属销售", name:"marketUserName", type:"text"},
            {display:"操作时间", name:"operateDate", type:"text"}
        ], dataAction:'server', pageSize:20, toolbar:{},
        url:'<c:url value="/order/customer/listAllWithContacter"/>', sortName:'operateDate', sortOrder:"desc",
        width:'98%', height:'100%', heightDiff:-10, checkbox:false,enabledSort:false
    });
	function uploadPic(customerId){
	   $.ajaxFileUpload({
	        url:'<c:url value="/order/customer/upload?customerId="/>'+customerId,
	        secureuri:false,
	        fileElementId:"fileData",
	        dataType:"json",
	        success:function (data) {
	            if (data.IsError) {
	                LG.showError(data.Message);
	            }
	        },error:function (data) {
            LG.showError(data);
        }
   		});
     }
    
	function downloadPic(customerId){
		window.location.href='<c:url value="/order/customer/download?customerId='+customerId+'"/>'; 
    }

    //搜索表单应用ligerui样式
    $("#formsearch").ligerForm({
        fields:[
            {display:"客户名称", name:"customer.fullName", newline:true, validate:{required:true}, labelWidth:100, width:220,
                space:30, type:"text", cssClass:"field"}
//            {display:"部门名称", name:"contacterDept", newline:false, labelWidth:100, width:220,
//                space:30, type:"text", cssClass:"field"},
//            {display:"客服名称", name:"serviceUser.userName", newline:true, labelWidth:100, width:220,
//                space:30, type:"text", cssClass:"field"},
//            {display:"销售名称", name:"marketUser.userName", newline:false, labelWidth:100, width:220,
//                space:30, type:"text", cssClass:"field"}
        ],
        toJSON:JSON2.stringify
    });

    //增加搜索按钮,并创建事件
    LG.appendSearchButtons("#formsearch", grid,true, false);

    //加载toolbar
    LG.loadToolbar(grid, toolbarBtnItemClick);

    //工具条事件
    function toolbarBtnItemClick(item) {
        switch (item.id) {
            case "add":
                top.f_addTab(null, '增加客户信息', '<c:url value="/order/customer/add?menuNo=${menuNo}"/>');
                break;
            case "addContacter":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择客户!');
                    return
                }
                top.f_addTab(null, '增加部门信息', '<c:url value="/order/contactor/add?menuNo=${menuNo}&customerId="/>'+selected.id);
                break;
            case "view":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                top.f_addTab(null, '查看客户信息', '<c:url value="/order/customer/view/?id="/>' + selected.id);
                break;
            case "viewAll":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                top.f_addTab(null, '查看客户信息', '<c:url value="/order/customer/view/?flag=true&id="/>' + selected.id);
                break;

            case "modify":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                top.f_addTab(null, '修改客户信息', '<c:url value="/order/customer/edit/?menuNo=${menuNo}&id="/>' + selected.id);
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
                url:'<c:url value="/order/contactor/delete"/>',
                loading:'正在删除中...',
                data:{ id:selected.contacterId },
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
    resizeDataGrid(grid);
</script>
</body>
</html>
