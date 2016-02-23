package core.framework;

import java.util.HashMap;
import lib.Log;
import lib.Log.Level;
import lib.Stock;
import lib.Web;
import org.testng.IConfigurationListener2;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import core.framework.ThrowException.TYPE;

public class TestListener implements ITestListener, IConfigurationListener2, ISuiteListener, IInvokedMethodListener {

	int currentTCInvocationCount=0;
	public void onStart(ISuite suite) {
		try{
			Stock.getParam(Globals.GC_TESTCONFIGLOC+
			Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
			Log.Report(Level.INFO,"Test Configuration initialized successfully");
		}catch(Exception e){
			ThrowException.Report(TYPE.EXCEPTION,e.getMessage());			
		}		
	}

	public void onStart(ITestContext test) {	
		Globals.GC_MANUAL_TC_NAME = test.getName();
		try{	
			if(!Web.webdriver.getWindowHandle().isEmpty()){
				Log.Report(Level.INFO,"Web Driver instance found to be active for the Test Case :"+test.getName());				
			}
		}catch(Exception t){				
			try{
				Log.Report(Level.INFO,"Web Driver instance found to be inactive for the Test Case :"+
			               test.getName()+" ,hence re-initiating");
				Web.webdriver = Web.getWebDriver(Stock.getConfigParam("BROWSER"));
				Log.Report(Level.INFO,"Web Driver instance re-initiated successfully the Test Case :"+
			               test.getName());
			}catch(Exception e){
				ThrowException.Report(TYPE.EXCEPTION,"Failed to re-initialize Web Driver :" +e.getMessage());
			}			
		}						
	}

	
	public void onTestStart(ITestResult result) {
		
	}

	
	public void onTestSuccess(ITestResult result) {
		
	}

	
	public void onTestFailure(ITestResult result) {
		
	}

	
	public void onTestSkipped(ITestResult result) {
		
	}

	
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
	}

	
	public void onFinish(ITestContext context) {
	}

	
	public void onConfigurationSuccess(ITestResult result) {
	}

	
	public void onConfigurationFailure(ITestResult result) {
		
	}

	
	public void onConfigurationSkip(ITestResult result) {
	}

	
	public void beforeConfiguration(ITestResult result) {
	}

	
	public void onFinish(ISuite suite) {
		try {
			if (Web.webdriver.getWindowHandles().size() >= 0)
				Web.webdriver.quit();
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION,"Failed to quit Web Driver :" +e.getMessage());
		}
		
	}

	// This belongs to IInvokedMethodListener and will execute before every
	// method including @Before @After @Test
	@SuppressWarnings("unchecked")
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {		
		if (method.getTestMethod().isTest()) {
			HashMap<String, String> globalTestData = (HashMap<String, String>) testResult.getParameters()[1];
			Stock.globalTestdata = globalTestData;
			currentTCInvocationCount = testResult.getMethod().getCurrentInvocationCount();
			Web.ifEndTestITRExecuted = ((Stock.getIterations() == (currentTCInvocationCount+1))? true:false);				
		}
	}

	
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		
	}	
}