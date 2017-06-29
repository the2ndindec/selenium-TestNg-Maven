package com.test.plugins;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import com.test.utils.LogConfiguration;

public class TestngRetry implements IRetryAnalyzer {

	static {
		LogConfiguration.initLog("TestngRetryPage_");
	}
	private static Logger logger = Logger.getLogger(TestngRetry.class);
	private int retryCount = 1;
	private static int maxRetryCount;

	static {
		ConfigReader configReader = ConfigReader.getInstance();
		maxRetryCount = configReader.getRetryCount();
		logger.info("RetryCount=" + maxRetryCount);
		logger.info("SourceDir=" + configReader.getSourceCodeDir());
		logger.info("SourceEncoding=" + configReader.getSourceCodeEncoding());
	}

	public boolean retry(ITestResult result) {
		if (retryCount < maxRetryCount) {
			String message = "Retry for£º [" + result.getName() + "] on class [" + result.getTestClass().getName()
					+ "] retry " + retryCount + " times";
			logger.info(message);
			Reporter.setCurrentTestResult(result);
			Reporter.log("RunCount=" + (retryCount + 1));
			retryCount++;
			return true;
		}
		return false;
	}

	public static int getMaxRetryCount() {
		return maxRetryCount;
	}

	public int getRetryCount() {
		return retryCount;
	}
}
