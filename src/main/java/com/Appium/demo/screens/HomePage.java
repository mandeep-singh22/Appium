package com.Appium.demo.screens;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public class HomePage {

	public HomePage(AppiumDriver driver) {
		PageFactory.initElements(new AppiumFieldDecorator(driver), this);
	}
	
	@AndroidFindBy(xpath="//*[@resource-id='android:id/text1']")
	public WebElement CounrtySelector;
	
	@AndroidFindBy(xpath="//*[@text='Enter name here']")
	public WebElement Name;
	
	@AndroidFindBy(xpath="//*[@text='Male']")
	public WebElement Gender;
	
	@AndroidFindBy(id="com.androidsample.generalstore:id/btnLetsShop")
	public WebElement Submit;
	
	@AndroidFindBy(xpath="//*[@text='Argentina']")
	public WebElement Country;
}
