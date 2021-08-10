<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"/>
    <title>
        User Overview
    </title>
</head>
<body>
<section>
    <jsp:useBean id="user" scope="request" class="model.User"/>
    <%--<h3>User Information: ${user.id} | ${user.fullName}</h3>--%>
    <div class="container-fluid">
        <div class="member-name-link">
            <div class="row mt-3 mb-1">
                <strong>${user.fullName}'s profile</strong>
            </div>
        </div>
        <div>
            <table class="d-table justify-content-center border-top border-bottom mt-3" style="width: 50%;">
                <thead>User's files</thead>
                <tbody>
                <tr class="d-flex justify-content-between border-top">
                    <td class="col-md-3 d-table-cell border-left">File no.</td>
                    <td class="col-md-3 d-table-cell">Filename</td>
                    <td class="col-md-3 d-table-cell">Action</td>
                </tr>
                <c:forEach var="file" items="${user.files}">
                    <tr class="d-flex justify-content-between border-bottom">
                        <td class="col-md-3 d-table-cell border-left">${file.id}</td>
                        <td class="col-md-3 d-table-cell">${file.fileName}</td>
                        <td class="col-md-3 d-table-cell border-right"><a href="?file_id=${file.id}">Delete</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="">
            <table class="d-table justify-content-center border-top border-bottom mt-3" style="width: 50%;">
                <thead>User's uploads history</thead>
                <tbody>
                <tr class="d-flex justify-content-between border-top">
                    <td class="col-md-3 d-table-cell border-left">Event no.</td>
                    <td class="col-md-3 d-table-cell">Event type</td>
                    <td class="col-md-3 d-table-cell">Filename</td>
                    <td class="col-md-3 d-table-cell border-right">Uploaded time</td>
                </tr>
                <c:forEach var="event" items="${user.events}">
                    <tr class="d-flex justify-content-between border-bottom">
                        <td class="col-md-3 d-table-cell border-left"><a href="?id=${event.id}">${event.id}</a></td>
                        <td class="col-md-3 d-table-cell">${event.eventType}</td>
                        <td class="col-md-3 d-table-cell">${event.file.fileName}</td>
                        <td class="col-md-3 d-table-cell border-right">${event.uploadedTime}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="row mt-2">
                <div class="col-md-2">
                    <a class="btn btn-info" href="?action=update&uid=${user.id}">Update info</a>
                </div>
                <div class="col-md-2">
                    <a class="btn btn-info" href="?action=create">Create user</a>
                </div>
                <div class="col-md-2">
                    <a class="btn btn-danger" href="?action=delete&id=${user.id}">Delete user and related history</a>
                </div>
            </div>
        </div>
    </div>
</section>
</body>
</html>
