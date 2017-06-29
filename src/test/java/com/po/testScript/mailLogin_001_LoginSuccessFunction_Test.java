package com.po.testScript;

import org.testng.annotations.Test;

import com.test.utils.SeleniumUtil;

import commonObject.UseBrowser;
import pageObject.HomePageObj;
import pageObject.LoginPageObj;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import org.testng.annotations.AfterTest;
import org.apache.log4j.Logger;

public class mailLogin_001_LoginSuccessFunction_Test {

	public WebDriver driver;
	Logger ogger = Logger.getLogger("devpinoyLogger");
	LoginPageObj lPage = new LoginPageObj();
	UseBrowser ubrowser = new UseBrowser();
	HomePageObj hPage = new HomePageObj();
	SeleniumUtil pubMe = new SeleniumUtil();

	@Test
	@Parameters("userID")
	public void loginAcc(String userID) throws Exception {
		driver = ubrowser.setupFireDriver("http://mail.163.com/");
		// driver =ubrowser.setupChrome("http://mail.163.com/");
		String strPageTitle = driver.getTitle();
		Assert.assertTrue(strPageTitle.contains("网易"));
		driver.switchTo().frame(driver.findElement(By.id("x-URS-iframe")));
		// Snapshot.snapshot((TakesScreenshot) driver, "登陆界面");
		lPage.input_IDBox(driver, userID);
		lPage.input_PsdBox(driver, "Passw0rd@1");

		// 高亮显示元素
		// WebElement btnElement =
		// driver.findElement(By.xpath(".//*[@id='dologin']"));
		// pubMe.highlightElement(driver,btnElement);

		lPage.click_LoginBtn(driver);
		Thread.sleep(1000);
	}

	@Test(enabled = false)
	public void sendEamil() {
		// Snapshot.snapshot((TakesScreenshot) driver, "邮箱首页");
		hPage.click_sendMail(driver);
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("Before Test ====");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("After test ====");
		ubrowser.teardownBrowse();
	}

}
