package app.pptweb.testcases.loans;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.deferrals.Deferrals;
import pageobjects.enrollment.Enrollment;
import pageobjects.general.LeftNavigationBar;
import pageobjects.investment.ManageMyInvestment;
import pageobjects.landingpage.LandingPage;
import pageobjects.liat.RetirementIncome;
import pageobjects.loan.RequestLoanPage;
import pageobjects.login.LoginPage;
import pageobjects.login.TwoStepVerification;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;
@SuppressWarnings("unused")
public class LoansTestCases {
	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	public Map<String, String> mapInvestmentOptionsReviewPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsConfirmPage = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptions = new HashMap<String, String>();
	public Map<String, String> mapInvestmentOptionsRebalance = new HashMap<String, String>();
	public List<String> ConfirmationNos = new ArrayList<String>();
	LoginPage login;
	String tcName;
	static String printTestData="";
	static String userName=null;
	static String confirmationNumber="";

	@BeforeClass
	public void ReportInit() {
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
	private String printTestData() throws Exception {
		printTestData="";
		for (Map.Entry<String, String> entry : Stock.globalTestdata.get(Thread.currentThread().getId()).entrySet()) {
			if(!entry.getKey().equalsIgnoreCase("PASSWORD"))
				printTestData=printTestData+entry.getKey() + "="+ entry.getValue() +"\n";
		}
	 return printTestData;
	}
	
	/**
	 * This Test Case to verify General Purpose Loan Repayment Page
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void DDTC_27488_General_Purpose_Loan_Repayment_Term_Page_Verification(int itr, Map<String, String> testdata) {

		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_REPORTER_MAP.get(Thread.currentThread().getId())+"_"+Stock.getConfigParam("BROWSER"));
			lib.Reporter.logEvent(Status.INFO,"Test Data used for this Test Case:",printTestData(),false);
			
			//Step 1 to 3
			LoginPage login = new LoginPage();
			TwoStepVerification mfaPage = new TwoStepVerification(login);
			LandingPage homePage = new LandingPage(mfaPage);
			LeftNavigationBar leftmenu = new LeftNavigationBar(homePage);
			RequestLoanPage requestLoan= new RequestLoanPage(leftmenu);
			requestLoan.get();

			//Step 4
			requestLoan.isTextFieldDisplayed("AVAILABLE TO BORROW");
			String loanAmt =requestLoan.getMaximumLoanAmount();
			if(!loanAmt.isEmpty())
				Reporter.logEvent(Status.PASS,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is displayed \nLoan Maximum:"+loanAmt, true);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Loan Maximum Amount is displayed",
						"Loan Maximum Amount is not displayed \nLoan Maximum:"+loanAmt, true);
		
			//Step 5
			String expectedNoofLoans =requestLoan.getMaximumLoansAllowed();
			String ActualNoofLoans =requestLoan.getMaximumLoansAllowedforPlan(Stock.GetParameterValue("ga_id"));
			if(expectedNoofLoans.equalsIgnoreCase(ActualNoofLoans))
				Reporter.logEvent(Status.PASS,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, false);
			
			else
				Reporter.logEvent(Status.FAIL,
						"Verify Maximum Loans Allowed is Matching",
						"Maximum Loans Allowed is not displayed as per Data base\nExpected No.Of Loans:"+expectedNoofLoans+"\nActual No.Of Loans:"+ActualNoofLoans, true);
			//Step 6
			//TODO
			//Step 7
			requestLoan.verifyMaximumLoansForLoan(Stock.GetParameterValue("ga_id"));
			//Step 8
			requestLoan.verifyLoansDisclaimer();
			//Step 9
			Web.clickOnElement(requestLoan, "Button Request A New Loan");
			
			Web.waitForElement(requestLoan, "LOAN TYPE GENERAL");
			
			
			requestLoan.isTextFieldDisplayed("Loan purpose");
			requestLoan.isTextFieldDisplayed("Maximum loan");
			requestLoan.isTextFieldDisplayed("Minimum loan");
			requestLoan.isTextFieldDisplayed("Repayment term");
			requestLoan.isTextFieldDisplayed("Documentation required");
			requestLoan.isTextFieldDisplayed("Interest rate");
			requestLoan.isTextFieldDisplayed("Repayment");
			requestLoan.isTextFieldDisplayed("Fees");
			
			requestLoan.selectLoneType(Stock.GetParameterValue("loanType"));
			
			requestLoan.EnterLoanAmtAndTerm("$1000", "12");
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Throwable t = e.getCause();
			String msg = "Unable to retrive cause from exception. Click below link to see stack track.";
			if (null != t) {
				msg = t.getMessage();
			}
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", msg, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured", ae.getMessage(), true);
			// throw ae;
		} finally {
			try {
				Web.getDriver().switchTo().defaultContent();
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
}