//坐席登录状态
LG.telephoneStatus = 1;
//是否是来电
LG.isOutCall = 1;
//是否正在通话
LG.isTongHua = 1;

LG.serverIp = '192.168.2.3';

LG.serverPort = 60000;

LG.currentTelephoneRecordId='';

LG.callId='';

LG.dial = function () {
    $(document).bind('keydown.dial', function (e) {
        if (e.keyCode == 13) {
            doDial();
        }
    });

    if (!window.dialWin) {
        var dialPanle = $("<form></form>");
        dialPanle.ligerForm({
            fields: [
                { display: '电话号码', name: 'phone', type: 'text', validate: { maxlength: 16, required: true, messages: { required: '请输入电话号码'}} }
            ]
        });

        //验证
        LG.validate(dialPanle);

        window.dialWin = $.ligerDialog.open({
            width: 400,
            height: 120, top: 200,
            isResize: true,
            modal:false,
            title: '拨打电话',
            target: dialPanle,
            buttons: [
                { text: '拨打', onclick: function () {
                    doDial();
                }
                },
                { text: '取消', onclick: function () {
                    window.dialWin.hide();
                    $(document).unbind('keydown.dial');
                }
                }
            ]
        });
    }
    else {
        window.dialWin.show();
    }

    function cleanForm() {
        $("#phone").val('');
    }

    function doDial() {
        var phone = $("#phone").val();
        if (dialPanle.valid()) {
            LG.ajax({
                url:GLOBAL_CTX + '/telephone/record/saveWithTelephone',
                data:{ telephone:phone },
                success:function (data,message) {
                    LG.call(phone);
                    window.dialWin.hide();
                    cleanForm();
                    LG.currentTelephoneRecordId = data[0];
                },
                error:function (message) {
                    LG.showError(message);
                }
            });
        }
    }
};

LG.call = function (phone) {
	try{
	    var snell = document.getElementById("snocx");
	    LG.isOutCall = 0;
//	    snell.snlMakeCall("9" + phone, "0");
        snell.snlMakeCall(phone, "0");
    }catch(e){}
    $("#telephone").html(LG.hiddenPhone(phone));
    $("#userStatus").removeClass("i-ready");
    $("#userStatus").addClass("i-dialing");
    $("#userStatus").next("b").removeClass("green");
    $("#userStatus").next("b").text("拨号中");
};

LG.connected = function (type) {
    var snell = document.getElementById("snocx");
    if (type == 0) {
        //在通话过程中, 话机转为保持状态
        snell.snlHeldCall();
    } else {
        //在通话过程中, 取回被当前话机保持的那一通电话
        snell.snlRetriveCall();
    }
};

LG.transfer = function () {
    $(document).bind('keydown.transfer', function (e) {
        if (e.keyCode == 13) {
            doTransfer();
        }
    });

    if (!window.transferWin) {
        var dialPanle = $("<form></form>");
        dialPanle.ligerForm({
            fields: [
                { display: '分机号码', name: 'extension', type: 'text', validate: { maxlength: 16, required: true, messages: { required: '请输入分机号码'}} }
            ]
        });

        //验证
        LG.validate(dialPanle);

        window.transferWin = $.ligerDialog.open({
            width: 400,
            height: 120, top: 200,
            isResize: true,
            modal:false,
            title: '转接分机',
            target: dialPanle,
            buttons: [
                { text: '拨打', onclick: function () {
                    doTransfer();
                }
                },
                { text: '取消', onclick: function () {
                    window.transferWin.hide();
                    $(document).unbind('keydown.transfer');
                }
                }
            ]
        });
    }
    else {
        window.transferWin.show();
    }

    function cleanForm() {
        $("#extension").val('');
    }

    function doTransfer() {
        var extension = $("#extension").val();
        if (dialPanle.valid()) {
            var snell = document.getElementById("snocx");
            snell.snlTransferCall(extension);
            window.transferWin.hide();
        }
    }
};

LG.hangup = function () {
    $.ligerDialog.confirm('确定要挂断电话吗？', function (yes) {
        if (yes) {
            var snell = document.getElementById("snocx");
            snell.snlClearCall();
        }
    });
};

LG.mute = function (type) {
    var snell = document.getElementById("snocx");
    //静音模式 0-开, 1-关
    if (type == 0) {
        snell.snlSilentCall(0);
    } else {
        snell.snlSilentCall(1);
    }
};

LG.control = function () {

};
