<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="../header.jsp" %>
<body style="padding-bottom:31px;">
<!--<div id="formBtn" style="display:none; text-align:left; float:left; clear:both;">
    <input id="playRecord" onclick="javascript:listenRecord();" type="button" value="播放录音"
           style="height:20px;width:69px;background:url(/static/ligerUI/icons/silkicons/control_play_blue.png) no-repeat left bottom;text-indent:17px;border:1px solid;border-color:#b1b1b1;cursor:pointer;"/>
    <input id="pauseRecord" onclick="javascript:stopRecord();" type="button" value="停止播放"
           style="height:20px;width:69px;background:url(/static/ligerUI/icons/silkicons/control_pause_blue.png) no-repeat left bottom;text-indent:17px;border:1px solid;border-color:#b1b1b1;cursor:pointer;"/>
</div>-->

<form:form id="mainform" name="mainform" method="post"></form:form>
<!--<bgsound id="sound">-->

<object classid="CLSID:6BF52A52-394A-11d3-B153-00C04F79FAA6" id="MediaPlayer" 
  width="250" height="60" style="margin-left:488px;margin-top:-50px;">
	<param NAME="AutoStart" VALUE="0">
	<param name="uiMode" value="full">
	<param name="volume" value="100">
	<param name="url" value="${phoneRecordAddress}${telephoneRecord.fldRecordFilePath}">
</object>

</body>
<script type="text/javascript">
    var callTypeData = <sys:dictList type = "28"/>;
    var resultTypeData = <sys:dictList type = "27"/>;

    //覆盖本页面grid的loading效果
    LG.overrideGridLoading();

    //表单底部按钮
    LG.setFormDefaultBtn(f_cancel);

    var fields = [
        {display: "ID", name: "fldId", type: "hidden", attr: {value: "${telephoneRecord.fldId}", readonly: "readonly"}},
        {display: "客户姓名", name: "fldName", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCustomerName}", readonly: "readonly"}, group: "<label style=white-space:nowrap;>基本信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
        {display: "拨打号码", name: "fldPhone", newline: false, type: "text", attr: {value: LG.hiddenPhone("${telephoneRecord.fldPhone}"), readonly: "readonly"}},
        {display: "呼叫类型", name: "fldCallType", newline: true, type: "select",
            options: {
                valueField: 'value',
                textField: 'text',
                isMultiSelect: false,
                data: callTypeData,
                initValue: '${telephoneRecord.fldCallType}',
                valueFieldID: "fldCallType"
            }},
        {display: "跟踪结果", name: "fldResultType", newline: false, type: "select",
            options: {
                valueField: 'value',
                textField: 'text',
                isMultiSelect: false,
                data: resultTypeData,
                initValue: '${telephoneRecord.fldResultType}',
                valueFieldID: "fldResultType"
            }},
        {display: "备注", name: "fldComment", newline: true, type: "text", attr: {value: "${telephoneRecord.fldComment}", readonly: "readonly"}},
        {display: "拨打/呼入时间", name: "fldCallDate", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCallDate}", readonly: "readonly"}, group: "<label style=white-space:nowrap;>通话信息</label>", groupicon: '<c:url value="/static/ligerUI/icons/32X32/communication.gif"/>'},
        {display: "通话时长(秒)", name: "fldCallLong", newline: false, type: "text", attr: {value: "${telephoneRecord.fldCallLong}", readonly: "readonly"}},
        {display: "通话开始时间", name: "fldCallBeginTime", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCallBeginTime}", readonly: "readonly"}},
        {display: "通话结束时间", name: "fldCallEndTime", newline: true, type: "text", attr: {value: "${telephoneRecord.fldCallEndTime}", readonly: "readonly"}},
        {display: "录音", name: "fldRecordFilePath", newline: false, type: "text", width: 71, fieldCss: "border:10px;"}
    ];

    //创建表单结构
    var mainform = $("#mainform");
    mainform.ligerForm({
        inputWidth: 250,
        labelWidth: 100,
        space: 30,
        fields: fields
    });

    var panle = $("#fldRecordFilePath").parent();
    $("#fldRecordFilePath").parent().hide();
    $("#pauseRecord").hide();
    $("#formBtn").insertAfter(panle).show();

    function listenRecord() {
        var recordFile = "${phoneRecordAddress}${telephoneRecord.fldRecordFilePath}";
        document.all.sound.src = recordFile;
        $("#playRecord").hide();
        $("#pauseRecord").show();
    }

    function stopRecord() {
        document.all.sound.src = '';
        $("#playRecord").show();
        $("#pauseRecord").hide();
    }

    function f_cancel() {
        var win = parent || window;
        win.LG.closeCurrentTab(null);
    }
</script>
</html>