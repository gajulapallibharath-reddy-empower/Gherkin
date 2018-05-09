package pageobject.HOWR;

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

public class HOWR_Query_Standard_Rule {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GQ19HOWR_Query_Standard_Rule";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;
	public ResultSet queryResultSet2 = null;
	public ResultSet queryResultSet3 = null;
	public ResultSet queryResultSet4 = null;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String STD_RL_ID_0;

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

	

	public void setSTD_RL_ID_0(String sTD_RL_ID_0) {
		STD_RL_ID_0 = sTD_RL_ID_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setSTD_RL_ID_0(Stock.GetParameterValue("STD_RL_ID_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&STD_RL_ID_0="
				+ STD_RL_ID_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for HOWL service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: "+serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run HOWL service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run HOWL service",
					"Running Failed:", false);
		}
	}

	public void validateOutput() throws SQLException {
		String Error = doc.getElementsByTagName("Error").item(0)
				.getTextContent();

		if (Error.isEmpty()) {
			Reporter.logEvent(Status.PASS,
					"Validate response - Error is empty", "Error tag is empty",
					false);
			Reporter.logEvent(
					Status.INFO,
					"Values from Response: ",
					"CONTROL_MENU_TITLE_0: "
							+ doc.getElementsByTagName(
									"CONTROL_MENU_TITLE_0").item(0)
									.getTextContent()
							, false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {

		
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForHOWR1")[1], STD_RL_ID_0);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database",
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}
		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForHOWR2")[1], STD_RL_ID_0);
		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database",
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}
		
	queryResultSet2 = DB.executeQuery(General.dbInstance,
			Stock.getTestQuery("queryForHOWR3")[1], STD_RL_ID_0);
	if (DB.getRecordSetCount(queryResultSet2) > 0) {
		Reporter.logEvent(Status.PASS,
				"Validating existence of data in the Database",
				"Record exists in the Database",
				false);

	} else {
		Reporter.logEvent(Status.FAIL,
				"Validating existence of data in the Database",
				"Record doesn't exists in the Database", false);
	}
	
	queryResultSet3 = DB.executeQuery(General.dbInstance,
			Stock.getTestQuery("queryForHOWR4")[1], STD_RL_ID_0);
	if (DB.getRecordSetCount(queryResultSet3) > 0) {
		Reporter.logEvent(Status.PASS,
				"Validating existence of data in the Database",
				"Record exists in the Database",
				false);

	} else {
		Reporter.logEvent(Status.FAIL,
				"Validating existence of data in the Database",
				"Record doesn't exists in the Database", false);
	}
	
	queryResultSet4 = DB.executeQuery(General.dbInstance,
			Stock.getTestQuery("queryForHOWR5")[1]);
	if (DB.getRecordSetCount(queryResultSet4) > 0) {
		Reporter.logEvent(Status.PASS,
				"Validating existence of data in the Database",
				"Record exists in the Database",
				false);

	} else {
		Reporter.logEvent(Status.FAIL,
				"Validating existence of data in the Database",
				"Record doesn't exists in the Database", false);
	}
	}
}
