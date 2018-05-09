package pageobject.GPKG;

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

public class GPKG_CHANGE_GROUP_COMPENSATION_INFORMATION {
	public String queryString;
	private String serviceURL = "http://"
			+ Stock.getConfigParam("TargetServiceHostServer").trim()
			+ ":8080/ServiceManager/Macro/ExecMacro/GPKG_CHANGE_GROUP_COMPENSATION_INFORMATION";
	private DocumentBuilderFactory docBuilderFactory;
	private DocumentBuilder docBuilder;
	private Document doc;
	public ResultSet queryResultSet = null;
	String futureDate=DateCompare.GenerateFutureDatePlus20Days();

	String CONTROL_SELECTION_0;
	String LOGON_DATABASE;
	String LOGON_PASSWORD;
	String LOGON_USERNAME;
	String GET_GA_GA_ID_0;
	String CFG_CONTROL_DFLT_BRK_TERMDATE_0;
	String DEFAULT_BROKER_LOV;
	String DEFAULT_BROKER_SHARE_PERCENT_0;
	String param270112;
	String param270112_X1;

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

	

	public void setCFG_CONTROL_DFLT_BRK_TERMDATE_0(
			String cFG_CONTROL_DFLT_BRK_TERMDATE_0) {
		CFG_CONTROL_DFLT_BRK_TERMDATE_0 = cFG_CONTROL_DFLT_BRK_TERMDATE_0;
	}

	public void setDEFAULT_BROKER_LOV(String dEFAULT_BROKER_LOV) {
		DEFAULT_BROKER_LOV = dEFAULT_BROKER_LOV;
	}

	public void setDEFAULT_BROKER_SHARE_PERCENT_0(
			String dEFAULT_BROKER_SHARE_PERCENT_0) {
		DEFAULT_BROKER_SHARE_PERCENT_0 = dEFAULT_BROKER_SHARE_PERCENT_0;
	}

	public void setParam270112(String param270112) {
		this.param270112 = param270112;
	}

	public void setParam270112_X1(String param270112_X1) {
		this.param270112_X1 = param270112_X1;
	}

	public void setServiceParameters() {
		this.setLOGON_DATABASE(Stock.GetParameterValue("LOGON_DATABASE"));
		this.setLOGON_PASSWORD(Stock.GetParameterValue("LOGON_PASSWORD"));
		this.setLOGON_USERNAME(Stock.GetParameterValue("LOGON_USERNAME"));
		this.setCONTROL_SELECTION_0(Stock
				.GetParameterValue("CONTROL_SELECTION_0"));
		this.setGET_GA_GA_ID_0(Stock.GetParameterValue("GET_GA_GA_ID_0"));
		this.setCFG_CONTROL_DFLT_BRK_TERMDATE_0(Stock.GetParameterValue("CFG_CONTROL_DFLT_BRK_TERMDATE_0"));
		this.setDEFAULT_BROKER_LOV(Stock.GetParameterValue("DEFAULT_BROKER_LOV"));
		this.setDEFAULT_BROKER_SHARE_PERCENT_0(Stock
				.GetParameterValue("DEFAULT_BROKER_SHARE_PERCENT_0"));
		this.setParam270112(Stock.GetParameterValue("param270112"));
		this.setParam270112_X1(Stock.GetParameterValue("param270112_X1"));

		queryString = "?LOGON_USERNAME="
				+ LOGON_USERNAME
				+ "&LOGON_PASSWORD="
				+ LOGON_PASSWORD
				+ "&LOGON_DATABASE="
				+ LOGON_DATABASE
				+ "&CONTROL_SELECTION_0="
				+ CONTROL_SELECTION_0
				+ "&GET_GA_GA_ID_0="
				+ GET_GA_GA_ID_0
				+ "&CFG_CONTROL_DFLT_BRK_TERMDATE_0="
				+ CFG_CONTROL_DFLT_BRK_TERMDATE_0
				+ "&DEFAULT_BROKER_LOV="
				+ DEFAULT_BROKER_LOV
				+ "&DEFAULT_BROKER_SHARE_PERCENT_0="
				+ DEFAULT_BROKER_SHARE_PERCENT_0
				+ "&param270112="
				+ param270112
				+ "&param270112_X1="
				+ param270112_X1
				+ "&numOfRowsInTable=20&json=false&handlePopups=false&useLabelsAsKeys=false";

		Reporter.logEvent(Status.INFO, "Prepare test data for GPKG service",
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
			Reporter.logEvent(Status.PASS, "Run GPKG service",
					"Execution Done!", false);
		} catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "Run GPKG service",
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
					"GRP_COMP_PKG_COMP_PKG_ID_0: "
							+ doc.getElementsByTagName(
									"GRP_COMP_PKG_COMP_PKG_ID_0").item(0)
									.getTextContent()
							+ "\nGET_GA_PLAN_NAME_0: "
							+ doc.getElementsByTagName("GET_GA_PLAN_NAME_0")
									.item(0).getTextContent()
							+ "\nGET_GA_PROD_ID_0: "
							+ doc.getElementsByTagName("GET_GA_PROD_ID_0")
									.item(0).getTextContent()
							+ "\nSTEP_TYPE_DESCR_1: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(1).getTextContent()
							+ "\nSTEP_TYPE_DESCR_2: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(2).getTextContent()
							+ "\nSTEP_TYPE_DESCR_3: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(3).getTextContent()
							+ "\nSTEP_TYPE_DESCR_4: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(4).getTextContent()
							+ "\nSTEP_TYPE_DESCR_5: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(5).getTextContent()
							+ "\nSTEP_TYPE_DESCR_6: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(6).getTextContent()
							+ "\nSTEP_TYPE_DESCR_7: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(7).getTextContent()
							+ "\nSTEP_TYPE_DESCR_8: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(8).getTextContent()
							+ "\nSTEP_TYPE_DESCR_9: "
							+ doc.getElementsByTagName("STEP_TYPE_DESCR_0")
									.item(9).getTextContent(), false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate response - Error is empty",
					"Error tag is not empty\nActual value:\n" + Error, false);
			System.out.println(Error);

		}
	}

	public void validateInDatabase() throws SQLException {

		
		System.out.println("futureDate: "+futureDate);
		queryResultSet = DB.executeQuery(General.dbInstance,
				Stock.getTestQuery("queryForGPKGUpdate")[1], GET_GA_GA_ID_0,futureDate);
		if (DB.getRecordSetCount(queryResultSet) > 0) {
			Reporter.logEvent(Status.INFO,
					"Validating existence of data in the Database",
					"User is able to query a Groups's compensation package information via QYGR/CHGR",
					false);

		} else {
			Reporter.logEvent(Status.FAIL,
					"Validating existence of data in the Database",
					"Record doesn't exists in the Database", false);
		}
		}
}
