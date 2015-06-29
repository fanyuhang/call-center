<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../../include/formHeader.jsp" %>
<script src="<c:url value="/static/ligerUI/js/DataPrivilageSysParm.js"/>" type="text/javascript"></script>
<script src='<c:url value="/static/ligerUI/ligerUI/js/ligerui.all.js"/>' type="text/javascript"></script>
<style type="text/css">
    #fieldvalues {
        padding: 2px;
        border: 1px solid #d3d3d3;
        margin: 4px;
        background: #f1f1f1;
    }

    .fieldvaluelink {
        float: left;
        display: block;
        margin: 2px;
        margin-right: 4px;
        text-decoration: underline
    }

    .fieldvaluelink:hover {
        color: #FF0505;
    }

    .l-filter-value .valtxt {
        width: 200px;
    }

    table.l-filter-group select, table.l-filter-group .valtxt {
        margin-top: 2px;
    }
</style>
</head>
<body style="padding-bottom:31px;">
<div id="fieldvalues" style="display:none; text-align:left; float:left; clear:both;">
</div>
<form id="mainform" method="post"></form>
<script type="text/javascript">
    var resources;

    $(SysParms).each(function () {
        var link = $('<a class="fieldvaluelink" onclick="javascript:void(0)"></a>');
        link.html(this.display).attr("val", this.name).attr("title", this.name).appendTo('#fieldvalues');
    });
    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);

    //创建表单结构
    var mainform = $("#mainform");

    var formFields = [
        {name:"id", type:"hidden"}
    ];

    var field = {display:"资源", width:400, name:"master", type:"select", comboboxName:"resourceName"};
    field.options = {valueFieldID:"master", valueField:"name", textField:"name"};
    field.validate = {required:true, remote:'<c:url value="/security/dataPrivilege/validate?&rnd="/>' + Math.random(), messages:{required:'请选择资源', remote:'该资源已经设置!'}};
    formFields.push(field);

    formFields.push({display:"条件规则", name:"privilegeRule", type:"textarea"});

    mainform.ligerForm({
        labelWidth:100, inputWidth:720, space:30,
        fields:formFields,
        toJSON:JSON2.stringify
    });

    var masterManager = $.ligerui.get("resourceName");
    $.getJSON('<c:url value="/security/dataPrivilege/getResources?rnd="/>' + Math.random(), function (data) {
        resources = data;
        masterManager.setData(data);
    }, function () {
        LG.showError("加载资源失败");
    });
    masterManager.bind('selected', function (value, text) {
        filter.set('fields', getFields(value));
    });


    //隐藏原来的多行文本框
    $("#privilegeRule").hide();
    //取而代之是一个条件构造器
    var filterPanle = $('<div id="filterPanle"></div>').appendTo($("#privilegeRule").parent());
    $("#fieldvalues").insertAfter(filterPanle).show();

    var filter = filterPanle.ligerFilter({fields:SysParms});


    mainform.attr("action", "<c:url value="/security/dataPrivilege/save"/>");

    //验证

    LG.validate(mainform);

    function f_loaded() {
        return;
    }
    function f_save() {
        var json = JSON2.stringify(filter.getData());
        $("#privilegeRule").val(json);
        LG.submitForm(mainform, function (data) {
            var win = parent || window;
            if (data.IsError) {
                win.LG.showError('错误:' + data.Message);
            }
            else {
                win.LG.showSuccess('保存成功', function () {
                    win.LG.closeAndReloadParent(null, "${menuNo}");
                });
            }
        });
    }
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }

    var currentText;
    //得到焦点
    $(".l-filter-value .valtxt").live('focus', function () {
        currentText = $(this);
    });
    $("a.fieldvaluelink").live('click', function () {
        if (!currentText) {
            currentText = $(".l-filter-value .valtxt:first");
        }
        var val = $(this).attr("val");
        currentText.val(val);
    });
</script>
</body>
</html>

