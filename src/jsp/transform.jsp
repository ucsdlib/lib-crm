<%@ page import="java.io.*" %>
<%@ page errorPage="error_pages/error.jsp" %>

<jsp:useBean id="crm" class="edu.ucsd.library.crm.beans.crm_bean" scope="session"/>

<html>

<body background="../images/background.gif" text="#FFFFFF" VLINK="#FFFFFF" ALINK="#FFFFFF" LINK="#FFFFFF">

<br>
<h2>
<font face="Verdana, Arial, sans-serif" size=2 class="fontNormal" color="#FFFFFF">
<%
  if(crm.canTransform()) {
%>
<b>CSV File transformed!!</b>
<%
  } else {
%>
<b>CSV File Transform failed!!!</b><br/>
<a href="downloadPropertiesServlet.do?fileName=missing_mapping.txt">Download missing mapping file</a>
<%
  }
%>
</font>
</h2>

<br><br>

<%
	crm.transformEmployee();
%>

</body>
</html>