

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
   

    <body><a href="logOutServlet">Log Out</a>
        <%
            if (patientInfo != null) {
                out.println("<h1>Patient Info</h1>");
                out.println("<table border=1>");
                out.println("<tr><th>Patient Alias</th><th>City</th><th>Province</th><th>Email</th><th>Patient Name</th>");
                if(!(Boolean)session.getAttribute("isDoctor")){
                    out.println("<th>Friend Status</th></tr>");
                }else{
                     out.println("</tr>");
                }
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
                    if(!(Boolean)session.getAttribute("isDoctor")){
                    if(p.getFriendStatus() == 0){
                        out.print("</td><td>");
                        out.print("<a href=\"HandleFriendStatusServlet?action=send&url=search&palias="+p.getPatientAlias()+"\">Send Friend request</a>");
                    }else if(p.getFriendStatus() == 1){
                        out.print("</td><td>");
                        out.print("<a href=\"HandleFriendStatusServlet?action=accept&url=search&palias="+p.getPatientAlias()+"\">Accept Friend Request</a>");
                    }else if(p.getFriendStatus() == 2){
                        out.print("</td><td>");
                        out.print("Not Accepted Yet");
                    }else if(p.getFriendStatus() == 3){
                        out.print("</td><td>");
                        out.print("Already Friends");
                    }
                    }
                }
                out.println("</table>");
                
            }
        %>
        <p> 
        <a href="patient_search.jsp">return to Search A Patient main page</a>
    </body>
</html>
