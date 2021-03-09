package com.supportlibraries;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class LoadProperty {
	/**
	 * @author Anjulakshmy.P.U
	 *  Single ton class to provide multiple initialization of
	 *         property object
	 */
	private static Logger logger = Logger.getLogger(LoadProperty.class.getName());
	private static LoadProperty loadProperty;
	private static Properties property;
	private static File propertyFile;

	private LoadProperty() throws Exception {
		propertyFile = new File("./resources/config.properties");
		loadProperty();
	}

	/**
	 * 
	 * Method to return the property class object to the calling function. This
	 * class is a single ton class
	 * 
	 * @return
	 * @throws Exception
	 */

	public static LoadProperty getPropertyInstance() throws Exception {
		if (loadProperty == null)
			loadProperty = new LoadProperty();

		return loadProperty;
	}

	/**
	 * method to load property object
	 * 
	 * @throws Exception
	 */
	private void loadProperty() throws Exception {
		FileInputStream fin = null;
		if (property == null) {
			property = new Properties();

			try {
				fin = new FileInputStream(propertyFile);
				property.load(fin);

			} catch (FileNotFoundException f) {
				throw (new Exception("Unable to locate the property file"));
			}

			catch (IOException IO) {
				throw (new Exception("Error occured while reading proprerty file"));
			} finally {
				if (fin != null)
					fin.close();

			}

		}
	}

	/**
	 * 
	 * method to read from a property file
	 * 
	 * @param propertyName
	 * @return
	 * @throws Exception
	 */
	public String getProperty(String propertyName) throws Exception {

		return property.getProperty(propertyName);
	}

	/**
	 * method to write in to a property file
	 * 
	 * @param propertyName
	 * @param Value
	 * @throws Exception
	 */
	public void setProperty(String propertyName, String Value) throws Exception {
		FileOutputStream fileOut = null;
		try {

			property.setProperty(propertyName, Value);

			fileOut = new FileOutputStream(propertyFile);

			property.store(fileOut, "");

		} catch (FileNotFoundException f) {
			throw (new Exception("Unable to locate the property file"));
		}

		catch (IOException IO) {
			throw (new Exception("Error occured while reading proprerty file"));
		} finally {
			if (fileOut != null)
				fileOut.close();

		}

	}

}
