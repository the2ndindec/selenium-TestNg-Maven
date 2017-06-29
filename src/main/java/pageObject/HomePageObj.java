package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.test.pages.HomePageElementPath;

public class HomePageObj extends HomePageElementPath {
	
	//µã»÷Ð´ÐÅ°´Å¥
	public void click_sendMail(WebDriver driver){
		driver.findElement(By.xpath(btn_sendEmail_xpath)).click();
	}
	
	
}
