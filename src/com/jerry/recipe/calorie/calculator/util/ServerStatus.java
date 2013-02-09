package com.jerry.recipe.calorie.calculator.util;

public class ServerStatus {

	private int mResponseCode;
	private String mResponseMessage;
	
	public int getResponseCode() {
		return mResponseCode;
	}

	public String getResponseMessage() {
		return mResponseMessage;
	}
	
	public ServerStatus(int responseCode, String responseMessage) {
		mResponseCode = responseCode;
		mResponseMessage = responseMessage;
	}
	
}
