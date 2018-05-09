package pageobject.MMOD;

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

public class MMOD_Edit_Module {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/MMOD_Edit_Module";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;	
	ResultSet queryResultSet;
	
	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String MO1_SHORT_NAME_0;
	String MO1_LANGUAGE_0;
	String MO1_LANGUAGE_0_X1;
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
	public void setMO1_SHORT_NAME_0(String mO1_SHORT_NAME_0) {
		MO1_SHORT_NAME_0 = mO1_SHORT_NAME_0;
	}
	public void setMO1_LANGUAGE_0(String mO1_LANGUAGE_0) {
		MO1_LANGUAGE_0 = mO1_LANGUAGE_0;
	}
	public void setMO1_LANGUAGE_0_X1(String mO1_TYPE_CODE_0) {
		MO1_LANGUAGE_0_X1 = mO1_TYPE_CODE_0;
	}
	
	public void setServiceParameters()
	{
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setMO1_LANGUAGE_0_X1(Stock.GetParameterValue("MO1_LANGUAGE_0_X1"));
		this.setMO1_SHORT_NAME_0(Stock.GetParameterValue("MO1_SHORT_NAME_0"));
		this.setMO1_LANGUAGE_0(Stock.GetParameterValue("MO1_LANGUAGE_0"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&MO1_SHORT_NAME_0="+MO1_SHORT_NAME_0+"&MO1_LANGUAGE_0="+MO1_LANGUAGE_0+"&MO1_LANGUAGE_0_X1="+MO1_LANGUAGE_0_X1+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for MMOD service", this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run MMOD service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run MMOD service", "Running Failed:", false);
		}
	}
	
	public void validateOutput()
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);		
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
			
	}
	
	public void validateInDatabase() throws SQLException{
	
		queryResultSet = DB.executeQuery(General.dbInstance, Stock.getTestQuery("queryForMMODEdit")[1], this.MO1_SHORT_NAME_0);
		if(DB.getRecordSetCount(queryResultSet)>0){
			Reporter.logEvent(Status.PASS, "Validating record in DB", "Records edited in DB\nTableName: MODULE", false);
			while(queryResultSet.next()){
				
				Reporter.logEvent(Status.INFO, "Values from Database", "SHORT_NAME: "+queryResultSet.getString("SHORT_NAME")+
						"\nLANGUAGE: "+queryResultSet.getString("LANGUAGE")+
						"\nTYPE_CODE: "+queryResultSet.getString("TYPE_CODE"), false);
			}			
		}else{
			Reporter.logEvent(Status.FAIL, "Validating record in DB", "Records not edited DB\nTableName: MODULE", false);
		}
		
	}
}
