package com.mmx.hackathon.service;

import com.mmx.hackathon.dto.FileHolder;
import com.mmx.hackathon.dto.Permission;
import com.mmx.hackathon.manager.FileManager;
import com.mmx.hackathon.manager.PermissionManager;
import com.mmx.hackathon.util.Common;
import com.mmx.hackathon.util.Constants;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 *
 * @author Partha
 */
public class FetchFileByIdService extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/json;charset=UTF-8");
        OutputStream out = response.getOutputStream();

        try {
            String loginid = request.getParameter("loginid");
            String code = request.getParameter("code");
            boolean securityFlag = new Common().checkUserSecret(loginid, code);
            if (securityFlag) {
                String fileId = request.getParameter("fileId");
                Permission permission = new PermissionManager().fetch(loginid, fileId);
                if (permission != null && !permission.toString().isEmpty() && permission.getStatus().equals("active")) {
                    FileHolder fileHolder = new FileManager().downloadFile(fileId);

                    if (fileHolder != null) {
                        String mimeType = fileHolder.getMimeType();
                        if (mimeType == null) {
                            mimeType = "appliction/octet-stream";
                        }
                        response.setContentType(mimeType);
                        if (fileHolder.getFileExt().equals("pdf") || fileHolder.getFileExt().equals("doc")) {
                            response.setHeader("Content-Disposition", "inline;filename=" + fileHolder.getFileName() + "." + fileHolder.getFileExt());
                        }
                        request.setAttribute("statuscode", Constants.HTTP_STATUS_SUCCESS);
                        //out.write(new Gson().toJson(fileHolder));
                        ByteArrayOutputStream baos = fileHolder.getBaos();
                        byte b[] = baos.toByteArray();
                        out.write(b);

                    } else {
                        request.setAttribute("statuscode", Constants.HTTP_STATUS_FAIL);
                        out.write(Constants.HTTP_STATUS_FAIL.getBytes());
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println("Exception::::" + ex);
            out.write(Constants.HTTP_STATUS_EXCEPTION.getBytes());

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
