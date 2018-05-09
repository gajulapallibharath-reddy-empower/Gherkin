package pageobject.CNPA;

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

public class CNPA_Conversion_Prior_Activity_info_through_CNCN {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/CNPA_Conversion_Prior_Activity_info_through_CNCN";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	public ResultSet queryResultSet1 = null;
	String EVID_DB;

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0;
	String param9171;
	String MO1_DISP_DFT_PRIN_CODE_0;

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

	public void setCONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0(
			String cONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0) {
		CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0 = cONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0;
	}

	public void setParam9171(String param9171) {
		this.param9171 = param9171;
	}

	public void setMO1_DISP_DFT_PRIN_CODE_0(String mO1_DISP_DFT_PRIN_CODE_0) {
		MO1_DISP_DFT_PRIN_CODE_0 = mO1_DISP_DFT_PRIN_CODE_0;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setCONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0(Stock
				.GetParameterValue("CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0"));
		this.setParam9171(Stock.GetParameterValue("param9171"));
		this.setMO1_DISP_DFT_PRIN_CODE_0(Stock
				.GetParameterValue("MO1_DISP_DFT_PRIN_CODE_0"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0="
				+ CONVERSION_GROUP_ACCOUNT_SUBJECT_ID_0
				+ "&param9171="
				+ param9171
				+ "&MO1_DISP_DFT_PRIN_CODE_0="
				+ MO1_DISP_DFT_PRIN_CODE_0
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for CNPA service",
				this.queryString.replaceAll("&", "\n"), false);
	}

	public void runService() {
		try {
			this.queryString = URLDecoder.decode(this.queryString, "UTF-8");
			serviceURL += this.queryString;
			System.out.println("serviceURL: " + serviceURL);
			docBuilderFactory = DocumentBuilderFactory.newInstance();
			docBuilderFactory.setIgnoringComments(true);
			docBuilderFactory.setIgnoringElementContentWhitespace(true);
			docBuilderFactory.setNamespaceAware(true);
			docBuilder = docBuilderFactory.newDocumentBuilder();
			doc = docBuilder.parse(serviceURL);
			// System.out.println(serviceURL);
			doc.getDocumentElement().normalize();
			Reporter.logEvent(Status.PASS, "Run CNPA service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run CNPA service",
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
			Reporter.logEvent(Status.INFO,
					"Fetching Auraplayer Response Values: ",
					"Auraplayer response values: "
							+ "\nWORK_CONV_PART1_SSN_0: "
							+ doc.getElementsByTagName("WORK_CONV_PART1_SSN_0")
									.item(0).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {

		String ssn = doc.getElementsByTagName("WORK_CONV_PART1_SSN_0").item(0)
				.getTextContent();

		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForCNSV1")[1], ssn);

		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"Record exists in the Database", false);

			while (queryResultSet.next()) {
				EVID_DB = queryResultSet.getString("EV_ID");

				Reporter.logEvent(Status.INFO,
						"Fetching data from the Database: ",
						"Data from the Database: " + "\nEVID_DB: " + EVID_DB,
						false);
			}

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

		queryResultSet1 = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForCNSV2")[1], EVID_DB);

		if (DB.getRecordSetCount(queryResultSet1) > 0) {
			Reporter.logEvent(Status.PASS,
					"Validating existence of data in the Database",
					"Record exists in the Database", false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}

	}
}
