package pageobject.STIO;

import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import lib.Reporter;
import lib.Stock;

import org.w3c.dom.Document;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class STIO_Fund_Array {

	public String queryString;
	private String serviceURL =  "http://" + Stock.getConfigParam("TargetServiceHostServer").trim() + ":8080/ServiceManager/Macro/ExecMacro/STIO_Fund_Array";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STD_INVOPT_ID_0;
	String STD_INVOPT_ID_0_X1;
	String STD_INVOPT_ID_0_X2;
	
	public void setSTD_INVOPT_ID_0_X1(String sTD_INVOPT_ID_0_X1) {
		STD_INVOPT_ID_0_X1 = sTD_INVOPT_ID_0_X1;
	}
	public void setSTD_INVOPT_ID_0_X2(String sTD_INVOPT_ID_0_X2) {
		STD_INVOPT_ID_0_X2 = sTD_INVOPT_ID_0_X2;
	}
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
	public void setSTD_INVOPT_ID_0(String sTD_INVOPT_ID_0) {
		STD_INVOPT_ID_0 = sTD_INVOPT_ID_0;
	}
	
	public void setServiceParameters()	
	{
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSTD_INVOPT_ID_0(Stock.GetParameterValue("STD_INVOPT_ID_0"));
		this.setSTD_INVOPT_ID_0_X1(Stock.GetParameterValue("STD_INVOPT_ID_0_X1"));
		this.setSTD_INVOPT_ID_0_X2(Stock.GetParameterValue("STD_INVOPT_ID_0_X2"));
		
		queryString = "?LOGON_USERNAME="+LOGON_USERNAME+"&LOGON_PASSWORD="+LOGON_PASSWORD+"&LOGON_DATABASE="+LOGON_DATABASE+"&CONTROL_SELECTION_0="+CONTROL_SELECTION_0+
				"&STD_INVOPT_ID_0="+STD_INVOPT_ID_0+"&STD_INVOPT_ID_0_X1="+STD_INVOPT_ID_0_X1+"&STD_INVOPT_ID_0_X2="+STD_INVOPT_ID_0_X2+
				"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";
		
		Reporter.logEvent(Status.INFO, "Prepare test data for STIO service", this.queryString.replaceAll("&", "\n"), false);
		
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
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run STIO service", "Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run STIO service", "Running Failed:", false);
		}
	}
	
	
	public void validateOutput() throws SQLException
	{
		String Error = doc.getElementsByTagName("Error").item(0).getTextContent();

		if (Error.isEmpty()){
			Reporter.logEvent(Status.PASS, "Validate response - Error is empty", "Error tag is empty", false);
			Reporter.logEvent(Status.INFO, "From Response", "FUND ARRAY NAME: "+doc.getElementsByTagName("ASSOC_FUND_WITH_FUND_ARRAY_NAME_9").item(0).getTextContent()+
					"\nLEGAl NAME: "+doc.getElementsByTagName("STD_INVOPT_EIVR_LONG_LEGAL_NAME_0").item(0).getTextContent()+
					"\nSTART DATE: "+doc.getElementsByTagName("INPUT_LINE_START_STATUS_START_DATE_0").item(0).getTextContent()
					, false);
			
		} else {
			Reporter.logEvent(Status.FAIL, "Validate response - Error is empty", "Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}
		
	}
}
