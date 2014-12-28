package com.raoxun.airhockey.util;

import com.raoxun.airhockey.util.LoggerConfig.LogLevel;

import android.util.Log;

public class RxLogger {
	public String mTag;
	public RxLogger(String tag){
		this.mTag = tag;
	}
	
	public void e(String message){
		if(LoggerConfig.ON && LoggerConfig.LEVEL >= LogLevel.Error){
			Log.e(mTag, message);
		}
	}
	
	public void w(String message){
		if(LoggerConfig.ON && LoggerConfig.LEVEL >= LogLevel.Warning){
			Log.e(mTag, message);
		}		
	}
	
	public void i(String message){
		if(LoggerConfig.ON && LoggerConfig.LEVEL >= LogLevel.Info){
			Log.e(mTag, message);
		}	
	}
	
	public void d(String message){
		if(LoggerConfig.ON && LoggerConfig.LEVEL >= LogLevel.Debug){
			Log.e(mTag, message);
		}
	}
	
	public void v(String message){
		if(LoggerConfig.ON && LoggerConfig.LEVEL >= LogLevel.Verbose){
			Log.e(mTag, message);
		}
	}
}
