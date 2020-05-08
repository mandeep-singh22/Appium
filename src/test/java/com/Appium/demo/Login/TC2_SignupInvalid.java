package com.Appium.demo.Login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Appium.demo.base.DynamicBaseDriver;
import com.Appium.demo.base.utils;
import com.Appium.demo.screens.HomePage;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TC2_SignupInvalid  extends DynamicBaseDriver{

	
	public static AndroidDriver<AndroidElement> driver;
	Properties prop= new Properties();
	String filepath="src/main/java/com/Appium/demo/resources/userInfo.properties";
	
	@BeforeTest
	public void setup() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
		
		startService();
	driver= capabilities();
	driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	@AfterTest
	public void teardown() throws IOException, InterruptedException {
		killServer();
		service.stop();
	}
	
	@Test (priority=0)
	public void signUp2() throws FileNotFoundException {
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

		//Submit
		h.Submit.click();
	}
	
	@Test (dependsOnMethods = "signUp2")
	public void validateSignup() {
		
		String toastMessage=driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		System.out.println("Toast Message: "+ toastMessage);
		Assert.assertEquals(prop.getProperty("LoginToast"), toastMessage);
		System.out.println("Toast message verified");
	}
	
}
