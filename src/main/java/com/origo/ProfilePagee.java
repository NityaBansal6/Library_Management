package com.origo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ProfilePagee extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private String getSessionUsername(HttpServletRequest request) {
	        HttpSession session = request.getSession();
	        return (String) session.getAttribute("username");
	    }
	 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		String name=request.getParameter("name");
		String gender=request.getParameter("gender");
		String hobbies=request.getParameter("hobbies");
		
		HttpSession session = request.getSession();
		String action = (String) session.getAttribute("action");
		session.setAttribute("action", action);
		System.out.println(action);
		out.println("<html><body>");
		try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "tiger");
            PreparedStatement ps = null;
            String username = getSessionUsername(request);
            if("Add".equals(action)) {
                ps = con.prepareStatement("insert into profile (username , name , gender , hobbies) values (? , ? , ? , ?)");
                ps.setString(1, username);
                ps.setString(2, name);
                ps.setString(3, gender);
                ps.setString(4, hobbies);
            }else if("Edit".equals(action)){
            	 ps = con.prepareStatement("update profile set name=?, gender=?, hobbies=? where username=?");
            	 ps.setString(1, name);
                 ps.setString(2, gender);
                 ps.setString(3, hobbies);
                 ps.setString(4, username);
            }
            if(ps != null) {
            ps.executeUpdate();
            request.getSession().setAttribute("action", action);
            request.getRequestDispatcher("Login.jsp").forward(request, response);
            }
		}
        catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(SQLException e) {
        	e.printStackTrace();
        }
		out.println("</body></html>");
      }
		
}
