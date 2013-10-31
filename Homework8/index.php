 <?php
  	 
 	 if( isset($_GET['locationText'] ) ){
 		  processData();
 	 }
	  
 	 function processData(){
	  $locationType = $_GET['locationType'];
	  $locationText = $_GET['locationText'];
	  $temperatureType = $_GET['temperatureType'];
	
	 $appid = "5VxpoZbV34FBFVTvJfCDj4z.5x4r_ylzd1lUXfCrhkGmp9cLKCDvlQ7tzZDceyx51RZwkayfoyReqbf1.EZKL_u1b3pb7OE-" ; 
	 $url = "";
	 if( $locationType == "Zip" ){
	 	$url1 = "http://where.yahooapis.com/v1/concordance/usps/".$locationText."?appid=$appid";
		processZip($url1, $temperatureType, $locationText, $locationType );
	  } 
 	 else{
	    $url1 ="http://where.yahooapis.com/v1/places\$and(.q('$locationText'),.type(7));start=0;count=5?appid=$appid";
 	 	//echo "<weather>"."<error>".$url1."</error>";
		$url1 = urlencode($url1); 
 	 	//echo  "<error2>".$url1."</error2></weather>";
 		processZip($url1, $temperatureType, $locationText, $locationType );
 		}
	}
	
	function processZip($url1, $temperatureType, $locationText, $locationType ){
 		$xml1 = @simplexml_load_file($url1);
		$outputString ="";
		$woeid="";
		if(!$xml1){
			echo "<weather><error>Weather information cannot be found!</error></weather>";	
		}else{
		 
		 if( $locationType == "Zip" ){
			$woeid=$xml1->woeid; 
		 }else{
			 $place=$xml1->children();
			 $firstPlace = $place[0];
			 $woeid=$firstPlace->woeid; 
		}
		 
 		
	    $url2="http://weather.yahooapis.com/forecastrss?w=".$woeid."&u=".$temperatureType;
		
 		$xml2 = @simplexml_load_file($url2,'SimpleXMLElement', LIBXML_NOCDATA);
	    $channel = $xml2-> channel;
		 
		 $link = $channel -> item -> link;
		 $city = $channel -> children('yweather', TRUE)->location->attributes()->city;
	 	$region = $channel -> children('yweather', TRUE)->location->attributes()->region;
		$country = $channel -> children('yweather', TRUE)->location->attributes()->country;
	 	$temperatureUnits = $channel -> children('yweather', TRUE)->units->attributes()->temperature;
		$condition = $channel -> item -> children('yweather', TRUE)->condition->attributes()->text;
		$temp = $channel -> item -> children('yweather', TRUE)->condition->attributes()->temp;
		$description = $channel -> item -> description;
		$forecastChildren = $channel -> item -> children('yweather', TRUE)->forecast;

		// preg_match('(?<=<img (*)src=\")[^\"]*', $description, $imageTags);
		 // preg_match('<img[^>]+src="([^">]+)"', $description, $imageTags);
		 //preg_match('/<img.+?src(?: )*=(?: )*[\'"](.*?)[\'"]/si', $description, $imageTags); 

		 preg_match('/src(?: )*=(?: )*[\'"](.*?)[\'"]/si', $description, $imageTags); 
		 $res = explode('"', $imageTags[0]); 
		 
		$url2=htmlspecialchars($url2);	  
		$outputString ="<weather>";
		$outputString = $outputString. "<feed>". $url2 ."</feed>";
	  	$outputString = $outputString. "<link>".$link."</link>";
	  	$outputString = $outputString. "<location city='". $city."' region='". $region ."' country='".$country."' />";
	  	$outputString = $outputString. "<units temperature='". $temperatureUnits ."' />";
	  	$outputString = $outputString. "<condition text='". $condition."' temp='".$temp."' />";
 	    $outputString = $outputString."<img>". $res[1]."</img>";

		foreach($forecastChildren as $singleChild)
		{
			$fDay = $singleChild->attributes()->day;
			$fLow = $singleChild->attributes()->low;
			$fHigh = $singleChild->attributes()->high;
			$fText = $singleChild->attributes()->text;

			$outputString = $outputString."<forecast day='".$fDay ."' low='".$fLow."' high='".$fHigh."' text='".$fText."' />";
		}
 	    $outputString = $outputString."</weather>";
 	 echo $outputString;
		}
 
 	}
	function checkNA($var){
		if($var==''){
			$var="N/A";
		} 
		return $var;
	}
	 
 ?>
 
 