package com.DevonaWard.java2_project1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;
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
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
	Context context = this;
	JSONArray theSavedObject;
	String fullName;
	String teamID;
	Button moreInfo;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		moreInfo = (Button) findViewById(R.id.moreBtn);
		moreInfo.setEnabled(false);
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
		intent2.putExtra("teamName", teamID);
		this.startActivity(intent2);
	}
	//Start About Intent (Implicit Intent)
	public void startAbout(View view){
		Intent aboutIntent = new Intent (Intent.ACTION_VIEW,Uri.parse("https://github.com/dward1289?tab=repositories"));
		startActivity(aboutIntent);
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
				teamID = mainObj.getString("team_id");
				fullName = mainObj.getString("full_name");
				String abbreviation = mainObj.getString("abbreviation");
				String areaName = mainObj.getString("site_name");
				String division = mainObj.getString("division");
				//Data displayed in the text views.
				teamN.setText(fullName);
				teamAb.setText(abbreviation);
				teamAr.setText(areaName);
				teamDiv.setText(division);
				
				//Enable more info button
				moreInfo.setEnabled(true);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
}
