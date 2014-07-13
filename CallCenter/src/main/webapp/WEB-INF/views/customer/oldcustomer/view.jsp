<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>

<script type="text/javascript" src="<c:url value='/static/ligerUI/js/highslide-with-gallery.min.js'/>"></script>
<link rel="stylesheet" type="text/css" href="<c:url value='/static/ligerUI/css/highslide.css'/>" />

<style>
.customerFileImg{
	max-width: 120px;
}
</style>
<script type="text/javascript">
	hs.graphicsDir = "<c:url value='/static/ligerUI/images/highslide/graphics/'/>";
	hs.align = 'center';
	hs.transitions = ['expand', 'crossfade'];
	hs.outlineType = 'rounded-white';
	hs.fadeInOut = true;	
</script>

<body style="padding-bottom:31px;">
<div id="layout" style="margin:2px; margin-right:3px;">
    <div position="center" id="mainmenu">
        <form id="mainform" method="post"></form>
        <div style="display: block; width: 100%; height: 30px; position: absolute; margin: 7px 0;">
            <span style="float:left; text-align: left; padding: 2px 0px; display: inline-block; width: 106px; margin-left: 7px;">附件图片:</span>
            <div style="float:left; display:inline">
                <a id="customerFile" href="#" style="display:none" class="highslide" onclick="return hs.expand(this)">
                    <image src="#" class="customerFileImg" alt="点击放大" title="点击放大s"></image>
                </a>
            </div>
        </div>
    </div>
    <div position="bottom" title="联系人">
        <div id="contactgrid"></div>
    </div>
</div>
<a id="contacterFile" href="#" style="display:none" class="highslide" onclick="return hs.expand(this)">
</a>
<textarea cols="50" rows="10" id="tmpComment" name="tmpComment" style="display: none">${customer.comment}</textarea>
<script type="text/javascript">

    var riskLevel = <sys:dictList type="35"/>;
    var customerType=<sys:dictList type="52" />;
    var customerFilePath = "<c:url value='/order/customer/getCustomerImage?customerId=${customer.id}'/>";
    
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, null);

    var layout = $("#layout").ligerLayout({
        //5分之3的高度
        bottomHeight:2 * $(window).height() / 5,
        heightDiff:-6,
        onEndResize:updateGridHeight,
        onHeightChanged:updateGridHeight
    });
    var bottomHeader = $(".l-layout-bottom > .l-layout-header:first");

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth:280,
        labelWidth:110,
        space:30,
        fields:[
            {name:"id", type:"hidden", attr:{value:"${customer.id}"}},
            {display:"客户名称", name:"fullName", newline:true,   type:"text", attr:{value:"${customer.fullName}"}, validate:{required:true, maxlength:60}, group:"客户基本信息", groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            {display:"客户简称", name:"shortName", newline:false,   type:"text", attr:{value:"${customer.shortName}"}, validate:{required:true, maxlength:60}},
            {display:"财务名称", name:"financialName", newline:true,   type:"text", attr:{value:"${customer.financialName}"}, validate:{required:true, maxlength:60}},
            {display:"客户类型", name:"type", newline:false,   type:"select", comboboxName:"typeName", options:{
                valueFieldID:"type",
                valueField:"value",
                textField:"text",
                initValue:"${customer.type}",
                data:customerType}},
            {display:"销售", name:"marketUserName", newline:true,   type:"text", attr:{value:"${customer.marketUserName}"}, validate:{required:true}},
            {display:"客服", name:"serviceUserName", newline:false,   type:"text", attr:{value:"${customer.serviceUserName}"}},
            {display:"风险级别", name:"riskLevel", newline:true, validate:{required:true}, type:"select", comboboxName:"riskLevelName",
                options:{
                    valueFieldID:"riskLevel",
                    valueField:"value",
                    textField:"text",
                    initValue:'${customer.riskLevel}',
                    data:riskLevel
                }
            },
           {display:"备注", name:"comment", newline:true,  width:700, type:"text", validate:{maxlength:1024}}
        ],
        toJSON:JSON2.stringify
    });

    var commentElement = $("[name=" + ("" + "comment") + "]", mainform);
    commentElement.val($("#tmpComment").val());

    var toolbarOptions = {
        items:[
            {
                id:'display', text:'查看图片',
                img:'<c:url value="/static/ligerUI/icons/silkicons/add.png"/>',
                click:function (item) {
                    var selected = grid.getSelected();
                    getContacterImage(selected.id);
                }
            }
	]};
            
    var grid = $("#contactgrid").ligerGrid({
        columns:[
            { display:"部门名称", name:"contacterDept", align:'right', width:140,
                minWidth:60, type:'text', isSort:false, editor:{ type:'text' }},
            { display:"有无附件", name:"attach", align:'right', width:140,
                minWidth:60, type:'text', isSort:false, editor:{ type:'text' }, render:function (item) {
                var option = "";
                if (item.contacterAttachPath&&item.contacterAttachPath != '') {
                    option += "<a href=\"<c:url value='/order/customer/getContacterAttach?contacterId='/>";
                    option += item.id;
                    option += "\">下载</a>";
                } else {
                    option = "<span style=\"color:#999\">无</span>";
                }
                return option;
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
                minWidth:60, type:'text', isSort:false, editor:{ type:'text' }}
        ], dataAction:'server', pageSize:20, toolbar:toolbarOptions,
        width:'98%', height:'98%', align:'right', columnWidth:150,minWidth:60, isSort:false,
        url:'<c:url value="/order/contactor/listWithUser?customerId=${customer.id}"/>',
        checkbox:false, usePager:false, enabledEdit:false,enabledSort:false,
        fixedCellHeight:true, rowHeight:25
    });

	if("${hasCustomerAttachFile}" == "false"){
		$("#downloadArea").hide();
	}
	
    function f_loaded() {
        //查看状态，控制不能编辑
        $("input,select,textarea", mainform).attr("readonly", "readonly");
    }
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }

    function updateGridHeight() {
        var topHeight = $("#layout > .l-layout-center").height();
        var bottomHeight = $("#layout > .l-layout-bottom").height();
    }

    function getCustomerImagePath(){
    	if(!customerFilePath)
    		return;
    	var tempPath = customerFilePath.replace(/^\s+|\s+$/, '');
    	tempPath = tempPath.replace(/^\s+|\s+$/, '');
    	if(tempPath === "")
    		return;
    	
    	$.ajax({
			url:tempPath,
	    	cache:false,
	        async:true,
	        dataType:'json', type:'post',
	        complete: function(data){
            	if(data.readyState == "4" && data.status == "200"){
            		var responseData = data.responseText;
            		if(responseData && responseData != ""){
            			$("#customerFile").attr("href", responseData);
            			$("#customerFile").find("img").attr("src", responseData);
            			$("#customerFile").show();
            		}
            	}
	        },
	        error: function (message) {
	        }
        });
    }
	
    function getContacterImage(id){
		if(id === "")
			return;
		
		$.ajax({
			url:"<c:url value='/order/customer/getContacterImage?contacterId='/>"+id,
	    	cache:false,
	        async:true,
	        dataType:'json', type:'post',
	        complete: function(data){
	        	if(data.readyState == "4" && data.status == "200"){
	        		var responseData = data.responseText;
            		if(responseData && responseData != ""){
            			$("#contacterFile").attr("href", responseData);
            			$("#contacterFile").show();
            			$("#contacterFile").click();
            		}
	        	}
	        },
	        error: function (message) {
	        }
	    });
    }
    
    updateGridHeight();
    f_loaded();
    
    getCustomerImagePath();
</script>
</body>
</html>

