

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

    <%! ArrayList<Patient> patientInfo;%>
    <% patientInfo = (ArrayList<Patient>) request.getAttribute("searchAPatient");%>
   

    <body>
        <%
            if (patientInfo != null) {
                out.println("<h1>Patient Info</h1>");
                out.println("<table border=1>");
                out.println("<tr><th>Patient Alias</th><th>City</th><th>Province</th><th>Email</th><th>Patient Name</th><th>Friend Status</th></tr>");
                for (Patient p : patientInfo) {
                    out.println("<tr><td>");
                    String palias = p.getPatientAlias();
                    out.print(palias);
                    out.print("</td><td>");
                    out.print(p.getCity());
                    out.print("</td><td>");
                    out.print(p.getProvince());
                    out.print("</td><td>");
                    out.print(p.getEmail());
                    out.print("</td><td>");
                    out.print(p.getPatientName());
                    if(p.getFriendStatus() == 0){
                        out.print("</td><td>");
                        out.print("<a href=\"HandleFriendStatusServlet?action=send&palias="+p.getPatientAlias()+"\">Send Friend request</a>");
                    }else if(p.getFriendStatus() == 1){
                        out.print("</td><td>");
                        out.print("<a href=\"HandleFriendStatusServlet?action=accept&palias="+p.getPatientAlias()+"\">Accept Friend Request</a>");
                    }else if(p.getFriendStatus() == 2){
                        out.print("</td><td>");
                        out.print("Not Accepted Yet");
                    }else if(p.getFriendStatus() == 3){
                        out.print("</td><td>");
                        out.print("Already Friends");
                    }
                }
                out.println("</table>");
                
            }
        %>
        <p>
        <a href=HandleFriendStatusServlet?action=viewrequest>View Friend Requests</a><br><br>  
        <a href="patient_search.jsp">return to Search A Patient main page</a>
    </body>
</html>
