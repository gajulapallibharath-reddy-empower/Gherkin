package app.pptweb.testcases.login;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.login.LoginPage;
import pageobjects.userregistration.AccountLookup;
import pageobjects.userregistration.AccountSetup;
import core.framework.Globals;


public class registrationtestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;

	@BeforeClass
	public void InitTest() throws Exception {
		Globals.GBL_SuiteName = this.getClass().getName();
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);
	}

	@Test(dataProvider = "setData")
	public void SF01_TC01_User_has_PIN(int itr, Map<String, String> testdata){
			
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			String headerText;
			String activeTab;
			String actualValue;
			String actualErrMsg, expectedErrMsg;

			LoginPage loginPage = new LoginPage();
			
			if (Web.appLoginStatus==false) {
				loginPage.get();
				loginPage.clickRegister();
			}else{
				loginPage.get();
				loginPage.clickRegister();
			}
			AccountLookup accLookup = new AccountLookup(loginPage);

			//Steps
			accLookup.get();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.", "Account lookup page is displayed", true);

			//Step 3 - Verify verbiage on the top of the page
			headerText = accLookup.getAccountLookupHeaderBlockText();
			if (headerText == null) {
				Reporter.logEvent(Status.FAIL, "Verify Account Lookup Header block text", "Header text block is not displayed on the page", false);
			} else {
				Web.VerifyText("Lets look up your account Enter the information below to set up your account.", headerText, true);
			}

			//Step 4 - Verify default tab opened is "I do not have a PIN"
			activeTab = accLookup.getActiveTabName();
			if (activeTab.equalsIgnoreCase("I do not have a PIN")) {
				Reporter.logEvent(Status.PASS, "Verify default tab opened is 'I do not have a PIN'", "Tab 'I do not have a PIN' is open by default", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify default tab opened is 'I do not have a PIN", "Tab 'I do not have a PIN' is not open by default", false);
			}

			//Navigate to tab 'I have a PIN'
			if (accLookup.navigateToTab("I have a PIN")) {
				Reporter.logEvent(Status.PASS, "Navigate to \"I have a PIN\" tab", "Navigation successful", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Navigate to \"I have a PIN\" tab", "Navigation failed", true);
			}

			//Step 5 - Verify that SSN and PIN text boxes are displayed
			if (accLookup.isFieldDisplayed("Social Security Number")) {
				Reporter.logEvent(Status.PASS, "SSN field verification", "SSN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN field verification", "SSN field is NOT visible", false);	
			}

			//Verify PIN text box is displayed
			if (accLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.PASS, "PIN field verification", "PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "PIN field verification", "PIN field is NOT visible", false);	
			}

			//Step 6 - Enter Alpha-numeric SSN and move the cursor out
			accLookup.setTextToFields("Social Security Number", "ab12CD34e");
			accLookup.clickOnFields("PIN");
			accLookup.clickOnFields("CONTINUE");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			Thread.sleep(2000);
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message 'Social Security Number must be numeric.' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg,"Social Security Number must be numeric.",true)) {
					Reporter.logEvent(Status.PASS, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}

			//Step 7 - Enter SSN less than 9 digits and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("Social Security Number", "1252");
			accLookup.clickOnFields("PIN");
			accLookup.clickOnFields("CONTINUE");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number must be nine digits.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}

			//Step 8 - Enter SSN more than 9 digits
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			actualValue = accLookup.setTextToFields("Social Security Number", "12345678956");
			if (actualValue.trim().equals("123456789")) {
				Reporter.logEvent(Status.PASS, "Verify SSN field accepts only 9 digits", 
						"Value set: 12345678956\nValue accepted: " + actualValue, false);
			} else if (actualValue.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL, "Verify SSN field accepts only 9 digits", 
						"Value set: 12345678956\nValue accepted: " + actualValue, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify SSN field accepts only 9 digits", 
						"Value set: 12345678956\nValue accepted: Unable to set/No Value accepted", false);
			}

			//Step 9 - Enter Alpha-numeric PIN and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "ab12");
			accLookup.clickOnFields("Social Security Number");
			accLookup.clickOnFields("CONTINUE");
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN must be numeric";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message 'PIN must be numeric.' is displayed", 
						"No error message displayed for PIN.", false);
			} else {
				
				if (Web.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}

			//Step 10 - Enter PIN less than 4 digits and move the cursor out
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "12");
			accLookup.clickOnFields("Social Security Number");
			accLookup.clickOnFields("CONTINUE");
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN must be four digits.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for PIN number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}

			//Step 11 - Enter PIN more than 4 digits
			actualValue = accLookup.setTextToFields("PIN", "123456");
			if (actualValue.trim().equals("1234")) {
				Reporter.logEvent(Status.PASS, "Verify PIN field accepts only 4 digits", 
						"Value set: 123456\nValue accepted: " + actualValue, false);
			} else if (actualValue.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL, "Verify PIN field accepts only 4 digits", 
						"Value set: 123456\nValue accepted: " + actualValue, false);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify PIN field accepts only 4 digits", 
						"Value set: 123456\nValue accepted: Unable to set/No Value accepted", false);
			}

			//Step 12 - Leave SSN blank and enter valid PIN and click on Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("PIN", "1234");
			accLookup.clickOnFields("Continue");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}

			//Step 13 - Enter valid SSN and leave PIN blank and click on Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.setTextToFields("Social Security Number", "123456789");
			accLookup.clickOnFields("Continue");
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for PIN number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}

			//Step 14 - Leave both SSN and PIN blank and click on Continue
			accLookup.navigateToTab("I do not have a PIN");
			accLookup.navigateToTab("I have a PIN");
			accLookup.clickOnFields("Continue");
			actualErrMsg = accLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}
			actualErrMsg = accLookup.getFieldErrorMsg("PIN");
			expectedErrMsg = "PIN is required.";
			if (actualErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
						"No error message displayed for Social Security number.", false);
			} else {
				if (Web.VerifyText(actualErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + actualErrMsg, false);
				}
			}

		}
		catch(Exception e)
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
			
		}
		finally { 
		try { Reporter.finalizeTCReport(); }
		catch (Exception e1) { e1.printStackTrace(); } 
		}
	}


	@Test(dataProvider = "setData")
	public void SF01_TC02_User_does_not_have_PIN(int itr, Map<String, String> testdata){
			
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			boolean blnIsElePresent;
			String txtActErrMsg;
			String headerText;
			String activeTab;
			String expectedErrMsg;
			
			LoginPage objloginPage = new LoginPage();
			
			if (Web.appLoginStatus==false) {
				objloginPage.get();
				objloginPage.clickRegister();
			}else{
				objloginPage.get();
				objloginPage.clickRegister();
			}
			AccountLookup objAccountLookup = new AccountLookup(objloginPage).get();

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.","Account lookup page is displayed", true);

			// Step 3 - Verify verbiage on the top of the page
			headerText = objAccountLookup.getAccountLookupHeaderBlockText();
			if (headerText == null) {
				Reporter.logEvent(Status.FAIL,
						"Verify Account Lookup Header block text","Header text block is not displayed on the page", false);
			} else {
				Web.VerifyText("Lets look up your account\nEnter the information below to set up your account.",headerText, false);
			}

			// Step 4 - Verify default tab opened is "I do not have a PIN"
			activeTab = objAccountLookup.getActiveTabName();
			if (activeTab.equalsIgnoreCase("I do not have a PIN")) {
				Reporter.logEvent(Status.PASS,"Verify default tab opened is 'I do not have a PIN'",
						"Tab 'I do not have a PIN' is open by default", false);
			} else {
				Reporter.logEvent(Status.FAIL,"Verify default tab opened is 'I do not have a PIN",
						"Tab 'I do not have a PIN' is not open by default", false);
			}

			// Step 5 - Verify that the following fields are shown along with text
			// entry boxes for values:
			// Social Security number (defaults to placeholder text that displays as
			// (___-__-____)
			// Zip Code
			// Last Name
			// Date of Birth (defaults to show greyed out format of MM DD YYYY)
			// Street Address

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Social Security Number");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "SSN Field Displayed'","Social Security Number field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN Field Displayed'","Social Security Number field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Zip Code");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Zip Code Field Displayed'","Zip Code field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Zip Code Field Displayed'","Zip Code field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Last Name");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Last Name Field Displayed'","Last Name field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Last Name Field Displayed'","Last Name field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Date of Birth");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Date of Birth Field Displayed'","Date of Birth field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Date of Birth Field Displayed'","Date of Birth field is NOT Displayed", false);
			}

			blnIsElePresent = objAccountLookup.isFieldDisplayed("Street Address");
			if (blnIsElePresent == true) {
				Reporter.logEvent(Status.PASS, "Street Address Field Displayed'","Street Address field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "Street Address Field Displayed'","Street Address field is NOT Displayed", false);
			}

			// Step 7 - In the social security field, enter alphanumeric characters
			// and move the cursor out of the field.

			objAccountLookup.setTextToFields("Social Security Number", "ab12CD34e");
			objAccountLookup.clickOnFields("ZIP Code");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Social Security Number");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,"Verify error message 'Social Security Number must be numeric.' is displayed",
						"No error message displayed for Social Security number.",false);
			} else {
				if (Web.VerifyText(txtActErrMsg,"Social Security Number must be numeric.",true)) {
					Reporter.logEvent(Status.PASS, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message 'Social Security Number must be numeric.' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 8 - Enter SSN less than 9 digits and move the cursor out
			objAccountLookup.setTextToFields("Social Security Number", "1252");
			objAccountLookup.clickOnFields("Zip Code");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Social Security Number");
			expectedErrMsg = "Social Security Number must be nine digits.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message '"+ expectedErrMsg + "' is displayed",
						"No error message displayed for Social Security number.",false);
			} else {
				if (Web.VerifyText(txtActErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 9 - Enter SSN more than 9 digits
			txtActErrMsg = objAccountLookup.setTextToFields("Social Security Number", "12345678956");
			if (txtActErrMsg.trim().equals("123456789")) {
				Reporter.logEvent(Status.PASS,"Verify SSN field accepts only 9 digits","Value set: 12345678956\nValue accepted: " + txtActErrMsg,
						false);
			} else if (txtActErrMsg.trim().length() > 0) {
				Reporter.logEvent(Status.FAIL,"Verify SSN field accepts only 9 digits","Value set: 12345678956\nValue accepted: " + txtActErrMsg,
						false);
			} else {
				Reporter.logEvent(Status.FAIL,"Verify SSN field accepts only 9 digits",
						"Value set: 12345678956\nValue accepted: Unable to set/No Value accepted",false);
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 10 - In the zip code field, enter alphanumeric characters and
			// move the cursor out of the field..

			objAccountLookup.setTextToFields("ZIP CODE", "abc23");
			objAccountLookup.clickOnFields("Last Name");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("ZIP CODE");
			expectedErrMsg = "Please enter valid ZIP Code.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,"ZIP CODE error Verification for alpha numberic char",
						"Error message 'Please enter valid ZIP Code.' is not displayed",false);
			} else {
				if (Web.VerifyText(txtActErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}

			/*
			// step 11 and 12 are UI validations that cannot be done
			// Step 13 - Verfiy that the placeholder text for the three boxes in the
			// Date of Birth field should be "__/__/____"
			*/

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 14 - Verify that only numeric characters are allowed to be
			// entered in the Date of Birth text entry box and no alpha character
			// should be allowed in the fields.

			objAccountLookup.setTextToFields("Date of Birth", "123123aa");
			objAccountLookup.clickOnFields("Street Address");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Date of Birth");
			expectedErrMsg = "Date of Birth must be numeric.";
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.FAIL,"Date of Birth error Verification for alpha numberic char",
						"Verify error message 'Date of Birth must be numeric.' is displayed",false);
			} else {
				if (Web.VerifyText(txtActErrMsg,expectedErrMsg,true)) {
					Reporter.logEvent(Status.PASS, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message is displayed.", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message '" + expectedErrMsg + "' is displayed", 
							"Error message displayed is not matching.\nActual message: " + txtActErrMsg, false);
				}
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// step 15 cannot be automated
			// Step 16 -Verify text entry box for the Street Address and that the
			// Street Address field allows for text entry of just the house number
			objAccountLookup.setTextToFields("Street Address", "23");
			objAccountLookup.clickOnFields("Social Security Number");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.PASS,"Street Address error Verification",
						"No error was displayed for Street address for entering only house number",false);
			} else {
				Reporter.logEvent(Status.FAIL,"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number\nActual Message: " + txtActErrMsg,false);
			}

			objAccountLookup.navigateToTab("I have a PIN");
			objAccountLookup.navigateToTab("I do not have a PIN");

			// Step 16 -Verify text entry box for the Street Address and that the
			// Street Address field allows for text entry of just the house number
			// and Street name
			objAccountLookup.setTextToFields("Street Address", "23 BOURKE STREET");
			objAccountLookup.clickOnFields("Social Security Number");
			txtActErrMsg = objAccountLookup.getFieldErrorMsg("Street Address");
			if (txtActErrMsg.length() == 0) {
				Reporter.logEvent(Status.PASS,"Street Address error Verification",
						"No error was displayed for Street address for entering house number and street name",false);
			} else {
				Reporter.logEvent(Status.FAIL,"Street Address error Verification",
						"Some error was displayed for Street address for entering only house number and street name\nActual Message: " + txtActErrMsg,false);
			}

			// Step 17 - Click on the "I have a PIN" tab at the top of the page
			if (objAccountLookup.navigateToTab("I have a PIN")) {
				Reporter.logEvent(Status.PASS,"Verify tab opened is 'I have a PIN'","The user navigated to 'I have a PIN' tab", true);
			} else {
				Reporter.logEvent(Status.FAIL,"Verify tab opened is 'I do not have a PIN"," The user did not navigate to 'I have a PIN'", false);
			}

			// Step 17 - Click on the "I have a PIN" tab at the top of the page and
			// verify if the SSN and PIN field is displayed
			if (objAccountLookup.isFieldDisplayed("Social Security Number")) {
				Reporter.logEvent(Status.PASS, "SSN Field Displayed'","Social Security Number field is Displayed", false);
			} else {
				Reporter.logEvent(Status.FAIL, "SSN Field Displayed'","Social Security Number field is NOT Displayed", false);
			}
			if (objAccountLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.PASS, "PIN field verification","PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.FAIL, "PIN field verification","PIN field is NOT visible", false);
			}

			// Step 18 - Click on the "I do not have a PIN" - The user is taken back
			// to registering without a pin
			objAccountLookup.navigateToTab("I do not have a PIN");
			if (objAccountLookup.isFieldDisplayed("PIN")) {
				Reporter.logEvent(Status.FAIL, "PIN field verification","PIN field is visible", false);
			} else {
				Reporter.logEvent(Status.PASS, "PIN field verification","PIN field is NOT visible", false);
			}
		}
		catch(Exception e)
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
			
		}
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC03_NegativeFlow_WithAndWithoutPINTabs(int itr, Map<String, String> testdata){
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			String ActErrMessage;
			LoginPage loginPage = new LoginPage();
			
			if (Web.appLoginStatus==false) {
				loginPage.get();
				loginPage.clickRegister();
			}else{
				loginPage.get();
				loginPage.clickRegister();
			}
			AccountLookup accLookup = new AccountLookup(loginPage);

			//Steps
			//Step 2 - Navigate to Account lookup page by clicking on Register link
			accLookup.get();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page", "Navigation succeeded", true);

			//Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			//Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim().equalsIgnoreCase(Stock.GetParameterValue("TabName"))) {
				Reporter.logEvent(Status.PASS, "Verify user is on \"" + Stock.GetParameterValue("TabName") + "\" tab", 
						"User is on \"" + Stock.GetParameterValue("TabName") + "\" tab", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify user is on \"" + Stock.GetParameterValue("TabName") + "\" tab", 
						"User is not on \"" + Stock.GetParameterValue("TabName") + "\" tab", true);
			}

			//Step 5 - Enter a valid participant details and click continue
			if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("SSN", Stock.GetParameterValue("SSN"));
				//Enter PIN
				accLookup.setTextToFields("PIN", Stock.GetParameterValue("PIN"));

			} else if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I do not have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("Social Security Number", Stock.GetParameterValue("SSN"));
				//Enter Zip Code
				accLookup.setTextToFields("Zip Code", Stock.GetParameterValue("ZipCode"));
				//Enter Last Name
				accLookup.setTextToFields("Last Name", Stock.GetParameterValue("LastName"));
				//Enter Date of Birth
				accLookup.setTextToFields("Date of Birth", Stock.GetParameterValue("DOB"));
				//Enter Street Address
				accLookup.setTextToFields("Street Address", Stock.GetParameterValue("StreetAddress"));
			}

			//Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(Status.INFO, "Enter participant details and click on Continue button.", 
					"Submitted participant details and clicked on Continue button", true);

			//Verify error message displayed
			ActErrMessage = accLookup.getMainErrorMsg();
			if (ActErrMessage.length() == 0) {
				Reporter.logEvent(Status.FAIL, "Verify error message displayed", "No error message is displayed on the page", true);
			} else {
				if (Web.VerifyText(Stock.GetParameterValue("ExpectedErrorMsg"), ActErrMessage, true)) {
					Reporter.logEvent(Status.PASS, "Verify error message after submitting invalid details", "Expected error message is displayed.\nExpected: " + Stock.GetParameterValue("ExpectedErrorMsg"), true);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify error message after submitting invalid details", 
							"Expected error message is not displayed.\nExpected: " + Stock.GetParameterValue("ExpectedErrorMsg") + "\n"
									+ ActErrMessage, true);
				}
			}

			//Switch to other tab - to help continuation for other iterations if any
			if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I have a PIN")) {
				accLookup.navigateToTab("I do not have a PIN");
			} else {
				accLookup.navigateToTab("I have a PIN");
			}
		}
		catch(Exception e)
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
			 
			
			try {
				 String[] sqlQuery;
				sqlQuery = Stock.getTestQuery("unlockParticipants");
				DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("SSN"));
				DB.executeUpdate(sqlQuery[0], "Commit;");
				
			} catch (Exception e) {
				Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);
			}
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}

	@Test(dataProvider = "setData")
	public void SF01_TC05_UserLock_WithAndWithoutPIN(int itr, Map<String, String> testdata){
			
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			String activeTab;
			String actErrMsg;
			boolean blnTempVar;
			String expNoPinUsrLckmsgOne = "Error: The entries you provided do not match our records. Please try again. You have 2 attempt(s) remaining.";
			String expNoPinUsrLockmsgTwo = "Error: The entries you provided do not match our records. Please try again. You have 1 attempt(s) remaining.";
			String expNoPinUsrLockmsg = "You have exceeded the maximum number of login attempts allowed. For security reasons, Internet access to your account has been temporarily disabled.";
			String expWithPinUsrLckmsgOne = "The passcode you entered is invalid. Please re-enter your passcode. You have 2 attempt(s) left.";
			String expWithPinUsrLckmsgTwo = "The passcode you entered is invalid. Please re-enter your passcode. You have 1 attempt(s) left.";
			String expWithPinUsrLockmsg = "You have exceeded the maximum number of login attempts allowed. For security reasons, Internet access to your account has been temporarily disabled.";

			LoginPage objloginPage = new LoginPage();
			if (Web.appLoginStatus==false) {
				objloginPage.get();
				objloginPage.clickRegister();
			}else{
				objloginPage.get();
				objloginPage.clickRegister();
			}
			AccountLookup objAccountLookup = new AccountLookup(objloginPage).get();		

			Reporter.logEvent(Status.INFO, "Navigate to Account look-up page.", "Account lookup page is displayed", false);

			objAccountLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I have a PIN")) {

				//Verify if the user is on the 'I have a Pin' tab
				activeTab = objAccountLookup.getActiveTabName();
				if (activeTab.trim().equalsIgnoreCase("I have a PIN")) {
					Reporter.logEvent(Status.PASS, "Verify Verification", "The user navigated to 'I have a PIN' tab", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify Verification", " The user did not navigate to 'I have a PIN'", false);
				}

				//verify the first error message that the user gets on entering invalid pin
				objAccountLookup.registrationWithPIN(Stock.GetParameterValue("SSN"), "4231");

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,expWithPinUsrLckmsgOne,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}

				}

				objAccountLookup.clickOnFields("I do not have a PIN");
				objAccountLookup.clickOnFields("I have a PIN");

				//verify the second error message that the user gets on entering invalid pin
				objAccountLookup.registrationWithPIN(Stock.GetParameterValue("SSN"), "4231");

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,expWithPinUsrLckmsgTwo,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}
				}

				objAccountLookup.clickOnFields("I do not have a PIN");
				objAccountLookup.clickOnFields("I have a PIN");

				//verify the final error message that the user gets on entering invalid pin,where the user is locked
				objAccountLookup.registrationWithPIN(Stock.GetParameterValue("SSN"), "4231");

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify user Lock Message'" + actErrMsg , 
							"user profile lock msg has not been displayed. Please check your data", false);
				} else {
					if (actErrMsg.trim().toUpperCase().startsWith(expWithPinUsrLockmsg.trim().toUpperCase())) {
						Reporter.logEvent(Status.PASS, "Verify user Lock Message'" + actErrMsg , 
								"User has locked his profile sucessfully after 3 unsuccessful tries.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify user Lock Message'" + actErrMsg , 
								"User profile lock message after 3 unsuccessful login attempts was not verified correctly", true);
					}
				}


			} else if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I do not have a PIN")) {

				//Verify if the user is on the 'I do not have a Pin' tab
				activeTab = objAccountLookup.getActiveTabName();
				if (activeTab.trim().equalsIgnoreCase("I do not have a PIN")) {
					Reporter.logEvent(Status.PASS, "Verify Verification", "The user navigated to 'I do not have a PIN' tab", false);
				} else {
					Reporter.logEvent(Status.FAIL, "Verify Verification", " The user did not navigate to 'I do not have a PIN'", false);
				}

				//verify the first error message that the user gets on entering invalid pin
				objAccountLookup.registerWithoutPIN(Stock.GetParameterValue("SSN"), 
						Stock.GetParameterValue("ZipCode"),  
						Stock.GetParameterValue("LastName"), 
						Stock.GetParameterValue("DOB"), 
						Stock.GetParameterValue("StreetAddress"));

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,expNoPinUsrLckmsgOne,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 1'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}

				}

				//avoid staleElementException

				objAccountLookup.clickOnFields("I have a PIN");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					//Do nothing
				}
				objAccountLookup.clickOnFields("I do not have a PIN");

				//verify the second error message that the user gets on entering invalid pin
				objAccountLookup.registerWithoutPIN(Stock.GetParameterValue("SSN"), 
						Stock.GetParameterValue("ZipCode"),  
						Stock.GetParameterValue("LastName"), 
						Stock.GetParameterValue("DOB"), 
						Stock.GetParameterValue("StreetAddress"));

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
							"No error message displayed on account lookup page.", false);
				} else {
					blnTempVar = Web.VerifyText(actErrMsg,expNoPinUsrLockmsgTwo,true);
					if (blnTempVar) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message displayed as Expected.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock - try 2'" + actErrMsg , 
								"Error message did not match to the Expected error message.", true);
					}
				}

				objAccountLookup.clickOnFields("I have a PIN");
				objAccountLookup.clickOnFields("I do not have a PIN");

				//verify the final error message that the user gets on entering invalid pin,where the user is locked
				objAccountLookup.registerWithoutPIN(Stock.GetParameterValue("SSN"), 
						Stock.GetParameterValue("ZipCode"),  
						Stock.GetParameterValue("LastName"), 
						Stock.GetParameterValue("DOB"), 
						Stock.GetParameterValue("StreetAddress"));

				actErrMsg = objAccountLookup.getMainErrorMsg();
				if (actErrMsg.length() == 0) {
					Reporter.logEvent(Status.FAIL, "Verify error message for User Lock down'" + actErrMsg , 
							"user profile lock msg has not been displayed. Please check your data", false);
				} else {
					if (actErrMsg.trim().toUpperCase().startsWith(expNoPinUsrLockmsg.trim().toUpperCase())) {
						Reporter.logEvent(Status.PASS, "Verify error message for User Lock down'" + actErrMsg , 
								"User has locked his profile sucessfully after 3 unsuccessful tries.", true);
					} else {
						Reporter.logEvent(Status.FAIL, "Verify error message for User Lock down'" + actErrMsg , 
								"User has NOT locked his profile sucessfully after 3 unsuccessful tries.", false);
					}
				}
			}
		}
		catch(Exception e)
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

		}
		finally { 
			String[] sqlQuery;
			try {
				sqlQuery = Stock.getTestQuery("unlockParticipants");
				DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("SSN"));
				DB.executeUpdate(sqlQuery[0], "Commit;");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}

	@Test(dataProvider = "setData")
	public void SF03_TC01_AccountSetup(int itr, Map<String, String> testdata){
				
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LoginPage objloginPage = new LoginPage();
			if (Web.appLoginStatus==false) {
				objloginPage.get();
				objloginPage.clickRegister();
			}else{
				objloginPage.get();
				objloginPage.clickRegister();
			}
			AccountLookup objAccountLookup = new AccountLookup(objloginPage);
			AccountSetup objAccountSetup = new AccountSetup(objAccountLookup).get();

			if (Stock.GetParameterValue("FunctionalityType").trim().equalsIgnoreCase("contactinformation")) {
				objAccountSetup.validateContactInformationUI(Stock.GetParameterValue("SSN"));
			} else {
				objAccountSetup.validateUserNameAndPasswordUI();
			}
		}
		catch(Exception e)
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
			
		}
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}

	@Test(dataProvider = "setData")
	public void SF04_TC01_AccountLookup_PositiveFlow(int itr, Map<String, String> testdata){

		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			String hdrBlockText;
			LoginPage loginPage = new LoginPage();
			if (Web.appLoginStatus==false) {
				loginPage.get();
				loginPage.clickRegister();
			}else{
				loginPage.get();
			}
			AccountLookup accLookup = new AccountLookup(loginPage);
			AccountSetup accSetup = new AccountSetup(accLookup);

			//Steps
			//Step 2 - Navigate to Account lookup page by clicking on Register link
			accLookup.get();
			Reporter.logEvent(Status.PASS, "Navigate to Account Lookup page", "Navigation succeeded", true);

			//Step 3 - Click on "I have a PIN" or "I do not have a PIN" tab
			accLookup.clickOnFields(Stock.GetParameterValue("TabName"));

			//Step 4 - Verify user is on opted tab
			if (accLookup.getActiveTabName().trim().equalsIgnoreCase(Stock.GetParameterValue("TabName"))) {
				Reporter.logEvent(Status.PASS, "Verify user is on \"" + Stock.GetParameterValue("TabName") + "\" tab", 
						"User is on \"" + Stock.GetParameterValue("TabName") + "\" tab", true);
			} else {
				Reporter.logEvent(Status.FAIL, "Verify user is on \"" + Stock.GetParameterValue("TabName") + "\" tab", 
						"User is not on \"" + Stock.GetParameterValue("TabName") + "\" tab", true);
			}

			//Step 5 - Enter a valid participant details and click continue
			if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("SSN", Stock.GetParameterValue("SSN"));
				//Enter PIN
				accLookup.setTextToFields("PIN", Stock.GetParameterValue("PIN"));

			} else if (Stock.GetParameterValue("TabName").trim().equalsIgnoreCase("I do not have a PIN")) {
				//Enter SSN
				accLookup.setTextToFields("Social Security Number", Stock.GetParameterValue("SSN"));
				//Enter Zip Code
				accLookup.setTextToFields("Zip Code", Stock.GetParameterValue("ZipCode"));
				//Enter Last Name
				accLookup.setTextToFields("Last Name", Stock.GetParameterValue("LastName"));
				//Enter Date of Birth
				accLookup.setTextToFields("Date of Birth", Stock.GetParameterValue("DOB"));
				//Enter Street Address
				accLookup.setTextToFields("Street Address", Stock.GetParameterValue("StreetAddress"));
			}

			//Click on Continue button
			accLookup.clickOnFields("Continue");
			Reporter.logEvent(Status.INFO, "Enter participant details and click on Continue button.", 
					"Submitted participant details and clicked on Continue button", false);

			//Verify set up your account page is displayed
			hdrBlockText = accSetup.getAccountSetupHeaderBlockText();
			if (hdrBlockText == null) {
				Reporter.logEvent(Status.FAIL, "Verify Account setup Header block text", "Header text block is not displayed on the page", true);
			} else {
				Web.VerifyText("We found you!\nTo continue, provide your contact information and create a username and password.", hdrBlockText, true);
				Reporter.logEvent(Status.PASS, "Navigate to Account setup page", "user successfully navigated to the Account Setup page", true);
			}
		}
		catch(Exception e)
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
			
		}
		finally { 
			try { Reporter.finalizeTCReport(); }
			catch (Exception e1) { e1.printStackTrace(); } 
			}
	}



	@AfterClass
	public void cleanupSessions() {
		lib.Web.webdriver.close();
		lib.Web.webdriver.quit();
	}
}