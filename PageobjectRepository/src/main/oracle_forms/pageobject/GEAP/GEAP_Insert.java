package pageobject.GEAP;

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

public class GEAP_Insert {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/GEAP_INSERT";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String GET_GA_GA_ID_0_X1;
	String EAPACTY1_RSN_CODE_LOV2;
	String EAPACTY1_AMOUNT_0;
	
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
	public void setGET_GA_GA_ID_0(String gET_GA_GA_ID_0) {
		GET_GA_GA_ID_0 = gET_GA_GA_ID_0;
	}
	public void setGET_GA_GA_ID_0_X1(String gET_GA_GA_ID_0_X1) {
		GET_GA_GA_ID_0_X1 = gET_GA_GA_ID_0_X1;
	}
	public void setEAPACTY1_RSN_CODE_LOV2(String eAPACTY1_RSN_CODE_LOV2) {
		EAPACTY1_RSN_CODE_LOV2 = eAPACTY1_RSN_CODE_LOV2;
	}
	public void setEAPACTY1_AMOUNT_0(String eAPACTY1_AMOUNT_0) {
		EAPACTY1_AMOUNT_0 = eAPACTY1_AMOUNT_0;
	}
	
	public void setServiceParameters()	throws SQLException
	{	
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setEAPACTY1_AMOUNT_0(Stock.GetParameterValue("EAPACTY1_AMOUNT_0"));
		this.setEAPACTY1_RSN_CODE_LOV2(Stock.GetParameterValue("EAPACTY1_RSN_CODE_LOV2"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setGET_GA_GA_ID_0_X1(Stock.GetParameterValue("GET_GA_GA_ID_0_X1"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&GET_GA_GA_ID_0="+GET_GA_GA_ID_0+"&GET_GA_GA_ID_0_X1="+GET_GA_GA_ID_0_X1+
				"&EAPACTY1_RSN_CODE_LOV2="+EAPACTY1_RSN_CODE_LOV2+"&EAPACTY1_AMOUNT_0="+EAPACTY1_AMOUNT_0+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for GEAP service", this.queryString.replaceAll("&", "\n"), false);		
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
//			System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run GEAP service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GEAP service", "Running Failed:", false);
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
		String ev_id = null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGEAP1")[1], this.GET_GA_GA_ID_0_X1);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of Records in Database","Record exists in Database", false);
				while(queryResultSet.next()){
					ev_id = queryResultSet.getString("EV_ID");
			
					Reporter.logEvent(Status.INFO, "Values From Database","Table Name: EAP_ACTY\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nAMOUNT: "+queryResultSet.getString("AMOUNT")+
					"\nRSN_CODE: "+queryResultSet.getString("RSN_CODE"),false); 
			}	
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of Records in Database","No Record exists ", false);
		}
		
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForGEAP2")[1], ev_id);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating existence of Records in Database","Record exists in Database", false);
				while(queryResultSet.next()){
					Reporter.logEvent(Status.INFO, "Values From Database","Table Name: STEP\nEV_ID: "+queryResultSet.getString("EV_ID")+
					"\nSETY_CODE: "+queryResultSet.getString("SETY_CODE")+
					"\nEVTY_CODE: "+queryResultSet.getString("EVTY_CODE"),false); 
			}	
		}else{
			Reporter.logEvent(Status.FAIL, "Validating existence of Records in Database","No Record exists ", false);
		}
	}
}
