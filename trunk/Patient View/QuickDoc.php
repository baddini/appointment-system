<html>
<!--
QuickDoc.php
  Author: Alexander Miner
  Project: RDI Project 2
  Group: E Man Maet
    Eric Kisner
	Charles Porter
	Alexander Miner
-->
<head>
	<title>Quick Doc - My View</title>
	<meta http-equiv="Content-Type" content="text/html;" />
	<link rel = "StyleSheet" href = "master.css">
	<script type = "text/javascript" src = "http://code.jquery.com/jquery-latest.js"></script>
	<script type = "text/javascript" src = "validation.js"></script>
	<!--script type = "text/javascript" src = "main.js"></script-->
	<script type="text/javascript">
	//<![CDATA[
		//window.onload = initialize;

		//add fields required to process the form here
		//requiredFields = [""];
	//]]>
	</script>
</head>
<body>
	<?php
		include_once('appointmentHandler.php');
		init();
		updatePage();
	?>
	<div id = "main">
		<?php
			showTest();
		?>
	
		<form name = "control" method = "post" action="QuickDoc.php" onsubmit="return validateForm(this)">
			<?php
				buildControl();
			?>
			<!--input type = "text" name = "validate" id = "validate" size = "15" value = "" style = "display:none;"-->
		</form>
		<div id = "errorMessage">
			<p class = "errorMessage"></p>
		</div>
	</div>
		

		
</body>
</html>
