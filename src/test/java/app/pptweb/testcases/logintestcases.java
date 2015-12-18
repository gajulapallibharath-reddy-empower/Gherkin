package app.pptweb.testcases;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import app.common.Reporter;
import app.common.common;
import app.common.Reporter.Status;
import core.framework.Globals;
import core.utils.Stock;


public class logintestcases {
	
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	@BeforeClass
	public void InitTest() throws Exception {
		Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
		Globals.GBL_SuiteName = this.getClass().getName();
		common.webdriver = common.getWebDriver(Stock.globalParam.get("BROWSER"));
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getName(), testCase.getName());
	}
	
	@BeforeMethod
    public void getTCName(Method tc) {
           tcName = tc.getName();       
    }
	
	
	@Test(dataProvider = "setData")
	public void SF01_TC01_Verify_invalid_Userid_and_Password(int itr, Map<String, String> testdata){
		
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		try{
			Reporter.initializeReportForTC(itr, "SF01_TC01_Verify_invalid_Userid_and_Password");
			login = new LoginPage().get();
			String errMsg = "";
			login.submitLoginCredentials(testdata.get("username"),testdata.get("password"));
			errMsg = login.isValidCredentials();
				
			if (errMsg.trim().isEmpty()) {
				Reporter.logEvent(Status.FAIL, "Check error message displayed on submiting invalid credentials", 
						"No error message is displayed after submiting login credentials\nExpected error message on clicking on Sign In button", true);
				
				return;
			} else {
				Reporter.logEvent(Status.INFO, "Check error message displayed on submiting invalid credentials", 
						"An error message is displayed as expected", true);
			}
			
			common.VerifyText(testdata.get("ExpectedErrorMessage"), 
								errMsg, true);

			
		}catch(Exception e)
        {
            e.printStackTrace();
            Globals.exception = e;
            Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
        }
		catch(Error ae)
        {
                        ae.printStackTrace();
                        Globals.error = ae;
                        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
                        //throw ae;
        }
		finally {
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
}
	}
	
@Test(dataProvider = "setData")
public void SF01_TC02_Verify_login_Successfully_into_unregistered_Device(int itr, Map<String, String> testdata){
	Stock.globalTestdata = testdata;
//  Globals.GBL_CurrentIterationNumber = itr;
	
	try{
		Reporter.initializeReportForTC(itr, "SF01_TC02_Verify_login_Successfully_into_unregistered_Device");
		String verificationCode = "";
		
		TwoStepVerification twoStepVerification = new TwoStepVerification(new LoginPage());
		twoStepVerification.get();
		
		twoStepVerification.setPageMandatory(true);	//Two step verification page is expected to load
		twoStepVerification.get();
		
		Reporter.logEvent(Status.PASS, "Navigate to 'Two step verification (2 of 3)' page", 
				"Navigation succeeded", true);
		
		// TODO Add code to verify text displayed on Two step verification page
		
		// Verify options 'Text me', 'Call me', 'Email Me' and 'Already have a code?' exists
		common.verifyDropDownOptionExists(twoStepVerification, "CHOOSE DELIVERY METHOD", "TEXT ME:");
		common.verifyDropDownOptionExists(twoStepVerification, "CHOOSE DELIVERY METHOD", "CALL ME:");
		common.verifyDropDownOptionExists(twoStepVerification, "CHOOSE DELIVERY METHOD", "EMAIL:");
		
		if (common.isWebElementDisplayed(twoStepVerification, "Already have a code?")) {
			Reporter.logEvent(Status.PASS, "Verify 'Already have a code?' link is displayed", 
					"Link is displayed", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify 'Already have a code?' link is displayed", 
					"Link is not displayed", false);
		}
		
		//Select code delivery option and click continue
		twoStepVerification.selectCodeDeliveryOption(testdata.get("deliveryOption"));
		
		//Get verification code
		if (testdata.get("deliveryOption").trim().equalsIgnoreCase("ALREADY_HAVE_CODE")) {
			verificationCode = twoStepVerification.getVerificationCode(true);
		} else {
			if (testdata.get("deliveryOption").trim().equalsIgnoreCase("EMAIL")) {
				verificationCode = twoStepVerification.getVerificationCode(false);
			} else {
				if (twoStepVerification.isActivationCodeGenerated(testdata.get("deliveryOption"))) {
					Reporter.logEvent(Status.PASS, "Verify activation code is generated", "Activation code is successfully generated", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify activation code is generated", "Activation code is not generated", false);
				}
				return;
			}
		}
		
		if (verificationCode.trim().length() == 0) {
			Reporter.logEvent(Status.FAIL, "Fetch verification code.", "Verification code not generated", false);
			return;
		}
		
		//Submit verification code
		Thread.sleep(5000);
		twoStepVerification.submitVerificationCode(verificationCode, true, 
				Boolean.parseBoolean(testdata.get("rememberDevice")));
		
		//Dismiss pop ups if displayed
		LandingPage landingPage = new LandingPage(twoStepVerification);
		landingPage.dismissPopUps(true, true);
		
		//Verify if landing page is displayed - Landing page is loaded if Logout link is displayed.
		//LandingPage landingPage = new LandingPage(twoStepVerification);
		if (common.isWebElementDisplayed(landingPage, "Log out")) {
			Reporter.logEvent(Status.PASS, "Verify landing page is displayed", 
					"Landing page is displayed", true);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify landing page is displayed", 
					"Landing page is not displayed", true);
		}
		
		//Logout if opted
		landingPage.logout(true);
		Reporter.finalizeTCReport();
		
	}
	catch(Exception e)
    {
        e.printStackTrace();
        Globals.exception = e;
        Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception Occured", true);
    }catch(AssertionError ae)
    {
        ae.printStackTrace();
        Globals.assertionerror = ae;
        Reporter.logEvent(Status.FAIL, "Assertion Error Occured","Assertion Failed!!" , true);                    
        //throw ae;
    }
    finally {
    }
    try {
                    Reporter.finalizeTCReport();
    } catch (Exception e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
    }
	}
	
	
	@AfterClass
	public void cleanupSessions() {
		common.webdriver.close();
		common.webdriver.quit();
	}
}
