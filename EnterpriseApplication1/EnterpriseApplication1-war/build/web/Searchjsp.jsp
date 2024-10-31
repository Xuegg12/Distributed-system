<%-- 
    Document   : Searchjsp
    Created on : 2024年10月29日, 下午7:53:06
    Author     : xwt
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>  
<!DOCTYPE html>  
<html>  
<head>  
    <title>Book Search</title>  
    <!-- 可以在这里添加CSS样式来美化界面 -->  
    <style>  
        body { font-family: Arial, sans-serif; margin: 40px; }  
        h1 { color: #333; }  
        form { margin-top: 20px; }  
        label { margin-right: 10px; }  
        input[type="text"] { padding: 8px; width: 200px; }  
        input[type="submit"] { padding: 8px 15px; background-color: #007bff; color: white; border: none; cursor: pointer; }  
        input[type="submit"]:hover { background-color: #0056b3; }  
    </style>  
</head>  
<body>  
    <h1>Book Search</h1>  
    <form action="book-servlet" method="get"> <!-- 将method改为get，以便在URL中显示搜索参数 -->  
        <label for="bookTitle">Title:</label>  
        <input type="text" id="bookTitle" name="bookTitle" placeholder="Enter book title" required="false"> <!-- 移除required属性 -->  
  
        <label for="author">Author:</label>  
        <input type="text" id="author" name="author" placeholder="Enter author name" required="false"> <!-- 移除required属性 -->  
  
        <input type="submit" value="Search">  
    </form>  
</body>  
</html>