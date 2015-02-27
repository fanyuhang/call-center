<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<script src='<c:url value="/static/ckeditor/ckeditor.js"/>' type="text/javascript"></script>
<script src='<c:url value="/static/ckeditor/adapters/jquery.js"/>' type="text/javascript"></script>
<script src='<c:url value="/static/ckfinder/ckfinder.js"/>' type="text/javascript"></script>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="notice"></form:form>
<script type="text/javascript">
    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);
    var statusType = <sys:dictList type="32"/>;

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth:280,
        labelWidth:110,
        space:30,
        fields:[
            {display:"状态", name:"fldStatus", newline:false,type:"hidden",attr:{value:'0'}},
            { display:'产品名称', name:'fldTitle', validate:{ required:true, maxlength:500 }, newline:true, type:"text", group:"基本信息", groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            { display:'内容', name:'fldContent', validate:{ required:true }, width: '100%',newline:false, type:"textarea", group:"其他信息", groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'}
        ]
    });
    mainform.attr("action", '<c:url value="/telephone/product/save"/>');

    LG.validate(mainform);

    function f_save() {
        $("#fldContent").text(editorContent.getData().replace(/\n/g,"" ));
        LG.submitForm(mainform, function (data) {
            var win = parent || window;
            if (data.IsError == false) {
                win.LG.showSuccess('保存成功', function () {
                    win.LG.closeAndReloadParent(null, "${menuNo}");
                });
            }
            else {
                win.LG.showError('错误:' + data.Message);
            }
        });
    }
    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }

    var editorContent = null;
    window.onload = function() {
        editorContent = CKEDITOR.replace( 'fldContent', {} );
        CKFinder.setupCKEditor( editorContent, '<c:url value="/static/ckfinder/"/>' );
    };
</script>
</body>
</html>
