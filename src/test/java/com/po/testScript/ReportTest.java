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
			String[] s = { String.format("���ǵڣ�%s��������", i) };
			dataProvider.add(s);
		}
		return dataProvider.iterator();
	}

	@Test(dataProvider = "createData")
	public void dataProviderTest(String s) {
		// ���log���ڱ���������
		Reporter.log("��ȡ��������" + s, true);
		Assert.assertTrue(s.length() > 2, " �ɹ���ʧ�ܣ�");
	}

	@Test
	public void testTrue() {
		Assert.assertTrue(true, "�ɹ���!");
	}

	@Test
	public void testFail() {
		Assert.fail("ʧ�ܿ�!");
	}
}
