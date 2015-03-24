<%-- 
    Document   : index
    Created on : 11-Jan-2013, 9:14:06 AM
    Author     : Wojciech Golab
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="ece356.Doctor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Search Results</title>
    </head>

    <%! ArrayList<Doctor> doctorList;%>
    <% doctorList = (ArrayList<Doctor>) request.getAttribute("doctorList");%>

    <body><a href="logOutServlet">Log Out</a>
            <a href="patient_home.jsp">Return Home</a>
        <%
            if (doctorList != null) {
                if(doctorList.size() > 0){
        %>
        <h1>Doctor Data</h1>
        <table border=1>
            <tr><th>docName</th><th>gender</th><th>avgStarRating</th><th>numOfReviews</th></tr>
            <%
                for (Doctor doc : doctorList) {
            %>
            <tr>
                <td><%= doc.getDoctorName()%></td>
                <td><%= doc.getGender()%></td>
                <td><%= doc.getAverageStarRating()%></td>
                <td><%= doc.getNumOfReviews()%></td>
                <td>
                    <!--<a href="QueryServlet?qnum=2&docAlias=<%=doc.getDoctorAlias()%>" >View Profile</a>-->
                    <form action="QueryServlet?qnum=2" method="POST">
                        <input type="hidden" name="docAlias" value="<%=doc.getDoctorAlias()%>" />
                        <input type="submit" value="View Profile" />
                    </form>
                </td>
            </tr>
            <%
                }
                
            
            %>
        </table>
            <%
                }
                else{
            
            %>
            <div>No Doctors match specified query</div>
            <%
                }
            }
            
            %>
        <p>
            <a href="doctor_search.jsp">Return to Search</a>
    </body>
</html>
