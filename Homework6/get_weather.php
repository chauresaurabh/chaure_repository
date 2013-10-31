
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"><head>
<meta http-equiv="Content-Type" content="text/html; " />
<title>Homework 6</title>
<script type="text/javascript">
  
function validateForm(){
			
	var e = document.getElementById("locationText");
	var location = e.value;
	location = location.trim();
	if(location==null || location.trim()=="" || location.replace(/\s/g, '').length==0){
 		alert('Please enter a location');
		return false;
	}
	
 	var e = document.getElementById("locationType");
	if( e.options[e.selectedIndex].value=='Zip' ){
			var regExZip = /^\d{5}$/;
  	 	if (! regExZip.test(location) ) {
  			 alert("Please enter a 5 digit Zip Code");
 			 return false;
		 }
 	}else{ 
		 
		 var regEx2 = /[-!$#%&*()_+|~={}\[\]:";'<>?,.\/]/;
		  if ( regEx2.test(location) ) {
  			 alert("Please Enter a valid City!");
 			 return false;
		 }
	}
	
 	return true;
 }
 	
</script>

 <style type="text/css">
 
form {
 margin-left: 400px;
 margin-top: 100px; 
}

.zeroResults{
	text-align:center;
	}
 
</style>

</head>

<body >
 <div >
<form id="mainForm" method="POST" action="get_weather.php" >
<table style="border-style:double;border-spacing:15px">
  <caption style="font-weight:bold;font-size:30px">Weather Search</caption>
   <tr>
    <td>Location</td>
    <td><input type="text" id="locationText" name="locationText" width="300" /></td>
  </tr>
  <tr>
    <td>Location Type</td>
    <td>
    	<select id="locationType" name="locationType">
        	<option id="locationTypeCity" value="City"> City </option>
  			<option id="locationTypeZIP" value="Zip"> ZIP Code </option>
        </select>
    </td>
  </tr>
   <tr>
    <td>Temperature Unit</td>
    <td>
     <input type="radio" id="temperatureF" name="temperature" checked="checked" value="f"> Fareinheit </input>
     <input type="radio" id="temperatureC" name="temperature" value="c"> Celcius </input>
    </td>
  </tr>
    <tr>
    <td colspan="2" align="center">
    	<input type="submit" value="Search" onclick="return validateForm()"/>
     </td>
   </tr>
</table>
<form>
 </div>
 <?php
  	if( isset($_POST['locationText'] ) ){ ?>
 		  <script type="text/javascript">
			var locationText     =  '<?php echo $_POST['locationText'] ?>';
			var temperatureType  =  '<?php echo $_POST['temperature'] ?>';
 		 	var locationType 	 = 	'<?php echo $_POST['locationType'] ?>';
 			document.getElementById("locationText").value = locationText ;
			if(temperatureType=='f'){
			 			document.getElementById("temperatureF").checked = true ;
			}else{
		   			    document.getElementById("temperatureC").checked = true ;
			}
			if(locationType=='City'){
			 			document.getElementById("locationTypeCity").selected = true ;
			}else{
		   			    document.getElementById("locationTypeZIP").selected = true ;
			}
  		  </script>
 	<?php  } ?>
    
    <?php
 
 	 if( isset($_POST['locationText'] ) ){
 		  processData();
 	 }
	  
 	 function processData(){
	  $locationType = $_POST['locationType'];
	  $locationText = $_POST['locationText'];
	  $temperature = $_POST['temperature'];
	
	 $appid = "5VxpoZbV34FBFVTvJfCDj4z.5x4r_ylzd1lUXfCrhkGmp9cLKCDvlQ7tzZDceyx51RZwkayfoyReqbf1.EZKL_u1b3pb7OE-" ; 
	 $url = "";
	 if( $locationType == "Zip" ){
	 	$url1 = "http://where.yahooapis.com/v1/concordance/usps/".$locationText."?appid=$appid";
		processZip($url1, $temperature, $locationText );
	  }else{
     	$url1 ="http://where.yahooapis.com/v1/places\$and(.q('$locationText'),.type(7));start=0;count=5?appid=$appid";
 		$url1 = urlencode($url1);
  		processCity( $url1, $temperature, $locationText );
	 }
 	 
	}
	
	function processZip($url1, $temperatureType, $locationText ){
 		$xml1 = @simplexml_load_file($url1);
		$outputString ="";
		if(!$xml1){
			echo "<br><div class='zeroResults'><b>Zero Results found!</b></div>";	
		}else{
		$woeid=$xml1->woeid; 
 		
	    $url2="http://weather.yahooapis.com/forecastrss?w=".$woeid."&u=".$temperatureType;
 		$xml2 = @simplexml_load_file($url2,'SimpleXMLElement', LIBXML_NOCDATA);
		
 $outputString = $outputString. "<br><table style='margin:auto' id='outputTable' border='1' cellpadding='10'> <caption><b> 1 Result for Zip Code : ".$locationText."</b>   </caption>						                  <tr> 
				<th>Weather </th> <th>Temperature </th>	<th>City </th> <th>Region </th>
				<th>Country </th> <th>Latitude </th> <th>Longitude </th> <th>Link to Details </th>
			 </tr>";
		$channel = $xml2-> channel;
		$city = $channel -> children('yweather', TRUE)->location->attributes()->city;
	 	$region = $channel -> children('yweather', TRUE)->location->attributes()->region;
		$country = $channel -> children('yweather', TRUE)->location->attributes()->country;
		$temperatureUnits = $channel -> children('yweather', TRUE)->units->attributes()->temperature;
		$condition = $channel -> item -> children('yweather', TRUE)->condition->attributes()->text;
		$temp = $channel -> item -> children('yweather', TRUE)->condition->attributes()->temp;
		 
		$temperature = $condition." ".$temp."&deg; ".$temperatureUnits;
		$latitude = $channel -> item -> children('geo', TRUE)->lat;
		$longitude = $channel -> item -> children('geo', TRUE)->long;
		
		$description = $channel -> item -> description;

		 preg_match('/src=(["\'])(.*?)\1/', $description, $imageTags);
		$imgTag = $imageTags[0];
	 	$image = "<a href='$url2' target='_blank'><img ".$imgTag ." alt='$condition' title='$condition'/></a>";
	 	$link = $channel -> item -> link;
 
 		$city = checkNA($city);
		$region = checkNA($region);
		$country = checkNA($country);
		$latitude = checkNA($latitude);
		$longitude = checkNA($longitude);
		$condition = checkNA($condition);
		$temp = checkNA($temp);
		$temperatureUnits = checkNA($temperatureUnits);
		
		$temperature = $condition." ".$temp."&deg; ".$temperatureUnits;
		
   		$outputString = $outputString. "<tr> 
			<td align='center'>$image</td>
			<td align='center'>$temperature</td>
			<td align='center'>$city</td>
			<td align='center'>$region</td>
			<td align='center'>$country</td>
			<td align='center'>$latitude</td>
			<td align='center'>$longitude</td>";
			if($link==''){
				$outputString = $outputString."<td align='center'> N/A </td>";
			}else{
				$outputString = $outputString."<td align='center'> <a href='$link' target='_blank'>Details</a></td>";
			}
			$outputString = $outputString."</tr>";
		 $outputString =  $outputString. "</table>"; 
		 echo $outputString;
		}
 
 	}
	function checkNA($var){
		if($var==''){
			$var="N/A";
		} 
		return $var;
	}
	
	function processCity( $url1, $temperatureType, $locationText ){
	 	$xml1 = @simplexml_load_file($url1);
		$ctr = 0;
		$outputString = "";
		$tableHeader ="";
		
		$place=$xml1->children();

		 $tableHeader = $tableHeader. "<table table style='margin:auto' id='outputTable' border='1' cellpadding='10'>";
		
		 $outputString = $outputString."
			<tr> 
				<th>Weather </th> <th>Temperature </th>	<th>City </th> <th>Region </th>
				<th>Country </th> <th>Latitude </th> <th>Longitude </th> <th>Link to Details </th>
			 </tr>"; 
		foreach($place as $single_place)
		{
			$woeid = $single_place->woeid;
	   		$url2="http://weather.yahooapis.com/forecastrss?w=".$woeid."&u=".$temperatureType;
			$xml2 = @simplexml_load_file($url2,'SimpleXMLElement', LIBXML_NOCDATA);
			 
			$channel = $xml2-> channel;
		 	$title = $channel->title;
			if($title=="Yahoo! Weather - Error"){
				continue;
			}
		$ctr = $ctr + 1;
		$city = $channel -> children('yweather', TRUE)->location->attributes()->city;
	 	$region = $channel -> children('yweather', TRUE)->location->attributes()->region;
		$country = $channel -> children('yweather', TRUE)->location->attributes()->country;
		$temperatureUnits = $channel -> children('yweather', TRUE)->units->attributes()->temperature;
		$condition = $channel -> item -> children('yweather', TRUE)->condition->attributes()->text;
		$temp = $channel -> item -> children('yweather', TRUE)->condition->attributes()->temp;
		 
		$latitude = $channel -> item -> children('geo', TRUE)->lat;
		$longitude = $channel -> item -> children('geo', TRUE)->long;
		
		$description = $channel -> item -> description;
		preg_match('/src=(["\'])(.*?)\1/', $description, $imageTags);
		$imgTag = $imageTags[0];
		$image = "<a href='$url2' target='_blank'><img ".$imgTag ." alt='$condition' title='$condition' /></a>";
		
 		$city = checkNA($city);
		$region = checkNA($region);
		$country = checkNA($country);
		$latitude = checkNA($latitude);
		$longitude = checkNA($longitude);
		$condition = checkNA($condition);
		$temp = checkNA($temp);
		$temperatureUnits = checkNA($temperatureUnits);
		
		$temperature = $condition." ".$temp."&deg; ".$temperatureUnits;
	
 	 	$link = $channel -> item -> link;
  		
			$outputString = $outputString. "<tr> 
			<td align='center'>$image</td>
			<td align='center'>$temperature</td>
			<td align='center'>$city</td>
			<td align='center'>$region</td>
			<td align='center'>$country</td>
			<td align='center'>$latitude</td>
			<td align='center'>$longitude</td>";
			if($link==''){
				$outputString = $outputString."<td align='center'> N/A </td>";
			}else{
				$outputString = $outputString."<td align='center'> <a href='$link' target='_blank'>Details</a></td>";
			}
			$outputString = $outputString."</tr>";
		
		}
		if($ctr>0){
			$captionString = " <caption><b>  $ctr   Result(s) for City $locationText </b> </caption>";
  			$outputString = "<br>".$tableHeader. $captionString. $outputString. "</table>"; 
 		}else {
			$outputString="<br><div class='zeroResults'><b>Zero Results found!</b></div>";
		}
		echo $outputString;
 	
 	}
 ?>
 

</body>
</html>