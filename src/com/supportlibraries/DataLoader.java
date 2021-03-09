package com.supportlibraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Hashtable;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DataLoader {
	/**
	 * @author Anjulakshmy.P.U
	 * 
	 * 
	 */
	private static Logger logger = Logger.getLogger(DataLoader.class.getName());
	private static HSSFWorkbook workBook;
	private static Hashtable<String, Hashtable<String, String>> mainDataTable;
	private static Hashtable<String, String> innerDataTable;
	private static Hashtable<String, Hashtable<String, String>> masterDataTable;
	private static DataLoader dataLoader;
	private static String excelDataFilePath;
	private static String xmllDataFilePath;
	private static String testDataDocumentType;

	private DataLoader() throws Exception {
		excelDataFilePath = LoadProperty.getPropertyInstance().getProperty("EXCEL_DATA_FILE_PATH");
		xmllDataFilePath = LoadProperty.getPropertyInstance().getProperty("XML_DATA_FILE_PATH");
		testDataDocumentType = LoadProperty.getPropertyInstance().getProperty("TEST_DATA_DOC_TYPE");
		loadDocumentType();
		System.out.println("");
	}

	public static void setDataLoader() throws Exception {

		if (dataLoader == null) {
			dataLoader = new DataLoader();
		}
	}

	/**
	 * Method to retrieve thw workbook instance
	 * 
	 * @return
	 * @throws Exception
	 */
	private HSSFWorkbook getWorkBook() throws Exception {
		try {
			if (workBook == null) {
				logger.info("reading data from data sheet "
						+ excelDataFilePath);
				File file = new File(excelDataFilePath);

				FileInputStream fins = new FileInputStream(file);
				workBook = new HSSFWorkbook(fins);
			}

		} catch (Exception e) {
			logger.error("Exception occured while reading data from file "
					+ LoadProperty.getPropertyInstance().getProperty("EXCEL_DATA_FILE_PATH") + "\n"
					+ e.getStackTrace());

		}
		return workBook;

	}

	/**
	 * 
	 * @throws Exception
	 */

	private void loadDocumentType() throws Exception {
		if (testDataDocumentType.equalsIgnoreCase("excel")) {
			masterDataTable = loadDataTable("regression");

		} else if (testDataDocumentType.equalsIgnoreCase("xml"))
			masterDataTable = Utils.loadDataFromXml(xmllDataFilePath, "testcase");
	}

	/**
	 * Method to retrieve the work sheet instance
	 * 
	 * @param sheetName
	 * @return
	 * @throws Exception
	 */
	private HSSFSheet getDataSheet(String sheetName) throws Exception {
		return getWorkBook().getSheet(sheetName);
	}

	/**
	 * method to load data in to hashtable from the excel sheet.This happens during
	 * the initial data load
	 * 
	 * @param workBookName
	 * @param sheetName
	 * @return
	 */
	private Hashtable<String, Hashtable<String, String>> loadDataTable(String sheetName) {

		String dataSetID;
		int j = 0;
		try {
			mainDataTable = new Hashtable<>();
			HSSFSheet sheet = getDataSheet(sheetName);
			int rowCount = sheet.getLastRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();
			for (int i = 1; i <= rowCount; i++) {
				try {
					innerDataTable = new Hashtable<>();
					dataSetID = sheet.getRow(i).getCell(0).toString();
					for (j = 1; j < colCount; j++) {
						try {
						innerDataTable.put(sheet.getRow(0).getCell(j).toString(),
								sheet.getRow(i).getCell(j).toString());}
						catch(NullPointerException e) {
							
						}
					}
					mainDataTable.put(dataSetID, innerDataTable);
				} catch (NullPointerException e) {
					logger.error("error while reading data from row " + i + " column" + j);
				}
			}
		} catch (Exception e) {
			logger.error("error while reading data from " + sheetName);
		}
		return mainDataTable;

	}

	/**
	 * method to retrieve test data from data tables
	 * 
	 * @param dataTableName
	 * @param dataset
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getTestData(String dataSet, String key) throws Exception {
		try {

			return masterDataTable.get(dataSet).get(key);

		} catch (Exception e) {
			logger.error("Error while fetching test data " + "\n" + e.getStackTrace());
			throw (new Exception("Error while fetching test data " + "\n" + e.getMessage()));
		}

	}
	
	protected JSONObject getDataFile(String dataFileName) {
	    String dataFilePath = "%userprofile%//desktop//";
	    JSONObject testObject = null; 

	    try {
	        FileReader reader = new FileReader(dataFilePath + dataFileName);                        
	        JSONParser jsonParser = new JSONParser();
	        JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
	        testObject = (JSONObject) jsonObject;
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	    return testObject;
	}
	
	public java.util.List<JSONObject> getTestScenarios(String dataFileName, String testCaseName) {
	    JSONArray testCase = (JSONArray) getDataFile(dataFileName).get(testCaseName);
	    java.util.List<JSONObject> testScenarioArray = new ArrayList<JSONObject>();
	    

	    for (int i = 0; i < testCase.size(); i++) {
	         testScenarioArray.add((JSONObject) testCase.get(i));
	    }
//	    Object[][] dataProviderArray = new Object[testScenarioArray.size()][];
//	    for (int scenario = 0; scenario < testScenarioArray.size(); scenario++) {
//	        String scenarioName = null;
//
//	        if ((String) testScenarioArray.get(scenario).get("scenario") != null) {
//	            scenarioName = (String) testScenarioArray.get(scenario).get("scenario");
//	        } else {
//	            scenarioName = "No scenario name specified";
//	        };
//	        dataProviderArray[scenario] = new Object[] { scenarioName, (JSONObject) testScenarioArray.get(scenario) };
//	    }
	    return testScenarioArray;
	}

}
