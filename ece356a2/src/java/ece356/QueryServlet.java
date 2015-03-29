/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Wojciech Golab
 */
public class QueryServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String strQueryNum = request.getParameter("qnum");
        int intQueryNum = Integer.parseInt(strQueryNum);
        HttpSession session = request.getSession(true);
        String userAlias = ((UserData)session.getAttribute("userData")).getAlias();
        String url;
        String doctorAlias = request.getParameter("docAlias");
        try {
            if (intQueryNum == 1) {
                querydoctorhelper(request, response,userAlias);
                url = "/success.jsp";
            } 
            else if(intQueryNum == 2){
                if(doctorAlias == null){
                    url="/error_page.jsp";
                }else{
                viewprofilehelper(request, response);
                url = "/view_profile.jsp";
                }
            }
            else if(intQueryNum == 3){
                if(doctorAlias == null){
                    url= "/error_page.jsp";
                }
                else{
                    request.setAttribute("docAlias", doctorAlias);
                    url = "/write_review.jsp";
                }
            }
            else if(intQueryNum == 4){
                Boolean flag= (Boolean)session.getAttribute("isDoctor") ;
                
                if(!(Boolean)session.getAttribute("isDoctor") && request.getParameter("docAlias") != null){
                    writereviewhelper(request,response,userAlias);
                    viewprofilehelper(request,response);

                    url = "/view_profile.jsp";
                }
                else{
                    url = "/error_page.jsp";
                }
            }
            else if(intQueryNum == 5){
                if(doctorAlias != null){
                    viewreviewhelper(request,response);
                    url = "/view_review.jsp";
                }else
                {
                    url = "/error_page.jsp";
                }
            }
            else if(intQueryNum == 6){
                if(doctorAlias != null){
                    viewnextreviewhelper(request,response, true);
                    url = "/view_review.jsp";
                }
                else{
                     url = "/error_page.jsp";
                }
            }
            else if(intQueryNum == 7){
                if(doctorAlias != null){
                viewnextreviewhelper(request,response,false);
                url = "/view_review.jsp";
                }else{
                     url = "/error_page.jsp";
                }
            }
            else {
                throw new RuntimeException("Invalid query number: " + intQueryNum);
            }
        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/fancyError.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }
    protected void querydoctorhelper(HttpServletRequest request, HttpServletResponse response, String userAlias)
            throws java.sql.SQLException, ClassNotFoundException, NamingException {
        String doctorName = request.getParameter("doctorName");
        String gender = request.getParameter("gender");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String province = request.getParameter("province");
        String postalCode = request.getParameter("postalCode");
        String specialization = request.getParameter("specialization");
        String strYearsLicensed = request.getParameter("yearsLicensed");
        int yearsLicensed = -1;
        if (!strYearsLicensed.equals("")) {
            yearsLicensed = Integer.parseInt(strYearsLicensed);
            if (yearsLicensed < 0) {
                yearsLicensed = 0;
            }
        }
        String strAvgStarRating = request.getParameter("avgStarRating");
        double avgStarRating = -1.0;
        if (!strAvgStarRating.equals("")) {
            avgStarRating = Double.parseDouble(strAvgStarRating);
            if (avgStarRating < 0.0) {
                avgStarRating = 0.0;
            }
        }
        String comments = request.getParameter("keywords");
        Boolean reviewedByFriend = false;
        if(request.getParameter("reviewedByFriend") != null){
            reviewedByFriend = true;
        }

        ArrayList ret = Lab3DBAO.queryDoctor(userAlias,doctorName, gender, street, city, 
                province, postalCode, specialization, yearsLicensed, 
                avgStarRating,comments, reviewedByFriend);
        request.setAttribute("doctorList", ret);
    }
    protected void viewprofilehelper(HttpServletRequest request, HttpServletResponse response)
            throws java.sql.SQLException, ClassNotFoundException, NamingException {
        HttpSession session = request.getSession(false);
        //String doctorAlias = (String)session.getAttribute("docAlias");
        String doctorAlias = (String)request.getParameter("docAlias");
        Doctor doc = Lab3DBAO.viewDoctorProfile(doctorAlias);
        request.setAttribute("doctor", doc);
        
        ArrayList addresses = Lab3DBAO.getAddresses(doctorAlias);
        request.setAttribute("addressList", addresses);
        
        ArrayList specializations = Lab3DBAO.getSpecs(doctorAlias);
        request.setAttribute("specList", specializations);
        
        ArrayList reviews = Lab3DBAO.getReviews(doctorAlias);
        request.setAttribute("reviewList", reviews);
    }
    protected void viewreviewhelper(HttpServletRequest request, HttpServletResponse response)
            throws java.sql.SQLException, ClassNotFoundException, NamingException {
        String doctorAlias = request.getParameter("docAlias");
        String strRevID = request.getParameter("revID");
        int revID = -1;
        if (!strRevID.equals("")) {
            revID = Integer.parseInt(strRevID);
        }
        request.setAttribute("doc",doctorAlias);
        Review review = Lab3DBAO.readReview(revID);
        request.setAttribute("review", review);
    }
    protected void viewnextreviewhelper(HttpServletRequest request, HttpServletResponse response, Boolean next)
            throws java.sql.SQLException, ClassNotFoundException, NamingException {
        String doctorAlias = request.getParameter("docAlias");
        String strRevID = request.getParameter("revID");
        int revID = -1;
        if (!strRevID.equals("")) {
            revID = Integer.parseInt(strRevID);
        }
        request.setAttribute("doc",doctorAlias);
        if(next){
            revID = Lab3DBAO.getPrevReview(doctorAlias, revID);
        }else{
            revID = Lab3DBAO.getNextReview(doctorAlias, revID);
        }
        Review review = Lab3DBAO.readReview(revID);
        request.setAttribute("review", review);
    }
    protected void writereviewhelper(HttpServletRequest request, HttpServletResponse response, String userAlias)
            throws java.sql.SQLException, ClassNotFoundException, NamingException {
        String doctorAlias = request.getParameter("docAlias");
        String strStarRating = request.getParameter("rating");
        double rating = 0.0;
        if (!strStarRating.equals("")) {
            rating = Double.parseDouble(strStarRating);
            if (rating < 0.0) {
                rating = 0.0;
            }
        }
        String comments = request.getParameter("keywords");

        Lab3DBAO.writeReview(userAlias, doctorAlias, rating, comments);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP
     * <code>GET</code> method.
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
     * Handles the HTTP
     * <code>POST</code> method.
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
