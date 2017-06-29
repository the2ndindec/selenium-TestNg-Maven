package commonObject;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

//¹«¹²Àà,²Ù×÷ä¯ÀÀÆ÷Ïà¹ØµÄ

public class UseBrowser {
	public WebDriver driver;

	// Æô¶¯¹È¸èä¯ÀÀÆ÷
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

	// Æô¶¯»ðºüä¯ÀÀÆ÷
	public WebDriver setupFireDriver(String test_url) {
		driver = new FirefoxDriver();
		driver.get(test_url);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		return driver;
	}

	// ¹Ø±Õä¯ÀÀÆ÷
	public void teardownBrowse() {
		driver.quit();
	}

}
