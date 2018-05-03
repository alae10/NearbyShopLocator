<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*"%>
    <%@ page import="org.json.JSONObject"%>
    <%@ page import="org.json.JSONArray"%>
    <%@ page import="org.json.JSONException"%>
    <%@ page import="com.util.JSONfunctions"%>
    <%@ page import="java.net.URLEncoder"%>
    <%@ page import= "java.util.ArrayList"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="mycss.css">
         <link rel="stylesheet" href="css/bootstrap.min.css">
        <link rel="stylesheet" href="css/dbStyle.css">
        <link href="font-awesome-4.4.0/css/font-awesome.css" rel="stylesheet">
        <title>Preferred Shops</title>
    </head>
    <div class="page-header">
            <nav id="links"> 
                <ul>
                    <li> <a href="#"> Home </a> </li>
                    <li> <a href="user_shop.jsp"> Nearby Shops </a> </li>
                    
                </ul>
                
            </nav>
            <div id="user_info"> Hi  <%= (session.getAttribute("name"))%> <span style="color:green">   <i> <!--%= name %-->! </i>  </span> </div>
            </div>
<form name="logout" action="Controller" method="Post">

                
                 <input class="form-group btn btn-default pull-right" type="submit" name="signout" value=" Logout "> 
             </form>  

<body>
<h1>My Preferred Shops</h1>
  
 
<p><%
          int i=0;
          %>
        </p>
        <fieldset>

        <table border="1"> 
         <%
   try
   {%>
       <% Class.forName("com.mysql.jdbc.Driver");
       String url="jdbc:mysql://localhost:3306/india_db";
       String username="root";
       String password="";
       String query="select * from shop where name='"+session.getAttribute("name")+"' ";
       Connection conn=DriverManager.getConnection(url, username, password);
       Statement stmt=conn.createStatement();
       ResultSet rs=stmt.executeQuery(query);%>
     <%  while(rs.next())
       {
   %><% if(i%4==0){%><tr> </tr><%} %>
					<% 
					%><% i++;
					String a=rs.getString("shopname");
					%>
					<td>
			        <div style=""><% out.println(rs.getString("shopname"));%><img border="1"
			            src="https://png.icons8.com/cotton/1600/online-shop.png" height="160" width="160" />
			            <form action="Controller" method="post">
    
    <button type="submit" name="button2"  value="<%=a%>"  >Remove</button>
			      </form>      </div>
           </td>
          
           <%
       }
   %>
   </table>
   <%
        rs.close();
        stmt.close();
        conn.close();
   }
   catch(Exception e)
   {
        e.printStackTrace();
   }
   %>
           </fieldset> 
 
 
</body>
</html>