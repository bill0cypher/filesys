<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <title>
        User page
    </title>
</head>
<body>
<section>
    <%--<h3>User Information: ${user.id} | ${user.fullName}</h3>--%>
    <div class="container-fluid">
        <table class="d-table justify-content-center border-top border-bottom mt-3" style="width: 100%;">
            <thead>
            <tr>
                <th colspan="2">Users</th>
            </tr>
            </thead>
            <tbody>
            <jsp:useBean id="users" scope="request" class="java.util.ArrayList"/>
            <c:forEach var="user" items="${users}">
                <tr class="d-flex justify-content-between border-bottom">
                    <td class="col-md-3 d-table-cell border-left"><a href="?id=${user.id}">${user.id}</a></td>
                    <td class="col-md-3 d-table-cell">${user.fullName}</td>
                    <td class="col-md-3 d-table-cell border-right">${user.email}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </div>
</section>
</body>
</html>
