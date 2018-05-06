$(document).ready(function() {
	
	ajaxGet();
	
	// DO GET
	function ajaxGet(){
		$.ajax({
			type : "GET",
			url : window.location + "/api/stadiums/stadiums",
			success: function(result){
				$.each(result, function(i, stadiums){
					
					var stadiumsRow = '<tr>' +
										'<td>' + customer.name + '</td>' +
										'<td>' + customer.lng + '</td>' +
										'<td>' + customer.city + '</td>' +
									  '</tr>';
					$('#customerTable tbody').append(stadiumsRow);
					
		        });
				
				$( "#customerTable tbody tr:odd" ).addClass("info");
				$( "#customerTable tbody tr:even" ).addClass("success");
			},
			error : function(e) {
				alert("ERROR: ", e);
				console.log("ERROR: ", e);
			}
		});	
	}
})