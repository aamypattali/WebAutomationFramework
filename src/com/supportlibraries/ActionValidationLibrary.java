package com.supportlibraries;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.sourceforge.htmlunit.corejs.javascript.JavaScriptException;




public class ActionValidationLibrary extends DriverSetUp {
/**
 * @author Anjulakshmy.P.U
 */
	private static Logger logger = Logger.getLogger(ActionValidationLibrary.class.getName());
	int     defaultTimeOut = 30;
	private WebElement       webElement;
	private  WebDriverWait   wait=new  WebDriverWait(driver, defaultTimeOut);
	private Actions builder = new Actions(driver);
	


	/**
	 * method to wait for a particular webelement to be present on the dom
	 * 
	 * @param locatorName
	 * @throws Exception
	 */
	public void waitForElementToBePresent(By locator, String locatorName) throws Exception {
		try {
			logger.info("Waiting for webelement " + locatorName + " to be present on the page");
			wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		} catch (Exception E) {
			logger.error("Unable to find the presence of element "+locatorName+" on the page after waiting for " + defaultTimeOut+"\n"+E.getStackTrace());
			throw (new Exception(
					"Unable to find the presence of element "+locatorName+"on the page after waiting for " + defaultTimeOut));

		}
	}
	
	
	/**
	 * methoid to wait for a particular webelement to be visible on the dom
	 * @param locatorName
	 * @param locator
	 * @throws Exception 
	 */
	public void waitForElementToBeVisible(By locator ,String locatorName) throws Exception {
		try {
			logger.info("Waiting for webelement " + locatorName + " to be visible on the page");
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		} catch (Exception E) {
			logger.error("Unable to find the visibility of element "+locatorName+" on the page after waiting for " + defaultTimeOut+"\n"+E.getStackTrace());
			throw (new Exception(
					"Unable to find the visibility of element "+locatorName+"on the page after waiting for " + defaultTimeOut));

		}
	}
	
	
	
	/**
	 * methoid to wait for a particular webelement to be clickable on the dom
	 * @param locatorName
	 * @param locator
	 * @throws Exception 
	 */
	public void waitForElementToBeClickable(By locator ,String locatorName) throws Exception {
		try {
			logger.info("Waiting for webelement " + locatorName + " to be clickable on the page");
			wait.until(ExpectedConditions.elementToBeClickable(locator));
		} catch (Exception E) {
			logger.error("Unable to find the visibility of element "+locatorName+" on the page after waiting for " + defaultTimeOut+"\n"+E.getStackTrace());
			throw (new Exception(
					"Unable to find the visibility of element "+locatorName+"on the page after waiting for " + defaultTimeOut));

		}
	}
	
	
	
	
	
	
	/**
	 * Method to wait for a particular web element to be invisible on the dom
	 * 
	 * @param locatorName
	 * @param locator
	 * @throws Exception
	 */

	public void waitForElementToDisAppear(By locator, String locatorName) throws Exception {
		try {
			logger.info("Waiting for webelement " + locatorName + " to be invisible  on the page");
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		} catch (Exception E) {
			logger.error(
					"Web Element " + locatorName + " still visible on the page after waiting for " + defaultTimeOut+"\n"+E.getStackTrace());
			throw (new Exception(
					"Web Element " + locatorName + " still visible on the page after waiting for " + defaultTimeOut));

		}
	}

	/**
	 * method to scroll to a particular web element
	 * 
	 * @param locatorName
	 * @param locator
	 * @throws Exception 
	 */
	public void scrollTo(By locator, String locatorName) throws Exception {
		try {

			int y = getElement(locator, locatorName).getLocation().getY();
			int x = getElement(locator, locatorName).getLocation().getX();
			executeScript("window.scrollTo(" + x + "," + y + ");");
			logger.info("Scrolled to Web element " + locatorName);
		} catch (Exception e) {
			logger.error("Failed to scroll to Web element " + locatorName+"\n"+e.getStackTrace());
			throw(new Exception("Failed to scroll to Web element " + locatorName+"\n"+e.getMessage()));
		}
	}
	
	
	/**
	 * method to scroll to a particular web element
	 * 
	 * @param locatorName
	 * @param locator
	 * @throws Exception 
	 */
	public void scrollToPresent(By locator, String locatorName) throws Exception {
		try {

			int y = getElementPresent(locator, locatorName).getLocation().getY();
			int x = getElementPresent(locator, locatorName).getLocation().getX();
			executeScript("window.scrollTo(" + x + "," + y + ");");
			logger.info("Scrolled to Web element " + locatorName);
		} catch (Exception e) {
			logger.error("Failed to scroll to Web element " + locatorName+"\n"+e.getStackTrace());
			throw(new Exception("Failed to scroll to Web element " + locatorName+"\n"+e.getMessage()));
		}
	}
	
	
	
	/**
	 * Method to scroll to bottom of the page
	 * 
	 * @throws Exception
	 * 
	 */
	public void scrollToBottom() throws Exception {
		try {
			executeScript("window.scrollBy(0,10000)");
			logger.info("Scrolled to the bottom of page ");
		} catch (Exception e) {
			logger.error("Failed to Scroll to bottom of page " + "\n" + e.getStackTrace());
			throw (new Exception("Failed to Scroll to bottom of page "));
		}
	}

	/**
	 * 
	 * Method to scroll to the top of the page
	 * 
	 * @throws Exception
	 */

	public void scrollToTop() throws Exception {
		try {
			executeScript("window.scrollBy(0,-10000)");
			logger.info("Scrolled tothe  top of page ");
		} catch (Exception e) {
			logger.error("Failed to Scroll to the top of page " + "\n" + e.getStackTrace());
			throw (new Exception("Failed to Scroll to the top of page "+"\n"+e.getMessage()));
		}
	}

	/**
	 * 
	 * Method to scroll to the ,id of the page
	 * 
	 * @throws Exception
	 */

	public void scrollToMid() throws Exception {
		try {
			executeScript("window.scrollBy(0,-5000)");
			logger.info("Scrolled tothe  mid of page ");
		} catch (Exception e) {
			logger.error("Failed to Scroll to the mid of page " + "\n" + e.getStackTrace());
			throw (new Exception("Failed to Scroll to the mid of page "+"\n"+e.getMessage()));
		}
	}
	
	/**
	 * 
	 * Method to check if an alert is present on the web page
	 * @return
	 */
	@SuppressWarnings("unused")
	public boolean isAlertPresent() {
		try {
			logger.info("Checking for alert");
			Alert alert = driver.switchTo().alert();
			logger.info("Alert is present");
			return true;
		} catch (NoAlertPresentException e) {
			logger.info("Not Alert Present");
			return false;
		}
		
	}

	/**
	 * Method to click on a webelement after moving to that webelement
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */
	public void moveToElementAndClick(By locator, String locatorName) throws Exception {

		try {
			
            logger.info("clicking on webelement "+locatorName);
			builder.moveToElement(getElement(locator, locatorName)).click().perform();
			logger.info("clicked on webelement " + locatorName);
		} catch (Exception e) {
			logger.error("failed to click on " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("failed to click on " + locatorName + "\n" + e.getMessage()));

		}

	}
	
	
	/**
	 * Method to click on a webelement after moving to that webelement
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */
	public void moveToVisibleElement(By locator, String locatorName) throws Exception {

		try {
			
            logger.info("moving to webelement "+locatorName);
			builder.moveToElement(getElement(locator, locatorName)).perform();
			logger.info("moved to webelement " + locatorName);
		} catch (Exception e) {
			logger.error("failed to move on " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("failed to move on " + locatorName + "\n" + e.getMessage()));

		}

	}
	
	

	/**
	 * Method to click on a webelement after moving to that webelement
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */
	public void moveToElementPresent(By locator, String locatorName) throws Exception {

		try {
			
            logger.info("moving to webelement "+locatorName);
			builder.moveToElement(getElementPresent(locator, locatorName)).perform();
			logger.info("moved to webelement " + locatorName);
		} catch (Exception e) {
			logger.error("failed to move on " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("failed to move on " + locatorName + "\n" + e.getMessage()));

		}

	}
	
	
	
	
	
	
	/**
	 * Method to click on a webelement after hover on another web element
	 * 
	 * @param hoverLocator
	 * @param elementToBeClicked
	 * @param locatorName
	 * @throws Exception
	 */

	public void hoverToElementAndClick(By hoverLocator, By elementToBeClicked, String hoverLocatorName,
			String locatorName) throws Exception {

		try {
			logger.info("clicking on webelement "+locatorName);
			builder.moveToElement(getElement(hoverLocator, hoverLocatorName))
					.moveToElement(getElement(elementToBeClicked, locatorName)).click().build().perform();
			logger.info("clicked on webelement " + locatorName);
		} catch (Exception e) {
			logger.error("failed to click on " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("failed to click on " + locatorName + "\n" + e.getMessage()));

		}

	}
	
	
	/**
	 * Method to click on web element using java script
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */
	public void clickUsingJS(By locator, String locatorName) throws Exception {
		try {
			logger.info("clicking on webelement " + locatorName);

			((JavascriptExecutor) driver).executeScript("arguments[0].click();", getElement(locator, locatorName));
			logger.info("clicked on webelement " + locatorName);
		} catch (Exception e) {

			logger.error("Failed to click on the element : " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("Failed to click on the element : " + locatorName + "\n" + e.getMessage()));
		}

	}

	
	/**
	 * Method to perform a normal click on a locator
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */

	public void click(By locator, String locatorName) throws Exception {
		try {
			logger.info("clicking on webelement " + locatorName);
			waitForElementToBeClickable(locator, locatorName);
			getElement(locator, locatorName).click();
			logger.info("clicked on webelement " + locatorName);

		} catch (Exception e) {
			logger.error("failed to click on " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("failed to click on " + locatorName + "\n" + e.getMessage()));
		}

	}

	/**
	 * method to wait for a particular web element state changes from disabled to
	 * enable
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */
	public void waitForElementToGetEnabled(By locator, String locatorName) throws Exception {
		try {
			logger.info("waiting for element " + locatorName + "to be enabled");
			wait.until(ExpectedConditions.attributeToBe(locator, "disabled", "false"));
			logger.info("element " + locatorName + "is enabled");
		} catch (Exception e) {
			logger.error("Element " + locatorName + "is disabled" + "\n" + e.getStackTrace());
			throw (new Exception("Element " + locatorName + "is disabled" + "\n" + e.getMessage()));
		}

	}

	/**
	 * method to check if a web element is disabled
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public boolean isWebelementDisabled(By locator, String locatorName) throws Exception {
		try {
			logger.info("Checking ,if web element is disabled");
			boolean flag = getElement(locator, locatorName).getAttribute("disabled").equalsIgnoreCase("true");
			logger.info("the web element " + locatorName + " disabled :" + flag);
			return flag;
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}
	}

	
	/**
	 * method to check if a web element is enabled by using native selenium method
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public boolean isWebelementEnabled(By locator, String locatorName) throws Exception {
		try {
			logger.info("Checking ,if web element is enabled");
			boolean flag = getElement(locator, locatorName).isEnabled();
			logger.info("the web element " + locatorName + " enabled :" + flag);
			return flag;
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}

	}

	/**
	 * method to perform click on a web element by simulating enter key
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */

	public void clickUsingEnter(By locator, String locatorName) throws Exception {
		try {
			logger.info("Scrollng to webelement " + locatorName);
			scrollTo(locator, locatorName);
			logger.info("pressing 'Enter Key' on webelement " + locatorName);
			getElement(locator, locatorName).sendKeys(Keys.ENTER);
			logger.info(" 'Enter Key' pressed on webelement " + locatorName);
		} catch (Exception e) {
			logger.error("Could not click on web element " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("Could not click on web element " + locatorName + "\n" + e.getMessage()));
		}

	}

	/**
	 * Method to type in to a text box or text area
	 * 
	 * @param text
	 * @param locatorName
	 * @param locator
	 * @throws Exception
	 */
	public void typeInToWebElement(String text, By locator, String locatorName) throws Exception {
		try {
			logger.info("clearing the values in side the Web element " + locatorName);
			getElement(locator, locatorName).clear();
			getElement(locator, locatorName).sendKeys(text);
			logger.info("Entered value in to the Web element " + locatorName);

		} catch (Exception e) {
			logger.error("failed to type in to  the Web element " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("failed to type in to  the Web element " + locatorName + "\n" + e.getMessage()));
		}
	}

	/**
	 * Method to type in to text area/text box
	 * 
	 * @param text
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */
	public void typeUsingControlKeys(String text, By locator, String locatorName) throws Exception {
		try {
			logger.info("clearing the values in side the Web element " + locatorName);
			getElement(locator, locatorName).sendKeys(Keys.chord(Keys.CONTROL, "a"));
			getElement(locator, locatorName).sendKeys(text);
			getElement(locator, locatorName).sendKeys(Keys.TAB);

		} catch (Exception e) {
			logger.error("failed to type in to  the Web element " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("failed to type in to  the Web element " + locatorName + "\n" + e.getMessage()));
		}
	}

	/**
	 * Method to check if webelement is editable
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public boolean isWebelementEditable(By locator, String locatorName) throws Exception {

		boolean flag = false;
		try {
			logger.info("Checking if web element " + locatorName + " is editable");
			getElement(locator, locatorName).clear();
			logger.info("web element " + locatorName + " is editable");
			flag = true;
			return flag;

		} catch (InvalidElementStateException e) {
			if (e.getMessage().contains("Element must be user-editable in order to clear it")) {
				flag = false;
				return flag;
			}
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}
		return flag;

	}
	
/**
 * method to check if an element is displayed
 * @param locator
 * @param locatorName
 * @return
 */
	public boolean isElementDisplayed(By locator, String locatorName) {
		try {
			return getElement(locator, locatorName).isDisplayed();

		} catch (Exception e) {
			return false;
		}

	}

	

	
	/**
	 * Method to check whether a locator is displayed in a webpage or not
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 */
	public boolean isElementDisplayedAfterSpecificTimeOut(By locator, String locatorName,int timeout) {
		try {
			WebDriverWait wait=new WebDriverWait(driver, timeout);
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			logger.info("Web element " + locatorName + " is not displayed on the page" );
			return false;
		}

	}
	

	

	/**
	 * Method to check whether the labels are displayed as expected
	 * 
	 * @param locatorName
	 * @throws Exception
	 */
	public boolean verifyLabelText(By locator, String locatorName, String expectedString) throws Exception {
		boolean flag;
		String actualText;
		try {
			logger.info("Verifying the  text value of web element " + locatorName + " actual value");
			waitForElementToBeVisible(locator, locatorName);
			actualText = getElement(locator, locatorName).getText();
			flag = actualText.contains(expectedString);
			logger.info("Expected  text value of web element " + expectedString + " actual value " + actualText);
			return flag;
		} catch (Exception e) {
			logger.error(
					"Unable to retrieve text value from the web element " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception(
					"Unable to retrieve text value from the web element " + locatorName + "\n" + e.getMessage()));
		}

	}


	/**
	 * Method to get text from a locator
	 * 
	 * @param locatorName
	 * @return true/false
	 * @throws Exception
	 */
	public String getTextFromWebElement(By locator, String locatorName) throws Exception {

		try {
			logger.info("getting text value of web element " + locatorName);
			return getElement(locator, locatorName).getText();

		} catch (NoSuchElementException e) {
			logger.info(e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}

	}

	/**
	 * Method to fetch the page title from a web page
	 * 
	 * @return pageTitle
	 * @throws Exception
	 */
	public String getPageTitle() throws Exception {
		try {
			logger.info("getting page title");
			logger.info("Page title is " + driver.getTitle());
			return driver.getTitle();
		} catch (Exception e) {
			logger.error("unable to fetch page title \n" + e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}
	}

	/**
	 * Method to fetch current URL from the webpage
	 * 
	 * @return currentURL
	 * @throws Exception 
	 */
	public String getPageURL() throws Exception {
		try {
			logger.info("getting page url");
			logger.info("Page url is " + driver.getCurrentUrl());
			return driver.getCurrentUrl();
		} catch (Exception e) {
			logger.error("unable to fetch page url \n" + e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}
	}

	/**
	 * Method to wait for x seconds
	 * 
	 * @param timetowait
	 *            in seconds
	 * @return true/false
	 * @throws Exception 
	 */
	public void wait(int waitTimeInSeconds) throws Exception {
		try {
			logger.info("pausing execution for "+waitTimeInSeconds);
			Thread.sleep(waitTimeInSeconds * 1000);
			
		} catch (Exception e) {
			
			logger.error(e.getStackTrace());
			throw(new Exception());
		}
	}

	/**
	 * Enum defining various keyboard keys
	 * 
	 */
	public enum KEYBOARDKEYS {
		ENTER, TAB, SPACE, ESCAPE, ARROW_DOWN, ARROW_UP
	}

	/**
	 * Method to perform key press on a webpage
	 * 
	 * @param locator
	 * @param locatorName
	 * @param keyName
	 * @throws Exception
	 */
	public void pressKeyboardKeys(By locator, String locatorName, String keyName) throws Exception {

		try {
			logger.info("performing key board action '"+keyName +"'on Weblement "+locatorName);
			switch (KEYBOARDKEYS.valueOf(keyName.toUpperCase())) {
			case ENTER:
				
				getElement(locator, locatorName).sendKeys(Keys.ENTER);
				break;

			case TAB:
				
				getElement(locator, locatorName).sendKeys(Keys.TAB);
				break;

			case SPACE:
				getElement(locator, locatorName).sendKeys(Keys.SPACE);
				break;

			case ESCAPE:
				getElement(locator, locatorName).sendKeys(Keys.ESCAPE);
				break;

			case ARROW_DOWN:
				getElement(locator, locatorName).sendKeys(Keys.ARROW_DOWN);
				break;

			case ARROW_UP:
				getElement(locator, locatorName).sendKeys(Keys.ARROW_UP);
				break;

			}
			logger.info("performed key board action '"+keyName +"'on Weblement "+locatorName);
		} catch (Exception E) {
			logger.error("Unable to perform key board action '" + keyName + "' on webelement" + locatorName + "\n"
					+ E.getStackTrace());
			throw (new Exception("Unable to perform key board action '" + keyName + "' on webelement" + locatorName
					+ "\n" + E.getMessage()));
		}
	}

	/**
	 * Method to maximize window in a web browser
	 * 
	 * 
	 */
	public void windowMaximize() {
		try {
			driver.manage().window().maximize();
			
		} catch (Exception e) {
			logger.error("Exception occured while maximizing window."+"\n"+e.getStackTrace());
			
		}

	}

	/**
	 * Method to fetch the page source in a webpage
	 * 
	 * @return pageSource in a webpage
	 */
	public String getPageSource() {
		try {
			return driver.getPageSource();
		} catch (Exception e) {
			logger.error("Exception occured while fetching PageSource"+"\n"+e.getStackTrace());
			return null;
		}

	}

	/**
	 * Method to hover mouse above a locator
	 * 
	 * @param locatorName
	 * @return true/false
	 * @throws Exception
	 */
	public void mouseOver(By locator, String locatorName) throws Exception {

		try {

			if (isSafari() || isIE()) {
				
				logger.info("hovering on to webelement " + locatorName);
				hoverMouseUsingJS(locator,locatorName);
			} else {
				logger.info("hovering on to webelement " + locatorName);
				Actions action = new Actions(driver);
				action.moveToElement(getElement(locator, locatorName));
				action.perform();
			}
		} catch (Exception e) {
			logger.error("Exception occured in while hovering on to webelement : " + locatorName+"\n"+e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}

	}

	/**
	 * Method to perform double click on a locator
	 * 
	 * @param locatorName
	 * 
	 * @throws Exception
	 */
	public void doubleClick(By locator, String locatorName) throws Exception {
		try {
			logger.info("peforming double click on  webelement " + locatorName);

			Actions action = new Actions(driver);
			action.doubleClick((getElement(locator, locatorName)));
			action.perform();

		} catch (Exception e) {
			logger.error("Exception occured while doubleClicking. webelement : " + locatorName+"\n"+e.getStackTrace());
			throw (new Exception("unable to double click on web element " + locatorName+"\n"+e.getMessage()));
		}

	}

	

	/**
	 * Method to refresh the current webpage
	 * @throws Exception 
	 * 
	 */
	public void refresh() throws Exception {
		try {
			logger.info("refreshing the current page");
			driver.navigate().refresh();
		} catch (Exception e) {
			logger.error("Exception occured while refreshing web page\n"+e.getStackTrace());
			throw(new Exception(e.getMessage()));
			
		}
	}

	
	/**
	 * Method to select value from the dropdown
	 * 
	 * @param locator
	 * @param locatorName
	 * @param optionValue
	 * @throws Exception
	 */

	public void selectValueFromDropDownByValue(By locator, String locatorName, String optionValue) throws Exception {
		try {

			logger.info("Selecting value " + optionValue + " from dropdown " + locatorName);
			Select select = new Select(getElement(locator, locatorName));
			select.selectByValue(optionValue);
		} catch (Exception e) {
			logger.error("failed to Select value " + optionValue + " from dropdown " + locatorName + "\n"
					+ e.getStackTrace());

			throw (new Exception("Exception occured while selecting value ' " + optionValue + " from dropdown "
					+ locatorName + "\n" + e.getMessage()));

		}

	}
	
	
	/**
	 * Method to select value from the dropdown
	 * 
	 * @param locator
	 * @param locatorName
	 * @param optionValue
	 * @throws Exception
	 */

	public void selectValueFromDropDownByText(By locator, String locatorName, String text) throws Exception {
		try {

			logger.info("Selecting value " + text + " from dropdown " + locatorName);
			Select select = new Select(getElement(locator, locatorName));
			select.selectByVisibleText(text);
		} catch (Exception e) {
			logger.error("failed to Select value " + text + " from dropdown " + locatorName + "\n"
					+ e.getStackTrace());

			throw (new Exception("Exception occured while selecting value ' " + text + " from dropdown "
					+ locatorName + "\n" + e.getMessage()));

		}

	}
	
	
	/**
	 * Method to select value from the dropdown
	 * 
	 * @param locator
	 * @param locatorName
	 * @param optionValue
	 * @throws Exception
	 */

	public void selectValueFromDropDownByIndex(By locator, String locatorName, int index) throws Exception {
		try {

			logger.info("Selecting index " + index + " from dropdown " + locatorName);
			Select select = new Select(getElement(locator, locatorName));
			select.selectByIndex(index);;
		} catch (Exception e) {
			logger.error("failed to Select index " + index + " from dropdown " + locatorName + "\n"
					+ e.getStackTrace());

			throw (new Exception("Exception occured while selecting index ' " + index + " from dropdown "
					+ locatorName + "\n" + e.getMessage()));

		}

	}
	
	
	
	

	/**
	 * Method to check whether a radio button is selected
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public boolean isRadioButtonSelected(By locator, String locatorName) throws Exception {
		try {
			logger.info("validating if raido button " + locatorName + " is selected");
			return getElement(locator, locatorName).isSelected();
		} catch (Exception e) {
			logger.error("Exception occured " + e.getStackTrace());

			throw (new Exception(e.getMessage()));

		}

	}

	
	/**
	 * Method to verify whether a check box is selected
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception 
	 */
	public boolean isCheckBoxSelected(By locator ,String locatorName) throws Exception {
		try {
			logger.info("validating if check box button " + locatorName + " is selected");
			
				return getElement(locator,locatorName)
						.isSelected();
			
		} catch (Exception e) {
			logger.error("Exception occured " + e.getStackTrace());

			throw (new Exception(e.getMessage()));
		}
		
	}

	/**
	 * 
	 * @param locator
	 * @param locatorName
	 * @param attribute
	 * @return
	 * @throws Exception 
	 */
	public String getAttribute(By locator, String locatorName, String attribute) throws Exception {
		try {
			logger.info("fetching " + attribute + " attributte value of weblememnt" + locatorName);
			return getElement(locator, locatorName).getAttribute(attribute);
		} catch (Exception e) {
			logger.error("Exception occured while fetching Attribute of webelement : " + locatorName);
			throw (new Exception(e.getMessage()));

		}

	}

	/**
	 * Method to move focus to a new frame in the webpage
	 * @param frame
	 * @param locatorName
	 * @throws Exception
	 */
	public void switchToFrame(By frame,String locatorName) throws Exception {
		try {
			logger.info("switching to frame "+locatorName);
			driver.switchTo().frame(getElement(frame, locatorName));
			
		} catch (Exception e) {
			logger.error("Exception occured while switching ToFrame. Locator : "
					+ locatorName+"\n"+e.getStackTrace());
			throw(new Exception("unable to switch to frame "+locatorName+"\n"+e.getMessage()));
		}
	}

	
	/**
	 * Method to move focus to a new frame in the webpage
	 * @param locatorName
	 * @param index
	 * @throws Exception
	 */
	public void switchToFrameByIndex(String locatorName,int index) throws Exception {
		try {
			logger.info("switching to frame "+locatorName);
			driver.switchTo().frame(index);
			
		} catch (Exception e) {
			logger.error("Exception occured while switching ToFrame. Locator : "
					+ locatorName+"\n"+e.getStackTrace());
			throw(new Exception("unable to switch to frame "+locatorName+"\n"+e.getMessage()));
		}
	}
	
	
	
	/**
	 * Method to move focus to a new frame in the webpage
	 * @param locatorName
	 * @param frameName
	 * @throws Exception
	 */
	public void switchToFrameByName(String locatorName,String frameName) throws Exception {
		try {
			logger.info("switching to frame "+locatorName);
			driver.switchTo().frame(frameName);
			
		} catch (Exception e) {
			logger.error("Exception occured while switching ToFrame. Locator : "
					+ locatorName+"\n"+e.getStackTrace());
			throw(new Exception("unable to switch to frame "+locatorName+"\n"+e.getMessage()));
		}
	}
	
	public void switchToDefaultContent() throws Exception {
		try {
			logger.info("switching back to default window");
		driver.switchTo().defaultContent();
		}
		catch(Exception e ) {
			logger.error("failed to switch back to default window"+"\n"+e.getStackTrace());
			throw(new Exception("failed to switch back to default window"+"\n"+e.getMessage()));
		}
	}
	

	/**
	 * Method to select a checkbox
	 * 
	 * @param locator
	 * @param locatorName
	 * @throws Exception
	 */
	public void selectCheckBox(By locator, String locatorName) throws Exception {
		try {
			logger.info("Selecting checkbox " + locatorName);
			getElement(locator, locatorName).click();

		} catch (Exception e) {
			logger.error("Exception occured while selecting CheckBox.  : " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("Unable to select checkbox " + locatorName + "\n" + e.getMessage()));
		}
	}

	/**
	 * Method to deselect an already selected checkbox
	 * 
	 * @param locatorName
	 * @return true/false
	 * @throws Exception 
	 */
	public void deselectCheckBox(By locator,String locatorName) throws Exception {
		try {
			logger.info("Deselecting check box "+locatorName);
			if (isCheckBoxSelected(locator,locatorName)) {
				getElement(locator,locatorName).click();
				
			}
			
		} catch (Exception e) {
			logger.error("Exception occured while deselcting check box : "
					+ locatorName+"\n"+e.getStackTrace());
			throw(new Exception("Exception occured while deselcting check box :"+locatorName+"\n"+e.getMessage()));
			
		}
	}

	

	

	

	/**
	 * Method to clear the browser cache
	 */
	public void clearCache() {
		try {
			logger.info("clearing the cache");
			Runtime.getRuntime().exec(
					"RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
		} catch (IOException e) {
			logger.error("Exception occured while clearing the cache");
			// e.printStackTrace();
		}
	}

	

	/**
	 * Enum defined for various browser types
	 */
	public enum BROWSER {
		FIREFOX, ANDROID, GOOGLECHROME, IPAD, IPHONE, IE, CHROME, IEXPLORE, SAFARI
	};

	

	/**
	 * Method to get the default selected value in dropdown box
	 * 
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */

	public String getDefaultSelected(By locator, String locatorName) throws Exception {
		try {
			Select select = new Select(getElement(locator, locatorName));
			return select.getFirstSelectedOption().getText();
		} catch (Exception e) {
			logger.error("unable to retrieve the default selected value fro drop down " + locatorName + "\n"
					+ e.getStackTrace());
			throw (new Exception(
					"unable to retrieve the default selected value fro drop down" + "\n" + e.getMessage()));
		}
	}

	


	/**
	 * Method to perform hover mouse using java script
	 * 
	 * @param locatorName
	 * @throws Exception 
	 */
	public void hoverMouseUsingJS(By locator,String locatorName) throws Exception {
		try {
			logger.info("hovering to webelement "+locatorName+"Using javascript");
		int x = getElement(locator,locatorName)
				.getLocation().getX();
		int y = getElement(locator,locatorName)
				.getLocation().getY();
		String javascriptToExecute = "window.jQuery(document.elementFromPoint("
				+ x + "," + y + ")).mouseover();";
		executeScript(javascriptToExecute);
		}
		catch(Exception e) {
		logger.error("unable to hover on webelement "+locatorName+"\n"+e.getStackTrace());	
		throw(new Exception("Unable to hover on webelement"+locatorName+"\n"+e.getMessage()));
		}
	}

	/**
	 * Method to execute javascript
	 * 
	 * @param javascriptToExecute
	 */
	private Object executeScript(String javascriptToExecute) {
		// System.out.println("Executing JavaScript : "+javascriptToExecute);
		return ((JavascriptExecutor) driver).executeScript(javascriptToExecute);
	}

	/**
	 * Method to identify the current browser
	 * 
	 * @return FF/IE
	 */
	public String identifyBrowserType() {
		if (driver instanceof FirefoxDriver) {
			return "firefox";
		} else if (driver instanceof ChromeDriver) {
			return "chrome";
		} else if (driver instanceof InternetExplorerDriver) {
			return "iexplore";
		} else if (driver instanceof SafariDriver) {
			return "safari";
		} else
			return "";
	}

	/**
	 * Check the current browser instance and returns true if its IE
	 * 
	 * @return
	 */
	public boolean isIE() {
		if (identifyBrowserType().equalsIgnoreCase("IEXPLORE")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the current browser instance and returns true if its Chrome
	 * 
	 * @return
	 */
	public boolean isChrome() {
		if (identifyBrowserType().equalsIgnoreCase("CHROME")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the current browser instance and returns true if its safari
	 * 
	 * @return
	 */
	public boolean isSafari() {
		if (identifyBrowserType().equalsIgnoreCase("SAFARI")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check the current browser instance and returns true if its Firefox
	 * 
	 * @author skrishnan
	 * @return
	 */
	public boolean isFF() {
		if (identifyBrowserType().equalsIgnoreCase("FIREFOX")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * method to return list of web elements
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public List<WebElement> getListOfWebElementsVisible(By locator,String locatorName ) throws Exception {
		try {
		return getElementsVisible(locator,locatorName);
		}
		catch(Exception e) {
			logger.error(e.getStackTrace());
			throw(new Exception(e.getMessage()));
		}
	}
	
	
	/**
	 * method to return list of web elements
	 * @param locator
	 * @param locatorName
	 * @return
	 * @throws Exception
	 */
	public List<WebElement> getListOfWebElementsPresent(By locator,String locatorName ) throws Exception {
		try {
		return getElementsPresent(locator,locatorName);
		}
		catch(Exception e) {
			logger.error(e.getStackTrace());
			throw(new Exception(e.getMessage()));
		}
	}
	
	
	
	
	/**
	 * @throws Exception
	 * 
	 */
	private List<WebElement>getElementsVisible(By locator, String locatorName) throws Exception {
		try {
			
			List<WebElement> elementList = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
			
			return elementList;
		}

		catch (ElementNotVisibleException e) {
			logger.error("Elements - " + locatorName + " were not visible " + "\n" + e.getStackTrace());
			throw (new Exception("Elements - " + locatorName + " were not visible " + "\n" + e.getMessage()));

		} catch (NoSuchElementException e) {
			logger.error("Elements - " + locatorName + " were not found in the page" + "\n" + e.getStackTrace());
			throw (new Exception("Elements - " + locatorName + " were not found in the page" + "\n" + e.getMessage()));

		} catch (StaleElementReferenceException e) {
			logger.error("Elements - " + locatorName + " are no longer available on the page" + "\n" + e.getStackTrace());
			throw (new Exception(
					"Elements - " + locatorName + " are no longer available on the page" + "\n" + e.getMessage()));
		} catch (TimeoutException e) {
			logger.error("Time out exception encounterd : " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("Time out exception encounterd : " + locatorName + "\n" + e.getMessage()));
		} catch (Exception e) {
			logger.error("Unable to locate the weblements " + locatorName + "with locator " + locator.toString());
			throw (new Exception(
					"Unable to locate the weblements " + locatorName + "with locator " + locator.toString()));
		}
	}
	
	/**
	 * @throws Exception
	 * 
	 */
	private List<WebElement>getElementsPresent(By locator, String locatorName) throws Exception {
		try {
			
			List<WebElement> elementList = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
			
			return elementList;
		}

		catch (ElementNotVisibleException e) {
			logger.error("Elements - " + locatorName + " were not visible " + "\n" + e.getStackTrace());
			throw (new Exception("Elements - " + locatorName + " were not visible " + "\n" + e.getMessage()));

		} catch (NoSuchElementException e) {
			logger.error("Elements - " + locatorName + " were not found in the page" + "\n" + e.getStackTrace());
			throw (new Exception("Elements - " + locatorName + " were not found in the page" + "\n" + e.getMessage()));

		} catch (StaleElementReferenceException e) {
			logger.error("Elements - " + locatorName + " are no longer available on the page" + "\n" + e.getStackTrace());
			throw (new Exception(
					"Elements - " + locatorName + " are no longer available on the page" + "\n" + e.getMessage()));
		} catch (TimeoutException e) {
			logger.error("Time out exception encounterd : " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("Time out exception encounterd : " + locatorName + "\n" + e.getMessage()));
		} catch (Exception e) {
			logger.error("Unable to locate the weblements " + locatorName + "with locator " + locator.toString());
			throw (new Exception(
					"Unable to locate the weblements " + locatorName + "with locator " + locator.toString()));
		}
	}
	
	
	
	
	
	
	
	
	
	

	/**
	 * @throws Exception
	 * 
	 */
	private WebElement getElement(By locator, String locatorName) throws Exception {
		try {
			webElement = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return webElement;
		}

		catch (ElementNotVisibleException e) {
			//logger.error("Element - " + locatorName + " was not visible " + "\n" + e.getStackTrace());
			throw (new Exception("Element - " + locatorName + " was not visible " + "\n" + e.getMessage()));

		} catch (NoSuchElementException e) {
			//logger.error("Element - " + locatorName + " was not found in the page" + "\n" + e.getStackTrace());
			throw (new Exception("Element - " + locatorName + " was not found in the page" + "\n" + e.getMessage()));

		} catch (StaleElementReferenceException e) {
			//logger.error("Element - " + locatorName + " is no longer available on the page" + "\n" + e.getStackTrace());
			throw (new Exception(
					"Element - " + locatorName + " is no longer available on the page" + "\n" + e.getMessage()));
		} catch (TimeoutException e) {
			//logger.error("Time out exception encounterd : " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("Time out exception encounterd : " + locatorName + "\n" + e.getMessage()));
		} catch (Exception e) {
			//logger.error("Unable to locate the weblement " + locatorName + "with locator " + locator.toString());
			throw (new Exception(
					"Unable to locate the weblement " + locatorName + "with locator " + locator.toString()));
		}
	}
	
	
	
	/**
	 * @throws Exception
	 * 
	 */
	private WebElement getElementPresent(By locator, String locatorName) throws Exception {
		try {
			webElement = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			return webElement;
		}

		catch (ElementNotVisibleException e) {
			//logger.error("Element - " + locatorName + " was not visible " + "\n" + e.getStackTrace());
			throw (new Exception("Element - " + locatorName + " was not visible " + "\n" + e.getMessage()));

		} catch (NoSuchElementException e) {
			//logger.error("Element - " + locatorName + " was not found in the page" + "\n" + e.getStackTrace());
			throw (new Exception("Element - " + locatorName + " was not found in the page" + "\n" + e.getMessage()));

		} catch (StaleElementReferenceException e) {
			//logger.error("Element - " + locatorName + " is no longer available on the page" + "\n" + e.getStackTrace());
			throw (new Exception(
					"Element - " + locatorName + " is no longer available on the page" + "\n" + e.getMessage()));
		} catch (TimeoutException e) {
			//logger.error("Time out exception encounterd : " + locatorName + "\n" + e.getStackTrace());
			throw (new Exception("Time out exception encounterd : " + locatorName + "\n" + e.getMessage()));
		} catch (Exception e) {
			//logger.error("Unable to locate the weblement " + locatorName + "with locator " + locator.toString());
			throw (new Exception(
					"Unable to locate the weblement " + locatorName + "with locator " + locator.toString()));
		}
	}

	
	
	
	
	
	
	

	/**
	 * Navigate to the previous page in browser
	 * 
	 * @throws Exception
	 */
	public void navigateBack() throws Exception {
		try {
			logger.info("navigating back to the previous window");
			driver.navigate().back();
		} catch (Exception e) {
			logger.error("Exception occured while navigating back " + "\n" + e.getStackTrace());
			throw (new Exception("Exception occured while navigating back" + "\n" + e.getMessage()));
		}
	}

	/**
	 * method to switch to window based on window name
	 * 
	 * @param windowName
	 * @throws Exception
	 */
	public void selectWindow(String windowName) throws Exception {
		try {
			logger.info("switching to window " + windowName);
			driver.switchTo().window(windowName);
		} catch (Exception e) {
			logger.error("Exception occured while switching to window " + windowName + "\n" + e.getStackTrace());
			throw (new Exception("Exception occured while switching to window " + windowName + "\n" + e.getMessage()));
		}
	}

	/**
	 * Returns all window handles
	 * @return
	 */
	public Set<String> getAllWindowNames() {
		return driver.getWindowHandles();
	}

	/*
	public boolean openNewTabAndShiftFocus(String URL) {

		Set<String> beforePopup = getAllWindowNames();
		openNewTab(URL);
		Set<String> afterPopup = getAllWindowNames();
		afterPopup.removeAll(beforePopup);
		// System.out.println(afterPopup.size());
		if (afterPopup.size() == 1) {
			selectWindow((String) afterPopup.toArray()[0]);
			initializeURL(URL);
			wait(5);
			return true;
		}
		return false;
	}*/

	

/**
 * method to open new tab
 * @param URL
 */
	public void openNewTab(String URL) {
		String script = "var d=document,a=d.createElement('a');a.target='_blank';a.href='%s';a.innerHTML='.';d.body.appendChild(a);return a";
		Object element = ((JavascriptExecutor) driver).executeScript(String
				.format(script, URL));
		if (element instanceof WebElement) {
			WebElement anchor = (WebElement) element;
			anchor.click();
			((JavascriptExecutor) driver).executeScript(
					"var a=arguments[0];a.parentNode.removeChild(a);", anchor);
		} else {
			throw new JavaScriptException(element, "Unable to open tab", 1);
		}
	}

	

	

	

	/**
	 * Method to get cookie value given the cookie name
	 * 
	 * @param cookieName
	 * @return cookie value
	 */
	public String getCookieWithName(String cookieName) {
		if (driver != null) {
			return driver.manage().getCookieNamed(cookieName).toString();
		}
		return null;
	}

	/**
	 * Method to clear all cookies from the browser
	 * 
	 *
	 */
	public void clearAllCookies() {
		if (driver != null)
			driver.manage().deleteAllCookies();
	}

	/**
	 * method to check the presence of a text in the page
	 * 
	 * @param expectedText
	 * @return
	 */
	public boolean verifyPageContainsText(String expectedText) {
		logger.info("verfying the text '" + expectedText + "' in the page");
		return driver.getPageSource().toLowerCase().contains(expectedText.toLowerCase());

	}
	
	/**
	 * method to check the presence of a text in the page
	 * 
	 * @param expectedText
	 * @return
	 * @throws Exception
	 */
	public boolean verifyPageContainsText(By locator, String locatorName, String expectedText) throws Exception {
		try {
			logger.info("verfying the text '" + expectedText + "' in the page");
			String actualText = getElement(locator, locatorName).getText();
			return expectedText.equalsIgnoreCase(actualText);
		} catch (Exception e) {
			logger.error(e.getStackTrace());
			throw (new Exception(e.getMessage()));
		}

	}
	

	

	/**
	 * method to accept sslCertificate
	 */
	public void acceptSSLCertificates() {
		try {
			executeScript("document.getElementById('overridelink').click()");
		} catch (Exception e) {

		}

	}

	

	
	public String getColour(By locator,String locatorName) throws Exception {
		try {
			logger.info("fetching the color of web element "+locatorName);
		
		WebElement element = getElement(locator, locatorName);
		String colour = Color.fromString(element.getCssValue("background-color"))
				.asHex();
		
		return colour;
	}
		catch(Exception e) {
			logger.error(e.getStackTrace());
			throw(new Exception(e.getMessage()));
		}
}
	
	
	
	public boolean checkImage(By image,String imageName) throws Exception {
		WebElement img=getElement(image, imageName);
		boolean imagePresent = (Boolean) ((JavascriptExecutor)driver).executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth != \"undefined\" && arguments[0].naturalWidth > 0", img);
		return imagePresent;
	}
	
	
	public boolean checkImageUsingResponseCode(By imageLocator, String locatorName) throws Exception {
		try {
			HttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(getAttribute(imageLocator, locatorName, "src"));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() != 200)
				return true;
			else
				return false;

		} catch (Exception e) {
			throw (new Exception(e.getMessage()));
		}
	}
	
	
	
	public int getResponseCode(String urlString) throws Exception {
		try {
			URL url = new URL(urlString);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();

			int code = connection.getResponseCode();
			return code;
		} catch (Exception e) {
			throw (new Exception(e.getMessage()));
		}
	}
	
	/**
	 * Checking if a specific attribute is present for an Element
	 * @pramod
	 * **/
	public boolean isAttributePresent(By locator, String loactorName, String attributeValue) throws Exception {
		boolean result = false;
		try {
			String value= getElement(locator, loactorName).getAttribute(attributeValue);
			if (value!=null) {
				result =true;
			}
			
		}catch(Exception e) {
			logger.error(e.getStackTrace());
			throw e;
		}
		return result;
	}
	
	/**
	 * Method for retrying clicking an element when stale element exception is thrown
	 * @Anjulakshmy
	 * **/
	public boolean retryingClick(By locator, String locatorName)  throws Exception{
	    boolean result = false;
	    int attempts = 0;
	    while(attempts < 8) {
	        try {
	        	logger.info("clicking on webelement " + locatorName);
				waitForElementToBeClickable(locator, locatorName);
				getElement(locator, locatorName).click();
				logger.info("clicked on webelement " + locatorName);
	            result = true;
	            break;
	        } catch(StaleElementReferenceException e) {
	        	
	        	logger.error("failed to click on " + locatorName + "\n" + e.getStackTrace());
	        	
	        }
	        attempts++;
	    }
	    return result;
	}
	
}

