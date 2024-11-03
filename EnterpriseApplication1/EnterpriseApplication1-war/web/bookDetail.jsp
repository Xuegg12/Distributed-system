<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Book Details</title>
    <style>
        img {
            width: 200px;
            height: auto;
        }
        .details {
            margin: 20px;
        }
    </style>
</head>
<body>
    <h2>Book Details</h2>
    
    <c:if test="${book != null}">
        <div class="details">
            <img src="${book.coverImage}" alt="${book.title} Cover">
            <h3>${book.title}</h3>
            <p><strong>Author:</strong> ${book.author}</p>
            <p><strong>Genre:</strong> ${book.genre}</p>
            <p><strong>Description:</strong> ${book.description}</p>
            <p><strong>Price:</strong> ${book.price}</p>
            <p><strong>Available Quantity:</strong> ${book.availableQuantity}</p>
            <p><strong>Publish Date:</strong> ${book.publishDate}</p>
            <p><strong>Publisher:</strong> ${book.publisher}</p>
            <p><strong>Language:</strong> ${book.language}</p>
        </div>
    </c:if>

    <form action="book-servlet" method="post">
        <input type="hidden" name="action" value="buy">
        <input type="hidden" name="bookId" value="${book.id}">
        <button type="submit">Buy</button>
    </form>
    <form action="book-servlet" method="post">
        <input type="hidden" name="action" value="borrow">
        <input type="hidden" name="bookId" value="${book.id}">
        <button type="submit">Borrow</button>
    </form>

    <!-- 返回个人信息按钮 -->
    <a href="user-servlet?action=viewProfile">UserProfile</a>
</body>
</html>
