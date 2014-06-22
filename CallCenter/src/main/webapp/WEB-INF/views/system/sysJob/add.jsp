<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../../include/formHeader.jsp" %>
<body style="padding-bottom:31px;">
<form:form id="mainform" name="mainform" method="post" modelAttribute="sysjob"></form:form>
<script type="text/javascript">
    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel, f_save);
    var statusType = <sys:dictList type="4"/>;
    var triggerType = <sys:dictList type="5"/>;

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth:280,
        labelWidth:110,
        space:30,
        fields:[
            {name:"id", type:"hidden", attr:{value:'${sysjob.id}'}},
            { display:'Job名称', name:'jobName', attr:{value:'${sysjob.jobName}'}, validate:{ required:true, maxlength:32 }, newline:true, type:"text", group:"基本信息", groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            { display:'Job组', name:'jobGroup', attr:{value:'${sysjob.jobGroup}'}, validate:{  maxlength:32 }, newline:false, type:"text"},
            { display:'Trigger名称', name:'triggerName', attr:{value:'${sysjob.triggerName}'}, validate:{ required:true, maxlength:32 }, newline:true, type:"text"},
            { display:'Trigger组', name:'triggerGroup', attr:{value:'${sysjob.triggerGroup}'}, validate:{  maxlength:32 }, newline:false, type:"text"},
            {display:"Trigger类型", name:"triggerType", newline:true, type:"select", type:"select", comboboxName:"triggerTypeName",
                options:{
                    valueFieldID:"triggerType",
                    valueField:"value",
                    textField:"text",
                    initValue:'0',
                    data:triggerType
                }},
            {display:"状态", name:"status", newline:false, type:"select", type:"select", comboboxName:"statusName",
                options:{
                    valueFieldID:"status",
                    valueField:"value",
                    textField:"text",
                    initValue:'0',
                    data:statusType
                }},
            { display:'Job类名', name:'jobClass', attr:{value:'${sysjob.jobClass}'}, validate:{ required:true, maxlength:100 }, newline:true, width:600, type:"text"},
            { display:'表达式', name:'cronExpression', attr:{value:'${sysjob.cronExpression}'}, validate:{ maxlength:32 }, width:600, newline:true, type:"text", group:"周期信息", groupicon:'<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
            { display:'执行时间间隔', name:'intervalInSeconds', attr:{value:'${sysjob.intervalInSeconds}'}, newline:true, type:"int"},
            { display:'重复次数', name:'repeatCount', attr:{value:'${sysjob.repeatCount}'}, newline:false, type:"int"}
        ]
    });
    mainform.attr("action", '<c:url value="/system/sysJob/save"/>');


    LG.validate(mainform);

    function f_save() {
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
</script>
</body>
</html>
