
<%@ page import="java.sql.*, javax.sql.*, java.io.*, javax.naming.*" %>
<html>
<head><title>Gelen Proje Mailleri</title></head>
<body>
<%
  InitialContext ctx;
  DataSource ds;
  Connection conn;
  Statement stmt;
  ResultSet rs;

  try {
	  Class.forName("com.mysql.jdbc.Driver");
	  java.sql.Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mail_db","root",""); 
	  //ctx = new InitialContext();
    //ds = (DataSource) ctx.lookup("java:comp/env/jdbc/MySQLDataSource");
    //ds = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
    //conn = ds.getConnection();
    stmt = con.createStatement();
    rs = stmt.executeQuery("SELECT * FROM mails");
%>
<table border="1" width="100%">
<tr>
   <th>ID</th>
   <th>Grup No</th>
   <th>Numara</th>
   <th>Ad-Soyad</th>
</tr>
<% 
    while(rs.next()) {
%>
    <tr>
    	<td><%= rs.getInt("id") %></td>
     	<td> <%= rs.getInt("group_no") %></td>
    	<td><%= rs.getString("no") %></td>
    	<td><%= rs.getString("name") %></td>
    </tr>
<%    
    }
  }
  catch (SQLException se) {
%>
    <%= se.getMessage() %>
<%      
  }
  catch (NamingException ne) {
%>  
    <%= ne.getMessage() %>
<%
  }
%>
</body>
</html>