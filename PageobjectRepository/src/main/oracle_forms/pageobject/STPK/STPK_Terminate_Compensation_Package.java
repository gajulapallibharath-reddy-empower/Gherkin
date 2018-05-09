/**
 * 
 */
package pageobject.STPK;

import generallib.DateCompare;
import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

/**
 * @author smykjn
 *
 */
public class STPK_Terminate_Compensation_Package {
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STPK_Terminate_Compensation_Package";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	
	String termDate;
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String COMP_PKG_ID_0;
	String COMP_PKG_TERMDATE_0;
	String COMP_PKG_TERMDATE_0_X1;
	
	public void setCONTROL_SELECTION_0(String cONTROL_SELECTION_0) {
		CONTROL_SELECTION_0 = cONTROL_SELECTION_0;
	}
	public void setLOGON_DATABASE(String lOGON_DATABASE) {
		LOGON_DATABASE = lOGON_DATABASE;
	}
	public void setLOGON_PASSWORD(String lOGON_PASSWORD) {
		LOGON_PASSWORD = lOGON_PASSWORD;
	}
	public void setLOGON_USERNAME(String lOGON_USERNAME) {
		LOGON_USERNAME = lOGON_USERNAME;
	}
	public void setCOMP_PKG_ID_0(String cOMP_PKG_ID_0) {
		COMP_PKG_ID_0 = cOMP_PKG_ID_0;
	}
	public void setCOMP_PKG_TERMDATE_0(String cOMP_PKG_TERMDATE_0) {
		COMP_PKG_TERMDATE_0 = cOMP_PKG_TERMDATE_0;
	}
	public void setCOMP_PKG_TERMDATE_0_X1(String cOMP_PKG_TERMDATE_0_X1) {
		COMP_PKG_TERMDATE_0_X1 = cOMP_PKG_TERMDATE_0_X1;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCOMP_PKG_ID_0(Stock.GetParameterValue("COMP_PKG_ID_0"));
		this.setCOMP_PKG_TERMDATE_0(Stock.GetParameterValue("COMP_PKG_TERMDATE_0"));
		this.setCOMP_PKG_TERMDATE_0_X1(DateCompare.GenerateFutureDate(1));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&COMP_PKG_ID_0="+COMP_PKG_ID_0+"&COMP_PKG_TERMDATE_0="+COMP_PKG_TERMDATE_0+"&COMP_PKG_TERMDATE_0_X1="+COMP_PKG_TERMDATE_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STPK_Terminate_Compensation_Package service", this.queryString.replaceAll("&", "\n"), false);
		
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run STPK_Terminate_Compensation_Package service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STPK_Terminate_Compensation_Package service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();
		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		}
		else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("compPkg")[1], this.COMP_PKG_ID_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating Records exists in DB.\nTable :comp_pkg", "Record exists in DB.", false);
			while(queryResultSet.next()){
				 termDate = queryResultSet.getString("TERMDATE");
				String id = queryResultSet.getString("ID");
				Reporter.logEvent(Status.INFO,"From DB:","TermDate:"+termDate+"\nID:"+id, false);
			}
			
			Reporter.logEvent(Status.PASS,"Validate termdate is updated in DB.","Term date is updated.\n"
						+ "Expected termdate:"+COMP_PKG_TERMDATE_0_X1+"\nActual termdate:"+termDate, false);
			
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		
	}
	
	public void flushData(){
//		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("UpdatecompPkg")[1], this.COMP_PKG_ID_0);
	//	Reporter.logEvent(Status.INFO,"Reset the data.","Data has been reset.\nQuery:"+Stock.getTestQuery("UpdatecompPkg")[1], false);
	}
	
	
}
