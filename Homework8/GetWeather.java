import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

public class GetWeather extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 

			throws ServletException, IOException { 

		String[] paramValues = request.getParameterValues("weather");
		String[] paramValues2 = request.getParameterValues("locationType");
		String[] paramValues3 = request.getParameterValues("temperatureType");

		PrintWriter out = response.getWriter();
		String str="";
		try{
			str = getWeatherInfo(paramValues[0],paramValues2[0], paramValues3[0]);
		}catch(Exception e){
			str =  "{" +
					" weather: {" +
									" error : 'Error occured while getting data, Cause : "+ e.getMessage()+"'" +
								"}" +
					"}";
		}
		out.println (str);
	}

	public String getWeatherInfo(String zipcode, String locationType, String temperatureType) throws Exception{

		zipcode = URLEncoder.encode(zipcode, "UTF-8");

		String aUrl = "http://saurabh-chaure.elasticbeanstalk.com/?locationType="+locationType+"&locationText="+zipcode+"&temperatureType="+temperatureType;
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
		jsonObj = XML.toJSONObject(str.toString());
		value="";
		value = value+jsonObj;
	 
		 return value;
	}

}