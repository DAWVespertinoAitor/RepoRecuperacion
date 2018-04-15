/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daw2
 */
@WebServlet(name = "Registro", urlPatterns = {"/Registro"})
public class Registro extends HttpServlet {

    char[] letras = {'T', 'R', 'W', 'A', 'G', 'M', 'Y', 'F', 'P', 'D', 'X', 'B', 'N', 'J', 'Z', 'S', 'Q', 'V', 'H', 'L', 'C', 'K', 'E'};
    String[] meses = {"Enero","Feberero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    String[] mesesIngles = {"JANUARY","FEBRUARY","MARCH","APRIL","MAY","JUNE","JULY","AUGUST","SEPTEMBER","OCTOBER","NOVEMBER","DECEMBER"};

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            int todoCorrecto = 0;
            boolean fechaNaci = false;
            boolean documentacion = false;
            String tipoDocu = " ";
            boolean telefMovil = false;
            boolean pref1 = false;
            boolean pref2 = false;
            boolean pref3 = false;
            boolean pref4 = false;
            String sexo = "";
            String fecha = "";
            int contador = 0;
            
            
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\"/>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + request.getContextPath() + "/CSS/estilos.css\" media=\"screen\" />");
            out.println("</head>");
            out.println("<body>");
            

            

            Map<String, String[]> parametros = request.getParameterMap();
            System.out.println(parametros.keySet());
            for (Iterator<String> i = parametros.keySet().iterator(); i.hasNext();) {
                System.out.println("Vuelta " + contador);
                contador = contador + 1;
                String nombre = i.next();

                if (!nombre.startsWith("enviar")) {

                    System.out.println("Llego hasta el switch");
                    switch (nombre) {
                        case "Sexo":
                             sexo = request.getParameter("Sexo");
//                            System.out.println("Llego hasta el sexo");
//                            if (sexoChecked == "Hombre") {
//                                sexo = "Mujer";
//                            } else {
//                                sexo = "Hombre";
//                            }
                            break;

                        case "fechaN":
                            LocalDate ahora = LocalDate.now();
                            fecha = request.getParameter("fechaN");

                            if (!fecha.equals("")) {
                                DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                LocalDate fechaNac = LocalDate.parse(fecha, fmt);

                                if (fechaNac.compareTo(ahora) < 0) {
                                    String mes = String.valueOf(fechaNac.getMonth());
                                    for(int j = 0;j<mesesIngles.length;j++){
                                        if(mesesIngles[j].equals(mes)){
                                            fecha = fecha.substring(0,2)+" de "+meses[j] + " de "+fecha.substring(6,10);
                                        }
                                        
                                    }
                                    todoCorrecto = todoCorrecto + 1;
                                    fechaNaci = true;
                                }
                            }

                            break;
                        case "Documentacion":
                            String docuIdentidad = request.getParameter(nombre);

                            switch (docuIdentidad) {
                                case "NIF":
                                    System.out.println(request.getParameter("numeroDocum"));
                                    if (request.getParameter("numeroDocum") != "") {
                                        documentacion = esValidoNif(request.getParameter("numeroDocum"));
                                        todoCorrecto = todoCorrecto + 1;
                                    }

                                    System.out.println("Llego a la NIF");
                                    break;
                                case "NIE":
                                    System.out.println(request.getParameter("numeroDocum"));
                                    if (request.getParameter("numeroDocum") != "") {
                                        documentacion = esValidoNie(request.getParameter("numeroDocum"));
                                        todoCorrecto = todoCorrecto + 1;
                                    }
                                    break;
                                case "Pasaporte":
                                    if (request.getParameter("numeroDocum") != "") {
                                        todoCorrecto = todoCorrecto + 1;
                                    }
                                    break;
                            }
                            break;
                        case "Telefono":
                            String telefono = request.getParameter(nombre);
                            System.out.println(telefono);
                            if (telefono != "") {
                                if (esValidoTelefMovil(telefono)) {
                                    telefMovil = true;
                                    todoCorrecto = todoCorrecto + 1;
                                }
                            }
                            break;
                        case "Preferencia":
                            String[] preferencias = request.getParameterValues("Preferencia");
                            if (nombre != null) {
                                for (int j = 0; j < preferencias.length; j++) {
                                    switch(j){
                                        case 0:
                                            pref1 = true;
                                            break;
                                        case 1:
                                            pref2 = true;
                                            break;
                                        case 2:
                                            pref3 = true;
                                            break;
                                        case 3:
                                            pref4 = true;
                                            break;
                                    }
                                }
                            }
                            break;
                    }
                }
            }

            if (todoCorrecto == 3) {
                out.println("<div class=\"columnasCentradas\">");
                for (Iterator<String> i = parametros.keySet().iterator(); i.hasNext();) {
                    String nombre = i.next();
                    if (!nombre.startsWith("enviar")) {
                        if (nombre.equals("Preferencia")) {
                            String[] preferencias = request.getParameterValues("Preferencia");
                            if (nombre != null) {
                                for (int j = 0; j < preferencias.length; j++) {
                                    out.println((j + 1) + "º " + nombre + " - <strong>" + preferencias[j] + "</strong><br/>");
                                }
                            }
                        } else if(nombre.equals("fechaN")){
                            out.println(nombre + " - <strong>" + fecha + "</strong><br/>");
                        } else {
                            out.println(nombre + " - <strong>" + request.getParameter(nombre) + "</strong><br/>");
                        }
                    }

                }

                out.print("<p><a href='" + request.getContextPath() + "'>Menu Principal</a></p>");
                out.print("</div>");
            } else {
                out.println("<div class=\"rellenarForm\">");
                out.print("<h1>Errores en el registro</h1>");
                out.print("<form action='Registro' method='post'>");
                out.print("<table>\n"
                        + "<tr>\n"
                        + "<td><h3>Datos Personales</h3></td>\n"
                        + "</tr>");
                out.print("<tr>\n"
                        + "<td><label for=\"Nombre\">Nombre:</label></td>\n"
                        + "<td><input type=\"text\" id=\"Nombre\" name=\"Nombre\" value='" + request.getParameter("Nombre") + "' required></td>\n"
                        + "</tr>");
                out.print("<tr>\n"
                        + "<td><label for=\"Apellidos\">Apellidos </label></td>\n"
                        + "<td><input type=\"text\" id=\"Apellidos\" name=\"Apellidos\" value='" + request.getParameter("Apellidos") + "'></td>\n"
                        + "</tr>");
                System.out.println(sexo);
                if (sexo == "Hombre") {
                    out.print("<tr>\n"
                            + "<td>\n"
                            + "Hombre: <input type=\"radio\" id=\"Sexo\" name=\"Sexo\" value=\"Hombre\" checked/>\n"
                            + "Mujer: <input type=\"radio\" id=\"Sexo\" name=\"Sexo\" value=\"Mujer\"/>\n"
                            + "</td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td>\n"
                            + "Hombre: <input type=\"radio\" id=\"Sexo\" name=\"Sexo\" value=\"Hombre\"/>\n"
                            + "Mujer: <input type=\"radio\" id=\"Sexo\" name=\"Sexo\" value=\"Mujer\" checked/>\n"
                            + "</td>\n"
                            + "</tr>");
                }
                if (fechaNaci) {
                    out.print("<tr>\n"
                            + "<td><label for=\"fechaN\">Fecha de Nacimiento: </label></td>\n"
                            + "<td><input type=\"text\" id=\"fechaN\" name=\"fechaN\" value='" + request.getParameter("fechaN") + "'></td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td><label for=\"fechaN\">Fecha de Nacimiento: </label></td>\n"
                            + "<td><input type=\"text\" id=\"fechaN\" name=\"fechaN\" placeholder='DD-MM-AAAA' style=\"border: 1px solid red;\"></td>\n"
                            + "</tr>");
                }

                if (request.getParameter("Documentacion").equals("NIF")) {
                    out.print("<tr>\n"
                            + "<td><label for=\"Documentacion\">Documentación: </label></td>\n"
                            + "<td>\n"
                            + "<select name=\"Documentacion\" id=\"Documentacion\">\n"
                            + "<option value=\"NIF\" selected>NIF</option>\n"
                            + "<option value=\"NIE\">NIE</option>\n"
                            + "<option value=\"Pasaporte\">Pasaporte</option>\n"
                            + "</select>\n"
                            + "</td>\n"
                            + "</tr>");
                } else if (request.getParameter("Documentacion").equals("NIE")) {
                    out.print("<tr>\n"
                            + "<td><label for=\"Documentacion\">Documentación: </label></td>\n"
                            + "<td>\n"
                            + "<select name=\"Documentacion\" id=\"Documentacion\">\n"
                            + "<option value=\"NIF\">NIF</option>\n"
                            + "<option value=\"NIE\" selected>NIE</option>\n"
                            + "<option value=\"Pasaporte\">Pasaporte</option>\n"
                            + "</select>\n"
                            + "</td>\n"
                            + "</tr>");
                } else if (request.getParameter("Documentacion") == "Pasaporte") {
                    out.print("<tr>\n"
                            + "<td><label for=\"Documentacion\">Documentación: </label></td>\n"
                            + "<td>\n"
                            + "<select name=\"Documentacion\" id=\"Documentacion\">\n"
                            + "<option value=\"NIF\">NIF</option>\n"
                            + "<option value=\"NIE\">NIE</option>\n"
                            + "<option value=\"Pasaporte\" selected>Pasaporte</option>\n"
                            + "</select>\n"
                            + "</td>\n"
                            + "</tr>");
                }

                if (documentacion) {
                    out.print("<tr>\n"
                            + "<td><label for=\"numeroDocum\">Numero de Documentación: </label></td>\n"
                            + "<td><input type=\"text\" id=\"numeroDocum\" name=\"numeroDocum\" value='" + request.getParameter("numeroDocum") + "'></td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td><label for=\"numeroDocum\">Numero de Documentación: </label></td>\n"
                            + "<td><input type=\"text\" id=\"numeroDocum\" name=\"numeroDocum\" style=\"border: 1px solid red;\"></td>\n"
                            + "</tr>");
                }

                out.print("<tr>\n"
                        + "<td><h3>Datos del Usuario</h3></td>\n"
                        + "</tr>");
                out.print("<tr>\n"
                        + "<td><label for=\"Usuario\">Usuario:</label></td>\n"
                        + "<td><input type=\"text\" id=\"Usuario\" name=\"Usuario\" value='" + request.getParameter("Usuario") + "' required></td>\n"
                        + "</tr>");
                out.print("<tr>\n"
                        + "<td><label for=\"Contraseña\">Contraseña:</label></td>\n"
                        + "<td><input type=\"password\" id=\"Contraseña\" name=\"Contraseña\" value='" + request.getParameter("Contraseña") + "' required></td>\n"
                        + "</tr>");
                if (telefMovil) {
                    out.print("<tr>\n"
                            + "<td><label for=\"Telefono\">Teléfono o movil: </label></td>\n"
                            + "<td><input type=\"text\" id=\"Telefono\" name=\"Telefono\" value='" + request.getParameter("Telefono") + "'></td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td><label for=\"Telefono\">Teléfono o movil: </label></td>\n"
                            + "<td><input type=\"text\" id=\"Telefono\" name=\"Telefono\" style=\"border: 1px solid red;\"></td>\n"
                            + "</tr>");
                }

                out.print("<tr>\n"
                        + "<td><label for=\"Preferencia\"><h3>Preferencias:</h3></label></td>\n"
                        + "</tr>");
                if (pref1) {
                    out.print("<tr>\n"
                            + "<td>Deporte: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Deporte\" checked/>\n"
                            + "</td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td>Deporte: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Deporte\"/>\n"
                            + "</td>\n"
                            + "</tr>");
                }
                if (pref2) {
                    out.print("<tr>\n"
                            + "<td>Lectura: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Lectura\" checked/>\n"
                            + "</td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td>Lectura: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Lectura\"/>\n"
                            + "</td>\n"
                            + "</tr>");
                }
                if (pref3) {
                    out.print("<tr>\n"
                            + "<td>Cine: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Cine\" checked/>\n"
                            + "</td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td>Cine: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Cine\"/>\n"
                            + "</td>\n"
                            + "</tr>");
                }
                if (pref4) {
                    out.print("<tr>\n"
                            + "<td>Viajes: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Viajes\" checked/>\n"
                            + "</td>\n"
                            + "</tr>");
                } else {
                    out.print("<tr>\n"
                            + "<td>Viajes: </td>\n"
                            + "<td>\n"
                            + "<input type=\"checkbox\" id=\"Preferencia\" name=\"Preferencia\" value=\"Viajes\"/>\n"
                            + "</td>\n"
                            + "</tr>");
                }
                out.print("<tr><td><input type='submit' name='enviar' value='Enviar'/></td>");
                out.print("<td><input type=\"reset\" name=\"Borrar\"></td></tr>");
                out.print("</table>");
                out.print("</form>");
                out.print("<tr><td><p><a href='index.html'>Menu Principal</a></p></td></tr>");
                out.print("</div>");

            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        PrintWriter out = response.getWriter();

    }

    public boolean esValidoNif(String nif) {
        boolean esValido = false;
        if (nif.length() == 9 && Character.isLetter(nif.charAt(8))) {
            for (int i = 0; i < 8; i++) {
                if (nif.charAt(i) >= 0 || nif.charAt(i) <= 9) {
                    esValido = true;
                }
                if (!esValido) {
                    i = nif.length() - 1;
                }
            }
        }

        if (esValido) {
            char letraDocu = Character.toUpperCase(nif.charAt(8));
            int numerosDocu = Integer.parseInt(nif.substring(0, 8));
            System.out.println(numerosDocu);
            int resto = numerosDocu % 23;
            System.out.println("Esto es el resto " + resto);
            esValido = (letraDocu == letras[resto]);
        }
        return esValido;
    }

    public boolean esValidoNie(String nie) {
        boolean esValido = false;

        if (nie.length() == 9 || Character.isLetter(nie.charAt(8)) && (Character.toUpperCase(nie.charAt(0)) == 'X') || (Character.toUpperCase(nie.charAt(0)) == 'Y') || (Character.toUpperCase(nie.charAt(0)) == 'Z')) {
            for (int i = 0; i < nie.length() - 1; i++) {
                esValido = (nie.charAt(i) >= 0 || nie.charAt(i) <= 9);
                if (!esValido) {
                    i = nie.length() - 1;
                }
            }
        }

        if (esValido && nie.substring(0, 1).toUpperCase().equals("X")) {
            nie = "0" + nie.substring(1, 9);
        } else if (esValido && nie.substring(0, 1).toUpperCase().equals("Y")) {
            nie = "1" + nie.substring(1, 9);
        } else if (esValido && nie.substring(0, 1).toUpperCase().equals("Z")) {
            nie = "2" + nie.substring(1, 9);
        }

        if (esValido) {
            char letraDocu = Character.toUpperCase(nie.charAt(8));
            String numeroAhora = nie.substring(0, 8);
            System.out.println(numeroAhora);
            int numerosDocu = Integer.parseInt(nie.substring(0, 8));
            int resto = numerosDocu % 23;
            esValido = (letraDocu == letras[resto]);
        }
        return esValido;
    }

    public boolean esValidoTelefMovil(String teleMovil) {
        boolean esValido = false;
        System.out.println(teleMovil);
        if (teleMovil.length() == 9 || teleMovil.charAt(0) == 6 || teleMovil.charAt(0) == 7) {
            esValido = true;
        } else if (teleMovil.length() == 9 || teleMovil.substring(0, 2) == "924") {
            esValido = true;
        }
        System.out.println("Movil " + esValido);
        return esValido;
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
