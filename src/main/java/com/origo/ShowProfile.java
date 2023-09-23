package com.origo;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ShowProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 String name="-", gender="-", hobbies="-";
	 private String getSessionUsername(HttpServletRequest request) {
	        HttpSession session = request.getSession();
	        return (String) session.getAttribute("username");
	    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out=response.getWriter();
		out.println("<html><body>");
		 String username =getSessionUsername(request);
		 ResultSet rs=null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_management", "root", "tiger");
            PreparedStatement ps = con.prepareStatement("select name, gender, hobbies from profile where username=?");
            ps.setString(1, username);
           
            rs=ps.executeQuery();
            if(rs.next()) {
            	name=rs.getString("name");
                gender=rs.getString("gender");
                hobbies=rs.getString("hobbies");
            }
            	HttpSession session = request.getSession();
            	session.setAttribute("name", name);
            	session.setAttribute("gender", gender);
            	session.setAttribute("hobbies", hobbies);
            	request.getRequestDispatcher("EditProfile.jsp").forward(request, response);
            
		}catch(Exception e) {
			
		}
		out.println("</body></html>");
	}

}
