package com.util;
//import com.util.validate;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Scanner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpSession;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {
	static String query;
	static String query1;
	static PreparedStatement ps;

    SessionBean bean = new SessionBean();

   
    static Connection con;
    
    HashMap authentication_status_map = new HashMap();
   

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        response.setContentType("text/html");
        try {
            out.println("<h1>Servlet Controller at " + request.getContextPath() + "</h1>");
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String login_name;
        String login_password;
        String registration_username;
        String registration_password;
     //   String registration_emailaddress;
        boolean result;
        String username_exists;
        RequestDispatcher rd;
        HttpSession session = request.getSession(true);
        session.setAttribute("Order_Confirmation", null);

        // Case for logout!

        if (request.getParameter("signout") != null) {
            System.out.println("Reached log out in the controller!!");
            //Query.clearSession();            
            session.setAttribute("authentication", null);
            session.removeAttribute("authentication"); //clearing the authentication
            session.setAttribute("name", null); // clearing the session name
            
            rd = request.getRequestDispatcher("/index.jsp");
            rd.forward(request, response);
        }

        //Case for Login Submit

        if (request.getParameter("login_submit") != null) {
            login_name = request.getParameter("login_name");
            System.out.println("login_name before trimming = " + login_name);
            System.out.println("login_name after trimming = " + login_name.trim()); // what it does is, if the username is
            login_password = request.getParameter("login_password");                  // "  my_name" it reads the username as  
            System.out.println("login_password = " + login_password.trim());        // "my_name"
            username_exists = new register().if_username_exists(login_name.trim());
            if (username_exists.equals("false")) {
                session.setAttribute("authentication", "Auth_Failure");
                rd = request.getRequestDispatcher("/index.jsp");
                rd.forward(request, response);
            }
            if (username_exists.equals("true")) {
                authentication_status_map = new authentication().authenticate(login_name.trim(), login_password.trim()); //check for admin here. redirect 2 admin page..
                String authentication_status = (String) authentication_status_map.get("authentication_status");
                String IsAdmin = (String) authentication_status_map.get("IsAdmin");
                System.out.println("Authentication status = " + authentication_status + " user = " + IsAdmin);
                if (authentication_status.equals("true")) {
                    bean.setName(login_name);
                    request.setAttribute("name", login_name);
                    session.setAttribute("name", login_name);
                    System.out.println("Authentication Succesfull. User granted permission!!");
                    
                        session.setAttribute("authentication", "Auth_Success");
                        rd = request.getRequestDispatcher("/user_shop.jsp");
                        rd.forward(request, response);
                   

                } else {
                    System.out.println("Username or Password does not exists!!");
                    session.setAttribute("authentication", "Auth_Failure");
                    rd = request.getRequestDispatcher("/index.jsp");
                    rd.forward(request, response);
                }
            }
        }

        //Case for registration submit  

        if (request.getParameter("registration_submit") != null) {
            registration_username = request.getParameter("registration_username");
            registration_password = request.getParameter("registration_password");
            System.out.println("registration_username before trimming = " + registration_username);
            System.out.println("registration username after trimming = " + registration_username.trim());
            System.out.println("registration_password = " + registration_password.trim());
            //Below code checks if the username or password is Null.
            //The following code is not necessary becauase, i have used JavaScript for Form Validations.
            // Just kept this code as backup and understanding purposes!
            if (registration_password.trim().equals("") || registration_username.trim().equals("")) {
                System.out.println("password equal");
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                try {
                    out.println("<h1> Username or Password cannot be Null </h1>");
                } finally {
                    out.close();
                }
            }
            username_exists = new register().if_username_exists(registration_username.trim());
            System.out.println("username_exists true or false= " + username_exists);
            response.setContentType("text/html"); // need to change it later to reduce the codes that follow it..
            if (username_exists.equals("true")) {
                PrintWriter out = response.getWriter();
                try {
                    out.println("<h1> Username Taken </h1>");
                } finally {
                    out.close();
                }
            } else {
                result = new register().register_user(registration_username.trim(), registration_password.trim());
                if (result == true) {
                    PrintWriter out = response.getWriter();
                    try {
                    	RequestDispatcher dispatcher = request.getRequestDispatcher("registersuccess.jsp");
                    	dispatcher.forward(request, response);
                        //out.println("<h1> Registration Successful </h1>");
                    } finally {
                        out.close();
                    }
                } else {
                    PrintWriter out = response.getWriter();
                    try {
                        out.println("<h1> Registration Failure </h1>");
                    } finally {
                        out.close();
                    }
                }
            }
        } // registration_submit ends here..

        
        if (request.getParameter("button") != null) {
            try {
            	
            	con = DAOConnection.sqlconnection();
                String button = request.getParameter("button");
                String user =  (String) session.getAttribute("name");
                Statement stmt= con.createStatement();
                System.out.print(user);
                query1 = " SELECT name FROM shop WHERE  name= '"+user+"'AND shopname= '"+button+"'";
                
                    query = " insert into shop(name,shopname) values ('"+user+"','"+button+"')";
                
                 ResultSet rs= stmt.executeQuery(query1);
                 
                 if(rs.next()){System.out.print("error");
                 rd = request.getRequestDispatcher("/user_shop.jsp");
                 rd.forward(request, response);}
                 else{
                ps = con.prepareStatement(query);
                ps.executeUpdate();
                
                rd = request.getRequestDispatcher("/user_shop.jsp");
                rd.forward(request, response);}
            } catch (SQLException ex) {
                
            } 
    }
        if (request.getParameter("button1") != null) {
            try {
                con = DAOConnection.sqlconnection();
                Statement stmt = con.createStatement();
                String button = request.getParameter("button1");
                String user =  (String) session.getAttribute("name");
                System.out.print(user);
                    query = " DELETE FROM shop WHERE name = '"+user+"' AND shopname = '" + button + "'";
                
               

                    stmt.executeUpdate(query);
                    
                rd = request.getRequestDispatcher("/user_shop.jsp");
                rd.forward(request, response);
            } catch (SQLException ex) {
                
            } 
        }
        if (request.getParameter("button2") != null) {
            try {
                con = DAOConnection.sqlconnection();
                Statement stmt = con.createStatement();
                String button = request.getParameter("button2");
                String user =  (String) session.getAttribute("name");
                System.out.print(user);
                    query = " DELETE FROM shop WHERE name = '"+user+"' AND shopname = '" + button + "'";
                
               

                    stmt.executeUpdate(query);
                    
                rd = request.getRequestDispatcher("/NewFile.jsp");
                rd.forward(request, response);
            } catch (SQLException ex) {
                
            } 
        }
     



    }// post method ends here
}//servlet class ends here
