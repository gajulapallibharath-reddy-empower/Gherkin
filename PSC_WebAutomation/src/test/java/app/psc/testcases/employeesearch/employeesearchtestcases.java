package app.psc.testcases.employeesearch;

import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import pageobjects.employeesearch.EmployeeSearch;
import pageobjects.homepage.HomePage;
import lib.Reporter;

import com.aventstack.extentreports.*;

import lib.Stock;
import lib.Web;
import core.framework.Globals;
import framework.util.CommonLib;

public class employeesearchtestcases {

	private LinkedHashMap<Integer, Map<String, String>> testData = null;
	EmployeeSearch employeesearch;
	String actualErrorMessage = "";
	ResultSet resultset;
	HomePage homePage;
	
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

	/**
	 * This test case verifies the search results for valid SSN input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC001_Search_Employee_For_Valid_SSN(int itr,
			Map<String, String> testdata) {				
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for SSN input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN(Stock.GetParameterValue("SSN"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid SSN",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid SSN",
						"Search results are not displayed correctly", true);
			}			
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case verifies the search results for invalid SSN input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC002_Search_Employee_For_Invalid_SSN(int itr,
			Map<String, String> testdata) {				
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for SSN input" + ":" +"Negative flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN(Stock.GetParameterValue("SSN"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {

				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid SSN",
						"Search results are displayed correctly", false);
				Thread.sleep(2000);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid SSN",
						"Search results are not displayed correctly", true);
			}
			Reporter.finalizeTCReport();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (AssertionError ae) {
			ae.printStackTrace();
			Globals.assertionerror = ae;
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
	 * This test case verifies the search results for valid Name input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC003_Search_Employee_For_Valid_Name_Input(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Name input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByName(Stock.GetParameterValue("Name"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid SSN",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid SSN",
						"Search results are not displayed correctly", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case verifies the search results are not displayed for invalid Name input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC004_Search_Employee_For_Invalid_Name_Input(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Name input" + ":" +"Negative flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByName(Stock.GetParameterValue("Name"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {

				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid Name",
						"Search results are displayed correctly", false);
				Thread.sleep(2000);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid Name",
						"Search results are not displayed correctly", false);
			}
			Reporter.finalizeTCReport();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);

		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);			
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case verifies the search results for valid EmployeeID input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC005_Search_Employee_For_Valid_EmployeeID_Input(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByEmployeeId(Stock
					.GetParameterValue("EmployeeID"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid Name",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid Name",
						"Search results are not displayed correctly", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * This test case verifies the search results are not displayed for invalid Employee ID input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC006_Search_Employee_For_Invalid_EmployeeID_Input(int itr,
			Map<String, String> testdata) {		
		try {	
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID input" + ":" +"Negative flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByEmployeeId(Stock
					.GetParameterValue("EmployeeID"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are displayed correctly", false);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are not displayed correctly", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);

		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {

			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * This test case verifies the search results are not displayed for plans with divisions
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC07_Verify_employee_details_displayed_for_plans_with_divisions(
			int itr, Map<String, String> testdata) {				
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for plans having divisions", false);
			employeesearch = new EmployeeSearch().get();			
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithDivisions"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			employeesearch.navigateToEmployeeTab();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	/*
	 * ---- Regression Test cases starts from here -------
	 * =========================================================================
	 * ====
	 */
	/**
	 * This testcase validates the search results for valid EmployeeID input
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC08_Verify_employee_details_displayed_for_valid_participant_id(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for ParticipantID input" + ":" +"Positive flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchByParticipantID(Stock
					.GetParameterValue("ParticipantId"));
			Thread.sleep(3000);
			if (employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(Status.PASS,
						"Check if search results are displayed for valid Name",
						"Search results are displayed correctly", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if search results are displayed for valid Name",
						"Search results are not displayed correctly", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}/**
	 * This testcase validates the search results for invalid EmployeeID input
	 * @param itr
	 * @param testdata
	 */

	@Test(dataProvider = "setData")
	public void TC09_Verify_employee_details_displayed_for_invalid_participant_id(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID input" + ":" +"Negative  flow", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchByParticipantID(Stock
					.GetParameterValue("ParticipantId"));
			Thread.sleep(3000);
			if (!employeesearch.isSearchResultsDisplayed()) {
				Reporter.logEvent(
						Status.PASS,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are displayed correctly", false);
				actualErrorMessage = employeesearch
						.getErrorMessageTextforInvalidSearch();
				if (CommonLib.VerifyText(
						Stock.GetParameterValue("ExpectederrorMessage"),
						actualErrorMessage, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if proper error message is displayed",
							"Proper error message is displayed", false);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if proper error message is displayed",
							"Proper error message is not displayed", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if search results are displayed for invalid EmployeeID",
						"Search results are not displayed correctly", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * This testcase validates the search results for SSN Doesn't contain duplicate value
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC010_Verify_the_Search_results_for_SSN_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for SSN Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeBySSN(Stock.GetParameterValue("SSN"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	/**
	 * This testcase validates the search results for Name Doesn't contain duplicate value
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC011_Verify_the_Search_results_for_Name_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Name Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByName(Stock.GetParameterValue("Name"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	/**
	 * This testcase validates the search results for EmployeeID Doesn't contain duplicate value
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC012_Verify_the_Search_results_for_EmployeeID_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for EmployeeID Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchEmployeeByEmployeeId(Stock
					.GetParameterValue("EmployeeID"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
/**
 * This testcase validates the search results for participant ID Doesn't contain duplicate value
 * @param itr
 * @param testdata
 */
	@Test(dataProvider = "setData")
	public void TC013_Verify_the_Search_results_for_pptID_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for pptID Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();
			employeesearch.searchByParticipantID(Stock
					.GetParameterValue("Participant ID"));
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
/**
 * This testcase validates the search results for plans with Division Doesn't contain duplicate value
 * @param itr
 * @param testdata
 */
	@Test(dataProvider = "setData")
	public void TC014_Verify_the_Search_results_for_Plans_with_Div_doesnot_Contain_duplicate_Values(
			int itr, Map<String, String> testdata) {
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for plans with Division Doesn't contain duplicate values", false);
			employeesearch = new EmployeeSearch().get();	
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithDivisions"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			Thread.sleep(3000);
			if (employeesearch.checkIfduplicateExists()) {
				Reporter.logEvent(Status.PASS,
						"Check if any duplicate record exists",
						"There is no duplicate record in search results", false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Check if any duplicate record exists",
						"The search result contains duplicate records", true);
			}
			employeesearch.navigateToEmployeeTab();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
	/**
	 * <pre>This testcase validates the search results for Plans that are having single division</pre>
	 * @param itr
	 * @param testdata
	 */
	@Test(dataProvider = "setData")
	public void TC015_Check_For_Plans_With_one_division(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Plans that are having only one division", false);
			employeesearch = new EmployeeSearch().get();			
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithSingleDivision"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			employeesearch.dismissErrorBox();
			employeesearch.navigateToEmployeeTab();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

	}
/**
 * <pre>This testcase validates the search results for Plans that are having multiple division</pre>
 * @param itr
 * @param testdata
 */
	@Test(dataProvider = "setData")
	public void TC016_Check_For_Plans_With_multiple_divisions(int itr,
			Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					"This testcase validates the search results for Plans that are having multiple division", false);
			employeesearch = new EmployeeSearch().get();			
			resultset = employeesearch.selectPlanForUser(
					Stock.getTestQuery("getPlanswithMorethanSingleDivision"),
					Stock.GetParameterValue("username"));
			employeesearch.selectPlanFromResultset(resultset);
			Thread.sleep(3000);
			employeesearch.navigateToEmployeeTab();
			employeesearch.searchByDivision();
			employeesearch.dismissErrorBox();
			employeesearch.navigateToEmployeeTab();
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			String exceptionMessage = e.getMessage();
			Reporter.logEvent(Status.FAIL, "A run time exception occured.",
					exceptionMessage, true);
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	/**
	 * <pre>This testcase validates general employment informations</pre>
	 * @param itr
	 * @param testdata
	 */
		@Test(dataProvider = "setData")
		public void TC017_Employment_Info_Verification(int itr,
				Map<String, String> testdata) {		
			try {
				Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
				Reporter.logEvent(Status.INFO, "Testcase Description",
						"This testcase validates general employment informations", false);
				employeesearch = new EmployeeSearch().get();	
				employeesearch.searchEmployeeBySSN(employeesearch.getSSNFromDB());//need to fetch employee from database
				employeesearch.verifyEmploymentInfoANDLabels();
				employeesearch.updateEmploymentInfoModalWindow();
				
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				String exceptionMessage = e.getMessage();
				Reporter.logEvent(Status.FAIL, "A run time exception occured.",
						exceptionMessage, true);
			} catch (Error ae) {
				ae.printStackTrace();
				Globals.error = ae;
				String errorMsg = ae.getMessage();
				Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
						errorMsg, true);
			} finally {
				try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		}
	
		
		/**
		 * <pre>This testcase validates general contact informations</pre>
		 * @param itr
		 * @param testdata
		 */
			@Test(dataProvider = "setData")
			public void TC018_Employee_Contact_Info_Verification(int itr,
					Map<String, String> testdata) {		
				try {
					Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
					Reporter.logEvent(Status.INFO, "Testcase Description",
							"This testcase validates general employment informations", false);
					employeesearch = new EmployeeSearch().get();	
					employeesearch.searchEmployeeBySSN(employeesearch.getSSNFromDB());
					employeesearch.contactInFoSectionValidation();
					employeesearch.contactInFoLabelValidation();
					employeesearch.contactInFoValidationModalWindow();
					employeesearch.validateContactInfoWithDB();
					
				} catch (Exception e) {
					e.printStackTrace();
					Globals.exception = e;
					String exceptionMessage = e.getMessage();
					Reporter.logEvent(Status.FAIL, "A run time exception occured.",
							exceptionMessage, true);
				} catch (Error ae) {
					ae.printStackTrace();
					Globals.error = ae;
					String errorMsg = ae.getMessage();
					Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
							errorMsg, true);
				} finally {
					try {
						Reporter.finalizeTCReport();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			
			
			/**
			 * <pre>This testcase validates beneficiary details with DB</pre>
			 * @param itr
			 * @param testdata
			 */
				@Test(dataProvider = "setData")
				public void TC019_Verify_Beneficiary_Info(int itr,
						Map<String, String> testdata) {		
					try {
						Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
						Reporter.logEvent(Status.INFO, "Testcase Description",
								"This testcase validates general employment informations", false);
						employeesearch = new EmployeeSearch().get();
						employeesearch.str1 = employeesearch.getBeneficiaryEmployeeSSN();
						employeesearch.searchEmployeeBySSN(employeesearch.str1[0]);
						employeesearch.navigateToEmployeeOverViewPage();
						employeesearch.navigateBeneficiaryPage();
						employeesearch.validateBeneficiaryWithDB();
					} catch (Exception e) {
						e.printStackTrace();
						Globals.exception = e;
						String exceptionMessage = e.getMessage();
						Reporter.logEvent(Status.FAIL, "A run time exception occured.",
								exceptionMessage, true);
					} catch (Error ae) {
						ae.printStackTrace();
						Globals.error = ae;
						String errorMsg = ae.getMessage();
						Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
								errorMsg, true);
					} finally {
						try {
							Reporter.finalizeTCReport();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				
				
				/**
				 * <pre>This testcase validates beneficiary details with DB</pre>
				 * @param itr
				 * @param testdata
				 */
					@Test(dataProvider = "setData")
					public void TC020_Recently_Viewed_Employee(int itr,
							Map<String, String> testdata) {		
						try {
							Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
							Reporter.logEvent(Status.INFO, "Testcase Description",
									"This testcase validates general employment informations", false);
							employeesearch = new EmployeeSearch().get();
							employeesearch.searchEmployeeBySSN(employeesearch.getSSNFromDB());
							employeesearch.navigateToEmployeeOverViewPage();
							employeesearch.Verify_Recently_Viewed_Employee();
							
						} catch (Exception e) {
							e.printStackTrace();
							Globals.exception = e;
							String exceptionMessage = e.getMessage();
							Reporter.logEvent(Status.FAIL, "A run time exception occured.",
									exceptionMessage, true);
						} catch (Error ae) {
							ae.printStackTrace();
							Globals.error = ae;
							String errorMsg = ae.getMessage();
							Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
									errorMsg, true);
						} finally {
							try {
								Reporter.finalizeTCReport();
							} catch (Exception e1) {
								e1.printStackTrace();
							}
						}
					}
					
					
					/**
					 * <pre>This testcase to update salary details and validate with DB</pre>
					 * @param itr
					 * @param testdata
					 */
						@Test(dataProvider = "setData")
						public void TC021_Salary_View_And_Update(int itr,
								Map<String, String> testdata) {		
							try {
								Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
								Reporter.logEvent(Status.INFO, "Testcase Description",
										"This testcase validates general employment informations", false);
								employeesearch = new EmployeeSearch().get();
								homePage = new HomePage();
								homePage.searchPlanWithIdOrName(employeesearch.getSSNAndGaIdForSalaryEmp()[0]);
								employeesearch.navigateToEmployeeTab();
								employeesearch.searchEmployeeBySSN(employeesearch.individual[2]);
								employeesearch.navigateToEmployeeOverViewPage();
								employeesearch.navigateToEmpDetailPage();
								employeesearch.verifySalarySection();
								employeesearch.updateSalaryinfo();
							} catch (Exception e) {
								e.printStackTrace();
								Globals.exception = e;
								String exceptionMessage = e.getMessage();
								Reporter.logEvent(Status.FAIL, "A run time exception occured.",
										exceptionMessage, true);
							} catch (Error ae) {
								ae.printStackTrace();
								Globals.error = ae;
								String errorMsg = ae.getMessage();
								Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
										errorMsg, true);
							} finally {
								try {
									Reporter.finalizeTCReport();
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							}
						}
	
						

						
						/**
						 * <pre>This testcase to verify Paycheck contribution section of an employee</pre>
						 * @param itr
						 * @param testdata
						 */
							@Test(dataProvider = "setData")
							public void TC022_Paycheck_contribution_View(int itr,
									Map<String, String> testdata) {		
								try {
									Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
									Reporter.logEvent(Status.INFO, "Testcase Description",
											"This testcase validates general employment informations", false);
									employeesearch = new EmployeeSearch().get();
									employeesearch.searchEmployeeBySSNAllPlans(employeesearch.ssnOfPayCheckContribution());
									employeesearch.navigateToEmployeeOverViewPage();
									employeesearch.navigateToEmpDetailPage();
									employeesearch.verify_Paycheck_ContributionSection();
								} catch (Exception e) {
									e.printStackTrace();
									Globals.exception = e;
									String exceptionMessage = e.getMessage();
									Reporter.logEvent(Status.FAIL, "A run time exception occured.",
											exceptionMessage, true);
								} catch (Error ae) {
									ae.printStackTrace();
									Globals.error = ae;
									String errorMsg = ae.getMessage();
									Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
											errorMsg, true);
								} finally {
									try {
										Reporter.finalizeTCReport();
									} catch (Exception e1) {
										e1.printStackTrace();
									}
								}
							}
	
							

							/**
							 * <pre>This testcase to verify Paycheck contribution section of an employee</pre>
							 * @param itr
							 * @param testdata
							 */
								@Test(dataProvider = "setData")
								public void TC023_EnrolmmentAndEligibility(int itr,
										Map<String, String> testdata) {		
									try {
										Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
										Reporter.logEvent(Status.INFO, "Testcase Description",
												"This testcase validates general employment informations", false);
										employeesearch = new EmployeeSearch().get();
										employeesearch.SSNOfEmployeeWithTermDate();
										employeesearch.searchEmployeeBySSNAllPlans(employeesearch.terminatedEmp[0]);
										employeesearch.navigateToEmployeeOverViewPage();
										employeesearch.navigateToEmpDetailPage();
										employeesearch.beforeRehiring();
										employeesearch.rehireEmployee();
										employeesearch.AfterRehiring();
									} catch (Exception e) {
										e.printStackTrace();
										Globals.exception = e;
										String exceptionMessage = e.getMessage();
										Reporter.logEvent(Status.FAIL, "A run time exception occured.",
												exceptionMessage, true);
									} catch (Error ae) {
										ae.printStackTrace();
										Globals.error = ae;
										String errorMsg = ae.getMessage();
										Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
												errorMsg, true);
									} finally {
										try {
											Reporter.finalizeTCReport();
										} catch (Exception e1) {
											e1.printStackTrace();
										}
									}
								}
	
	
								/**
								 * <pre>This testcase to verify Paycheck contribution section of an employee</pre>
								 * @param itr
								 * @param testdata
								 */
									@Test(dataProvider = "setData")
									public void TC024_Verify_Employee_Overview_Screen_Elements(int itr,
											Map<String, String> testdata) {		
										try {
											Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
											Reporter.logEvent(Status.INFO, "Testcase Description",
													"This testcase validates general employment informations", false);
											employeesearch = new EmployeeSearch().get();
											homePage = new HomePage();
											homePage.searchPlanWithIdOrName(employeesearch.findPlanForUser(Stock.getTestQuery("queryTofindPlansForUser"),
													Stock.GetParameterValue("username")));
											employeesearch.navigateToEmployeeTab();
											employeesearch.searchEmployeeByEmployeeId("");
											employeesearch.navigateToEmployeeOverViewPage();
											employeesearch.verifyOverviewScreenElements();
											
										} catch (Exception e) {
											e.printStackTrace();
											Globals.exception = e;
											String exceptionMessage = e.getMessage();
											Reporter.logEvent(Status.FAIL, "A run time exception occured.",
													exceptionMessage, true);
										} catch (Error ae) {
											ae.printStackTrace();
											Globals.error = ae;
											String errorMsg = ae.getMessage();
											Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
													errorMsg, true);
										} finally {
											try {
												Reporter.finalizeTCReport();
											} catch (Exception e1) {
												e1.printStackTrace();
											}
										}
									}
	
	
									/**
									 * <pre>This testcase to verify Employee overview plan list view section</pre>
									 * @param itr
									 * @param testdata
									 */
										@Test(dataProvider = "setData")
										public void TC025_Verify_Employee_Overview_PlanList_Balance_Section(int itr,
												Map<String, String> testdata) {		
											try {
												Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
												Reporter.logEvent(Status.INFO, "Testcase Description",
														"This testcase validates general employment informations", false);
												employeesearch = new EmployeeSearch().get();
												employeesearch.searchEmployeeBySSNAllPlans(employeesearch.getMultiPlanEmployee());
												employeesearch.navigateToEmployeeOverViewPage();
												employeesearch.verifyColumnsOfPlanListsection();
												employeesearch.verifySelectedPlanHeaderAndHighlightedPlan();
												employeesearch.verifyTotalBalance();
												employeesearch.verifyViewPageForAPlanHavingBalance();
												employeesearch.verifyInvestmentSection();
												employeesearch.verifyCalendarIcon();
												employeesearch.returnToEmployeeOverview();
											} catch (Exception e) {
												e.printStackTrace();
												Globals.exception = e;
												String exceptionMessage = e.getMessage();
												Reporter.logEvent(Status.FAIL, "A run time exception occured.",
														exceptionMessage, true);
											} catch (Error ae) {
												ae.printStackTrace();
												Globals.error = ae;
												String errorMsg = ae.getMessage();
												Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
														errorMsg, true);
											} finally {
												try {
													Reporter.finalizeTCReport();
												} catch (Exception e1) {
													e1.printStackTrace();
												}
											}
										}
	
										


/**
 * <pre>This test case verifies employee basic information on overview page and update few fields and verifies the changes.</pre>
 * @param itr
 * @param testdata
 */
@Test(dataProvider = "setData")
public void TC026_Verify_Employee_Basic_Info(int itr,
Map<String, String> testdata) {		
try {
		Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
		Reporter.logEvent(Status.INFO, "Testcase Description",
		"This testcase validates general employment informations", false);
		employeesearch = new EmployeeSearch().get();
		employeesearch.getQNTTypeIndividuals();
		Thread.sleep(3000);
		employeesearch.searchEmployeeBySSNAllPlans(employeesearch.qntTypeIndividuals.get(0));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.navigateToEmpDetailPage();
		employeesearch.verifyBasicInformationOnOverviewPage();
		employeesearch.searchEmployeeBySSNAllPlans(employeesearch.qntTypeIndividuals.get(1));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.verifyBasicInformationOnOverviewPage();
		employeesearch.searchEmployeeBySSNAllPlans(employeesearch.qntTypeIndividuals.get(2));
		employeesearch.navigateToEmployeeOverViewPage();
		employeesearch.verifyBasicInformationOnOverviewPage();
		employeesearch.verifyBasicInfoModalWindow();
		employeesearch.editBasicInfoAndSave();
		
													
	} catch (Exception e) {
		e.printStackTrace();
		Globals.exception = e;
		String exceptionMessage = e.getMessage();
		Reporter.logEvent(Status.FAIL, "A run time exception occured.",
		exceptionMessage, true);
	} catch (Error ae) {
		ae.printStackTrace();
		Globals.error = ae;
		String errorMsg = ae.getMessage();
		Reporter.logEvent(Status.FAIL, "Assertion Error Occured",errorMsg, true);
	} finally {
			try {
					Reporter.finalizeTCReport();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
	}
}
	
	
	
	
	
	
	
	
	
	
	

	@AfterSuite
	public void cleanUpSession() {
		Web.getDriver().close();
		Web.getDriver().quit();
	}

}
