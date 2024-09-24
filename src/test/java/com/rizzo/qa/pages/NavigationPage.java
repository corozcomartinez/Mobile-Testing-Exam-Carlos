package com.rizzo.qa.pages;

import org.openqa.selenium.WebElement;

import com.rizzo.qa.BaseTest;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class NavigationPage extends BaseTest {
	@AndroidFindBy(accessibility = "Views") WebElement views; 
	@AndroidFindBy(xpath = "//android.widget.ListView[@resource-id=\"android:id/list\"]") WebElement scrollList; 
	@AndroidFindBy(accessibility = "Animation") WebElement animation; 
	@AndroidFindBy(accessibility = "Push") WebElement push; 
	
	
	//methods
	
	public NavigationPage clickView() throws InterruptedException {
		Thread.sleep(3000);
		views = driver.findElement(AppiumBy.accessibilityId("Views"));
		click(views,"Clicking on views"); 
		return this;
		
	}
	
	public NavigationPage clickAnim() throws InterruptedException {
		Thread.sleep(1000);
		animation = driver.findElement(AppiumBy.accessibilityId("Animation"));
		click(animation,"Clicking on Animation");
		return this;
		
	}
	
	public NavigationPage clickPush() throws InterruptedException {
		Thread.sleep(1000);
		push = driver.findElement(AppiumBy.accessibilityId("Push"));
		click(push,"Clicking on Push");
		return this;
		
	}
	
	public NavigationPage scrollToScrollBars() {
		scrollToElement("Scrolling to ScrollBars");
		return this;
		
		
	}
	
	public NavigationPage scrollToTop() {
		scrollList = driver.findElement(AppiumBy.xpath("//android.widget.ListView[@resource-id=\"android:id/list\"]"));
		scrollUpFull(scrollList,"Scrolling to the top");
		return this;
	}


}
