<%-- 
    Document   : patient_home
    Created on : 8-Mar-2015, 3:31:27 PM
    Author     : steal_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body><a href="logOutServlet">Log Out</a>
        <ul>
            <li><a href="doctor_search.jsp">Doctor Search</a>
                    </li>
            <li><a href="patient_search.jsp">Patient Search</a>
                    </li>
        <a href=HandleFriendStatusServlet?action=viewrequest>View Friend Requests</a><br><br> 
        </ul>
    </body>
</html>
