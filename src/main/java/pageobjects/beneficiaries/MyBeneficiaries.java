
package pageobjects.beneficiaries;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import lib.DB;
import lib.Reporter;
import lib.Stock;
import lib.Web;
import lib.Reporter.Status;

import org.openqa.selenium.By;
import org.openqa.selenium.By.ById;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.testng.Assert;

import appUtils.Common;
import pageobjects.general.LeftNavigationBar;



public class MyBeneficiaries extends LoadableComponent<MyBeneficiaries> {

	//Declarations
	private LoadableComponent<?> parent;
	//private static boolean waitforLoad = false;
	
	@FindBy(xpath=".//*[text()='My Beneficiaries']") private WebElement lblMyBeneficiaries;
	@FindBy(id="btn-add-beneficiary") private WebElement btnAddAnotherBeneficiary;
//	@FindBy(id="btn-continue") private WebElement btnContinue;
	@FindBy(id="btn-continue submit") private WebElement btnContinue;
	@FindBy(xpath=".//span[text()='Contingent']") private WebElement btnContingent;
	@FindBy(xpath=".//span[text()='Primary']") private WebElement btnPrimary;
	@FindBy(id="btn-cancel") private WebElement btnCancel;
	@FindBy(id="btn-save") private WebElement btnSave;
	@FindBy(xpath=".//*[@class='page-title']") private WebElement lblAddBeneficiaryTitle;
	@FindBy(xpath=".//*[text()='Primary']") private WebElement btnPrimaryBenType;
	@FindBy(xpath=".//*[text()='Contingent']") private WebElement btnContingentBenType;
	@FindBy(id="beneficiaryType") private WebElement selMyBeneficiary;
	@FindBy(xpath=".//*[contains(@class,'bene-type-header')]") private WebElement lblBenSelectedHdr;
	@FindBy(id="beneficiaryFirstName") private WebElement txtFirstName;
	@FindBy(id="beneficiaryMiddleName") private WebElement txtMiddleName;
	@FindBy(id="beneficiaryLastName") private WebElement txtLastName;
	@FindBy(id="beneficiarySuffix") private WebElement txtSuffix;
	@FindBy(id="dateOfBirth") private WebElement txtDateOfBirth;
	@FindBy(id="socialSecurityNumber") private WebElement txtSSN;
	@FindBy(id="phoneNumber") private WebElement txtPhoneNumber;
	@FindBy(id="useAddressOnFile") private WebElement chkUseMyAddressForBen;
	@FindBy(id="address1") private WebElement txtAddressOne;
	@FindBy(id="address2") private WebElement txtAddressTwo;
	@FindBy(id="city") private WebElement txtCity;
	@FindBy(id="zipCode") private WebElement txtZipCode;
	@FindBy(id="state") private WebElement selState;
	@FindBy(id="country") private WebElement selCountry;
	@FindBy(id="removeBeneficiary") private WebElement chkDelBeneficiary;
	@FindBy(xpath=".//*[@id='account-details-container']/div/div/form/div[1]/div/table/tbody/tr/td[1]/a") private WebElement Linkclick;

	@FindBy(xpath="//h1[text()='Designate beneficiary']") private WebElement lblDesignateBeneficiary;
	@FindBy(xpath="//span[text()='Yes']") private WebElement btnMarried;
	@FindBy(xpath="//span[text()='No']") private WebElement btnUnmarried;
	@FindBy(xpath="//table[@class='beneficiaries primary-beneficiaries table']/tbody") private WebElement tblMyBeneficiaries;
	@FindBy(xpath="//button[text()='Delete']") private WebElement btnDelete;
	@FindBy(xpath="//table[@class='beneficiaries primary-beneficiaries table']//*[@class='beneficiary-name']/a") private List<WebElement> lstlnkPrimaryBeneficiaryName;
	@FindBy(xpath="//table[@class='beneficiaries contingent-beneficiaries table']//*[@class='beneficiary-name']/a") private List<WebElement> lstlnkContingentBeneficiaryName;
	@FindBy(xpath="//*[contains(@class,'contingent')]//*[@class='beneficiary-allocation']//input") private List<WebElement> lsttxtContingentAllocation;
	@FindBy(xpath="//*[contains(@class,'primary')]//*[@class='beneficiary-allocation']//input") private List<WebElement> lsttxtPrimaryAllocation;
	@FindBy(xpath="//div[@class='table-details ng-scope']/table") private List<WebElement> lstTablePrimaryBeneficiary;
	@FindBy(xpath="//div[@class='table-details']/table/tbody/tr") private List<WebElement> lstTblConfirmationDetails;
	@FindBy(xpath=".//*[text()='Confirmation #:']") private WebElement lblConfirmation;
	@FindBy(xpath="//input[@id='primaryBeneficiaryAllocationPercent0']") private WebElement txtAllocationBeneficiary1;
	@FindBy(xpath="//input[@id='primaryBeneficiaryAllocationPercent1']") private WebElement txtAllocationBeneficiary2;
	
	@FindBy(xpath="//input[@id='beneficiaryName']") private WebElement txtBeneficiaryName;
	@FindBy(xpath="//input[@id='dateOfTrust']") private WebElement txtDateOfTrust;
	@FindBy(xpath="//input[@id='taxIdentificationNumber']") private WebElement txtTaxIdentificationNo;
	@FindBy(xpath=".//*[@class='ng-binding ng-scope' and contains(text(),'Date of birth')]") private WebElement txtDOBErrorMsg;
	@FindBy(xpath=".//*[@class='ng-binding ng-scope' and contains(text(),'Tax Identification Number')]") private WebElement txtTINErrorMsg;
	@FindBy(xpath="//div[@class='alert alert-warning']//p") private WebElement lblAlertMsg;
	@FindBy(id="btn-view-beneficiaries") private WebElement btnViewBeneficiaries;
	@FindBy(xpath="//h1[text()='Account Overview']") private WebElement hdrAccountOverview;
	@FindBy(xpath="//div[@class='inner-container with-padding with-shadow']/p") private WebElement authCodeIErrorMsg;
	@FindBy(xpath="//div[@class='error-block ng-scope']/p") private WebElement deleteBeneficiaryErrorMsg;
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	
	/** Empty args constructor
	 * 
	 */
	public MyBeneficiaries() {
		this.parent = new LeftNavigationBar();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as input
	 * 
	 * @param parent
	 */
	public MyBeneficiaries(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(0, ssn.length()-3));
		
<<<<<<< Upstream, based on origin/master
		String userFromDatasheet = null;
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
=======
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(0, ssn.length()-3));
>>>>>>> 5c155f8 modified isloaded logic
		
<<<<<<< Upstream, based on origin/master
=======
		String userFromDatasheet = null;
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		
>>>>>>> 5c155f8 modified isloaded logic
		String userLogedIn = this.lblUserName.getText();
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			if(Stock.GetParameterValue("Add_Allocation").equalsIgnoreCase("No"))
				Assert.assertTrue(lib.Web.isWebElementDisplayed(lblDesignateBeneficiary));
			else
				Assert.assertTrue(lib.Web.isWebElementDisplayed(lblMyBeneficiaries));
		} else {
			this.lnkLogout.click();
		}
	}

	@Override
	protected void load() {
		this.parent.get();
		
		((LeftNavigationBar) this.parent).clickNavigationLink("Beneficiaries");
		
	}
	
	private WebElement getWebElement(String fieldName) {

		if (fieldName.trim().equalsIgnoreCase("Married")) {
			return this.btnMarried;
		}
		
		if (fieldName.trim().equalsIgnoreCase("Unmarried")) {
			return this.btnUnmarried;
		}
		
		if (fieldName.trim().equalsIgnoreCase("DesignateBeneficiary")) {
			return this.lblDesignateBeneficiary;
		}
		
		if (fieldName.trim().equalsIgnoreCase("AddAnotherBeneficiary")) {
			return this.btnAddAnotherBeneficiary;
		}
		
		if (fieldName.trim().equalsIgnoreCase("ContinueAndConfirm")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("DOB Error Msg")) {
			return this.txtDOBErrorMsg;
		}
		if (fieldName.trim().equalsIgnoreCase("DOB")) {
			return this.txtDateOfBirth;
		}
		if (fieldName.trim().equalsIgnoreCase("Beneficiary Relation")) {
			return this.selMyBeneficiary;
		}
		if (fieldName.trim().equalsIgnoreCase("View Beneficiary Button")) {
			return this.btnViewBeneficiaries;
		}
		if (fieldName.trim().equalsIgnoreCase("MyBeneficiaries")) {
			return this.lblMyBeneficiaries;
		}
		if (fieldName.trim().equalsIgnoreCase("TIN")) {
			return this.txtTaxIdentificationNo;
		}
		if (fieldName.trim().equalsIgnoreCase("TIN Error Msg")) {
			return this.txtTINErrorMsg;
		}
		if (fieldName.trim().equalsIgnoreCase("Save button")) {
			return this.btnSave;
		}
		if (fieldName.trim().equalsIgnoreCase("Cancel button")) {
			return this.btnCancel;
		}
		if (fieldName.trim().equalsIgnoreCase("Account Overview")) {
			return this.hdrAccountOverview;
		}
		if (fieldName.trim().equalsIgnoreCase("Auth code I Error Msg")) {
			return this.authCodeIErrorMsg;
		}
		if (fieldName.trim().equalsIgnoreCase("Delete Checkbox")) {
			return this.chkDelBeneficiary;
		}
		if (fieldName.trim().equalsIgnoreCase("Delete button")) {
			return this.btnDelete;
		}
		if (fieldName.trim().equalsIgnoreCase("My Beneficiaries")) {
			return this.lblMyBeneficiaries;
		}
		if (fieldName.trim().equalsIgnoreCase("Submit button")) {
			return this.btnContinue;
		}
		if (fieldName.trim().equalsIgnoreCase("Delete Beneficiary Error Message")) {
			return this.deleteBeneficiaryErrorMsg;
		}
		if (fieldName.trim().equalsIgnoreCase("Alert Msg")) {
			return this.lblAlertMsg;
		}
		if (fieldName.trim().equalsIgnoreCase("First name")) {
			return this.txtFirstName;
		}
		if (fieldName.trim().equalsIgnoreCase("Middle name")) {
			return this.txtMiddleName;
		}
		if (fieldName.trim().equalsIgnoreCase("Last name")) {
			return this.txtLastName;
		}
		if (fieldName.trim().equalsIgnoreCase("Suffix")) {
			return this.txtSuffix;
		}
		if (fieldName.trim().equalsIgnoreCase("SSN")) {
			return this.txtSSN;
		}
		if (fieldName.trim().equalsIgnoreCase("Phone number")) {
			return this.txtPhoneNumber;
		}
		return null;
		
	}
	
	
	/**<pre> Method to check if a particular element is present on the page.
	 *.</pre>
	 * @param fieldName - fieldname
	 * 
	 * @return - boolean
	 */
	public boolean isFieldDisplayed(String fieldName) {
		WebElement element = this.getWebElement(fieldName);
		
		if (element == null) {
			return false;
		} else {
			return Web.isWebElementDisplayed(element);
		}
	}
	
	/**<pre> Method to add a beneficiary.
	 *.</pre>
	 * @param maritalStatus - marital Status of the beneficiary
	 * @param useMyAddress - whether to use current address
	 * @param beneficiaryType - Beneficiary type can be primary or contingent
	 */
	public void addBeneficiary(String maritalStatus, String beneficiaryRelation, String useMyAddress, String beneficiaryType){
		WebElement maritalstatus = this.getWebElement(maritalStatus);
		
		lib.Web.waitForElement(btnContinue);
		if(Web.isWebElementDisplayed(lblDesignateBeneficiary))
			maritalstatus.click();
		
		else{
			Web.clickOnElement(this.btnAddAnotherBeneficiary);
			lib.Web.waitForElement(btnPrimary);
			if(beneficiaryType.equalsIgnoreCase("Primary"))
				
				Web.clickOnElement(this.btnPrimary);
			else
				Web.clickOnElement(this.btnContingent);
		}
		lib.Web.waitForElement(selMyBeneficiary);
		
		Web.selectDropDownOption(this.selMyBeneficiary, beneficiaryRelation);
		if(Stock.GetParameterValue("Beneficiary Relation").equalsIgnoreCase("A Trust"))
			this.enterEntityDetails();
		else{
			this.enterBeneficiaryDetails();
			if(useMyAddress.equalsIgnoreCase("Yes")){
				if(!this.chkUseMyAddressForBen.isSelected())
					this.chkUseMyAddressForBen.click();
			}
			else{
				if(this.chkUseMyAddressForBen.isSelected())
					this.chkUseMyAddressForBen.click();
				this.enterAddressDetails();
			}
		}
			
		Web.clickOnElement(this.btnSave);

		
		if(Stock.GetParameterValue("Add_Allocation").equalsIgnoreCase("Yes")){
			lib.Web.waitForElement(txtAllocationBeneficiary1);
			this.txtAllocationBeneficiary1.clear();
			this.txtAllocationBeneficiary1.sendKeys(Stock.GetParameterValue("Allocation"));
			this.txtAllocationBeneficiary2.sendKeys(Stock.GetParameterValue("Allocation"));
		}
	}
	
	

	
	public boolean clickOnBeneficiaryFromTable(String beneficiaryName,String beneficiaryType){
		boolean clickedBeneficiary=false;
		int noOfBeneficiaries=0;
		if(beneficiaryName!=null){
			if(beneficiaryType.equalsIgnoreCase("Primary")){
					noOfBeneficiaries= this.lstlnkPrimaryBeneficiaryName.size();
					for(int i=0; i<noOfBeneficiaries;i++){
						if(lstlnkPrimaryBeneficiaryName.get(i).getText().contains(beneficiaryName)){
							lstlnkPrimaryBeneficiaryName.get(i).click();
							clickedBeneficiary=true;
							break;
						}
					}
			}
			
			}
			if(beneficiaryType.equalsIgnoreCase("Contingent")){
					noOfBeneficiaries = this.lstlnkContingentBeneficiaryName.size();
					for(int i=0; i<noOfBeneficiaries;i++){
						if(lstlnkContingentBeneficiaryName.get(i).getText().contains(beneficiaryName)){
							lstlnkContingentBeneficiaryName.get(i).click();
							clickedBeneficiary=true;
							break;
						}
							
					}
					
			
		}
		
		if(beneficiaryName==null){
			if(beneficiaryType.equalsIgnoreCase("Primary"))
				lstlnkPrimaryBeneficiaryName.get(0).click();
			else
				lstlnkContingentBeneficiaryName.get(0).click();
		}
		return clickedBeneficiary;
	}
	
	/**<pre> Method to verify the details after adding a beneficiary.
	 *.</pre>
	 * @param attribute - beneficiary attribute to be verified
	 * 
	 * @return - boolean
	 */
	public boolean verifyBeneficiaryDetails(String attribute){
		
		
		boolean isSuccess = false;
		String beneficiaryName = Stock.GetParameterValue("FirstName")+" "+Stock.GetParameterValue("MiddleName")+" "+Stock.GetParameterValue("LastName");
		lib.Web.waitForElement(lblConfirmation);
		
		if(Web.isWebElementDisplayed(this.lblConfirmation)){
			
				if(attribute.equalsIgnoreCase("Confirmation number"))
					if(lstTblConfirmationDetails.get(0).getText().split(":")[1] != null)
						return isSuccess;
				if(attribute.equalsIgnoreCase("Marital status"))
					return Web.VerifyText("Marital Status: "+Stock.GetParameterValue("Marital Status"), lstTblConfirmationDetails.get(1).getText(), true);
			
			
			for(int i=0;i<lstTablePrimaryBeneficiary.size();i++){
				if(lstTablePrimaryBeneficiary.get(i).getText().contains(beneficiaryName)){
						
					if(attribute.equalsIgnoreCase("Name"))
						return Web.VerifyText("Name: "+beneficiaryName+", "+Stock.GetParameterValue("Prefix"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[0], true);
					
					if(attribute.equalsIgnoreCase("Allocation"))	
						return Web.VerifyText("Allocation: "+Stock.GetParameterValue("Allocation")+"%", lstTablePrimaryBeneficiary.get(i).getText().split("\n")[1], true);
					
					if(attribute.equalsIgnoreCase("Relationship"))	
						return Web.VerifyText("Relationship: "+Stock.GetParameterValue("Beneficiary Relation"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[2], true);
					
					if(attribute.equalsIgnoreCase("SSN")){
//						return Web.VerifyText("SSN (LAST FOUR): "+Stock.GetParameterValue("ssn").split("-")[2], lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
						return Web.VerifyText("SSN (LAST FOUR): "+Stock.GetParameterValue("ssn").substring(Stock.GetParameterValue("ssn").length()-4), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
					}
					if(attribute.equalsIgnoreCase("DOB")){
						if(Stock.GetParameterValue("Validate_Date").equalsIgnoreCase("Yes"))
							return Web.VerifyText("DATE OF BIRTH: "+Stock.GetParameterValue("Valid_Date"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[4], true);
						else
							return Web.VerifyText("DATE OF BIRTH: "+Stock.GetParameterValue("DOB"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[4], true);
					}
					if(attribute.equalsIgnoreCase("Phone Number"))	
						return Web.VerifyText("PHONE NUMBER: "+Stock.GetParameterValue("PhoneNumber"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[5], true);
					
					if(attribute.equalsIgnoreCase("Address")){
						String address= Stock.GetParameterValue("AddressOne")+" "+Stock.GetParameterValue("AddressTwo")+" "+Stock.GetParameterValue("City")+", "+Stock.GetParameterValue("Country")+", "+Stock.GetParameterValue("Zipcode").split("\\.")[0];
						return Web.VerifyText("Address: "+address, lstTablePrimaryBeneficiary.get(i).getText().split("\n")[6]+" "+lstTablePrimaryBeneficiary.get(i).getText().split("\n")[7]+" "+lstTablePrimaryBeneficiary.get(i).getText().split("\n")[8] ,true);
					}
				}
				
			}
		}
		
		return false;
	}
	
	public boolean verifyEntityDetails(String attribute){
		
		Web.waitForElement(this.lblConfirmation);
		
		
		for(int i=0;i<lstTablePrimaryBeneficiary.size();i++){
			if(lstTablePrimaryBeneficiary.get(i).getText().contains(Stock.GetParameterValue("BeneficiaryName"))){
				if(attribute.equalsIgnoreCase("Name"))
					return Web.VerifyText("Name: "+Stock.GetParameterValue("BeneficiaryName"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[0], true);
				
				if(attribute.equalsIgnoreCase("Allocation"))	
					return Web.VerifyText("Allocation: "+Stock.GetParameterValue("Allocation")+"%", lstTablePrimaryBeneficiary.get(i).getText().split("\n")[1], true);
				
				if(attribute.equalsIgnoreCase("Relationship"))	
					return Web.VerifyText("Type: "+Stock.GetParameterValue("Beneficiary Relation"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[2], true);
				
				if(attribute.equalsIgnoreCase("TIN")){
//					return Web.VerifyText("SSN (LAST FOUR): "+Stock.GetParameterValue("ssn").split("-")[2], lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
					return Web.VerifyText("TIN (last four): "+Stock.GetParameterValue("Tax_Identification_No").substring(Stock.GetParameterValue("Tax_Identification_No").length()-4), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[3], true);
				}
				if(attribute.equalsIgnoreCase("DOT")){	
					return Web.VerifyText("DATE OF TRUST: "+Stock.GetParameterValue("Date_of_Trust"), lstTablePrimaryBeneficiary.get(i).getText().split("\n")[4], true);
				}
			}
		}
		return false;
	}
	

	/**<pre> Method to enter the beneficiary details.
	 *.</pre>
	 */
	public void enterBeneficiaryDetails(){
		lib.Web.setTextToTextBox(txtFirstName,Stock.GetParameterValue("FirstName"));
		lib.Web.setTextToTextBox(txtMiddleName,Stock.GetParameterValue("MiddleName"));
		lib.Web.setTextToTextBox(txtLastName,Stock.GetParameterValue("LastName"));
		lib.Web.setTextToTextBox(txtSuffix,Stock.GetParameterValue("Prefix"));
		lib.Web.setTextToTextBox(txtDateOfBirth,Stock.GetParameterValue("DOB"));
		lib.Web.setTextToTextBox(txtSSN, Stock.GetParameterValue("SSN"));
		lib.Web.setTextToTextBox(txtPhoneNumber,Stock.GetParameterValue("PhoneNumber"));
	
	}
	

	/**<pre> Method to enter the address details of a beneficiary.
	 *.</pre>
	 */
	public void enterAddressDetails(){
		
		lib.Web.setTextToTextBox(txtAddressOne,Stock.GetParameterValue("AddressOne"));
		lib.Web.setTextToTextBox(txtAddressTwo,Stock.GetParameterValue("AddressTwo"));
		lib.Web.setTextToTextBox(txtCity,Stock.GetParameterValue("City"));
		Web.selectDropDownOption(this.selState,Stock.GetParameterValue("State"));
		lib.Web.setTextToTextBox(txtZipCode,Stock.GetParameterValue("Zipcode").split("\\.")[0]);
		Web.selectDropDownOption(this.selCountry,Stock.GetParameterValue("Country"));
	}
	
	public void enterEntityDetails(){
		lib.Web.setTextToTextBox(this.txtBeneficiaryName, Stock.GetParameterValue("BeneficiaryName"));
		lib.Web.setTextToTextBox(this.txtDateOfTrust, Stock.GetParameterValue("Date_of_Trust"));
		lib.Web.setTextToTextBox(this.txtTaxIdentificationNo, Stock.GetParameterValue("Tax_Identification_No"));
	}
	/**<pre> Method to delete beneficiaries from DB.
	 *.</pre>
	 * @param ssn - Partcipant ssn
	 * @param firstName - Participant First name
	 * 
	 * @return - boolean
	 * @throws Exception 
	 */
	public void deleteBeneficiariesFromDB(String ssn, String firstName) throws Exception{
		String[] sqlQuery;
		String[] sqlQuery_commit;
		sqlQuery = Stock.getTestQuery("deleteBeneficiaries");
		sqlQuery_commit = Stock.getTestQuery("deleteBeneficiaries_commit");
		
		DB.executeUpdate(sqlQuery[0], sqlQuery[1], ssn, firstName);
		DB.executeUpdate(sqlQuery_commit[0], sqlQuery_commit[1]);
		
	}
	
	public String readErrorMessage(String msgType){
		String error_msg="";
		WebElement msg = this.getWebElement(msgType);
		error_msg=msg.getText();
		System.out.println(error_msg);
		return error_msg;
	}
	
	public boolean ifElementDisabled(String elementName){
		boolean isElementDisabled=false;
		WebElement element = this.getWebElement(elementName);
		System.out.println(elementName);
//		if(!element.isEnabled())
//			isElementDisabled=true;
		
		System.out.println(element.getAttribute("disabled"));
		return isElementDisabled;
	}
	
	public String fetchMaritalStatusFromDB(String queryName, String ssn) throws Exception{
		String[] sqlQuery;
		sqlQuery = Stock.getTestQuery(queryName);
		ResultSet recSet=DB.executeQuery(sqlQuery[0], sqlQuery[1], ssn);
		

		if (DB.getRecordSetCount(recSet) > 0) {
			try {
				recSet.first();			
			} catch (SQLException e) {
				e.printStackTrace();
				Reporter.logEvent(Status.WARNING, "Query Participant Info from DB:" + sqlQuery[0] , "The Query did not return any results. Please check participant test data as the appropriate data base.", false);
			}
		}
		String marital_status=recSet.getString("MARITAL_STATUS");
		return marital_status;
	}
	
	
}
