package com.supportlibraries;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

public class DriverSetUp extends Base {
	/**
	 * @author Anjulakshmy.P.U
	 * @throws Exception
	 */
	private static Logger logger = Logger.getLogger(DriverSetUp.class.getName());
	private static String Appium_Node_Path = "C:\\Program Files\\nodejs\\node.exe";
	private static AppiumDriverLocalService service;
	private static String Appium_JS_Path = "C:\\Users\\jvijayaratnam\\AppData\\Roaming\\npm\\node_modules\\appium\\lib\\appium.js";
	private static String service_url;
	private static String mobileConfigurationPath;
	private static Hashtable<String, String> mobileConfigTable;

	/**
	 * 
	 * Method to set up the driver
	 * 
	 * @throws Exception
	 */
	private void setUpDriver() throws Exception {
		try {
			String browserName = LoadProperty.getPropertyInstance().getProperty("BROWSER_NAME");
			String executionMode = LoadProperty.getPropertyInstance().getProperty("EXECUTION_MODE");
			logger.info("Setting up the " + executionMode + " driver ");
			if (executionMode.equalsIgnoreCase("local"))
				DriverFactory.get().setLocalDriver(browserName);
			else {
				DriverFactory.get().setRemoteDriver(browserName);
			}
		} catch (Throwable t) {
			
			throw (new Exception(t.getMessage()));
		}

	}

	/**
	 * Start point of execution
	 * 
	 * @throws Exception
	 */
	@BeforeSuite
	@Parameters({ "environment", "mode", "browser", "url", "nodeUrl", "deviceName" })
	public void initialize(String environment, String mode, String browser, String url, String nodeUrl,
			String deviceName) throws Exception {

		if (environment.equalsIgnoreCase("web")) {
			if (mode.equalsIgnoreCase("local")) {
				setWebLocalConfiguration(mode, browser, url);
				DataLoader.setDataLoader();
				setUpDriver();
				launchUrl(environment);
			} else if (mode.equalsIgnoreCase("remote")) {
				setWebRemoteConfiguration(mode, browser, url, nodeUrl);
				DataLoader.setDataLoader();
				setUpDriver();
				launchUrl(environment);
			}
		} else if (environment.equalsIgnoreCase("nativeMobile")) {
			mobileConfigurationPath = LoadProperty.getPropertyInstance().getProperty("MOBILE_NATIVE_CONFIG_PATH");
			if (mode.equalsIgnoreCase("local")) {
				setMobileConfiguration(mode, browser, url);
				setMobileConfigPropertiesFromXml(deviceName);
				DataLoader.setDataLoader();
				appiumStart(LoadProperty.getPropertyInstance().getProperty("APPIUM_PORT"));
				setUpDriver();
			} else if (mode.equalsIgnoreCase("remote")) {
				setMobileConfiguration(mode, browser, url);
				setMobileConfigPropertiesFromXml(deviceName);
				DataLoader.setDataLoader();
				appiumStart(LoadProperty.getPropertyInstance().getProperty("APPIUM_PORT"));
				setUpDriver();
			}
		}

		else if (environment.equalsIgnoreCase("mobileWeb")) {
			mobileConfigurationPath = LoadProperty.getPropertyInstance().getProperty("MOBILE_WEB_CONFIG_PATH");
			if (mode.equalsIgnoreCase("local")) {
				setMobileConfiguration(mode, browser, url);
				setMobileConfigPropertiesFromXml(deviceName);
				DataLoader.setDataLoader();
				appiumStart(LoadProperty.getPropertyInstance().getProperty("APPIUM_PORT"));
				setUpDriver();
				launchUrl(environment);
			} else if (mode.equalsIgnoreCase("remote")) {
				setMobileConfiguration(mode, browser, url);
				DataLoader.setDataLoader();
				appiumStart(LoadProperty.getPropertyInstance().getProperty("APPIUM_PORT"));
				setUpDriver();
				launchUrl(environment);
			}
		}
		else if (environment.equalsIgnoreCase("androidMobileWeb")) {
			initializeAndroidDriver();
		}

	}
	
	public void initializeAndroidDriver()  throws MalformedURLException
	{
	// TODO Auto-generated method stub
//		 File appDir = new File("src");
//	     File app = new File(appDir, "ApiDemos-debug.apk");
	     DesiredCapabilities capabilities = new DesiredCapabilities();
	     capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Demo");
//	     capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	     capabilities.setCapability(MobileCapabilityType.BROWSER_NAME,"Chrome");
	     capabilities.setCapability("autoGrantPermissions",true);
	     driver= new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
	   
	}

	private void setMobileConfigPropertiesFromXml(String deviceName) throws Exception {

		mobileConfigTable = Utils.loadDataFromXml(mobileConfigurationPath, "device", deviceName);
		for (String mobileConfig : mobileConfigTable.keySet()) {
			LoadProperty.getPropertyInstance().setProperty(mobileConfig, mobileConfigTable.get(mobileConfig));
		}

	}

	/**
	 * method to update the config file
	 * 
	 * @param mode
	 * @param browser
	 * @param url
	 * @throws Exception
	 */
	private void setWebLocalConfiguration(String mode, String browser, String url) throws Exception {
		logger.info("Updating the property files ");
		LoadProperty.getPropertyInstance().setProperty("BROWSER_NAME", browser);
		LoadProperty.getPropertyInstance().setProperty("EXECUTION_MODE", mode);
		LoadProperty.getPropertyInstance().setProperty("TEST_URL", url);

	}

	private void setWebRemoteConfiguration(String mode, String browser, String url, String nodeUrl) throws Exception {
		logger.info("Updating the property files ");
		LoadProperty.getPropertyInstance().setProperty("BROWSER_NAME", browser);
		LoadProperty.getPropertyInstance().setProperty("EXECUTION_MODE", mode);
		LoadProperty.getPropertyInstance().setProperty("TEST_URL", url);
		LoadProperty.getPropertyInstance().setProperty("NODE_PORT", nodeUrl);
	}

	private void setMobileConfiguration(String mode, String browser, String url) throws Exception {
		logger.info("Updating the property files ");
		LoadProperty.getPropertyInstance().setProperty("BROWSER_NAME", browser);
		LoadProperty.getPropertyInstance().setProperty("EXECUTION_MODE", mode);
		LoadProperty.getPropertyInstance().setProperty("TEST_URL", url);

	}

	/**
	 * 
	 * Method to launch the url
	 * 
	 * @throws Exception
	 */
	private void launchUrl(String environment) throws Exception {

		String url = null;
		try {
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			if(environment.equalsIgnoreCase("web"))
				driver.manage().window().maximize();
			
			url = LoadProperty.getPropertyInstance().getProperty("TEST_URL");
			driver.get(url);
			logger.info("launching the url :" + url);
		} catch (Exception e) {
			logger.error("Failed to launch the url:" + url + "\n" + e.getMessage());

		}

	}

	/**
	 * Method to start appium service
	 * 
	 * @param port
	 * @throws Exception
	 */
	private static void appiumStart(String port) throws Exception {
		
		
		try {
			
			Runtime.getRuntime().exec("cmd.exe /c start cmd.exe /k \"appium -a 127.0.0.1 -p "+ port+" --session-override -dc \"{\"\"noReset\"\": \"\"false\"\"}\"\"");
			Thread.sleep(10000);
			
			/*service = AppiumDriverLocalService.buildService(new AppiumServiceBuilder().usingPort(Integer.parseInt(port))
					.usingDriverExecutable(new File(Appium_Node_Path)).withAppiumJS(new File(Appium_JS_Path)));
			service.start();
			logger.info("Starting appium server..... ,Listening to port: " + port);
			Thread.sleep(10000);
			service_url = service.getUrl().toString();
			LoadProperty.getPropertyInstance().setProperty("APPIUM_SERVER_ADDRESS", service_url);*/
			
			
			
			
		} catch (Exception e) {
			logger.error("Error while starting appium service ,port :" + port + " \n" + e.getMessage());
			throw (new Exception("unable to start appium service"));
		}
		
	}

	/**
	 * method to stop appium service
	 * 
	 * @throws Exception
	 */
	private static void appiumStop() throws Exception {
		//service.stop();
		Runtime runtime = Runtime.getRuntime();
		runtime.exec("taskkill /F /IM node.exe");
		runtime.exec("taskkill /F /IM cmd.exe");
	}

	/**
	 * End point of the execution
	 * 
	 * @throws Exception
	 */
	@AfterSuite
	public void tearDown() throws Exception {
		quitBrowser();
		//appiumStop();
	}

	/**
	 * method to quit the browser
	 * 
	 * 
	 */
	private void quitBrowser() {
		driver.quit();
	}

}
