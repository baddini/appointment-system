function validateForm(thisform){
	var fieldsEmpty = "";		//holds error message output
	fieldNum = 0;				//holds index of required item in elements array
	
	//for each required field
	for(i = 0; i < requiredFields.length; i++){
		//finds the index of the current required field in the elements array
		for(j = 0; j < thisform.elements.length; j++){
			if(thisform.elements[j].name == requiredFields[i]){
				fieldNum = j;
			}
		}

		//determines if the field is valid
		if(thisform.elements[fieldNum].value == "" || thisform.elements[fieldNum].value == null){
			fieldsEmpty += "The '" + requiredFields[i] + "' Field is empty. ";
		}
	}
	
	//validates if email is valid
	var emailPattern = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
	if(!emailPattern.test(document.getElementById("from").value)){
		fieldsEmpty += "Please enter a valid email address. ";
	}
	
	if(document.getElementById("validate").value != ""){
		fieldsEmpty = "There was an error processing your request, please refresh the page and try again.";
	}
	
	if(fieldsEmpty == ""){
		return true;
	}
	
	document.getElementById('errorMessage').innerHTML = fieldsEmpty;
	return false;
}