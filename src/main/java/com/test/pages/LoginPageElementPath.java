package com.test.pages;

//页面对象路径类
public class LoginPageElementPath {
	
	//frame
	public String frame_name_id = "x-URS-iframe";
	
	//账户 
	public String input_idbox_xpath = ".//*[@id='account-box']/div[1]/input[@name='username']";
	public String input_idbox_id = "idInput";
	public String input_idbox_name = "email";
	//密码 
	public String input_psdbox_name = "password";
	//登录按钮
	public String btn_loginBtn_xpath = ".//*[@id='dologin']";
	
	//新闻链接
	public String news_link_xpath = ".//*[@id='u_sp']/a[1]";
	public String news_link_name = "tj_trnews";
	
	//登录链接
	
}