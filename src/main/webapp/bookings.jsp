<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title></title>
</head>
<body>
<h1>Here you can find info about your booking</h1>
<form action="/booking" method="post">
    <label for="user-field">Your Booking Number</label>

    <input id="user-field" type="text" name="id">
    <input type="submit" value="Get info">
</form>
<br>
<a href="\">back to main</a>
</body>
</html>