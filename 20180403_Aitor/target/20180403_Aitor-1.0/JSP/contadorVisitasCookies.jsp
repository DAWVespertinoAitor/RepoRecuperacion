<%-- 
    Document   : contadorVisitasCookies
    Created on : 12-abr-2018, 16:21:13
    Author     : Daw2
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Contador de visitas con cookies</title>
        <meta charset="utf-8"/>
        <link rel="stylesheet" type="text/css" href="../CSS/estilos.css">
    </head>
    <body>
        <!--
        contador
        Titulo cookie
        caducidad
        nombre cookie
        segura
        version
        -->
        <div class="columnasCentradas">
            <% 
                
                    
                Cookie[] cookies = request.getCookies();
                Cookie miContador = null;
                
                for(int i = 0; i<cookies.length;i++){
                    if(cookies[i].getName().equals("contador")){
                        miContador = new Cookie (cookies[i].getName(), cookies[i].getValue());
                        break;
                    }
                }
                if(miContador == null){
                    miContador = new Cookie("contador","0");
                } else if(request.getParameter("Eliminar") != null){
                        miContador.setValue("0");
                }
                
                int contador = Integer.parseInt(miContador.getValue());
                miContador.setValue(Integer.toString(contador + 1));
                miContador.setMaxAge(86400);
                
                response.addCookie(miContador);
                
                
                


            %>
            <form action="contadorVisitasCookies.jsp" method="post">
                <h1>Has visitado esta pagina <%=miContador.getValue()%> veces</h1>
                <%
                    if(Integer.parseInt(miContador.getValue()) < 2){
                %>
                <h2>Manejando Cookies</h2>
                <h3>Caducidad: <%=miContador.getMaxAge()%></h3>
                <h3>Segura: <%=miContador.getSecure()%></h3>
                <h3>Version: <%=miContador.getVersion()%></h3>
                <%
                    }
                %>
                <a href="contadorVisitasCookies.jsp"><input type="button" name="Recargar" value="Recargar"></a>
                <input type="submit" name="Eliminar" value="Eliminar">
                <a href="../index.html"><input type="button" name="Volver" value="Menu Principal"></a><br><br>
            </form>
            
        </div>
    </body>
</html>
