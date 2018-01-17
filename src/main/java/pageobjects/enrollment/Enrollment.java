package pageobjects.enrollment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;

import com.aventstack.extentreports.*;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.beneficiaries.MyBeneficiaries;
import pageobjects.general.LeftNavigationBar;
import pageobjects.login.TwoStepVerification;

public class Enrollment extends LoadableComponent<Enrollment> {
	
//Declarations
	private LoadableComponent<?> parent;
	//private static boolean waitforLoad = false;
	
	@FindBy(xpath="//h1[contains(text(),'Enrollment')]") private WebElement hdrEnrollment;
	@FindBy(id="home") private WebElement lnkHome;
	@FindBy(id="btn-enroll submit") private WebElement btnEnrollSubmit;
	@FindBy(id="btn-enroll submit") private WebElement hdrEnrollmentDetails;
	@FindBy(xpath="//div[@class='row']//a[text()='Skip & View My Account']") private WebElement btnSkipAndViewAccount;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath="//*[@id='quickEnrollmentLabel']/span") private WebElement inpQuickEnrollment;
	@FindBy(xpath="//*[@id='customEnrollmentLabel']/span") private WebElement inpCustomEnrollment;
	@FindBy(id="btnCustom") private WebElement btnGetStarted;
	@FindBy(xpath="//div[contains(@class,'title')]//h1") private WebElement hdrPriorContribution;
	@FindBy(xpath="//div[contains(@class,'title')]//p") private WebElement txtPriorContribution;
	@FindBy(xpath="//label[./input[@id='radioPreviousContributionYes']]/span") private WebElement inpPriorPlanYes;
	@FindBy(xpath="//label[./input[@id='radioPreviousContributionNo']]/span") private WebElement inpPriorPlanNo;
	//@FindBy(xpath="//button[contains(text(),'Continue')]") private WebElement btnContinue;
	@FindBy(xpath="//span[contains(text(),'Continue')]") private WebElement btnContinue;
	@FindBy(id="skipButton") private WebElement btnSkip;
	@FindBy(xpath="//button[text()[normalize-space()='Back']]") private WebElement btnBack;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(id="regular") private WebElement inpContribution;
	@FindBy(xpath="//div[@ng-if='contributionRate']//span") private WebElement txtContributionRate;
	@FindBy(xpath="//*[@id='companyMatch']") private WebElement txtCoMpanyMatch;
	@FindBy(xpath="//tr[./th[contains(text(),'Confirmation Number:')]]/td") private WebElement txtConfirmationNo;
	@FindBy(xpath="//a[text()[normalize-space()='View My Account']]") private WebElement btnViewMyAccount;
	@FindBy(id="autoEnrollEligibleMsg3") private WebElement txtAutoEnrollment;
	@FindBy(id="quickEnrollEligibleMsg") private WebElement txtQuickEnrollment;
	@FindBy(xpath="//div[@ng-if='contributionRate']/span") private WebElement txtAutoEnrollContributionPecent;
	@FindBy(xpath="//pw-quick-enrollment//legend") private WebElement txtAutoEnrollPriorContribution;
	@FindBy(xpath="//label[@id='madeContributionsThisYearYes']") private WebElement inpAutoEnrollPriorPlanYes;
	@FindBy(xpath="//label[@id='madeContributionsThisYearNo']") private WebElement inpAutoEnrollPriorPlanNo;
	@FindBy(className="paycheck-contribution") private WebElement txtPayCheckEstimate;
	@FindBy(linkText="Participation Agreement for Online Enrollment") private WebElement lnkParticipationAgreement;
	@FindBy(xpath="//div[contains(@class,'participation-agreement-modal')]//div[@class='modal-content']") private WebElement txtModalContent;
	@FindBy(xpath="//a[text()[normalize-space()='I Agree']]") private WebElement lnkIAgree;
	@FindBy(xpath="//div[contains(@ng-if,'investmentOptions')]//h4") private WebElement txtInvestmentOption;
	@FindBy(id="btnSubmit submit") private WebElement btnPriorPlanContinue;
	@FindBy(id="buttonContinue submit") private WebElement btnMyContributionConfirm;
	@FindBy(id="do-it-for-me-link") private WebElement btnDoItForMe;
	@FindBy(xpath="//button[@class='btn btn-primary pull-right']") private WebElement btnRetirementContinue;
	@FindBy(linkText="Next, Add Beneficiaries") private WebElement lnkNextBeneficiaries;
	@FindBy(xpath="//span[@class='currency']") private WebElement txtBalance;
	@FindBy(partialLinkText="Account overview") private WebElement lnkAccountOverview;
	@FindBy(id="buttonContinue") private WebElement btnEnrollmentContinue;
	@FindBy(id="submit") private WebElement lnkViewMyAccount;

	
	private String textField="//*[contains(text(),'webElementText')]";
	

	/** Empty args constructor
     * 
     */
	 /** Empty args constructors
     * 
     */
    public Enrollment() {
    	this.parent = new TwoStepVerification();
    	PageFactory.initElements(lib.Web.getDriver(), this);
    }
    
    /** Constructor taking parent as input
     * 
     * @param parent
     */
	public Enrollment(LoadableComponent<?> parent){
		//this.driver = DriveSuite.webDriver;
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	@Override
	protected void isLoaded() throws Error {

		Assert.assertTrue(Web.isWebElementDisplayed(this.hdrEnrollment),"Enrollment Page is Not Loaded");
		
		
	}

	@Override
	protected void load() {
		TwoStepVerification mfaScreen = (TwoStepVerification) this.parent;
		this.parent.get();
		
		if (!Web.isWebElementDisplayed(this.lnkHome)) {
			mfaScreen.selectCodeDeliveryOption("ALREADY_HAVE_CODE");
			try {
				mfaScreen.submitVerificationCode(Stock.getConfigParam("defaultActivationCode"), true, false);
			
				Common.waitForProgressBar();
				Web.waitForPageToLoad(Web.getDriver());
				Thread.sleep(1000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
	
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Header Prior Plan Contribution")) {
			return this.hdrPriorContribution;
		}
		if (fieldName.trim().equalsIgnoreCase("Continue Button")) {
			return this.btnContinue;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Back Button")) {
			return this.btnBack;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Skip Button")) {
			return this.btnSkip;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Header Prior Plan Contribution")) {
			return this.hdrPriorContribution;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Get Started Button")) {
			return this.btnGetStarted;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Button I Agree Enroll Now")) {
			return this.btnEnrollSubmit;
		}
		if (fieldName.trim().equalsIgnoreCase("Confirmation Number")) {
			return this.txtConfirmationNo;
		}
		if (fieldName.trim().equalsIgnoreCase("Button View My Account")) {
			return this.btnViewMyAccount;
		}
		if (fieldName.trim().equalsIgnoreCase("Auto Enrollment Message")) {
			return this.txtAutoEnrollment;
		}
		if (fieldName.trim().equalsIgnoreCase("Quick Enrollment Message")) {
			return this.txtQuickEnrollment;
		}
		if (fieldName.trim().equalsIgnoreCase("Auto Enrollment Contribution Percent")) {
			return this.txtAutoEnrollContributionPecent;
		}
		if (fieldName.trim().equalsIgnoreCase("Label Yes")) {
			return this.inpAutoEnrollPriorPlanYes;
		}
		if (fieldName.trim().equalsIgnoreCase("Label No")) {
			return this.inpAutoEnrollPriorPlanNo;
		}
		if (fieldName.trim().equalsIgnoreCase("Link Participation Agreement")) {
			return this.lnkParticipationAgreement;
		}
		if (fieldName.trim().equalsIgnoreCase("Participation Agreement Modal")) {
			return this.txtModalContent;
		}
		if (fieldName.trim().equalsIgnoreCase("Link I Agree")) {
			return this.lnkIAgree;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Prior Plan Continue")) {
			return this.btnPriorPlanContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("Enrollment Continue")) {
			return this.btnEnrollmentContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("My Contributions Confirm")) {
			return this.btnMyContributionConfirm;
		}
		if (fieldName.trim().equalsIgnoreCase("Do it for me")) {
			return this.btnDoItForMe;
		}
		if (fieldName.trim().equalsIgnoreCase("Retirement Continue")) {
			return this.btnRetirementContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("Add Beneficiaries")) {
			return this.lnkNextBeneficiaries;
		}
		if (fieldName.trim().equalsIgnoreCase("Account Overview")) {
			return this.lnkAccountOverview;
		}
		if (fieldName.trim().equalsIgnoreCase("Read Balance")) {
			return this.txtBalance;
		}
		if (fieldName.trim().equalsIgnoreCase("View My Account Link")) {
			return this.lnkViewMyAccount;
		}
		if (fieldName.trim().equalsIgnoreCase("Prior Plan No")) {
			return this.inpPriorPlanNo;
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

    
    public void selectQuickEnroll(){
    	Web.waitForElement(inpQuickEnrollment);
    	Web.clickOnElement(inpQuickEnrollment);
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
    /**
     * Method to select Customize Enrollment 
     */
    public void selectCustomizeEnroll(){
    	Web.waitForElement(inpCustomEnrollment);
    	Web.clickOnElement(inpCustomEnrollment);
    	Web.waitForElement(btnGetStarted);
    	verifyWebElementDisplayed("Get Started Button");
    	isTextFieldDisplayed("You may customize your enrollment by clicking \"Get Started\".");
    	Web.clickOnElement(btnGetStarted);
    }
    
    /**
	 * <pre>
	 * Method to Verify Text Field is Displayed on the WebPage
	 * </pre>
	 * 
	 *
	 */

    public boolean isTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		
		if (isTextDisplayed)
			lib.Reporter.logEvent(Status.PASS, "Verify TEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Displayed",
					false);

		}
		catch(NoSuchElementException e){
			lib.Reporter.logEvent(Status.FAIL, "VerifyTEXT Field " + fieldName
					+ "  is Displayed", "TEXT Field '"+fieldName + "' is Not Displayed", false);
			isTextDisplayed=false;
		}
	
  return isTextDisplayed;
	}
    /**
	 * <pre>
	 * Method to Verify Text Field is Displayed on the WebPage
	 * </pre>
	 * 
	 * @return boolean isTextDisplayed
	 */

    public boolean verifyTextFieldDisplayed(String fieldName) {
		boolean isTextDisplayed=false;
		try{
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", fieldName)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		}
		catch(NoSuchElementException e){
			
			isTextDisplayed=false;
		}
	
  return isTextDisplayed;
	}
    /**
   	 * <pre>
   	 * Method to Verify Webelement is Displayed
   	 * 
   	 * </pre>
   	*/
    public boolean verifyWebElementDisplayed(String fieldName) {

		boolean isDisplayed = Web.isWebElementDisplayed(
				this.getWebElement(fieldName), true);

		if (isDisplayed) {

			Reporter.logEvent(Status.PASS, "Verify \'"+fieldName+"\' is displayed",
					"\'"+fieldName+"\' is displayed", false);
			isDisplayed = true;

		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify\'"+fieldName+"\' is displayed",
					"\'"+fieldName+"\' is not displayed", false);
		}

		return isDisplayed;

	}
	
    /**
   	 * <pre>
   	 * Method to Verify All Fields in Prior Plan Contribution Page
   	 * </pre>
   	  
   	 */
    public void verifyPriorPlanContributionsPage(){
		
		String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		Web.waitForElement(hdrPriorContribution);
		verifyPageHeaderIsDisplayed("Header Prior Plan Contribution");
		String actualText = txtPriorContribution.getText();
		if(lib.Web.VerifyPartialText("Have you made contributions to any other retirement plans since 1/1/"+year+"?",actualText , true))
			Reporter.logEvent(Status.PASS, "Verify text in Prior Contributions page", "text is matching\nExpected:Have you made contributions to any other retirement plans since 1/1/"+year+"? \nActual:"+actualText, true);
		else
			Reporter.logEvent(Status.FAIL, "Verify text in Prior Contributions page", "text is not matching\nExpected:Have you made contributions to any other retirement plans since 1/1/"+year+"? \nActual:"+actualText, true);
		verifyWebElementDisplayed("Continue Button");
		verifyWebElementDisplayed("Skip Button");
		verifyWebElementDisplayed("Back Button");
		isTextFieldDisplayed("You are solely responsible for the accuracy of the contribution information you provide and it may result in you overcontributing or undercontributing for the year.");
		}
    /**
	 * <pre>
	 * Method to Verify the Page Header is Displayed or Not
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyPageHeaderIsDisplayed(String webElement) {
		
		WebElement webelement= getWebElement(webElement);
	 String pageHeader=webelement.getText().toString().trim();
		if (Web.isWebElementDisplayed(webelement)) {
			lib.Reporter.logEvent(Status.PASS, "Verify "+pageHeader 
					+ " Page  is Displayed", "Verify "+pageHeader 
					+ " Page  is Displayed",
					true);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "Verify "+pageHeader 
					+ " Page  is Displayed", "Verify "+pageHeader 
					+ " Page  is Not Displayed",true);
			throw new Error(webElement+" is not displayed");
		}
	

	}
	
	 /**
     * Method to Add Previous Contributions for Prior Plan 
     */
    public void addPreviousContribution(String contributionAmount){
    	Web.waitForElement(inpPriorPlanYes);
    	Web.clickOnElement(inpPriorPlanYes);
    	Web.waitForElement(inpContribution);
    	Web.setTextToTextBox(inpContribution, contributionAmount);
        Web.clickOnElement(btnContinue);
    }
    
    /**
	 * <pre>
	 * Method to Verify the Page Header is Displayed or Not
	 *
	 * </pre>
	 * 
	 *
	 */
	public void verifyContributionRateMatching(String contributionRate) {
		
		Web.waitForElement(txtContributionRate);
		String actualContributionRate=txtContributionRate.getText().toString().trim();
		String expectedContributionRate=contributionRate+"%";
		if (actualContributionRate.equalsIgnoreCase(expectedContributionRate)) {
			Reporter.logEvent(Status.PASS, "Verify Contribution Rate is Matching in Enrollment Page", 
					"Contribution Rate is Matching in Enrollment Page\nExpected:"+expectedContributionRate+"\nActual:"+actualContributionRate ,
					false);

		} else {
					
			Reporter.logEvent(Status.FAIL, "Verify Contribution Rate is Matching in Enrollment Page", 
					"Contribution Rate is not Matching in Enrollment Page\nExpected:"+expectedContributionRate+"\nActual:"+actualContributionRate ,
					false);
			
		}
		
		if (Web.isWebElementDisplayed(txtCoMpanyMatch)) {
			Reporter.logEvent(Status.PASS, "Verify Company Match is Displayed in Enrollment Page", 
					"Company Match is Displayed in Enrollment Page" ,
					false);

		} else {
					
			Reporter.logEvent(Status.FAIL,"Verify Company Match is Displayed in Enrollment Page", 
					"Company Match is Not Displayed in Enrollment Page" ,
					false);
		}
	

	}
	public boolean isInvestmentOptiondDisplayed(String investmentOption) {
		boolean isTextDisplayed=false;
		 WebElement txtField= Web.getDriver().findElement(By.xpath(textField.replace("webElementText", investmentOption)));
	
		isTextDisplayed = Web.isWebElementDisplayed(txtField, true);
		if (isTextDisplayed) {
			lib.Reporter.logEvent(Status.PASS, "Verify Investment Option " + investmentOption
					+ "  is Displayed", "Investment Option '"+investmentOption + "' is Displayed",
					false);

		} else {
					
			lib.Reporter.logEvent(Status.FAIL, "Verify Investment Option " + investmentOption
					+ "  is Displayed", "Investment Option'"+investmentOption + "' is Not Displayed", false);
			throw new Error(investmentOption+" is not displayed");
		}
	
return isTextDisplayed;
	}
	/**
	 * <pre>
	 * Method to get the text of an webElement
	 * Returns string webElement is displayed
	 * </pre>
	 * 
	 * @return String - getText
	 */
	public String getWebElementText(String fieldName) {
		String getText = "";

		if (verifyWebElementDisplayed(fieldName)) {

			getText = this.getWebElement(fieldName).getText().trim();

		}

		return getText;

	}
	/**
	 * Method to verify Auto Enrollment Section
	 * @return contributionPecent
	 */
	 public String  verifyAutoEnrollmentSection(){
	   String contributionPecent="";
	   String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
		 Web.waitForElement(btnEnrollSubmit);
		 String actualMsg=getWebElementText("Auto Enrollment Message");
		 String expectedMsg="To elect to enroll using the following contribution rate and investment option, click \"I Agree Enroll Now.\" "
		 		+ "Or you may customize your enrollment below. If you do not make an election before 8/23/2017, "
		 		+ "you will be automatically enrolled using the identified contribution rate(s) and investment option(s), "
		 		+ "and the changes will be effective as soon as administratively feasible.";
		 if (actualMsg.equalsIgnoreCase(expectedMsg)) {
			Reporter.logEvent(Status.PASS, "Verify Auto Enrollment Message is Displayed",
						"Auto Enrollment Message is Displayed\nExpected:"+expectedMsg+"\nActual:"+actualMsg,
						false);

			} else {
						
				lib.Reporter.logEvent(Status.FAIL,  "Verify Auto Enrollment Message is Displayed",
						"Auto Enrollment Message is Not Matching\nExpected:"+expectedMsg+"\nActual:"+actualMsg,
						false);
			}
		 isTextFieldDisplayed("CONTRIBUTION RATE");
		 if(Web.isWebElementDisplayed(txtAutoEnrollContributionPecent)){
			 if(txtAutoEnrollContributionPecent.getText().contains("%")){
				 contributionPecent=txtAutoEnrollContributionPecent.getText().toString().trim();	 
			 Reporter.logEvent(Status.PASS, "Verify Contribution Percent is Displayed",
						"Contribution Percent is Displayed in Percent(%) ",
						false);
			 }
		else
			 Reporter.logEvent(Status.FAIL,  "Verify Contribution Percent is Displayed",
						"Contribution is Not in %",
						false);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,  "Verify Contribution Percent is Displayed",
						"Contribution Percent is Not Displayed",
						false);
		 }
		  String actualText = txtAutoEnrollPriorContribution.getText().toString().trim();
			if(lib.Web.VerifyPartialText("Have you made contributions to any other retirement plans since 1/1/"+year+"?",actualText , true))
				Reporter.logEvent(Status.PASS, "Verify text in Prior Contributions page", "text is matching\nExpected:Have you made contributions to any other retirement plans since 1/1/"+year+"? \nActual:"+actualText, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify text in Prior Contributions page", "text is not matching\nExpected:Have you made contributions to any other retirement plans since 1/1/"+year+"? \nActual:"+actualText, true);
			verifyWebElementDisplayed("Label Yes");
			verifyWebElementDisplayed("Label No");
			
		
		 if(btnEnrollSubmit.isEnabled())
			 Reporter.logEvent(Status.PASS, "Verify 'I Agree,Enroll Now' Button is Enabled",
					"'I Agree,Enroll Now' Button is Enabled",
					false);
	else
		 Reporter.logEvent(Status.FAIL,  "Verify 'I Agree,Enroll Now' Button is Enabled",
					"'I Agree,Enroll Now' Button is Not Enabled",
					false);
		 return contributionPecent;
	 } 	
	 
	 
	 public String verifyQuickEnrollmentSection(){
		 String contributionPecent="";
		   String year = Integer.toString(Calendar.getInstance().get(Calendar.YEAR));
			
		 Web.waitForElement(btnEnrollSubmit);
		 String actualMsg=getWebElementText("Quick Enrollment Message");
		 String expectedMsg="To elect to enroll using the following contribution rate and investment option, click \"I Agree, Enroll Now.\" "
		 		+ "Or you may customize your enrollment below.";
		 if (actualMsg.equalsIgnoreCase(expectedMsg)) {
			Reporter.logEvent(Status.PASS, "Verify Quick Enrollment Message is Displayed",
						"Quick Enrollment Message is Displayed\nExpected:"+expectedMsg+"\nActual:"+actualMsg,
						true);

			} else {
						
				Reporter.logEvent(Status.FAIL,  "Verify Quick Enrollment Message is Displayed",
						"Quick Enrollment Message is Not Matching\nExpected:"+expectedMsg+"\nActual:"+actualMsg,
						true);
			}
		 isTextFieldDisplayed("CONTRIBUTION RATE");
		 if(Web.isWebElementDisplayed(txtAutoEnrollContributionPecent)){
			 if(txtAutoEnrollContributionPecent.getText().contains("%")){
				 contributionPecent=txtAutoEnrollContributionPecent.getText().toString().trim();	 
			 Reporter.logEvent(Status.PASS, "Verify Contribution Percent is Displayed",
						"Contribution Percent is Displayed in Percent(%) ",
						false);
			 }
		else
			 Reporter.logEvent(Status.FAIL,  "Verify Contribution Percent is Displayed",
						"Contribution is Not in %",
						false);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL,  "Verify Contribution Percent is Displayed",
						"Contribution Percent is Not Displayed",
						false);
		 }
		 
		 if(Web.isWebElementDisplayed(txtPayCheckEstimate)){
			 if(txtPayCheckEstimate.getText().contains("$")){
				
			 Reporter.logEvent(Status.PASS, "Verify Paycheck Estimate is Displayed",
						"Paycheck Estimate is Displayed in Dollar($) ",
						false);
			 }
		else
			 Reporter.logEvent(Status.FAIL,  "Verify Paycheck Estimate is Displayed",
						"Paycheck Estimate is Not Displayed in Dollar($) ",
						false);
		 }
		 else{
			 Reporter.logEvent(Status.FAIL, "Verify Paycheck Estimate is Displayed",
						"Paycheck Estimate is Not Displayed",
						false);
		 }
		 
		 isTextFieldDisplayed("INVESTMENT OPTION");
		 
		 if (Web.isWebElementDisplayed(txtInvestmentOption)) {
				Reporter.logEvent(Status.PASS, "Verify Default Investment Option is Displayed",
							" Default Investment Option is Displayed\nInvestment Option:"+txtInvestmentOption.getText().toString().trim(),
							true);

				} else {
							
					Reporter.logEvent(Status.FAIL,  "Verify Default Investment Option is Displayed",
							" Default Investment Option is Not Displayed",
							true);
				}
		  String actualText = txtAutoEnrollPriorContribution.getText().toString().trim();
			if(lib.Web.VerifyPartialText("Have you made contributions to any other retirement plans since 1/1/"+year+"?",actualText , true))
				Reporter.logEvent(Status.PASS, "Verify text in Prior Contributions page", "text is matching\nExpected:Have you made contributions to any other retirement plans since 1/1/"+year+"? \nActual:"+actualText, true);
			else
				Reporter.logEvent(Status.FAIL, "Verify text in Prior Contributions page", "text is not matching\nExpected:Have you made contributions to any other retirement plans since 1/1/"+year+"? \nActual:"+actualText, true);
			verifyWebElementDisplayed("Label Yes");
			verifyWebElementDisplayed("Label No");
			
		
		 if(btnEnrollSubmit.isEnabled())
			 Reporter.logEvent(Status.PASS, "Verify 'I Agree,Enroll Now' Button is Enabled",
					"'I Agree,Enroll Now' Button is Enabled",
					false);
		 else
		 Reporter.logEvent(Status.FAIL,  "Verify 'I Agree,Enroll Now' Button is Enabled",
					"'I Agree,Enroll Now' Button is Not Enabled",
					false);
		 return contributionPecent;
	}
	 
	 public String getElementText(String fieldName) {
			return Web.getWebElementText(this.getWebElement(fieldName)).trim();
		}
		
	    
}
