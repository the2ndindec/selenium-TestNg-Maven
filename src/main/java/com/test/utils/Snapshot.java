package com.test.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Snapshot {
	public static void snapshot(TakesScreenshot driver, String filename) {
		// TODO Auto-generated method stub
		// String currentPath = System.getProperty("D:\\sysdoc\\snapshots");

		try {
			File scrFile = driver.getScreenshotAs(OutputType.FILE);
			//  π”√jpg∏Ò Ω±£¥ÊΩÿÕº
			FileUtils.copyFile(scrFile, new File("D:\\sysdoc\\snapshots" + "\\" + filename + "_" + curTime() + ".jpg"));
			System.out.println("ΩÿÕº∑≈÷√Œª÷√:" + "D:\\sysdoc\\snapshots" + "£¨ΩÿÕº√˚≥∆£∫" + filename + "_" + curTime() + ".jpg");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("ΩÿÕº ß∞‹");
			e.printStackTrace();
		}
	}

	private static String curTime() {
		// TODO Auto-generated method stub
		SimpleDateFormat curTime = new SimpleDateFormat("yyyyMMdd_HHmmss");
		return curTime.format(new Date());
	}
}
