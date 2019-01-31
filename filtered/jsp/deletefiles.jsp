<%@ page import="java.io.*, java.util.*, javax.naming.*" %>
<%@ page errorPage="error_pages/error.jsp" %>

<jsp:useBean id="crm" class="edu.ucsd.library.crm.beans.crm_bean" scope="session"/>

<%
		String webinfDir = crm.getContextDir() + "WEB-INF" + File.separator;
		InitialContext jndi = new InitialContext();
				
		String marcFilesDir =  application.getInitParameter("marcFilePath");
		
		for (Enumeration en=request.getParameterNames(); en.hasMoreElements();) {
			    String name = (String)en.nextElement();
			    String value = request.getParameter(name);

			    name = name.substring(7, name.length());

			    File myF = new File( marcFilesDir + name);
			    if (value.equals("on")) myF.delete();
		}

		%> <jsp:forward page="download_employees.jsp"/> <%
	
%>
