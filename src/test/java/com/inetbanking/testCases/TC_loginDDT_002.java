package com.inetbanking.testCases;

import static org.testng.Assert.assertTrue;

import java.io.IOException;

import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.inetbanking.pageObjects.LoginPage;
import com.inetbanking.utilities.XLUtils;

public class TC_loginDDT_002 extends BasicClass {
	
	@Test(dataProvider="LoginData")
	public void loginDDT(String user,String pwd) throws InterruptedException {
		
		LoginPage lp=new LoginPage(driver);
		lp.setUserName(user);
		logger.info("user name provided");
		lp.setPassword(pwd);
		logger.info("password provided");
		lp.clickSubmit();
		
		Thread.sleep(3000);
		
		if(isAlertPresent()==true) {
			driver.switchTo().alert().accept();
			driver.switchTo().defaultContent();
			Assert.assertTrue(false);
			logger.warn("login failed");
		}else {
			
			Assert.assertTrue(true);
			logger.info("login passed");
			lp.clickLogout();
			Thread.sleep(3000);
			driver.switchTo().alert().accept(); // logout alert
			driver.switchTo().defaultContent();
			
		}
		
		
		
	}
	
	
	public boolean isAlertPresent() {    //user defined method to check alert present or not
		
		try {
			driver.switchTo().alert();
			return true;
		}catch(NoAlertPresentException e) {
			return false;
		}
		
	}
	
	@DataProvider(name="LoginData")
	String [][] getData() throws IOException{
		String path=System.getProperty("user.dir")+"/src/test/java/com/inetbanking/testData/LoginData.xlsx";
		//String path="C:\\Users\\dell\\eclipse-workspace\\inetBankingV1\\src\\test\\java\\com\\inetbanking\\testData\\LoginData.xlsx";
		
		int rownum=XLUtils.getRowCount(path, "Sheet1");
		System.out.println("rownum "+rownum);
		int cocount=XLUtils.getCellCount(path, "Sheet1", 1);
		System.out.println("cocount "+cocount);
		
		String logindata[][]=new String[rownum][cocount];
		
		for(int i=1;i<=rownum;i++) {
			for(int j=0;j<cocount;j++) {
				logindata[i-1][j]=XLUtils.getCellData(path, "Sheet1", i, j);
				
			}
		}
		return logindata;
		
	}

}
