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
        <title>Home Page</title>
    </head>
<div class="page-header">
            <nav id="links"> 
                <ul>
                    <li> <a href="#"> Home </a> </li>
                    <li> <a href="NewFile.jsp"> My Preferred Shops </a> </li>
                    
                </ul>
                
            </nav>
            </div>
            <div id="user_info"> Hi  <%= (session.getAttribute("name"))%> <span style="color:green">   <i> <!--%= name %-->! </i>  </span> </div>
<form name="logout" action="Controller" method="Post">

                
                 <input class="form-group btn btn-default pull-right" type="submit" name="signout" value=" Logout "> 
             </form>  
<body>
<h1>Nearby Shops</h1>
  
 <STYLE>
.newColor {color:red}
</STYLE>
<p><%JSONArray jsonarray;
          JSONObject jsonobject;
          JSONObject jsonobject1;
          
          
          String APIkey="AIzaSyC0ZeqqopWFQu5bx0mWHRNbnuzGogJ_ICM";// Please get an API key in google place API
          
          jsonobject1 = JSONfunctions.getJSONfromURL("http://ip-api.com/json");
          double lat= jsonobject1.getDouble("lat");
          double lon= jsonobject1.getDouble("lon");
          jsonobject = JSONfunctions.getJSONfromURL("https://maps.googleapis.com/maps/api/place/textsearch/json?query="+URLEncoder.encode("shops")+"&location="+URLEncoder.encode(""+lat+","+lon+"")+"&radius="+URLEncoder.encode("1000")+"&key="+URLEncoder.encode(APIkey));
          ArrayList<String> arraylist1 = new ArrayList<String>();
          int i=0;
          %>
        </p>
        <fieldset>

        <table border="1"> 
         <%  
          

         try {%>
				 <%jsonarray = jsonobject.getJSONArray("results"); %>

				<% while( i < jsonarray.length()) {%>
					<% if(i%4==0){%><tr> </tr><%} %>
					<% jsonobject = jsonarray.getJSONObject(i);
					arraylist1.add(i, jsonobject.getString("name"));
					
					String a=arraylist1.get(i);
					
					i++;
					%>
					<td>
			        <div style=""><% out.print(jsonobject.getString("name"));%><img border="1"
			            src="https://png.icons8.com/cotton/1600/online-shop.png" height="160" width="160" />
			            <form action="Controller" method="post">
    
    <button type="submit" name="button"  value="<%=a%>"  >Like</button>
    
    <button type="submit" name="button1" value="<%=a%>">Dislike</button>
   
</form></div>
			            
     

			        </td>
				<% }%>
			<% }%><%  catch (JSONException e) {%>
				
			                         
   
         <%} %>
           
           </table>
      
           </fieldset> 


</body>

</html>