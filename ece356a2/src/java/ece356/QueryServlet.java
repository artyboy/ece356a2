/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ece356;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

        String url;
        try {
            if (intQueryNum == 1) {
                querydoctorhelper(request, response);
            } 
            /*else if (intQueryNum == 2) {
                ArrayList ret = Lab3DBAO.getDepartments();
                request.setAttribute("departmentList", ret);
            } else if (intQueryNum == 3) {
                query3helper(request, response);
            } */
            else {
                throw new RuntimeException("Invalid query number: " + intQueryNum);
            }
            url = "/success.jsp";
        } catch (Exception e) {
            request.setAttribute("exception", e);
            url = "/fancyError.jsp";
        }
        getServletContext().getRequestDispatcher(url).forward(request, response);
    }

    protected void query3helper(HttpServletRequest request, HttpServletResponse response)
            throws java.sql.SQLException, ClassNotFoundException {
        String strEmpID = request.getParameter("empID");
        int empID = -1;
        if (!strEmpID.equals("")) {
            empID = Integer.parseInt(strEmpID);
            if (empID <= 0) {
                throw new RuntimeException("Employee ID out of range");
            }
        }
        String empName = request.getParameter("empName");
        String strDeptID = request.getParameter("deptID");
        int deptID = -1;
        if (!strDeptID.equals("")) {
            deptID = Integer.parseInt(strDeptID);
            if (deptID <= 0) {
                throw new RuntimeException("Department ID out of range");
            }
        }
        String job = request.getParameter("job");
        String strSalary = request.getParameter("salary");
        int salary = -1;
        if (!strSalary.equals("")) {
            salary = Integer.parseInt(strSalary);
            if (salary <= 0) {
                throw new RuntimeException("Salary out of range");
            }
        }

        ArrayList ret = Lab3DBAO.queryEmployee(empID, empName, deptID, job, salary);
        request.setAttribute("employeeList", ret);
    }
    protected void querydoctorhelper(HttpServletRequest request, HttpServletResponse response)
            throws java.sql.SQLException, ClassNotFoundException {
        /*String strEmpID = request.getParameter("empID");
        int empID = -1;
        if (!strEmpID.equals("")) {
            empID = Integer.parseInt(strEmpID);
            if (empID <= 0) {
                throw new RuntimeException("Employee ID out of range");
            }
        }*/
        String doctorName = request.getParameter("doctorName");
        String gender = request.getParameter("gender");
        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String province = request.getParameter("province");
        String country = request.getParameter("country");
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
            if (yearsLicensed < 0.0) {
                avgStarRating = 0.0;
            }
        }
        String comments = request.getParameter("keywords");
        Boolean reviewedByFriend = false;
        if(request.getParameter("reviewedByFriend") != null){
            reviewedByFriend = true;
        }
        /*
        String strSalary = request.getParameter("salary");
        int salary = -1;
        if (!strSalary.equals("")) {
            salary = Integer.parseInt(strSalary);
            if (salary <= 0) {
                throw new RuntimeException("Salary out of range");
            }
        }*/

        ArrayList ret = Lab3DBAO.queryDoctor(doctorName, gender, street, city, 
                province, country, postalCode, specialization, yearsLicensed, 
                avgStarRating,comments, reviewedByFriend);
        request.setAttribute("doctorList", ret);
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
