<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="product"></form:form>
<div position="bottom" title="产品明细">
	<div id="dtlDiv"><form:form id="detailform" name="detailform" method="post" modelAttribute="productDetail"></form:form></div>
</div>
<script type="text/javascript">
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
    
    var detailForm = $("#dtlDiv");
    detailForm.ligerGrid({
    	checkbox:false,
    	columns:[
    		{display: "产品明细编号", name: "fldId"}
    	], dataAction: 'server', pageSize: 20, url: '<c:url value="/customer/product/list"/>', sortName: 'operateDate', sortOrder: 'desc',
	    width: '96%', height: '98%', toJSON: JSON2.stringify
    });
</script>
</body>
</html>
