/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;

import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.annotation.WebServlet;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
  
@WebServlet(name = "NewServlet", urlPatterns = {"/new-servlet"})  
public class NewServlet extends HttpServlet {  
  
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
            String userText = request.getParameter("userText");  
  
            // Check if it's a POST request with user input  
            if (request.getMethod().equalsIgnoreCase("POST") && userText != null) {  
                // Process the user input (e.g., convert to uppercase)  
                String processedText = userText.toUpperCase();  
  
                // Output the processed text  
                out.println("<!DOCTYPE html>");  
                out.println("<html>");  
                out.println("<head>");  
                out.println("<title>Servlet NewServlet</title>");  
                out.println("</head>");  
                out.println("<body>");  
                out.println("<h1>Processed Text</h1>");  
                out.println("<p>You entered: " + processedText + "</p>");  
                out.println("</body>");  
                out.println("</html>");  
            } else {  
                // Output the form for user input  
                out.println("<!DOCTYPE html>");  
                out.println("<html>");  
                out.println("<head>");  
                out.println("<title>Servlet NewServlet</title>");  
                out.println("</head>");  
                out.println("<body>");  
                out.println("<h1>Enter Some Text</h1>");  
                out.println("<form method='post' action='new-servlet'>");  
                out.println("<input type='text' name='userText' />");  
                out.println("<input type='submit' value='Submit' />");  
                out.println("</form>");  
                out.println("</body>");  
                out.println("</html>");  
            }  
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
