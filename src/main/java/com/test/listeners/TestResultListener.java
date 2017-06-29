package com.test.listeners;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

public class TestResultListener extends TestListenerAdapter {

	private static Logger logger = Logger.getLogger(TestResultListener.class.getName());
	protected ITestContext testContext = null;
	String browser = null;

	@Override
	public void onStart(ITestContext testContext) {
		// TODO Auto-generated method stub
		this.testContext = testContext;
		browser = String.valueOf(testContext.getCurrentXmlTest().getParameter("browserName"));
		super.onStart(testContext);
	}

	@Override
	public void onTestFailure(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestFailure(tr);
		logger.warn(tr.getName() + "��������ִ��ʧ��!");
		WebDriver webDriver = (WebDriver) testContext.getAttribute("SELENIUM_DRIVER");
		saveScreenShot(tr, webDriver, browser);
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestSkipped(tr);
		WebDriver webDriver = (WebDriver) testContext.getAttribute("SELENIUM_DRIVER");
		logger.warn(tr.getName() + " ������������ĳЩԭ��������");
		saveScreenShot(tr, webDriver, browser);
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		// TODO Auto-generated method stub
		super.onTestSuccess(tr);
		logger.info(tr.getName() + " ��������ִ�гɹ���");
	}

	@Override
	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub
		super.onTestStart(result);
		logger.info(result.getName() + " ����������ʼִ�У�");
	}

	@Override
	public void onFinish(ITestContext testContext) {
		// TODO Auto-generated method stub
		super.onFinish(testContext);
		// ����ɾ��������
		ArrayList<ITestResult> testsToBeRemoved = new ArrayList<ITestResult>();
		// collect all id's from passed test
		Set<Integer> passedTestIds = new HashSet<Integer>();
		for (ITestResult passedTest : testContext.getPassedTests().getAllResults()) {
			logger.info("ִ�гɹ������� = " + passedTest.getName());
			passedTestIds.add(getId(passedTest));
		}

		// Eliminate the repeat methods
		Set<Integer> skipTestIds = new HashSet<Integer>();
		for (ITestResult skipTest : testContext.getSkippedTests().getAllResults()) {
			logger.info("������������ = " + skipTest.getName());
			// id = class + method + dataprovider
			int skipTestId = getId(skipTest);

			if (skipTestIds.contains(skipTestId) || passedTestIds.contains(skipTestId)) {
				testsToBeRemoved.add(skipTest);
			} else {
				skipTestIds.add(skipTestId);
			}
		}

		// Eliminate the repeat failed methods
		Set<Integer> failedTestIds = new HashSet<Integer>();
		for (ITestResult failedTest : testContext.getFailedTests().getAllResults()) {
			logger.info("ִ��ʧ�ܵ����� = " + failedTest.getName());
			// id = class + method + dataprovider
			int failedTestId = getId(failedTest);

			// if we saw this test as a failed test before we mark as to be
			// deleted
			// or delete this failed test if there is at least one passed
			// version
			if (failedTestIds.contains(failedTestId) || passedTestIds.contains(failedTestId)
					|| skipTestIds.contains(failedTestId)) {
				testsToBeRemoved.add(failedTest);
			} else {
				failedTestIds.add(failedTestId);
			}
		}

		// finally delete all tests that are marked
		for (Iterator<ITestResult> iterator = testContext.getFailedTests().getAllResults().iterator(); iterator
				.hasNext();) {
			ITestResult testResult = iterator.next();
			if (testsToBeRemoved.contains(testResult)) {
				logger.info("�Ƴ��ظ�ʧ�ܵ����� = " + testResult.getName());
				iterator.remove();
			}
		}
	}

	private Integer getId(ITestResult result) {
		// TODO Auto-generated method stub
		int id = result.getTestClass().getName().hashCode();
		id = id + result.getMethod().getMethodName().hashCode();
		id = id + (result.getParameters() != null ? Arrays.hashCode(result.getParameters()) : 0);
		return id;
	}

	private void saveScreenShot(ITestResult tr, WebDriver driver, String browser) {
		// TODO Auto-generated method stub
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		String mDateTime = formatter.format(new Date());
		String fileName = mDateTime + "_" + tr.getName();
		String filePath = "";
		try {
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			filePath = "D:\\sysdoc\\snapshots" + "\\" + fileName + ".jpg";
			File destFile = new File(filePath);
			FileUtils.copyFile(screenshot, destFile);
			logger.info("[" + fileName + "] ��ͼ�ɹ��������ڣ�" + "[ " + filePath + " ]");
		} catch (Exception e) {
			// TODO: handle exception
			filePath = "[" + fileName + "]" + " ,��ͼʧ�ܣ�ԭ��" + e.getMessage();
			logger.error(filePath);
		}

		if (!"".equals(filePath)) {
			Reporter.setCurrentTestResult(tr);
			Reporter.log(filePath);
			// �ѽ�ͼд�뵽Html�����з���鿴
			Reporter.log("<img src=\"../../" + filePath + "\"/>");

		}
	}
}
