<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="../header.jsp" %>
<style>
    .ui-autocomplete-loading {
        background: white url('<c:url value="/static/ligerUI/jquery/themes/base/images/ui-anim_basic_16x16.gif"/>') right center no-repeat;
    }
</style>
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
        {display: "性别", name: "fldGender",width:100,render:function(item) {
            return renderLabel(genderData,item.fldGender);
        }},
        {display: "手机", name: "fldMobile",width:50},
        {display: "固定电话", name: "fldPhone"},
        {display: "地址", name: "fldAddress"}
    ], dataAction: 'server', pageSize: 20, toolbar: {}, url: '<c:url value="/telephone/import/showDtl?fldId=${importId}"/>',
    width: '98%', height: '98%', toJSON: JSON2.stringify, onReload: f_reload
});

function f_reload() {
    grid.loadData();
}
</script>
</body>
</html>