<%-- 
    Document   : user_dat_form
    Created on : 8-Mar-2015, 2:38:32 PM
    Author     : steal_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html;
              charset=UTF-8">
        <title>Page2</title>
    </head>
    <body>
        <h1>User data form</h1>
        <form action="UserDataServlet" method="POST">
            what is your name: <input type="text"
                                      name="username" value="" /><br>
            what is your favorite color: <select
                name="usercolour">
                <option>red</option>
                <option>green</option>
                <option>blue</option>
            </select><br>
            <input type="submit" value="Submit Query" />
        </form>
    </body>
</html>