package com.supportlibraries;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Utils extends Base {
	private static Logger logger = Logger.getLogger(Utils.class.getName());
	private static SimpleDateFormat dateFormat;
	private static File screenShot;
	private static String filePath;
	private static File fileDirectory;
	private static String timeStamp;
	private static Date date;
	private static Document xmlDocument;
	private static Element object;
	private static Hashtable<String, String> dataTable;
	private static Hashtable<String, String> childTable;
	private static Hashtable<String, Hashtable<String, String>> masterTable;
	private static String dateStamp;

	public void createBackUpReport() {

	}

	private static String getTimeStamp() {
		date = new Date();
		dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String timeStamp = dateFormat.format(date).replaceAll("-", "_").replaceAll(":", "_").replace(" ", "_");
		return timeStamp;

	}

	/**
	 * 
	 * method to take screen shot
	 * 
	 * @param methodName
	 * @return
	 * @throws Exception
	 */
	public static String takeScreenShot(String methodName) throws Exception {

		try {
			dateStamp=Calendar.getInstance().getTime().toString().split(":")[0].replaceAll(" ", "_");
			fileDirectory=new File("\\"+LoadProperty.getPropertyInstance().getProperty("SCREENSHOT_LOCATION")+"/"+dateStamp);
			if(!fileDirectory.exists()) {
				fileDirectory.mkdir();
			}
			screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			
			filePath = fileDirectory + "\\" + methodName.replaceAll(" ", "_") + "_"+ getTimeStamp() + ".jpg";
			FileUtils.copyFile(screenShot, new File(filePath));
			return filePath;
		} catch (Exception e) {
			throw (new Exception());
		}
	}

	/**
	 * Method to parse through the XML based on locator key and return the contents
	 * in <value> tag
	 * 
	 * @param locatorName
	 * @param pageName
	 * @return locator
	 * @throws Exception
	 */
	public static Hashtable<String, String> loadDataFromXml(String filePath, String splitKey, String splitValue)
			throws Exception {

		dataTable = new Hashtable<>();
		Document xmlDocument = initializeXML(filePath);

		if (xmlDocument != null) {
			NodeList locatorNodes = xmlDocument.getElementsByTagName(splitKey);

			for (int i = 0; i < locatorNodes.getLength(); i++) {
				object = (Element) locatorNodes.item(i);
				if (object.getAttribute("name").equalsIgnoreCase(splitValue)) {
					NodeList propertyList = object.getElementsByTagName("data");
					for (int j = 0; j < propertyList.getLength(); j++) {
						object = (Element) propertyList.item(j);

						dataTable.put(object.getAttribute("key").toUpperCase(), object.getAttribute("value"));
					}
				}
			}
		}
		return dataTable;
	}

	/**
	 * Method to parse through the XML based on locator key and return the contents
	 * in <value> tag
	 * 
	 * @param locatorName
	 * @param pageName
	 * @return locator
	 * @throws Exception
	 */
	public static Hashtable<String, Hashtable<String, String>> loadDataFromXml(String filePath, String splitKey)
			throws Exception {
		masterTable = new Hashtable<>();
		Element parentNode;
		Element childNode;

		if (xmlDocument != null) {
			NodeList locatorNodes = xmlDocument.getElementsByTagName(splitKey);

			for (int i = 0; i < locatorNodes.getLength(); i++) {
				parentNode = (Element) locatorNodes.item(i);
				childTable = new Hashtable<>();

				NodeList propertyList = object.getElementsByTagName("data");
				for (int j = 0; j < propertyList.getLength(); j++) {
					childNode = (Element) propertyList.item(j);

					childTable.put(object.getAttribute("key"), object.getAttribute("value"));

				}
				masterTable.put(parentNode.getAttribute("name"), childTable);
			}
		}
		return masterTable;
	}

	/**
	 * Method to read data from XML
	 * 
	 * @param filePath
	 * @return contents of whole XML
	 * @throws Exception
	 */
	private static Document initializeXML(String filePath) throws Exception {
		try {
			logger.info("Initializing the xml file :" + filePath);
			xmlDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new File(filePath));
			xmlDocument.getDocumentElement().normalize();
			return xmlDocument;
		} catch (Exception e) {

			logger.error("Error while reading from XML. File Path : " + filePath + " \n" + e.getMessage());
			throw (new Exception(e.getMessage()));

		}
	}
	
	
	
	public static int getTimeDifference(DateTime startTime,DateTime endTime) {
		Seconds seconds=Seconds.secondsBetween(startTime, endTime);
		return seconds.getSeconds();
		
	}

}
