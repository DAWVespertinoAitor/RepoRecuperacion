<%-- 
    Document   : inicio
    Created on : 10-abr-2018, 16:26:04
    Author     : Daw2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <%
        String saludo = "Hola Mundo";
        out.println(saludo);
        %>
        <h1><%=saludo%></h1>
        <h1>Hello World!</h1>
    </body>
</html>
