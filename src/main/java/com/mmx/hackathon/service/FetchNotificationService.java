package com.mmx.hackathon.service;

import com.google.gson.Gson;
import com.mmx.hackathon.dto.Notification;
import com.mmx.hackathon.manager.NotificationManager;
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
public class FetchNotificationService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            String loginid = request.getParameter("loginid");
            String code = request.getParameter("code");
            boolean securityFlag = new Common().checkUserSecret(loginid, code);
            if (securityFlag) {
                //get the data from UI
                Map<String, String[]> arMap = request.getParameterMap();
                Map<String, String> inputMap = Common.getSingleMapValue(arMap);

                //convert it into DTO
                Notification n = (Notification) new Common().mapToDto(inputMap, Notification.class);
                String json = new NotificationManager().fetch(n.getToid(), n.getStatus());
                if (json == null || json.isEmpty()) {
                    out.write(new Gson().toJson(Constants.HTTP_STATUS_FAIL));
                } else {
//                List<Notification> nots = new Gson().fromJson(json, new TypeToken<List<Notification>>() {
//                }.getType());
                    out.write(json);
                }
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
