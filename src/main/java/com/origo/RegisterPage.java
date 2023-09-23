package com.origo;
import jakarta.servlet.RequestDispatcher;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegisterPage extends HttpServlet {
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
	public static boolean isValidPassword(String password,String re_password) {
		boolean upperCaseCount=false;
		boolean numericCount=false;
		boolean specialCharacterCount=false;
		if(re_password.equals(password)) {
		 for(char c: password.toCharArray()) {
			if(Character.isUpperCase(c))
				upperCaseCount=true;
			else if(Character.isDigit(c))
				numericCount=true;
			else if("@#$%^&*+=!".indexOf(c)!=-1)
				specialCharacterCount=true;
		 }
		}
		return upperCaseCount && numericCount && specialCharacterCount;
	    
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String username=request.getParameter("username");
		String password=request.getParameter("password");
		String re_password=request.getParameter("re-password");
		out.println("<html><body>");
		if(username.contains(" ")){
			String error_mes="Invalid Username Format Please create valid credentials";
			request.setAttribute("error_mes",error_mes);
			if(password.length()<7) {
				String error_message="Password must be at least greater than 7";
				request.setAttribute("error_message", error_message);
			}
			request.getRequestDispatcher("Register.jsp").forward(request, response);
		}
		if(password.length()<7) {
			 // Password is less than 7 characters, set an error message attribute
			String error_message="Password must be at least greater than 7";
			request.setAttribute("error_message", error_message);
			request.getRequestDispatcher("Register.jsp").forward(request, response);
		}
		else {
			if(isValidPassword(password,re_password)==true) {
		      try {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "tiger");
	            PreparedStatement ps = con.prepareStatement("insert into login values (?,?)");
	            ps.setString(1, username);
	            String enpassword=encryptString(password) ;
	            ps.setString(2, enpassword);
	            ps.executeUpdate();
	            
	            request.getRequestDispatcher("submit.jsp").forward(request, response);
	            }
	        catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        catch(SQLException e) {
	        	e.printStackTrace();
	        }
	      }
			else {
				String error_message="Please check your password it must contains 1 caps, 1 special and 1 Numeric letter or check both password you entered same or not";
				request.setAttribute("error_message", error_message);
				request.getRequestDispatcher("Register.jsp").forward(request, response);
			}
		}
		out.println("</body></html>");
	}
}


