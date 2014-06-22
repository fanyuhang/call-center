//处理键盘事件 禁止后退键（Backspace）密码或单行、多行文本框除外
function forbidBackSpace(e) {
    var ev = e || window.event; //获取event对象
    var obj = ev.target || ev.srcElement; //获取事件源
    var t = obj.type || obj.getAttribute('type'); //获取事件源类型
    //获取作为判断条件的事件类型
    var vReadOnly = obj.readOnly;
    var vDisabled = obj.disabled;
    //处理undefined值情况
    vReadOnly = (vReadOnly == undefined) ? false : vReadOnly;
    vDisabled = (vDisabled == undefined) ? true : vDisabled;
    //当敲Backspace键时，事件源类型为密码或单行、多行文本的，
    //并且readOnly属性为true或disabled属性为true的，则退格键失效
    var flag1 = ev.keyCode == 8 && (t == "password" || t == "text" || t == "textarea") && (vReadOnly == true || vDisabled == true);
    //当敲Backspace键时，事件源类型非密码或单行、多行文本的，则退格键失效
    var flag2 = ev.keyCode == 8 && t != "password" && t != "text" && t != "textarea";
    //判断
    if (flag2 || flag1) return false;
}

function generateYear() {
    var year = [];
    var begin = 2010;
    var end = 2020;
    for (var i = 2010; i <= end; i++) {
        var data = {"value":i, "text":i + '年'};
        year.push(data);
    }
    return year;
}

function generateMonth() {
    var month = [];
    for (var i = 1; i <= 12; i++) {
        var data = {"value":(i - 1), "text":i + '月'};
        month.push(data);
    }
    return month;
}

function generateDay() {
    var days = 31;

    var day = [];
    for (var i = 1; i <= days; i++) {
        day.push({"value":i, "text":i + '号'})
    }
    return day;
}

function generateHour() {
    var hour = [];
    for (var i = 0; i < 24; i++) {
        hour.push({"value":i, "text":i + '点'});
    }
    return hour;
}

function renderLabel(data, valueFieldId) {
    var label;
    $.each(data, function (idx, item) {
        if (item.value.length != 0 && item.value == valueFieldId) {
            label = item.text;
            return false;
        }
    });
    return label;
}

function getOrderStatus(statusObj){
    var text = "";
    for (var ele in statusObj) {
        if (ele == "active" && statusObj[ele] == 1) {
            text += "已激活";
            break;
        } else if (ele == "receive" && statusObj[ele] == 1) {
            text += "已收货";
            break;
        } else if (ele == "send" && statusObj[ele] == 1) {
            text += "已送货";
            break;
        } else if (ele == "assign" && statusObj[ele] == 1) {
            text += "部分配卡";
            break;
        } else if (ele == "assign" && statusObj[ele] == 2) {
            text += "已配卡";
            break;
        } else if (ele == "audit" && statusObj[ele] == 2) {
            text += "审核通过";
            break;
        } else if (ele == "audit" && statusObj[ele] == 1) {
            text += "未通过审核";
            break;
        } else if (ele == "valid" && statusObj[ele] == 1) {
            text += "未审核";
            break;
        }
    }

    if (statusObj["valid"] == 0) {
        text = "无效";
    }
    return text;
}

function getStatus(statusObj, receiveMoneyStatus) {
    var text = "";
    if(receiveMoneyStatus == 3){
        text = "已复核";

    }else if (receiveMoneyStatus == 2) {
        text = "已收款";
    } else if (receiveMoneyStatus == 1) {
        text = "部分收款";
    } else {
        text = "未收款";
    }
    text += ",";
    text += getOrderStatus(statusObj);
    if (text.lastIndexOf("无效") >= 0)
        return "无效";
    else
        return text;
}

function getStatusObjByOrder(item) {
    if (item !== undefined) {
        return {
            "active":item.activeStatus === undefined ? "0" : item.activeStatus,
            "receive":item.receiveStatus === undefined ? "0" : item.receiveStatus,
            "send":item.sendStatus === undefined ? "0" : item.sendStatus,
            "assign":item.assignStatus === undefined ? "0" : item.assignStatus,
            "audit":item.auditStatus === undefined ? "0" : item.auditStatus,
            "valid":item.validStatus === undefined ? "0" : item.validStatus
        }
    }
}

function getInvoiceStatus(invoiceStatus) {
    if (invoiceStatus == "2")
        return "已开票";
    else if (invoiceStatus == "1")
        return "部分开票";
    else
        return "未开票";
}

function error(content) {
    jQuery.ligerDialog.error(content);
}

function warn(content) {
    jQuery.ligerDialog.warn(content);
}

function padding_left(s, c, n) {
    if (!s || !c || s.length >= n) {
        return s;
    }
    var max = (n - s.length) / c.length;
    for (var i = 0; i < max; i++) {
        s = c + s;
    }
    return s;
}

// right padding s with c to a total of n chars
function padding_right(s, c, n) {
    if (!s || !c || s.length >= n) {
        return s;
    }
    var max = (n - s.length) / c.length;
    for (var i = 0; i < max; i++) {
        s += c;
    }
    return s;
}

function generateIds(rows) {
    var ids = "";
    for (var i = 0; i < rows.length; i++) {
        ids = ids + rows[i].id + ",";
    }

    if (ids.length > 0) {
        ids = ids.substring(0, ids.length - 1);
    }

    return ids;
}

function formatCurrency(money){
    return accounting.formatMoney(money,"");
}

function formatCurrency(money,precision){
  return accounting.formatMoney(money,"",precision);
}

function convertToRequestParam(form) {
    var obj = {};
    for (var i = 0; i < form.length; i++) {
        obj[form[i].name] = form[i].value ? form[i].value : "";
    }
    return obj;
}

function resizeDataGrid(grid){
    grid.filteredData = grid.data = {};
    grid.currentData = grid._getCurrentPageData(grid.filteredData);
    grid._showData();
}





