package com.supportlibraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;

import org.apache.log4j.Logger;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class ReporterListener extends TestListenerAdapter implements ITestListener, ISuiteListener {
	private static Logger logger = Logger.getLogger(ReporterListener.class.getName());
	private  int failCount = 0;
	private  int passCount = 0;
	private  int skipCount = 0;
	
	private  long executionTime;
	private static  LinkedHashMap<String,LinkedHashMap<String,String>> detailedInnerTable;
	private  LinkedHashMap<String,LinkedHashMap<String, String>> masterResultTable=new LinkedHashMap();
	private  LinkedHashMap<String,LinkedHashMap<String, LinkedHashMap<String, String>>> detailedFlowTable=new LinkedHashMap();
	private  LinkedHashMap<String,String> masterInnerTable;
	private static  LinkedHashMap<String, String> innerDetailTable;
    private static  String failureReason;
    private static  String testPurpose;
    private  String suiteName;
    private  String executedBy=System.getProperty("user.name");
    private  String environmentName;
    private static  String currentMethodName;
    private  boolean globalExecutionStatus=true;
    private LinkedHashMap<String, String> failureSummaryTable=new LinkedHashMap<>();
    String htmlReportPath ;
    
 
     
    
	@Override
	public void onStart(ISuite suite) {
		suiteName = suite.getName();
		 htmlReportPath = System.getProperty("user.dir")+"\\reports\\htmlreports\\" + suiteName + ".html";
		
		environmentName = suite.getParameter("environment");
	}

	@Override
	public void onFinish(ISuite suite) {
		
		try {
			suite.getResults();
			generateHtmlReport();
			sendEmail();
			
		} catch (Exception e) {
			logger.error("exception while creating html report");
		}
	}

	
	
	private void sendEmail() throws Exception {
		
		EmailSetUp .generateMail( htmlReportPath,globalExecutionStatus);
		
	}
	@Override

	public void onTestSuccess(ITestResult tr) {
		passCount++;
		executionTime= ( tr.getEndMillis()-tr.getStartMillis())/1000;
		masterInnerTable = new LinkedHashMap<>();
		masterInnerTable.put("testcaseName", tr.getName());
		try {
			masterInnerTable.put("tesCasePurpose", testPurpose);
		} catch (NullPointerException e) {
			masterInnerTable.put("tesCasePurpose", "NA");
		}
		masterInnerTable.put("executionStatus", "Passed");
		masterInnerTable.put("failureCause", "NA");
		masterInnerTable.put("screenShotPath", "NA");
		masterInnerTable.put("executionTime", executionTime + "_Seconds");

		masterResultTable.put(tr.getName(), masterInnerTable);
		completeDetailedTestResultCapture();
		testPurpose=null;

	}

	@Override
	public void onTestFailure(ITestResult tr) {
		
		globalExecutionStatus=false;
		failCount++;
		executionTime= ( tr.getEndMillis()-tr.getStartMillis())/1000;
		masterInnerTable = new LinkedHashMap<>();
		masterInnerTable.put("testcaseName", tr.getName());
		try {
			masterInnerTable.put("tesCasePurpose", testPurpose);
		} catch (NullPointerException e) {
			masterInnerTable.put("tesCasePurpose", "NA");
		}
		masterInnerTable.put("executionStatus", "Failed");
		masterInnerTable.put("failureCause", failureReason);
		masterInnerTable.put("executionTime", executionTime + "_Seconds");
		try {
			masterInnerTable.put("screenShotPath", Utils.takeScreenShot(tr.getName()));
		} catch (Exception e) {

		}
		masterResultTable.put(tr.getName(), masterInnerTable);
		completeDetailedTestResultCapture();
		failureReason = null;
		testPurpose=null;

	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		skipCount++;
		executionTime= ( tr.getEndMillis()-tr.getStartMillis())/1000;
		masterInnerTable = new LinkedHashMap<>();
		masterInnerTable.put("testcaseName", tr.getName());
		try {
			masterInnerTable.put("tesCasePurpose", testPurpose);
		} catch (NullPointerException e) {
			masterInnerTable.put("tesCasePurpose", "NA");
		}
		masterInnerTable.put("executionStatus", "Skipped");
		masterInnerTable.put("failureCause", "NA");
		masterInnerTable.put("screenShotPath", "NA");
		masterInnerTable.put("startTime", tr.getStartMillis() + "_Seconds");
		masterInnerTable.put("executionTime", executionTime + "_Seconds");
		completeDetailedTestResultCapture();
		testPurpose=null;
	}

	@Override
	public void onTestStart(ITestResult tr) {
		currentMethodName = tr.getName();
		detailedInnerTable = new LinkedHashMap<>();
	}

	/**
	 * Method to log error message
	 * 
	 * @param errormessage
	 */
	public static void logFailureMessage(String errormessage) {

		if (failureReason == null)
			failureReason = errormessage;
	}

	private void generateHtmlReport() throws Exception {
		logger.info("Generating html report.........");
		
		File htmlFile = new File(htmlReportPath);
		if (!htmlFile.exists())
			htmlFile.createNewFile();
		FileWriter fileWriter = new FileWriter(htmlFile);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write("<html><head><title>HtmlReport</title>");
		bufferedWriter.write("<style type=\"text/css\">	                    "
				+ "body {font:normal 80% verdana,arial,helvetica;color:#000000}"
				+ "table tr td, table tr th {font-size:80%}" + ".header{color:yellow;background:#000000}"
				+ "table.summary tr th {font-weight:bold;text-align:center;background:#a6caf0}"
				+ "table.summary tr td {text-align:center;background:#ffffff}"
				+ "table.details tr th {font-weight:bold;text-align:center;background:#a6caf0}"
				+ "table.details tr td {text-align:left;background:#ffffff}"
				+ "p {line-height:1.5em;margin-top:0.5em;margin-bottom:1.0em}"
				+ "h1, h2, h3, h5, h6 {font:bold verdana,arial,helvetica}" + "h2, h3, h5, h6 {margin-bottom:0.5em}"
				+ "h1 {margin:0px 0px 5px;font:300%}" + "h2 {margin-top:1em;font:125%}"
				+ "h3 {font:bold verdana,arial,helvetica}" + "h4 {font:90%;color:black;calibri}"
				+ "footer { position: fixed;left: 0;bottom: 0;width: 100%;background-color: black;color:white;text-align: center;}"
				+ "a {font:87% calibri;color:blue}" + ".Passed {color:green}" + ".passed {color:green}"
				+ ".failed {color:red}" + ".pass {color:green}" + ".fail {color:red}" + ".Pass {color:green}"
				+ ".Fail {color:red}" + ".Failed {color:red}" + ".Skipped {color:yellow}"
				+ ".tooltip {position: relative;display: inline-block;border-bottom: 1px dotted black;}"
				+ ".tooltip:hover .tooltipimage {display: block;height: 500px;width: 700px;overflow: auto;float: left;position: relative;}"
				+ ".tooltip .tooltipimage {display: none;align: center;position: absolute;z-index: 1;}"
				+ "</style>");
		bufferedWriter.write("<script type=\"text/javascript\">");
		bufferedWriter.write(
				"function openDetail(methodName){ var hidden=document.getElementById(methodName).style.display;");
		bufferedWriter.write("if(hidden==='none'){ document.getElementById(methodName).style.display=\"block\";} ");
		bufferedWriter.write("else{ document.getElementById(methodName).style.display=\"none\";}}");
		bufferedWriter.write("</script>");
		bufferedWriter.write("</head>");
		bufferedWriter.write("<body>");
		bufferedWriter.write("<br></br>");
		bufferedWriter.write("<div class=\"header\">");
		bufferedWriter.write("<h1 align=\"center\">Execution Report</h1>");
		bufferedWriter.write("</div>");
		bufferedWriter.write("<br></br>");
		bufferedWriter.write("<h2 align=\"center\">General Details</h2>");
		// Summary of Test execution
		bufferedWriter.write(
				" <table  align=\"center\" class=\"details\" border=\"1\" cellpadding=\"5\" cellspacing=\"2\" width=\"95%\">");
		bufferedWriter.write("<tr valign=\"top\">" + "<th width=\"20%\">Suite Name</th>"
				+ "<th width=\"20%\">Executed On</th>" + "<th width=\"20%\">Executed By</th>"
				+ "</tr>");

		bufferedWriter.write("<tr> " + "<td>" + suiteName + "</td>" + "<td> " + new Date() + " </td> " + "<td> "
				+ executedBy + "</td>" 
				+ "</tr>");
		bufferedWriter.write("</table>");
		bufferedWriter.write("<br></br>");
		setConfigurationDataTable(bufferedWriter);
		bufferedWriter.write("<br></br>");
		createTestResultTable(bufferedWriter);
		bufferedWriter.write("<br></br>");
		createCountTable(bufferedWriter);
		bufferedWriter.write("<br/>");
		bufferedWriter.write("<br/>");
		bufferedWriter.write("<br/>");
		bufferedWriter.write("<br/>");
		
		bufferedWriter.write("<footer>Powered By :<Strong>McFadyen Digital</Strong></footer>");
		bufferedWriter.write("</body></html>");

		bufferedWriter.close();

		logger.info("Completed Generating html report.........");

	}

	private void setConfigurationDataTable(BufferedWriter bufferedWriter) throws Exception {
		bufferedWriter.write("<h2 align=\"center\">Configuration Details</h2>");
		bufferedWriter.write(
				" <table  align=\"center\" class=\"details\" border=\"1\" cellpadding=\"5\" cellspacing=\"2\" width=\"95%\">");
		bufferedWriter.write("<tr valign=\"top\">");
		if (environmentName.equalsIgnoreCase("mobileweb") || environmentName.equalsIgnoreCase("nativeMobile")) {

			if (environmentName.equalsIgnoreCase("mobileweb"))
				bufferedWriter.write("<th width=\"20%\">Browser Name</th>");
			bufferedWriter.write("<th width=\"20%\">Platform Name</th>" + "<th width=\"20%\">Platform Version</th>"
					+ "<th width=\"20%\">Device Name</th>" + "<th width=\"20%\">Application Name</th>"
					+ "<th width=\"20%\">Appium Port</th>" + "<th width=\"20%\">Device Orientation</th>" + "</tr>");

			bufferedWriter.write("<tr> ");
			if (environmentName.equalsIgnoreCase("mobileweb"))
				bufferedWriter
						.write("<td>" + LoadProperty.getPropertyInstance().getProperty("MOBILE_BROWSER") + "</td>");
			bufferedWriter.write(" <td> " + LoadProperty.getPropertyInstance().getProperty("PLATFORM_NAME") + " </td> "
					+ "<td> " + LoadProperty.getPropertyInstance().getProperty("PLATFORM_VERSION") + "</td>" + "<td> "
					+ LoadProperty.getPropertyInstance().getProperty("MOBILE_DEVICE") + " </td> " + "<td> "
					+ LoadProperty.getPropertyInstance().getProperty("APP_PACKAGE") + "</td>" + "<td> "
					+ LoadProperty.getPropertyInstance().getProperty("APPIUM_PORT") + "</td>" + "<td> "
					+ LoadProperty.getPropertyInstance().getProperty("DEVICE_ORIENTATION") + "</td>" + "</tr>");

		} else if (environmentName.equalsIgnoreCase("web") || environmentName.equalsIgnoreCase("remote")) {

			bufferedWriter.write("<th width=\"20%\">Browser Name</th>" + "<th width=\"20%\">Execution Mode</th>"
					+ "<th width=\"20%\">Test Url</th>" + "<th width=\"20%\">Browser Version</th>");
			if (environmentName.equalsIgnoreCase("remote"))
				bufferedWriter.write("<th width=\"20%\">Node Url</th>");
			bufferedWriter.write("</tr>");

			bufferedWriter.write("<tr> ");
			bufferedWriter.write("<td>" + LoadProperty.getPropertyInstance().getProperty("BROWSER_NAME") + "</td>");
			bufferedWriter.write(" <td> " + LoadProperty.getPropertyInstance().getProperty("EXECUTION_MODE") + " </td> "
					+ "<td> " + LoadProperty.getPropertyInstance().getProperty("TEST_URL") + "</td>" + "<td> "
					+ LoadProperty.getPropertyInstance().getProperty("BROWSER_VERSION") + " </td> ");
			if (environmentName.equalsIgnoreCase("remote"))
				bufferedWriter.write(
						"<td> " + LoadProperty.getPropertyInstance().getProperty("NODE_PORT") + "</td>" + "</tr>");

		}

		bufferedWriter.write("</table>");

	}

	private void createTestResultTable(BufferedWriter bufferedWriter) throws IOException {
		int count = 0;
		bufferedWriter.write("<h2 align=\"center\">Test Execution Results</h2>");
		bufferedWriter.write(
				"<table  align=\"center\" class=\"summary\" border=\"1\" cellpadding=\"5\" cellspacing=\"2\" width=\"95%\">");
		bufferedWriter.write("<tr valign=\"top\">");
		bufferedWriter.write("<th width=\"20%\">Serial Number</th>" + "<th width=\"20%\">Test Case Name</th>"
				+ "<th width=\"20%\">Test Case Purpose</th>" + "<th width=\"20%\">Execution Status</th>"
				+ "<th width=\"20%\">Failure Reason</th>" + "<th width=\"20%\">Screen Shot Link</th>"
				+ "<th width=\"20%\">Execution Time</th>" + "<th width=\"20%\">Detailed Report Link</th>" + "</tr>");
		for (String masterKey : masterResultTable.keySet()) {
			count++;

			bufferedWriter.write("<tr valign=\"top\">");
			bufferedWriter.write("<td>" + count + "</td>");
			bufferedWriter.write("<td>" + masterKey + "</td>");
			bufferedWriter.write("<td>" + masterResultTable.get(masterKey).get("tesCasePurpose") + "</td>");
			bufferedWriter
					.write(String.format("<td class=\"%s\">", masterResultTable.get(masterKey).get("executionStatus"))
							+ masterResultTable.get(masterKey).get("executionStatus") + "</td>");
			bufferedWriter.write("<td>" + masterResultTable.get(masterKey).get("failureCause") + "</td>");
			if (masterResultTable.get(masterKey).get("executionStatus").equalsIgnoreCase("failed"))
				bufferedWriter.write(String.format("<td><a href=\"%s\">Click Here For ScreenShot</a></td>",
						masterResultTable.get(masterKey).get("screenShotPath")));
			else
				bufferedWriter.write("<td>NA</td>");

			bufferedWriter.write("<td>" + masterResultTable.get(masterKey).get("executionTime") + "</td>");
			bufferedWriter.write("<td ><button  class=\"detailedSummary\"onclick=\"openDetail('" + masterKey.trim()
					+ "')\">Click Here For More Details</td>");
			bufferedWriter.write("</tr>");
			bufferedWriter.write(String.format("<tr id=\"%s\" style=\"display :none;\">", masterKey));
			bufferedWriter.write("<td><div>");
			completeDetailedFlowTable(bufferedWriter, masterKey);
			bufferedWriter.write("</div>");
			bufferedWriter.write("</td>");
			bufferedWriter.write("</tr>");

		}
		bufferedWriter.write("</table>");

	}

	private void completeDetailedFlowTable(BufferedWriter bufferedWriter, String masterKey) throws IOException {
		int count = 0;
		bufferedWriter.write("<h2 align=\"center\">Detailed Flow Analysis</h2>");
		bufferedWriter.write(
				"<table  align=\"center\" class=\"summary\" border=\"1\" cellpadding=\"5\" cellspacing=\"2\" width=\"95%\">");
		bufferedWriter.write("<tr valign=\"top\">");
		bufferedWriter.write("<th width=\"20%\">Serial Number</th>" + "<th width=\"20%\">Scenario Name</th>"
				+ "<th width=\"20%\">Scenario Purpose</th>" + "<th width=\"20%\">Execution Status</th>"
				+ "<th width=\"20%\">Screen Shot</th>" + "</tr>");
		for (String masterKeyDetail : detailedFlowTable.get(masterKey).keySet()) {
			count++;
			bufferedWriter.write("<tr valign=\"top\">");
			bufferedWriter.write("<td>" + count + "</td>");
			bufferedWriter.write("<td>" + detailedFlowTable.get(masterKey).get(masterKeyDetail).get("scenarioName") + "</td>");
			bufferedWriter.write("<td>" + detailedFlowTable.get(masterKey).get(masterKeyDetail).get("scenarioPurpose") + "</td>");
			bufferedWriter.write(String.format("<td class=\"%s\">", detailedFlowTable.get(masterKey).get(masterKeyDetail).get("executionStatus"))
					+ detailedFlowTable.get(masterKey).get(masterKeyDetail).get("executionStatus") + "</td>");
			if(detailedFlowTable.get(masterKey).get(masterKeyDetail).get("screenShotPath").equalsIgnoreCase("NA"))
				bufferedWriter.write("<td>" + " " + "</td>");
			else {
			bufferedWriter.write("<td><div class=\"tooltip\" >Focus here for Screen Shot");
			bufferedWriter.write("<div class=\"tooltipimage\" >");
			bufferedWriter.write(String.format("<img  src=\"%s\" />",
					detailedFlowTable.get(masterKey).get(masterKeyDetail).get("screenShotPath")));
			bufferedWriter.write("</div>");
			bufferedWriter.write("</div>");
			bufferedWriter.write("</td>");
			}
			bufferedWriter.write("</tr>");
			
			}
		bufferedWriter.write(" </table>");
	}

	private void createCountTable(BufferedWriter bufferedWriter) throws IOException {
		bufferedWriter.write("<h2 align=\"center\">Over All Execution Status</h2>");
		bufferedWriter.write(
				" <table  align=\"center\" class=\"summary\" border=\"1\" cellpadding=\"5\" cellspacing=\"2\" width=\"95%\">");
		bufferedWriter.write("<tr valign=\"top\">");
		bufferedWriter.write("<th width=\"20%\">Total Executed</th>" + "<th width=\"20%\">Total Passed</th>"
				+ "<th width=\"20%\">Total Failed</th>" + "<th width=\"20%\">Total Skipped</th>" + "</tr>");
		bufferedWriter.write("<tr valign=\"top\">");
		bufferedWriter.write("<td>" + (failCount + passCount + skipCount) + "</td>");
		bufferedWriter.write("<td>" + passCount + "</td>");
		bufferedWriter.write("<td>" + failCount + "</td>");
		bufferedWriter.write("<td>" + skipCount + "</td>");
		bufferedWriter.write("</tr>");
		bufferedWriter.write(" </table>");
	}

	/**
	 * Method to set
	 * 
	 * @param scenarioName
	 * @param executionStatus
	 * @throws Exception
	 */
	public static void logDetailedInfoWithScreenCapture(String scenarioName, String scenarioPurpose, String executionStatus)
			throws Exception {
		innerDetailTable=new LinkedHashMap<>();
		innerDetailTable.put("scenarioName", scenarioName);
		innerDetailTable.put("scenarioPurpose", scenarioPurpose);
		innerDetailTable.put("executionStatus", executionStatus.toLowerCase());
		innerDetailTable.put("screenShotPath", Utils.takeScreenShot(currentMethodName + "_" + scenarioName));
		detailedInnerTable.put(currentMethodName + "_" + scenarioName,innerDetailTable);
	}
	
	
	/**
	 * Method to set
	 * 
	 * @param scenarioName
	 * @param executionStatus
	 * @throws Exception
	 */
	public static void logDetailedInfo(String scenarioName, String scenarioPurpose, String executionStatus)
			throws Exception {
		innerDetailTable=new LinkedHashMap<>();
		innerDetailTable.put("scenarioName", scenarioName);
		innerDetailTable.put("scenarioPurpose", scenarioPurpose);
		innerDetailTable.put("executionStatus", executionStatus.toLowerCase());
		innerDetailTable.put("screenShotPath", "NA");
		detailedInnerTable.put(currentMethodName + "_" + scenarioName,innerDetailTable);
	}
	
	
	
	

	/**
	 * Method to complete detailed test result capture
	 * 
	 * @param methodName
	 */
	private void completeDetailedTestResultCapture() {

		detailedFlowTable.put(currentMethodName, detailedInnerTable);
	}
	
	
	
	
	/**
	 * Method to set test case purpose
	 */
   public static void setTestPurpose(String testPurposeString) {
	if(testPurpose==null)   
		testPurpose=testPurposeString;
   }
}
