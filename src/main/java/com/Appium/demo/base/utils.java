package com.Appium.demo.base;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class utils {
    public  AndroidDriver<AndroidElement> driver;
	public utils(AndroidDriver<AndroidElement>driver){
		this.driver=driver;
	}
	
	public void scrollTo(String text) {
		
		driver.findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\""+text+"\"));");
	    	}
}
