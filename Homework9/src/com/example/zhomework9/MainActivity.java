package com.example.zhomework9;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 
 * @author SAURABH
 *
 */
public class MainActivity extends Activity {
	private Button searchButton;
	private EditText locationText;
	private TextView city_result;
	private TextView region_country;
	private TextView condition;
	private TextView temperature;
	private RadioButton radioButton;
	
	private ImageView imageView;
	String value = "";
	Bitmap bmp = null;
	String img = "";
	String region = "";
	String city = "";
	String country = "";
	String conditionTemp = "";
	String conditionText = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		searchButton = (Button) findViewById(R.id.btn_do_it);
		city_result = (TextView) findViewById(R.id.city_result);

		imageView = (ImageView)findViewById(R.id.imageview);
		region_country = (TextView) findViewById(R.id.region_country);
		condition = (TextView) findViewById(R.id.condition);
		temperature = (TextView) findViewById(R.id.temperature);

		radioButton = (RadioButton) findViewById(R.id.radio_fareinheit);

		
		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				locationText = (EditText) findViewById(R.id.locationText);
				final String text = locationText.getText().toString();
				 String tempType = "f";
				if(! radioButton.isChecked() ){
					tempType = "c";
				}
				AsyncTaskRunner runner = new AsyncTaskRunner();
				runner.execute(text,"Zip", tempType );
			}
		}); 
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	private class AsyncTaskRunner extends AsyncTask<String, String, String> {

		private String resp;

		@Override
		protected String doInBackground(String... params) {
			try {
				String text = params[0];
				String zip = params[1];
				String tempType = params[2];
				System.out.println(" ************  PARAMSSSS ARRAY ************ "+params);
				value = getWeatherInfo(text,zip,tempType );
			} catch (InterruptedException e) {
				e.printStackTrace();
				resp = e.getMessage();
			} catch (Exception e) {
				e.printStackTrace();
				resp = e.getMessage();
			}
			return resp;
		}

		@Override
		protected void onPostExecute(String result) {
			// execution of result of Long time consuming operation
			city_result.setText(city);
			imageView.setImageBitmap(bmp);
			
			region_country.setText(region + " ," + country);
			temperature.setText(conditionTemp);
			condition.setText(conditionText);
			city_result.setText(city);
 
 		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected void onProgressUpdate(String... text) {
			city_result.setText(value);
		}
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

		JSONObject weatherJSON = jsonObj.getJSONObject("weather");
		JSONObject locationJSON = weatherJSON.getJSONObject("location");
		JSONObject conditionJSON = weatherJSON.getJSONObject("condition");

		region = locationJSON.getString("region");
		city = locationJSON.getString("city");
		country = locationJSON.getString("country");
		conditionTemp = conditionJSON.getString("temp") + "°" +temperatureType.toUpperCase();
		conditionText = conditionJSON.getString("text");

 		value="";
		value = value+jsonObj.getString("weather");
		
		String img = weatherJSON.getString("img");
		URL url2 = new URL( img );
		bmp = BitmapFactory.decodeStream(url2.openConnection().getInputStream());

		return value;
	}

}