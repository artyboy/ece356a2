<%-- 
    Document   : patient_search
    Created on : 23-Mar-2015, 12:44:06 AM
    Author     : steal_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
     <title>Search A Patient</title>
    </head>
    <body>
    <%
        if(session.getAttribute("userData") != null){    
    %>
        <a href="logOutServlet">Log Out</a>
        <h1>Welcome to Search a Patient feature!</h1>
        <form action="PatientSearchServlet" method="POST">
            Enter searching criteria in any combination:<br><br>
            Patient Alias: <input type="text" name="Palias" value=""/><br><br>
            Patient Province: <select name="Pprovince">
                <option value="Black"></option>
                <option value="Alberta">Alberta</option>
                <option value="British Columbia">British Columbia</option>
                <option value="Manitoba">Manitoba</option>
                <option value="New Brunswick">New Brunswick</option>
                <option value="Newfoundland and Labrador">Newfoundland and Labrador</option>
                <option value="Nova Scotia">Nova Scotia</option>
                <option value="Ontario">Ontario</option>
                <option value="Prince Edward Island">Prince Edward Island</option>
                <option value="Quebec">Quebec</option>
                <option value="Saskatchewan">Saskatchewan</option>
                <option value="Northwest Territories">Northwest Territories</option>
                <option value="Nunavut">Nunavut</option>
                <option value="Yukon">Yukon</option>
            </select><br><br>
            Patient City: <input type="text" name="Pcity" value=""/><br><br><br>
            <input type="submit" value="Search">
        </form>
        <br><a href="patient_home.jsp">Return home</a>
        <%
        }
        else{
        %>
        <a href="/ece356a2/index.jsp">Please login</a>
        <%
        }
        %>
    </body>
</html>
