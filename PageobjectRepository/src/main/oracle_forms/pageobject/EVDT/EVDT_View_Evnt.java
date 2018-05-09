package pageobject.EVDT;

import generallib.General;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import com.aventstack.extentreports.*;

import org.w3c.dom.Document;

import core.framework.Globals;

public class EVDT_View_Evnt {
	
	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/EVDT_VIEW_Event3";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;  
	String LOGON_DATABASE; 
	String LOGON_PASSWORD; 
	String LOGON_USERNAME;
	String EV1_ID_0;
	
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
	public void setEV1_ID_0(String eV1_ID_0) {
		EV1_ID_0 = eV1_ID_0;
	}
	
	public void setServiceParameters() throws SQLException
	{
		 this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		 this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		 this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		 this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		 this.setEV1_ID_0(Stock.GetParameterValue("EV1_ID_0"));
				 
		 queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+
				 "&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+"&EV1_ID_0="+EV1_ID_0+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		 
		 Reporter.logEvent(Status.INFO, "Prepare test data for EVDT_VIEW service", this.queryString.replaceAll("&", "\n"), false);
		 
	}
	
	public void runService() throws SQLException {
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
				Reporter.logEvent(Status.PASS, "Run  EVDT_View_Event service", "Execution Done!", false);
			} catch (Exception e) {
				e.printStackTrace();
				Globals.exception = e;
				Reporter.logEvent(Status.FAIL, "Run EVDT_View_Event service", "Running Failed:", false);
			}
			
	}
	
public void validateOutput() throws SQLException{
		
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
		} else {
		//	Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
	
		Reporter.logEvent(Status.INFO, "Values From response: ","EV1_DISP_SUBJECT_ID_0: "+doc.getElementsByTagName("EV1_DISP_SUBJECT_ID_0").item(0).getTextContent()+
				"\nEV1_EVTY_CODE_0: "+doc.getElementsByTagName("EV1_EVTY_CODE_0").item(0).getTextContent()+
		"\n",false);
	}
	
	public void validateInDatabase() throws SQLException{
		String event_id = doc.getElementsByTagName("EV1_EVTY_CODE_0").item(0).getTextContent();
		String ev_id_db=null;
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForEditEvnt")[1], this.EV1_ID_0);
		
		if(queryResultSet.next()){
			Reporter.logEvent(Status.PASS, "Validating Records exists in Database", "Records exists in Database\nTable Name: EVENT", false);
			Reporter.logEvent(Status.INFO, "Values From database", "ID: "+ queryResultSet.getString("ID")+
					"\nEVTY CODE: "+queryResultSet.getString("EVTY_CODE"), false);
			ev_id_db = queryResultSet.getString("EVTY_CODE");
		}
		else{
			Reporter.logEvent(Status.FAIL, "Validating Record existence in Database", "No records exists in Database", false);
		}
		if(event_id.equalsIgnoreCase(ev_id_db)){
			Reporter.logEvent(Status.PASS, "Comparing and Validating Event ID from Response and Database", "Both the values are same as Expected"+
					"\nEvent ID: "+"From Response: "+event_id+"\nFrom Database: "+ev_id_db, false);
		}
  
	}

}
