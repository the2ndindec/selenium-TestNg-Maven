package com.test.utils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;

/**
 * 
 * @author Administrator
 * @date: 2017年6月28日
 * @Description: TODO包装所有selenium的操作以及通用方法，简化用例中代码量
 */
public class SeleniumUtil {

	// 使用Log4j，获取日志记录器，这个记录器将负责控制日志信息
	public static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public ITestResult it = null;
	public WebDriver driver;
	public WebDriver windowDriver;
	public int invalidLinksCount;

	// 启动浏览器并打开页面
	public void launchBrowser(String browserName, ITestContext context, String webUrl, int timeOut) {
		// TODO Auto-generated method stub
		SelectBrowser select = new SelectBrowser();
		driver = select.selectExplorerByName(browserName, context);
		try {
			maxWindow(browserName);
			waitForPageLoading(timeOut);
			get(webUrl);
		} catch (TimeoutException e) {
			// TODO: handle exception
			logger.warn("注意：页面没有完全加载出来，刷新重试！！");
			refresh();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String status = (String) js.executeScript("return document.readyState");
			logger.info("打印状态：" + status);
		}
	}

	// 刷新方法包装
	public void refresh() {
		// TODO Auto-generated method stub
		driver.navigate().refresh();
		logger.info("页面刷新成功!");
	}

	// close方法包装
	public void closed() {
		driver.close();
	}

	// 退出方法
	public void quit() {
		driver.quit();
	}

	// get方法包装
	public void get(String url) {
		// TODO Auto-generated method stub
		driver.get(url);
		logger.info("打开测试页面:[" + url + "]");
	}

	// 后退方法
	public void back() {
		driver.navigate().back();
	}

	// 前进方法
	public void forward() {
		driver.navigate().forward();
	}

	// 获得页面的标题
	public String getTitle() {
		return driver.getTitle();
	}

	// 等待alert出现
	public Alert switchToPromptedAlertAfterWait(long waitMillisecondsForAlert) throws NoAlertPresentException {
		final int ONE_ROUND_WAIT = 200;
		NoAlertPresentException lastException = null;
		long endTime = System.currentTimeMillis() + waitMillisecondsForAlert;
		for (int i = 0; i < waitMillisecondsForAlert; i += ONE_ROUND_WAIT) {
			try {
				Alert alert = driver.switchTo().alert();
				return alert;
			} catch (NoAlertPresentException e) {
				// TODO: handle exception
				lastException = e;
			}
			pause(ONE_ROUND_WAIT);
			if (System.currentTimeMillis() > endTime) {
				break;
			}
		}
		throw lastException;
	}

	/*
	 * public void loginOnWinGUI(String username, String password, String url) {
	 * driver.get(username + ":" + password + "@" + url); }
	 */
	// 查找元素---------------------
	// 查找元素的方法 element
	public WebElement findElementBy(By by) {
		return driver.findElement(by);
	}

	// 查找元素的方法 elements
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	// 判断元素状态------------------
	/**
	 * 判断文本是不是和需求要求的文本一致
	 * 
	 * @param
	 */
	public void isTextCorrect(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			// TODO: handle exception
			logger.error("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
			Assert.fail("期望的文字是 [" + expected + "] 但是找到了 [" + actual + "]");
		}
		logger.info("找到了期望的文字: [" + expected + "]");
	}

	/**
	 * 判断编辑框是不是可编辑
	 * 
	 * @param element
	 */
	public void isInputEdit(WebElement element) {

	}

	// 检查元素是否可用
	public boolean isEnabled(WebElement element) {
		boolean isEnable = false;
		if (element.isEnabled()) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is enabled");
			isEnable = true;
		} else if (element.isDisplayed() == false) {
			logger.warn("The element: [" + getLocatorByElement(element, ">") + "] is not enable");
			isEnable = false;
		}
		return isEnable;
	}

	// 检查元素是否显示
	public boolean idDisplayed(WebElement element) {
		boolean isDisplay = false;
		if (element.isDisplayed()) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is displayed");
			isDisplay = true;
		} else if (element.isDisplayed() == false) {
			logger.warn("The element: [" + getLocatorByElement(element, ">") + "] is not displayed");
			isDisplay = false;
		}
		return isDisplay;
	}

	// 检查元素是不是存在
	public boolean isElementExist(By byElement) {
		try {
			findElementBy(byElement);
			return true;
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return false;
		}
	}

	// 检查元素是否被勾选
	public boolean isSelected(WebElement element) {
		boolean flag = false;
		if (element.isSelected() == true) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is selected");
			flag = true;
		} else if (element.isSelected() == false) {
			logger.info("The element: [" + getLocatorByElement(element, ">") + "] is not selected");
			flag = false;
		}
		return flag;
	}

	// 判断实际文本时候包含期望文本
	public void isContains(String actual, String expect) {
		try {
			Assert.assertTrue(actual.contains(expect));
		} catch (AssertionError e) {
			// TODO: handle exception
			logger.error("The [" + actual + "] is not contains [" + expect + "]");
			Assert.fail("The [" + actual + "] is not contains [" + expect + "]");
		}
		logger.info("The [" + actual + "] is contains [" + expect + "]");
	}

	// 在给定的时间内去查找元素，如果没找到则超时，抛出异常
	public void waitForElementToLoad(int timeOut, final By By) {
		logger.info("开始查找元素[" + By + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(By);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			// TODO: handle exception
			logger.error("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");
			Assert.fail("超时!! " + timeOut + " 秒之后还没找到元素 [" + By + "]");
		}
		logger.info("找到了元素 [" + By + "]");
	}

	// 获得元素的文本
	public String getText(By by) {
		return driver.findElement(by).getText().trim();
	}

	// 获得当前select选择的值
	public List<WebElement> getCurrentSelectValue(By by) {
		List<WebElement> options = null;
		Select select = new Select(driver.findElement(by));
		options = select.getAllSelectedOptions();
		return options;
	}

	// 获得输入框的值 这个方法是针对某些input输入框 没有value属性，但是又想取得input的 值得方法
	public String getInputValue(String chose, String choseValue) {
		String value = null;
		switch (chose.toLowerCase()) {
		case "name":
			// 把JS执行的值 返回出去
			String jsName = "return document.getElementsByName('" + choseValue + "')[0].value;";
			value = (String) ((JavascriptExecutor) driver).executeScript(jsName);
			break;

		case "id":
			String jsId = "return document.getElementById('" + choseValue + "').value;";
			value = (String) ((JavascriptExecutor) driver).executeScript(jsId);
			break;

		default:
			logger.error("未定义的chose:" + chose);
			Assert.fail("未定义的chose:" + chose);
		}
		return value;
	}

	// 获得CSS value
	public String getCSSValue(WebElement element, String key) {
		return element.getCssValue(key);
	}

	// 获得元素 属性的文本
	public String getAttributeText(By by, String attribute) {
		return driver.findElement(by).getAttribute(attribute).trim();
	}

	// 根据元素来获取此元素的定位值
	private String getLocatorByElement(WebElement element, String string) {
		// TODO Auto-generated method stub
		String text = element.toString();
		String expect = null;
		try {
			expect = text.substring(text.indexOf(string) + 1, text.length() - 1);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("failed to find the string [" + string + "]");
		}
		return expect;
	}

	// 暂停当前用例的执行，暂停的时间为：sleepTime
	public void pause(int sleepTime) {
		// TODO Auto-generated method stub
		if (sleepTime <= 0) {
			return;
		}
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	// 等待页面加载.
	public void waitForPageLoading(int pageLoadTime) {
		// TODO Auto-generated method stub
		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
	}

	// 最大化浏览器操作
	public void maxWindow(String browserName) {
		// TODO Auto-generated method stub
		logger.info("最大化浏览器:" + browserName);
		driver.manage().window().maximize();
	}

	// 点击操作
	public void click(By by) {
		try {
			clickTheClickable(by, System.currentTimeMillis(), 2500);
		} catch (StaleElementReferenceException e) {
			// TODO: handle exception
			logger.error("点击的元素:[" + by + "] 不存在!");
			Assert.fail("点击的元素:[" + by + "] 不存在!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Failed to click element [" + by + "]");
			Assert.fail("Failed to click element [" + by + "]", e);
		}
		logger.info("点击元素 [" + by + "]");
	}

	// 清除操作
	public void clear(By by) {
		try {
			findElementBy(by).clear();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("清除元素 [" + by + "] 上的内容失败!");
		}
		logger.info("清除元素 [" + by + "]上的内容");
	}

	// 向输入框输入内容
	public void type(By by, String string) {
		try {
			findElementBy(by).sendKeys(string);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("输入 [" + string + "] 到 元素[" + by + "]失败");
			Assert.fail("输入 [" + string + "] 到 元素[" + by + "]失败");
		}
		logger.info("输入：[" + string + "] 到 [" + by + "]");
	}
	
	/**
	 * 模拟键盘操作的,比如Ctrl+A,Ctrl+C等
	 * @param element- 要被操作的元素 
	 * @param key- 键盘上的功能键 比如ctrl ,alt等
	 * @param keyword- 键盘上的字母
	 */
	public void pressKeysOnKeyboard(WebElement element, Keys key, String keyword) {
		element.sendKeys(Keys.chord(key,keyword));
	}
	
	// 切换frame - 根据String类型
	public void inFrame(String frameId) {
		driver.switchTo().frame(frameId);
	}
	// 切换frame - 根据frame在当前页面中的顺序来定位
	public void inFrame(int frameNum) {
		driver.switchTo().frame(frameNum);
	}
	// 切换frame - 根据页面元素定位
	public void switchFrame(WebElement element) {
		try {
			logger.info("正在跳进frame:[" + getLocatorByElement(element, ">") + "]");
			driver.switchTo().frame(element);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("跳进frame: [" + getLocatorByElement(element, ">") + "] 失败");
			Assert.fail("跳进frame: [" + getLocatorByElement(element, ">") + "] 失败");
		}
		logger.info("进入frame: [" + getLocatorByElement(element, ">") +"]成功 ");
	}
	// 跳出frame
	public void outFrame() {
		driver.switchTo().defaultContent();
	}
	
	// selenium模拟鼠标操作 - 鼠标移动到指定元素
	public void mouseMoveToElement(By by) {
		Actions actions = new Actions(driver).moveToElement(driver.findElement(by));
		actions.perform();
	}
	// selenium模拟鼠标操作 - 鼠标移动到指定元素
	public void mouseMoveToElement(WebElement element){
		Actions actions = new Actions(driver).moveToElement(element);
		actions.perform();
	}
	
	// 不能点击时候重试点击操作
	private void clickTheClickable(By by, long startTime, int timeOut) throws Exception {
		// TODO Auto-generated method stub
		try {
			findElementBy(by).click();
		} catch (Exception e) {
			// TODO: handle exception
			if (System.currentTimeMillis() - startTime > timeOut) {
				logger.warn(by + " is unclickable");
                throw new Exception(e);
			}else {
				Thread.sleep(500);
				logger.warn(by + " is unclickable, try again");
				clickTheClickable(by, startTime, timeOut);
			}
		}
	}

	// 右击操作
	public void rightClick(WebElement element) {
			Actions action = new Actions(driver).contextClick(element);
			action.build().perform();
	}

	// 双击操作
	public void doubleClick(WebElement element) {
			Actions action = new Actions(driver).doubleClick(element);
			action.build().perform();
	}

	// 拖拽操作
	public void dragAndDrop(WebElement sourceElement, WebElement destinationElement) {
		try {
			if (sourceElement.isDisplayed() && destinationElement.isDisplayed()) {
				Actions action = new Actions(driver);
				action.dragAndDrop(sourceElement, destinationElement).build().perform();
			} else {
				System.out.println("Element was not displayed to drag");
			}
		} catch (StaleElementReferenceException e) {
			// TODO: handle exception
			System.out.println("Element with " + sourceElement + "or" + destinationElement
					+ "is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			System.out.println("Element " + sourceElement + "or" + destinationElement + " was not found in DOM "
					+ e.getStackTrace());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Error occurred while performing drag and drop operation " + e.getStackTrace());
		}
	}

	// 使用JavaScriptExecutor执行点击操作
	public void safeJavaScriptClick(WebElement element) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				// System.out.println("使用JavaScript点击");
				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			}
		} catch (StaleElementReferenceException e) {
			// TODO: handle exception
			System.out.println("Element is not attached to the page document " + e.getStackTrace());
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			System.out.println("Element was not found in DOM " + e.getStackTrace());
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Unable to click on element " + e.getStackTrace());
		}
	}

	// 验证链接状态
	public void verifyURLStatus(String URL) {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(URL);

		try {
			HttpResponse response = client.execute(request);
			// 验证响应代码是否为200，不是则增加invalidLink的数量
			if (response.getStatusLine().getStatusCode() != 200) {
				invalidLinksCount++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * 自定义窗口大小,使用Dimension声明: 设置浏览器窗口的大小有下面两个比较常见的用途：<br>
	 * 1、在统一的浏览器大小下运行用例，可以比较容易的跟一些基于图像比对的工具进行结合，提升测试的灵活性及普遍适用性。
	 * 2.在不同的浏览器大小下访问测试站点，对测试页面截图并保存，然后观察或使用图像比对工具对被测页面的前端样式进行评测。
	 * 
	 * @param width
	 * @param height
	 */
	public void setBrowserSize(int width, int height) {
		// TODO Auto-generated method stub
		driver.manage().window().setSize(new Dimension(width, height));
	}

	// 下拉框选择,根据index角标
	public void selectwithDropdownsByIndex(By by, int index) {
		// TODO Auto-generated method stub
		Select select = new Select(driver.findElement(by));
		select.selectByIndex(index);
	}

	// 下拉框选择,根据Value
	public void selectwithDropdownsByValue(By by, String value) {
		// TODO Auto-generated method stub
		Select select = new Select(driver.findElement(by));
		select.selectByValue(value);
	}

	// 下拉框选择,根据文本内容
	public void selectwithDropdownsByVisibleText(By by, String text) {
		// TODO Auto-generated method stub
		Select select = new Select(driver.findElement(by));
		select.deselectByVisibleText(text);
	}

	// 高亮显示页面上的元素
	public void highlightElement(WebDriver driver, WebElement element) {
		//
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("element = arguments[0];" + "original_style =element.getAttribute('style');"
				+ "element.setAttribute('style',original_style + \";" + "background: yellow; border: 2px solidred;\");"
				+ "setTimeout(function(){element.setAttribute('style',original_style);}, 1000);", element);
	}

	// 模拟鼠标悬浮
	public void moveToElement(WebDriver driver, By locator) {
		// TODO Auto-generated method stub
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(locator)).perform();
	}
}
