package core.framework;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Stock;
import lib.Web;

import org.testng.IAnnotationTransformer;
import org.testng.IConfigurationListener2;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IRetryAnalyzer;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;

import reporter.Reporter;
import core.framework.ThrowException.TYPE;



public class TestListener implements ITestListener, IConfigurationListener2,
ISuiteListener, IInvokedMethodListener, IRetryAnalyzer,
IAnnotationTransformer {

	int currentTCInvocationCount = 0;
	static int suiteInvCount = 0;
	private static Map<Long,Boolean> finalTestStatusMap = new LinkedHashMap<>();
	public static Map<Long, String> browserMap = new LinkedHashMap<>();
	public static Map<Long, String> portMap = new LinkedHashMap<>();
	public Map<Integer, Map<String, String>> failedXmlMap = new LinkedHashMap<>();
	static int i = 0;
	static Map<String, Map<String, String>> gridPropertiesMap;




	private boolean isFinalTestStatus() {
		return finalTestStatusMap.get(Thread.currentThread().getId());
	}

	public static void setFinalTestStatus(boolean testStatus) {
		finalTestStatusMap.put(Thread.currentThread().getId(), testStatus);
	}
	public void onStart(ISuite suite) {
		Stock.readConfigProperty("testexecutionconfig.properties");
		Web.getWebDriver(Stock.getConfigParam("BROWSER"));
		Web.getDriver().manage().window().maximize();
		//Web.getDriver().get(Stock.getConfigParam("AppURL"));			
	}

	private void generateTestcaseReferenceMap(ISuite suite) {

	}

	public void onStart(ITestContext test) {
		try {
			//Reporter.initializeReportForTC(1, test.getName());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		Iterator<ITestResult> failedTestCases = context.getFailedTests()
				.getAllResults().iterator();
		while (failedTestCases.hasNext()) {
			ITestResult failedTestCase = failedTestCases.next();
			ITestNGMethod method = failedTestCase.getMethod();
			if (context.getFailedTests().getResults(method).size() > 1) {
				failedTestCases.remove();
			} else {

				if (context.getPassedTests().getResults(method).size() > 0) {
					failedTestCases.remove();
				}
			}
		}
	}

	public void onConfigurationSuccess(ITestResult result) {
	}

	public void onConfigurationFailure(ITestResult result) {

	}

	public void onConfigurationSkip(ITestResult result) {
	}

	public void beforeConfiguration(ITestResult result) {

		/*if(result.getMethod().isBeforeClassConfiguration())
		{
			Reporter.initializeModule("Login Test");
		}*/
	}

	public void onFinish(ISuite suite) {
		try{
			Reporter.objReport.flush();

			if (Web.getDriver().getWindowHandles().size() >= 0)
				Web.getDriver().quit();
		}
		catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, "Failed to quit Web Driver :"
					+ e.getMessage());
		}
	}

	// This belongs to IInvokedMethodListener and will execute before every
	// method including @Before @After @Test
	@SuppressWarnings("unchecked")
	public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
		//	System.out.println("hi");
		/*try {

			if (!Web.getDriver().getWindowHandle().isEmpty()) {
				Log.Report(Level.INFO,
						"Web Driver instance fou      nd to be active for the Test Case :"
								);
			}
		} catch (Exception t) {
			try {


			} catch (Exception e) {
				throw new WebDriverException("unable to start browser");
			}
		}*/

	}

	@SuppressWarnings({ "serial", "static-access" })
	public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
		if (method.getTestMethod().isTest()) {
			Reporter.objReport.flush();
			if (!isFinalTestStatus()) {
				testResult.setStatus(ITestResult.FAILURE);
			}
			if (suiteInvCount == 0) {
				if (testResult.getStatus() == testResult.FAILURE) {
					//Reporter.testCaseMap.get(Thread.currentThread().getId()).log(Status.FAIL,testResult.getThrowable());
					final String className = testResult.getTestClass()
							.getName();
					final String methodName = method.getTestMethod()
							.getMethodName();
					final String testName = testResult.getTestContext()
							.getName();
					failedXmlMap.put(i++, new HashMap<String, String>() {
						{
							put("className", className);
							put("methodName", methodName);
							put("testName", testName);
						}
					});
				}
			}
		}
	}

	public void transform(ITestAnnotation arg0, Class arg1, Constructor arg2,
			Method arg3) {
		// TODO Auto-generated method stub

	}

	public boolean retry(ITestResult arg0) {
		// TODO Auto-generated method stub
		return false;
	}



}