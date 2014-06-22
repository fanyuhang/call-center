<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sys" tagdir="/WEB-INF/tags" %>
<%
    String contextPath = request.getContextPath();
    if ("/".equals(contextPath)) {
        contextPath = "";
    }
    if (contextPath.endsWith("/")) {
        contextPath = contextPath.substring(0, contextPath.length() - 1);
    }
%>
<c:set var="ctx" value="<%=contextPath%>"/>