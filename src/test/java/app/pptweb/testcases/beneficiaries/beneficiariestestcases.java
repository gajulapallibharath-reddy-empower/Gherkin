package app.pptweb.testcases.beneficiaries;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import core.framework.Globals;

public class beneficiariestestcases {
	
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	LoginPage login;
	String tcName;
	
	@BeforeClass
	public void InitTest() throws Exception {
		Stock.getParam(Globals.GC_TESTCONFIGLOC + Globals.GC_CONFIGFILEANDSHEETNAME + ".xls");
		Globals.GBL_SuiteName = this.getClass().getName();
		lib.Web.webdriver = Web.getWebDriver(Stock.globalParam.get("BROWSER"));
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);
	}
	
	@BeforeMethod
    public void getTCName(Method tc) {
           tcName = tc.getName();       
    }
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC001_Married_with_Spouse_One_beneficiary_new_address_Sanity(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
//			if(homePage.getNoOfPlansFromDB() <= 2){
//				System.out.println("inside1");
//				 leftmenu = new LeftNavigationBar(homePage);
//			}
//			else{
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
//			}
				MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			
			
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			
			// add a beneficiary
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"));
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				
			}
			
			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);
			
			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			//verify beneficiary name
			if(beneficiary.verifyBeneficiaryDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify beneficiary name", "beneficiary name is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary name", "beneficiary name bot matching", true);
			
			//verify beneficiary allocation percentage
			if(beneficiary.verifyBeneficiaryDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Allocation", "beneficiary Allocation is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Allocation", "beneficiary Allocation bot matching", true);
			
			//verify beneficiary Relationship
			if(beneficiary.verifyBeneficiaryDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Relationship", "beneficiary Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Relationship", "beneficiary Relationship bot matching", true);
			
			//verify beneficiary ssn
			if(beneficiary.verifyBeneficiaryDetails("SSN"))
				Reporter.logEvent(Status.PASS, "verify beneficiary SSN", "beneficiary SSN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary SSN", "beneficiary SSN bot matching", true);
			
			//verify beneficiary DOB
			if(beneficiary.verifyBeneficiaryDetails("DOB"))
				Reporter.logEvent(Status.PASS, "verify beneficiary DOB", "beneficiary DOB is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary DOB", "beneficiary DOB bot matching", true);
			
			//verify beneficiary new address
			if(Stock.GetParameterValue("Use Current Address").equalsIgnoreCase("No")){
				if(beneficiary.verifyBeneficiaryDetails("Address"))
					Reporter.logEvent(Status.PASS, "verify beneficiary Address", "beneficiary Address is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary Address", "beneficiary Address bot matching", true);
			}
			
			//delete beneficiary from Database
			beneficiary.deleteBeneficiariesFromDB(Stock.GetParameterValue("Participant ssn"), Stock.GetParameterValue("Participant first name")+"%");
		
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
        }
		try {
            Reporter.finalizeTCReport();
		} catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
		}
	}
	
	@Test(dataProvider = "setData")
	public void Beneficiary_TC012_UnMarried_Multiple_Individual_beneficiary_Sanity(int itr, Map<String, String> testdata){
		Stock.globalTestdata = testdata;
//      Globals.GBL_CurrentIterationNumber = itr;
		
		
		try{
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
//			if(homePage.getNoOfPlansFromDB() <= 2){
//				System.out.println("inside1");
//				 leftmenu = new LeftNavigationBar(homePage);
//			}
//			else{
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
//			}
				MyBeneficiaries beneficiary = new MyBeneficiaries(leftmenu);
			
			beneficiary.get();
			
			
			Reporter.logEvent(Status.INFO, "Navigate to Beneficiary page.", "Beneficiary page is displayed", true);
			beneficiary.addBeneficiary(Stock.GetParameterValue("Marital Status"), Stock.GetParameterValue("Beneficiary Relation"), Stock.GetParameterValue("Use Current Address"), Stock.GetParameterValue("Beneficiary Type"));
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				
			}
			
			//verify if add another beneficiary button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("AddAnotherBeneficiary"))
				Reporter.logEvent(Status.PASS, "verify add another beneficiary button", "add another beneficiary button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "add another beneficiary button", "add another beneficiary button is not displayed", true);
			
			//verify if continue and confirm button is displayed after adding a beneficiary
			if(beneficiary.isFieldDisplayed("ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Confirm and Continue button displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Confirm and Continue button is not displayed", true);
			
			//click on continue and confirm button
			if(Web.clickOnElement(beneficiary, "ContinueAndConfirm"))
				Reporter.logEvent(Status.PASS, "Confirm and Continue button", "Clicked confirm and continue button", false);
			else
				Reporter.logEvent(Status.FAIL, "Confirm and Continue button", "Could not Click confirm and continue button", true);
			
			//verify beneficiary name
			if(beneficiary.verifyBeneficiaryDetails("Name"))
				Reporter.logEvent(Status.PASS, "verify beneficiary name", "beneficiary name is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary name", "beneficiary name bot matching", true);
			
			//verify beneficiary allocation percentage
			if(beneficiary.verifyBeneficiaryDetails("Allocation"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Allocation", "beneficiary Allocation is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Allocation", "beneficiary Allocation bot matching", true);
			
			//verify beneficiary relationship
			if(beneficiary.verifyBeneficiaryDetails("Relationship"))
				Reporter.logEvent(Status.PASS, "verify beneficiary Relationship", "beneficiary Relationship is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary Relationship", "beneficiary Relationship bot matching", true);
			
			//verify beneficiary ssn
			if(beneficiary.verifyBeneficiaryDetails("SSN"))
				Reporter.logEvent(Status.PASS, "verify beneficiary SSN", "beneficiary SSN is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary SSN", "beneficiary SSN bot matching", true);
			
			//verify beneficiary DOB
			if(beneficiary.verifyBeneficiaryDetails("DOB"))
				Reporter.logEvent(Status.PASS, "verify beneficiary DOB", "beneficiary DOB is matching", false);
			else
				Reporter.logEvent(Status.FAIL, "verify beneficiary DOB", "beneficiary DOB bot matching", true);
			
			//verify beneficiary new address
			if(Stock.GetParameterValue("Use Current Address").equalsIgnoreCase("No")){
				if(beneficiary.verifyBeneficiaryDetails("Address"))
					Reporter.logEvent(Status.PASS, "verify beneficiary Address", "beneficiary Address is matching", false);
				else
					Reporter.logEvent(Status.FAIL, "verify beneficiary Address", "beneficiary Address bot matching", true);
			}
			
			//delete beneficiary from Database
			if(Stock.GetParameterValue("IterationNumber").equalsIgnoreCase("2"))
				beneficiary.deleteBeneficiariesFromDB(Stock.GetParameterValue("Participant ssn"), Stock.GetParameterValue("Participant first name")+"%");
		
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
		lib.Web.webdriver.close();
		lib.Web.webdriver.quit();

	}
}