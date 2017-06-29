package commonObject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//������,�����������ص�

public class UseBrowser {
	public WebDriver driver;

	// �����ȸ������
	public WebDriver setupChrome(String test_url) {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
				"D:\\sysdoc\\workspace\\mavenProjectTest\\src\\main\\resources\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.get(test_url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	// ������������
	public WebDriver setupFireDriver(String test_url) {
		driver = new FirefoxDriver();
		driver.get(test_url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	// �ر������
	public void teardownBrowse() {
		driver.quit();
	}

}
