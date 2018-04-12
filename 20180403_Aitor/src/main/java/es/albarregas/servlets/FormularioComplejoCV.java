/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.albarregas.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author aitor
 */
@WebServlet(name = "FormularioComplejoCV", urlPatterns = {"/FormularioComplejoCV"})
public class FormularioComplejoCV extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\"/>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + request.getContextPath() + "/CSS/estilos.css\" media=\"screen\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class=\"columnas\">");
            
            Map<String, String[]> parametros = request.getParameterMap();
            int correcto = 0;
            for(Iterator<String> i=parametros.keySet().iterator();i.hasNext();){
                
                String nombre = i.next();
                if (!nombre.startsWith("enviar")) {
//                    System.out.println(nombre);
                    switch(nombre){
                        case "Nombre":
                            Pattern pattern = Pattern.compile("[a-zA-ZñÑáéíóúÁÉÍÓÚüÜ]{2,}");

                            // El email a validar
                            String nombreValidado = request.getParameter(nombre);

                            Matcher mather = pattern.matcher(nombreValidado);

                            if (mather.find() == true) {
                                out.println(nombre + " - <strong>" + request.getParameter(nombre) + "</strong><br/>");
                                correcto = correcto + 1;
                            } else {
                                out.println(nombre + " - <strong> No es un nombre</strong><br/>");
                            }
                            break;
                        case "Telefono":
                            pattern = Pattern.compile("^(\\+34|0034|34)?[67]\\d{8}$");

                            // El email a validar
                            String telefono = request.getParameter(nombre);

                            mather = pattern.matcher(telefono);

                            if (mather.find() == true) {
                                out.println(nombre + " - <strong>" + request.getParameter(nombre) + "</strong><br/>");
                                correcto = correcto + 1;
                            } else {
                                out.println(nombre + " - <strong> No es un telefono</strong><br/>");
                            }
                            break;
                        case "Correo":
                            pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

                            // El email a validar
                            String email = request.getParameter(nombre);

                            mather = pattern.matcher(email);

                            if (mather.find() == true) {
                                out.println(nombre + " - <strong>" + request.getParameter(nombre) + "</strong><br/>");
                                correcto = correcto + 1;
                            } else {
                                out.println(nombre + " - <strong> No es un Correo Electronico</strong><br/>");
                            }
                
                            break;
                        case "fechaN":
                            pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

                            // El email a validar
                            String fechaN = request.getParameter(nombre);

                            mather = pattern.matcher(fechaN);

                            if (mather.find() == true) {
                                out.println(nombre + " - <strong>" + request.getParameter(nombre) + "</strong><br/>");
                                correcto = correcto + 1;
                            } else {
                                out.println(nombre + " - <strong> No es una fecha de nacimiento</strong><br/>");
                            }
                            break;
                        case "Vehiculo":
                            String[] vehiculos = request.getParameterValues("Vehiculo");
                            if(nombre != null){
                                for(int j = 0; j < vehiculos.length; j++){
                                    out.println((j+1) + "º " + nombre + " - <strong>" + vehiculos[j] + "</strong><br/>");
                                }
                                correcto = correcto + 1;
                            } else {
                                out.println(nombre + " - <strong> Tienes que seleccionar al menos un vehiculo</strong><br/>");
                            }
                            break;
                            
                    }
//                   out.println(nombre + " - <strong>" + request.getParameter(nombre) + "</strong><br/>");
                }
            }
            if(correcto == 5){
                out.print("<p><a href='" + request.getContextPath() + "'>Menu Principal</a></p>");
            } else {
                out.print("<p><a href='html/formularioComplejoCV.html'>Volver</a></p>");
            }
            
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
