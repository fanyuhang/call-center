LG.changepassword = function () {
    $(document).bind('keydown.changepassword', function (e) {
        if (e.keyCode == 13) {
            doChangePassword();
        }
    });

    if (!window.changePasswordWin) {
        var changePasswordPanle = $("<form></form>");
        changePasswordPanle.ligerForm({
            fields:[
                { display:'原密码', name:'OldPassword', type:'password', validate:{ maxlength:50, isPassword:true, required:true, messages:{ required:'请输入密码'}} },
                { display:'新密码', name:'NewPassword', type:'password', validate:{ maxlength:50, isPassword:true, required:true, messages:{ required:'请输入密码'}} },
                { display:'确认密码', name:'NewPassword2', type:'password', validate:{ maxlength:50, isPassword:true, required:true, equalTo:'#NewPassword', messages:{ required:'请输入密码', equalTo:'两次密码输入不一致'}} }
            ]
        });

        //验证
        LG.validate(changePasswordPanle);

        window.changePasswordWin = $.ligerDialog.open({
            width:400,
            height:190, top:200,
            isResize:true,
            title:'用户修改密码',
            target:changePasswordPanle,
            buttons:[
                { text:'确定', onclick:function () {
                    doChangePassword();
                }
                },
                { text:'取消', onclick:function () {
                    window.changePasswordWin.hide();
                    $(document).unbind('keydown.changepassword');
                }
                }
            ]
        });
    }
    else {
        window.changePasswordWin.show();
    }

    function cleanForm() {
        $("#OldPassword").val('');
        $("#NewPassword").val('');
        $("#NewPassword2").val('');
    }

    function doChangePassword() {
        var OldPassword = $("#OldPassword").val();
        var LoginPassword = $("#NewPassword").val();
        if (changePasswordPanle.valid()) {
            LG.ajax({
                url:GLOBAL_CTX + '/user/changePassword',
                data:{ oldPassword:OldPassword, loginPassword:LoginPassword },
                success:function () {
                    LG.showSuccess('密码修改成功');
                    window.changePasswordWin.hide();
                    $(document).unbind('keydown.changepassword');
                    cleanForm();
                },
                error:function (message) {
                    LG.showError(message);
                }
            });
        }
    }
};

LG.resetpassword = function (userId) {
    $(document).bind('keydown.resetpassword', function (e) {
        if (e.keyCode == 13) {
            doResetPassword();
        }
    });

    if (!window.resetPasswordWin) {
        var resetPasswordPanle = $("<form></form>");
        resetPasswordPanle.ligerForm({
            fields:[
                { display:'新密码', name:'newPassword', type:'password', validate:{ maxlength:50, isPassword:true, required:true, messages:{ required:'请输入密码'}} },
                { display:'确认密码', name:'newPassword2', type:'password', validate:{ maxlength:50, isPassword:true, required:true, equalTo:'#newPassword', messages:{ required:'请输入密码', equalTo:'两次密码输入不一致'}} }
            ]
        });

        //验证
        LG.validate(resetPasswordPanle);

        window.resetPasswordWin = $.ligerDialog.open({
            width:350,
            height:150, top:200,
            isResize:true,
            title:'重置用户密码',
            target:resetPasswordPanle,
            buttons:[
                { text:'确定', onclick:function () {
                    doResetPassword();
                }
                },
                { text:'取消', onclick:function () {
                    window.resetPasswordWin.hide();
                    $(document).unbind('keydown.resetpassword');
                }
                }
            ]
        });
    }
    else {
        window.resetPasswordWin.show();
    }

    function cleanForm() {
        $("#newPassword").val('');
        $("#newPassword2").val('');
    }

    function doResetPassword() {
        var LoginPassword = $("#newPassword").val();
        if (resetPasswordPanle.valid()) {
            LG.ajax({
                url:GLOBAL_CTX + '/security/user/resetPassword',
                data:{userId:userId, loginPassword:LoginPassword },
                success:function () {
                    LG.showSuccess('密码重置成功');
                    window.resetPasswordWin.hide();
                    $(document).unbind('keydown.resetpassword');
                    cleanForm();
                },
                error:function (message) {
                    LG.showError(message);
                }
            });
        }
    }
};
