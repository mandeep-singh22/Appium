package com.Appium.demo.Login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Appium.demo.base.DynamicBaseDriver;
import com.Appium.demo.base.utils;
import com.Appium.demo.screens.HomePage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TC1_Signup  extends DynamicBaseDriver{

	
	public static AndroidDriver<AndroidElement> driver;
	Properties prop= new Properties();
	String filepath="src/main/java/com/Appium/demo/resources/userInfo.properties";
	

	@BeforeTest
	public void setup() throws IOException, InterruptedException {
		killServer();
		
		startService();
		driver= capabilities();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void teardown() {
		System.out.println("SignedUp Sucessfully");
		driver.closeApp();
		service.stop();
	}
	
	@Test (priority=0)
	public void signUp() throws FileNotFoundException {
		HomePage h=new HomePage(driver);
		FileInputStream fis= new FileInputStream(filepath);
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//Select Counrty
		h.CounrtySelector.click();
		//driver.findElementsByAndroidUIAutomator("new UiScrollable(new UiSelector()).scrollIntoView(text(\"Argentina\"));");
		utils u=new utils(driver);
		u.scrollTo(prop.getProperty("Country"));
		h.Country.click();

		//Enter Name
		h.Name.sendKeys(prop.getProperty("Name"));
		h.Gender.click();

		//Submit
		h.Submit.click();
		
		
	}
}
