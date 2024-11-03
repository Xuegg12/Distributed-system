/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;  
  
import dto.Book;
import java.io.IOException;  
import java.io.PrintWriter;  
import java.util.List;
import javax.servlet.ServletException;  
import javax.servlet.annotation.WebServlet;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import dto.BookDAO;
import dto.User;
import dto.UserDAO;
import newpackage.NewWebService;  
  
@WebServlet(name = "BookServlet", urlPatterns = {"/book-servlet"})  
public class BookServlet extends HttpServlet {

   @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {   
        
        String action = request.getParameter("action");
        String bookInfo = request.getParameter("bookInfo");
        BookDAO bookDAO = new BookDAO();

        if ("view".equals(action)) {
            // 获取书籍ID
            String bookId = request.getParameter("id");
            if (bookId != null && !bookId.isEmpty()) {
                try {
                    int id = Integer.parseInt(bookId);
                    // 根据ID查询书籍详细信息
                    Book book = bookDAO.getBookById(id);
                    request.setAttribute("book", book);
                    // 跳转到书籍详细信息页面
                    request.getRequestDispatcher("/bookDetail.jsp").forward(request, response);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } else if (bookInfo != null && !bookInfo.trim().isEmpty()) {
            // 如果接收到用户查询输入参数，处理请求并返回结果 
            processRequest(request, response);
        } else {
            // 显示所有书籍信息
            List<Book> books = bookDAO.getAllBooks();
            request.setAttribute("books", books);
            request.getRequestDispatcher("/books.jsp").forward(request, response); 
        }  
    }
     
  
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        
        String action = request.getParameter("action");
        int bookId = Integer.parseInt(request.getParameter("bookId"));
        
        // userId不完全，需要和登陆注册联动，这里先随便设个1
        int userId = 1;
        User user;
        
        BookDAO bookDAO = new BookDAO();
        UserDAO userDAO = new UserDAO();
        
        if("buy".equals(action)){
            boolean success = userDAO.purchaseBook(userId, bookId);
            if(success){
                // 更新用户信息
                user = userDAO.getUserById(userId);
            }
        }else if("borrow".equals(action)){
            boolean success = userDAO.borrowBook(userId, bookId);
            if(success){
                // 更新用户信息
                user = userDAO.getUserById(userId);
            }
        }
        
        // 跳转到用户界面未实现
        response.sendRedirect("user-servlet?action=viewProfile");
    }
    
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html;charset=UTF-8");  

        // 获取用户输入的书籍信息  
        String bookInfo = request.getParameter("bookInfo");
        
        // 创建 BookDAO 实例并调用GetBookByTitleOrAuthor方法  
        BookDAO bookDAO = new BookDAO();  
        List<Book> searchedBook = bookDAO.searchBooksByTitleOrAuthor(bookInfo);
        
        // 将查询到的书籍信息列表存入请求属性  
        request.setAttribute("books", searchedBook);  
        
        // 转发到JSP页面  
        request.getRequestDispatcher("/books.jsp").forward(request, response);  
    }
}