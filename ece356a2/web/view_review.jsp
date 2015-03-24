<%-- 
    Document   : view_review
    Created on : 15-Mar-2015, 9:53:22 PM
    Author     : steal_000
--%>

<%@page import="ece356.Doctor"%>
<%@page import="ece356.Review"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Review</title>
    </head>
    <%! Review review;%>
    <% review = (Review) request.getAttribute("review");%>
    <%! String docAlias;%>
    <% docAlias = (String) request.getAttribute("doc");%>
    <body><a href="logOutServlet">Log Out</a>
        <h1>Review for: <%= docAlias%></h1>
        <div>Date: <%= review.getDate()%></div>
        <div>Rating: <%= review.getStarRating()%></div>
        <div>Comments: <%= review.getComments()%></div>
        <form action="QueryServlet?qnum=7&revID=<%=review.getRevID()%>" method="POST">
            <input type="hidden" name="docAlias" value="<%=docAlias%>" />
            <input type="submit" value="Previous Review" />
        </form>
        <form action="QueryServlet?qnum=2" method="POST">
            <input type="hidden" name="docAlias" value="<%=docAlias%>" />
            <input type="submit" value="Return to Profile" />
        </form>
        <form action="QueryServlet?qnum=6&revID=<%=review.getRevID()%>" method="POST">
            <input type="hidden" name="docAlias" value="<%=docAlias%>" />
            <input type="submit" value="Next Review" />
        </form>
    </body>
</html>
