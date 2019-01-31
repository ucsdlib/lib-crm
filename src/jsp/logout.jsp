<%@ page errorPage="error_pages/error.jsp" %>

<%
	session.invalidate();
	response.sendRedirect("/crm/login/login.jsp");
%>