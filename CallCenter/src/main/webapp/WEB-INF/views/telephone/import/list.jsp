<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
    .ui-autocomplete-loading {
        background: white url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>') right center no-repeat;
    }
</style>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<input type="hidden" id="fileName"/>
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
<div id="maingrid"></div>
<div id="upload" style="display:none;">
    <div id="uploader">
        <p>Your browser doesn't have Flash, Silverlight, Gears, BrowserPlus or HTML5 support.</p>
    </div>
    <br/>
    <div>
    	是否去重:
    	<input name="fldDuplicateStatus" type="radio" value="0"/>去重
    	<input name="fldDuplicateStatus" type="radio" value="1"/>不去重
    </div>
</div>
<script type="text/javascript">
	var telephoneTemplate = "话单导入模板";
	
	//搜索表单应用ligerui样式
	$("#formsearch").ligerForm({
	    labelWidth: 100,
	    inputWidth: 150,
	    space: 30,
	    fields: [
	        {display: "操作人", name: "fldOperateUserNo", newline: true, type: "text", cssClass: "field"},
	        {display: "开始时间", name: "startDate", newline: true, type: "date", cssClass: "field",
	        	attr:{op:'greaterorequal', vt:'date', field:"fldOperateDate"}},
	        {display: "结束时间", name: "endDate", newline: false, type: "date", cssClass: "field",
	        	attr:{op:'lessorequal', vt:'date', field:"fldOperateDate"}}
	    ],
	    toJSON: JSON2.stringify
	});
	
	//列表结构
	var grid = $("#maingrid").ligerGrid({
	    checkbox: true,
	    rownumbers: true,
	    delayLoad: true,
	    columnWidth: 180,
	    columns: [
	    	{display: "ID", name: "fldId", hide:1,width:1},
	        {display: "操作人", name: "fldOperateUserNo"},
	        {display: "导入时间", name: "fldCreateDate"},
	        {display: "最近操作时间", name: "fldOperateDate"},
	        {display: "重复记录数", name: "fldDuplicateTotalNumber"},
	        {display: "导入记录数", name: "fldImportTotalNumber"},
	        {display: "已分配记录数", name: "fldAssignTotalNumber"}
	    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/import/list"/>', sortName: 'operateDate', sortOrder: 'desc',
	    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
	});

	//增加搜索按钮,并创建事件
	LG.appendSearchButtons("#formsearch", grid, true, true);

	//加载toolbar
	LG.loadToolbar(grid, toolbarBtnItemClick);

	//工具条事件
	function toolbarBtnItemClick(item) {
	    switch (item.id) {
	        case "import":
	            f_upload();
	            break;
	        case "template":
	        	f_template(telephoneTemplate);
	        	break;
	    }
	}

	$("#uploader").plupload({
    	runtimes: 'flash',
    	url: '<c:url value="/telephone/import/upload"/>',
    	//max_file_size: '5mb',
    	max_file_count: 1,
    	//chunk_size: '1mb',
    	rename: true,
    	multiple_queues: false,
   	 	resize: {width: 600, height: 500, quality: 30},
    	sortable: true,
    	filters: [
        	{title: "Excel files", extensions: "xls"}
    	],
    	flash_swf_url: '<c:url value="/static/plupload/plupload.flash.swf" />'
	});
		
	uploader = $('#uploader').plupload('getUploader');

	uploader.bind('ChunkUploaded', function (uploader, file, response) {
    	if (response.response) {
        	var rep = JSON.parse(response.response);
        	if (rep.IsError == false) {
            	//uploader.removeFile(file);
            	//uploader.stop();
            	//detailWin.hide();
            	//LG.showSuccess(rep.Message);
            	$("#fileName").val(rep.Message);
            	//f_reload();
        	} else {
            	LG.showError(rep.Message);
        	}
    	}
	});
	
	var detailWin = null;
	function f_upload() {
	    if (detailWin) {
	        detailWin.show();
	    } else {
	        detailWin = $.ligerDialog.open({
	            title: '话单导入',
	            target: $("#upload"),
	            width: 600, height: 420, top: 90,
	            buttons: [
	            	{
	            		text:"确定",onclick:function() {
	            			var fileName = $("#fileName").val();
	            			if(fileName == "") {
	            				LG.showError("请上传文件!");
	            				return;
	            			}
	            			
	            			var fldDuplicateStatus = $("input[name='fldDuplicateStatus']:checked").val();	
	            			if(!fldDuplicateStatus) {
	            				LG.showError("请选择是否去重!");
	            				return;
	            			}
	            			
	            			LG.ajax({
			                	url: '<c:url value="/telephone/import/save"/>',
				                data: {fileName:fileName,fldDuplicateStatus:fldDuplicateStatus},
				                beforeSend: function () {
				                	
				                },
				                complete: function () {
				                },
				                success: function () {
            						uploader.stop();
            						detailWin.hide();
            						LG.showSuccess("导入话单成功!");
            						f_reload();
				                },
				                error: function (message) {
						            LG.showError(message);
				                }
				            });
	            		}
	            	},
	                { text: '取消', onclick: function () {
	                    detailWin.hide();
	                	}
	                }
	            ]
	        });
	    }
	}
	
    function f_template(filename) {
        window.location.href = '<c:url value="/telephone/import/template"/>' + '/' +filename;
    }

	function f_reload() {
	    grid.loadData();
	}
</script>
</body>
</html>