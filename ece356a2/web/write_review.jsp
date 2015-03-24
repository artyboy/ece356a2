<%-- 
    Document   : write_review
    Created on : 15-Mar-2015, 7:40:50 PM
    Author     : steal_000
--%>

<%@page import="ece356.Doctor"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Write Review</title>
    </head>
    <%! String docAlias;%>
    <% docAlias = (String) request.getAttribute("docAlias");%>
    <body><a href="logOutServlet">Log Out</a>
        <h1>Review for: <%= docAlias%></h1>
        <form action="QueryServlet?qnum=4&docAlias=<%= docAlias%>" method="POST">
            Star Rating<input type="number"
                                      name="rating" value="" step="0.5" min="0" max="5"/> out of 5<br>
            Comment keywords<br> <textarea type="textarea"
                                           name="keywords" value="" maxlength="1000" /></textarea><br>
            <input type="submit" value="Submit Review" />
        </form>
    </body>
</html>
