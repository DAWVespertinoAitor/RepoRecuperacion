<%-- 
    Document   : contadorVisitasSesiones
    Created on : 14-abr-2018, 19:55:59
    Author     : aitor
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contador con Sesiones</title>
        <meta charset="utf-8"/>
        <link rel="stylesheet" type="text/css" href="../CSS/estilos.css">
    </head>
    <body>
        <div class="columnasCentradas">
        <%
            HttpSession sesion = request.getSession(true);
            if(sesion.getAttribute("contador") == null){
                sesion.setAttribute("contador", 0);
            } else {
                sesion.setAttribute("contador", Integer.parseInt(sesion.getAttribute("contador").toString())+1);
            }
            
            
            
            if(request.getParameter("Eliminar") != null){
                sesion.setAttribute("contador", 0);
                //No he hecho uso de este metodo, porque al recargar me da error la pagina jsp
                //sesion.invalidate();
            }
            
        %>
        <form action="contadorVisitasSesiones.jsp" method="post">
            <h1>Has visitado esta página <%=sesion.getAttribute("contador")%> veces</h1>
            Eliminar sesion al recargar <input type="checkbox" id="Eliminar" name="Eliminar"  value="Eliminar"/><br><br>
            <a href="contadorVisitasSesiones.jsp"><input type="submit" name="Recargar" value="Recargar"></a>
            <a href="../index.html"><input type="button" name="Volver" value="Menu Principal"></a><br><br>
        </form>
        </div>
    </body>
</html>
