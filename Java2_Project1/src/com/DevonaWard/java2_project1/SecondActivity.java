package com.DevonaWard.java2_project1;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;


public class SecondActivity extends Activity{
	String searchItem;
	String url;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.second_activity);
		
		//Get data from Intent
		Bundle data = getIntent().getExtras();
		if(data != null){
		searchItem = data.getString("teamName");
		Log.i("BUD",searchItem);
		}

		WebView myWebView = (WebView) findViewById(R.id.webview);
		//Fit content in web view. Found at http://pastebin.com/UkzM15QH
		myWebView.setInitialScale(100);
		myWebView.loadUrl("http://search.espn.go.com/"+searchItem);
		 
	}
}
