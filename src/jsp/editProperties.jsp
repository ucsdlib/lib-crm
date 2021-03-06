<%@ page import="java.io.*, java.util.*, edu.ucsd.library.util.*" %>
<%@ page errorPage="error_pages/error.jsp" %>
<jsp:useBean id="crm" class="edu.ucsd.library.crm.beans.crm_bean" scope="session"/>

<html>
<head>
<%
	String key = request.getParameter("propName");
	String value = request.getParameter("newPropValue");
	String target = request.getParameter("target");
	if( request.getParameter("action").equals("delete"))
	{
		request.setAttribute("action","edit");
		crm.delProperties(target, key); 
		request.setAttribute("message", "\"" + key + "\" is deleted!");
	}
	else if( request.getParameter("action").equals("add"))
	{
		if(crm.hasPropertiesKey(target,key))
		{
			request.setAttribute("action","keyInvalid");
			request.setAttribute("propName",key);
			request.setAttribute("newPropValue",value);
			request.setAttribute("target",target);
		}
		else
		{
			request.setAttribute("action","edit");
			crm.setPropertiesFile(target, key, value, true); 
			request.setAttribute("message", "\"" + key + "\" is added.");
		}
			
	}
	else
	{
		request.setAttribute("action","edit");
		crm.setPropertiesFile(target, key, value, false); 
		request.setAttribute("message", "\"" + key + "\" is updated.");
		
	}
	
%>
<jsp:forward page="listProperties.jsp" />

</head>
<body>

</body>
</html>