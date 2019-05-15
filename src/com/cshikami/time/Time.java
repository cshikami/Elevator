package com.cshikami.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class Time {

	private volatile static Time ourInstance;
	
	private long startTime;
	
	private Time() {
		startTime = System.currentTimeMillis();
	}
	
	public static Time getInstance() {
		if (ourInstance == null ) {
			synchronized (Time.class) {
				if (ourInstance == null) {
					ourInstance = new Time();
				}
			}
		}
		return ourInstance;
	}
	
	public String getCurrentTime(){

		long currentTime = System.currentTimeMillis() - startTime;
		return (new SimpleDateFormat("mm:ss:SS")).format(new Date(currentTime));
    }
}
