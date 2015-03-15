<%-- 
    Document   : index
    Created on : 11-Jan-2013, 9:14:06 AM
    Author     : Wojciech Golab
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="ece356.Employee"%>
<%@page import="ece356.Department"%>
<%@page import="ece356.Doctor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Lab 2</title>
    </head>

    <%! ArrayList<Doctor> doctorList;%>
    <%! ArrayList<Department> departmentList;%>
    <%! ArrayList employeeDepartmentList;%>
    <% doctorList = (ArrayList<Doctor>) request.getAttribute("doctorList");%>
    <% departmentList = (ArrayList<Department>) request.getAttribute("departmentList");%>
    <% employeeDepartmentList = (ArrayList) request.getAttribute("employeeDepartmentList");%>

    <body>
        <h1>Success!</h1>
        <%
            if (doctorList != null) {
        %>
        <h1>Doctor Data</h1>
        <table border=1><tr><th>docName</th><th>gender</th>><th>avgStarRating</th><th>numOfReviews</th></tr>
            <%
                for (Doctor doc : doctorList) {
            %>
            <tr>
                <td><%= doc.getDoctorName()%></td>
                <td><%= doc.getGender()%></td>
                <td><%= doc.getAverageStarRating()%></td>
                <td><%= doc.getNumOfReviews()%></td>
            </tr>
            <%
                }
            %>
        </table>
        <%
        } else if (departmentList != null) {
        %>
        <h1>Department Data</h1>
        <table border=1><tr><th>deptID</th><th>deptName</th><th>location</th></tr>
            <%
                for (Department dep : departmentList) {
            %>
            <tr>
                <td><%= dep.getDeptID()%></td>
                <td><%= dep.getDeptName()%></td>
                <td><%= dep.getLocation()%></td>
            </tr>
            <%
                }
            %>
        </table>        
        <%
        } else if (employeeDepartmentList != null) {
        %>
        <h1>Employee-Department Data</h1>
        <table border=1>
            <tr>
                <th>empID</th><th>empName</th><th>job</th><th>salary</th>
                <th>deptID</th><th>deptName</th><th>location</th>
            </tr>
            <%
                for (int i = 0; i < employeeDepartmentList.size(); i += 2) {
                    Employee em = (Employee) employeeDepartmentList.get(i);
                    Department dep = (Department) employeeDepartmentList.get(i + 1);
            %>
            <tr>
                <td><%= em.getEmpID()%></td>
                <td><%= em.getEmpName()%></td>
                <td><%= em.getJob()%></td>                
                <td><%= em.getSalary()%></td>
                <td><%= dep.getDeptID()%></td>
                <td><%= dep.getDeptName()%></td>
                <td><%= dep.getLocation()%></td>
            </tr>
            <%
                }
            %>
        </table>        
        <%
            }
        %>
        <p>
            <a href="index.jsp">return to main page</a>
    </body>
</html>
