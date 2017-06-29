package com.test.base;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.test.utils.LogConfiguration;
import com.test.utils.SeleniumUtil;

/**
 * 
 * @author Administrator
 * @date: 2017��6��29��
 * @Description: ���Կ�ʼ �� ���Խ��� �Ĳ���
 */
public class BaseParpare {

	// �����ҳ����־ ��ʼ��
	static Logger logger = Logger.getLogger(BaseParpare.class.getName());
	protected SeleniumUtil seleniumUtil = null;
	// ��ӳ�Ա��������ȡbeforeClass�����context����
	protected ITestContext testContext = null;
	protected String webUrl = "";
	protected int timeOut = 0;

	/**
	 * ������������򿪲���ҳ��
	 */
	@BeforeClass
	public void startTest(ITestContext context) {
		// TODO Auto-generated method stub
		LogConfiguration.initLog(this.getClass().getSimpleName());
		seleniumUtil = new SeleniumUtil();
		this.testContext = context;
		// ��testng.xml�ļ��л�ȡ�����������ֵ
		String browserName = context.getCurrentXmlTest().getParameter("browserName");
		timeOut = Integer.valueOf(context.getCurrentXmlTest().getParameter("timeOut"));
		webUrl = context.getCurrentXmlTest().getParameter("testurl");

		try {
			// ���������launchBrowser���������Լ���������Ҫ�Ǵ��������������Ե�ַ������󻯴���
			seleniumUtil.launchBrowser(browserName, context, webUrl, timeOut);
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("������������������������ǲ��Ǳ��ֶ��رջ�������ԭ��", e);
		}
		// ����һ��testng���������ԣ���driver��������֮�����ʹ��context��ʱȡ������Ҫ���ṩarrow
		// ��ȡdriver����ʹ�õģ���Ϊarrow��ͼ������Ҫһ��driver����
		testContext.setAttribute("SELENIUM_DRIVER", seleniumUtil.driver);
	}

	/**
	 * �������Թر������
	 */
	@AfterClass
	public void endTest() {
		if (seleniumUtil.driver != null) {
			// �˳������
			seleniumUtil.quit();
		} else {
			logger.error("�����driverû�л�ö���,�˳�����ʧ��");
			Assert.fail("�����driverû�л�ö���,�˳�����ʧ��");
		}
	}

}
