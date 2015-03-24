<%@page contentType="text/html" pageEncoding="UTF-8"%>

<html>
<head>
<title>Form 1</title>
</head>
<body>
<h2>Thank you for entering your user data.</h2>

<jsp:useBean id="userData" class="ece356.UserData" scope="session"/>

GREETING:  Welcome to Sai & Nathan Clinic in Waterloo <%= userData.getAlias() %>!<br/>


<p>
                        <a href="patient_home.jsp">Patient Home</a>
<a href="logOutServlet">Log Out</a>

</body>
</html>