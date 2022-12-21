package com.inetbanking.utilities;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentReporter;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class Reporting extends TestListenerAdapter {
	
	public ExtentSparkReporter  spark ;
	public ExtentReports extent;
	public ExtentTest logger;
	
	public void onStart(ITestContext testContext) {
		
		String timeStamp=new SimpleDateFormat("YYYY.MM.DD.HH.MM.SS").format(new Date());
		String repName="Test-Report-"+timeStamp+".html";
		
		spark=new ExtentSparkReporter(System.getProperty("user.dir")+"/test-output/"+repName);
		try {
			spark.loadXMLConfig(System.getProperty("user.dir")+"/extent-config.xml");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		extent=new ExtentReports();
		
		extent.attachReporter(spark);
		extent.setSystemInfo("Host name", "localhost");
		extent.setSystemInfo("Enviornment", "QA");
		extent.setSystemInfo("user", "Nishank");
		
		spark.config().setDocumentTitle("inetbanking test project");
		spark.config().setReportName("Functional test report");
		spark.config().setTheme(Theme.DARK);
		
	}
	
	public void onTestSuccess(ITestResult tr) {
		logger=extent.createTest(tr.getName());
		logger.log(Status.PASS,MarkupHelper.createLabel(tr.getName(), ExtentColor.GREEN));
		
	}
	
	public void onTestFailure(ITestResult tr) {
		logger=extent.createTest(tr.getName());
		logger.log(Status.FAIL,MarkupHelper.createLabel(tr.getName(), ExtentColor.RED));
		
		String screenshotpath=System.getProperty("user.dir")+"\\Screenshots\\"+tr.getName()+".png";
		
		File f=new File(screenshotpath);
		
		if(f.exists()) {
			logger.fail("screenshot is bellow:"+logger.addScreenCaptureFromPath(screenshotpath));
		}
		
		
		
	}
	
	public void onTestSkipped(ITestResult tr) {
		
		logger=extent.createTest(tr.getName());
		logger.log(Status.SKIP,MarkupHelper.createLabel(tr.getName(), ExtentColor.ORANGE));
		
		
	}
	
	public void onFinish(ITestContext testContext) {
		extent.flush();
		
	}
	
	

}
