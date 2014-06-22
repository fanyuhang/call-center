<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<title>部门</title>
</head>
<body style="padding:10px;height:100%; text-align:center;">
<ipnut type="hidden" id="MenuNo" value="${MenuNo}"/>
<div id="detail" style="display:none;">
    <form id="mainform" method="post"></form>
</div>
<div id="maingrid"></div>
<script type="text/javascript">
    //列表结构
    var grid = $("#maingrid").ligerGrid({
        columns:[
            {display:"部门名称", name:"deptName", width:280, type:"text", align:"left"},
            {display:"部门代码", name:"deptCode", width:480, type:"text", align:"left"}
        ], dataAction:'server', toolbar:{},
        url:'<c:url value="/security/dept/list"/>', sortName:'deptCode',
        tree:{ columnName:'deptName' },
        width:'98%', height:'100%', heightDiff:-10, checkbox:false, usePager:false, enabledSort:false
    });

    //加载toolbar
    LG.loadToolbar(grid, toolbarBtnItemClick);

    //验证
    var maingform = $("#mainform");

    LG.validate(maingform, { debug:true });

    //工具条事件
    function toolbarBtnItemClick(item) {
        switch (item.id) {
            case "add":
                //top.f_addTab(null, '增加部门信息', 'MemberManage/DepartmentDetail.aspx');
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择上级部门!');
                    return
                }
                $.getJSON('<c:url value="/security/dept/getNextChild?parent="/>' + selected.deptCode + "&time=" + (new Date()), function (data) {
                    showDetail({
                        parentDept:selected ? selected.deptName : '',
                        parentCode:selected ? selected.deptCode : '',
                        deptCode:data ? data.deptCode : ''
                    }, true);
                }, function () {
                    LG.tip('获取子节点编号失败');
                });

                break;
            case "modify":
                var selected = grid.getSelected();
                if (!selected) {
                    LG.tip('请选择行!');
                    return
                }
                var parent = grid.getParent(selected);
                showDetail({
                    parentCode:selected ? selected.parent : '',
                    deptCode:selected.deptCode,
                    deptName:selected.deptName,
                    parentDept:parent ? parent.deptName : ''
                }, false);
                //top.f_addTab(null, '修改部门信息', 'MemberManage/DepartmentDetail.aspx?ID=' + selected.DeptID);
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
            if (selected.deptCode == '001') {
                LG.tip('根节点不能删除!');
                return;
            }
            LG.ajax({
                url:'<c:url value="/security/dept/delete"/>',
                loading:'正在删除中...',
                data:{ code:selected.deptCode },
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

    var detailWin = null, curentData = null, currentIsAddNew;
    function showDetail(data, isAddNew) {
        curentData = data;
        currentIsAddNew = isAddNew;
        if (detailWin) {
            detailWin.show();
        }
        else {
            //创建表单结构
            var mainform = $("#mainform");
            mainform.ligerForm({
                inputWidth:280,
                fields:[
                    {name:"parent", type:"hidden"},
                    { display:"部门编码", name:"deptCode", newline:true, labelWidth:100, width:220, space:30, type:"text", validate:{ required:true, maxlength:50} },
                    { display:"部门名称", name:"deptName", newline:true, labelWidth:100, width:220, space:30, type:"text", validate:{required:true, maxlength:50} },
                    { display:"上级部门", name:"parentDept", newline:true, labelWidth:100, width:220, space:30, type:"text", attr:{readonly:"readonly"}}
                ],
                toJSON:JSON2.stringify
            });

            $("#parent").attr("readonly", "readonly");

            detailWin = $.ligerDialog.open({
                target:$("#detail"),
                width:450, height:200, top:90,
                buttons:[
                    { text:'确定', onclick:function () {
                        save();
                    } },
                    { text:'取消', onclick:function () {
                        detailWin.hide();
                    } }
                ]
            });
        }
        if (curentData) {
            $("#deptCode").val(curentData.deptCode);
            $("#deptName").val(curentData.deptName);
            $("#parentDept").val(curentData.parentDept);
            $("#parent").val(curentData.parentCode);
        }

        $("#deptCode").attr("readonly", "readonly");
    }
    function validate() {
        if (!LG.validator.form()) {
            LG.showInvalid();
            return false;
        }
        return true;
    }
    function save() {
        if (!validate()) {
            return;
        }
        curentData = curentData || {};
        curentData.deptName = $("#deptName").val();
        curentData.deptCode = $("#deptCode").val();
        curentData.parent = $("#parent").val();
        LG.ajax({
            url:currentIsAddNew ? '<c:url value="/security/dept/save"/>' : '<c:url value="/security/dept/update"/>',
            loading:'正在保存数据中...',
            data:curentData,
            success:function () {
                detailWin.hide();
                grid.loadData();
                LG.tip('保存成功!');
            },
            error:function (message) {
                LG.tip(message);
            }
        });
    }
</script>
</body>
</html>
