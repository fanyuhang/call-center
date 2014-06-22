function onOrderStatusChange(element){
    var returnVal = "";
    var value = element.value;
    for(var i=0;i<statusSelctedData.length;i++){
        if(statusSelctedData[i].text == value) {
            returnVal = statusSelctedData[i].value;
            break;
        }
    }
    $("#validStatus").remove();
    $("#auditStatus").remove();
    $("#assignStatus").remove();
    $("#sendStatus").remove();
    $("#receiveStatus").remove();
    $("#activeStatus").remove();
    $("#statusAll").remove();

    if (returnVal && returnVal != ""){
        var statusObj = eval("("+returnVal+")");

        Object.keys = Object.keys || function(o) {
            var result = [];
            for(var name in o) {
                if (o.hasOwnProperty(name))
                  result.push(name);
            }
            return result;
        };
        var keyList = Object.keys(statusObj);

        for(var j=0;j<keyList.length;j++){
            var key = keyList[j];
            var node="<input id='"+key+"' type='hidden' value='"+eval("statusObj."+key)+"' name='"+key+"' require='true' op='equal' vt='int'>";
            $("#formsearch").append(node);
        }
    }
}