package com.po.testScript;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ReportTest {

	@DataProvider(name = "createData")
	public Iterator<Object[]> createData() {
		List<Object[]> dataProvider = new ArrayList<Object[]>();
		for (int i = 0; i < 2; i++) {
			String[] s = { String.format("我是第（%s）个参数", i) };
			dataProvider.add(s);
		}
		return dataProvider.iterator();
	}

	@Test(dataProvider = "createData")
	public void dataProviderTest(String s) {
		// 输出log会在报告中提现
		Reporter.log("获取到参数：" + s, true);
		Assert.assertTrue(s.length() > 2, " 成功？失败？");
	}

	@Test
	public void testTrue() {
		Assert.assertTrue(true, "成功咯!");
	}

	@Test
	public void testFail() {
		Assert.fail("失败咯!");
	}
}
