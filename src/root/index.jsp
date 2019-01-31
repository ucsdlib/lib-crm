<%@ page import="java.io.*, java.util.*, javax.naming.*, edu.ucsd.library.util.*"%>
<%@ page errorPage="error_pages/error.jsp" %>

<jsp:useBean id="crm" class="edu.ucsd.library.crm.beans.crm_bean" scope="session"/>


<%
	// taken from index2.jsp
	String contextDir = request.getRealPath("") + File.separator;
	crm.setContextDir(contextDir);
	String contextUrl = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	contextUrl = contextUrl.substring(0, contextUrl.lastIndexOf("/")+1);
	crm.setContextUrl(contextUrl);
	
	String webinfDir = contextDir + "WEB-INF" + File.separator;
	
	InitialContext jndi = new InitialContext();
	
	String marcFilePath = application.getInitParameter("marcFilePath");
		
	crm.setPathToProperties(marcFilePath + "patron_load.properties");
	
	String pathToProperties = crm.getPathToProperties();
	
	crm.setMarcFilesDir(marcFilePath);
	crm.setPathToACLFile(webinfDir + "acl" + File.separator + "users.acl");
	
	String contextURL = javax.servlet.http.HttpUtils.getRequestURL(request).toString();
	contextURL = contextURL.substring(0, contextURL.lastIndexOf('/')) + "/";
%>



<%
	String userName = "user";
			
	session.setAttribute("username", userName);
		
	crm.setCurrentUser(userName);
	response.sendRedirect("jsp/validuser.jsp");		
%>
