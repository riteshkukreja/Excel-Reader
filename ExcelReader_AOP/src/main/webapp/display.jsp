<%-- 
    Document   : display
    Created on : Jun 10, 2017, 9:49:29 PM
    Author     : Ritesh
--%>

<%@page import="java.util.List"%>
<%@page import="Model.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="style/display.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
        
        <% List<User> users = (List<User>)request.getAttribute("users"); %>
        <% 
            if(users == null) {
                out.println("<h1>Error in Uploading</h1>");
                out.println("<h2>" + request.getAttribute("error") + "</h2>");
            } else {
        %>
            <h1 class="blue">Uploaded Users</h1>
            <table class="container">
                <tr>
                    <th>ID</th>
                    <th>USERNAME</th>
                    <th>FIRST NAME</th>
                    <th>LAST NAME</th>
                    <th>ADDRESS</th>
                    <th>AGE</th>
                    <th>PASSING YEAR</th>
                </tr>

                <% for (int i = 0; i < users.size(); i++) { %>

                <tr>
                    <td>
                        <%= (int) users.get(i).getId() %>
                    </td>
                    <td>
                        <%= users.get(i).getUsername() %>
                    </td>
                    <td>
                        <%= users.get(i).getFirst_name() %>
                    </td>
                    <td>
                        <%= users.get(i).getLast_name() %>
                    </td>
                    <td>
                        <%= users.get(i).getAddress() %>
                    </td>
                    <td>
                        <%= (int) users.get(i).getAge() %>
                    </td>
                    <td>
                        <%= (int) users.get(i).getPassing_year() %>
                    </td>
                </tr>

                <% } %>
            </table>
        <% } %>
    </body>
</html>
