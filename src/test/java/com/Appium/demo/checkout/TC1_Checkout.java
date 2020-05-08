package com.Appium.demo.checkout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.Appium.demo.base.DynamicBaseDriver;
import com.Appium.demo.base.utils;
import com.Appium.demo.screens.HomePage;
import com.Appium.demo.screens.ProductCart;
import com.Appium.demo.screens.ProductPage;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class TC1_Checkout extends DynamicBaseDriver{

	public static AndroidDriver<AndroidElement> driver;
	Properties prop= new Properties();
	String filepath="src/main/java/com/Appium/demo/resources/userInfo.properties";
	
	@BeforeTest
	public void setup() throws IOException, InterruptedException {
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);
		
		startService();
		driver =capabilities();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterTest
	public void teardown() {
		System.out.println("Checkout product Sucessfully");
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

	@Test(dependsOnMethods = "signUp")
	public void addProducts() throws FileNotFoundException {
		FileInputStream fis= new FileInputStream(filepath);
		try {
			prop.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ProductPage p1=new ProductPage(driver);
		//Get Toast Text
		p1.Cart.click();
		utils u=new utils(driver);
		String toastMessage=driver.findElement(By.xpath("//android.widget.Toast[1]")).getAttribute("name");
		System.out.println(toastMessage);
		Assert.assertEquals("Please add some product at first", toastMessage);

		//Add products to cart
		//1st product
		//String firstProduct="new UiScrollable(new UiSelector()).scrollIntoView(text(\"Nike Blazer Mid '77\"));";
		//driver.findElementsByAndroidUIAutomator(firstProduct);
		u.scrollTo(prop.getProperty("Product1"));	
		p1.AddToCartBtn.get(0).click();
		
		//adding 2nd product
		//String SecondProduct="new UiScrollable(new UiSelector().resourceId(\"com.androidsample.generalstore:id/rvProductList\")).scrollIntoView(new UiSelector().textMatches(\"Jordan 6 Rings\").instance(0))";
		//driver.findElement(MobileBy.AndroidUIAutomator(SecondProduct));
		u.scrollTo(prop.getProperty("Product2"));
		int count=p1.ProductNameList.size();
		for(int i=0;i<count;i++)
		{
			String text=p1.ProductNameList.get(i).getText();
			if(text.equalsIgnoreCase(prop.getProperty("Product2")))
			{
				p1.AddToCartBtn.get(i).click();
				break;
			}
		}
		//GotoCart
		p1.Cart.click();
	}

	@Test(dependsOnMethods = "addProducts")
	public void verifyAmount() throws InterruptedException {
		Thread.sleep(3000);
		ProductCart p2=new ProductCart(driver);
		String amount1 = null, amount2;
		Double amount, TotalAmount, sum=(double) 0;
		
		int count= p2.ProductPrice.size();
		System.out.println(count);
		for(int i=0; i<count;i++) {
		
			amount1=p2.ProductPrice.get(i).getText();
			amount1= amount1.substring(1);
			amount = Double.parseDouble(amount1); 
			sum= sum+amount;
			System.out.println(p2.CartProductName.get(i).getText()+": $"+ amount);
		}
		System.out.println("Total Aomunt: $"+sum);
		
		amount2=p2.TotalAmount.getText();
		amount2= amount2.substring(1);
		TotalAmount=Double.parseDouble(amount2);
		Assert.assertEquals(sum, TotalAmount);
		System.out.println("Total Amount verified");
		
	
	}

	@Test(dependsOnMethods = "verifyAmount")
	public void checkout() {
		ProductCart p2=new ProductCart(driver);
		p2.CheckBox.click();
		p2.Checkout.click();
	}


}




