package com.Appium.demo.screens;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class ProductCart {

	public ProductCart(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	@AndroidFindBy(xpath="//*[@resource-id='com.androidsample.generalstore:id/productName']")
	public List<WebElement> CartProductName;

	@AndroidFindBy(id="com.androidsample.generalstore:id/productPrice")
	public List<WebElement> ProductPrice;

	@AndroidFindBy(id="com.androidsample.generalstore:id/totalAmountLbl")
	public WebElement TotalAmount;
     
	@AndroidFindBy(className="android.widget.CheckBox")
	public WebElement CheckBox;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/btnProceed")
	public WebElement Checkout;
	
}
