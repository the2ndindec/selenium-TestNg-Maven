package com.po.testScript;

import org.testng.annotations.Test;

import commonObject.UseBrowser;
import pageObject.LoginPageObj;

import org.testng.annotations.BeforeTest;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

public class WindowsSwitch {
	public WebDriver driver;
	UseBrowser ubrowser = new UseBrowser();
	LoginPageObj hPage = new LoginPageObj();

	@Test
	public void switchhandl() throws Exception {
		driver = ubrowser.setupFireDriver("https://www.baidu.com/");
		
		String searchHandle = driver.getWindowHandle();
		System.out.println("baidu search handle : " + searchHandle);

		// 获取百度新闻的连接，然后利用js打开一个新的窗口
		String href = hPage.getNewsLink(driver);
		System.out.println("the link of news is : " + href);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.open('" + href + "')");

		Thread.sleep(3000);
		Set<String> handles = driver.getWindowHandles();
		Iterator iterator = handles.iterator();
		Iterator iterator2 = handles.iterator();
		
		//进入百度新闻窗口,并获取title验证
		while (iterator.hasNext()) {
			String hString = (String) iterator.next();
			if (hString != searchHandle ) {
				driver.switchTo().window(hString);
				if (driver.getTitle().contains("百度新闻")) {
					System.out.println("switch to news page successfully");
					break;
				}else {
					continue;
				}
			}
		}
		//返回百度首页
		while (iterator2.hasNext()) {
			String hString = (String) iterator2.next();
			if (searchHandle.equals(hString)) {
				driver.switchTo().window(hString);
				if(driver.getTitle().contains("百度一下")){
					System.out.println("switch to search page successfully");
					break;
				}else {
					continue;
				}
			}
		}
	}

	@BeforeTest
	public void beforeTest() {
		System.out.println("Before Test ==");
	}

	@AfterTest
	public void afterTest() {
		System.out.println("After test ==");
		ubrowser.teardownBrowse();
	}

}
