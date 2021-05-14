package com;

import java.sql.*;

public class User {
	//A common method to connect to the DB
	private Connection connect()
	{
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			//Provide the correct details: DBServer/DBName, user name, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", "1234");
			
			//for testing
			System.out.println("Successfully Connected");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not Connected");
		}
		return con;
	}

	public String insertUser(String name, String password, String phone, String email) {
		String output = "";
		try
		{
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting."; 
			}
			
			// create a prepared statement
			String query = " INSERT INTO `users`(`userID`, `username`, `password`, `phoneNo`, `email`) values (?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, name);
			preparedStmt.setString(3, password);
			preparedStmt.setString(4, phone);
			preparedStmt.setString(5, email);
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newUser = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}"; 
		
		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting.\"}"; 
			System.err.println(e.getMessage());
		}
		
		return output;
	}

	public String readUsers() {
		String output = "";
		
		try	{
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for reading."; 
			}
			
			// Prepare the html table to be displayed
			output = "<table class='table table-striped'><tr><th>Username</th><th>Password</th>" +
			"<th>Phone Number</th>" +
			"<th>Email</th>" +
			"<th>Update</th><th>Remove</th></tr>";
			
			String query = "select * from users";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String userID = Integer.toString(rs.getInt("userID"));
				String username = rs.getString("username");
				String password = rs.getString("password");
				String phoneNo = rs.getString("phoneNo");
				String email = rs.getString("email");
				
				// Add into the html table
				output += "<tr><td><input id='hidItemIDUpdate' name='hidItemIDUpdate' type='hidden' value='" + userID
						+ "'>" + username + "</td>";
				output += "<td>" + password + "</td>";
				output += "<td>" + phoneNo + "</td>";
				output += "<td>" + email + "</td>";
				
				// buttons
				output += "<td> <input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-success' data-itemid='" + userID + "''></td>"
						+ "<td> <input name='btnRemove' type='button' value='Remove' class='btnRemove btn btn-danger' data-itemid='" + userID + "''> "
						+ "<input name='hidItemIDDelete' type='hidden' value='" + userID + "'>"
						+ "</td></tr>";				}
			
				con.close();
				
				// Complete the html table
				output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the users.";
			System.err.println(e.getMessage());
		}
		return output;
	}

public String updateUser(String ID, String name, String password, String phone, String email) {
	String output = "";
	
	try {
		Connection con = connect();
		
		if (con == null) {
			return "Error while connecting to the database for updating."; 
		}
		
		// create a prepared statement
		String query = "UPDATE users SET username=?,password=?,phoneNo=?,email=?WHERE userID=?";
		PreparedStatement preparedStmt = con.prepareStatement(query);
		
		// binding values
		preparedStmt.setString(1, name);
		preparedStmt.setString(2, password);
		preparedStmt.setString(3, phone);
		preparedStmt.setString(4, email);
		preparedStmt.setInt(5, Integer.parseInt(ID));
		
		// execute the statement
		preparedStmt.execute();
		con.close();
		
		String newUser = readUsers();
		output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}"; 
	} catch (Exception e) {
		output =  "{\"status\":\"error\", \"data\": \"Error while updating.\"}";
		System.err.println(e.getMessage());
	}
	
	return output;
}

	public String deleteUser(String userID) {
		String output = "";
		try {
			Connection con = connect();
			
			if (con == null) {
				return "Error while connecting to the database for deleting."; 
			}
			
			// create a prepared statement
			String query = "delete from users where userID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(userID));
			
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newUser = readUsers();
			output = "{\"status\":\"success\", \"data\": \"" + newUser + "\"}";
		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while deleting.\"}";
			System.err.println(e.getMessage());
		}
		
		return output;
	} 
}
