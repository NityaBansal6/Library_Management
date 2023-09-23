<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Edited ProfilePage</title>
<link rel="stylesheet" type="text/css" href="exstyle/Profile.css" />
</head>
<body>
<% String username = (String) session.getAttribute("username"); %>
<% String name = (String) session.getAttribute("name"); %>
<% String gender = (String) session.getAttribute("gender"); %>
<% String hobbies = (String) session.getAttribute("hobbies"); %>
<div>
<header><h1 align="center">My Profile</h1></header>
<div class="div1">
<form action="Profile.jsp" method="get">
  <label for="Name">Name:</label>
  <input type="text" name="name" value="<%= name %>" readonly><br><br>
  <label for="Gender">Gender:</label>
  <input type=text name="gender" value="<%= gender %>" readonly><br><br>
  <label for="Hobbies">Hobbies:</label>
  <input type="text" name="hobbies" value="<%= hobbies %>" readonly><br><br>
  <div class=button>
  <input type="submit" name="action" value="Edit"> 
  <input type="submit" name="action" value="Add">
  </div>
</form>
 </div>
 
</div>
</body>
</html>