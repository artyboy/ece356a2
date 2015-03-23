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
    <body>
        <h1>Doctor Search</h1>
        <form action="QueryServlet?qnum=1" method="POST">
            Doctor Name <input type="text"
                                      name="doctorName" value="" /><br>
            Gender <input type="text"
                                      name="gender" value="" /><br>
            Work Address<br>
            Street <input type="text"
                                      name="street" value="" /><br>
            City <input type="text"
                                      name="city" value="" /><br>
            Province <input type="text"
                                      name="province" value="" /><br>
            Country <input type="text"
                                      name="country" value="" /><br>
            <!--need postal code validation-->
            Postal Code <input type="text"
                                      name="postalCode" value="" /><br>
            Specialization <input type="text"
                                      name="specialization" value="" /><br>
            Licensed for greater than <input type="text"
                                      name="yearsLicensed" value="" /> Years<br>
            Average Star Rating greater than <input type="number"
                                      name="avgStarRating" value="" step="0.5" min="0" max="5"/> out of 5<br>
            Reviewed by friends <input type="checkbox"
                                      name="reviewedByFriend" value="checked" /><br>
            Comment keywords <input type="text"
                                      name="keywords" value="" /><br>
            <input type="submit" value="Submit" />
        </form>
    </body>
</html>
