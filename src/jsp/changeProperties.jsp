<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.io.*, java.util.*, edu.ucsd.library.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<body background="../images/background.gif">>


<table>
<tr>
<td>
<font face="Verdana, Arial, sans-serif" size=2 class="fontNormal" color="#FFFFFF">
Choose the File :
</font>
</td>
</tr>
<tr>

<td>
<form action="listProperties.jsp" method="get">
<input type="hidden" name="target" value="org_name_mapping.properties"/>
<input type="submit" value="Organization Name File"/>
</form>
</td>

</tr>

<tr>

<td>
<form action="listProperties.jsp" method="get">
<input type="hidden" name="target" value="appointment_mapping.properties"/>
<input type="submit" value="Appointment Type File"/>
</form>
</td>

</tr>

</table>



</body>
</html>