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
 * @date: 2017��6��28��
 * @Description: TODO��װ����selenium�Ĳ����Լ�ͨ�÷������������д�����
 */
public class SeleniumUtil {

	// ʹ��Log4j����ȡ��־��¼���������¼�������������־��Ϣ
	public static Logger logger = Logger.getLogger(SeleniumUtil.class.getName());
	public ITestResult it = null;
	public WebDriver driver;
	public WebDriver windowDriver;
	public int invalidLinksCount;

	// �������������ҳ��
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
			logger.warn("ע�⣺ҳ��û����ȫ���س�����ˢ�����ԣ���");
			refresh();
			JavascriptExecutor js = (JavascriptExecutor) driver;
			String status = (String) js.executeScript("return document.readyState");
			logger.info("��ӡ״̬��" + status);
		}
	}

	// ˢ�·�����װ
	public void refresh() {
		// TODO Auto-generated method stub
		driver.navigate().refresh();
		logger.info("ҳ��ˢ�³ɹ�!");
	}

	// close������װ
	public void closed() {
		driver.close();
	}

	// �˳�����
	public void quit() {
		driver.quit();
	}

	// get������װ
	public void get(String url) {
		// TODO Auto-generated method stub
		driver.get(url);
		logger.info("�򿪲���ҳ��:[" + url + "]");
	}

	// ���˷���
	public void back() {
		driver.navigate().back();
	}

	// ǰ������
	public void forward() {
		driver.navigate().forward();
	}

	// ���ҳ��ı���
	public String getTitle() {
		return driver.getTitle();
	}

	// �ȴ�alert����
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
	// ����Ԫ��---------------------
	// ����Ԫ�صķ��� element
	public WebElement findElementBy(By by) {
		return driver.findElement(by);
	}

	// ����Ԫ�صķ��� elements
	public List<WebElement> findElements(By by) {
		return driver.findElements(by);
	}

	// �ж�Ԫ��״̬------------------
	/**
	 * �ж��ı��ǲ��Ǻ�����Ҫ����ı�һ��
	 * 
	 * @param
	 */
	public void isTextCorrect(String actual, String expected) {
		try {
			Assert.assertEquals(actual, expected);
		} catch (AssertionError e) {
			// TODO: handle exception
			logger.error("������������ [" + expected + "] �����ҵ��� [" + actual + "]");
			Assert.fail("������������ [" + expected + "] �����ҵ��� [" + actual + "]");
		}
		logger.info("�ҵ�������������: [" + expected + "]");
	}

	/**
	 * �жϱ༭���ǲ��ǿɱ༭
	 * 
	 * @param element
	 */
	public void isInputEdit(WebElement element) {

	}

	// ���Ԫ���Ƿ����
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

	// ���Ԫ���Ƿ���ʾ
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

	// ���Ԫ���ǲ��Ǵ���
	public boolean isElementExist(By byElement) {
		try {
			findElementBy(byElement);
			return true;
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			return false;
		}
	}

	// ���Ԫ���Ƿ񱻹�ѡ
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

	// �ж�ʵ���ı�ʱ����������ı�
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

	// �ڸ�����ʱ����ȥ����Ԫ�أ����û�ҵ���ʱ���׳��쳣
	public void waitForElementToLoad(int timeOut, final By By) {
		logger.info("��ʼ����Ԫ��[" + By + "]");
		try {
			(new WebDriverWait(driver, timeOut)).until(new ExpectedCondition<Boolean>() {
				public Boolean apply(WebDriver driver) {
					WebElement element = driver.findElement(By);
					return element.isDisplayed();
				}
			});
		} catch (TimeoutException e) {
			// TODO: handle exception
			logger.error("��ʱ!! " + timeOut + " ��֮��û�ҵ�Ԫ�� [" + By + "]");
			Assert.fail("��ʱ!! " + timeOut + " ��֮��û�ҵ�Ԫ�� [" + By + "]");
		}
		logger.info("�ҵ���Ԫ�� [" + By + "]");
	}

	// ���Ԫ�ص��ı�
	public String getText(By by) {
		return driver.findElement(by).getText().trim();
	}

	// ��õ�ǰselectѡ���ֵ
	public List<WebElement> getCurrentSelectValue(By by) {
		List<WebElement> options = null;
		Select select = new Select(driver.findElement(by));
		options = select.getAllSelectedOptions();
		return options;
	}

	// ���������ֵ ������������ĳЩinput����� û��value���ԣ���������ȡ��input�� ֵ�÷���
	public String getInputValue(String chose, String choseValue) {
		String value = null;
		switch (chose.toLowerCase()) {
		case "name":
			// ��JSִ�е�ֵ ���س�ȥ
			String jsName = "return document.getElementsByName('" + choseValue + "')[0].value;";
			value = (String) ((JavascriptExecutor) driver).executeScript(jsName);
			break;

		case "id":
			String jsId = "return document.getElementById('" + choseValue + "').value;";
			value = (String) ((JavascriptExecutor) driver).executeScript(jsId);
			break;

		default:
			logger.error("δ�����chose:" + chose);
			Assert.fail("δ�����chose:" + chose);
		}
		return value;
	}

	// ���CSS value
	public String getCSSValue(WebElement element, String key) {
		return element.getCssValue(key);
	}

	// ���Ԫ�� ���Ե��ı�
	public String getAttributeText(By by, String attribute) {
		return driver.findElement(by).getAttribute(attribute).trim();
	}

	// ����Ԫ������ȡ��Ԫ�صĶ�λֵ
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

	// ��ͣ��ǰ������ִ�У���ͣ��ʱ��Ϊ��sleepTime
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

	// �ȴ�ҳ�����.
	public void waitForPageLoading(int pageLoadTime) {
		// TODO Auto-generated method stub
		driver.manage().timeouts().pageLoadTimeout(pageLoadTime, TimeUnit.SECONDS);
	}

	// ������������
	public void maxWindow(String browserName) {
		// TODO Auto-generated method stub
		logger.info("��������:" + browserName);
		driver.manage().window().maximize();
	}

	// �������
	public void click(By by) {
		try {
			clickTheClickable(by, System.currentTimeMillis(), 2500);
		} catch (StaleElementReferenceException e) {
			// TODO: handle exception
			logger.error("�����Ԫ��:[" + by + "] ������!");
			Assert.fail("�����Ԫ��:[" + by + "] ������!");
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("Failed to click element [" + by + "]");
			Assert.fail("Failed to click element [" + by + "]", e);
		}
		logger.info("���Ԫ�� [" + by + "]");
	}

	// �������
	public void clear(By by) {
		try {
			findElementBy(by).clear();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("���Ԫ�� [" + by + "] �ϵ�����ʧ��!");
		}
		logger.info("���Ԫ�� [" + by + "]�ϵ�����");
	}

	// ���������������
	public void type(By by, String string) {
		try {
			findElementBy(by).sendKeys(string);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error("���� [" + string + "] �� Ԫ��[" + by + "]ʧ��");
			Assert.fail("���� [" + string + "] �� Ԫ��[" + by + "]ʧ��");
		}
		logger.info("���룺[" + string + "] �� [" + by + "]");
	}
	
	/**
	 * ģ����̲�����,����Ctrl+A,Ctrl+C��
	 * @param element- Ҫ��������Ԫ�� 
	 * @param key- �����ϵĹ��ܼ� ����ctrl ,alt��
	 * @param keyword- �����ϵ���ĸ
	 */
	public void pressKeysOnKeyboard(WebElement element, Keys key, String keyword) {
		element.sendKeys(Keys.chord(key,keyword));
	}
	
	// �л�frame - ����String����
	public void inFrame(String frameId) {
		driver.switchTo().frame(frameId);
	}
	// �л�frame - ����frame�ڵ�ǰҳ���е�˳������λ
	public void inFrame(int frameNum) {
		driver.switchTo().frame(frameNum);
	}
	// �л�frame - ����ҳ��Ԫ�ض�λ
	public void switchFrame(WebElement element) {
		try {
			logger.info("��������frame:[" + getLocatorByElement(element, ">") + "]");
			driver.switchTo().frame(element);
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("����frame: [" + getLocatorByElement(element, ">") + "] ʧ��");
			Assert.fail("����frame: [" + getLocatorByElement(element, ">") + "] ʧ��");
		}
		logger.info("����frame: [" + getLocatorByElement(element, ">") +"]�ɹ� ");
	}
	// ����frame
	public void outFrame() {
		driver.switchTo().defaultContent();
	}
	
	// seleniumģ�������� - ����ƶ���ָ��Ԫ��
	public void mouseMoveToElement(By by) {
		Actions actions = new Actions(driver).moveToElement(driver.findElement(by));
		actions.perform();
	}
	// seleniumģ�������� - ����ƶ���ָ��Ԫ��
	public void mouseMoveToElement(WebElement element){
		Actions actions = new Actions(driver).moveToElement(element);
		actions.perform();
	}
	
	// ���ܵ��ʱ�����Ե������
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

	// �һ�����
	public void rightClick(WebElement element) {
			Actions action = new Actions(driver).contextClick(element);
			action.build().perform();
	}

	// ˫������
	public void doubleClick(WebElement element) {
			Actions action = new Actions(driver).doubleClick(element);
			action.build().perform();
	}

	// ��ק����
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

	// ʹ��JavaScriptExecutorִ�е������
	public void safeJavaScriptClick(WebElement element) throws Exception {
		// TODO Auto-generated method stub
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				// System.out.println("ʹ��JavaScript���");
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

	// ��֤����״̬
	public void verifyURLStatus(String URL) {

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(URL);

		try {
			HttpResponse response = client.execute(request);
			// ��֤��Ӧ�����Ƿ�Ϊ200������������invalidLink������
			if (response.getStatusLine().getStatusCode() != 200) {
				invalidLinksCount++;
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	/**
	 * �Զ��崰�ڴ�С,ʹ��Dimension����: ������������ڵĴ�С�����������Ƚϳ�������;��<br>
	 * 1����ͳһ���������С���������������ԱȽ����׵ĸ�һЩ����ͼ��ȶԵĹ��߽��н�ϣ��������Ե�����Լ��ձ������ԡ�
	 * 2.�ڲ�ͬ���������С�·��ʲ���վ�㣬�Բ���ҳ���ͼ�����棬Ȼ��۲��ʹ��ͼ��ȶԹ��߶Ա���ҳ���ǰ����ʽ�������⡣
	 * 
	 * @param width
	 * @param height
	 */
	public void setBrowserSize(int width, int height) {
		// TODO Auto-generated method stub
		driver.manage().window().setSize(new Dimension(width, height));
	}

	// ������ѡ��,����index�Ǳ�
	public void selectwithDropdownsByIndex(By by, int index) {
		// TODO Auto-generated method stub
		Select select = new Select(driver.findElement(by));
		select.selectByIndex(index);
	}

	// ������ѡ��,����Value
	public void selectwithDropdownsByValue(By by, String value) {
		// TODO Auto-generated method stub
		Select select = new Select(driver.findElement(by));
		select.selectByValue(value);
	}

	// ������ѡ��,�����ı�����
	public void selectwithDropdownsByVisibleText(By by, String text) {
		// TODO Auto-generated method stub
		Select select = new Select(driver.findElement(by));
		select.deselectByVisibleText(text);
	}

	// ������ʾҳ���ϵ�Ԫ��
	public void highlightElement(WebDriver driver, WebElement element) {
		//
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("element = arguments[0];" + "original_style =element.getAttribute('style');"
				+ "element.setAttribute('style',original_style + \";" + "background: yellow; border: 2px solidred;\");"
				+ "setTimeout(function(){element.setAttribute('style',original_style);}, 1000);", element);
	}

	// ģ���������
	public void moveToElement(WebDriver driver, By locator) {
		// TODO Auto-generated method stub
		Actions builder = new Actions(driver);
		builder.moveToElement(driver.findElement(locator)).perform();
	}
}
