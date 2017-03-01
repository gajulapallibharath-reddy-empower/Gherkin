package app.pptweb.testcases.deferrals;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.deferrals.Deferrals;
import pageobjects.deferrals.PriorPlanContributions;
import pageobjects.general.LeftNavigationBar;
import pageobjects.general.MyAccountsPage;
import pageobjects.landingpage.LandingPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;
import appUtils.TestDataFromDB;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class deferralstestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	private static HashMap<String, String> testDataFromDB = null;
	LoginPage login;
	String tcName;
	static String printTestData="";
	

	@BeforeClass
	public void InitTest() throws Exception {
		Reporter.initializeModule(this.getClass().getName());
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage()
				.getName(), testCase.getName());
	}
	
	 public void prepareDynamicTestData(String quesryNmae,String... queryParam) {
			try {
				testDataFromDB = TestDataFromDB.getParticipantDetails(
						quesryNmae, queryParam);
				TestDataFromDB.addUserDetailsToGlobalMap(testDataFromDB);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	    
	    private String printTestData() throws Exception {
			printTestData="";
			for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
				if(!entry.getKey().equalsIgnoreCase("PASSWORD"))
					printTestData=printTestData+entry.getKey() + "="+ entry.getValue() +"\n";
			}
		 return printTestData;
		}

	

	/**
	 * The following script After Tax Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_001_After-tax_Select
	 * another contribution rate_Sanity
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__AfterTax(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			

			
			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,
						"Verify After-tax contribution page",
						"After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify After-tax contribution page",
						"After-tax Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			
			lib.Web.clickOnElement(deferrals, "Continue button");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.myContributions_Confirmation_Page();
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * The following script Bonus Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_002_Bonus_Select another
	 * contribution rate 2.Bonus_SelectAnotherContribution_after
	 * 3.Bonus_SelectAnotherContribution_split
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__Bonus(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			try {
				lib.Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Bonus contribution page",
						"Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Bonus contribution page",
						"Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			

			if (Stock.GetParameterValue("Contribution_type").equalsIgnoreCase(
					"split"))
				deferrals.split_bonus_contribution();
			else
				deferrals.select_ContributionType(Stock
						.GetParameterValue("Contribution_type"));

			deferrals.add_Auto_Increase(Stock
					.GetParameterValue("Add_auto_increase_type"));
			deferrals.myContributions_Confirmation_Page();
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	@Test(dataProvider = "setData")
	public void Deferral_002_Bonus_Select_another_contribution_rate(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Bonus(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Bonus_SelectAnotherContribution_after(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Bonus(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Bonus_SelectAnotherContribution_split(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Bonus(itr, testdata);
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases:
	 * 1.SIT_PPTWEB_Deferral_012_Regular_BEFORE-Select Another contribution
	 * rate_Sanity 2.SIT_PPTWEB_Deferral_016_Regular_SPLIT- Select Another
	 * contributiont rate_Sanity
	 * 3.SIT_PPTWEB_Deferral_010_Regular_BEFORE-Maximize to the company match
	 * 4.SIT_PPTWEB_Deferral_011_Regular_BEFORE-Maximize to the IRS limit
	 * 5.SIT_PPTWEB_Deferral_013_Regular_ROTH-Maximize to the company match
	 * 6.SIT_PPTWEB_Deferral_014_Regular_ROTH-Maximize to the IRS limit
	 * 7.SIT_PPTWEB_Deferral_015_Regular_ROTH-Select Another contribution rate
	 * 8.SIT_PPTWEB_Deferral_017_Regular_SPLIT-Maximize to the company match
	 * 9.SIT_PPTWEB_Deferral_018_Regular_SPLIT-Maximize to the IRS limit
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral__Regular(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Participant ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else{
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();

			try {
				lib.Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);

			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to company match"))
				deferrals.click_MaximizeToCompanyMatch();
			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit();
			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();

			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			if (!lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.add_Auto_Increase(lib.Stock
						.GetParameterValue("Add_auto_increase_type"));

			deferrals.myContributions_Confirmation_Page();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", "",
					true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_012_Regular_BEFORE_Select_Another_contribution_rate(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_016_Regular_SPLIT_Select_Another_contributiont_rate(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_010_Regular_BEFORE_Maximize_to_the_company_match(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_011_Regular_BEFORE_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_013_Regular_ROTH_Maximize_to_the_company_match(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_014_Regular_ROTH_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_015_Regular_ROTH_Select_Another_contribution_rate(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_017_Regular_SPLIT_Maximize_to_the_company_match(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_018_Regular_SPLIT_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) {
		SIT_PPTWEB_Deferral__Regular(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_Catch_up(int itr,
			Map<String, String> testdata) throws Exception {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			try {
				lib.Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Catch Up Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Catch up contribution page",
						"Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Catch up contribution page",
						"Catch up Contributions page is not displayed", true);

			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.click_Maximize_IRS_Limit();
			if (lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Select another contribution"))
				deferrals.click_Select_Your_Contribution_Rate();

			if (lib.Stock.GetParameterValue("Contribution_type")
					.equalsIgnoreCase("Split"))
				deferrals.split_catchup_contribution();
			else
				deferrals.select_ContributionType(lib.Stock
						.GetParameterValue("Contribution_type"));

			if (!lib.Stock.GetParameterValue("Contributing_type")
					.equalsIgnoreCase("Maximize to irs limit"))
				deferrals.add_Auto_Increase(lib.Stock
						.GetParameterValue("Add_auto_increase_type"));

			deferrals.myContributions_Confirmation_Page();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			Web.getDriver().navigate().refresh();
			Deferrals deferrals = new Deferrals();
			Web.waitForElement(deferrals, "My Contributions");
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Deferral_005_Catch_up_Maximize_to_the_IRS_limit(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_004_Catch_up_Select_another_contribution_rate(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_SelectAnotherContribution_Split(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_SelectAnotherContribution_Roth(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_MaximizeToIRSLimit_Roth(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Catchup_MaximizeToIRSLimit_Split(int itr,
			Map<String, String> testdata) throws Exception {
		SIT_PPTWEB_Deferral_Catch_up(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_006_Previous_Contributions_Participant_hired_in_prior_year(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			if (homePage.getNoOfPlansFromDB(lib.Stock
					.GetParameterValue("Particicpant_ssn")) <= 2)
				leftmenu = new LeftNavigationBar(homePage);
			else {
				MyAccountsPage accountPage = new MyAccountsPage(homePage);
				leftmenu = new LeftNavigationBar(accountPage);
			}
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);
			priorContributions.get();

			if (priorContributions.verifyParticipantsHiredInPriorYear())
				Reporter.logEvent(Status.PASS,
						"Verify if Participant was hired in previous year",
						"Participant hired in previous year", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify if Participant was hired in previous year",
						"Participant not hired in previous year", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Thee following scripts test for the prior contribution details of the
	 * participants that are in the following category 1. Participant in the
	 * first year of joining the plan or first year of the employment 2.
	 * Participant in the first year of catchup contribution 3. Participant who
	 * is not in the first year of employment
	 * 
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_008_Prior Plan
	 * Contributions_Participant not in first year of employment
	 * 2.SIT_PPTWEB_Deferral_007_Prior Plan Contributions_Catchup - participant
	 * within first year of employment 3.SIT_PPTWEB_Deferral_006_Previous
	 * Contributions_Participant hired in current year
	 */

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			// logic to verify the prior plan contribution functionality with 3
			// combination of manual test cases listed above in comments
			// if
			// (lib.Stock.GetParameterValue("Period_of_Participation").equalsIgnoreCase("Not_In_First_Year"))
			// {
			//
			// if
			// (lib.Web.isWebElementDisplayed(leftmenu,"Prior plan contributions"
			// )) {
			// Reporter.logEvent(Status.FAIL,
			// "Prior plan contributions link visibility",
			// "participant is unable to find the Prior plan contributions link since the participant is not in his first year of employment",
			// true);
			// } else {
			// Reporter.logEvent(Status.PASS,
			// "Prior plan contributions link visibility",
			// "participant is able to find the Prior plan contributions link even though the participant is not in his first year of employment. Please check the test data",
			// true);
			// }
			// } else {
			// Navigate to the prior plan contribution
			priorContributions.get();

			if (lib.Stock.GetParameterValue("Period_of_Participation")
					.equalsIgnoreCase("Not_In_First_Year")) {

				if (lib.Web.isWebElementDisplayed(leftmenu,
						"Prior plan contributions")) {
					Reporter.logEvent(
							Status.FAIL,
							"Prior plan contributions link visibility",
							"participant is unable to find the Prior plan contributions link since the participant is not in his first year of employment",
							true);
				} else {
					Reporter.logEvent(
							Status.PASS,
							"Prior plan contributions link visibility",
							"participant is able to find the Prior plan contributions link even though the participant is not in his first year of employment. Please check the test data",
							true);
				}
			} else {
				// perform a initial check on the prior plan contribution
				priorContributions.verifyPriorPlanContributionsPage();
				// enter the year to date and catchup contribution value
				priorContributions.enterContributionValue(
						lib.Stock.GetParameterValue("yearToDateContribution"),
						lib.Stock.GetParameterValue("catchupContribution"));
				priorContributions.verifyContributionMessage(lib.Stock
						.GetParameterValue("yearToDateContribution"));
				
				// verify the values for the year to date and catchup
				// contribution value on the confirmtion page
				if (priorContributions.verifyContributionMessage(lib.Stock
						.GetParameterValue("yearToDateContribution"))) {
					Reporter.logEvent(
							Status.PASS,
							"Verify catchup Contribution Confirmation Detials verification",
							"Test data for catchup Contribution value matched with test data on the confirmation page",
							true);
				} else {
					Reporter.logEvent(
							Status.FAIL,
							"Verify catchup Contribution Confirmation Detials verification",
							"Test data for catchup Contribution value DID NOT matched with test data on the confirmation page",
							true);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Thee following scripts Add Other deferral type
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_009_Other_Select another
	 * contribution rate
	 */
	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_Other(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			if (deferrals.clickAddEditButton("Other Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Other contribution page",
						"Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Other contribution page",
						"Other Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			

			if (lib.Stock.GetParameterValue("Contribution_type")
					.equalsIgnoreCase("Split"))
				deferrals.split_Other_contribution();
			else
				deferrals.select_ContributionType(lib.Stock
						.GetParameterValue("Contribution_type"));

			deferrals.add_Auto_Increase(Stock
					.GetParameterValue("Add_auto_increase_type"));
			deferrals.myContributions_Confirmation_Page();
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	
	@Test(dataProvider = "setData")
	public void Other_SelectAnotherContribution_split(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral_Other(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Other_SelectAnotherContribution_Catchup_Roth(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral_Other(itr, testdata);
	}

	@Test(dataProvider = "setData")
	public void Deferral_009_Other_Select_another_contribution_rate(int itr, Map<String, String> testdata) {
		SIT_PPTWEB_Deferral_Other(itr, testdata);
	}
	
	/**
	 * Thee following script checks if the participant is available for Bonus
	 * type deferral
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_003_Bonus_Participant
	 * not eligible for Bonus type contribution
	 */
	@Test(dataProvider = "setData")
	public void Participant_not_elegible_for_Bonus_type_contribution(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);150551
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			MyAccountsPage myAccountPage = new MyAccountsPage(homePage);
			myAccountPage.get();
			if(Web.isWebElementDisplayed(myAccountPage,"PLAN NAME", true)) { 
				 myAccountPage.clickPlanNameByGAID("150551-01"); 
							  }
			leftmenu = new LeftNavigationBar(myAccountPage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (!deferrals
					.check_if_participant_eligible_for_deferral_type("Bonus"))
				Reporter.logEvent(
						Status.PASS,
						"Check if Participant eligible for bonus type contribution",
						"Participant is not eligible for bonus type contribution",
						false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Check if Participant eligible for bonus type contribution",
						"Participant eligible for bonus type contribution",
						true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	/**
	 * The participant selects the Maximize me always option for Standard
	 * deferral type
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_020_Regular_SPLIT-
	 * Change of Maximized with Catchup to Maximize me always
	 */
	@Test(dataProvider = "setData")
	public void Regular_SPLIT_Maximize_me_always(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution",true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);

			deferrals.click_Maximize_IRS_Limit();
			/**
			 * This step is not applicable as new functionalitychange
			 */

			/*if (lib.Web.isWebElementDisplayed(deferrals, "Maximize Checkbox"))
				Reporter.logEvent(Status.PASS,
						"Check if Maximize me always check box is present",
						"Maximize me always check box is present", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Check if Maximize me always check box is present",
						"Maximize me always check box is not present", true);
*/
			deferrals.regular_maximize_me_always("Yes");

			deferrals.myContributions_Confirmation_Page();
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_003_View_only_Standard_with_changes_allowed_deferral(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			boolean isTextMatching = false;
			Thread.sleep(5000);
			String perRoth = deferrals
					.getContributionPercentage(Stock.GetParameterValue("Roth"));//RTH
			String perBeforeTax = deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX"));//BFRTX

			String contributionRate = Stock
					.GetParameterValue("Contribution Rate");

			deferrals.View_only_Standard_with_changes(contributionRate);
			contributionRate = deferrals.contrbution_rate;
           Thread.sleep(5000);
			int cr = Integer.parseInt(contributionRate.split("%")[0]);
			int bft = Integer.parseInt(perBeforeTax.split("%")[0]);
			String newPerRoth = Integer.toString(cr - bft);
			isTextMatching = Web.VerifyText(newPerRoth
					+ "% "+Stock.GetParameterValue("Roth")+" contribution with "
					+ perBeforeTax +" " +Stock.GetParameterValue("viewonlyBFTX"), deferrals
					.getWebElementText("VIEW ONLY SPLIT CONTRIBUTION TEXT")
					.trim(), true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is Same on Select Contribution page \nExpected:"+newPerRoth
										+ "% "+Stock.GetParameterValue("Roth")+" contribution with "
										+ perBeforeTax +" " +Stock.GetParameterValue("viewonlyBFTX")+"\nActual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT")
										.trim(),
								true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is not Same on Select Contribution page \nExpected:"+newPerRoth
										+ "% "+Stock.GetParameterValue("Roth")+" contribution with "
										+ perBeforeTax +" " +Stock.GetParameterValue("viewonlyBFTX")+"\nActual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT").trim(), true);
			}
			Web.clickOnElement(deferrals, "Continue button");
			Thread.sleep(4000);
			Web.clickOnElement(deferrals, "Confirm button");
			Thread.sleep(4000);
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			isTextMatching = Web.VerifyText(perRoth ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("Roth")),true);
			if (!isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Standard Roth After Split Contribution on My Contribution page",
								"Roth is updated on My Contribution page \nExpected:"
										+ newPerRoth
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("Roth"))
										.trim(),
								true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Standard Roth After Split Contribution on My Contribution page",
								"Roth is not updated on My Contribution page \nExpected:"
										+ newPerRoth
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("Roth"))
										.trim(),
								true);
			}
			isTextMatching = Web.VerifyText(perBeforeTax ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX")),true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Standard Before Tax After Split Contribution on My Contribution page",
								"Before Tax is Not updated on My Contribution page \nExpected:"
										+ perBeforeTax
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX"))
										.trim(),
								true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Standard Before Tax After Split Contribution on My Contribution page",
								"Standard Before Tax is updated on My Contribution page \nExpected:"
										+ perBeforeTax
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyBFTX"))
										.trim(),
								true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					"Exception Occured", true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);
			// throw ae;
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}
	}

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_002_View_only_After_tax_with_no_split_contributions(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (deferrals.checkAftertaxOptionNotdisplayed()) {
				Reporter.logEvent(
						Status.FAIL,
						"Check if after tax option is displayed in contributions page",
						"The after tax option is displayed", true);
			} else {
				Reporter.logEvent(
						Status.PASS,
						"Check if after tax option is displayed in contributions page",
						"The after tax option is not displayed", true);
			}

			if (lib.Web.isWebElementDisplayed(deferrals, "Edit Btn Aftertax")) {
				Reporter.logEvent(
						Status.FAIL,
						"Check if edit button is displayed for after tax with no split contributions",
						"The edit button is displayed for after tax with no split contributions",
						true);
			} else {
				Reporter.logEvent(
						Status.PASS,
						"Check if edit button is displayed for after tax with no split contributions",
						"The edit button is not displayed for after tax with no split contributions",
						true);
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void SIT_PPTWEB_Deferral_001_View_only_Catchup_with_split_contributions(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			boolean isTextMatching = false;
			Thread.sleep(5000);
			String perAgeRothCatchUp = deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyroth"));//Age roth catch up
			String perAgeCatchupBefore = deferrals
					.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax"));//Age Catch- up before

			String contributionRate = Stock
					.GetParameterValue("Contribution Rate");

			deferrals.Catchup_with_split_contributions(contributionRate);
			contributionRate = deferrals.contrbution_rate;
            Thread.sleep(5000);
			int cr = Integer.parseInt(contributionRate.split("%")[0]);
			int acr = Integer.parseInt(perAgeRothCatchUp.split("%")[0]);
			String newPerAgeCatchupBefore = Integer.toString(cr - acr);
			isTextMatching = Web.VerifyText(newPerAgeCatchupBefore
					+ "% "+Stock.GetParameterValue("catchupbeforetax")+" contribution with "
					+ perAgeRothCatchUp + " "+Stock.GetParameterValue("viewonlyroth"), deferrals
					.getWebElementText("VIEW ONLY SPLIT CONTRIBUTION TEXT")
					.trim(), true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is Same on Select Contribution page \nExpected:"
										+newPerAgeCatchupBefore
										+ "% "+Stock.GetParameterValue("catchupbeforetax")+" contribution with "
										+ perAgeRothCatchUp + " "+Stock.GetParameterValue("viewonlyroth")+" \nActual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT")
										.trim(),
								true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Split Contribution on Select Contribution page",
								"Split Contribution is Same on Select Contribution page. \nExpected:"
										+newPerAgeCatchupBefore
										+ "% "+Stock.GetParameterValue("catchupbeforetax")+" contribution with "
										+ perAgeRothCatchUp + " "+Stock.GetParameterValue("viewonlyroth")+" \n Actual:"+deferrals.getWebElementText(
										"VIEW ONLY SPLIT CONTRIBUTION TEXT")
										.trim(), true);
			}
			Web.clickOnElement(deferrals, "Continue button");
			Thread.sleep(4000);
			Web.clickOnElement(deferrals, "Confirm button");
			Thread.sleep(4000);
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			isTextMatching = Web.VerifyText(perAgeCatchupBefore ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax")),true);
			if (!isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Age Catch- up before After Split Contribution on My Contribution page",
								"Age Catch- up before is updated on My Contribution page \nExpected"
										+ newPerAgeCatchupBefore
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax"))
										.trim(),
								true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Age Catch- up before After Split Contribution on My Contribution page",
								"Age Catch- up before is updated on My Contribution page \nExpected"
										+ newPerAgeCatchupBefore
										+ "%"+
										"\nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("catchupbeforetax"))
										.trim(),
								true);
			}
			isTextMatching = Web.VerifyText(perAgeRothCatchUp ,deferrals
					.getContributionPercentage(Stock.GetParameterValue("viewonlyroth")),true);
			if (isTextMatching) {
				lib.Reporter
						.logEvent(
								Status.PASS,
								"Verify Age Catch- up Roth After Split Contribution on My Contribution page",
								"Age Catch- up Age roth catch up is Not updated on My Contribution page \nExpected:"
										+ perAgeRothCatchUp
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyroth"))
										.trim(),
								true);

			} else {
				lib.Reporter
						.logEvent(
								Status.FAIL,
								"Verify Age Catch- up Roth After Split Contribution on My Contribution page",
								"Age Catch- up Age roth catch up is updated on My Contribution page \nExpected:"
										+ perAgeRothCatchUp
										+
										" \nActual:"+deferrals
										.getContributionPercentage(Stock.GetParameterValue("viewonlyroth"))
										.trim(),
								true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_020_Regular_SPLIT_Change_of_Maximized_with_Catchup_to_Maximize_me_always(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();

//			if (deferrals.getCatchupMaximized() == null) {
//				deferrals.clickAddEditButton("Catch Up Add");
//				deferrals.click_Maximize_IRS_Limit();
//				deferrals.select_ContributionType("Before");
//				deferrals.myContributions_Confirmation_Page();
//				lib.Web.clickOnElement(deferrals, "MyContribution Button");
//			}
			deferrals
					.Regular_SPLIT_Change_of_Maximized_with_Catchup_to_Maximize_me_always();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_006_Catch_up_Cancel_Maximizer_on_cancellation_of_standard_maximizer(
			int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			deferrals
					.Catch_up_Cancel_Maximizer_on_cancellation_of_standard_maximizer();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_007_Catch_up_Salary_changes_on_IRS_panel(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			deferrals.Catch_up_Salary_changes_on_IRS_panel();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@Test(dataProvider = "setData")
	public void Deferral_008_Catch_up_Maximize_me_always(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			deferrals.Catch_up_Maximize_me_always();
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}

	@AfterSuite
	public void cleanupSessions() {
		lib.Web.getDriver().close();
		lib.Web.getDriver().quit();
	}

	/**
	 * Thee following scripts test for the prior contribution details of the
	 * participants that are in the following category 1. Participant in the
	 * first year of joining the plan or first year of the employment 2.
	 * Participant in the first year of catchup contribution 3. Participant who
	 * is not in the first year of employment
	 * 
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_008_Prior Plan
	 * Contributions_Participant not in first year of employment
	 * 2.SIT_PPTWEB_Deferral_007_Prior Plan Contributions_Catchup - participant
	 * within first year of employment 3.SIT_PPTWEB_Deferral_006_Previous
	 * Contributions_Participant hired in current year
	 */

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions6(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			priorContributions.get();
			Thread.sleep(5000);

			if (Web.isWebElementDisplayed(priorContributions, "EDIT")) {
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				priorContributions.enterContributionValue(
						"CATCHUP CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.waitForElement(priorContributions, "EDIT");
				Web.clickOnElement(priorContributions, "EDIT");
			}

			// perform a initial check on the prior plan contribution
			priorContributions.verifyPriorPlanContributionsPage();
			Web.clickOnElement(priorContributions, "CANCEL");
			Web.clickOnElement(priorContributions, "YES");
			// Web.VerifyText(inExpectedText, inActualText, ignoreCase)
			// enter the year to date and catchup contribution value
			priorContributions.enterContributionValue(
					"YEAR TO DATE CONTRIBUTION",
					lib.Stock.GetParameterValue("yearToDateContribution"));
			Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
			priorContributions.verifyContributionMessage(lib.Stock
					.GetParameterValue("yearToDateContribution"));
			

			if (lib.Web.isWebElementDisplayed(priorContributions, "Edit"))
				Reporter.logEvent(Status.PASS, "Verify Edit button",
						"Edit button is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Edit button",
						"Edit button is Not displayed", true);
			// this will clear thedata
			try {
				Thread.sleep(4000);
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.clickOnElement(priorContributions, "EDIT");
				Web.clickOnElement(priorContributions, "LOGOUT");
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions7(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			priorContributions.get();
			//Web.waitForElement(priorContributions, "EDIT");
			Thread.sleep(5000);
			/*if (Web.isWebElementDisplayed(priorContributions, "EDIT")) {
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				priorContributions.enterContributionValue(
						"CATCHUP CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.waitForElement(priorContributions, "EDIT");
				Web.clickOnElement(priorContributions, "EDIT");
			}*/

			// perform a initial check on the prior plan contribution
			priorContributions.verifyPriorPlanContributionsPage();
			Web.clickOnElement(priorContributions, "CANCEL");
			Web.clickOnElement(priorContributions, "YES");
			// Web.VerifyText(inExpectedText, inActualText, ignoreCase)
			// enter the year to date and catchup contribution value
			priorContributions.enterContributionValue(
					"YEAR TO DATE CONTRIBUTION",
					lib.Stock.GetParameterValue("yearToDateContribution"));
			priorContributions.enterContributionValue("CATCHUP CONTRIBUTION",
					lib.Stock.GetParameterValue("catchupContribution"));
			Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
		boolean updated=	priorContributions.verifyContributionMessage(lib.Stock
					.GetParameterValue("totalContribution"));
			
			if (updated) {
				Reporter.logEvent(
						Status.PASS,
						"Verify Year to Date Confirmation Detials Updated",
						"Year to Date Confirmation Detials Updated",
						true);
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Verify Year to Date Confirmation Detials Updated",
						"Year to Date Confirmation Detials Not Updated",
						true);
			}

			if (lib.Web.isWebElementDisplayed(priorContributions, "Edit"))
				Reporter.logEvent(Status.PASS, "Verify Edit button",
						"Edit button is displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify Edit button",
						"Edit button is Not displayed", true);
			// this will clear thedata
			try {
				Web.clickOnElement(priorContributions, "EDIT");
				priorContributions.enterContributionValue(
						"YEAR TO DATE CONTRIBUTION", "0");
				priorContributions.enterContributionValue(
						"CATCHUP CONTRIBUTION", "0");
				Web.clickOnElement(priorContributions, "SAVE AND CLOSE");
				Web.clickOnElement(priorContributions, "EDIT");
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions8(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			priorContributions.get();
			if (!lib.Web.isWebElementDisplayed(priorContributions,
					"TEXT PRIOR PLAN CONTRIBUTION"))
				Reporter.logEvent(Status.PASS,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Not Displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferrals_Participant_Prior_Contributions9_Plan_not_set_for_PPC(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			prepareDynamicTestData(Stock.GetParameterValue("queryName"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			PriorPlanContributions priorContributions = new PriorPlanContributions(
					leftmenu);

			priorContributions.get();
			if (!lib.Web.isWebElementDisplayed(priorContributions,
					"TEXT PRIOR PLAN CONTRIBUTION"))
				Reporter.logEvent(Status.PASS,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Not Displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Prior Plan Contribution Link Is Displayed",
						"Prior Plan Contribution Link Is Displayed", true);

		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferrals_Standard_Contribution_with_Chaining(int itr,
			Map<String, String> testdata) throws Exception {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);

			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);

			deferrals.get();
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType(Stock.GetParameterValue("Contributing_type"));
			if(Web.isWebElementDisplayed(deferrals, "Confirm button", true))
				Reporter.logEvent(Status.PASS,"Verify Shopping cart  page is displayed","Shopping cart  page is displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Shopping cart  page is displayed","Shopping cart  page not displayed", true);
			deferrals.chainingForStandardContribution(Stock.GetParameterValue("Chaining_Option"));
			deferrals.verifyChainingConfirmationPage(Stock.GetParameterValue("Chaining_Option"), Stock.GetParameterValue("Plan_id"));

			Web.clickOnElement(deferrals,"MyContribution Button");
			Reporter.logEvent(Status.PASS,"Verify My Contributions button is clicked","Clicked n My Contribution button", false);
			
			if (deferrals.verifyMyContributions(new DecimalFormat("##.##").format(deferrals.before_tax), "Before-tax", "Standard"))
				Reporter.logEvent(Status.PASS,"Verify Before contribution percent for Standard deferral","Before contribution percent matching", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Before contribution percent for Standar deferral","Before contribution percent matching", true);
			
			deferrals.verifyChainingMessage(Stock.GetParameterValue("Chaining_Option"));
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			Deferrals deferral=new Deferrals();
			deferral.deleteActiveChainingFromDB(Stock.GetParameterValue("SSN"),Stock.GetParameterValue("first_name"));
			deferral.refresh();
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_012M_Standard_contribution_with_chaining_to_Aftertax(int itr,
			Map<String, String> testdata) throws Exception {
		Deferrals_Standard_Contribution_with_Chaining(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_013M_Standard_contribution_with_chaining_to_Aftertax_to_NonQual(int itr,
			Map<String, String> testdata) throws Exception {
		Deferrals_Standard_Contribution_with_Chaining(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Deferral_014M_Standard_contribution_with_chaining_to_NonQua(int itr,
			Map<String, String> testdata) throws Exception {
		Deferrals_Standard_Contribution_with_Chaining(itr, testdata);
	}
	
	/**
	 * The following script adds or edits all the deferral type and then confirm
	 * it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_020_Multiple_Create
	 * contribution rate for multiple deferral type, split contribution
	 * 2.SIT_PPTWEB_Deferral_021_Multiple_Change contribution rate for multiple
	 * deferral type, split contributions
	 */
	@Test(dataProvider = "setData")
	public void Multiple_deferral_split_contribution(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }
			leftmenu = new LeftNavigationBar(homePage);
			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			if (lib.Web.isWebElementDisplayed(deferrals,"Table Header Contribution",true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page","My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page","My Contributions page is not displayed", true);
			
			if(Stock.GetParameterValue("create_or_edit").equalsIgnoreCase("create")){
				
				//Addind Regular Deferral and split
				if (deferrals.clickAddEditButton("Standard Add"))
					Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
				else
					Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.select_ContributionType("Split");
				
				
				//Adding Catch up deferral and split
				if (deferrals.clickAddEditButton("Catch Up Add"))
					Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_catchup_contribution();
				
				//Adding After-Tax deferral
				if (deferrals.clickAddEditButton("After Tax Add"))
					Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				lib.Web.clickOnElement(deferrals, "Continue button");
				
				//Adding Bonus defferal and split
				/*if (deferrals.clickAddEditButton("Bonus Add"))
					Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_bonus_contribution();*/
				
				//Adding Other deferral and split
				if (deferrals.clickAddEditButton("Other Add"))
					Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_Other_contribution();
			}
			
			if(Stock.GetParameterValue("create_or_edit").equalsIgnoreCase("edit")){
				
				//Addind Regular Deferral and split
				if (deferrals.clickAddEditButton("Standard Edit"))
					Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
				else
					Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.select_ContributionType("Split");
				
				
				//Adding Catch up deferral and split
				if (deferrals.clickAddEditButton("Catch Up Edit"))
					Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_catchup_contribution();
				
				//Adding After-Tax deferral
				if (deferrals.clickAddEditButton("After Tax Edit"))
					Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				lib.Web.clickOnElement(deferrals, "Continue button");
				
				//Adding Bonus defferal and split
				/*if (deferrals.clickAddEditButton("Bonus Edit"))
					Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_bonus_contribution();
				*/
				//Adding Other deferral and split
				if (deferrals.clickAddEditButton("Other Edit"))
					Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
				else
					Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
				deferrals.click_Select_Your_Contribution_Rate();
				deferrals.split_Other_contribution();
			}

				//add auto increase
				deferrals.add_Auto_Increase("Before Add Auto Increase");
				deferrals.add_Auto_Increase("Roth Add auto Increase");
				deferrals.add_Auto_Increase("After Add Auto Increase");
				deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
				deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
				//deferrals.add_Auto_Increase("Roth Bonus Add Auto Increase");
				//deferrals.add_Auto_Increase("Before Bonus Add Auto Increase");
				
				//Confirm all the deferrals
				deferrals.myContributions_Confirmation_Page();

				lib.Web.clickOnElement(deferrals, "MyContribution Button");
				if (lib.Web.isWebElementDisplayed(deferrals,
						"Table Header Contribution", true))
					Reporter.logEvent(Status.PASS, "Verify My Contributions page",
							"My Contributions page is  displayed", true);
				else
					Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
							"My Contributions page is not displayed", true);
			
		}
		catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	@Test(dataProvider = "setData")
	public void Create_Multiple_deferral_split_contribution(int itr, Map<String, String> testdata) {
		Multiple_deferral_split_contribution(itr, testdata);
	}
	
	@Test(dataProvider = "setData")
	public void Edit_Multiple_deferral_split_contribution(int itr, Map<String, String> testdata) {
		Multiple_deferral_split_contribution(itr, testdata);
	}
	
	/**
	 * The following script Standard Deferral and confirms it and Verify Plan rules
	 * 
	 * Covered Manual Test Cases: SIT_PPTWEB_Deferral_010M_Verify combined rules on 
	 * deferral types with election greater than zero
	 */
	@Test(dataProvider = "setData")
	public void Deferral_010M_Verify_combined_rules_with_election_greater_than_zero(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			

			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			Web.clickOnElement(deferrals, "Continue button");
			Web.clickOnElement(deferrals, "PLAN RULE");
			Web.waitForElement(deferrals, "Header PlanRule");

			if (lib.Web.isWebElementDisplayed(deferrals,
					"Header PlanRule"))
				Reporter.logEvent(Status.PASS, "Verify Pan Rule PopUp is Displayed",
						"Pan Rule PopUp is Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Pan Rule PopUp is Displayed",
						"Pan Rule PopUp is Not Displayed", true);
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Text PlanRule"))
				Reporter.logEvent(Status.PASS, "Verify Pan Rule Text is Displayed and Proper",
						"Pan Rule Text is Displayed and Proper", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify Pan Rule Text is Displayed and Proper",
						"Pan Rule Text is Not Proper", true);
			Web.clickOnElement(deferrals, "OK BUTTON");
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * The following script Standard Deferral and confirms it and Verify Plan rules
	 * 
	 * Covered Manual Test Cases: SIT_PPTWEB_Deferral_011M_Verify combined rules 
	 * on deferral types with zero election
	 */
	@Test(dataProvider = "setData")
	public void Deferral_011M_Verify_combined_rules_with_zero_election(int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			//LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			// if(homePage.getNoOfPlansFromDB(lib.Stock.GetParameterValue("Particicpant_ssn"))
			// <= 2)
			// leftmenu = new LeftNavigationBar(homePage);
			// else {
			// MyAccountsPage accountPage = new MyAccountsPage(homePage);
			// leftmenu = new LeftNavigationBar(accountPage);
			// }

			LeftNavigationBar	leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			try {
				lib.Web.waitForElement(deferrals, "Table Header Contribution");
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);

			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			Web.clickOnElement(deferrals, "Continue button");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions Page is Displayed and combined rules a1re not checked on deferral types ",
						"My Contributions page is  displayed and combined rules are not checked on deferral types ", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions Page is Displayed and combined rules a1re not checked on deferral types ",
						"My Contributions page is not displayed and combined rules are checked on deferral types ", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following scriptStandard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_021_Regular_SPLIT- Company match suppressed
	 */
	@Test(dataProvider = "setData")
	public void Deferral_021_Regular_SPLIT_Company_match_suppressed(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3
			deferrals.verifyCompanyMatchChangesDynamically();
			//Step4
			// TODO
			//Step 5
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 6 & 7
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			lib.Web.clickOnElement(deferrals, "Continue button");
			//Step 9 10 11 12 13
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_Roth"));
			//Step 14
			deferrals.myContributions_Confirmation_Page();
			//Step 15
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following scriptStandard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_022_Regular_SPLIT- Company match
	 */
	@Test(dataProvider = "setData")
	public void Deferral_022_Regular_SPLIT_Company_match(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3
			if (lib.Web.isWebElementDisplayed(deferrals,
					"TEXT COMPANY MATCH", true))
				Reporter.logEvent(Status.PASS, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify COMPANY MATCH is Displayed",
						"COMPANY MATCH is not displayed", true);
			//Step4
			
			// TODO
			
			//Step 5
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 6 & 7
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 8
			lib.Web.clickOnElement(deferrals, "Continue button");
			//Step 9 10 11 12 13
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_Roth"));
			//Step 14
			deferrals.myContributions_Confirmation_Page();
			//Step 15
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_025_Regular_BEFORE-Select Another contribution rate_subcode ADJRUNDATE or GENERIC
	 */
	@Test(dataProvider = "setData")
	public void Deferral_025_Regular_BEFORE_Select_Another_contribution_rate_subcode_ADJRUNDATE_GENERIC(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			//Update date picker to calendar
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("ga_PlanId"));
			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			//Step 18
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_026_Regular_SPLIT- Maximizer to custom with same contribution rate
	 */
	@Test(dataProvider = "setData")
	public void Deferral_026_Regular_SPLIT_Maximizer_to_custom_with_same_contribution_rate(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			
			//Precondition
			
			if (deferrals.clickAddEditButton("Standard Add"))
				
			deferrals.click_Maximize_IRS_Limit();
		
			deferrals.select_ContributionType("split");
			
			deferrals.myContributions_Confirmation_Page();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			
			//Step 1
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 
			Web.clickOnElement(deferrals, "RADIO SELECT OTHER CONTRIBUTION");
			
			//Step 4
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step5
			Web.clickOnElement(deferrals, "Continue button");
			
			//Step 6
			deferrals.myContributions_Confirmation_Page();
		
			//Step 7
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_027_Multiple_No_changes_in_the_rate
	 */
	@Test(dataProvider = "setData")
	public void Deferral_027_Multiple_No_changes_in_the_rate(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
				//Step 1			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			
			//PreCondition
			//AddingStandard deferral and split
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.select_ContributionType("Split");
			
			//Adding Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Add"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_catchup_contribution();
			
			//Adding After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Adding Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();*/
			
			//Adding Other deferral and split
			if (deferrals.clickAddEditButton("Other Add"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_Other_contribution();
			//Confirm all the deferrals
			deferrals.myContributions_Confirmation_Page();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			
			
			//Step 2
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.click_Select_Your_Contribution_Rate();
			//Step 5 & 6
			deferrals.select_ContributionType("Split");
			//Step 7
			//Editing After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Edit"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 8
			//Editing Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Edit"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_catchup_contribution();
			
			//Editing Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Edit"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();
			*/
			//Editing Other deferral and split
			if (deferrals.clickAddEditButton("Other Edit"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_Other_contribution();
		
			//Step 9 & 10
			//add auto increase
			deferrals.add_Auto_Increase("Before Add Auto Increase");
			deferrals.add_Auto_Increase("Roth Add auto Increase");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
			//deferrals.add_Auto_Increase("Roth Bonus Add Auto Increase");
			//deferrals.add_Auto_Increase("Before Bonus Add Auto Increase");
			
			//Confirm all the deferrals
			/*deferrals.myContributions_Confirmation_Page();

			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);*/
			
			//Step 11
			deferrals.clickAddEditButton("Standard Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.select_ContributionType("Split");
	
			deferrals.clickAddEditButton("After Tax Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			deferrals.clickAddEditButton("Catch Up Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.split_catchup_contribution();
			
			/*deferrals.clickAddEditButton("Bonus Edit");
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.split_bonus_contribution();*/
			
			deferrals.clickAddEditButton("Other Edit");
				
			deferrals.click_Select_Your_Contribution_Rate();
			
			deferrals.split_Other_contribution();
			
			//Confirm all the deferrals
			
			if (!Web.isWebElementDisplayed(deferrals,
					"Confirm button"))
				Reporter.logEvent(Status.PASS, "Verify 'Confirm And Continue' Button is Not Displayed",
						"'Confirm And Continue' Button is Not Displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Confirm And Continue' Button is Not Displayed",
						"'Confirm And Continue' Button is Displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_029_Multiple_Change contribution rate for multiple deferral type, split contribution - Dollar deferrals
	 */
	@Test(dataProvider = "setData")
	public void Deferral_029_Multiple_Change_Dollar_deferrals(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			//Step 1			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			
			//Step 2
			//AddingStandard deferral and split
			if (deferrals.clickAddEditButton("Standard Edit"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			
			//Step 3
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			//Step 4 & 5 & 6
			deferrals.select_ContributionType("Split");
			
			//Step 7
			//Adding After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Edit"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 8
			//Adding Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Edit"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_catchup_contribution();
			
			
			//Adding Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();*/
			
			//Adding Other deferral and split
			if (deferrals.clickAddEditButton("Other Edit"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_Other_contribution();
			
			//Step 9 & 10
			
			deferrals.add_Auto_Increase("Before Add Auto Increase");
			deferrals.add_Auto_Increase("Roth Add auto Increase");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
			
			//Step 11
			//Confirm all the deferrals
			deferrals.myContributions_Confirmation_Page();
			
			
			
			deferrals.verifyDollarInConfirmationPage();
			
			//Step 12
			
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_028_Multiple_Create contribution rate for multiple deferral type, split contribution - Dollar deferrals
	 */
	@Test(dataProvider = "setData")
	public void Deferral_028_Multiple_Create_Dollar_deferrals(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			//Step 1			
			lib.Web.waitForElement(deferrals, "Table Header Contribution");
			
			//Step 2
			//AddingStandard deferral and split
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,"Verify Standard contribution page","Standard Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL,"Verify Standard contribution page","Standard Contributions page is not displayed", true);
			
			//Step 3
			deferrals.select_Another_Contribution_Rate_Dollar();
			
			//Step 4 & 5 & 6
			deferrals.select_ContributionType("Split");
			
			//Step 7
			//Adding After-Tax deferral
			if (deferrals.clickAddEditButton("After Tax Add"))
				Reporter.logEvent(Status.PASS,"Verify After-tax contribution page","After-tax Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify After-tax contribution page","After-tax Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 8
			//Adding Catch up deferral and split
			if (deferrals.clickAddEditButton("Catch Up Add"))
				Reporter.logEvent(Status.PASS,"Verify Catch up contribution page","Catch up Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Catch up contribution page","Catch up Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_catchup_contribution();
			
			
			//Adding Bonus defferal and split
			/*if (deferrals.clickAddEditButton("Bonus Add"))
				Reporter.logEvent(Status.PASS,"Verify Bonus contribution page","Bonus Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Bonus contribution page","Bonus Contributions page is not displayed", true);
			deferrals.click_Select_Your_Contribution_Rate();
			deferrals.split_bonus_contribution();*/
			
			//Adding Other deferral and split
			if (deferrals.clickAddEditButton("Other Add"))
				Reporter.logEvent(Status.PASS,"Verify Other contribution page","Other Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Other contribution page","Other Contributions page is not displayed", true);
			deferrals.select_Another_Contribution_Rate_Dollar();
			deferrals.split_Other_contribution();
			
			//Step 9 & 10
			
			deferrals.add_Auto_Increase("Before Add Auto Increase");
			deferrals.add_Auto_Increase("Roth Add auto Increase");
			deferrals.add_Auto_Increase("After Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Before Add Auto Increase");
			deferrals.add_Auto_Increase("Catch Up Roth Add Auto Increase");
			
			//Step 11
			//Confirm all the deferrals
			deferrals.myContributions_Confirmation_Page();
			
			//Step 12
			
			deferrals.verifyDollarInConfirmationPage();
			
			//Step 13
			
			lib.Web.clickOnElement(deferrals, "MyContribution Button");
			
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_030_Regular_BEFORE-Update auto increase when payroll calendar ADJRUNDATE
	 */
	@Test(dataProvider = "setData")
	public void Deferral_030_Regular_BEFORE_Update_auto_increase_when_payroll_calendar_ADJRUNDATE(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Update date picker to dropdown
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("sdsv_subcode"),Stock.GetParameterValue("ga_PlanId"));
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (lib.Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step  4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 3 & 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase_Date_DropDown(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			deferrals.verifyConfirmationMessage();
			
			//Step 18
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_031_Regular_BEFORE-Update auto increase when payroll calendar GENERIC
	 */
	@Test(dataProvider = "setData")
	public void Deferral_031_Regular_BEFORE_Update_auto_increase_when_payroll_calendar_GENERIC(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Update date picker to Calendar
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("sdsv_subcode"),Stock.GetParameterValue("ga_PlanId"));
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			deferrals.verifyConfirmationMessage();
			//Step 18
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_032_Regular_BEFORE-Update auto increase when payroll calendar ADJRUN_PAYROLLDATE
	 */
	@Test(dataProvider = "setData")
	public void Deferral_032_Regular_BEFORE_Update_auto_increase_when_payroll_calendar_ADJRUN_PAYROLLDATE(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Update date picker to dropdown
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], Stock.GetParameterValue("sdsv_subcode"),Stock.GetParameterValue("ga_PlanId"));
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
						
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step 3 & 4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 to 16
			deferrals.add_Auto_Increase_Date_DropDown("Before Add Auto Increase");
			
			//Step 17
			deferrals.myContributions_Confirmation_Page();
			deferrals.verifyConfirmationMessage();
			//Step 18
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * The following script Standard Deferral and confirms it
	 * 
	 * Covered Manual Test Cases: 1.Deferral_003A_Nonvisible Bonus deferral not displayed on confirm page
	 */
	@Test(dataProvider = "setData")
	public void Deferral_003A_Nonvisible_Bonus_deferral_not_displayed_on_confirm_page(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Step 2
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			//Step  4
			deferrals.verifyPayPeriodAmountIsMatching();
			//Step 5
			deferrals.verifyAnnualCompensationDisplayed();
			
			//Step6 Verify Company Match is Displayed as per Rules
			
			// TODO 
			
			//Step 7
			deferrals.verifyCompanyMatchChangesDynamically();
			
			
			//Step 3 & 8
			deferrals.click_Select_Your_Contribution_Rate();
			
			//Step 9
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			//Step 10
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			//Step 11 
			deferrals.add_Auto_Increase(lib.Stock
					.GetParameterValue("Add_auto_increase_BeforeTax"));
			
			//Step 12
			deferrals.myContributions_Confirmation_Page();
			
			if (!deferrals.verifyContributionTypeIsDisplayedInConfirmationPage("Bonus"))
				Reporter.logEvent(Status.PASS, "Verify 'Bonus' is Displayed in Confirmation page",
						"'Bonus' is Not Displayed in Confirmation page", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify 'Bonus' is Displayed in Confirmation page",
						"'Bonus' is Displayed in Confirmation page", true);
			
			//Step 13
			Web.clickOnElement(deferrals, "MyContribution Button");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * The following script Verify Pending Deferral
	 * 
	 * Covered Manual Test Cases: 1.SIT_PPTWEB_Deferral_035_Pending Deferral View
	 */
	@Test(dataProvider = "setData")
	public void Deferral_035_Pending_Deferral_View(int itr,
			Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			LeftNavigationBar leftmenu;
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			
			leftmenu = new LeftNavigationBar(homePage);

			Deferrals deferrals = new Deferrals(leftmenu);
			deferrals.get();
			
			Web.waitForElement(deferrals, "Table Header Contribution");
			//Step 1
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution"))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			//Precondition
			if (deferrals.clickAddEditButton("Standard Add"))
				Reporter.logEvent(Status.PASS,
						"Verify Standard contribution page",
						"Standard Contributions page is  displayed", false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Standard contribution page",
						"Standard Contributions page is not displayed", true);
			
			deferrals.click_Select_Your_Contribution_Rate();
			
			
			deferrals.select_ContributionType(lib.Stock
					.GetParameterValue("Contribution_type"));
			
			lib.Web.clickOnElement(deferrals, "Continue button");
			
			deferrals.myContributions_Confirmation_Page();
			
			Web.clickOnElement(deferrals, "MyContribution Button");
			
			//update DB effective date to get pending deferral
			DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar calendar = Calendar.getInstance();         
			calendar.add(Calendar.DATE, 1);
			String date=dateFormat.format(calendar.getTime());
			System.out.println("DATE"+date);
			String[] sqlQuery= Stock.getTestQuery(Stock.GetParameterValue("queryName"));
			DB.executeUpdate(sqlQuery[0], sqlQuery[1], date,Stock.GetParameterValue("username").substring(0, 9));
			Web.getDriver().navigate().refresh();
			deferrals.ClickPendingDeferralLink("Before Tax");
			deferrals.verifyPendingDeferralModal("Before Tax");
			Web.clickOnElement(deferrals, "Button Close Pending Deferral");
			if (Web.isWebElementDisplayed(deferrals,
					"Table Header Contribution", true))
				Reporter.logEvent(Status.PASS, "Verify My Contributions page",
						"My Contributions page is  displayed", true);
			else
				Reporter.logEvent(Status.FAIL, "Verify My Contributions page",
						"My Contributions page is not displayed", true);
			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e
					.getCause().getMessage(), true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					"Assertion Failed!!", true);

		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}

