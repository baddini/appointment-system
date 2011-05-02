<?php
/***************
  appointmentHandler.php
  Author: Alexander Miner
  Project: RDI Project 2
  Group: E Man Maet
    Eric Kisner
	Charles Porter
	Alexander Miner
****************/
	$pageState;
	$controlState;
	
	function init(){
		global $pageState;
		global $controlState;
		
		$pageState = "login";
		$controlState = "login";
	}
	
	//updates the page
	function updatePage(){
		global $pageState;
		global $controlState;
		
		if(isset($_POST['pageState'])){		//if page is receiving a POST submission
			if(($_POST['pageState']) == "login_attempt"){	//if attempting to log in
				//if login fails, display error field, echo error message
				
				//else, set pageState to home page (my appointments view)
			{
			$state = ($_POST['pageState']);	//sets the state to populate the proper display
		}
		if(isset($_POST['controlState'])){		//if page is receiving a POST submission
			$state = ($_POST['controlState']);	//sets the state to populate the proper control
		}
	}
	
	//display proper buttons
	function buildControl(){
		global $controlState;
		
		if($controlState == "login"){
			echo(buildLogin());
		}
	}
	
	//build the login display
	function string buildLogin(){
		$dispBuild = "<table border = 0 cellpadding = 0 cellspacing = 0>";
		$dispBuild = $dispBuild + "<tr><td>Name</td><input type = 'text' name = 'login_name' size = '15' maxlength = '30' value = ''></td></tr>";
		$dispBuild = $dispBuild + "<tr><td>Password</td><input type = 'text' name = 'login_pass' size = '15' maxlength = '30' value = ''></td></tr>";
		$dispBuild = $dispBuild + "</table>";
		$dispBuild = $dispBuild + "<input type = 'hidden' name = 'pageState' value = 'login_attempt' />";
		$dispBuild = $dispBuild + "<input type = 'Submit' name = 'login_submit' value = 'login'>";
		return $dispBuild;
	}
	
	//set up a display test
	function showTest(){
		//if($pageState == ""){
			echo "<p>This is where the display will go...once I have stuff to display.</p>";
		//}
	}
	
	//sets up mail to submit to footprints and sends mail
	function sendMail(){
		if(isset($_POST['submit'])){		//if page is receiving a POST submission
				logWrite();		//logs submission IP address
		}
		//Sets from, to, subject, and body text of email
		$mail = new Zend_Mail();
		$mail->setFrom($purifier->purify($_POST['from']));
		$mail->addTo($purifier->purify($_POST['to']));
		//$mail->addTo("swwdss@rit.edu");
		$mail->setSubject($purifier->purify($_POST['subject']));
		
		$messageText = $purifier->purify($_POST['message']) . "\n";

		//create the body for the mail
			reset($_POST);
						
		/*searches each value of $_POST and finds values that are not required
		and appends them to the body text as a workspace field*/
		while(list($key, $val) = each($_POST))
		{
			if(!(is_array($val)))
				$val = $purifier->purify($val);
			
			//determines if value is a required field or not; removes validate field
			if($key != "to" && $key != "from" && $key != "subject" && $key != "message" && $key != "submit" && $key != "validate")
			{
				//detemines if a field is blank
				if($val != "")
				{
					//determines whether or not the field is an array
					if(is_array($val))
					{
						//enters the array inside of the POST array
						while(list($subKey, $subVal) = each($val))
						{							
							//formats for the first line of multiple data for a workspace field
							if($subKey == 0)
								$messageText = $messageText . "\n" . $key . '=' . $subVal;
							//for each datum after the first
							else
								$messageText = $messageText . "\n" . $subVal;
						}
						//end the workspace field
						$messageText = $messageText . "\nEND " . $key;
					}
					//if the field is not an array
					else
					{
						$messageText = $messageText . "\n" . $key . '=' . $val . "\nEND " . $key;
					}
				}
			}
		}
		
		$mail->setBodyText($messageText);
		
		
		//counts the number of attachments, uploades them and attaches them to the email
		for($i=1;$i<=count($_FILES);$i++){
			$target = "upload/"; 
			$target = $target . basename( $purifier->purify($_FILES['at'.$i]['name'])) ;
			echo $target;
			if(move_uploaded_file($_FILES['at'.$i]['tmp_name'], $target)) { 
				echo "The file ". basename( $_FILES['at'.$i]['name']). " has been uploaded. "; 
				$fileContents = file_get_contents($target);
				$attach = $mail->createAttachment($fileContents);
				$attach->filename = $_FILES['at'.$i]['name'];;
			}
		}
		
		//send the email
		if(!mailSender($mail)){
			alert("An error occured while submitting your request. Please reload the form and try again.");
		}
	}
	
	//logs IP address and date upon submission
	function logWrite(){
		$logFile = "log/log.txt"; //opens the log file
		$fh = fopen($logFile, 'a') or die("Log file not found"); //sets up the file handler to append
		
		if(!empty($_SERVER['HTTP_CLIENT_IP']))   //check ip from share internet
		{
			$ip = $_SERVER['HTTP_CLIENT_IP'];
		}
		elseif (!empty($_SERVER['HTTP_X_FORWARDED_FOR']))   //to check ip is pass from proxy
		{
			$ip = $_SERVER['HTTP_X_FORWARDED_FOR'];
		}
		else
		{
			$ip = $_SERVER['REMOTE_ADDR'];
		}
		
		fwrite($fh, $ip . " - " . strftime('%c') . "\n");	//writes the user's IP address to the log file
		fclose($fh);				//closes the file
	}
?>