<!-- userProfile.jsp -->
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>用户个人信息</title>
</head>
<body>
<h2>个人信息</h2>
<p>用户名: ${user.userName}</p>
<p>电子邮件: ${user.email}</p>
<p>账户余额: ${user.balance}</p>

<h3>借阅的书籍</h3>
<c:forEach var="rentedBook" items="${rentedBooks}">
    <p>${rentedBook.title}</p>
</c:forEach>

<h3>购买的书籍</h3>
<c:forEach var="purchasedBook" items="${purchasedBooks}">
    <p>${purchasedBook.title}</p>
</c:forEach>
</body>
</html>
