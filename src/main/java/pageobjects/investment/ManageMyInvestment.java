package pageobjects.investment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.Choose;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import core.framework.Globals;
import appUtils.Common;
import pageobjects.balance.Balance;
import pageobjects.general.LeftNavigationBar;

public class ManageMyInvestment extends LoadableComponent<ManageMyInvestment> {

	private LoadableComponent<?> parent;
	String investmentFundName1;
	String investmentFundName2;
	String fromInvestmentOption;
	static String toInvestmentOption;
	String confirmationNo;

	// @FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']")
	// private WebElement lblUserName;
	@FindBy(xpath = ".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']")
	private WebElement lblUserName;
	@FindBy(xpath = "//h1[text()[normalize-space()='My Investments']]")
	private WebElement lblMyInvestments;
	@FindBy(linkText = "Log out")
	private WebElement lnkLogout;
	@FindBy(xpath = "//button[text()[normalize-space()='Change My Investments']]")
	private WebElement btnChangeMyInvestment;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Rebalance my investments']]")
	private WebElement radioRebalanceCurrBal;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Change how my future contributions will be invested']]")
	private WebElement radioChangeFutureContr;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Change how my current balance is invested']]")
	private WebElement radioChangeCurrentBalInvst;
	@FindBy(xpath = ".//strong[text()[normalize-space()='Dollar-cost average']]")
	private WebElement radioDollarCost;
	@FindBy(xpath = "//button[text()[normalize-space()='Continue']]")
	private WebElement btnContinue1;
	// @FindBy(xpath="//button[text()[normalize-space()='Continue']]") private
	// WebElement btnContinue;
	@FindBy(xpath = ".//input[@value = 'Continue']")
	private WebElement btnCont;
	@FindBy(xpath = "//input[@value='Continue']")
	private WebElement btnContinue2;
	@FindBy(xpath = "//input[@value='Cancel']")
	private WebElement btnCancel;
	@FindBy(xpath = "//input[@value='Submit']")
	private WebElement btnSubmitForRebalancer;
	@FindBy(xpath = "//button[@id='submitTransferbuttonOnVerifyId']")
	private WebElement btnSubmitForF2F;
	@FindBy(xpath = "//input[@value='View Pending Transfer']")
	private WebElement btnViewPendingTransfer;
	@FindBy(id = "perdTrfFreq_once")
	private WebElement radioOnce;
	@FindBy(id = "frequency_M")
	private WebElement radioMonthlyDollarCost;
	@FindBy(id = "perdTrfFreq_quarterly")
	private WebElement radioQuarterly;
	@FindBy(id = "perdTrfFreq_semi_annually")
	private WebElement radioSemiAnnually;
	@FindBy(id = "perdTrfFreq_annually")
	private WebElement radioAnnually;
	@FindBy(id = "perdTrfFreq_today")
	private WebElement radiotoday;
	@FindBy(id = "perdTrfFreq_future")
	private WebElement radioFuture;
	@FindBy(id = "future_effdate_no")
	private WebElement radiotodayDollarCost;
	@FindBy(id = "future_effdate_yes")
	private WebElement radioFutureDollarCost;
	@FindBy(id = "legacyFeatureIframe")
	private WebElement iframeLegacyFeature;
	@FindBy(id = "the_iframe")
	private WebElement theIframe;
	@FindBy(id = "transferPreSubmitButton")
	private List<WebElement> btnReviewTransfer;
	@FindBy(id = "preValidationWarningAId")
	private WebElement btnPreValidationOK;
	@FindBy(xpath = "//table[@class='default']//tbody//table//tr//td[2]//input")
	private List<WebElement> lsttxtPercentage;
	@FindBy(xpath = "//table[@class='default']//tbody//table//tr//td[1]")
	private List<WebElement> lstInvestmentFundName;
	@FindBy(xpath = "//table[@class='default']")
	private WebElement tblInvestmentOptionsTable;
	@FindBy(xpath = "//table[@class='default']//tbody//table//tr")
	private List<WebElement> lstInvestmentOptions;
	@FindBy(xpath = "//span[contains(text(),'Rebalancer frequency')]")
	private WebElement lblRebalancerFrequency;
	@FindBy(xpath = "//span[contains(text(),'setup date')]")
	private WebElement lblSetupDate;
	@FindBy(xpath = "//p//table/tbody/tr[contains(@class,'tableData')]")
	private List<WebElement> lstInvestmentFunds;
	@FindBy(xpath = "//h1[text()[normalize-space()='Change how my current balance is invested']]")
	private WebElement lblCurrentBalanceInvestment;
	@FindBy(xpath = "//div[@id='transferGraphByAssetCurrentDivId']")
	private WebElement currentAssetBalGraph;
	@FindBy(xpath = "//div[@id='transferGraphByAssetCurrentDivId']")
	private WebElement postTransferBalanceGraph;
	@FindBy(xpath = "//div[@class='dollarpercent']/a[contains(@class, 'percentbutton')]")
	private WebElement btnPercent;

	@FindBy(id = "transferFundFromTableId")
	private WebElement tblTransferFundFrom;
	@FindBy(xpath = "//table[@id='transferFundFromTableId']/thead/tr")
	private WebElement hdrTransferFundFromTable;
	@FindBy(xpath = "//table[@id='transferFundFromTableId']/tbody/tr/td/a")
	private WebElement lnkTransferFromInvestmentOption;
	@FindBy(xpath = "//input[contains(@id,'FromPercent')]")
	private List<WebElement> txtTransferFromPercent;

	@FindBy(id = "fundTransferToTableSelectId")
	private WebElement tblTransferFundTo;
	@FindBy(xpath = "//table[@id='fundTransferToTableSelectId']/thead/tr")
	private WebElement hdrTransferFundToTable;
	@FindBy(xpath = "//table[@id='fundTransferToTableSelectId']/tbody/tr/td[5]")
	private List<WebElement> lnkTransferToInvestmentOption;
	@FindBy(xpath = "//input[contains(@id,'ToPercent')]")
	private List<WebElement> txtTransferToPercent;
	@FindBy(xpath = "//div[text()='View by Asset Class']")
	private WebElement tabViewByAssetClass;
	@FindBy(xpath = "//div[@id='fundingoptions']/a[text()='Asset Class']")
	private WebElement selAssetClass;
	@FindBy(xpath = "//div[@id='fundingtype']/a[text()='Investment Option Family']")
	private WebElement selInvestmentOptFamily;

	@FindBy(xpath = "//tr[@id='verifyTransferFromRowId']")
	private WebElement hdrVerifyTransferFromTable;
	@FindBy(xpath = "//tbody[@id='fundTransferDisplayId']/tr/td[1]")
	private WebElement lblTransferFromOption;
	@FindBy(xpath = "//tbody[@id='fundTransferDisplayId']/tr/td[3]")
	private WebElement lblTransferFromPercent;

	@FindBy(xpath = "//tr[@id='fixedRatesColTitle']")
	private WebElement hdrVerifyTransferToTable;
	@FindBy(xpath = "//table[@id='transactions-validation']//tbody/tr/td[1]")
	private WebElement lblTransferToOption;
	@FindBy(xpath = "//table[@id='transactions-validation']//tbody/tr/td[2]")
	private WebElement lblTransferToPercent;
	@FindBy(xpath = "//p/span[@class='baseFontFace baseFontSize alertFontColor alertFontWeight baseTextAlign']")
	private WebElement lblTransactionDetails;
	@FindBy(xpath = "//p/span[@class='baseFontFace baseFontSize alertFontColor alertFontWeight baseTextAlign']/span")
	private WebElement lblConfirmationNumber;
	@FindBy(xpath = "//p[@class='bold confirmationNumber']")
	private WebElement lblConfirmationNumberF2F;
	@FindBy(xpath = "//span[@class='baseFontFace sectionTitleFontSize sectionTitleFontColor sectionTitleFontWeight']")
	private WebElement lblConfirmationNumberForCancel;
	@FindBy(xpath = "//button[text()='Back']")
	private WebElement btnBack;
	@FindBy(xpath = "//input[@class='canceltransferBtn']")
	private WebElement btnCancelTransfer;
	@FindBy(xpath = "//input[@value='Cancel Transfer']")
	private WebElement btnCancelTransferF2F;
	@FindBy(xpath = "//input[@value='Continue to the next step']")
	private WebElement btnContinueToNextStep;
	@FindBy(xpath = "//input[@id='use_termdate_no']")
	private WebElement radioDoNoTerminate;
	@FindBy(xpath = "//input[@id='use_termdate_yes']")
	private WebElement radioTerminate;
	@FindBy(xpath = "//table/tbody/tr[@class='tableSubtitle']")
	private WebElement hdrInvestmentOptionTblForDollarCost;
	@FindBy(xpath = "//table/tbody/tr[contains(@class,'tableData')]/td[1]")
	private List<WebElement> lstInvestmentOptionsDollarCost;
	@FindBy(xpath = "//table/tbody/tr[contains(@class,'tableData')]/td[2]")
	private List<WebElement> lstToInvestmentOptionsDollarCost;
	@FindBy(xpath = "//table/tbody/tr[contains(@class,'tableData')]/td/input")
	private List<WebElement> lstChkInvestmentOptionDollarCost;
	@FindBy(xpath = "//input[contains(@id,'ID')]")
	private WebElement lstInvestmentPercentDollarCost;
	@FindBy(xpath = "//input[@name='DOLLARS']")
	private WebElement txtTransferAmtDollarCost;
	@FindBy(xpath = "//div[contains(@id,'perdTrfFreq')]")
	private WebElement lblDollarCostFreq;
	@FindBy(xpath = "//span[contains(text(),'Setup Date')]")
	private WebElement lblDollarCostSetupDate;
	@FindBy(xpath = "//img[@class='site-logo']")
	private WebElement lblSponser;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath = "//input[@name='Total']")
	private WebElement inpTotal;
	@FindBy(xpath = ".//button[@id='portfolio-link']") private WebElement btnChooseIndividualFunds;
	@FindBy(xpath = ".//a[@id='rebalance-view-all-funds']") private WebElement lnkAddViewAllFunds;
	@FindBy(xpath = ".//div[@class='allocation-table-wrapper']//table[@id='allocation-all-funds-table']") private WebElement tableAllocationFund;
	
	@FindBy(xpath = ".//*[@id='allocation-all-funds-table']//tr//td[./label[@class='add-view-label']]//input") private List<WebElement> inpInvestmentOption;
	@FindBy(xpath = ".//*[@id='allocation-all-funds-table']//tr//td//a[contains(@class,'allocation-fund-name')]") private List<WebElement> txtInvestmentOption;
	@FindBy(xpath = ".//*[@id='add-funds-button']") private WebElement btnAdd;
	@FindBy(xpath = ".//*[@id='pptGrpModalSubmit']") private WebElement btnAlertContinue;
	@FindBy(xpath = ".//*[@id='submitButton submit']") private WebElement btnSubmit;
	@FindBy(xpath = ".//a/span[@class='em-x-bold']") private List<WebElement> btnRemoveInvestment;
	@FindBy(xpath = "//span[@class='allocation-funds-portfolio-total']") private WebElement txttotalInvestmentPercent;
	@FindBy(xpath = "//tr[contains(@ng-repeat,'rebalanceCurrentFunds')]//span[@class='fund-overview-url']//a") private List<WebElement> txtCurrentFunds;
	String inputAllocationPercrntage="//*[@id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')]]//input[@name='allocationPercentage']";
	String buttonlock=".//*[@id='rebalance-destination-funds-table']//tbody//tr[.//td//a[contains(text(),'Investment Option')]]//button[contains(@class,'btn-link')]";
	
	/**
	 * Empty args constructor
	 * 
	 */
	public ManageMyInvestment() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	/**
	 * Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public ManageMyInvestment(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(lblUserName),"User Name is Not Displayed\n");

		//Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName),"Manage My Investment Page is Not Loaded");
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		if (Globals.GC_EXECUTION_ENVIRONMENT.contains("PROD")) {
			userFromDatasheet = Stock.GetParameterValue("lblUserName");
		} else {
			ResultSet strUserInfo=null;
			try {
				strUserInfo = Common.getParticipantInfoFromDataBase(ssn);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			try {
				userFromDatasheet = strUserInfo.getString("FIRST_NAME") + " "
						+ strUserInfo.getString("LAST_NAME");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		String userLogedIn = this.lblUserName.getText();
		
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));	
			Assert.assertTrue(lib.Web.isWebElementDisplayed(lblMyInvestments),"View/Manage MyInvestments Page is Not Loaded");
		} else {
			
			this.lnkLogout.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Assert.assertTrue(false,"Login Page is not loaded\n");
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		((LeftNavigationBar) this.parent)
				.clickNavigationLink("View/Manage my investments");
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		lib.Web.isWebElementDisplayed(lblMyInvestments,true);	

	}

	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Rebalance Currnet Balance")) {
			return this.radioRebalanceCurrBal;
		}
		if (fieldName.trim().equalsIgnoreCase("Change Future Contribution")) {
			return this.radioChangeFutureContr;
		}
		if (fieldName.trim().equalsIgnoreCase(
				"Change Current Balance Investment")) {
			return this.radioChangeCurrentBalInvst;
		}
		if (fieldName.trim().equalsIgnoreCase("Dollar Cost")) {
			return this.radioDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Continue button1")) {
			return this.btnContinue1;
		}
		if (fieldName.trim().equalsIgnoreCase("Submit button Rebalancer")) {
			return this.btnSubmitForRebalancer;
		}
		if (fieldName.trim().equalsIgnoreCase("Submit button F2F")) {
			return this.btnSubmitForF2F;
		}
		if (fieldName.trim().equalsIgnoreCase("Once")) {
			return this.radioOnce;
		}
		if (fieldName.trim().equalsIgnoreCase("Monthly Dollar Cost")) {
			return this.radioMonthlyDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Today")) {
			return this.radiotoday;
		}
		if (fieldName.trim().equalsIgnoreCase("Today Dollar Cost")) {
			return this.radiotodayDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Setup Today Dollar Cost")) {
			return this.radiotodayDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("Future Dollar Cost")) {
			return this.radioFutureDollarCost;
		}
		if (fieldName.trim().equalsIgnoreCase("View By Asset Class Tab")) {
			return this.tabViewByAssetClass;
		}
		if (fieldName.trim().equalsIgnoreCase("Current Assets Balance Graph")) {
			return this.currentAssetBalGraph;
		}
		if (fieldName.trim().equalsIgnoreCase("Post Transfer Balance Graph")) {
			return this.postTransferBalanceGraph;
		}
		// Log out
		if (fieldName.trim().equalsIgnoreCase("LOG OUT")
						|| fieldName.trim().equalsIgnoreCase("LOGOUT")) {
					return this.lnkLogout;
				}
		if (fieldName.trim().equalsIgnoreCase("Login")) {
			return this.btnLogin;
		}

		return null;
	}

	public void clickChangeMyInvestmentButton() {
		Web.waitForElement(btnChangeMyInvestment);
		if (Web.isWebElementDisplayed(btnChangeMyInvestment, true)) {
			btnChangeMyInvestment.click();
			Reporter.logEvent(Status.INFO,
					"verify Change My Investment button is displayed",
					"Clicked on Change My Investment button", true);
		} else
			Reporter.logEvent(Status.FAIL,
					"verify Change My Investment button is displayed",
					"Change My Investment button not displayed", true);
	}

	public void choseInvestmentOption(String investmentOption) {
		WebElement option = this.getWebElement(investmentOption);
		if (Web.isWebElementDisplayed(option, true)) {
			Web.clickOnElement(option);
			Reporter.logEvent(Status.INFO, "verify " + investmentOption
					+ " option is displayed", investmentOption
					+ " option is displayed", true);
			
		} else
			Reporter.logEvent(Status.FAIL, "verify " + investmentOption
					+ " option is displayed", investmentOption
					+ " option not displayed", true);
	}

	public void rebalanceInvestment(String frequency, String setupDate,
			String percent) {
		Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		WebElement freq = this.getWebElement(frequency);
		WebElement date = this.getWebElement(setupDate);
		Web.clickOnElement(freq);
		Reporter.logEvent(Status.INFO,
				"verify if frequency period is selected",
				"Selected Frequency Period : " + frequency, true);
		Web.clickOnElement(date);
		Reporter.logEvent(Status.INFO, "Specify the Rebalancer Setup Date",
				"Setup Date : " + setupDate, false);
		// Web.getDriver().switchTo().frame(iframeLegacyFeature);
		btnContinue2.click();
		if (Web.isWebElementDisplayed(tblInvestmentOptionsTable, true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Investment options table is displayed",
					"Investment options table is displayed ", true);
			int noOfRows = lstInvestmentOptions.size();
			if (noOfRows >= 2) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment options are available",
						"Investment options available ", false);
				investmentFundName1 = lstInvestmentFundName.get(1).getText()
						.trim();
				investmentFundName2 = lstInvestmentFundName.get(2).getText()
						.trim();
				System.out.println(investmentFundName1);
				System.out.println(investmentFundName2);
				Web.setTextToTextBox(lsttxtPercentage.get(1), percent);
				Web.setTextToTextBox(lsttxtPercentage.get(2), percent);
				Reporter.logEvent(Status.INFO, "Enter Investment Percent",
						"Entered investment percent : " + percent + " for "
								+ investmentFundName1 + "and"
								+ investmentFundName2 + "each", true);
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Investment options are available",
						"Investment options not available ", true);

		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Investment options Table is displayed",
					"Investment options table not displayed ", true);
		Web.clickOnElement(inpTotal);
		btnContinue2.click();
		Web.getDriver().switchTo().defaultContent();
	}

	public void dollarCostAverageFlow(String frequency, String setupDate,
			String percent, String amount) throws InterruptedException,NullPointerException,IndexOutOfBoundsException {
		Actions keyBoard = new Actions(Web.getDriver());
		Web.waitForElement(iframeLegacyFeature);
		if(Web.isWebElementDisplayed(iframeLegacyFeature, true))
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		else{
			Web.waitForElement(iframeLegacyFeature);			
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		}
		WebElement freq = this.getWebElement(frequency);
		WebElement date = this.getWebElement(setupDate);
		Web.clickOnElement(freq);
		Reporter.logEvent(Status.INFO,
				"verify if frequency period is selected",
				"Selected Frequency Period : " + frequency, true);
		Web.clickOnElement(date);
		Reporter.logEvent(Status.INFO,
				"Specify the Dollar Cost Average Setup Date", "Setup Date : "
						+ setupDate, false);
		// Web.getDriver().switchTo().frame(iframeLegacyFeature);
		btnContinueToNextStep.click();
		Web.waitForElement(radioDoNoTerminate);
		radioDoNoTerminate.click();
		Reporter.logEvent(
				Status.INFO,
				"verify if 'Do not terminate this Dollar cost average' option is selected",
				"'Do not terminate this Dollar cost average' option is selected",
				false);
		if (Web.VerifyText("Investment Options Balance Effective Date",
				hdrInvestmentOptionTblForDollarCost.getText(), true))
			Reporter.logEvent(
					Status.PASS,
					"verify Investment Options Table Header for Dollar Cost Average",
					"Expected: Investment Options Balance Effective Date \n Actual: "
							+ hdrInvestmentOptionTblForDollarCost.getText()
									.trim(), false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Investment Options Table Header for Dollar Cost Average",
					"Expected: Investment Options Balance Effective Date \n Actual: "
							+ hdrInvestmentOptionTblForDollarCost.getText()
									.trim(), true);
		lstChkInvestmentOptionDollarCost.get(0).click();

		fromInvestmentOption = lstInvestmentOptionsDollarCost.get(0).getText();
		btnContinueToNextStep.click();
		Web.waitForElement(txtTransferAmtDollarCost);
		Web.setTextToTextBox(txtTransferAmtDollarCost, amount);
		Reporter.logEvent(Status.INFO, "verify if transfer amount is entered",
				"Entered Transfer amount: " + amount, true);
		btnContinueToNextStep.click();
		Thread.sleep(5000);
		Web.waitForElement(lstChkInvestmentOptionDollarCost.get(0));
		lstChkInvestmentOptionDollarCost.get(0).click();
		toInvestmentOption = lstToInvestmentOptionsDollarCost.get(0).getText();
		btnContinueToNextStep.click();
		Thread.sleep(5000);
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,-250)", "");
		Web.waitForElements(lstInvestmentOptionsDollarCost);
		if (lstInvestmentOptionsDollarCost.get(0).getText()
				.contains(toInvestmentOption))
			Reporter.logEvent(Status.PASS,
					"verify Investment Option displayed", "Expected: "
							+ toInvestmentOption + "\nActual: "
							+ lstInvestmentOptionsDollarCost.get(0).getText(),
					true);
		else
			Reporter.logEvent(Status.FAIL,
					"verify Investment Options displayed", "Expected: "
							+ toInvestmentOption + "\n Actual: "
							+ lstInvestmentOptionsDollarCost.get(0).getText(),
					true);
		//Thread.sleep(5000);
		Web.waitForElement(lstInvestmentPercentDollarCost);
		Web.clickOnElement(lstInvestmentPercentDollarCost);
		keyBoard.sendKeys(Keys.BACK_SPACE).perform();
		Web.setTextToTextBox(lstInvestmentPercentDollarCost,
				Stock.GetParameterValue("percent"));
		Reporter.logEvent(Status.INFO, "verify investment percent is entered",
				"investment percent : " + Stock.GetParameterValue("percent"),
				true);
		Web.getDriver().switchTo().defaultContent();
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		keyBoard.moveToElement(inpTotal).clickAndHold(inpTotal).release(inpTotal).build().perform();
		Web.setTextToTextBox(inpTotal,
				Stock.GetParameterValue("percent"));
		keyBoard.moveToElement(btnContinueToNextStep).click(btnContinueToNextStep).build().perform();
				
		// to handle allert
		for(int i=0;i<3;i++){
            if(Common.isAlerPresent()){
            Common.HandlePopAlert();
            Web.clickOnElement(lstInvestmentPercentDollarCost);
            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
            keyBoard.sendKeys(Keys.BACK_SPACE).perform();
            Web.setTextToTextBox(lstInvestmentPercentDollarCost,Stock.GetParameterValue("percent"));  
            keyBoard.sendKeys(Keys.TAB).perform();                  
            keyBoard.moveToElement(inpTotal).clickAndHold(inpTotal).release(inpTotal).build().perform();
            Web.clickOnElement(inpTotal);
            keyBoard.moveToElement(btnContinueToNextStep).click(btnContinueToNextStep).build().perform();
            }
    }

		//Web.clickOnElement(btnContinueToNextStep);
		Web.getDriver().switchTo().defaultContent();

	}

	public void verifyDollarCostInvestmentDetails(String frequency,
			String date, String percent) {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(lblDollarCostFreq);
		if (Web.VerifyText("Your chosen Dollar Cost Average frequency is: "
				+ frequency, "Your chosen Dollar Cost Average frequency is: "
				+ lblDollarCostFreq.getText(), true))
			Reporter.logEvent(
					Status.PASS,
					"verify Dollar Cost Average Frequency",
					"Expected: Your chosen Dollar Cost Average frequency is: "
							+ frequency + "\n Actual: "
							+ lblDollarCostFreq.getText(), true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Dollar Cost Average Frequency",
					"Expected: Your chosen Dollar Cost Average frequency is: "
							+ frequency + "\n Actual: "
							+ lblDollarCostFreq.getText(), true);

		if (Web.VerifyText(
				"Specify the Dollar Cost Average Setup Date " + date,
				lblDollarCostSetupDate.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer setup date",
					"Expected: Specify the Dollar Cost Average Setup Date "
							+ date + "\n Actual: "
							+ lblDollarCostSetupDate.getText().trim(), false);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer setup date",
					"Expected: Specify the Dollar Cost Average Setup Date "
							+ date + "\n Actual: "
							+ lblDollarCostSetupDate.getText().trim(), true);

	}

	public void verifyRebalanceInvestmentDetails(String frequency,
			String setupDate, String date, String percent) {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(lblRebalancerFrequency);
		if (Web.VerifyText("Your chosen Rebalancer frequency is: " + frequency,
				lblRebalancerFrequency.getText().trim() + " "
						+ radioOnce.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer Frequency",
					"Expected: Your chosen Rebalancer frequency is: "
							+ frequency + "\n Actual: "
							+ lblRebalancerFrequency.getText().trim() + " "
							+ radioOnce.getText().trim(), true);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer Frequency",
					"Expected: Your chosen Rebalancer frequency is: "
							+ frequency + "\n Actual: "
							+ lblRebalancerFrequency.getText().trim() + " "
							+ radioOnce.getText().trim(), true);

		if (Web.VerifyText("Your chosen Rebalancer setup date is: " + date,
				lblSetupDate.getText().trim(), true))
			Reporter.logEvent(Status.PASS, "verify Rebalancer setup date",
					"Expected: Your chosen Rebalancer setup date is: " + date
							+ "\n Actual: " + lblSetupDate.getText().trim(),
					false);
		else
			Reporter.logEvent(Status.FAIL, "verify Rebalancer setup date",
					"Expected: Your chosen Rebalancer setup date is: " + date
							+ "\n Actual: " + lblSetupDate.getText().trim(),
					true);
		System.out.println(lstInvestmentFunds.get(0).getText());
		System.out.println(percent);
		if (lstInvestmentFunds.get(0).getText().contains(investmentFundName1)
				& lstInvestmentFunds.get(0).getText().contains(percent))
			Reporter.logEvent(
					Status.PASS,
					"verify Fund name and investment percent displayed",
					"Expected Fund name:  " + investmentFundName1
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(0).getText(), false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Fund name and investment percent displayed",
					"Expected Fund name: " + investmentFundName1
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(0).getText(), true);

		if (lstInvestmentFunds.get(1).getText().contains(investmentFundName2)
				& lstInvestmentFunds.get(0).getText().contains(percent))
			Reporter.logEvent(
					Status.PASS,
					"verify Fund name and investment percent displayed",
					"Fund name: " + investmentFundName2
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(1).getText(), false);
		else
			Reporter.logEvent(
					Status.FAIL,
					"verify Fund name and investment percent displayed",
					"Fund name: " + investmentFundName2
							+ "\n Investment percent: " + percent
							+ "\n Actual Fund name: "
							+ lstInvestmentFunds.get(1).getText(), true);
		if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
				"Yes")) {
			btnSubmitForRebalancer.click();
			Web.getDriver().switchTo().defaultContent();
			Web.waitForElement(btnBack);
			Reporter.logEvent(Status.INFO, "Submit TRansfer Details",
					"Transfer Details Submitted", true);
		}

	}

	public void verifyRebalanceInvestmentConfirmationDetails() {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		if (StringUtils.containsIgnoreCase(lblTransactionDetails.getText(),
				"Your Confirmation Number is")) {
			confirmationNo = lblConfirmationNumber.getText();
			if (lblConfirmationNumber.getText() != " "
					& lblConfirmationNumber.getText() != "null")
				Reporter.logEvent(Status.PASS,
						"verify Confirmation Number Displayed",
						"Confirmation Numberdisplayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"verify Confirmation Number Displayed",
						"Confirmation Number not displayed", true);
		} else
			Reporter.logEvent(Status.FAIL,
					"verify Confirmation Number Displayed",
					"Confirmation Number not displayed", true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void verifyF2FConfirmationDetails() {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		if (StringUtils.containsIgnoreCase(lblConfirmationNumberF2F.getText(),
				"Your Confirmation Number is")) {
			confirmationNo = lblConfirmationNumberF2F.getText().split(":")[1]
					.trim();
			if (lblConfirmationNumberF2F.getText() != " "
					& lblConfirmationNumberF2F.getText() != "null")
				Reporter.logEvent(Status.PASS,
						"verify Confirmation Number Displayed",
						"Confirmation Numberdisplayed", true);
			else
				Reporter.logEvent(Status.FAIL,
						"verify Confirmation Number Displayed",
						"Confirmation Number not displayed", true);
		} else
			Reporter.logEvent(Status.FAIL,
					"verify Confirmation Number Displayed",
					"Confirmation Number not displayed", true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void fundToFundTransfer(String fromPercent, String toPercent) {
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		if (Web.isWebElementDisplayed(tblTransferFundFrom)) {
			Reporter.logEvent(Status.INFO,
					"Verify 'Transfer Fund From' Table is displayed",
					"Table is displayed", true);
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			Web.clickOnElement(btnPercent);
			//btnPercent.click();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.setTextToTextBox(txtTransferFromPercent.get(0), fromPercent);
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			fromInvestmentOption = lnkTransferFromInvestmentOption.getText();
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Transfer Fund From' Table is displayed",
					"Table is not displayed", true);

		if (Web.isWebElementDisplayed(tblTransferFundTo)) {
			Reporter.logEvent(Status.INFO,
					"Verify 'Transfer Fund To' Table is displayed",
					"Table is displayed", true);
			Web.setTextToTextBox(txtTransferToPercent.get(1), toPercent);
			toInvestmentOption = lnkTransferToInvestmentOption.get(1).getText();
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify 'Transfer Fund To' Table is displayed",
					"Table is not displayed", true);
		((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
		Web.waitForElement(btnReviewTransfer.get(1));
		btnReviewTransfer.get(1).click();
		// Web.waitForElement(btnPreValidationOK);
		// btnPreValidationOK.click();
		Web.getDriver().switchTo().defaultContent();
	}

	public void ReviewFundToFundTransfer(String fromPercent, String toPercent) throws InterruptedException {
		Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		Web.waitForElement(hdrVerifyTransferFromTable);
		
		do{
			Thread.sleep(3000);
		}while(!((JavascriptExecutor)Web.getDriver()).
				executeScript("return document.readyState").toString().equalsIgnoreCase("complete"));
		
		if (Web.VerifyText("Investment Option Balance Percent",
				hdrVerifyTransferFromTable.getText(), true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Transfer From Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferFromTable.getText(), true);

			if (Web.VerifyText(fromInvestmentOption,
					lblTransferFromOption.getText(), true))
				Reporter.logEvent(
						Status.PASS,
						"Verify From Investment Option in Review Transfer Page",
						"Expected : " + fromInvestmentOption + "\n Actual :"
								+ lblTransferFromOption.getText(), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify From Investment Option in Review Transfer Page",
						"Expected : " + fromInvestmentOption + "\n Actual :"
								+ lblTransferFromOption.getText(), true);

			if (Web.VerifyText(fromPercent + "%",
					lblTransferFromPercent.getText(), true))
				Reporter.logEvent(
						Status.PASS,
						"Verify From Investment Percent in Review Transfer Page",
						"Expected : " + fromPercent + "%" + "\n Actual :"
								+ lblTransferFromPercent.getText(), false);
			else
				Reporter.logEvent(
						Status.FAIL,
						"Verify From Investment Percent in Review Transfer Page",
						"Expected : " + fromPercent + "%" + "\n Actual :"
								+ lblTransferFromPercent.getText(), true);
		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Transfer From Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferFromTable.getText(), true);

		if (Web.VerifyText("Investment Option Balance Percent",
				hdrVerifyTransferFromTable.getText(), true)) {
			Reporter.logEvent(Status.PASS,
					"Verify Transfer From Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferFromTable.getText(), true);

			if (Web.VerifyText(toInvestmentOption,
					lblTransferToOption.getText(), true))
				Reporter.logEvent(Status.PASS,
						"Verify To Investment Option in Review Transfer Page",
						"Expected : " + toInvestmentOption + "\n Actual :"
								+ lblTransferToOption.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify To Investment Option in Review Transfer Page",
						"Expected : " + toInvestmentOption + "\n Actual :"
								+ lblTransferToOption.getText(), true);

			if (Web.VerifyText(toPercent + "%", lblTransferToPercent.getText(),
					true))
				Reporter.logEvent(Status.PASS,
						"Verify To Investment Percent in Review Transfer Page",
						"Expected : " + toPercent + "%" + "\n Actual :"
								+ lblTransferToPercent.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,
						"Verify To Investment Percent in Review Transfer Page",
						"Expected : " + toPercent + "%" + "\n Actual :"
								+ lblTransferToPercent.getText(), true);

		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Transfer To Table Header is Displayed",
					"Expected : Investment Option Balance Percent \n Actual : "
							+ hdrVerifyTransferToTable.getText(), true);
		if (Stock.GetParameterValue("Submit_Transaction").equalsIgnoreCase(
				"Yes")) {
			btnSubmitForF2F.click();
			Web.getDriver().switchTo().defaultContent();
			Web.waitForElement(btnBack);
			Reporter.logEvent(Status.INFO, "Submit TRansfer Details",
					"Transfer Details Submitted", true);
		}

	}

	public void navigateToTab(String tabName) {
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(iframeLegacyFeature);
		if(Web.isWebElementDisplayed(iframeLegacyFeature, true))
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
			else{
				Web.waitForElement(iframeLegacyFeature);
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
			}
		WebElement tab = this.getWebElement(tabName);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(tab);
		tab.click();
		Reporter.logEvent(Status.INFO, "Navigate To " + tabName,
				"Navigated to " + tabName, true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void verifyIfGraphDisplayed(String graphName) {
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		WebElement graph = this.getWebElement(graphName);
		if (Web.isWebElementDisplayed(graph, true))
			Reporter.logEvent(Status.PASS, "Verify " + graphName
					+ "is displayed", graphName + "is displayed", false);
		else
			Reporter.logEvent(Status.FAIL, "Verify " + graphName
					+ "is displayed", graphName + "is not displayed", true);
		Web.getDriver().switchTo().defaultContent();
	}

	public void cancelTransfer(String transferType) {
		btnBack.click();
		choseInvestmentOption(transferType);
		btnContinue1.click();
		if (transferType.equalsIgnoreCase("Rebalance Currnet Balance")) {
			Web.waitForElement(iframeLegacyFeature);
			Web.getDriver().switchTo().frame(iframeLegacyFeature);
			radioOnce.click();
			btnContinue2.click();
			btnViewPendingTransfer.click();
			Web.getDriver().switchTo().defaultContent();
		}
		if (transferType.equalsIgnoreCase("F2F")) {
			Web.waitForElement(btnCancelTransfer);
			btnCancelTransfer.click();

		}

		Web.waitForElement(iframeLegacyFeature);
		Web.getDriver().switchTo().frame(iframeLegacyFeature);
		// Web.getDriver().switchTo().frame(theIframe);
		btnCont.click();
		if (StringUtils.containsIgnoreCase(
				lblConfirmationNumberForCancel.getText(),
				"Transfer Details for Confirmation Number: " + confirmationNo))
			Reporter.logEvent(Status.PASS,
					"Verify Confirmation number displayed",
					"Expected : Transfer Details for Confirmation Number: "
							+ confirmationNo + "\n Actual : "
							+ lblConfirmationNumberForCancel.getText(), true);
		else
			Reporter.logEvent(
					Status.FAIL,
					"Verify Confirmation number displayed",
					"Expected : "
							+ "Expected : Transfer Details for Confirmation Number: "
							+ confirmationNo + "\n Actual : "
							+ lblConfirmationNumberForCancel.getText(), true);

		btnCancelTransferF2F.click();
		Reporter.logEvent(Status.INFO, "Verify Transfer Cancelled",
				"Transfer Cancelled", true);
		Web.getDriver().switchTo().defaultContent();
	}
	public void rebalanceInvestment_New(int noOfInvestmentoptions,String[] percent) {
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(btnChooseIndividualFunds);
		Web.clickOnElement(btnChooseIndividualFunds);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		if(Web.isWebElementDisplayed(lnkAddViewAllFunds, true)){
		Reporter.logEvent(Status.PASS,
				"verify if Rebalance Your Portfolio Page is Displayed",
				"Rebalance Your Portfolio Page is Displayed", true);
		}
		else{
		Reporter.logEvent(Status.FAIL,
				"verify if Rebalance Your Portfolio Page is Displayed",
				"Rebalance Your Portfolio Page is Not Displayed", true);
		}
		if(Web.isWebElementsDisplayed(btnRemoveInvestment))
		{
			for(int i=0;i<btnRemoveInvestment.size();i++){
				btnRemoveInvestment.get(i).click();
				Web.waitForPageToLoad(Web.getDriver());
			}
		}
				Web.clickOnElement(lnkAddViewAllFunds);
				Web.waitForPageToLoad(Web.getDriver());
				
		if (Web.isWebElementDisplayed(tableAllocationFund)) {
			Reporter.logEvent(Status.PASS,
					"Verify Investment Allocation table is displayed",
					"Investment Allocation table is displayed ", true);
			////////////////////////////////////////////
			int noOfRows = inpInvestmentOption.size();
			if (noOfRows >= noOfInvestmentoptions) {
				Reporter.logEvent(Status.PASS,
						"Verify Investment options are available",
						"Investment options are available ", false);
				investmentFundName1 = txtInvestmentOption.get(1).getText()
						.trim();
				investmentFundName2 = txtInvestmentOption.get(2).getText()
						.trim();
				System.out.println(investmentFundName1);
				System.out.println(investmentFundName2);
				Web.clickOnElement(inpInvestmentOption.get(1));
				Web.clickOnElement(inpInvestmentOption.get(2));
				Reporter.logEvent(Status.INFO, "Selected Investment Options",
						"Selected Investment Options : \n" 
								+ investmentFundName1 + ",\n"
								+ investmentFundName2 , true);
			} else
				Reporter.logEvent(Status.FAIL,
						"Verify Investment options are available",
						noOfInvestmentoptions+"Investment options are not available ", true);

		} else
			Reporter.logEvent(Status.FAIL,
					"Verify Investment options Table is displayed",
					"Investment options table is not displayed ", true);
		Web.clickOnElement(btnAdd);
			
		WebElement inptAllocationPercent1 = Web.getDriver().findElement(By
				.xpath(inputAllocationPercrntage.replace("Investment Option",
						investmentFundName1)));
		Web.setTextToTextBox(inptAllocationPercent1, percent[0]);
		WebElement inptAllocationPercent2 = Web.getDriver().findElement(By
				.xpath(inputAllocationPercrntage.replace("Investment Option",
						investmentFundName2)));
		Web.setTextToTextBox(inptAllocationPercent2, percent[1]);
		Web.waitForPageToLoad(Web.getDriver());
		if(txttotalInvestmentPercent.getText().contains("100")){
			Reporter.logEvent(Status.PASS,
					"Verify Investment Percentage is Entered and Equals to 100",
					"Investment Percentage is Matching and Equals to 100", true);
		}
		else 
			{
			Reporter.logEvent(Status.FAIL,
							"Verify Investment Percentage is Entered and Matching",
				"Investment Percentage is not Matching", true);
			}
		btnSubmit.click();
		if(Web.isWebElementDisplayed(btnAlertContinue, true)){
			Web.clickOnElement(btnAlertContinue);
		}
	Common.waitForProgressBar();
	Web.waitForPageToLoad(Web.getDriver());
	String currentFund1=txtCurrentFunds.get(0).getText().trim();
	String currentFund2=txtCurrentFunds.get(1).getText().trim();
	if(currentFund1.equalsIgnoreCase(investmentFundName1)&&currentFund2.equalsIgnoreCase(investmentFundName2)){
		
		Reporter.logEvent(Status.PASS,
				"Verify Selected Investment Options are Matching in Review your changes Page",
				"Investment Options are Matching in Review your changes Page\nExpected\n:"+investmentFundName1+",\n"+investmentFundName2+"\nActual:\n"+currentFund1+",\n "+currentFund2, true);
	}
	else 
		{
		Reporter.logEvent(Status.FAIL,
				"Verify Selected Investment Options are Matching in Review your changes Page",
				"Investment Options are Matching in Review your changes Page\nExpected:"+investmentFundName1+" and "+investmentFundName2+"\nActual:"+currentFund1+" and "+currentFund2, true);
		}
	}
}
