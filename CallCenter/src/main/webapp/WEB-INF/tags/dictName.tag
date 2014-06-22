<%@ tag import="com.common.AppContext" %>
<%@ tag import="org.apache.commons.lang3.StringUtils" %>
<%@ tag pageEncoding="UTF-8" %>
<%--
 author: Allen

 用法：
 1、在jsp页面中引入taglib
    <%@ taglib prefix="sys" tagdir="/WEB-INF/views/tags" %>
 2、在需要系统代码的地方使用如下代码：
    <sys:dictName type="1" value="1" />
--%>
<%@ attribute name="value" type="java.lang.String" required="false" description="字典值" %>
<%@ attribute name="type" type="java.lang.String" required="true" description="字典类型" %>
<%
    String dictName = "";
    if (StringUtils.isNotBlank(type)) {
        dictName = AppContext.getInstance().getDictName(Integer.valueOf(type), value);
    }
%>
"<%=dictName%>"