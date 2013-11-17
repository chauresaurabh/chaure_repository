package com.example.zhomework9;

import java.io.*;
import java.net.*;

import org.json.*;

import android.os.AsyncTask;

public class DownloadFilesTask extends AsyncTask<URL, Integer, Long> {
	 
	String value = "No Data Found ! ";
    protected Long doInBackground(URL...url)  {
    	System.out.println(" Inside Background");
        int count = url.length;
        long totalSize = 0;
        
        try {
			  value = getWeatherInfo("90007", "Zip", "f");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return totalSize;
    }

    protected void onProgressUpdate(Integer... progress) {
    	System.out.println(" Inside Progress");

       // setProgressPercent(progress[0]);
    }

    protected void onPostExecute(Long result) {
    	System.out.println(" ***********************  Task Complete ****************************");
    	System.out.println(" ************************************************");

    	System.out.println(value);
    	
    	System.out.println(" ************************************************");
    	System.out.println(" ************************************************");

     }
    
    public String getWeatherInfo(String zipcode, String locationType, String temperatureType) throws Exception{

		zipcode = URLEncoder.encode(zipcode, "UTF-8");

		String aUrl = "http://cs-server.usc.edu:19836/examples/GetWeather?weather="+zipcode+"&locationType="+locationType+"" +
				"&temperatureType="+temperatureType;
		System.out.println(aUrl);
		URL url = new URL( aUrl );
		String value="";
 		URLConnection urlConnection	= url.openConnection();
		urlConnection.setAllowUserInteraction(false);
		InputStream stream = url.openStream();	
		StringBuffer str = new StringBuffer();

		String line;
		BufferedReader br = new BufferedReader(new InputStreamReader(stream));
		while ((line = br.readLine()) != null) {
			str.append(line);
		}
		JSONObject jsonObj;
		jsonObj = new JSONObject(str.toString());
		
		System.out.println( " VALUE PRINTED IS : " + jsonObj.getString("weather") );
		value="";
		value = value+jsonObj;
	 
		 return value;
	}

}
