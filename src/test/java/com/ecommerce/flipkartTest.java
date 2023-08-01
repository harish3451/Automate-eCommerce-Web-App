package com.ecommerce;

import static org.testng.Assert.assertEquals;

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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.google.common.io.Files;

public class flipkartTest {
	
	WebDriver driver;
	WebDriver driver1;
	WebDriver driver2;
	static int counter = 1;
	public static void takeScreenShot(WebDriver driver) throws WebDriverException, IOException{
			
			TakesScreenshot screenShot = (TakesScreenshot) driver;
			Files.copy(screenShot.getScreenshotAs(OutputType.FILE),new File("F://tmp/automateWebsite/img"+ counter++ +".jpg"));
		}
  @Test(groups = "flipKart")
  public void loadtime() throws WebDriverException, IOException {
	  System.out.println("\n===================================================\nPageLoad time\n");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time ="+startTime);
		// Wait for the page to load completely
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		long endTime = System.currentTimeMillis();
		System.out.println("end time ="+endTime);
		System.out.println("load time ="+ (endTime-startTime));
		System.out.println("\n===================================================");
		takeScreenShot(driver);
  }
  
  @Test(groups = "flipKart")
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
  
  @Test(groups = "flipKart")
  public void imageVisiblity() {
	  List<WebElement> images = driver.findElements(By.tagName("img"));
		int WebHeight = driver.manage().window().getSize().getHeight();
		System.out.println("\n===================================================\nImages\n\n");
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
		System.out.println("\n===================================================");
  }
  
  @Test(groups = "flipKart")
  public void scrollFeature() throws InterruptedException, WebDriverException, IOException {
	  System.out.println("\n===================================================");
	  	WebElement body = driver.findElement(By.tagName("body"));
	  	System.out.println(body.getLocation());
	  	int tabHeight=driver.manage().window().getSize().getHeight();
	  	int contentHeight=body.getSize().height;
		System.out.println("windows tab height ="+ tabHeight);
		System.out.println("height of dody content ="+  contentHeight);
		int different = contentHeight-tabHeight;
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertTrue(different>0);
		System.out.println("This page has scroll feature");
		Thread.sleep(2000);
		takeScreenShot(driver);
		System.out.println("\n===================================================");
  }
  
  @Test(groups = "flipKart")
  public void scrollToEnd() throws WebDriverException, IOException {
	  System.out.println("\n===================================================");
	  WebElement body = driver.findElement(By.tagName("body"));
	  body.sendKeys(Keys.END);
	  takeScreenShot(driver);
  }
  
  @Test(groups = "differentBrowsers", dependsOnGroups = "flipKart")
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
	  
	  assertEquals(screenSize.get("chrome"), screenSize.get("Firefox"));
	  assertEquals(screenSize.get("Firefox"),screenSize.get("Edge"));
	  System.out.println("\n===================================================");
  }
  
  
  
  

@BeforeSuite
  public void beforeSuite() {
	  driver = new ChromeDriver();
	  driver1 = new FirefoxDriver();
	  driver2 = new EdgeDriver();
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
