package pageobjects.liat;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import lib.Reporter;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import pageobjects.landingpage.LandingPage;
import appUtils.Common;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class HealthCareCosts extends LoadableComponent<HealthCareCosts>  {
	

	//Declarations
	private LoadableComponent<?> parent;
	
	@FindBy(xpath=".//*[text()='Healthcare costs']") private WebElement lblHelathCareCosts;
	@FindBy(xpath=".//button[contains(@class,'btn-personalize')]") private WebElement btnPersonalize;
	@FindBy(xpath=".//*[text()[normalize-space()='Update']]") private WebElement btnUpdate;
	@FindBy(xpath="//div[@id='attainedAgeSlider']//button[@class='sliderThumb']") private WebElement sliderAttainedAge;
	@FindBy(xpath=".//*[text()[normalize-space()='Personalize your health-care costs']]") private WebElement lblPersonalizeHlthCareCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Doctors & Tests (Part A & B)']]/../td[3]") private WebElement lblDoctorAndTestsPartAandBCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Prescription Drugs (Part D)']]/../td[3]") private WebElement lblPrescriptionDrugPartDCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Medicare Supplemental']]/../td[3]") private WebElement lblMedicareSupplementalCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Dental Insurance']]/../td[3]") private WebElement lblDentalInsuranceCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Hearing & Vision']]/../td[3]") private WebElement lblHearingAndVisionCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Prescription Drugs']]/../td[3]") private WebElement lblPrescriptionDrugCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Dental']]/../td[3]") private WebElement lblDentalCost;
	@FindBy(xpath=".//*[text()[normalize-space()='Doctors & Tests']]/../td[3]") private WebElement lblDoctorAndTestsCost;
	@FindBy(id="projected-health-care-costs-income-label") private WebElement lblProjectedHlthCareCost;
	//@FindBy(xpath=".//*[@id='healthcare']//strong[contains(@class,'currency')]") private WebElement lblProjectedHlthCareCost;
	@FindBy(id="projected-health-care-costs-chart") private WebElement lblPieChart;
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='topHeaderUserProfileName']") private WebElement lblUserName;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	@FindBy(xpath="//table[@class='simple']//tr/td[3]") private List<WebElement> lstHealthcareCosts;
	@FindBy(linkText = "Your full report(PDF)") private WebElement lnkFullReport;
	@FindBy(linkText = "www.medicare.gov") private WebElement lnkMedicare;
	@FindBy(id = "medicareModal") private WebElement txtEmpowerModal;
	@FindBy(xpath= ".//*[@id='medicareModal']//h4[@id='myModalLabel']") private WebElement lblEmpowerModal;
	@FindBy(xpath="//div[@class='buttonContainer']//button[contains(@ng-click,'proceed()')]") private WebElement btnContinue;
	@FindBy(xpath="//button[text()[normalize-space()='Tour']]") private WebElement btnTour;
	@FindBy(id = "nextBtn") private WebElement btnNext;
	@FindBy(xpath = ".//*[text()[normalize-space()='Sign In']]") private WebElement btnLogin;
	@FindBy(xpath="//button[contains(@ng-click,'closeModal')]") private WebElement btnCancel;
	@FindBy(xpath="//button[contains(@class,'health-care-details') and ./span[contains(text(),'View details')]]") private WebElement btnViewDetails;
	private String modalHeader="//span[text()[normalize-space()='webElementText']]";
	
	/** Default constructor
	 * 
	 */
	public HealthCareCosts()
	{
		this.parent=new LandingPage();
		PageFactory.initElements(lib.Web.getDriver(), this);
	}
	
	/** Parameter Constructor
	 * 
	 * @param parent
	 */
	public HealthCareCosts(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.getDriver(), this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName),"Health Care Cost Page is Not Loaded");		
		String ssn = Stock.GetParameterValue("userName");
		String userFromDatasheet = null;
		ResultSet strUserInfo=null;
		if(Stock.getConfigParam(Globals.GC_COLNAME_TEST_ENV).contains("PROD"))
		{
			userFromDatasheet=Stock.GetParameterValue("lblUserName");
		}
		else{
		
		try {
			strUserInfo =Common.getParticipantInfoFromDataBase(ssn);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}

		
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		}
		String userLogedIn = this.lblUserName.getText();
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(Web.isWebElementDisplayed(btnPersonalize));
		} else {
			this.lnkLogout.click();
			Web.waitForElement(this.btnLogin);
			Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		}
		
	}

	@Override
	protected void load() {
		this.parent.get();
		//((LandingPage) this.parent).dismissPopUps(true,true);
	
		try {
			Web.waitForElement(this.lblHelathCareCosts);
		} catch (Exception e) {
		}
				
		this.lblHelathCareCosts.click();
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		
	}
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		if (fieldName.trim().equalsIgnoreCase("Health-care costs") || fieldName.trim().equalsIgnoreCase("Health care costs")) {
			return this.lblHelathCareCosts;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Personalize Button")) {
			return this.btnPersonalize;
		}
		if (fieldName.trim().equalsIgnoreCase("Your full report(PDF)")) {
			return this.lnkFullReport;
		}
		return null;
	}
	
	 /**<pre> Method to verify the health care cost by taking onto consideration the values see form the UI.
     *.</pre>
     * @param estMonthlyIncome - a float value - the value that is read from the application from retirement income page eg "$12,123.50"
     * 
     * @return - void
     */
	

	public void verifyHealthCostFromUI(float estMonthlyIncome){
			Web.clickOnElement(btnViewDetails);
		 float medicareSupplementalCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(1).getText());
		 float doctorAndTestsPartABCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(2).getText());
		 float prescriptionDrugsPartDCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(3).getText());
		 float dentalInsuranceCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(4).getText());
		 float prescriptionDrugsCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(5).getText());
		 float hearingAndVisionCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(6).getText());
		 float dentalCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(7).getText());
		 float doctorsAndTestsCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(8).getText());
		 
		 
//        float doctorAndTestsPartABCost = Web.getIntegerCurrency(this.lblDoctorAndTestsPartAandBCost.getText());
//        float prescriptionDrugsPartDCost = Web.getIntegerCurrency(this.lblPrescriptionDrugPartDCost.getText());
//        float medicareSupplementalCost = Web.getIntegerCurrency(this.lblMedicareSupplementalCost.getText());
//        float dentalInsuranceCost = Web.getIntegerCurrency(this.lblDentalInsuranceCost.getText());
//        float hearingAndVisionCost = Web.getIntegerCurrency(this.lblHearingAndVisionCost.getText());
//        float prescriptionDrugsCost = Web.getIntegerCurrency(this.lblPrescriptionDrugCost.getText());
//        float dentalCost = Web.getIntegerCurrency(this.lblDentalCost.getText());
//        float doctorsAndTestsCost = Web.getIntegerCurrency(this.lblDoctorAndTestsCost.getText());
        
        float totalHealthCareCost = doctorAndTestsPartABCost + prescriptionDrugsPartDCost + medicareSupplementalCost
                                                        + dentalInsuranceCost + hearingAndVisionCost + prescriptionDrugsCost + dentalCost +
                                                        doctorsAndTestsCost;
        float projectedHealthCareCost = (float)Math.round(Web.getIntegerCurrency(this.lblProjectedHlthCareCost.getText()));
        
        float calProjHlthCareCost = (float)Math.round(estMonthlyIncome - totalHealthCareCost);
        System.out.println(projectedHealthCareCost);
        System.out.println(calProjHlthCareCost);
        //if(projectedHealthCareCost==calProjHlthCareCost || projectedHealthCareCost==calProjHlthCareCost+1 ||  projectedHealthCareCost==calProjHlthCareCost-1)
        if(((float)projectedHealthCareCost-(float)calProjHlthCareCost)<=1.0)
               Reporter.logEvent(Status.PASS, "Verify the Projected health Cost", "Projected health care cost is validated as per the values seen on the UI \nExpected:"+projectedHealthCareCost+"\nActual:"+calProjHlthCareCost, false);
        else
               Reporter.logEvent(Status.FAIL, "Verify the Projected health Cost", "Projected health care cost is Not validated as per the values seen on the UI \nExpected:"+projectedHealthCareCost+"\nActual:"+calProjHlthCareCost, false);
        
 }

	
	/**<pre> Method to verify the if the pie chart is visible in the health care cost page.
     *.</pre>
     *      * 
     * @return - void
     */
	public void verifyPieChart(){
		if (Web.isWebElementDisplayed(this.lblPieChart,false)) {
			Reporter.logEvent(Status.PASS, "Verify Pie Chart Exists", "Pie chart is visible as expected", true);			
		}else{
			Reporter.logEvent(Status.FAIL, "Verify Pie Chart Exists", "Pie chart is Not visible as expected", true);
		}
	}
	
	/**<pre> Method to verify the if the attained Age Slider is visible in the health care cost page.
     *.</pre>
     *      * 
     * @return - void
     */
	public void verifyAttainedAgeSlide(){
		
		if (Web.isWebElementDisplayed(this.sliderAttainedAge,false)) {
			Reporter.logEvent(Status.PASS, "Verify Attained Age Slide bar Exists", "Attained Age Slide bar is visible as expected", true);			
		}else{
			Reporter.logEvent(Status.FAIL, "Verify Attained Age Slide bar Exists", "Attained Age Slide bar is Not visible as expected", true);
		}
	}
		
		/**<pre> Method to verify on clicking Personalize button details are displayed.
		 * </pre>
		 * @return - void
		 */
	public void verifyPersonalizeBtn() {	
		Actions mouse = new Actions(Web.getDriver());
		//	((JavascriptExecutor) Web.getDriver()).executeScript("window.scrollBy(0,250)", "");
			try{
			Web.getDriver().switchTo().defaultContent();
			Common.waitForProgressBar();
			Web.waitForPageToLoad(Web.getDriver());
			Web.waitForElement(btnPersonalize);
			mouse.moveToElement(btnPersonalize).clickAndHold(btnPersonalize).build().perform();
			
			//Web.clickOnElement(btnPersonalize);				
			if (!Web.isWebElementDisplayed(btnUpdate)) {
			Reporter.logEvent(Status.PASS, "Click Personalize button ", "Personalize button is clicked and verified", true);
			mouse.release(btnPersonalize).build().perform();
			Web.clickOnElement(btnCancel);
		}else {
			/*Web.clickOnElement(btnPersonalize);	
			if (!Web.isWebElementDisplayed(btnUpdate)) {
				Reporter.logEvent(Status.PASS, "Click Personalize button ", "Personalize button is clicked and verified", true);
			}else {*/
			Reporter.logEvent(Status.FAIL, "Click Personalize button", "Persnozalize button is not available",true);
			}
		//}
			}
			
			catch(Exception e){
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "A run time exception occured.", "Exception", true);
				Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getMessage(), true);
			}
	}
	
	
	
	
	public void verifyMedicareLink() throws InterruptedException{
		/*JavascriptExecutor executor = (JavascriptExecutor)Web.getDriver();
		Web.clickOnElement(lblHelathCareCosts);
		Common.waitForProgressBar();
		Web.waitForPageToLoad(Web.getDriver());
		if(!Web.isWebElementDisplayed( lnkMedicare, true))
		Web.clickOnElement(btnPersonalize);*/
		  Web.getDriver().switchTo().defaultContent();
		if(Web.isWebElementDisplayed( lnkMedicare, true)){
			Thread.sleep(5000);
			Reporter.logEvent(Status.PASS,"Verify 'Medicare' link is displayed","'Medicare' link is displayed", false);
			// executor.executeScript("arguments[0].click();", lnkMedicare);
			Web.clickOnElement(lnkMedicare);
			Web.waitForElement(txtEmpowerModal);
				if(!Web.isWebElementDisplayed(txtEmpowerModal, true)){
					//executor.executeScript("arguments[0].click();", lnkMedicare);
					Web.clickOnElement(lnkMedicare);
				}
			Reporter.logEvent(Status.INFO,"Verify 'Medicare' link is clicked","'Medicare' link is clicked", true);
			Web.waitForElement(txtEmpowerModal);
			Thread.sleep(2000);
			if(Web.VerifyText("You are now leaving Empower Retirement", lblEmpowerModal.getText().toString().trim(), true))
				Reporter.logEvent(Status.PASS,"Verify Modal Header","Expected : You are now leaving Empower Retirement \nACTUAL : "+lblEmpowerModal.getText(), false);
			else
				Reporter.logEvent(Status.FAIL,"Verify Modal Header","Expected : You are now leaving Empower Retirement \nACTUAL : "+lblEmpowerModal.getText(), true);
			
			boolean windowFound = false;
			String parentWindow = Web.getDriver().getWindowHandle();
			Web.clickOnElement(btnContinue);
			
			Set<String> handles = Web.getDriver().getWindowHandles();
			for (String windowHandle : handles) {

				if (!windowHandle.equals(parentWindow)) {
					Web.getDriver().switchTo().window(windowHandle);
					Web.waitForPageToLoad(Web.getDriver());
					if (Web.getDriver().getTitle().contains("Medicare.gov: the official U.S. government site for Medicare")) {
						windowFound = true;
						break;
					}
				}
			}
			if (windowFound) 
				lib.Reporter.logEvent(Status.PASS,"Verifying Medicare site is Opened in New Window","Medicare site is Opened in New Window",true);
			else 
				lib.Reporter.logEvent(Status.FAIL,"Verifying Medicare site is Opened in New Window","Medicare site is Not Opened in New Window",true);
			Web.getDriver().close();
			Web.getDriver().switchTo().window(parentWindow);
			Web.clickOnElement(btnCancel);
		}
		else
			Reporter.logEvent(Status.FAIL,"Verify 'Medicare' link is displayed","'Medicare' link is not displayed", true);
		
	}
	
	public void verifyTourModals(){
		
	}
	
//	/** <pre> Method to return the no of plans associated to a user from db
//	 * 
//	 * @return noOfPlans
//	 * @throws Exception 
//	 */
//	public ResultSet getParticipantInfoFromDB(String ssn) throws Exception{
//		
//		//query to get the no of plans
//		String[] sqlQuery = null;
//		try {
//			sqlQuery = Stock.getTestQuery("getParticipantInfo");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		ResultSet participantInfo = DB.executeQuery(sqlQuery[0], sqlQuery[1],ssn);
//		
//		if (DB.getRecordSetCount(participantInfo) > 0) {
//			try {
//				participantInfo.first();			
//			} catch (SQLException e) {
//				e.printStackTrace();
//				Reporter.logEvent(Status.WARNING, "Query Participant Info from DB:" + sqlQuery[0] , "The Query did not return any results. Please check participant test data as the appropriate data base.", false);
//			}
//		}		
//		return participantInfo;
//	}
	
}
