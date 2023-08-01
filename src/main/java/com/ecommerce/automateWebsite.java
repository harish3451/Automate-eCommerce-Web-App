package com.ecommerce;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.google.common.io.Files;

public class automateWebsite {
	
	public static int counter =1;

	public static void main(String[] args) throws WebDriverException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		
		driver.get("https://www.flipkart.com/");
		
		
		driver.manage().window().maximize();
	
		//measuring page load time
		
		System.out.println("\n===================================================\nPageLoad time\n\n");
		long startTime = System.currentTimeMillis();
		System.out.println("Start time ="+startTime);
		// Wait for the page to load completely
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		long endTime = System.currentTimeMillis();
		System.out.println("end time ="+endTime);
		System.out.println("load time ="+ (endTime-startTime));
		
		
		
		takeScreenShot(driver);
		
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
		
		//Check if the images are loaded and visible till the screen height only
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
		
		// scroll to end
		WebElement body = driver.findElement(By.tagName("body"));
		System.out.println(driver.manage().window().getSize().getHeight());
		System.out.println("height ="+  body.getSize().height+"\nWidth = "+body.getSize().width);
		body.sendKeys(Keys.END);
		Thread.sleep(2000);
		takeScreenShot(driver);
		driver.quit();
	}

public static void takeScreenShot(WebDriver driver) throws WebDriverException, IOException{
		
		TakesScreenshot screenShot = (TakesScreenshot) driver;
		Files.copy(screenShot.getScreenshotAs(OutputType.FILE),new File("F://tmp/automateWebsite/img"+ counter++ +".jpg"));
	}
}
