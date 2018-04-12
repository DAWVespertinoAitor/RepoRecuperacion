<%-- 
    Document   : edad
    Created on : 10-abr-2018, 17:20:30
    Author     : Daw2
--%>

<%@page import="java.time.Period"%>
<%@page import="java.time.format.DateTimeFormatter"%>
<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Calendar"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edad Exacta</title>
        <link rel="stylesheet" type="text/css" href="../CSS/estilos.css">
    </head>
    <body>
        <div class="columnasCentradas">
        <%
            LocalDate ahora = LocalDate.now();
            String fecha = request.getParameter("fecha");
            
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate fechaNac = LocalDate.parse(fecha, fmt);
            
            String edadExacta = " ";
            
            Map<String, String[]> parametros = request.getParameterMap();
            for (Iterator<String> i = parametros.keySet().iterator(); i.hasNext();) {
                String nombre = i.next();
                
                if (!nombre.startsWith("enviar")) {
                    
                     if(fechaNac.compareTo(ahora) < 0){
                        Period periodo = Period.between(fechaNac, ahora);
                        
                        if(periodo.getYears()>0 && periodo.getMonths()>0 && periodo.getDays()>0){
                            edadExacta = "<h1>Tu edad es de: </h1><h3>"+periodo.getYears()+" años "+ periodo.getMonths()+" meses y "+ periodo.getDays()+" días</h3>";
                            
                        } else if( periodo.getYears()==0 && periodo.getMonths()>0 && periodo.getDays()>0){
                            edadExacta = "<h1>Tu edad es de: </h1><h3>"+ periodo.getMonths()+" meses y "+ periodo.getDays()+" días</h3>";
                            
                        } else if( periodo.getYears()==0 && periodo.getMonths()<=0 && periodo.getDays()>0){
                            if(periodo.getDays()==1){
                                edadExacta = "<h1>Tu edad es de: </h1><h3>"+ periodo.getDays()+" día</h3>";
                            } else {
                                edadExacta = "<h1>Tu edad es de: </h1><h3>"+ periodo.getDays()+" días</h3>";
                            }
                        }
                    } else {
                        edadExacta = "<h3>No has introducido una fecha de nacimiento</h3>";
                    }
                }
            }
        %>
        <%=edadExacta%>
            <a href="../index.html">Menu principal</a>
        </div>
    </body>
</html>
