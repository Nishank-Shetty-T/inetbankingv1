package com.inetbanking.testCases;

import java.io.IOException;


import org.testng.Assert;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.AddCustomerPage;
import com.inetbanking.pageObjects.LoginPage;

public class TC_AddCustomerTest_003 extends BasicClass{
	
	@Test
	public void addNewCustomer() throws InterruptedException, IOException {
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(username);
		logger.info("provided username");
		lp.setPassword(password);
		logger.info("provided password");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		
		AddCustomerPage addcust=new AddCustomerPage(driver);
		logger.info("Adding new customer details...");
		addcust.clickAddNewCust();
		addcust.custName("Nishank");
		addcust.custGender("male");
		addcust.custdob("11", "05", "1994");
		addcust.custaddress("Mangaluru");
		addcust.custcity("Mangalore");
		addcust.custstate("Karnataka");
		addcust.custpin("575001");
		addcust.custtelphone("9633087352");
		String email=randomestring()+"@gmail.com";
		addcust.custemail(email);
		addcust.custpassword("Nishank@123");
		addcust.custsubmit();
		
		Thread.sleep(3000);
		logger.info("validation started...");
		boolean res=driver.getPageSource().contains("Customer Registered Successfully!!!");
		if(res==true) {
			Assert.assertTrue(true);
			logger.info("test case passed");
		}else {
			logger.info("test case failed");
			captureScreen(driver,"addNewCustomer");
			Assert.assertFalse(false);
			
		}
	}
	
	
	
	

}
