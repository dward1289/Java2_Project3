package com.DevonaWard.movietime;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;

public class Service extends IntentService{
	Message message;
	Messenger messenger;
	URL url;
	Context context = this;
	
	public Service(){
		super("Service");
	}
	
	@Override
	protected void onHandleIntent(Intent intent){
			try {
				url = new URL ("https://erikberg.com/nba/teams.json");//NEW API HERE
			} catch (MalformedURLException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			  
			  ConnectivityManager connectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			  NetworkInfo netInfo = connectManager.getActiveNetworkInfo();
			  
			  //Check network connection
			  if(netInfo != null && netInfo.isConnected()){
				  try{ 
					  String response = "";
					  
					  //Get connected
					  URLConnection connection = url.openConnection();
					  BufferedInputStream BIS = new BufferedInputStream(connection.getInputStream());
					  
					  byte[] contentBytes = new byte[1024];
					  int bytesRead = 0;
					  StringBuffer stringBuffer = new StringBuffer();
					  
					  while ((bytesRead = BIS.read(contentBytes)) != -1){
						  response = new String(contentBytes, 0, bytesRead);
						  stringBuffer.append(response);
					  }
					  response = stringBuffer.toString();

					  //Verify  JSON string
					  JSONArray json = null;
					  try{
						  json = new JSONArray(response);
						  Log.i("JSON IS HERE", "The json is valid");
						  
						  //Save json data
						  FileSystem.storeObjectFile(context, "teamsJSON", json.toString(), false);
						  FileSystem.storeStringFile(context, "teamsJSONString", json.toString(), false);
					  }catch (JSONException e){
						  Log.e("JSON storeJSON", e.getMessage().toString());
					  }
					  
					  String team = "";
					  for(int i = 0; i<json.length(); i++){
						  try{
							  team = json.getJSONObject(i).getString("team_id".toString());
						  } catch (JSONException e){
							  e.printStackTrace();
						  }
						  Log.d("DATA PARSED",team);
					  }
				  }catch(MalformedURLException e){
					  e.printStackTrace();
			  } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
	}
}
