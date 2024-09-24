package com.rizzo.qa.tests;

import java.lang.reflect.Method;

import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.rizzo.qa.BaseTest;
import com.rizzo.qa.pages.NavigationPage;

public class NavigationTests extends BaseTest {

	NavigationPage navigationPage;
	
  @BeforeMethod
  public void beforeMethod(Method m) {
	  
	  navigationPage = PageFactory.initElements(driver, NavigationPage.class);
	  System.out.println("\n" + "*********Starting Test: " + m.getName() + "***********" + "\n");

  }

  @AfterMethod
  public void afterMethod() {
	  closeApp();
	  launchApp();
  }

  @BeforeClass
  public void beforeClass() {

  }

  @AfterClass
  public void afterClass() {


  }

  
  @Test
  public void navigateFromHomeToPush() throws InterruptedException {
	  navigationPage.clickView();
	  takeScreenshot();
	  navigationPage.clickAnim();
	  takeScreenshot();
	  navigationPage.clickPush();
	  takeScreenshot();
	  
  }
  
  @Test
  public void scrollUpAndDown() throws InterruptedException {
	  navigationPage.clickView();
	  navigationPage.scrollToScrollBars();
	  navigationPage.scrollToTop();
  }
  
}
