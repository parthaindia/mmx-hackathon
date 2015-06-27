/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mmx.hackathon.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.FileHolder;
import com.mmx.hackathon.manager.FileManager;
import com.mmx.hackathon.util.Constants;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.net.URLConnection;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
@MultipartConfig
/**
 *
 * @author Partha
 */
public class FileUploadService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

   
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
         
                    
                    Part filePart = request.getPart("fileContent");
                    InputStream filecontent = filePart.getInputStream();
                    String keyWord = request.getParameter("keyWord");
                    String description = request.getParameter("description");
                    String srcfilepath = request.getParameter("srcfilepath");
                    String loginid=request.getParameter("loginid");

                    File file = new File(srcfilepath);
                    String fileName = file.getName();

                    FileHolder fileData = new FileHolder();
                    fileData.setOwnerID(loginid);
                    fileData.setKey(keyWord);
                    fileData.setMimeType(URLConnection.guessContentTypeFromName(file.getName()));
                    fileData.setDescription(description);
                    fileData.setSrcfilepath(srcfilepath);
                    fileData.setFileName(fileName);
                    fileData.setStatus(Constants.ACTIVE);
                    Type type = new TypeToken<File>() {
                    }.getType();
                  
                    fileData.setInputStream(filecontent);
                    String status = new FileManager().uploadFile(fileData);
                    if (status!=null) {
                        request.setAttribute("statuscode", Constants.HTTP_STATUS_SUCCESS);
                        out.write(new Gson().toJson(Constants.HTTP_STATUS_SUCCESS));
                       
                    } else {
                        request.setAttribute("statuscode", Constants.HTTP_STATUS_FAIL);
                        out.write(new Gson().toJson(Constants.HTTP_STATUS_FAIL));
                        
                    }


        } catch (Exception ex) {
            request.setAttribute("statuscode",Constants.HTTP_STATUS_EXCEPTION);
            StringWriter stack = new StringWriter();
            ex.printStackTrace(new PrintWriter(stack));
            
        } finally {
            out.close();
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
