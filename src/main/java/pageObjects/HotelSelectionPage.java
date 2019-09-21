package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import wrappers.GenericWrappers;

import java.util.List;
import java.util.Set;

public class HotelSelectionPage {
    WebDriver driver;
    WebDriverWait wdWait;
    public static String parentWindowHandle;
    public static String selectedHotelName;

    public HotelSelectionPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wdWait = new WebDriverWait(driver, 60);
        GenericWrappers.waitForPageLoad(driver);
        GenericWrappers.waitForInitialPageLoad(driver);
    }

    @FindBy(xpath = "//*[@class='datepicker-input datepicker-input-start']/preceding-sibling::div")
    private WebElement input_startDate;

    @FindBy(xpath = "//*[@class='datepicker-input datepicker-input-end']/preceding-sibling::div")
    private WebElement input_endDate;

    @FindAll(@FindBy(xpath = "//*[@class='months-wrapper']/span"))
    private List<WebElement> list_calMonthYearElements;

    @FindAll(@FindBy(xpath = "//*[@class='datepicker-list']/div"))
    private List<WebElement> list_calDateElements;

    @FindBy(xpath = "//*[@class='month-button month-button-next icon-arrow-right-bold']")
    private WebElement btn_calNextMonth;

    @FindBy(id = "submit")
    private WebElement btn_searchSubmit;

    @FindBy(id = "hotelsorting")
    private WebElement sortResult;

    @FindAll(@FindBy(xpath = "//*[@class='price-wrapper']//*[@class='price']"))
    private List<WebElement> list_hotelPriceValues;

    @FindBy(xpath = "//*[@id='hotelname-0']/a")
    private WebElement title_firstHotel;

    /**
     * Method to select/change new dates in Hotel Selection page
     * @param newStartDate new start date
     * @param newEndDate new end date
     */
    public void selectNewSearchDates(String newStartDate, String newEndDate){
        try {
            input_startDate.click();
            selectStartMonthInDatePicker(newStartDate);
            String startDateVal = newStartDate.split(" ", 2)[0];
            selectDateInDatePicker(startDateVal);
            String endDateVal = newEndDate.split(" ", 2)[0];
            selectDateInDatePicker(endDateVal);
            btn_searchSubmit.click();
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hotelauswahl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to select rating and review in Hotel Selection page
     * @param hotelType type of hotel
     * @param customerReview review
     * @param sortParam to sort the result
     */
    public void selectRatingReviewAndSort(String hotelType, String customerReview, String sortParam){
        try {
            //Get rating locator value from common map, select it and wait for page load
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hotelauswahl");
            String hotel = GenericWrappers.getRatingReviewLocator(hotelType);
            driver.findElement(By.xpath("//*[@data-name='"+hotel+"']")).click();
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hotelauswahl");
            //Get review locator value from common map, select it and wait for page load
            String review = GenericWrappers.getRatingReviewLocator(customerReview);
            WebElement reviewElem = driver.findElement(By.xpath("//*[@data-option='"+review+"']/.."));
            reviewElem.click();
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hotelauswahl");
            //sort the result and wait for page load
            GenericWrappers.waitForElementVisibility(driver, sortResult);
            new Select(sortResult).selectByValue(sortParam);
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hotelauswahl");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to verify results sorted with Price Descending in Hotel Selection page
     * @return boolean
     */
    public Boolean verifyPriceDescSortedResult(){
        boolean costliestHotel = false;
        try {
            int firstHotelPrice = Integer.parseInt(list_hotelPriceValues.get(0).getText().replace(".", "").replace(" €", ""));
            costliestHotel = true;
            for (WebElement eachHotel: list_hotelPriceValues.subList(1, list_hotelPriceValues.size())) {
                int hotelPrice = Integer.parseInt(eachHotel.getText().replace(".", "").replace(" €", ""));
                //if hotel is not costliest
                if (firstHotelPrice < hotelPrice)
                    costliestHotel = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return costliestHotel;
    }

    /**
     * Method to select the first hotel from search result in Hotel Selection page
     * @return
     */
    public HolidayPackagePage selectCostliestHotel(){
        try {
            //store the hotel name for validating it later in Booking page
            selectedHotelName = title_firstHotel.getAttribute("title");
            //Verify and instruct selenium to switch to correct window
            parentWindowHandle = driver.getWindowHandle();
            title_firstHotel.click();
            Set<String> allWindowHandles = driver.getWindowHandles();
            for (String eachHandle: allWindowHandles) {
                if (!eachHandle.equalsIgnoreCase(parentWindowHandle)){
                    driver.switchTo().window(eachHandle);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HolidayPackagePage(driver);
    }

    /**
     * Common method to select month in date picker
     * @param date day with month
     */
    public void selectStartMonthInDatePicker(String date){
        String monthYear = date.split(" ", 2)[1];
        try {
            for (WebElement eachElem: list_calMonthYearElements) {
                if (eachElem.getAttribute("class").equalsIgnoreCase("hidden"))
                    continue;
                else{
                    String uiElementVal = eachElem.getText();
                    if (monthYear.equalsIgnoreCase(uiElementVal)) {
                        break;
                    } else {
                        btn_calNextMonth.click();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Common method to select date in date picker
     * @param dateVal date
     */
    public void selectDateInDatePicker(String dateVal){
        try {
            for (WebElement daysElemet: list_calDateElements) {
                if (daysElemet.getAttribute("class").contains("hidden"))
                    continue;
                else{
                    List<WebElement> list_RowElements = daysElemet.findElements(By.tagName("tr"));
                    for (WebElement eachRow: list_RowElements.subList(1,list_RowElements.size())) {
                        List<WebElement> list_cellElements = eachRow.findElements(By.tagName("td"));
                        for (WebElement eachCell: list_cellElements) {
                            String cellValue = eachCell.getText();
                            if (dateVal.equalsIgnoreCase(cellValue)){
                                eachCell.click();
                                break;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
