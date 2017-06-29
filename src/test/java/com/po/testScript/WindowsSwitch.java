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

		// ��ȡ�ٶ����ŵ����ӣ�Ȼ������js��һ���µĴ���
		String href = hPage.getNewsLink(driver);
		System.out.println("the link of news is : " + href);

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("window.open('" + href + "')");

		Thread.sleep(3000);
		Set<String> handles = driver.getWindowHandles();
		Iterator iterator = handles.iterator();
		Iterator iterator2 = handles.iterator();
		
		//����ٶ����Ŵ���,����ȡtitle��֤
		while (iterator.hasNext()) {
			String hString = (String) iterator.next();
			if (hString != searchHandle ) {
				driver.switchTo().window(hString);
				if (driver.getTitle().contains("�ٶ�����")) {
					System.out.println("switch to news page successfully");
					break;
				}else {
					continue;
				}
			}
		}
		//���ذٶ���ҳ
		while (iterator2.hasNext()) {
			String hString = (String) iterator2.next();
			if (searchHandle.equals(hString)) {
				driver.switchTo().window(hString);
				if(driver.getTitle().contains("�ٶ�һ��")){
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
