 $(document).ready(function() 
{
	if ($("#alertSuccess").text().trim() == "") { 
		$("#alertSuccess").hide(); 
	 } 
	
	 $("#alertError").hide(); 
});

//Save Button
$(document).on("click", "#btnSave", function(event)
{ 
	// Clear alerts---------------------
	$("#alertSuccess").text(""); 
	$("#alertSuccess").hide(); 
	$("#alertError").text(""); 
	$("#alertError").hide();

	// Form validation-------------------
	var status = validateItemForm(); 
	if (status != true) 
	{ 
		 $("#alertError").text(status); 
		 $("#alertError").show(); 
		 
		 return; 
	}
	
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT"; 
	 
	$.ajax( 
	{ 
		 url : "UserAPI", 
		 type : type, 
		 data : $("#formU").serialize(), 
		 dataType : "text", 
		 complete : function(response, status) 
		 { 
		 onItemSaveComplete(response.responseText, status); 
		 } 
	 });

});

//On item save
function onItemSaveComplete(response, status)
{ 
	if (status == "success") 
	{ 
		 var resultSet = JSON.parse(response); 
		 
		 if (resultSet.status.trim() == "success") 
		 { 
			 $("#alertSuccess").text("Successfully saved."); 
			 $("#alertSuccess").show(); 
			 
			 $("#divGrid").html(resultSet.data); 
		 } else if (resultSet.status.trim() == "error") 
		 { 
			 $("#alertError").text(resultSet.data); 
			 $("#alertError").show(); 
		 } 
	 } else if (status == "error") 
	 { 
		 $("#alertError").text("Error while saving."); 
		 $("#alertError").show(); 
	 } else
	 { 
		 $("#alertError").text("Unknown error while saving.."); 
		 $("#alertError").show(); 
	 }
	
	 $("#hidItemIDSave").val(""); 
	 $("#formU")[0].reset(); 
}


//Update Button 
$(document).on("click", ".btnUpdate", function(event)
{ 	
	$("#hidItemIDSave").val($(this).data("itemid")); 
	$("#username").val($(this).closest("tr").find('td:eq(0)').text()); 
	$("#password").val($(this).closest("tr").find('td:eq(1)').text()); 
	$("#phoneNo").val($(this).closest("tr").find('td:eq(2)').text()); 
	$("#email").val($(this).closest("tr").find('td:eq(3)').text()); 
});

//CLIENT-MODEL================================================================
function validateItemForm() 
{ 
	// username
	if ($("#username").val().trim() == "") 
	{ 
		return "Insert Username."; 
	} 
	
	// Password
	if ($("#password").val().trim() == "") 
	{ 
		return "Insert Password."; 
	} 
	
	// phone number 
	if ($("#phoneNo").val().trim() == "") 
	{ 
		return "Insert PhoneNo."; 
	} 
	
	// email
	if ($("#email").val().trim() == "") 
	{ 
		return "Insert Email."; 
	}
	
	return true; 
}

$(document).on("click", ".btnRemove", function(event)
{ 
	$.ajax( 
	{ 
		url : "UserAPI", 
		type : "DELETE", 
		data : "userID=" + $(this).data("itemid"),
		dataType : "text", 
		complete : function(response, status) 
		{ 
		onItemDeleteComplete(response.responseText, status); 
		} 
	}); 
});

function onItemDeleteComplete(response, status)
{ 
	if (status == "success") 
	{ 
		var resultSet = JSON.parse(response);
		
		if (resultSet.status.trim() == "success") 
		{ 
			$("#alertSuccess").text("Successfully deleted."); 
			$("#alertSuccess").show(); 
			$("#divGrid").html(resultSet.data); 
		} else if (resultSet.status.trim() == "error") 
		{ 
			$("#alertError").text(resultSet.data); 
			$("#alertError").show(); 
		} 
	} else if (status == "error") 
	{ 
		$("#alertError").text("Error while deleting."); 
		$("#alertError").show(); 
	} else
	{ 
		$("#alertError").text("Unknown error while deleting.."); 
		$("#alertError").show(); 
	} 
}