<%-- 
    Document   : patient_home
    Created on : 8-Mar-2015, 3:31:27 PM
    Author     : steal_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%@page import="ece356.UserData"%>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
    <%
        if(session.getAttribute("userData") != null){
            
        
    %>
    <a href="logOutServlet">Log Out</a>
        <ul>
            <li><a href="doctor_search.jsp">Doctor Search</a>
                    </li>
            <li><a href="patient_search.jsp">Patient Search</a>
                    <% if(!(Boolean)session.getAttribute("isDoctor")){%>
                    </li>
                    <li><a href=HandleFriendStatusServlet?action=viewrequest>View Friend Requests</a><br><br> </li>
                    <%}
                    else{%>
                    <li><form action="QueryServlet?qnum=2" method="POST">
                        <input type="hidden" name="docAlias" value="<%=((UserData)session.getAttribute("userData")).getAlias()%>" />
                        <input type="submit" value="View Your Profile" />
                    </form> </li>
                    <%
                    }%>
        </ul>
    <%}
        else{ 
            %>
            <a href="/ece356a2/index.jsp">Please login</a>
        <%
        }
            
        
    %>
    </body>
</html>
