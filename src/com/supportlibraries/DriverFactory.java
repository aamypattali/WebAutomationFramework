package com.supportlibraries;

import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;

public class DriverFactory extends Base {
	/**
	 * @author Anjulakshmy.P.U 
	 * Java class that set up the webdriver
	 */
	private static Logger logger = Logger.getLogger(DriverFactory.class.getName());
	private static String mobileDevice;
	private static String osVersion;
	private static String mobileBrowser;
	private static String appPackage;
	private static String appActivity;
	private static String appiumServer;
	private static String mobileOrientation;

	/**
	 * 
	 * @return webriver instance
	 * @throws Exception
	 *             method to initialize the local driver
	 */

	public static ThreadLocal<DriverFactory> thread = new ThreadLocal<DriverFactory>() {

		protected synchronized DriverFactory initialValue() {
			return new DriverFactory();
		}
	};

	public static DriverFactory get() {

		return thread.get();

	}

	public WebDriver setLocalDriver(String browserName) throws Exception {
		try {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			switch (Browser.valueOf(browserName)) {
			case chrome:
				System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
				capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				ChromeOptions chromeOptions = new ChromeOptions();
				chromeOptions.addArguments("test-type");
				chromeOptions.addArguments("disable-popup-blocking");
				capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
				driver = new ChromeDriver(capabilities);
				logger.info("Launching" + Browser.chrome + " driver");
				break;
			case firefox:
								
				System.setProperty("webdriver.gecko.driver", "./drivers/geckodriver.exe");
				FirefoxProfile fireFoxProfile = new FirefoxProfile();
				fireFoxProfile.setAcceptUntrustedCertificates(false);
				fireFoxProfile.setAssumeUntrustedCertificateIssuer(true);
				fireFoxProfile.setPreference("browser.download.folderList", 2);
				fireFoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
				fireFoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
				fireFoxProfile.setPreference("browser.download.dir", "C:\\Downloads");
				fireFoxProfile.setPreference("browser.download.downloadDir", "C:\\Downloads");
				fireFoxProfile.setPreference("browser.download.defaultFolder", "C:\\Downloads");
				fireFoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk",
						"text/anytext ,text/plain,text/html,application/plain");
				capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(FirefoxDriver.PROFILE, fireFoxProfile);
				driver = new FirefoxDriver(capabilities);
				logger.info("Launching" + Browser.firefox + " driver");
				break;
			case internetexplorer:
				System.setProperty("webdriver.ie.driver", "./drivers/IEDriverServer.exe");
				capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability("ignoreZoomSetting", true);
				capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				driver = new InternetExplorerDriver(capabilities);
				logger.info("Launching" + Browser.internetexplorer + " driver");
				break;
			case safari:
				capabilities = DesiredCapabilities.safari();
				capabilities.setPlatform(Platform.WINDOWS);
				driver = new SafariDriver(capabilities);
				logger.info("Launching" + Browser.safari + " driver");
				break;
			case iosweb:
				capabilities = DesiredCapabilities.iphone();
				capabilities.setPlatform(Platform.MAC);
				logger.info("Launching driver");
				break;
			case iosnative:
				capabilities = DesiredCapabilities.iphone();
				capabilities.setPlatform(Platform.MAC);
				//logger.info("Launching" + Browser.ios + " driver");
				break;
			case androidweb:
				osVersion = LoadProperty.getPropertyInstance().getProperty("PLATFORM_VERSION");
				mobileDevice = LoadProperty.getPropertyInstance().getProperty("MOBILE_DEVICE");
				mobileBrowser = LoadProperty.getPropertyInstance().getProperty("MOBILE_BROWSER");
				appiumServer ="http://127.0.0.1:"+LoadProperty.getPropertyInstance().getProperty("APPIUM_PORT")+"/wd/hub";
				mobileOrientation = LoadProperty.getPropertyInstance().getProperty("DEVICE_ORIENTATION");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, osVersion);
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mobileDevice);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
				capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, mobileBrowser);
				capabilities.setCapability(MobileCapabilityType.ORIENTATION, mobileOrientation);
				capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600000);
				//capabilities.setCapability("appPackage", "com.android.browser");
				//capabilities.setCapability("appActivity", "com.android.browser.BrowserActivity");
				driver = new AndroidDriver(new URL(appiumServer), capabilities);
				logger.info("Launching Android driver ...........");
				logger.info("opening browser: "+mobileBrowser+"..........");
				break;

			case androidnative:
				osVersion = LoadProperty.getPropertyInstance().getProperty("PLATFORM_VERSION");
				mobileDevice = LoadProperty.getPropertyInstance().getProperty("MOBILE_DEVICE");
				mobileBrowser = LoadProperty.getPropertyInstance().getProperty("MOBILE_BROWSER");
				appiumServer = LoadProperty.getPropertyInstance().getProperty("APPIUM_SERVER_ADDRESS");
				mobileOrientation = LoadProperty.getPropertyInstance().getProperty("DEVICE_ORIENTATION");
				appPackage = LoadProperty.getPropertyInstance().getProperty("APP_PACKAGE");
				appActivity = LoadProperty.getPropertyInstance().getProperty("APP_ACTIVITY");
				capabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, osVersion);
				capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, mobileDevice);
				capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
				capabilities.setCapability(MobileCapabilityType.ORIENTATION, mobileOrientation);
				capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 600000);
				capabilities.setCapability("appPackage", appPackage);
				capabilities.setCapability("appActivity", appActivity);
				driver = new AndroidDriver(new URL(appiumServer), capabilities);
				logger.info("Launching Android driver ...........");
				break;
			default:
				break;
			}
			return driver;
		} catch (Throwable t) {
			logger.error("Launching " + browserName + " driver failed " + t.getMessage().toString());
			throw (new Exception("unable to initalize the webdriver"));
		}
	}

	/**
	 * @author jvijayaratnam
	 * @return webriver instance
	 * @throws Exception
	 *             method to initialize the remote driver
	 */
	public WebDriver setRemoteDriver(String browserName) throws Exception {
		try {
			DesiredCapabilities capability = new DesiredCapabilities();

			String nodeUrl = "http://" + LoadProperty.getPropertyInstance().getProperty("HUB_HOSTNAME") + ":"
					+ LoadProperty.getPropertyInstance().getProperty("NODE_PORT") + "/wd/hub";

			switch (Browser.valueOf(browserName)) {

			case firefox:
				capability = DesiredCapabilities.firefox();
				driver = new RemoteWebDriver(new URL(nodeUrl), capability);
				break;

			case chrome:
				capability = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				capability.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new RemoteWebDriver(new URL(nodeUrl), capability);
				break;

			case safari:
				capability = DesiredCapabilities.safari();
				SafariOptions safariOptions = new SafariOptions();
				safariOptions.setUseCleanSession(true);
				capability.setPlatform(Platform.MAC);
				capability.setCapability(SafariOptions.CAPABILITY, safariOptions);
				driver = new RemoteWebDriver(new URL(nodeUrl), capability);
				break;

			case internetexplorer:

				capability = DesiredCapabilities.internetExplorer();
				capability.setCapability("ignoreZoomSetting", true);
				driver = new RemoteWebDriver(new URL(nodeUrl), capability);
				break;
			case iosweb:
				capability = DesiredCapabilities.iphone();
				capability.setPlatform(Platform.MAC);
				// driver = new ios
				break;
			case iosnative:
				capability = DesiredCapabilities.iphone();
				capability.setPlatform(Platform.MAC);
				// driver = new ios
				break;
			case androidweb:
				osVersion = LoadProperty.getPropertyInstance().getProperty("PLATFORM_VERSION");
				mobileDevice = LoadProperty.getPropertyInstance().getProperty("ANDROID_DEVICE");
				mobileBrowser = LoadProperty.getPropertyInstance().getProperty("ANDROID_BROWSER");
				appiumServer = LoadProperty.getPropertyInstance().getProperty("APPIUM_SERVER_ADDRESS");
				mobileOrientation = LoadProperty.getPropertyInstance().getProperty("DEVICE_ORIENTATION");
				capability.setCapability(MobileCapabilityType.PLATFORM_VERSION, osVersion);
				capability.setCapability(MobileCapabilityType.DEVICE_NAME, mobileDevice);
				capability.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
				capability.setCapability(MobileCapabilityType.BROWSER_NAME, mobileBrowser);
				capability.setCapability(MobileCapabilityType.ORIENTATION, mobileOrientation);
				capability.setCapability("appPackage", "com.android.browser");
				capability.setCapability("appActivity", "com.android.browser.BrowserActivity");
				driver = new AndroidDriver<WebElement>(new URL(appiumServer), capability);
				logger.info("Launching Android driver ...........");
				logger.info("opening browser: "+mobileBrowser+"..........");
				driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capability);

				break;
			case androidnative:
				osVersion = LoadProperty.getPropertyInstance().getProperty("PLATFORM_VERSION");
				mobileDevice = LoadProperty.getPropertyInstance().getProperty("ANDROID_DEVICE");
				mobileBrowser = LoadProperty.getPropertyInstance().getProperty("ANDROID_BROWSER");
				appiumServer = LoadProperty.getPropertyInstance().getProperty("APPIUM_SERVER_ADDRESS");
				mobileOrientation = LoadProperty.getPropertyInstance().getProperty("DEVICE_ORIENTATION");
				appPackage = LoadProperty.getPropertyInstance().getProperty("APP_PACKAGE");
				appActivity = LoadProperty.getPropertyInstance().getProperty("APP_ACTIVITY");
				capability.setCapability(MobileCapabilityType.PLATFORM_VERSION, osVersion);
				capability.setCapability(MobileCapabilityType.DEVICE_NAME, mobileDevice);
				capability.setCapability(MobileCapabilityType.PLATFORM_NAME, "ANDROID");
				capability.setCapability(MobileCapabilityType.ORIENTATION, mobileOrientation);
				capability.setCapability("appPackage", appPackage);
				capability.setCapability("appActivity", "appActivity");
				driver = new RemoteWebDriver(new URL("http://127.0.0.1:4723/wd/hub"), capability);

				break;
			default:
				break;
			}

			return driver;
		} catch (Throwable t) {
			logger.error("Launching" + browserName + " driver failed " + t.getStackTrace().toString());
			throw (new Exception("unable to initalize the remote  webdriver"));
		}

	}

	/**
	 * method to set the appium url
	 * 
	 * @param appiumUrl
	 */
	public static void setAppiumServer(String appiumUrl) {
		appiumServer = appiumUrl;
	}
}
