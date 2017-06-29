package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.test.pages.LoginPageElementPath;

public class LoginPageObj extends LoginPageElementPath {
	
	//�л�frame
	public void switchFrame(WebDriver driver){
		driver.switchTo().frame(frame_name_id);
	}

	// ����û�id�����
	public void click_idBar(WebDriver driver) {
		driver.findElement(By.id(input_idbox_id)).click();
	}

	// �û�id����
	public void input_IDBox(WebDriver driver, String idString) {
		driver.findElement(By.name(input_idbox_name)).clear();
		driver.findElement(By.name(input_idbox_name)).sendKeys(idString);
	}

	// ��������
	public void input_PsdBox(WebDriver driver,String pwdString){
		driver.findElement(By.name(input_psdbox_name)).clear();
		driver.findElement(By.name(input_psdbox_name)).sendKeys(pwdString);
	}
	
	//�����¼��ť
	public void click_LoginBtn(WebDriver driver) {
		driver.findElement(By.xpath(btn_loginBtn_xpath)).click();
	}

	// �����������
	public void click_newsLink(WebDriver driver) {
		// driver.findElement(By.xpath(news_link_xpath)).click();
		driver.findElement(By.name(news_link_name)).click();
	}

	// ��ȡ��ַ
	public String getNewsLink(WebDriver driver) {
		// String NewsLink =
		// driver.findElement(By.xpath(news_link_xpath)).getAttribute("href");
		String newsLink = driver.findElement(By.name(news_link_name)).getAttribute("href");
		return newsLink;

	}
}
