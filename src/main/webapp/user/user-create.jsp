<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Guru Registration Form</title>
</head>
<body>
<h1>Guru Register Form</h1>
<form action="/user?action=fill" method="post">
    <table style="width: 50%">
        <tr>
            <td>Full Name</td>
            <td><input type="text" name="full_name"/></td>
        </tr>
        <tr>
            <td>Email</td>
            <td><input type="text" name="email"/></td>
        </tr>
        <tr>
            <td>Username</td>
            <td><input type="text" name="username"/></td>
        </tr>
        <tr>
            <td>Desired uploads amount</td>
            <td><input type="text" name="upload_limit"/></td>
        </tr>
    </table>
    <input type="submit" value="Fill user data"/></form>
<div class="mt-3"></div>
<h3>Please, select file to upload</h3>
<form action="/user?action=upload" method="post"
      enctype="multipart/form-data">
    <input type="file" name="file" size="100"/>
    <br/>
    <input type="submit" value="Upload File"/>
</form>
<form action="/user?action=register" method="post">
    <button type="submit">Register User</button>
</form>
</body>
</html>
