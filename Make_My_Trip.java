package com.in.MakeMyTrip_Website;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class Make_My_Trip {
	@Test   //using testNG to check the test result
	public void checkPrice() throws InterruptedException {
		double expPrice=5000;
		double flightPrice = getFirstFlightPrice();
		boolean flag = (flightPrice != 0.0 && flightPrice < expPrice) ? true : false;
		Assert.assertEquals(flag, true);
	
	}

	public double getFirstFlightPrice() throws InterruptedException {
		double firstFlightPrice = 0.0;
		WebDriver driver=new ChromeDriver();
		driver.manage().window().maximize();        // maximize the page
		driver.get("https://www.makemytrip.com");   //opening make my trip web page
		WebElement oneway=driver.findElement(By.xpath(".//label[@class='label_text flight-trip-type']"));  // click on oneway trip
		oneway.click();

		//selecting  banglore as start point
		WebElement webelement = driver.findElement(By.xpath(".//div/section/div/div/input[@id='hp-widget__sfrom']"));
		webelement.clear();
		Thread.sleep(2000);
		webelement.sendKeys("Ban");
		Thread.sleep(2000);
		webelement.sendKeys(Keys.RETURN); //enter on bengaluru location

		//selection Chennai as destination
		WebElement webelement1 = driver.findElement(By.xpath(".//div/section/div/div/input[@id='hp-widget__sTo']"));
		webelement1.clear();
		Thread.sleep(2000);
		webelement1.sendKeys("Che");
		Thread.sleep(2000);
		webelement1.sendKeys(Keys.RETURN);    //enter on chennai location
		Thread.sleep(2000);

		//selecting date
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, 1);
		Thread.sleep(2000);

		if (driver.findElements(By.xpath("//div[contains(@class,'dateFilter')][1]//td[contains(@class,'ui-datepicker-today') or contains(@class,'ui-datepicker-current')]/following-sibling::td[1]/a")).size() != 0) {
			boolean result = false;
			do {
				try {
					driver.findElement(By.xpath("//div[contains(@class,'dateFilter')][1]//td[contains(@class,'ui-datepicker-today') or contains(@class,'ui-datepicker-current')]/following-sibling::td[1]/a")).click();
				} catch (Exception e) {
					result = true;
				}
			} while (result);

			//when today's date is the last date of the calendar
		} else {

			List<WebElement> firstDate = driver.findElements(By.xpath(
					"//div[contains(@class,'dateFilter')][1]/div[contains(@class,'ui-datepicker-multi')]/div[2]//a[@class='ui-state-default']"));
			for (int i = 0; i < firstDate.size(); i++) {
				//to get the day
				if (firstDate.get(i).getText().trim().equalsIgnoreCase((new SimpleDateFormat("d")).format(c.getTime()))) {
					firstDate.get(i).click();
				}
			}
		}
		// click on search button for the flight
		WebElement search=driver.findElement(By.id("searchBtn"));
		search.click();
		Thread.sleep(5000);
		WebElement firstPriceWebElem = driver.findElement(By.xpath("//*[@id=\"content\"]/div/div[6]/div[5]/div[2]/div[5]/div/div[2]/div[6]/p[1]/span[2]"));
		if (firstPriceWebElem.isDisplayed()) {
			String firstPrice = firstPriceWebElem.getText().replaceAll(",", "");
			firstFlightPrice = Double.valueOf(firstPrice);
			Thread.sleep(10000);
			driver.close();
		}
		return firstFlightPrice;
		
	}
	
}

