package com.rizzo.qa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.google.common.collect.ImmutableMap;
import com.rizzo.qa.utils.TestUtils;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.InteractsWithApps;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.screenrecording.CanRecordScreen;

public class BaseTest {
	
	protected static AppiumDriver driver;
	protected static Properties props;
	protected static String dateTime;
	TestUtils utils;
	static Logger log = LogManager.getLogger(BaseTest.class.getName());

                 	
	
	@Parameters({"emulator","udid","platformName","deviceName"})
	@BeforeTest
	public void beforeTest(String emulator, String udid,String platformName, String deviceName) throws FileNotFoundException {
		log.info("Starting Appium Device initialization");
		  utils = new TestUtils();
		  dateTime = utils.getDateTime(); 

		//incializacion del driver y properties
	      InputStream inputStream = new FileInputStream("C:\\Users\\corozcomartinez\\Appium_Projects\\Day6Activity\\src\\main\\resources\\config.properties");
	  try {
		  props = new Properties();
		  inputStream.getClass().getResourceAsStream("C:\\Users\\corozcomartinez\\Appium_Projects\\Day6Activity\\src\\main\\resources\\config.properties");
          props.load(inputStream);

  		    String appUrl = System.getProperty("user.dir") + File.separator+ "src" + File.separator +  "test" + File.separator + "resources"+ File.separator + "app" + File.separator+"ApiDemos-debug.apk";
  			UiAutomator2Options options = new UiAutomator2Options().setAutomationName("UiAutomator2").setAppPackage(props.getProperty("androidAppPackage")).setAppActivity(props.getProperty("androidAppActivity"));
			URL url = new URL(props.getProperty("appiumURL"));


          
			//inicializamos el driver y la sesion
		    driver = new AndroidDriver(url, options);
			System.out.println("Session ID: "+ driver.getSessionId());
		  
	  }catch(Exception e) {
		  e.printStackTrace();
		  String trace = e.getMessage();
		  log.error("Could not initializate the Appium Driver: " + trace );
	  }
		log.info("Appium Device initialization Successfull");

	
	}
	
	public void click(WebElement e,String msg) throws InterruptedException {
		  Thread.sleep(2000);
		  log.info(msg);
		  e.click();
	  }
	
	  public void closeApp() {
			log.info("Closing app");
		  ((InteractsWithApps) driver).terminateApp(props.getProperty("androidAppPackage"));
	  }
	  public void launchApp() {
			log.info("Launching app");
		  ((InteractsWithApps) driver).activateApp(props.getProperty("androidAppPackage"));
	  }
	 
	  
	  public WebElement scrollToElement(String msg) {
			log.info(msg);
		  return driver.findElement(AppiumBy.androidUIAutomator(
				  "new UiScrollable(new UiSelector()"+".resourceId(\"android:id/list\")).scrollIntoView("+"new UiSelector().description(\"ScrollBars\"));"));
	  }
	  
	  public void scrollUpFull(WebElement e,String msg) { 
			log.info(msg);
		  Boolean canScrollMore = true;
	        while(canScrollMore){
	            canScrollMore = (Boolean) driver.executeScript("mobile: scrollGesture", ImmutableMap.of(
	                    "elementId", ((RemoteWebElement) e).getId(),
	                    "direction", "up",
	                    "percent", 1.0
	            ));
	            
		}
	  }
	  public String getDateTime() {
		  return dateTime;
	  }
	  public String random() {
		  Random rand = new Random();
		  Integer num = rand.nextInt(50);
		  String ran = num.toString();
		  return ran;
	  }
	  
	  //metodo screenshot
	  public void takeScreenshot() {
			log.info("Creating the filepath for screenshots");
		File file = driver.getScreenshotAs(OutputType.FILE);
		String imagePath = "Screenshots" + File.separator +"Android"+ 
				File.separator + getDateTime() + File.separator + random() +".png";
		String completeImagePath = System.getProperty("user.dir") + File.separator + imagePath;
		//copiar la screenshot al file path
		log.info("Attempting to copy Screenshot file to path");
		try {
			FileUtils.copyFile(file, new File(imagePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("Failed to copy the screenshot file to path: "+ e.getMessage());

		}
		log.info("Screenshot file copied");


	  }
	  
	  
	  @AfterTest
	  public void afterTest() {
		  //3.5 aqui cerramos el driver
			log.info("Quitting driver");
		  driver.quit();
	  }
	  
	  @BeforeMethod
	  public void beforeMethod() {
			log.info("start of recording");
		System.out.println("start of recording");
		((CanRecordScreen)driver).startRecordingScreen();

	  }
	  
	  @AfterMethod
	  public void afterMethod(ITestResult result) {
		String media = ((CanRecordScreen)driver).stopRecordingScreen();
		Map<String,String> params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		String dir = "Videos" + File.separator + params.get("platformName") +"_"+ params.get("deviceName") + File.separator + dateTime + File.separator + result.getTestClass().getRealClass().getSimpleName();
		File videoDir = new File(dir);
		if(!videoDir.exists()){
			videoDir.mkdirs();
		}
		try {
			FileOutputStream stream = new FileOutputStream(videoDir + File.separator + result.getName() + ".mp4");
			stream.write(Base64.decodeBase64(media));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			log.error("Video File not found: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			log.error("An error occured when trying to copy video file: " + e.getMessage());
		}
		System.out.println("end of recording, video saved");
		log.info("end of recording, video saved");

		  
	  }
	  
	  
	  
	


}
