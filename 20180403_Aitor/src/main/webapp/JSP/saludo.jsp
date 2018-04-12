<%-- 
    Document   : saludo
    Created on : 10-abr-2018, 16:50:39
    Author     : Daw2
--%>

<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Saludo</title>
        <link rel="stylesheet" type="text/css" href="../CSS/estilos.css">
    </head>
    <body>
        <%
            int hora;
            String saludo="";
            String genero = "";
            String persona ="";
            
            Calendar calendario = Calendar.getInstance();
            hora = calendario.get(Calendar.HOUR_OF_DAY);

            if (hora >= 8 && hora < 12) {
                saludo = "Buenos dias ";
            } else if (hora >= 12 && hora < 21) {
                saludo = "Buenas tardes ";
            } else if (hora >= 21 && hora < 8) {
                saludo = "Buenas noches ";
            }

            Map<String, String[]> parametros = request.getParameterMap();
            for (Iterator<String> i = parametros.keySet().iterator(); i.hasNext();) {
                String nombre = i.next();
                if (!nombre.startsWith("enviar")) {
                    if (nombre.startsWith("nombre")) {
                        persona = request.getParameter(nombre);
                    } else {
                        genero = request.getParameter(nombre);
                    }
                        
                }
            }
            
            switch(genero){
                case "hombre":
                    genero = "señor ";
                    break;
                case "mujer":
                    genero = "señora ";
                    break;
            }
        %>
        
        <div class="columnasCentradas">
            <h1>
                <%
                    out.println(saludo+genero+persona);
                %>
            </h1>
            <a href="../index.html">Menu principal</a>
        </div>
    </body>
</html>
