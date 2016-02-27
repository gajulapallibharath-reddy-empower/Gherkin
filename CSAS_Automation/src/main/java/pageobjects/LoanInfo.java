package pageobjects;

import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.ThrowException;
import core.framework.ThrowException.TYPE;

public class LoanInfo extends LoadableComponent<LoanInfo> {

	@FindBy(xpath = "//*[@id='oCMenu_315'][contains(text(),'Participant Info')]")
	private WebElement MenuPPTInfo;

	@FindBy(xpath = "//*[@id='oCMenu_329'][contains(text() , 'Loan Info')]")
	private WebElement MenuLoanInfo;

	@FindBy(css = "td.pageMenuTitle")
	private WebElement LoanInfoPageTitle;

	@FindBy(css = "table#table_loanInfoMain>tbody>tr:nth-of-type(1) div.titleText")
	private WebElement LoanStatus_Tab;

	@FindBy(css = "table#table_loanInfoMain>tbody>tr:nth-of-type(2) div.titleText")
	private WebElement Loans_At_othr_Prvdr_Tab;

	@FindBy(css = "table#table_loanInfoMain>tbody>tr:nth-of-type(3) div.titleText")
	private WebElement Loan_Quaotes_Tab;

	@FindBy(xpath = "//div[contains(text(),'Loan Payoff Quote Information')]")
	private WebElement Loan_Payoff_Quaotes_info_Tab;

	// Loan Status WE..
	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(1)")
	private WebElement Loan_Number_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(2)")
	private WebElement View_Payment_History_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(3)")
	private WebElement Payoff_Quote_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(4)")
	private WebElement Loan_Status_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(5)")
	private WebElement Effective_date_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(6)")
	private WebElement Loan_Term_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(7)")
	private WebElement Maturity_Date_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(8)")
	private WebElement Loan_Amount_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(9)")
	private WebElement Payment_Amount_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(10)")
	private WebElement Payment_Frq_Mthd_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(11)")
	private WebElement Total_Outstanding_Bal_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(12)")
	private WebElement Default_Indicator_Label;

	@FindBy(css = "table#table_loanHistorySummary th:nth-of-type(13)")
	private WebElement Default_Date_Label;

	@FindBy(css = "a#link_inlnagSeqnbr")
	private List<WebElement> Loan_Number_List;

	@FindBy(css = "a#link_history")
	private List<WebElement> View_Payment_Hst_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(4)")
	private List<WebElement> Loan_Status_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(5)")
	private List<WebElement> Effective_Date_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(10)")
	private List<WebElement> Payment_Frq_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(11)")
	private List<WebElement> Total_Outstanding_Bal_List;

	@FindBy(css = "table#table_loanHistorySummary tr td:nth-of-type(11)")
	private List<WebElement> Default_Indicator_List;

	LoadableComponent<?> parent;

	public LoanInfo() {
		PageFactory.initElements(Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.webdriver.getTitle()
				.contains("Loan Info/History"));
	}

	@Override
	protected void load() {
		
		this.parent = new ParticipantHome().get();
		try {
			Web.webdriver.get(Stock.getConfigParam("AppURL"));
			Reporter.logEvent(Status.INFO,
					"Check if the CSAS Log in page open",
					"CSAS log in page launhced successfully", true);
			new ParticipantHome().submitLoginCredentials(Stock.GetParameterValue("username"),
					Stock.GetParameterValue("password"));
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, e.getMessage());
		}
	}

	/**
	 * <pre>
	 * Method to Loan info page
	 * </pre>
	 * @author RANJAN
	 */
	public void verify_LoanInfoPage() {

		if (Web.isWebElementDisplayed(MenuPPTInfo, true)) {
			Web.mouseHover(MenuPPTInfo);
			if (Web.isWebElementDisplayed(MenuLoanInfo, true)) {
				Web.clickOnElement(MenuLoanInfo);
				if (Web.isWebElementDisplayed(LoanInfoPageTitle, true)) {
					Reporter.logEvent(Status.PASS,
							"Check if Loan info page displayed or not",
							"Loan info page displyed successfully", true);
				} else {
					Reporter.logEvent(Status.FAIL,
							"Check if Loan info page displayed or not",
							"Loan info page didn't disply successfully", true);
				}
			} else {
				Reporter.logEvent(
						Status.FAIL,
						"Check if Loan info Link on Participant Info tab displayed or not",
						"Loan info Link on Participant Info tab didn't disply successfully",
						true);
			}
		}
	}

}
