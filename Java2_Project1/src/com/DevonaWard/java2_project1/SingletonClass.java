package com.DevonaWard.java2_project1;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.util.Log;

//THIS SINGLETON CLASS SAVES AND READ FILES FROM THE EXTERNAL
public class SingletonClass {

	private static SingletonClass instance = null;
	protected SingletonClass(){
		
	}
	
	public static SingletonClass getInstance(){
		if(instance == null){
			instance = new SingletonClass();
		}
		return instance;
	}
	
	@SuppressWarnings("resource")
	public static Boolean storeStringFile(Context context, String filename, String content, Boolean external){
		try{
			File file;
			FileOutputStream FOS;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				FOS = new FileOutputStream(file);
			} else{
				FOS = context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			FOS.write(content.getBytes());
			FOS.close();
		} catch (IOException e){
			Log.e("WRITE ERROR", filename);
		}
		return true;
	}
	
	@SuppressWarnings("resource")
	public static Boolean storeObjectFile(Context context, String filename, String content, Boolean external){
		try{
			File file;
			FileOutputStream FOS;
			ObjectOutputStream OOS;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				FOS = new FileOutputStream(file);
			} else{
				FOS = context.openFileOutput(filename, Context.MODE_PRIVATE);
			}
			OOS = new ObjectOutputStream(FOS);
			OOS.writeObject(content);
			OOS.close();
			FOS.close();
		} catch (IOException e){
			Log.e("WRITE ERROR", filename);
		}
		return true;
	}
	
	@SuppressWarnings("resource")
	public static String readStringFile(Context context, String filename, Boolean external){
		String content = "";
		try{
			File file;
			FileInputStream FIN;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				FIN = new FileInputStream(file);
			} else{
				file = new File(filename);
				FIN = context.openFileInput(filename);
			}
			BufferedInputStream BIN = new BufferedInputStream(FIN);
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			while((bytesRead = BIN.read(contentBytes)) != -1){
				content = new String(contentBytes, 0, bytesRead);
				contentBuffer.append(content);
			}
			content = contentBuffer.toString();
			FIN.close();
		} catch (FileNotFoundException e){
			Log.e("READ ERROR","FILE NOT FOUND" + filename);
		} catch (IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		return content;
	}
	
	@SuppressWarnings("resource")
	public static Object readObjectFile(Context context, String filename, Boolean external){
		Object content = new Object();
		try{
			File file;
			FileInputStream FIN;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				FIN = new FileInputStream(file);
			} else{
				file = new File(filename);
				FIN = context.openFileInput(filename);
			}
			ObjectInputStream OIS = new ObjectInputStream (FIN);
			
			try{
				content = (Object) OIS.readObject();
			} catch (ClassNotFoundException e){
				Log.e("READ ERROR", "INVALID JAVA OBJECT FILE");
			}
			OIS.close();
			FIN.close();
		} catch (FileNotFoundException e){
			Log.e("READ ERROR","FILE NOT FOUND" + filename);
		} catch (IOException e){
			Log.e("READ ERROR", "I/O ERROR");
		}
		return content;
	}
}
