/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

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
 * 对user数据表的操作
 */
public class UserDAO {
    private static final String JDBC_URL = "jdbc:mysql://bj-cynosdbmysql-grp-3uoflkim.sql.tencentcdb.com:20820/library?autoReconnect=true&useSSL=false";  // 确保数据库名称正确  ?autoReconnect=true&useSSL=false
    private static final String JDBC_USER = "root";  
    private static final String JDBC_PASSWORD = "Ds123456"; 
    
    static {  
        try {  
            // 注册 MySQL JDBC 驱动  
            Class.forName("com.mysql.cj.jdbc.Driver");  
        } catch (ClassNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException("MySQL JDBC Driver not found.");  
        }  
    }

    // 购买图书的方法
    public boolean purchaseBook(int userId, int bookId) {
        String updateBalanceQuery = "UPDATE users SET balance = balance - ? WHERE user_id = ?";
        String updateQuantityQuery = "UPDATE books SET available_quantity = available_quantity - 1 WHERE book_id = ?";
        String insertPurchaseRecordQuery = "INSERT INTO reservations (user_id, book_id, status) VALUES (?, ?, 'completed')";

        PreparedStatement updateBalanceStmt = null;
        PreparedStatement updateQuantityStmt = null;
        PreparedStatement insertPurchaseRecordStmt = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)) {
            connection.setAutoCommit(false);

            // 获取书籍的价格
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);
            if (book == null || book.getAvailableQuantity() <= 0) {
                return false;
            }

            // 获取用户的余额
            User user = getUserById(userId);
            if (user == null || user.getBalance().compareTo(book.getPrice()) < 0) {
                return false;
            }

            // 更新用户余额
            updateBalanceStmt = connection.prepareStatement(updateBalanceQuery);
            updateBalanceStmt.setBigDecimal(1, book.getPrice());
            updateBalanceStmt.setInt(2, userId);
            updateBalanceStmt.executeUpdate();

            // 更新图书数量
            updateQuantityStmt = connection.prepareStatement(updateQuantityQuery);
            updateQuantityStmt.setInt(1, bookId);
            updateQuantityStmt.executeUpdate();

            // 插入一条购买（预订记录）
            insertPurchaseRecordStmt = connection.prepareStatement(insertPurchaseRecordQuery);
            insertPurchaseRecordStmt.setInt(1, userId);
            insertPurchaseRecordStmt.setInt(2, bookId);
            insertPurchaseRecordStmt.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 借阅图书的方法
    public boolean borrowBook(int userId, int bookId) {
        String updateQuantityQuery = "UPDATE books SET available_quantity = available_quantity - 1 WHERE book_id = ?";
        String insertRentalRecordQuery = "INSERT INTO rentals (user_id, book_id, status) VALUES (?, ?, 'rented')";

        PreparedStatement updateQuantityStmt = null;
        PreparedStatement insertRentalRecordStmt = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD)){
            connection.setAutoCommit(false);

            // 检查图书是否可获得
            BookDAO bookDAO = new BookDAO();
            Book book = bookDAO.getBookById(bookId);
            if (book == null || book.getAvailableQuantity() <= 0) {
                return false;
            }

            // 更新图书数量
            updateQuantityStmt = connection.prepareStatement(updateQuantityQuery);
            updateQuantityStmt.setInt(1, bookId);
            updateQuantityStmt.executeUpdate();

            // 插入一条借阅记录
            insertRentalRecordStmt = connection.prepareStatement(insertRentalRecordQuery);
            insertRentalRecordStmt.setInt(1, userId);
            insertRentalRecordStmt.setInt(2, bookId);
            insertRentalRecordStmt.executeUpdate();

            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // 获取用户的借阅数量
    public int getUserBorrowedCount(int userId) {
        String query = "SELECT COUNT(*) FROM rentals WHERE user_id = ? AND status = 'rented'";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    // 获取用户余额
    public BigDecimal getUserBalance(int userId) {
        String query = "SELECT balance FROM users WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBigDecimal("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return BigDecimal.ZERO;
    }
    
     // 获取用户信息
    public User getUserById(int userId) {
        User user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("user_id"));
                user.setUserName(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setBalance(rs.getBigDecimal("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
    // 获取用户的借阅记录
    public List<Book> getRentedBooks(int userId) {
        List<Book> rentedBooks = new ArrayList<>();
        String sql = "SELECT b.title, COUNT(r.book_id) AS quantity " +
                     "FROM rentals r JOIN books b ON r.book_id = b.book_id " +
                     "WHERE r.user_id = ? AND r.status = 'rented' " +
                     "GROUP BY r.book_id";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAvailableQuantity(rs.getInt("quantity"));
                rentedBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rentedBooks;
    }

    // 获取用户的购买记录
    public List<Book> getPurchasedBooks(int userId) {
        List<Book> purchasedBooks = new ArrayList<>();
        String sql = "SELECT b.title, COUNT(p.book_id) AS quantity " +
                     "FROM reservations p JOIN books b ON p.book_id = b.book_id " +
                     "WHERE p.user_id = ? " +
                     "GROUP BY p.book_id";

        try (Connection connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
            PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setAvailableQuantity(rs.getInt("quantity"));
                purchasedBooks.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return purchasedBooks;
    }
    
}
