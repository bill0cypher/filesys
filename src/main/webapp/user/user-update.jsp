<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>Guru Registration Form</title>
</head>
<body>
<jsp:useBean id="user" scope="request" class="model.User"/>
<div class="container-fluid">
    <div style="width: 50%">
        <div class="d-inline-flex" style="width: 100%">
            <div class="col-md-6">
                <h1>${user.fullName} profile</h1>
            </div>
            <div class="col-md-6">
                <form class="float-right mr-2" action="/user?action=update" method="post">
                    <button class="btn btn-danger" type="submit">Update User</button>
                </form>
                <form class="float-right" action="/user?action=delete" method="post">
                    <button class="btn btn-danger" type="submit">Delete profile and related history</button>
                </form>
            </div>
        </div>
        <form class="border-info" action="/user?action=fill" method="post">
            <table style="width: 100%">
                <tr>
                    <td>Full Name</td>
                    <td class="float-right"><input type="text" name="full_name" value="${user.fullName}"/></td>
                </tr>
                <tr>
                    <td>Email</td>
                    <td class="float-right"><input type="text" name="email" value="${user.email}"/></td>
                </tr>
            </table>
            <button class="btn btn-info" type="submit">Update personals</button>
        </form>
        <div class="mt-3"></div>
        <h3>Please, select file to upload</h3>
        <form class="border-info" action="/user?action=upload" method="post"
              enctype="multipart/form-data">
            <input type="file" name="file" size="100"/>
            <br/>
            <button class="btn btn-info mt-1" type="submit">Upload File</button>
        </form>
    </div>
</div>
</body>
</html>
