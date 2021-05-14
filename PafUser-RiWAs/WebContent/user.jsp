<%@page import="com.User"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link href="Views/bootstrap.min.css" rel="stylesheet"> 
<script src="Components/jquery-3.6.0.min.js"> </script>
<script src="Components/user.js" type="text/javascript"></script>

<title>User</title>
</head>
<body>
<div class="container" >
		<div class="row" >
			<div class="col">
				<h1> Users</h1>
				<form id="formU" name="formU">
					UserName <input id="username" name="username" type="text" class="form-control">
							 		
					Password <input id="password" name="password" type="password" class="form-control"> 
			
					Phone Number <input id="phoneNo" name="phoneNo" type="text" class="form-control">
				
					Email <input id="email" name="email" type="email" class="form-control">
					
					<br>
					
					<div class="d-grid clo-6 gap-2">
  						<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary btn-lg btn-block">
					</div>
					
					<input type="hidden" id="hidItemIDSave" name="hidItemIDSave" value="">
				</form>
				
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div  id="divGrid">
						<%
							User itemObj = new User();
							out.print(itemObj.readUsers());
						%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>