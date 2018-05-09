package pageobject.SIFS;

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

public class SIFS_Add_Redemption_Fee_Rule {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/SIFS_Add_Redemption_Fee_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STD_RULE_LOV;
	String STD_REDEMPTION_FEE_RULE_EFFDATE_0;
	String TXN_TYPE_LOV;
	
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
	public void setSTD_RULE_LOV(String sTD_RULE_LOV) {
		STD_RULE_LOV = sTD_RULE_LOV;
	}
	public void setSTD_REDEMPTION_FEE_RULE_EFFDATE_0(
			String sTD_REDEMPTION_FEE_RULE_EFFDATE_0) {
		STD_REDEMPTION_FEE_RULE_EFFDATE_0 = sTD_REDEMPTION_FEE_RULE_EFFDATE_0;
	}
	public void setTXN_TYPE_LOV(String tXN_TYPE_LOV) {
		TXN_TYPE_LOV = tXN_TYPE_LOV;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSTD_RULE_LOV(Stock.GetParameterValue("STD_RULE_LOV"));
		this.setTXN_TYPE_LOV(Stock.GetParameterValue("TXN_TYPE_LOV"));
		this.setSTD_REDEMPTION_FEE_RULE_EFFDATE_0(Stock.GetParameterValue("STD_REDEMPTION_FEE_RULE_EFFDATE_0"));
		
		queryString = "?CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&LOGON_DATABASE="+LOGON_DATABASE+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_USERNAME="+LOGON_USERNAME+
				"&STD_REDEMPTION_FEE_RULE_EFFDATE_0="+STD_REDEMPTION_FEE_RULE_EFFDATE_0+"&STD_RULE_LOV="+STD_RULE_LOV+"&TXN_TYPE_LOV="+TXN_TYPE_LOV+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for SIFS service", this.queryString.replaceAll("&", "\n"), false);
		
	}
	
	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println(serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run SIFS service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run SIFS service", "Running Failed:", false);
		}
	}
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0: "+doc.getElementsByTagName("STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0").item(0).getTextContent()+
					"\nSTD_REDEMPTION_FEE_RULE_TXN_TYPE_0: "+doc.getElementsByTagName("STD_REDEMPTION_FEE_RULE_TXN_TYPE_0").item(0).getTextContent(), false);
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	}
	
	public void validateInDatabase() throws SQLException{
		String sdio_id = doc.getElementsByTagName("STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0").item(0).getTextContent();
		String rule_id = null;
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFSAdd1")[1]);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: STD_RULE", false);
			Reporter.logEvent(Status.PASS, "Values from DB", "Total no. of records in table: "+DB.getRecordSetCount(queryResultSet), false);			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFSAdd2")[1],  sdio_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: STD_INVOPT_FEE_SCHEDULE", false);
			while(queryResultSet.next()){
				rule_id = queryResultSet.getString("STD_RL_ID");
				Reporter.logEvent(Status.INFO, "Values from Database", "SDIO_ID: "+queryResultSet.getString("SDIO_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nSTD_RL_ID: "+queryResultSet.getString("STD_RL_ID"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFSAdd3")[1],  rule_id, this.STD_REDEMPTION_FEE_RULE_EFFDATE_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Checking for Database records", "Records inserted in Database\nTable Name: STD_REDEMPTION_FEE_RULE", false);
			while(queryResultSet.next()){
				Reporter.logEvent(Status.INFO, "Values from Database", "STD_RULE_ID: "+queryResultSet.getString("STD_RL_ID")+
						"\nEFFDATE: "+queryResultSet.getString("EFFDATE")+
						"\nTERMDATE: "+queryResultSet.getString("TERMDATE")+
						"\nTXN_TYPE: "+queryResultSet.getString("TXN_TYPE"), false);
			}
			
		}else{
			Reporter.logEvent(Status.FAIL, "Checking if Database records are present or not", "No records exists in Database", false);
		}
	}
	
	public void FlushData(){
		String sdio_id = doc.getElementsByTagName("STD_INVOPT_FEE_SCHEDULE_SDIO_ID_0").item(0).getTextContent();
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForSIFSAdd2Del")[1], sdio_id);
		
	}
}
