 <%
    String version = application.getInitParameter("version-number");
    if ( version == null || version.trim().equals("") )
    {
            version = "0.0.0";
    }
    String build = application.getInitParameter("build-date");
    if ( build == null || build.trim().equals("") )
    {
            build = "unknown";
    }
%><html>
  <head>
    <title>CRM: Version <%=version%></title>
  </head>
  <body>
    <p>CRM, Version <%=version%>, Build <%=build%></p>
  </body>
</html>
