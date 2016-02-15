package pageobjects.liat;

import java.util.ArrayList;
import java.util.List;

import lib.Reporter;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;






import org.testng.Assert;

import pageobjects.landingpage.LandingPage;

public class HealthCareCosts extends LoadableComponent<HealthCareCosts>  {
	

	//Declarations
	private LoadableComponent<?> parent;
	//private static boolean waitforLoad = false;
	
	@FindBy(xpath=".//*[text()='Health-care costs']") private WebElement lblHelathCareCosts;
	@FindBy(xpath=".//*[text()[normalize-space()='Personalize']]") private WebElement btnPersonalize;
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
	@FindBy(id="projected-health-care-costs-chart") private WebElement lblPieChart;
	
	@FindBy(xpath="//table[@class='simple']//tr/td[3]") private List<WebElement> lstHealthcareCosts;
	
	/** Default constructor
	 * 
	 */
	public HealthCareCosts()
	{
		this.parent=new LandingPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Parameter Constructor
	 * 
	 * @param parent
	 */
	public HealthCareCosts(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}

	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(btnPersonalize));
	}

	@Override
	protected void load() {
		this.parent.get();
		((LandingPage) this.parent).dismissPopUps(true,true);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.lblHelathCareCosts.click();
		
		
	}
	
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		//Log out
		if (fieldName.trim().equalsIgnoreCase("Health-care costs") || fieldName.trim().equalsIgnoreCase("Health care costs")) {
			return this.lblHelathCareCosts;
		}
		return null;
	}
	
	 /**<pre> Method to verify the health care cost by taking onto consideration the values see form the UI.
     *.</pre>
     * @param estMonthlyIncome - a float value - the value that is read from the application from retirement income page eg "$12,123.50"
     * 
     * @return - void
     */
	
//	public void verifyHealthCostFromUI(float estMonthlyIncome){
//		float doctorAndTestsPartABCost = common.getIntegerCurrency(this.lblDoctorAndTestsPartAandBCost.getText());
//		float prescriptionDrugsPartDCost = common.getIntegerCurrency(this.lblPrescriptionDrugPartDCost.getText());
//		float medicareSupplementalCost = common.getIntegerCurrency(this.lblMedicareSupplementalCost.getText());
//		float dentalInsuranceCost = common.getIntegerCurrency(this.lblDentalInsuranceCost.getText());
//		float hearingAndVisionCost = common.getIntegerCurrency(this.lblHearingAndVisionCost.getText());
//		float prescriptionDrugsCost = common.getIntegerCurrency(this.lblPrescriptionDrugCost.getText());
//		float dentalCost = common.getIntegerCurrency(this.lblDentalCost.getText());
//		float doctorsAndTestsCost = common.getIntegerCurrency(this.lblDoctorAndTestsCost.getText());
//		
//		float totalHealthCareCost = doctorAndTestsPartABCost + prescriptionDrugsPartDCost + medicareSupplementalCost
//									+ dentalInsuranceCost + hearingAndVisionCost + prescriptionDrugsCost + dentalCost +
//									doctorsAndTestsCost;
//		float projectedHealthCareCost = common.getIntegerCurrency(this.lblProjectedHlthCareCost.getText());
//		
//		float calProjHlthCareCost = estMonthlyIncome - totalHealthCareCost;
//		
//		if(((int)projectedHealthCareCost-(int)calProjHlthCareCost)<=0.04)
//			Reporter.logEvent(Status.PASS, "Verify the Projected health Cost", "Projected health care cost is validated as per the values seen on the UI ", false);
//		else
//			Reporter.logEvent(Status.FAIL, "Verify the Projected health Cost", "Projected health care cost is Not validated as per the values seen on the UI ", false);
//		
//		
//	}
	
	public void verifyHealthCostFromUI(float estMonthlyIncome){
		
		 float medicareSupplementalCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(0).getText());
		 float doctorAndTestsPartABCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(1).getText());
		 float prescriptionDrugsPartDCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(2).getText());
		 float dentalInsuranceCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(3).getText());
		 float prescriptionDrugsCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(4).getText());
		 float hearingAndVisionCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(5).getText());
		 float dentalCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(6).getText());
		 float doctorsAndTestsCost = Web.getIntegerCurrency(this.lstHealthcareCosts.get(7).getText());
		
//		
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
        float projectedHealthCareCost = Web.getIntegerCurrency(this.lblProjectedHlthCareCost.getText());
        
        float calProjHlthCareCost = estMonthlyIncome - totalHealthCareCost;
        
        if(((int)projectedHealthCareCost-(int)calProjHlthCareCost)<=0.04)
               Reporter.logEvent(Status.PASS, "Verify the Projected health Cost", "Projected health care cost is validated as per the values seen on the UI ", false);
        else
               Reporter.logEvent(Status.FAIL, "Verify the Projected health Cost", "Projected health care cost is Not validated as per the values seen on the UI ", false);
        
        
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
			this.btnPersonalize.click();				
			if (Web.isWebElementDisplayed(this.btnUpdate, false)) {
			Reporter.logEvent(Status.PASS, "Click Personalize button ", "Personalize button is clicked and verified", true);
		}else {
			Reporter.logEvent(Status.FAIL, "Click Personalize button", "Persnozalize button is not available",true);
		}
	}
	
}
