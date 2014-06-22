var ruleDataOptions = {
    valueField:"id",
    textField:"roleName",
    width:200,
    url:"/security/select/selectRole",
    isMultiSelect:true, split:','
};

var userDataOptions = {
    valueField:"id",
    textField:"userName",
    width:200,
    url:"/security/select/selectUser"
};
var deptDataOptions = {
    width:200,
    selectBoxWidth:200,
    treeLeafOnly:false,
    textField:'deptName',
    valueField:'deptCode',
    tree:{
        textFieldName:"deptName",
        idFieldName:"deptCode",
        url:'/security/select/selectDept',
        checkbox:false
    }
};

var SysParms = [
    { name:'{CurrentUserID}', display:'{当前用户}', type:'int',
        editor:{ type:'combobox', options:userDataOptions }
    },
    { name:'{CurrentRoleID}', display:'{当前角色}', type:'int',
        editor:{ type:'combobox', options:ruleDataOptions }
    },
    { name:'{CurrentDeptID}', display:'{当前部门}', type:'string',
        editor:{ type:'combobox', options:deptDataOptions }
    }
];

function getFields(view) {
    for (var i = 0, l = resources.length; i < l; i++) {
        var v = resources[i];

        if (v.name == view) {
            var fields = [];
            $(v.columns).each(function () {
                fields.push({
                    name:this.name,
                    display:this.display,
                    type:this.type,
                    editor:getFieldEditor(view, this.name)
                });
            });
            $(SysParms).each(function () {
                fields.push({
                    name:this.name,
                    display:this.display,
                    type:this.type,
                    editor:this.editor
                });
            });
            return fields;
        }
    }
    return SysParms;
}

var fieldEditors = {};

function getFieldEditor(view, field) {
    if (fieldEditors[view] && fieldEditors[view][field])
        return fieldEditors[view][field];
    return null;
}