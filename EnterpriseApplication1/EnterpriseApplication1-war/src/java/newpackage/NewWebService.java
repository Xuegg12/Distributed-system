/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/WebService.java to edit this template
 */
package newpackage;

import javax.jws.WebService;  
import javax.jws.WebMethod;  
import javax.jws.WebParam;  
import java.sql.*;  
  
/**  
 * @author xwt  
 */  
@WebService(serviceName = "BookWebService")  
public class NewWebService {  
  
   
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/bookinfo?autoReconnect=true&useSSL=false";  // 确保数据库名称正确  ?autoReconnect=true&useSSL=false
    private static final String JDBC_USER = "root";  
    private static final String JDBC_PASSWORD = "123456";  
  
    static {  
        try {  
            // 注册 MySQL JDBC 驱动  
            Class.forName("com.mysql.cj.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException("MySQL JDBC Driver not found.");  
        }  
    }  
  
    // 添加书籍  
    @WebMethod(operationName = "AddBook")  
    public String AddBook(@WebParam(name = "title") String title,  
                          @WebParam(name = "author") String author,  
                          @WebParam(name = "publisher") String publisher,  
                          @WebParam(name = "price") double price,  
                          @WebParam(name = "publishDate") String publishDate,  
                          @WebParam(name = "isbn") String isbn,  
                          @WebParam(name = "genre") String genre,  
                          @WebParam(name = "description") String description) {  
        String sql = "INSERT INTO books (title, author, publisher, price, publish_date, isbn, genre, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";  
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);  
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  
            pstmt.setString(1, title);  
            pstmt.setString(2, author);  
            pstmt.setString(3, publisher);  
            pstmt.setDouble(4, price);  
            pstmt.setString(5, publishDate);  
            pstmt.setString(6, isbn);  
            pstmt.setString(7, genre);  
            pstmt.setString(8, description);  
            int rows = pstmt.executeUpdate();  
            return "Book added successfully. Rows affected: " + rows;  
        } catch (SQLException e) {  
            return "Error adding book: " + e.getMessage();  
        }  
    }  
  
    // 删除书籍  
    @WebMethod(operationName = "DeleteBook")  
    public String DeleteBook(@WebParam(name = "id") int id) {  
        String sql = "DELETE FROM books WHERE id = ?";  
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);  
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  
            pstmt.setInt(1, id);  
            int rows = pstmt.executeUpdate();  
            return "Book deleted successfully. Rows affected: " + rows;  
        } catch (SQLException e) {  
            return "Error deleting book: " + e.getMessage();  
        }  
    }  
  
    // 更新书籍  
    @WebMethod(operationName = "UpdateBook")  
    public String UpdateBook(@WebParam(name = "id") int id,  
                             @WebParam(name = "title") String title,  
                             @WebParam(name = "author") String author,  
                             @WebParam(name = "publisher") String publisher,  
                             @WebParam(name = "price") double price,  
                             @WebParam(name = "publishDate") String publishDate,  
                             @WebParam(name = "isbn") String isbn,  
                             @WebParam(name = "genre") String genre,  
                             @WebParam(name = "description") String description) {  
        String sql = "UPDATE books SET title = ?, author = ?, publisher = ?, price = ?, publish_date = ?, isbn = ?, genre = ?, description = ? WHERE id = ?";  
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);  
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  
            pstmt.setString(1, title);  
            pstmt.setString(2, author);  
            pstmt.setString(3, publisher);  
            pstmt.setDouble(4, price);  
            pstmt.setString(5, publishDate);  
            pstmt.setString(6, isbn);  
            pstmt.setString(7, genre);  
            pstmt.setString(8, description);  
            pstmt.setInt(9, id);  
            int rows = pstmt.executeUpdate();  
            return "Book updated successfully. Rows affected: " + rows;  
        } catch (SQLException e) {  
            return "Error updating book: " + e.getMessage();  
        }  
    }  
  
    // 查询书籍  
    @WebMethod(operationName = "GetBook")  
    public String GetBook(@WebParam(name = "id") int id) {  
        String sql = "SELECT * FROM books WHERE id = ?";  
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);  
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  
            pstmt.setInt(1, id);  
            ResultSet rs = pstmt.executeQuery();  
            if (rs.next()) {  
                String bookInfo = "ID: " + rs.getInt("id") +  
                        ", Title: " + rs.getString("title") +  
                        ", Author: " + rs.getString("author") +  
                        ", Publisher: " + rs.getString("publisher") +  
                        ", Price: " + rs.getDouble("price") +  
                        ", Publish Date: " + rs.getString("publish_date") +  
                        ", ISBN: " + rs.getString("isbn") +  
                        ", Genre: " + rs.getString("genre") +  
                        ", Description: " + rs.getString("description");  
                return bookInfo;  
            } else {  
                return "Book not found.";  
            }  
        } catch (SQLException e) {  
            return "Error retrieving book: " + e.getMessage();  
        }  
    }  
  
    // 查询书籍通过书名或作者  
    @WebMethod(operationName = "GetBookByTitleOrAuthor")  
    public String GetBookByTitleOrAuthor(@WebParam(name = "title") String title,  
                                         @WebParam(name = "author") String author) {  
        String sql = "SELECT * FROM books WHERE title = ? OR author = ?";  
        try (Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);  
             PreparedStatement pstmt = conn.prepareStatement(sql)) {  
            pstmt.setString(1, title);  
            pstmt.setString(2, author);  
            ResultSet rs = pstmt.executeQuery();  
            StringBuilder bookInfo = new StringBuilder();  
            while (rs.next()) {  
                bookInfo.append("ID: ").append(rs.getInt("id")).append(", ")  
                        .append("Title: ").append(rs.getString("title")).append(", ")  
                        .append("Author: ").append(rs.getString("author")).append(", ")  
                        .append("Publisher: ").append(rs.getString("publisher")).append(", ")  
                        .append("Price: ").append(rs.getDouble("price")).append(", ")  
                        .append("Publish Date: ").append(rs.getString("publish_date")).append(", ")  
                        .append("ISBN: ").append(rs.getString("isbn")).append(", ")  
                        .append("Genre: ").append(rs.getString("genre")).append(", ")  
                        .append("Description: ").append(rs.getString("description")).append("\n");  
            }  
            if (bookInfo.length() > 0) {  
                return bookInfo.toString();  
            } else {  
                return "Book not found.";  
            }  
        } catch (SQLException e) {  
            return "Error retrieving book: " + e.getMessage();  
        }  
    }  
}
