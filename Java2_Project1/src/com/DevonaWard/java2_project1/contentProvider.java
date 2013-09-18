package com.DevonaWard.java2_project1;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.provider.BaseColumns;
import android.util.Log;

public class contentProvider extends ContentProvider {
	public static final String AUTHORITY = "com.DevonaWard.Java2_Project1.contentProvider";
	
	public static class teamData implements BaseColumns{
		public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");
		public static String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.DevonaWard.Java2_Project1.item";
		public static String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.DevonaWard.Java2_Project1.item";
		
		//Define column
		public static final String OBJECT_COLUMN = "object";
		public static final String TEAM_COLUMN = "team";
		public static final String SITE_COLUMN = "site";
		
		public static final String[] PROJECTION = {"_Id", OBJECT_COLUMN,TEAM_COLUMN,SITE_COLUMN};
		private teamData(){};
	}
	
	public static final int ITEMS = 1;
	public static final int ITEMS_ID= 2;
	
	private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static{
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS);
		uriMatcher.addURI(AUTHORITY, "items/", ITEMS_ID);
	}
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		
		switch(uriMatcher.match(uri)){
		case ITEMS:
			return teamData.CONTENT_TYPE;
			
		case ITEMS_ID:
			return teamData.CONTENT_ITEM_TYPE;
		}
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		MatrixCursor result = new MatrixCursor(teamData.PROJECTION);
		
		String JSONString = SingletonClass.readStringFile(getContext(), "teamsJSON", false);
		JSONObject team = null;
		JSONArray teamArray = null;
		JSONObject field = null;
		
		try {
			team = new JSONObject(JSONString);
			teamArray = team.getJSONArray("first_name");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if (teamArray == null){
			return result;
		}
		
		switch(uriMatcher.match(uri)){
		case ITEMS:
			
			for(int i=0; i<teamArray.length(); i++){
				try{
					field = teamArray.getJSONObject(i).getJSONObject("first_name");
					result.addRow(new Object[] {i+1, field.get("last_name"), field.get("site")});
				}catch(JSONException e){
					e.printStackTrace();
				}
			}
			
		case ITEMS_ID:
			String itemId = uri.getLastPathSegment();
			Log.i("queryId", itemId);
			
			int index;
			try{
				index = Integer.parseInt(itemId);
			}
			catch (NumberFormatException e)
			{
				Log.e("query", "index format error");
				break;
			}
			if(index <=0 || index > teamArray.length()){
				Log.e("query", "index out of range for " + uri.toString());
				break;
			}
			
			try {
				field = teamArray.getJSONObject(index-1).getJSONObject("first_name");
				result.addRow(new Object[] {index, field.get("last_name"), field.get("site")});
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
			default:
				Log.e("query", "invalid uri = " + uri.toString());
		}
		return result;

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
