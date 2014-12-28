package com.raoxun.airhockey.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.Resources;

public class TextResourceReader {
	public static String readTextFileFromResource(Context context, int resourId){
		StringBuilder body = new StringBuilder();
		try{
			InputStream inputStream = context.getResources().openRawResource(resourId);
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String nextLine;
			while((nextLine = bufferedReader.readLine()) != null){
				body.append(nextLine);
				body.append("\n");
			}
		} catch (IOException e){
			throw new RuntimeException("coundn't open resource: " + resourId, e);
		} catch (Resources.NotFoundException e) {
			throw new RuntimeException("resource not found: " + resourId, e);
		}
		
		return body.toString();
	}
}
