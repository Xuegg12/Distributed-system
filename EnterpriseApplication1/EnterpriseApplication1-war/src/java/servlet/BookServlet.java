/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package servlet;  
  
import java.io.IOException;  
import java.io.PrintWriter;  
import javax.servlet.ServletException;  
import javax.servlet.annotation.WebServlet;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import newpackage.NewWebService;  
  
@WebServlet(name = "BookServlet", urlPatterns = {"/book-servlet"})  
public class BookServlet extends HttpServlet {  
  
        /*protected void processRequest(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        response.setContentType("text/html;charset=UTF-8");  
        try (PrintWriter out = response.getWriter()) {  
            // 获取用户输入的书名和作者  
            String bookTitle = request.getParameter("bookTitle");  
            String author = request.getParameter("author");  
  
            // 创建NewWebService实例并调用GetBookByTitleOrAuthor方法  
            NewWebService webService = new NewWebService();  
            String bookInfo = webService.GetBookByTitleOrAuthor(bookTitle, author);  
  
            // 输出书籍信息  
            out.println("<!DOCTYPE html>");  
            out.println("<html>");  
            out.println("<head>");  
            out.println("<title>Book Info</title>");  
            out.println("</head>");  
            out.println("<body>");  
            out.println("<h1>Book Information</h1>");  
            out.println("<p>" + bookInfo + "</p>"); 
            out.println("</body>");  
            out.println("</html>");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  */
        protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {  
        response.setContentType("text/html;charset=UTF-8");  

        // 获取用户输入的书名和作者  
        String bookTitle = request.getParameter("bookTitle");  
        String author = request.getParameter("author");  

        // 创建NewWebService实例并调用GetBookByTitleOrAuthor方法  
        NewWebService webService = new NewWebService();  
        String bookList = webService.GetBookByTitleOrAuthor(bookTitle, author);  

        // 将书籍信息列表存入请求属性  
        request.setAttribute("bookList", bookList);  

        // 转发到JSP页面  
        request.getRequestDispatcher("/SearchResult.jsp").forward(request, response);  
    }
  
  @Override  
    protected void doGet(HttpServletRequest request, HttpServletResponse response)    
            throws ServletException, IOException {    
        // 检查是否已接收到bookTitle或author参数    
        String bookTitle = request.getParameter("bookTitle");    
        String author = request.getParameter("author");    
    
        // 如果没有接收到任何参数，显示输入表单    
        if (bookTitle == null && author == null) {    
            request.getRequestDispatcher("/Searchjsp.jsp").forward(request, response);    
        } else {    
            // 如果已经接收到参数，处理请求并返回结果    
            processRequest(request, response);    
        }    
    }    
     
  
    /*private void showInputForm(HttpServletResponse response) throws IOException {  
        response.setContentType("text/html;charset=UTF-8");  
        try (PrintWriter out = response.getWriter()) {  
            // 输出HTML表单供用户输入书籍ID  
            out.println("<!DOCTYPE html>");  
            out.println("<html>");  
            out.println("<head>");  
            out.println("<h1>Enter Book Title or Author</h1>");  
            out.println("<form action=\"book-servlet\" method=\"get\">");  
            out.println("<label for=\"bookTitle\">Book Title:</label>");  
            out.println("<input type=\"text\" id=\"bookTitle\" name=\"bookTitle\">");
            out.println("<br>"); // 换行  
            out.println("<label for=\"author\">Author:</label>");  
            out.println("<input type=\"text\" id=\"author\" name=\"author\">");  
            out.println("<input type=\"submit\" value=\"Submit\">");  
            out.println("</form>");  
            out.println("</form>");  
            out.println("</body>");  
            out.println("</html>");  
        }  
    }  */
  
    @Override  
    protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        processRequest(request, response);  
    }  
}