// JavaScript Document


   /*全选*/
		function check_all(){
		    var oInput = document.getElementsByName("account");
			var checkAll = document.getElementById("all");
			for(var i=0;i<oInput.length;i++){
			   if(checkAll.checked == true){
			      oInput[i].checked = true;
			   }else{
				  oInput[i].checked = false;   
			   }	
			}	
	    }
		
		function check_all2(){
		    var oInput2 = document.getElementsByName("account2");
			var checkAll2 = document.getElementById("all2");
			for(var i=0;i<oInput2.length;i++){
			   if(checkAll2.checked == true){
			      oInput2[i].checked = true;
			   }else{
				  oInput2[i].checked = false;   
			   }	
			}	
	    }