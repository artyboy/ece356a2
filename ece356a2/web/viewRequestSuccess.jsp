

<%@page import="ece356.Patient"%>
<%@page import="java.util.ArrayList"%>
<%@page import="ece356.Employee"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Patient Information</title>
    </head>

    <%! ArrayList<Patient> viewRequestList;%>
    <% viewRequestList = (ArrayList<Patient>) request.getAttribute("viewRequest");%>
   

    <body>
        <%
            if (viewRequestList != null) {
                out.println("<h1>Friend Requests</h1>");
                out.println("<table border=1>");
                out.println("<tr><th>Patient Alias</th><th>Requests</th></tr>");
                for (Patient p : viewRequestList) {
                    out.println("<tr><td>");
                    String palias = p.getPatientAlias();
                    out.print(palias);
                    out.print("</td><td>");
                    out.print("<a href=\"HandleFriendStatusServlet?action=send&palias="+p.getPatientAlias()+"\">Accept/Request</a>");
                }
                out.println("</table>");
            }
        %>
        <p>   
        <a href="index.jsp">return to  Search A Patient main page</a>
    </body>
</html>
