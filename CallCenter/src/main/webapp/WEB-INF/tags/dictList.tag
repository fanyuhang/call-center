<%@ tag import="com.common.AppContext" %>
<%@ tag import="com.redcard.system.entity.Dictionary" %>
<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@ tag import="java.util.ArrayList" %>
<%@ tag import="java.util.List" %>
<%@ tag pageEncoding="UTF-8" %>
<%--
 author: Allen

 用法：
 1、在jsp页面中引入taglib
    <%@ taglib prefix="sys" tagdir="/WEB-INF/views/tags" %>
 2、在需要系统代码的地方使用如下代码：
    <sys:dictList type="1" nullable="true" nullText="----" nullValue=""/>
--%>
<%@ attribute name="type" type="java.lang.String" required="true" description="字典类型" %>
<%@ attribute name="nullable" type="java.lang.Boolean" required="false" description="是否需要空白选项" %>
<%@ attribute name="nullText" type="java.lang.String" required="false" description="空白选项对应显示文本" %>
<%@ attribute name="nullValue" type="java.lang.String" required="false" description="空白选项对应value" %>
<%
    List<Dictionary> list;
    if (StringUtils.isNotBlank(type)) {
        list = AppContext.getInstance().getDictionary(Integer.valueOf(type));
    } else {
        list = new ArrayList<Dictionary>();
    }
%>
[
<%
    if (nullable != null && nullable) {
%>
{"value":"<%=nullValue == null ? "" : nullValue%>", "text":"<%=nullText == null ? "不限" : nullText%>"}<%=list.size() > 0 ? "," : ""%>
<%
    }
%>
<%
    for (int i = 0; i < list.size(); i++) {
        Dictionary dictionary = list.get(i);
%>
{"value":"<%=dictionary.getValue()%>", "text":"<%=dictionary.getName()%>"}<%=i < list.size() - 1 ? "," : ""%>
<%
    }
%>
]