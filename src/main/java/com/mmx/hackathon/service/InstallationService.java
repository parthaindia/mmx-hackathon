package com.mmx.hackathon.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mmx.hackathon.dto.Installation;
import com.mmx.hackathon.manager.InstallationManager;
import com.mmx.hackathon.util.Common;
import com.mmx.hackathon.util.Constants;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Manindar
 */
public class InstallationService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String input = request.getParameter("regmap");
            Map<String, String> inputMap = new Gson().fromJson(input, new TypeToken<Map<String, String>>() {
            }.getType());
            Installation i = (Installation) new Common().mapToDto(inputMap, Installation.class);
            String id = new InstallationManager().add(i);
            if (id == null || id.isEmpty()) {
                out.write(new Gson().toJson(Constants.FAIL));
            } else {
                out.write(new Gson().toJson(Constants.SUCCESS));
            }

        } catch (Exception ex) {
            out.write(new Gson().toJson(Constants.ERROR));
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
