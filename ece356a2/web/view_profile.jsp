<%-- 
    Document   : view_profile
    Created on : 15-Mar-2015, 3:28:07 PM
    Author     : steal_000
--%>
<%@page import="java.util.ArrayList"%>
<%@page import="ece356.Doctor"%>
<%@page import="ece356.UserData"%>
<%@page import="ece356.Address"%>
<%@page import="ece356.Specialization"%>
<%@page import="ece356.Review"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Profile</title>
    </head>
    
    <%! Doctor doc;%>
    <% doc = (Doctor) request.getAttribute("doctor");%>
    <%! ArrayList<Address> addressList;%>
    <% addressList = (ArrayList<Address>) request.getAttribute("addressList");%>
    <%! ArrayList<Specialization> specList;%>
    <% specList = (ArrayList<Specialization>) request.getAttribute("specList");%>
    <%! ArrayList<Review> reviewList;%>
    <% reviewList = (ArrayList<Review>) request.getAttribute("reviewList");%>
    
    <body>
    <%
        if(session.getAttribute("userData") != null){    
    %>
    <%
        if (doc != null) {
    %>
        <a href="logOutServlet">Log Out</a>
        <a href="patient_home.jsp">Return Home</a>
        <h1><%= doc.getDoctorName()%></h1>
        <%
        if(
            !(Boolean)session.getAttribute("isDoctor")){
        
        %>
        <div>
            <form action="QueryServlet?qnum=3" method="post">
                <input type="hidden" name="docAlias" value="<%= doc.getDoctorAlias()%>"/>
                <input type="submit" name="Write Review" value="Write Review"/>
            </form>
        </div><br>
        <%
        }
        %>
        <span>Gender: <%= doc.getGender()%></span>
        <div>Year Licensed: <%= doc.getYearsLicensed()%></div>
        <div>Avg Rating: <%= doc.getAverageStarRating()%></div>
        <div>Number of Reviews: <%= doc.getNumOfReviews()%></div>
        <%
        if(
            (Boolean)session.getAttribute("isDoctor") && (((UserData)session.getAttribute("userData")).getAlias().equals(doc.getDoctorAlias()))){
            out.println("<div>Email:"+doc.getEmail()+" </div>");
        }
        %>
        <%
            if (addressList != null) {
        %>
        <div>Addresses: </div>
        
        <ul>
            <%
                for (Address adrs : addressList) {
            %>
            <li>
                <span><%= adrs.getStreet()%>,</span>
                <span><%= adrs.getCity()%>,</span>
                <span><%= adrs.getProvince()%>,</span>
                <span><%= adrs.getPostalCode()%></span>
            </li>
            <%
                }
            %>
        </ul>
        <%
            }
        %>
        
        <%
            if (specList != null) {
        %>
        <div>Specializations: </div>
        
        <ul>
            <%
                for (Specialization spec : specList) {
            %>
            <li>
                <span><%= spec.getSpecialization()%></span>
            </li>
            <%
                }
            %>
        </ul>
        
        <%
            }
        %>
        <%
            if (reviewList != null) {
        %>
        <table border=1><tr><th>date</th><th>rating</th><th>comments</th></tr>
            <%
                for (Review rev : reviewList) {
            %>
            <tr>
                <td><%= rev.getDate()%></td>
                <td><%= rev.getStarRating()%></td>
                <td><%= rev.getComments().substring(0, Math.min(rev.getComments().length(),10))%>...</td>
                <td>
                    <form action="QueryServlet?qnum=5&revID=<%=rev.getRevID()%>" method="POST">
                        <input type="hidden" name="docAlias" value="<%=doc.getDoctorAlias()%>" />
                        <input type="submit" value="Read Review" />
                    </form>
                </td>
            </tr>
            <%
                }
            %>
        </table>
        
    <%
        }
            
        %>
        <!--<a href="QueryServlet?qnum=1">Go back to search</a>-->
         <%
        }
            
        %>
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
