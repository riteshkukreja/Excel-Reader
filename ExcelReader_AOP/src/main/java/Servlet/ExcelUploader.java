/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Model.Parser;
import Model.StorageManager;
import Model.User;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 *
 * @author Ritesh
 */
public class ExcelUploader extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request,
        HttpServletResponse response) {
        response.setContentType("text/html;charset=UTF-8");
        
        String filePath = null;
        StorageManager fileManager = null;
        Parser excelParser = null;
        
        try {

            try {
                // Create path components to save the file
                final Part filePart = request.getPart("file");
                
                //ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
                WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
                
                fileManager = context.getBean(StorageManager.class);
                excelParser = context.getBean(Parser.class);

                filePath = fileManager.add(filePart);
                List<User> parsedUsers = excelParser.parse(new File(filePath));
                request.setAttribute("users", parsedUsers);
            } catch(Exception e) {
                request.setAttribute("users", null);
                request.setAttribute("error", e.getMessage());

                // rollback uploaded file
                if(filePath != null) {
                    fileManager.remove(filePath);
                }
            }

            request.getRequestDispatcher("display.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
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
