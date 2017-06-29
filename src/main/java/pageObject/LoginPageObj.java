package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.test.pages.LoginPageElementPath;

public class LoginPageObj extends LoginPageElementPath {
	
	//切换frame
	public void switchFrame(WebDriver driver){
		driver.switchTo().frame(frame_name_id);
	}

	// 点击用户id输入框
	public void click_idBar(WebDriver driver) {
		driver.findElement(By.id(input_idbox_id)).click();
	}

	// 用户id输入
	public void input_IDBox(WebDriver driver, String idString) {
		driver.findElement(By.name(input_idbox_name)).clear();
		driver.findElement(By.name(input_idbox_name)).sendKeys(idString);
	}

	// 密码输入
	public void input_PsdBox(WebDriver driver,String pwdString){
		driver.findElement(By.name(input_psdbox_name)).clear();
		driver.findElement(By.name(input_psdbox_name)).sendKeys(pwdString);
	}
	
	//点击登录按钮
	public void click_LoginBtn(WebDriver driver) {
		driver.findElement(By.xpath(btn_loginBtn_xpath)).click();
	}

	// 点击新闻链接
	public void click_newsLink(WebDriver driver) {
		// driver.findElement(By.xpath(news_link_xpath)).click();
		driver.findElement(By.name(news_link_name)).click();
	}

	// 获取地址
	public String getNewsLink(WebDriver driver) {
		// String NewsLink =
		// driver.findElement(By.xpath(news_link_xpath)).getAttribute("href");
		String newsLink = driver.findElement(By.name(news_link_name)).getAttribute("href");
		return newsLink;

	}
}
