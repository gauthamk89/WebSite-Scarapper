<%-- 
    Document   : result
    Created on : Jan 31, 2014, 9:39:02 PM
    Author     : Gautham
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.2//EN" "http://www.openmobilealliance.org/tech/DTD/xhtml-mobile12.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
       <h2><%= request.getAttribute("heading")%></h2><br>
       <img src=<%= request.getAttribute("pictureTag")%>><br><br>
       <form action="getAnInterestingPicture" method="GET">
        <label for="l1">Enter the Artist Last name</label>
        <p><input type="text" name="searchWord" value="" /><br></p>
        <input type="submit" value="Click Here" />
        </form>
    </body>
</html>
