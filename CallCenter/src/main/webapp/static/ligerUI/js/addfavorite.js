LG.addfavorite = function (success)
{
    $(document).bind('keydown.addfavorite', function (e)
    {
        if (e.keyCode == 13)
        {
            doAddFavorite();
        }
    });

    if (!window.addfavoriteWin)
    {
        var addfavoritePanle = $("<form name='mainform' id='mainform'></form>");

        var menusTree = {
            id: 'addfavoriteMenusTree',
            url: GLOBAL_CTX+'/security/favorite/getMyMenu',
            checkbox: false,
            nodeWidth: 220,
            textFieldName: "name",
            childIcon: "icon",
            parentIcon:"icon"
        };

        addfavoritePanle.ligerForm({
            fields: [
                 { display: "页面", name: "nodeCode", newline: true, labelWidth: 100, width: 220, space: 30, type: "select", comboboxName: "MyMenusMenuID",
                     options: { id: 'MyMenusMenuID', treeLeafOnly: true, tree: menusTree,valueFieldID: "nodeCode",textField:"name", valueField: "code" },
                     validate: { required: true, messages: { required: '请选择页面'} }
                 },

                 { display: "收藏备注", name: "comment", newline: true, labelWidth: 100, width: 220, space: 30, type: "textarea" }

            ]
        });

        //验证
        LG.validate(addfavoritePanle);

        window.addfavoriteWin = $.ligerDialog.open({
            width: 400,
            height: 190, top: 150, left: 230,
            isResize: true,
            title: '增加收藏',
            target: addfavoritePanle,
            buttons: [
            { text: '确定', onclick: function ()
            {
                doAddFavorite();
            }
            },
            { text: '取消', onclick: function ()
            {
                window.addfavoriteWin.hide();
                $(document).unbind('keydown.addfavorite');
            }
            }
            ]
        });
    }
    else
    {
        window.addfavoriteWin.show();
    }

    function doAddFavorite()
    { 
        var manager = $.ligerui.get("MyMenusMenuID"); 
        if (addfavoritePanle.valid() && manager)
        {
            LG.ajax({
                url: GLOBAL_CTX+'/security/favorite/save?1=1',
                data: { "node.code": manager.getValue(), comment: $("#comment").val() },
                success: function ()
                {
                    LG.showSuccess('收藏成功');
                    $("#mainform")[0].reset();
                    window.addfavoriteWin.hide();
                    $(document).unbind('keydown.addfavorite');
                    if (success)
                    {
                        success();
                    }
                },
                error: function (message)
                {
                    LG.showError(message);
                }
            });
        }

    }

};