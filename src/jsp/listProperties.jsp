<%@ page import="java.io.*, java.util.*, edu.ucsd.library.util.*" %>
<%@ page errorPage="error_pages/error.jsp" %>
<jsp:useBean id="crm" class="edu.ucsd.library.crm.beans.crm_bean" scope="session"/>
<html>
<head>
<script type="text/javascript">
function getUrlVars() {
    var vars = {};
    var parts = window.location.href.replace(/[?&]+([^=&]+)=([^&]*)/gi, function(m,key,value) {
        vars[key] = value;
    });
    return vars;
}
</script>
</head>

<body background="../images/background.gif">
<%
	if(request.getAttribute("action")!= null && ((String)request.getAttribute("action")).equals("edit"))
	{
		String msg = (String)request.getAttribute("message");
		if(msg != null)
		{
			%>
			<font color="RED"> <%=msg%> </font>
			<% 
		}
	}
	if(request.getAttribute("action")!= null && ((String)request.getAttribute("action")).equals("keyInvalid"))
	{
		%>
		<script type="text/javascript">
			var r = confirm("Key entered is already existed in the Properties File. \n\t     Do you want to replace the old value?");
			var target = getUrlVars()["target"];
			var value = getUrlVars()["newPropValue"];
			var key = getUrlVars()["propName"];
			var npass = "listProperties.jsp?target=" + target;
			var pass = "editProperties.jsp?target=" + target + "&propName=" + key + "&newPropValue=" + value + "&action=edit";
			if(r==false)
				window.location.href = npass;
			else
				window.location.href = pass;
		</script>
		
		
		<%
		
	}
	String typ = request.getParameter("target");
	String link = "../WEB-INF/pub/data1/import/htdocs/crm/" + typ;
	String listName = "";
	String col1 = "";
	String col2 = "";
	String col3 = "";
	if(typ.equals("org_name_mapping.properties"))
	{
		listName = "Organization Name";
		col1 = "Department Name";
		col2 = "LibAssignedName";
		col3 = "New LibAssignedName";
	} else {
    listName = "Appointment Type";
    col1 = "Appointment Title";
    col2 = "Employee Type";
    col3 = "New Employee Type";
	}
	String dLink = "downloadPropertiesServlet.do?fileName="+ typ;
%>
<div id="main" style="width:400;margin:auto;position:relative;text-align:center;">
<font face="Verdana, Arial, sans-serif" size=2  color="#FFFFFF">
<b><%=listName%></b>
</br>
<a href=<%=dLink%> style="position:absolute;right:0px"> Download here</a>
</font>
</br>
<table border="1" style="width:400;margin:auto;text-align:center;">
<tr style="color:#FFFFFF"">
<th><%=col1%></th>
<th><%=col2%></th>
<th><%=col3%></th>
<th>Action</th>
<th>Action2</th>
</tr>
<tr style="color:#FFFFFF">
<form action="editProperties.jsp" method="get">
<td><input type="text" name="propName" style="width:40"/></td>
<td> </td>
<td><input type="text" name="newPropValue" style="width:60"/></td>
<input type="hidden" name="target" value="<%=request.getParameter("target")%>"/>
<input type="hidden" value="add" name="action">
<td><input type="submit" value="Add"/></td>
</form>
</tr>
<%
Map map = crm.getPropertiesSet(request.getParameter("target"));
Iterator it = map.entrySet().iterator();
String propName;
while(it.hasNext())
{
	Map.Entry pairs = (Map.Entry)it.next();
	//propName = entry.getKey();
	//String propValue = pairs.getValue();
	%>
	<tr style="color:#FFFFFF">
	<td><%=pairs.getKey()%></td>
	<td><%=pairs.getValue()%></td>
	<form action="editProperties.jsp" method="get">
	<td><input type="text" name="newPropValue" style="width:60"/></td>
	<input type="hidden" name="propName" value="<%=pairs.getKey()%>" />
	<input type="hidden" name="target" value="<%=request.getParameter("target")%>"/>
	<input type="hidden" value="edit" name="action">
	<td><input type="submit" value="Update"/></td> 
	</form>
	<form action="editProperties.jsp" method="get">
	<input type="hidden" name="propName" value="<%=pairs.getKey()%>" />
	<input type="hidden" name="target" value="<%=request.getParameter("target")%>"/>
	<input type="hidden" value="delete" name="action">
	<td><input type="submit" value="Delete"/></td> 
	</form>
	</tr>
	<%
	
}
%>
</table>
</div>

</body>
</html>