/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import dto.Book;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 57869
 * 对book数据表的操作
 */  
public class BookDAO {
    private static final String JDBC_URL = "jdbc:mysql://bj-cynosdbmysql-grp-3uoflkim.sql.tencentcdb.com:20820/library?autoReconnect=true&useSSL=false";  // 确保数据库名称正确  ?autoReconnect=true&useSSL=false
    private static final String JDBC_USER = "root";  
    private static final String JDBC_PASSWORD = "Ds123456";  
    public BookDAO(){}
    static {  
        try {  
            // 注册 MySQL JDBC 驱动  
            Class.forName("com.mysql.cj.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException("MySQL JDBC Driver not found.");  
        }  
    }  
    
    // 用于获取所有书籍并显示
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
               addBooks(books, resultSet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
    
    // 用于根据名称模糊查询具体书籍
    public List<Book> searchBooksByTitleOrAuthor(String query) {
    List<Book> books = new ArrayList<>();
    String sql = "SELECT * FROM books WHERE title LIKE ? OR author LIKE ?";
    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + query + "%");
        statement.setString(2, "%" + query + "%");
        ResultSet resultSet = statement.executeQuery();
        while (resultSet.next()) {
           addBooks(books,resultSet);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return books;
    }
    
    public Book getBookById(int id) {
    Book book = null;
    try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
        String sql = "SELECT * FROM books WHERE book_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            book = new Book();
            book.setId(resultSet.getInt("book_id"));
            book.setTitle(resultSet.getString("title"));
            book.setAuthor(resultSet.getString("author"));
            book.setGenre(resultSet.getString("genre"));
            book.setDescription(resultSet.getString("description"));
            book.setPrice(BigDecimal.valueOf(Double.parseDouble(resultSet.getString("price"))));
            book.setAvailableQuantity(Integer.valueOf(resultSet.getString("available_quantity")));
            book.setPublishDate(resultSet.getDate("publish_date").toLocalDate());
            book.setPublisher(resultSet.getString("publisher"));
            book.setLanguage(resultSet.getString("language"));
            book.setCoverImage(resultSet.getString("cover_image"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return book;
}

    
    public void addBooks(List<Book> books, ResultSet resultSet) throws SQLException{
        Book book = new Book();
        book.setId(resultSet.getInt("book_id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        book.setGenre(resultSet.getString("genre"));
        book.setDescription(resultSet.getString("description"));
        book.setPrice(BigDecimal.valueOf(Double.parseDouble(resultSet.getString("price"))));
        book.setAvailableQuantity(Integer.valueOf(resultSet.getString("available_quantity")));
        book.setPublishDate(resultSet.getDate("publish_date").toLocalDate());
        book.setPublisher(resultSet.getString("publisher"));
        book.setLanguage(resultSet.getString("language"));
        book.setCoverImage(resultSet.getString("cover_image"));
        books.add(book);
    }
}
