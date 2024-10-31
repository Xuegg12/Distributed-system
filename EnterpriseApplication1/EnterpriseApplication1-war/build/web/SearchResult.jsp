<%-- 
    Document   : SearchResult
    Created on : 2024年10月29日, 下午8:54:25
    Author     : xwt
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>  
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>  
<head>  
    <title>Book Information</title>  
    <style>  
        .book-info {  
            font-family: Arial, sans-serif;  
            border: 1px solid #ccc;  
            padding: 10px;  
            margin: 10px;  
            max-width: 600px;  
        }  
        .book-info div {  
            margin-bottom: 5px;  
        }  
    </style>  
</head>  
<body>  
<div class="book-info">  
    <%  
        String bookInfo = (String) request.getAttribute("bookList");  
        out.println(bookInfo);
    %>  
</div>  
</body>  
</html>