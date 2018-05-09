package pageobject.VWPR;

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

public class GQ19VWPR_QYVWPR_Query_Comp_Packages {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GQ19VWPR_QYVWPR_Query_Comp_Packages";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	private ResultSet queryResultSet;
	

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String PRODUCER_TYPE_CODE_0;
	String PRODUCER_ACCT_NBR_0;
	String PRODUCER_EFFDATE_0;


 
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
	public void setPRODUCER_TYPE_CODE_0(String pRODUCER_TYPE_CODE_0){
		PRODUCER_TYPE_CODE_0 = pRODUCER_TYPE_CODE_0;
	}
	public void setPRODUCER_ACCT_NBR_0(String pRODUCER_ACCT_NBR_0){
		PRODUCER_ACCT_NBR_0 = pRODUCER_ACCT_NBR_0;
	}
	public void setPRODUCER_EFFDATE_0(String pRODUCER_EFFDATE_0){
		PRODUCER_EFFDATE_0 = pRODUCER_EFFDATE_0;
	}
	

	
	
	
	public void setServiceParameters() {
		this.setCONTROL_SELECTION_0(Stock.GetParameterValue("CONTROL_SELECTION_0"));
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setPRODUCER_TYPE_CODE_0(Stock.GetParameterValue("PRODUCER_TYPE_CODE_0"));
		this.setPRODUCER_ACCT_NBR_0(Stock.GetParameterValue("PRODUCER_ACCT_NBR_0"));
		this.setPRODUCER_EFFDATE_0(Stock.GetParameterValue("PRODUCER_EFFDATE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME 
				+"&LOGON_PASSWORD="
				+LOGON_PASSWORD
				+"&LOGON_DATABASE="
				+LOGON_DATABASE
				+"&CONTROL_SELECTION_0="
				+CONTROL_SELECTION_0
				+"&PRODUCER_TYPE_CODE_0="
				+PRODUCER_TYPE_CODE_0
				+"&PRODUCER_ACCT_NBR_0="
				+PRODUCER_ACCT_NBR_0
				+"&PRODUCER_EFFDATE_0="
				+PRODUCER_EFFDATE_0
				+"&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for VWPR service",
				this.queryString.replaceAll("&", "\n"), false);
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
			Reporter.logEvent(Status.PASS, "Run VWPR service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run VWPR service",
					"Running Failed:", false);
		}
	}

	public void validateOutput() {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();

		if (Error.isEmpty()) {
			Reporter.logEvent(Status.PASS,
					"Validate response - Error is empty", "Error tag is empty",
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);
		}

	}

	public void validateInDatabase() throws SQLException {

		
		
		queryResultSet = DB.executeQuery(General.dbInstance,Stock.getTestQuery("queryForVWPR1")[1]);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating Records exists in Database",
					"Records exists in Database", false);
			System.out.println("The count of the records fetched from the are:"+DB.getRecordSetCount(queryResultSet));
			Reporter.logEvent(Status.INFO,"Validating the number of rows fetched.","Number of rows fetched is:"+DB.getRecordSetCount(queryResultSet), false);

		}
				else {
					Reporter.logEvent(
							Status.FAIL,
							"Validating Records exists in Database",
							"Records does not exists in Database",
							false);
				

		}
		
		

	
	}


}