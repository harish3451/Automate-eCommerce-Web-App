package com.ecommerce;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertTrue;

import java.awt.Scrollbar;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.io.Files;

import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;

public class flipkartTest {
	
	WebDriver driver;
	WebDriver driver1;
	WebDriver driver2;
	static int counter = 1;
	
	static long startTime;
	static long endTime;
	
	
	public static void takeScreenShot(WebDriver driver) throws WebDriverException, IOException{
			
			TakesScreenshot screenShot = (TakesScreenshot) driver;
			Files.copy(screenShot.getScreenshotAs(OutputType.FILE),new File("F://tmp/automateWebsite/img"+ counter++ +".jpg"));
		}
  @Test(groups = "flipKart")
  public void loadtime() throws WebDriverException, IOException {
	  System.out.println("\n===================================================\nPageLoad time\n");
		
		System.out.println("Start time ="+startTime);
		// Wait for the page to load completely
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		endTime = System.currentTimeMillis();
		System.out.println("end time ="+endTime);
		System.out.println("load time ="+ (endTime-startTime)+" milli second");
		System.out.println("===================================================");
		takeScreenShot(driver);
  }
  
  @Test(groups = "test1")
  public void searchProduct() throws InterruptedException, WebDriverException, IOException {
	  WebElement x = driver.findElement(By.cssSelector("body > div._2Sn47c > div > div > button"));
		x.click();
		
		WebElement mobile = driver.findElement(By.cssSelector("#container > div > div._331-kn._2tvxW > div > div > div:nth-child(2) > a > div.xtXmba"));
		mobile.click();
		Thread.sleep(2000);
		takeScreenShot(driver);

		WebElement search = driver.findElement(By.cssSelector("#container > div > div._1kfTjk > div._1rH5Jn > div._2Xfa2_ > div._1cmsER > form > div > div > input"));
		search.sendKeys("iPhone 13"+Keys.ENTER);
		Thread.sleep(2000);
		takeScreenShot(driver);
  }
  
  @Test(groups = "test2", dependsOnGroups = "test1")
  public void imageVisiblity() {
	  List<WebElement> images = driver.findElements(By.tagName("img"));
		int WebHeight = driver.manage().window().getSize().getHeight();
		System.out.println("===================================================\nImages\n");
		for(WebElement img:images) {
			int imageLocation = img.getLocation().getY();
			
			if(imageLocation < WebHeight && imageLocation>=0) {
				if(img.isDisplayed()) {
					System.out.println("Image is loaded and displayed = "+img.getAttribute("src"));
				}
				else {
					System.out.println("Image is not displayed ="+img.getAttribute("src"));
				}
				
			}
			else {
				System.out.println("Image is out of screen height = "+img.getAttribute("src"));
			}
		}
		System.out.println("===================================================");
  }
  
  @Test(groups = "test3", dependsOnGroups = "test2")
  public void scrollFeature() throws InterruptedException, WebDriverException, IOException {
	  System.out.println("\n===================================================");
	  	WebElement body = driver.findElement(By.tagName("body"));
	  	int tabHeight=driver.manage().window().getSize().getHeight();
	  	int contentHeight=body.getSize().height;
		System.out.println("windows tab height ="+ tabHeight);
		System.out.println("height of dody content ="+  contentHeight);
		int different = contentHeight-tabHeight;
		SoftAssert softAssert = new SoftAssert();
		assertTrue(different>0);
		System.out.println("This page has scroll feature");
		Thread.sleep(2000);
		takeScreenShot(driver);
		System.out.println("===================================================");
  }
  
  @Test(groups="test4", dependsOnGroups = "test3")
  public void frequencyOfContentReload() throws InterruptedException {
	  Actions actions = new Actions(driver);
	  long start = System.currentTimeMillis();
	  WebElement nextPage = driver.findElement(By.cssSelector("#container > div > div._36fx1h._6t1WkM._3HqJxg > div._1YokD2._2GoDe3 > div:nth-child(2) > div:nth-child(26) > div > div > nav > a:nth-child(3)"));
	  
	  actions.scrollToElement(nextPage).perform();
	  nextPage.click();
	  driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
	  long end = System.currentTimeMillis();
	  System.out.println("Content reload frequency =" +(end-start)+" milli second");
	  
	  Thread.sleep(10000);
  }
  
  @Test(groups = "test5", dependsOnGroups = "test4")
  public void scrollToEnd() throws WebDriverException, IOException {
	  WebElement body = driver.findElement(By.tagName("body"));
	  body.sendKeys(Keys.END);
	  takeScreenShot(driver);
  }
  
  @Test(groups = "test6", dependsOnGroups = "test5")
  public void TestOnDifferentBrowser() throws WebDriverException, IOException {
	  System.out.println("\n===================================================");
	  driver.get("https://www.flipkart.com/");
	  driver1.get("https://www.flipkart.com/");
	  driver2.get("https://www.flipkart.com/");
	  takeScreenShot(driver);
	  takeScreenShot(driver1);
	  takeScreenShot(driver2);
	  Map<String, Dimension> screenSize = new HashMap<>();
	  screenSize.put("chrome",driver.manage().window().getSize());
	  screenSize.put("Firefox",driver1.manage().window().getSize());
	  screenSize.put("Edge",driver2.manage().window().getSize());
	  
	  System.out.println("screen size of chrome drowser = "+screenSize.get("chrome"));
	  System.out.println("screen size of fireFox drowser = "+screenSize.get("Firefox"));
	  System.out.println("screen size of edge drowser = "+screenSize.get("Edge"));
	  
	  assertNotEquals(screenSize.get("chrome"), screenSize.get("Firefox"));
	  assertNotEquals(screenSize.get("Firefox"),screenSize.get("Edge"));
	  System.out.println("===================================================");
  }
  
  
  
  
  
@BeforeSuite
  public void beforeSuite() {
	  driver = new ChromeDriver();
	  driver1 = new FirefoxDriver();
	  driver2 = new EdgeDriver();
	  startTime = System.currentTimeMillis();
		driver.get("https://www.flipkart.com/");
		driver.manage().window().maximize();
  }
  
  @AfterSuite
  public void afterSuite() {
	  driver.quit();
	  driver1.quit();
	  driver2.quit();
  }
  
}
