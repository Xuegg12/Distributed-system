<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <form action="book-servlet" method="get">
        <input type="text" id="bookInfo" name="bookInfo" placeholder="Enter book title or author" required="false">
        <input type="submit" value="Search">  
    </form>

    <title>Book List</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        th {
            background-color: #f4f4f4;
        }
        img {
            width: 100px;
            height: auto;
        }
    </style>
</head>
<body>

<h2>Book List</h2>

<table>
    <thead>
        <tr>
            <th>ID</th>
            <th>Cover</th>
            <th>Title</th>
            <th>Author</th>
            <th>Genre</th>
            <th>Description</th>
            <th>Price</th>
            <th>Available Quantity</th>
            <th>Publish Date</th>
            <th>Publisher</th>
            <th>Language</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="book" items="${books}">
            <tr>
                <td>${book.id}</td>
                <!-- 图片链接 -->
                <td>
                    <a href="book-servlet?action=view&id=${book.id}">
                        <img src="${book.coverImage}" alt="${book.title} Cover">
                    </a>
                </td>
                <!-- 标题链接 -->
                <td>
                    <a href="book-servlet?action=view&id=${book.id}">
                        ${book.title}
                    </a>
                </td>
                <td>${book.author}</td>
                <td>${book.genre}</td>
                <td>${book.description}</td>
                <td>${book.price}</td>
                <td>${book.availableQuantity}</td>
                <td>${book.publishDate}</td>
                <td>${book.publisher}</td>
                <td>${book.language}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>

</body>
</html>
