<%-- 
    Document   : doctor_search
    Created on : 8-Mar-2015, 3:32:52 PM
    Author     : steal_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Doctor Search</title>
    </head>
    <body><a href="logOutServlet">Log Out</a>
        <h1>Doctor Search</h1>
        <form action="QueryServlet?qnum=1" method="POST">
            Doctor Name <input type="text"
                                      name="doctorName" value="" /><br><br>
            Gender <input type="text"
                                      name="gender" value="" /><br><br>
            <div style="font-weight: bold;">Work Address</div><br>
            Street <input type="text"
                                      name="street" value="" /><br><br>
            City <input type="text"
                                      name="city" value="" /><br>
            <br><br>
            Province: <select name="province">
                <option value=""></option>
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
            <!--need postal code validation-->
            Postal Code <input type="text"
                                      name="postalCode" value="" /><br><br>
            Specialization <input type="text"
                                      name="specialization" value="" /><br><br>
            Licensed for greater than <input type="number"
                                      name="yearsLicensed" value="" step="1" min="0" max="999"/> Years<br><br>
            Average Star Rating greater than <input type="number"
                                      name="avgStarRating" value="" step="0.5" min="0" max="5"/> out of 5<br><br>
            Reviewed by friends <input type="checkbox"
                                      name="reviewedByFriend" value="checked" /><br><br>
            Comment keywords <input type="text"
                                      name="keywords" value="" /><br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
