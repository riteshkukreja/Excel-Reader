/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlet;

import Model.ExcelParser;
import Model.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
        
        try {
            // Create path components to save the file
            final String path = "C:\\Users\\admin\\Documents";
            final Part filePart = request.getPart("file");
            final String fileName = getFileName(filePart);
            File excelFile = null;

            OutputStream out = null;
            InputStream filecontent = null;

            try {
                excelFile = new File(path + File.separator + fileName);
                
                // validate file
                validateSize(excelFile);
                validateFileType(excelFile);
                
                out = new FileOutputStream(excelFile);
                filecontent = filePart.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[1024];

                while ((read = filecontent.read(bytes)) != -1) {
                    out.write(bytes, 0, read);
                }
                
                out.close();

                // parse excel file
                List<User> parsedUsers = ExcelParser.readUsersFromExcelFile(excelFile);                
                request.setAttribute("users", parsedUsers);
            } catch (FileNotFoundException fne) {
                request.setAttribute("users", null);
                request.setAttribute("error", fne.getMessage());
            } finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
        } catch(Exception e) {
            request.setAttribute("users", null);
            request.setAttribute("error", e.getMessage());
            e.printStackTrace();
        }
        
        try {
            request.getRequestDispatcher("display.jsp").forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
      
    private static String getFileExtension(File file) {
        String name = file.getName();
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }
     
    private static void validateSize(File excelFile) throws Exception {
        if(excelFile.length() > 1024 * 1024)
            throw new Exception("File size should be less than 1 MB");
    }
    
    private static void validateFileType(File excelFile) throws Exception {
        if(!getFileExtension(excelFile).equals("xls"))
            throw new Exception("Invalid file type. Only XLS (2003 - 2007) document supported!");
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
