package com.origo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static String encryptString(String str) {
        char c[] =str.toCharArray();
        String encryptedpassword="";
       for(char c1 : c){
           c1 = (char) (c1 + 2);
           encryptedpassword=encryptedpassword+c1;
       }
       return encryptedpassword;
    }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		password=encryptString(password);
		
		out.println("<html><body>");
		if(username.contains(" ")) {
			String error_mes="Invalid Username Format Please check the credentials";
			request.setAttribute("error_mes",error_mes);
			if(password.length()<7) {
				String error_message="Password must be at least greater than 7";
				request.setAttribute("error_message", error_message);
			}
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		if(password.length()<7){
			String error_message="Password must be at least greater than 7";
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		
		else {
		     try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "tiger");
	            PreparedStatement ps = con.prepareStatement("select * from login where username=? and password=?");
	            ps.setString(1, username);
	            ps.setString(2, password);
	            
	            
	            ResultSet rs=ps.executeQuery();
	            if(rs.next()) {
	            	HttpSession session = request.getSession();
	                session.setAttribute("username", username);
	            	RequestDispatcher rd=request.getRequestDispatcher("Login.jsp");
	            	rd.forward(request, response);
	               
	            }
	            else {
	            	String error_message="Please enter valid password";
	            	request.setAttribute("error_message", error_message);
	            	request.getRequestDispatcher("index.jsp").forward(request, response);
	            }
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        catch(SQLException e) {
	        	e.printStackTrace();
	        }
		
	      
  }
		out.println("</body></html>");
}
}