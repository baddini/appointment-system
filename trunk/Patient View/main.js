	/*lab = "";					//holds name of the selected lab
	filler = "";				//holds content to add to computer names select box
	labName = new Array();		//holds all lab names
	myLabs = new Array();		//holds all computer names*/

	function initialize(){
	
	}
	
	//gets the JSON data after the page opens and populates the lab select box
	/*function initialize(){
		$.getJSON("../config.json", function(json){
			//inside of the json file
			for (i = 0, i_max = json.labs.length; i < i_max ; i++){
				//inside of the labs array
				labName.push(json.labs[i].id);		//populates labName with each lab
				myLabs[i] = new Array(json.labs[i].stations.length);
				
				for(j = 0, j_max = json.labs[i].stations.length; j < j_max; j++){
					//inside of the stations array
					myLabs[i][j] = json.labs[i].stations[j].id;		//populates myLabs with each computer
				}
			}
			getLabs();
		});
	}
	
	//launches after the DOM is populated
	$(document).ready(function() {
		$("#jsPrompt").toggle(false);		//removes jsPrompt div if javascript is enabled
	
		//when the select box is changed
		$("#location").change(function () {
			//gets the name of the selected lab
			$("#location option:selected").each(function () {
				lab = $(this).text();
			});
			
			//if a lab is selected
			if(lab != "Select a Location"){
					var labIdNum;		//gets the index of the selected lab
					
					$("#computerName").html(" ");
					$("#computerName").append("<option>Select a Computer</option><option>ALL</option><option>OTHER</option>");
					$("#greeting").text("Submit an HRepair for " + lab);
					
					//finds the index for the selected lab in the computers array
					for(i = 0; i < labName.length; i++){
						if(labName[i] == lab){
				 			labIdNum = i;
						}
					}
					
					//populates the computer name select box with the selected lab's computers
					for(i = 0; i < myLabs[labIdNum].length; i++){
						$("#computerName").append("<option>" + myLabs[labIdNum][i] + "</option>");
					}
					
					$("#hForm").toggle(true);	//displays the form
			}
			else{
				$("#hForm").toggle(false);		//remove the form if a lab is not selected
				$("#greeting").text("Select a location to begin");
			}
		});
	});
	
	//populates the labs select box
	function getLabs(){
		for(i = 0; i < labName.length; i++){
			filler += '<option value = "' + labName[i] + '">' + labName[i] + '</option>';
		}
		$("#location").html('<option value=""selected>Select a Location</option>' + filler);
	}*/