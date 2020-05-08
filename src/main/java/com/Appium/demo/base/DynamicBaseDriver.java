package com.Appium.demo.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.aspectj.util.FileUtil;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class DynamicBaseDriver {
	
	public static AppiumDriverLocalService service;
	public static AndroidDriver<AndroidElement> driver;
	
	public AppiumDriverLocalService startService() {
	   boolean flag= checkIfServerIsRunning(4723);
	   if(!flag) {
		service = AppiumDriverLocalService.buildDefaultService();
		service.start();
	   }
		return service;
	}
	
	public static boolean checkIfServerIsRunning(int port) {
	
		boolean isServerRunning=false;
		ServerSocket serverSocket;
		
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.close();
		}catch(IOException e)
		{
			isServerRunning= true;
		}finally {
			serverSocket= null;
		}
		return isServerRunning;
	}
	
	public static void killServer() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
	}
	public static void startEmulator() throws IOException, InterruptedException {
		Runtime.getRuntime().exec(System.getProperty("user.dir")+"/src/main/java/com/Appium/demo/resources/startEmulator.bat");
		Thread.sleep(10000);
	}
	
	public static AndroidDriver<AndroidElement> capabilities() throws IOException, InterruptedException {

		InputStream input = new FileInputStream("src/main/java/com/Appium/demo/resources/config.properties"); 
		Properties prop = new Properties();
		// load a properties file
		try {
			prop.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		File f=new File("src");
		File fs=new File(f,prop.getProperty("ApkFile"));

		//Setting Device        
		String ServerURL= prop.getProperty("ServerUrl");
		DesiredCapabilities cap= new DesiredCapabilities();
			
			String device=(String) System.getProperty("deviceName");
			if(device.contains("Pixel")) {
				startEmulator();
			}
		cap.setCapability(MobileCapabilityType.DEVICE_NAME,device);
		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);

		
		driver =new AndroidDriver<AndroidElement>(new URL(ServerURL) ,cap);
		return driver;
	}
	
	public static void getScreenshot(String s) throws IOException {
		File srcfile= ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(srcfile,new File(System.getProperty("user.dir")+"\\"+s+".png"));	
	}
	 
}
