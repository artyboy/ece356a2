/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sai
 */
public class HandleFriendStatusServlet extends HttpServlet {

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
            throws ServletException, IOException, NamingException {
        response.setContentType("text/html;charset=UTF-8");
        String url;
        String action = request.getParameter("action");
        String reqUrl = request.getParameter("url");
        String requestURL = request.getRequestURL().toString();
        try {
            
        HttpSession session = request.getSession(true);
        String userAlias = ((UserData)session.getAttribute("userData")).getAlias();
            if("viewrequest".equals(action)){
                ArrayList viewRequest = PatientDBAO.viewRequest(request,userAlias);
                request.setAttribute("viewRequest", viewRequest);
                url = "/viewRequestSuccess.jsp";
            }else{
                PatientDBAO.sendRequest(request,userAlias);
                if(reqUrl.equals("search")){
                    ArrayList patient = PatientDBAO.searchPatient(request, userAlias);
                    request.setAttribute("searchAPatient", patient);
                    url = "/PatientSearchSuccess.jsp";
                }
                else if(reqUrl.equals("request")){
                    ArrayList viewRequest = PatientDBAO.viewRequest(request,userAlias);
                    request.setAttribute("viewRequest", viewRequest);
                    url = "/viewRequestSuccess.jsp";
                }else
                    url = "/sendRequestSuccess.jsp";
            }      
            
        } catch (ClassNotFoundException | SQLException e) {
            request.setAttribute("exception", e);
            url = "/PatientSearchError.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(HandleFriendStatusServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (NamingException ex) {
            Logger.getLogger(HandleFriendStatusServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
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
