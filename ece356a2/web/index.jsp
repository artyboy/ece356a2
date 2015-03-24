<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
<%@page import="ece356.UserData"%>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ECE</title>
        <%
            String url = "/patient_home.jsp";
            if(session.getAttribute("userData") != null){
                getServletContext().getRequestDispatcher(url).forward(request, response);
            }
        %>
    </head>
    <body>
        <div style="text-align: center;">
	<div style="box-sizing: border-box; display: inline-block; width: auto; max-width: 480px; background-color: #7E587E; border: 2px solid #0361A8; border-radius: 5px; box-shadow: 0px 0px 8px #0361A8; margin: 50px auto auto;">
	<div style="background: #7E587E; border-radius: 5px 5px 0px 0px; padding: 15px;"><span style="font-family: verdana,arial; color: #D4D4D4; font-size: 1.00em; font-weight:bold;">Enter your login and password</span></div>
	<div style="background: #7E587E; padding: 15px">
	<style type="text/css" scoped>
	td { text-align:left; font-family: verdana,arial; color: #064073; font-size: 1.00em; }
	input { border: 1px solid #CCCCCC; border-radius: 5px; color: #666666; display: inline-block; font-size: 1.00em;  padding: 5px; width: 100%; }
	input[type="button"], input[type="reset"], input[type="submit"] { height: auto; width: auto; cursor: pointer; box-shadow: 0px 0px 5px #0361A8; float: right; text-align:right; margin-top: 10px; margin-left:7px;}
	table.center { margin-left:auto; margin-right:auto; }
	.error { font-family: verdana,arial; color: #D41313; font-size: 1.00em; }
	</style>
<form method="post" action="UserDataServlet" name="aform" target="_top">
<input type="hidden" name="action" value="login">
<input type="hidden" name="hide" value="">
<table class='center'>
<tr><td>Alias:</td><td><input type="text" name="alias"></td></tr>
<tr><td>Password:</td><td><input type="password" name="password"></td></tr>
<tr><td>&nbsp;</td><td><input type="submit" value="Submit"></td></tr>
<tr><td colspan=2>&nbsp;</td></tr>

<!--<tr><td colspan=2>Not member yet? Click <a href="newUserSignUp.jsp">here</a> to register.</td></tr>-->
</table>
</form>
</div></div></div>
    </body>
</html>
