<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<style type="text/css">
.fullscreenBlock{
    display: none;
    height: 100%;
    width: 100%;
    overflow: hidden;
    position: absolute;
    z-index: 10000;
}
.backgroundBlock{
    background: gray;
    opacity: 0.4;
    height: 100%;
    width: 100%;
}
.loading{
    background: url('/static/ligerUI/images/loading.gif') no-repeat;
    position: absolute;
    left: 650px;
    top: 300px;
    height: 48px;
    width: 48px;
    z-index: 10001;
}
</style>
<body style="padding-bottom:31px;">
<div id="layout" style="margin:2px; margin-right:3px;">
    <div id="loadingBlock" class="fullscreenBlock">
        <div class="backgroundBlock"></div>
        <div id="loading" class="loading"></div>
    </div>
    <div position="center" id="mainmenu">
        <form id="mainform" method="post"></form>
        <br/>
        <div style="display: block; width: 100%; height: 30px; position: absolute; margin: 7px 0;">
            <span style="text-align: left; padding: 2px 0px; display: inline-block; width: 106px; margin-left: 7px;">附件上传:</span>
            <input type="file" style="" name="customerFile" id="customerFile">
        </div>
    </div>
    <div position="bottom" title="联系人">
        <div id="contactgrid"></div>
    </div>
    <div id="detail" style="display:none;">
        <form id="detailMainform" method="post"></form>
        <br/>
        <div style="display: block; width: 100%; height: 30px; position: absolute; margin: 7px 0;">
			<span style="text-align: left; padding: 2px 0px; display: inline-block; width: 95px; margin-left: 7px;">附件上传:</span>
			<input type="file" id="contactFile" name="contactFile"/>
		</div>
    </div>
</div>
<textarea cols="50" rows="10" id="tmpComment" name="tmpComment" style="display: none">${customer.comment}</textarea>
<script type="text/javascript">
var currentEditRow = null;
var currentEditRowDom = null;
var grid = null;

var riskLevel = <sys:dictList type="35"/>;
var customerType=<sys:dictList type="52" />;

var checkSubmitDisabled = false;
//覆盖本页面grid的loading效果
LG.overrideGridLoading();

function showLoading(){
    $("#loadingBlock").show();
}
function hideLoading(){
    $("#loadingBlock").hide();
}
//表单底部按钮
LG.setFormDefaultBtn(f_cancel, f_save);

var layout = $("#layout").ligerLayout({
    //5分之3的高度
    bottomHeight:2 * $(window).height() / 5,
    heightDiff:-6,
    onEndResize:updateGridHeight,
    onHeightChanged:updateGridHeight
});
var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");

var toolbarOptions = {
    items:[
        {
            id:'add', text:'新增',
            img:'<c:url value="/static/ligerUI/icons/silkicons/add.png"/>',
            click:function (item) {
                addNewRow();
            }
        },
        {
            id:'modify', text:'修改',
            img:'<c:url value="/static/ligerUI/icons/silkicons/add.png"/>',
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

var customerServiceId = "${customer.serviceId}";
var customerMarketId = "${customer.marketId}";

//创建表单结构
var mainform = $("#mainform");
mainform.ligerForm({
    inputWidth:280,
    labelWidth:110,
    space:30,
    fields:[
        {name:"id", type:"hidden", attr:{value:"${customer.id}"}},
        {name:"serviceId", type:"hidden", attr:{value:"${customer.serviceId}"}},
        {display:"客户名称", name:"fullName", newline:true, type:"text", attr:{value:"${customer.fullName}"}, validate:{required:true, maxlength:60}, group:"客户基本信息", groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
        {display:"客户简称", name:"shortName", newline:false, type:"text", attr:{value:"${customer.shortName}"}, validate:{ maxlength:60}},
        {display:"财务名称", name:"financialName", newline:true, type:"text", attr:{value:"${customer.financialName}"}, validate:{maxlength:60}},
        {display:"客户类型", name:"type", newline:false, attr:{value:"${customer.type}"}, type:"select", comboboxName:"typeName", options:{
            valueFieldID:"type",
            valueField:"value",
            textField:"text",
            initValue:"${customer.type}",
            data:customerType
        }},
        {display:"销售", name:"marketId", newline:true, type:"select", comboboxName:"marketName", options:{valueFieldID:'marketId'}, validate:{required:true}},
        {display:"客服", name:"serviceUserName", newline:false, type:"text", attr:{value:"${customer.serviceUserName}", readonly:"readonly"}},
        {display:"备注", name:"comment", newline:true, width:700, type:"text", validate:{maxlength:1024}}
    ],
    toJSON:JSON2.stringify
});

$.ligerui.get("fullName").bind("blur", function () {
    var fullName = $("#fullName").val();
    if (fullName != ''&&fullName!="${customer.fullName}") {
        LG.ajax({
            url:'<c:url value="/order/customer/isExist"/>',
            data:{ fullName:fullName},
            beforeSend:function () {
            },
            complete:function () {
            },
            success:function () {
            },
            error:function (message) {
                LG.showError(message);
            }
        });
    }

});

var commentElement = $("[name=" + ("" + "comment") + "]", mainform);
commentElement.val($("#tmpComment").val());

$.ligerui.get("marketName").openSelect({
    grid:{
        columns:[
            {display:"用户名称", name:"userName", width:300},
            {display:"登录名称", name:"loginName", width:300}
        ], pageSize:20,heightDiff:-10,
        url:'<c:url value="/security/user/list"/>', sortName:'userName', checkbox:false
    },
    search:{
        fields:[
            {display:"用户名称", name:"userName", newline:true, type:"text", cssClass:"field"}
        ]
    },
    valueField:'id', textField:'userName', top:30
});
$.ligerui.get("marketName")._changeValue('${customer.marketId}', '${customer.marketUserName}');

grid = $("#contactgrid").ligerGrid({
    columns:[
        { display:"部门名称", name:"contacterDept", align:'right', width:140,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"有无附件", name:"attach", align:'right', width:140,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }, render:function (item) {
            if (item.contacterAttachPath == '' || !item.contacterAttachPath) {
                return "无";
            }
            return "有";
        }},
        { display:"联系人名称", name:"contacterName", align:'right', width:100,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"地址", name:"contacterAddress", align:'right', width:120,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"销售姓名", name:"marketUserName", width:120},
        { display:"客服姓名", name:"serviceUserName", width:120},
        { display:"电话", name:"contacterPhone", align:'right', width:120,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"身份证号", name:"contacterIdNumber", align:'right', width:140,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"职位", name:"contacterPosition", align:'right', width:120,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"手机", name:"contacterMobile", align:'right', width:120,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"传真", name:"contacterTax", align:'right', width:120,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"邮箱", name:"contacterEmail", align:'right', width:120,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
        { display:"附件", name:"contacterAttachPath", align:'right', width:120,
            minWidth:60, type:'text', isSort:false, editor:{ type:'text' }}
    ], dataAction:'server', pageSize:20, toolbar:toolbarOptions,
    width:'98%', height:'98%',
    url:'<c:url value="/order/contactor/listWithUser?customerId=${customer.id}"/>',
    checkbox:false, usePager:false, enabledEdit:false,
    fixedCellHeight:true, rowHeight:25
});

mainform.attr("action", "<c:url value="/order/customer/update"/>");

//验证
LG.validate(mainform);

function f_loaded() {
    return;
}
function f_save() {
    if (checkSubmitDisabled) {
        LG.tip('请不要重复提交');
        return;
    }
    var win = parent || window;
    var data = grid.getData();

    if(!$("#marketId").val()){
        LG.showError("请选择销售");
        return;
    }
    var formParamList = $("#mainform").formToArray();
    var paramList = convertToRequestParam(formParamList);

    if (mainform.valid()) {
        checkSubmitDisabled = true;
        LG.ajax({
            url:'<c:url value="/order/customer/update"/>',
            data:paramList,
            dataType:'json', type:'post',
            success:function (data) {                
                uploadFileForCustomer(function(data){
                    checkSubmitDisabled = false;
                    LG.tip('保存成功');
                    win.LG.closeAndReloadParent(null, '${menuNo}');
                }, function(data){
                    checkSubmitDisabled = false;
                    LG.showError(data);
                }, $("#fullName").val(), data[0]);
            },
            error:function (message) {
                checkSubmitDisabled = false;
                LG.showError(message);
            }
        });

    } else {
        checkSubmitDisabled = false;
        LG.showInvalid();
    }
}
function f_cancel() {
    checkSubmitDisabled = false;
    var win = parent || window;
    win.LG.closeCurrentTab(null);
}

function uploadFileForCustomer(successCallback, failureCallback, customerName, customerId){
    if($("#customerFile").val() == ""){
        successCallback();
        return;
    }
    var url = '<c:url value="/order/customer/customerUpload"/>';
    if(!customerName || customerName == ""){
        failureCallback();
        return;
    }
    url += "?customerName="+customerName;
    if(customerId && customerId != ""){
        url += "&customerId="+customerId;
    } 
    $.ajaxFileUpload({
		url:url,
		secureuri:false,
		fileElementId:'customerFile',
		dataType: 'json',
		beforeSend:function(){
			showLoading();
		},
		complete:function(){
			hideLoading();
		},				
		success: function (data, status){
			if(typeof(data.error) != 'undefined'){
				if(data.error != ''){
				}else{
		            successCallback();
				}
			}else{
            	successCallback();
			}
		},
		error: function (data, status, e){
            failureCallback();
		}
	});
	
	return false;
}
function uploadFileForContacter(contacterName, successCallback, failureCallback){
    if(!contacterName || contacterName == ""){
        failureCallback();
        return;
    }
    if($("#contactFile").val() == ""){
        failureCallback();
        return;
    }
    $.ajaxFileUpload({
    	url:'<c:url value="/order/customer/contacterUpload?contacterName="/>'+contacterName,
		secureuri:false,
		fileElementId:'contactFile',
		dataType: 'json',
		beforeSend:function(){
		},
		complete:function(){
		},
		success: function (data, status){
			if(typeof(data.error) != 'undefined'){
				if(data.error != ''){
				}else{
		            successCallback({
		                attachFile: data.Data[0]
		            });
				}
			}else{
	            successCallback({
	                attachFile: data.Data[0]
	            });
			}
		},
		error: function (data, status, e){
            failureCallback();
		}
	});
	
	return false;
}
function convertToRequestParam(form) {
    var obj = {};
    for (var i = 0; i < form.length; i++) {
        obj[form[i].name] = form[i].value ? form[i].value : "";
    }
    return obj;
}

function updateGridHeight() {
    var topHeight = $("#layout > .l-layout-center").height();
    var bottomHeight = $("#layout > .l-layout-bottom").height();
}

function addNewRow() {
    showDetail(true);
}

function editRow() {
    showDetail(false);
}

function f_reload(){
    grid.loadData();
}

function f_delete() {
    var selected = grid.getSelected();
    if (selected) {
        LG.ajax({
            url:'<c:url value="/order/contactor/delete"/>',
            loading:'正在删除中...',
            data:{ id: selected.id },
            success:function () {
                LG.showSuccess('删除成功');
                f_reload()
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

var detailWin = null, curentData = null, currentIsAddNew, detailMainform;
function showDetail(isNew) {
    var deptDuplicate = false;
    var errorMsg = "";
    var id,name, idNumber, dept, position,address, phone, mobile, tax, email, serviceId, marketId, marketUserName;
    var obj = currentEditRow;
    var objDom = currentEditRowDom;
    currentIsAddNew = isNew;
    if (obj) {
        id = obj.id ? obj.id : "";
        name = obj.contacterName ? obj.contacterName : "";
        idNumber = obj.contacterIdNumber ? obj.contacterIdNumber : "";
        dept = obj.contacterDept ? obj.contacterDept : "";
        position = obj.contacterPosition ? obj.contacterPosition : "";
        address = obj.contacterAddress ? obj.contacterAddress : "";
        phone = obj.contacterPhone ? obj.contacterPhone : "";
        mobile = obj.contacterMobile ? obj.contacterMobile : "";
        tax = obj.contacterTax ? obj.contacterTax : "";
        email = obj.contacterEmail ? obj.contacterEmail : "";
        serviceId = obj.serviceId ? obj.serviceId : "";
        marketId = obj.marketId ? obj.marketId : "";
        marketUserName = obj.marketUserName ? obj.marketUserName : "";
    } else {
        id = "";
        name = "";
        idNumber = "";
        dept = "";
        position = "";
        address = "";
        phone = "";
        mobile = "";
        tax = "";
        email = "";
        serviceId = "";
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
                { display:"ID", name:"contacterIdForm", type:"hidden"},
                { display:"部门名称", name:"contacterDeptForm", newline:true,
                    width:180, space:30, type:"text", validate:{required:true,maxlength:64}, cssClass:"field", group:"部门信息",
                    groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
                {display:"销售", name:"marketIdForm", newline:false, type:"select", comboboxName:"contacterMarketName", options:{valueFieldID:'marketIdForm'}, validate:{required:true}},
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

        <%--$.ligerui.get("contacterNameForm").bind("blur", function () {--%>
            <%--var contacterName = $("#contacterNameForm").val();--%>
            <%--if (contacterDept != ''&&contacterDept!=dept) {--%>
                <%--LG.ajax({--%>
                    <%--url:'<c:url value="/order/contactor/isExist"/>',--%>
                    <%--data:{ contacterDept:contacterDept, customerId:"${customer.id}"},--%>
                    <%--success:function () {--%>
                        <%--deptDuplicate = false;--%>
                        <%--errorMsg = "";--%>
                    <%--},--%>
                    <%--error:function (message) {--%>
                        <%--errorMsg = message;--%>
                        <%--LG.showError(message);--%>
                        <%--deptDuplicate = true;--%>
                    <%--}--%>
                <%--});--%>
            <%--}--%>
        <%--});--%>

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
                        var contacterName = $("#contacterNameForm").val();
                        for (var i = 0; i < data.length; i++) {
                            if (contacterName == data[i].contacterName) {
                                LG.showError("联系人名称重复");
                                return;
                            }
                        }
                    }

                    if(!$("#marketIdForm").val()){
                        LG.showError("请选择销售");
                        return;
                    }
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

    $("#contacterIdForm").val(id);
    $("#contacterNameForm").val(name);
    $("#contacterIdNumberForm").val(idNumber);
    $("#contacterDeptForm").val(dept);
    $("#contacterPositionForm").val(position);
    $("#contacterAddressForm").val(address);
    $("#contacterPhoneForm").val(phone);
    $("#contacterMobileForm").val(mobile);
    $("#contacterTaxForm").val(tax);
    $("#contacterEmailForm").val(email);
    $("#marketIdForm").val(marketId);
    $.ligerui.get("contacterMarketName")._changeValue(marketId, marketUserName);
}

function saveContacter(data) {
    hideLoading();
    var win = parent || window;

    var obj = {};
    if (detailMainform.valid()) {
        obj.contacterId = $("#contacterIdForm").val();
        obj.contacterName = $("#contacterNameForm").val();
        obj.contacterIdNumber = $("#contacterIdNumberForm").val();
        obj.contacterDept = $("#contacterDeptForm").val();
        obj.contacterPosition = $("#contacterPositionForm").val();
        obj.contacterAddress = $("#contacterAddressForm").val();
        obj.contacterPhone = $("#contacterPhoneForm").val();
        obj.contacterMobile = $("#contacterMobileForm").val();
        obj.contacterTax = $("#contacterTaxForm").val();
        obj.contacterEmail = $("#contacterEmailForm").val();
        obj.marketUserName = $("#contacterMarketName").val();
        obj.customerId = "${customer.id}";
        obj.marketId = $("#marketIdForm").val();
//        obj.serviceId = currentEditRow ? currentEditRow.serviceId : customerServiceId;
//        obj.marketId = currentEditRow ? currentEditRow.marketId : customerMarketId;
        if(data && data.attachFile && data.attachFile != "")
            obj.contacterAttachPath = data.attachFile;


        var url = '<c:url value="/order/contactor/save"/>';
        if(currentIsAddNew==false){
            url = '<c:url value="/order/contactor/update"/>';
        }

        LG.ajax({
            url: url,
            data:obj, type:'post',
            success:function (data) {
                win.LG.showSuccess('保存成功', function () {
                    currentEditRow = null;
                    currentEditRowDom = null;
                    f_reload();
                    detailWin.hide();
                });
            },
            error:function (message) {
                LG.showError(message);
                detailWin.hide();
            }
        });
    }
}
updateGridHeight();
</script>
</body>
</html>

