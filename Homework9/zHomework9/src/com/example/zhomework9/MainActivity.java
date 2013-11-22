package com.example.zhomework9;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.*;
import com.facebook.model.*;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
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

	private TextView postCurrent;
	private TextView postForeCast;
	private TextView forecastlabel;
	private TextView error_result;
	Dialog dialog;
	private TableLayout tableLayout;

	private ImageView imageView;
	String value = "";
	Bitmap bmp = null;
	String img = "";
	String region = "";
	String city = "";
	String country = "";
	String conditionTemp = "";
	String conditionText = "";
	JSONArray forecastArray = null;
	String errorString = "";
	String displayLink = "";

	String feed = "";

	String cityStateCountry = "";
	String currentCaption = "";

	Session session = null;

	String  tempType="f";
	ArrayList < List<Integer> > list = new ArrayList<List<Integer>>();

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
		forecastlabel = (TextView) findViewById(R.id.forecastlabel);
		error_result = (TextView) findViewById(R.id.error_result);

		radioButton = (RadioButton) findViewById(R.id.radio_fareinheit);

		tableLayout = (TableLayout) findViewById(R.id.forecast_table);

		postCurrent = (TextView) findViewById(R.id.postCurrent);
		postForeCast = (TextView) findViewById(R.id.postForecast);

		List <Integer> row1 = Arrays.asList(R.id.row1_1, R.id.row1_2 ,R.id.row1_3 ,  R.id.row1_4 );
		List <Integer> row2 = Arrays.asList(R.id.row2_1, R.id.row2_2 ,R.id.row2_3 ,  R.id.row2_4 );
		List <Integer> row3 = Arrays.asList(R.id.row3_1, R.id.row3_2 ,R.id.row3_3 ,  R.id.row3_4 );
		List <Integer> row4 = Arrays.asList(R.id.row4_1, R.id.row4_2 ,R.id.row4_3 ,  R.id.row4_4 );
		List <Integer> row5 = Arrays.asList(R.id.row5_1, R.id.row5_2 ,R.id.row5_3 ,  R.id.row5_4 );

		list.add(row1);	list.add(row2);list.add(row3);list.add(row4);list.add(row5);

		Session session2 = Session.getActiveSession();

		if(session2 == null )
			session2 = new Session(MainActivity.this);

		Session.setActiveSession(session2);


		searchButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				locationText = (EditText) findViewById(R.id.locationText);
				final String text = locationText.getText().toString();
				tempType = "f";
				if(! radioButton.isChecked() ){
					tempType = "c";
				}
				AsyncTaskRunner runner = new AsyncTaskRunner();
				runner.execute(text,"City", tempType );
			}
		}); 

		TextView btn=(TextView) findViewById(R.id.postCurrent);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog = new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.dialog_current);
				dialog.setTitle("Post to Facebook");
				dialog.show();

				Button postCurrent = (Button)dialog.findViewById(R.id.postCurrent);    
				postCurrent.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Session.openActiveSession( MainActivity.this, true, new Session.StatusCallback() {
							// callback when session changes state
							@Override
							public void call(Session session, SessionState state, Exception exception) {
								if (state.isOpened() || session.isOpened()) {
									publishFeedDialog(0);        
								}
							}
						}); 

					}
				});

				Button button = (Button)dialog.findViewById(R.id.postCurrentCancel);    
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

			}
		});

		/**
		 * 
		 */

		TextView text2=(TextView) findViewById(R.id.postForecast);
		text2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				dialog = new Dialog(MainActivity.this);
				dialog.setContentView(R.layout.dialog_forecast);
				dialog.setTitle("Post to Facebook");
				dialog.show();

				Button postForecast = (Button)dialog.findViewById(R.id.postForecast);    
				postForecast.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Session.openActiveSession( MainActivity.this, true, new Session.StatusCallback() {
							// callback when session changes state
							@Override
							public void call(Session session, SessionState state, Exception exception) {
								if (state.isOpened() || session.isOpened()) {
									publishFeedDialog(1);        
								}
							}
						}); 

					}
				});

				Button button = (Button)dialog.findViewById(R.id.postForecastCancel);    
				button.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				});

			}
		});



	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Session.getActiveSession().onActivityResult(this, requestCode, resultCode, data);
	}

	private void publishFeedDialog(int current) {
		// this method will be flag based 
		String currentCaption = "";
		String temperatureText = "";
		if(current == 0){
			  currentCaption = "The current condition for " + city + " is \n" + conditionText;
			  temperatureText = "Temperature is " + temperature.getText() ;
		}else if (current == 1){
			  currentCaption = "Weather Forecast for "+city;
				for (int i = 0; i < forecastArray.length(); i++) {
					
						try {
							JSONObject object = (JSONObject) forecastArray.get(i);
							temperatureText+=object.getString("day")+":"+object.getString("text")+","+object.getString("high")+"/"+object.getString("low")
									+"°"+tempType.toUpperCase()+";";
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
				}
 		}
		Bundle params = new Bundle();
		params.putString("name", city+" ,"+region + " ," + country);
		params.putString("caption", currentCaption);
		params.putString("description",  temperatureText);
		params.putString("link", feed );
		params.putString("picture", img);


		JSONObject property = new JSONObject();
		try {
			property.put("text", " here");
			property.put("href", displayLink);
			JSONObject properties = new JSONObject();
			properties.put("Look at details", property);
			params.putString("properties", properties.toString());

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		WebDialog feedDialog = (
				new WebDialog.FeedDialogBuilder(MainActivity.this,
						Session.getActiveSession(),
						params))
						.setOnCompleteListener(new OnCompleteListener() {

							//  @Override
							public void onComplete(Bundle values,
									FacebookException error) {
								dialog.dismiss();

								if (error == null) {
									// When the story is posted, echo the success
									// and the post Id.
									final String postId = values.getString("post_id");
									if (postId != null) {
										Toast.makeText(MainActivity.this,
												"Posted Weather Information to the Wall",
												Toast.LENGTH_SHORT).show();
									} else {
										// User clicked the Cancel button
										Toast.makeText(MainActivity.this.getApplicationContext(), 
												"Publish cancelled", 
												Toast.LENGTH_SHORT).show();
									}
								} else if (error instanceof FacebookOperationCanceledException) {
									// User clicked the "x" button
									Toast.makeText(MainActivity.this.getApplicationContext(), 
											"Publish cancelled", 
											Toast.LENGTH_SHORT).show();
								} else {
									// Generic, ex: network error
									Toast.makeText(MainActivity.this.getApplicationContext(), 
											"Error posting story", 
											Toast.LENGTH_SHORT).show();
								}
							}

						})
						.build();
		feedDialog.show();
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
			if(errorString==null){
				city_result.setText(city);
				imageView.setImageBitmap(bmp);
				region_country.setText(region + " ," + country);
				temperature.setText(conditionTemp);
				condition.setText(conditionText);
				error_result.setText("");

				tableLayout.setVisibility(0);
				forecastlabel.setVisibility(0);
				postCurrent.setVisibility(0);
				imageView.setVisibility(0);
				postForeCast.setVisibility(0);

			}else{
				error_result.setText(errorString);
				city_result.setText("");
				cityStateCountry = "";

				imageView.setVisibility(4);
				region_country.setText("");
				temperature.setText("");
				condition.setText("");
				tableLayout.setVisibility(4);
				forecastlabel.setVisibility(4);
				postCurrent.setVisibility(4);

				postForeCast.setVisibility(4);
			}

			for (int i = 0; i < forecastArray.length(); i++) {
				try {
					JSONObject object = (JSONObject) forecastArray.get(i);
					List list1 = (List) list.get(i);

					for (int j = 0; j < list1.size(); j++) {

						switch (j) {						
						case 0:
							((TextView) findViewById((Integer)list1.get(j))).setText("      "+object.getString("day"));
							break;
						case 1:
							((TextView) findViewById((Integer)list1.get(j))).setText("      "+object.getString("text")+"   ");
							break;
						case 2:
							((TextView) findViewById((Integer)list1.get(j))).setText("      "+object.getString("high") + "°" +tempType.toUpperCase());
							break;
						case 3:
							((TextView) findViewById((Integer)list1.get(j))).setText("      "+object.getString("low") + "°" +tempType.toUpperCase() );
							break;

						}
					}

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
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

		errorString = null;

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
		if(str.toString().contains("error")){
			errorString = weatherJSON.getString("error");
			region = "";
			city = "";
			country = "";
			conditionTemp = "";
			conditionText = "";
			feed = "";
			forecastArray = new JSONArray();

			return "";
		}
		// there could be exception here need to check.
		JSONObject locationJSON = weatherJSON.getJSONObject("location");
		JSONObject conditionJSON = weatherJSON.getJSONObject("condition");
		region = locationJSON.getString("region");
		city = locationJSON.getString("city");
		country = locationJSON.getString("country");
		conditionTemp = conditionJSON.getString("temp") + "°" +temperatureType.toUpperCase();
		conditionText = conditionJSON.getString("text");

		value="";
		value = value+jsonObj.getString("weather");

		img = weatherJSON.getString("img");
		displayLink = weatherJSON.getString("link");

		feed = weatherJSON.getString("feed");
		URL url2 = new URL( img );
		bmp = BitmapFactory.decodeStream(url2.openConnection().getInputStream());


		forecastArray = weatherJSON.getJSONArray("forecast");

		return value;
	}

}