<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:40px;overflow-y:hidden;">
<div id="layout" style="margin:2px; margin-right:3px;">
	<form:form id="mainform" name="mainform" method="post" modelAttribute="product"></form:form>
	<div position="bottom" title="产品明细">
		<div id="contactgrid"></div>
	</div>
</div>
<script type="text/javascript">
	function updateGridHeight() {
    	var topHeight = $("#layout > .l-layout-center").height();
    	var bottomHeight = $("#layout > .l-layout-bottom").height();
	}

	var layout = $("#layout").ligerLayout({
   	 	bottomHeight:$(window).height() * 3 / 5,
    	heightDiff:0,
    	onEndResize:updateGridHeight,
    	onHeightChanged:updateGridHeight
	});

	var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");

	var genderData =<sys:dictList type = "1"/>;
	
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: [
            {display: "产品编号",name: "fldId", newline: true, type: "text", validate: {required: true}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display: "产品全称", name: "fldFullName", newline: false, type: "text", validate: {required: true}},
            {display: "产品简称", name: "fldShortName", newline: true, type: "date", attr:{readonly: "readonly"}},
            {display: "成立日期", name: "fldEstablishDate", newline: false, type: "date", attr:{readonly: "readonly"}},
            {display: "起息日期", name: "fldValueDate", newline: true, type: "text"},
            {display: "产品描述", name: "fldDescription", newline: false, type: "text", validate: { maxlength: 64}}
        ]
    });

    mainform.attr("action", '<c:url value="/customer/product/save"/>');

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_check);
    
    function f_check() {
    	var fldName = $("#fldName").val();
        if (fldName != '') {
        	var phone = $("#fldPhone").val();
        	var mobile = $("#fldMobile").val();
            if (!phone && !mobile) {
        		LG.showError("请录入固定电话或手机");
        		return;
    		}
        
            LG.ajax({
                url: '<c:url value="/customer/customer/isExist"/>',
                data: {fldName:fldName,phone:phone,mobile:mobile},
                beforeSend: function () {
                	
                },
                complete: function () {
                },
                success: function () {
                	f_save();
                },
                error: function (message) {
		            LG.showError(message);
                }
            });
        } else {
        	f_save();
        }
    }
    
    function f_save() {
    	LG.validate(mainform);
    	
    	LG.submitForm(mainform, function (data) {
	        var win = parent || window;
	        if (data.IsError == false) {
	            win.LG.showSuccess('保存成功', function () {
	                win.LG.closeAndReloadParent(null, "${menuNo}");
	            });
	        } else {
	            win.LG.showError('错误:' + data.Message);
	        }
    	});
    }
    
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
    
    function addNewRow() {
    	showDetail();
	}
	
	function showDetail(isNew) {
    var deptDuplicate = false;
    var errorMsg = "";
    var name, idNumber, dept, position,address, phone, mobile, tax, email, marketId, marketUserName;
    var obj = currentEditRow;
    var objDom = currentEditRowDom;

    if (obj) {
        name = obj.contacterName ? obj.contacterName : "";
        idNumber = obj.contacterIdNumber ? obj.contacterIdNumber : "";
        dept = obj.contacterDept ? obj.contacterDept : "";
        position = obj.contacterPosition ? obj.contacterPosition : "";
        address = obj.contacterAddress ? obj.contacterAddress : "";
        phone = obj.contacterPhone ? obj.contacterPhone : "";
        mobile = obj.contacterMobile ? obj.contacterMobile : "";
        tax = obj.contacterTax ? obj.contacterTax : "";
        email = obj.contacterEmail ? obj.contacterEmail : "";
        marketId = obj.marketId ? obj.marketId : "";
        marketUserName = obj.marketUserName ? obj.marketUserName : "";
    } else {
        name = "";
        idNumber = "";
        dept = "";
        position = "";
        address = "";
        phone = "";
        mobile = "";
        tax = "";
        email = "";
        marketId = "";
        marketUserName = "";
    }


    if (detailWin) {
        detailWin.show();
    }
    else {
        //创建表单结构
        detailMainform = $("#detailMainform");
        detailMainform.ligerForm({
            labelWidth:100,
            inputWidth:180,
            fields:[
                { display:"部门名称", name:"contacterDeptForm", newline:true,
                    width:180, space:30, type:"text", validate:{required:true,maxlength:64}, cssClass:"field", group:"部门信息",
                    groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
                {display:"销售", name:"contacterMarketId", newline:false, type:"select", comboboxName:"contacterMarketName", options:{valueFieldID:'contacterMarketId'}, validate:{required:true}},
                { display:"地址", name:"contacterAddressForm", newline:true, labelWidth:100,
                    width:490, space:30, type:"text", validate:{maxlength:256}, cssClass:"field"},
                { display:"联系人名称", name:"contacterNameForm", newline:true, labelWidth:100,
                    width:180, space:30, type:"text",
                    validate:{required:true, maxlength:32}, cssClass:"field", group:"联系人信息",
                    groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
                { display:"电话", name:"contacterPhoneForm", newline:false, labelWidth:100,
                    width:180, space:30, type:"text",
                    validate:{maxlength:32}, cssClass:"field"},
                { display:"身份证号", name:"contacterIdNumberForm", newline:true, labelWidth:100,
                    width:180, space:30, type:"text", validate:{maxlength:20}, cssClass:"field"},
                { display:"职位", name:"contacterPositionForm", newline:false, labelWidth:100,
                    width:180, space:30, type:"text", validate:{maxlength:30}, cssClass:"field"},
                { display:"手机", name:"contacterMobileForm", newline:true, labelWidth:100,
                    width:180, space:30, type:"text",
                    validate:{maxlength:32}, cssClass:"field"},
                { display:"传真", name:"contacterTaxForm", newline:false, labelWidth:100,
                    width:180, space:30, type:"text", validate:{maxlength:32}, cssClass:"field"},
                { display:"邮箱", name:"contacterEmailForm", newline:true, labelWidth:100,
                    width:180, space:30, type:"text", validate:{email:true}, cssClass:"field"}
            ],
            toJSON:JSON2.stringify
        });

        //提交前校验

        LG.validate(detailMainform);

        $.ligerui.get("contacterMarketName").openSelect({
            grid:{
                columns:[
                    {display:"用户名称", name:"userName", width:300},
                    {display:"登录名称", name:"loginName", width:300}
                ], pageSize:20,heightDiff:-10,
                url:'<c:url value="/security/user/list"/>', sortName:'userName', checkbox:false
            },
            search:{
                fields:[
                    {display:"用户名称",attr:{id:"contacterMarketNameMainSearch"}, name:"userName", newline:true, type:"text", cssClass:"field"}
                ]
            },
            valueField:'id', textField:'userName', top:30
        });

        detailWin = $.ligerDialog.open({
            target:$("#detail"),
            width:700, height:400, top:30,
            buttons:[
                { text:'确定', onclick:function () {
                    if (currentIsAddNew) {
                        var data = grid.getData();
                        var contacterDept = $("#contacterDeptForm").val();
                        for (var i = 0; i < data.length; i++) {
                            if (contacterDept == data[i].contacterDept) {
                                LG.showError("联系人部门重复");
                                return;
                            }
                        }
                    }
                    if(!$("#contacterMarketId").val()){
                        LG.showError("请选择销售");
                        return;
                    }
                    if(deptDuplicate==true){
                        LG.showError(errorMsg);
                        return;
                    }
                    showLoading();
                    uploadFileForContacter($("#contacterNameForm").val(), function(data){
                        saveContacter(data);
                    }, function(data){
                        saveContacter(data);
                    });
                } },
                { text:'取消', onclick:function () {
                    detailWin.hide();
                } }
            ]
        });
    }

    $("#contacterNameForm").val(name);
    $("#contacterIdNumberForm").val(idNumber);
    $("#contacterDeptForm").val(dept);
    $("#contacterPositionForm").val(position);
    $("#contacterAddressForm").val(address);
    $("#contacterPhoneForm").val(phone);
    $("#contacterMobileForm").val(mobile);
    $("#contacterTaxForm").val(tax);
    $("#contacterEmailForm").val(email);
    $.ligerui.get("contacterMarketName")._changeValue(marketId, marketUserName);

}
    
    var toolbarOptions = {
    	items:[
        {
            id:'add', text:'新增',
            img:'<c:url value="/static/ligerUI/icons/silkicons/add.png"/>',
            click:function (item) {
                currentEditRow = null;
                currentEditRowDom = null;
                addNewRow();
            }
        },
        {
            id:'modify', text:'修改',
            img:'<c:url value="/static/ligerUI/icons/miniicons/page_edit.gif"/>',
            click:function (item) {
                currentEditRow = grid.getSelectedRow();
                currentEditRowDom = grid.getSelectedRowObj();
                if (currentEditRow != null) {
                    editRow();
                }
            }
        },
        {
            id:'modify', text:'删除',
            img:'<c:url value="/static/ligerUI/icons/miniicons/page_delete.gif"/>',
            click:function (item) {
                var editingrow = grid.getSelectedRow();
                if (editingrow != null) {
                    $.ligerDialog.confirm('确定删除吗?', function (confirm) {
                        if (confirm)
                            f_delete();
                    });
                } else {
                    LG.tip('请选择行');
                }
            }
        }
    	]
	};
    
    var detailForm = $("#contactgrid");
    detailForm.ligerGrid({
    	checkbox:false,
    	columns:[
    		{display: "产品明细编号", name: "fldId"}
    	], dataAction: 'server', pageSize: 20, toolbar:toolbarOptions, url: '<c:url value="/customer/product/list"/>', sortName: 'operateDate', sortOrder: 'desc',
	    height: '98%', toJSON: JSON2.stringify
    });
    
    updateGridHeight();
</script>
</body>
</html>
