package com.mmx.hackathon.service;

import com.mmx.hackathon.manager.FileManager;
import com.mmx.hackathon.util.Common;
import com.mmx.hackathon.util.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Partha
 */
public class FetchFileService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String loginid = request.getParameter("loginid");
            String code = request.getParameter("code");
            boolean securityFlag = new Common().checkUserSecret(loginid, code);
            if (securityFlag) {
                String status = new FileManager().fetchallUserFiles(loginid);
                if (status != null) {
                    request.setAttribute("statuscode", Constants.HTTP_STATUS_SUCCESS);
                    out.write(status);

                } else {
                    request.setAttribute("statuscode", Constants.HTTP_STATUS_FAIL);
                    out.write(Constants.HTTP_STATUS_FAIL);
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception::::" + ex);
            out.write(Constants.HTTP_STATUS_EXCEPTION);

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
