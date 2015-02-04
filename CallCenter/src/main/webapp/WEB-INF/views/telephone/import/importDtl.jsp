<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<body style="padding:10px;height:100%; text-align:center;">
<input type="hidden" id="MenuNo" value="${menuNo}"/>
<div id="maingrid"></div>
<script type="text/javascript">
	var genderData = <sys:dictList type = "1"/>;

//列表结构
var grid = $("#maingrid").ligerGrid({
    checkbox: false,
    rownumbers: true,
    delayLoad: false,
    columnWidth: 180,
    columns: [
        {display: "客户姓名", name: "fldCustomerName"},
        {display: "性别", name: "fldGender",render:function(item) {
            return renderLabel(genderData,item.fldGender);
        }},
        {display: "手机", name: "fldMobile",
            render:function(item) {
                return LG.hiddenPhone(item.fldMobile);
            }},
        {display: "固定电话", name: "fldPhone"},
        {display: "地址", name: "fldAddress"},
        {display: "备注", name: "fldComment"}
    ], dataAction: 'server', pageSize: 20, url: '<c:url value="/telephone/import/showDtl?fldId=${importId}"/>',
    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
});

function f_reload() {
    grid.loadData();
}
</script>
</body>
</html>