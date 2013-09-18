package com.DevonaWard.java2_project1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
	Context context = this;
	JSONArray theSavedObject;
	String fullName;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	//On click for the "Go" button
	@SuppressLint("HandlerLeak")
	public void startService(View view) {
		
		//HANDLER GOES HERE
		Handler theHandler = new Handler(){
			public void handleMessage(Message msg){
				super.handleMessage(msg);
			}
			
		};
		
		Messenger dataMessenger = new Messenger(theHandler);
		Intent intent = new Intent(this, theService.class);
		intent.putExtra("messenger", dataMessenger);
		startService(intent);

		showData();
		
	}
	//New Activity called from onClick
	public void startActivity(View view){
		//Explicit Intent created
		Intent intent2 = new Intent(this,SecondActivity.class);
		intent2.putExtra("teamName", fullName);
		this.startActivity(intent2);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	private void showData(){
		//Data pulled
		String savedData = SingletonClass.readStringFile(context, "teamsJSONString", false);
		//Spinner gets position
		Spinner spinner = (Spinner) findViewById(R.id.team_spinner);
		int spinnerPosition = spinner.getSelectedItemPosition();
		//Text views to hold called data
		TextView teamN = (TextView) findViewById(R.id.teamTxt);
		TextView teamAb = (TextView) findViewById(R.id.abTxt);
		TextView teamAr = (TextView) findViewById(R.id.arenaTxt);
		TextView teamDiv = (TextView) findViewById(R.id.divTxt);
		
			try {
				theSavedObject = new JSONArray (savedData);
				JSONObject mainObj = theSavedObject.getJSONObject(spinnerPosition);
				//Data put in to strings
				fullName = mainObj.getString("full_name");
				String abbreviation = mainObj.getString("abbreviation");
				String areaName = mainObj.getString("site_name");
				String division = mainObj.getString("division");
				//Datta displayed in the text views.
				teamN.setText(fullName);
				teamAb.setText(abbreviation);
				teamAr.setText(areaName);
				teamDiv.setText(division);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
