package wrappers;

import com.google.common.base.Function;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class GenericWrappers {
    static ResourceBundle config = ResourceBundle.getBundle("config");

    /**
     * Generic method makes the selenium script to wait for page load
     * @param driver Webdriver needs to be passed as a parameter
     */
    public static void waitForPageLoad(WebDriver driver){
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, 60);

            //wait until loading completes within given time
            wdWait.until(new Function<WebDriver, Boolean>(){
                public Boolean apply(WebDriver driver){
                    System.out.println("Current Window State :"
                            +String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
                    return (Boolean) (((JavascriptExecutor) driver).executeScript("return (document.readyState == 'complete')"));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generic method makes the selenium script to wait for specific ajax load in a page
     * @param driver Webdriver needs to be passed as a parameter
     * @param pageName Based on the page name locators will be called from config.properties file
     */
    public static void waitForAjaxLoad(WebDriver driver, String pageName){
        WebDriverWait wdWait = new WebDriverWait(driver, 60);
        int i=1;
        while (i==2) {
            try {
                wdWait.until(ExpectedConditions.not(ExpectedConditions.attributeContains(locatorSplit(config.getString(pageName)), "class", "refreshing")));
                break;
            } catch (TimeoutException e) {
                e.printStackTrace();
                driver.navigate().refresh();
                i++;
            }
        }
    }

    /**
     * Generic method makes the selenium script to wait for the initial page load
     * @param driver Webdriver needs to be passed as a parameter
     */
    public static void waitForInitialPageLoad(WebDriver driver){
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, 60);
            wdWait.until(ExpectedConditions.attributeToBe(By.id("ajaxLoadSkeleton"), "style", "display: none;"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generic method makes the selenium script to wait for the element to be visible
     * @param driver Webdriver needs to be passed as a parameter
     * @param element Webelement for which the script has to wait
     */
    public static void waitForElementVisibility(WebDriver driver, WebElement element){
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, 60);
            wdWait.until(ExpectedConditions.visibilityOf(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generic method makes the selenium script to wait for the element to be clickable
     * @param driver Webdriver needs to be passed as a parameter
     * @param element Webelement for which the script has to wait
     */
    public static void waitForElementToBeClickable(WebDriver driver, WebElement element){
        try {
            WebDriverWait wdWait = new WebDriverWait(driver, 60);
            wdWait.until(ExpectedConditions.elementToBeClickable(element));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generic method which is used to retrieve locator value for rating and review elements
     * @param userInput input provided by user in feature file
     * @return String
     */
    public static String getRatingReviewLocator(String userInput){
        return ratingAndReviewLocatorData(userInput).get(userInput);
    }

    /**
     * Generic method to give locator value dynamically
     * @param inputData input provided by user in feature file
     * @return Map
     */
    public static Map<String, String> ratingAndReviewLocatorData(String inputData){
        Map<String, String> ratingReviewMap = new LinkedHashMap<String, String>();

        if (inputData.contains("tar")){
            ratingReviewMap.put("2Star", "ab 2 Sternen");
            ratingReviewMap.put("3Star", "ab 3 Sternen");
            ratingReviewMap.put("4Star", "ab 4 Sternen");
            ratingReviewMap.put("5Star", "ab 5 Sternen");
        }
        else if (inputData.contains("eview")){
            ratingReviewMap.put("Arbitrary Review", "rating1");
            ratingReviewMap.put("Sufficient Review", "rating2");
            ratingReviewMap.put("Good Review", "rating3");
            ratingReviewMap.put("VeryGood Review", "rating4");
            ratingReviewMap.put("Excellent Review", "rating5");
        }
        return ratingReviewMap;
    }

    /**
     * Generic method based on the parameter actionType, retrieves Offset value for slider or time range
     * @param fromRange input from feature file
     * @param toRange input from feature file
     * @param actionType offset or timerange
     * @return List of time or offset values in integer
     */
    public static List<Integer> getSliderTimeRange(String fromRange, String toRange, String actionType){
        int fromTime = Integer.parseInt(fromRange.split(":")[0]);
        int toTime = Integer.parseInt(toRange.split(":")[0]);
        int fromOffset = 0;
        int toOffset = 0;
        List<Integer> list_TimeRange = new ArrayList<Integer>();
        if (actionType.equalsIgnoreCase("offset")) {
            if (fromTime < toTime) {
                for (int i = 1; i <= fromTime; i++) {
                    fromOffset = fromOffset + 10;
                }
                list_TimeRange.add(0, fromOffset);
                for (int j = 24; j > toTime; j--) {
                    toOffset = toOffset + 10;
                }
                list_TimeRange.add(1, toOffset);
            } else
                list_TimeRange = null;
        }
        else if (actionType.equalsIgnoreCase("timerange")){
            list_TimeRange.add(0, fromTime);
            list_TimeRange.add(1, toTime);
        }
        return list_TimeRange;
    }

    /**
     * Generic method to split the locator name(id, xpath etc) from value in config.properties file
     * @return By (with different locators)
     */
    public static By locatorSplit(String locatorVal){
        By bReturn = null;
        try {
            //split the locator value from its locator type
            String[] locator = locatorVal.split(" ", 2);

            //select the locator By based on the input
            switch (locator[0]) {
                case "id":
                    bReturn = By.id(locator[1]);
                    break;

                case "name":
                    bReturn = By.name(locator[1]);
                    break;

                case "class":
                    bReturn = By.className(locator[1]);
                    break;

                case "tag":
                    bReturn = By.tagName(locator[1]);
                    break;

                case "link":
                    bReturn = By.linkText(locator[1]);
                    break;

                case "partial":
                    bReturn = By.partialLinkText(locator[1]);
                    break;

                case "css":
                    bReturn = By.cssSelector(locator[1]);
                    break;

                case "xpath":
                    bReturn = By.xpath(locator[1]);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bReturn;
    }
}
