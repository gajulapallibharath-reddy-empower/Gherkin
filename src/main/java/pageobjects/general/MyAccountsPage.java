package pageobjects.general;

import java.sql.ResultSet;
import java.sql.SQLException;
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

import appUtils.Common;
import pageobjects.landingpage.LandingPage;

public class MyAccountsPage extends LoadableComponent<MyAccountsPage> {

	//Declarations
	private LoadableComponent<?> parent;
	//private static boolean waitforLoad = false;
	
//	@FindBy(xpath=".//h1[text()='My Accounts']") private WebElement hdrMyAccounts;
	@FindBy(xpath=".//h1[text()='Account Overview']") private WebElement hdrMyAccounts;
	@FindBy(xpath=".//*[@class='plan']/*[starts-with(@id,'ga_')]") private List<WebElement> lstLnkPlanName;
	@FindBy(xpath=".//*[@id='utility-nav']/.//a[@id='userProfileName']") private WebElement lblUserName;
	@FindBy(linkText="Log out") private WebElement lnkLogout;
	
	/** Empty args constructor
	 * 
	 */
	public MyAccountsPage() {
		this.parent = new LandingPage();
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	/** Constructor taking parent as arg
	 * 
	 * @param parent
	 */
	public MyAccountsPage(LoadableComponent<?> parent) {
		this.parent = parent;
		PageFactory.initElements(lib.Web.webdriver, this);
	}
	
	@Override
	protected void isLoaded() throws Error {
		Assert.assertTrue(Web.isWebElementDisplayed(this.lblUserName));
		String ssn = Stock.GetParameterValue("userName");
		ResultSet strUserInfo = Common.getParticipantInfoFromDB(ssn.substring(0, ssn.length()-3));
		
		String userFromDatasheet = null;
		try {
			userFromDatasheet = strUserInfo.getString("FIRST_NAME")+ " " + strUserInfo.getString("LAST_NAME");
		} catch (SQLException e) {
			e.printStackTrace();
		}		
		
		String userLogedIn = this.lblUserName.getText();
		if (userFromDatasheet.equalsIgnoreCase(userLogedIn)) {
			Assert.assertTrue(userFromDatasheet.equalsIgnoreCase(userLogedIn));		
			Assert.assertTrue(Web.isWebElementDisplayed(hdrMyAccounts));
		} else {
			this.lnkLogout.click();
		}
	}

	@Override
	protected void load() {
		LandingPage land = (LandingPage) this.parent;
		
		this.parent.get();
		((LandingPage) this.parent).dismissPopUps(true,true);
		Web.clickOnElement(land, "MY ACCOUNTS");
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/** <pre> Method to return WebElement object corresponding to specified field name
	 * Elements available for fields:
	 * 	
	 * </pre>
	 * @param fieldName
	 * @return
	 */
	@SuppressWarnings("unused")
	private WebElement getWebElement(String fieldName) {
		
		Reporter.logEvent(Status.WARNING, "Get WebElement for field '" + fieldName + "'", 
				"No WebElement mapped for this field\nPage: <b>" + this.getClass().getName() + "</b>", false);
		
		return null;
	}
	
	
	/** method to click on Plan name link which corresponds to the specified group account ID
	 * 
	 * @param groupAccountID - Example: 95301-01
	 * @return boolean - <b>true</b> if successful in clicking specified link. <b>false</b> otherwise.
	 */
	public boolean clickPlanNameByGAID(String... groupAccountID) {
		boolean success = false;
		int planCount = this.lstLnkPlanName.size();
		String actualGAID = "";
		WebElement currElement;
		
		if (groupAccountID.length==0) {
			currElement = this.lstLnkPlanName.get(0);
			currElement.click();
			success = true;
		} else {
			for (int iCntr = 0; iCntr < planCount; iCntr++) {
				currElement = this.lstLnkPlanName.get(iCntr);
				actualGAID = currElement.getAttribute("id");
				
				if (actualGAID.trim().equals("ga_" + groupAccountID[0].trim())) {
					currElement.click();
					success = true;
					break;
				}
			}
		}
				
		return success;
	}

}