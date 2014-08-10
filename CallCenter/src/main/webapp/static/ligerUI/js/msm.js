LG.msm = function () {
    var msmPanle;
    var templateList = [];
    $(document).bind('keydown.msm', function (e) {
        if (e.keyCode == 13) {
            doMsm();
        }
    });

    function loadTemplate(){
        LG.ajax({
            url:GLOBAL_CTX + "/message/template/listAll",
            datType:"json",
            success:function (result) {
                templateList = result;
                if (templateList.length > 0) {
                    $.ligerui.get("messageTemplateId").setData(templateList);
                }
            },
            error:function (message) {
                LG.showError(message);
            }
        });
    }

    if (!window.msmWin) {
        msmPanle = $("<form></form>");
        msmPanle.ligerForm({
            fields: [
                { display: '手机号', name: 'fldMobiles', type: 'text', validate: { maxlength: 11, required: true, messages: { required: '请输入手机号'}} },
                {
                    display: "短信模板",
                    name: "fldMessageTemplateId",
                    newline: true,
                    type: "select",
                    comboboxName: "messageTemplateId",
                    options: {
                        valueFieldID: "messageTemplateId",
                        valueField:"fldId", textField:"fldName"
                    }
                },
                {
                    display : "内容",
                    name : "fldContent",
                    newline : true,
                    type : "textarea",
                    width : 400,
                    attr:{"cols":"63"},
                    validate : {
                        required : true,
                        maxlength : 500
                    }
                }
            ]
        });

        loadTemplate();

        $.ligerui.get("messageTemplateId").bind("selected", function (value, text) {
            if (templateList.length > 0) {
                var template = $.grep(templateList, function (data, i) {
                    return (data.fldId == value);
                });

                if (template && template.length > 0) {
                    $("#fldContent").text(template[0].fldContent);
                }
            }
        });

        //验证
        LG.validate(msmPanle);

        window.msmWin = $.ligerDialog.open({
            width: 600,
            height: 250, top: 200,
            isResize: true,
            modal:false,
            title: '短信发送',
            target: msmPanle,
            buttons: [
                { text: '发送', onclick: function () {
                    doMsm();
                }
                },
                { text: '取消', onclick: function () {
                    window.msmWin.hide();
                    cleanForm();
                    $(document).unbind('keydown.msm');
                }
                }
            ]
        });


    }
    else {
        window.msmWin.show();
    }

    $("#fldMobiles").focus();

    function cleanForm() {
        msmPanle[0].reset();
        $("#fldContent").text("");
    }

    function doMsm() {
        if (msmPanle.valid()) {
            $("#fldContent").text($("#fldContent").val()+"【聚金金融】");

            LG.ajax({
                url:GLOBAL_CTX + '/message/operate/save',
                data:{ fldMobiles:$("#fldMobiles").val(), fldContent:$("#fldContent").val() },
                success:function () {
                    LG.showSuccess('发送成功');
                    window.msmWin.hide();
                    $(document).unbind('keydown.dial');
                    cleanForm();
                },
                error:function (message) {
                    LG.showError(message);
                }
            });
        }
    }
};