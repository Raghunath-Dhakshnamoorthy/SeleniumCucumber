package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import wrappers.GenericWrappers;

import java.util.List;

public class HomePage {
    WebDriver driver;
    JavascriptExecutor jse;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        jse = (JavascriptExecutor) driver;
        GenericWrappers.waitForPageLoad(driver);
    }

    @FindBy(id = "CybotCookiebotDialogBodyButtonAccept")
    private WebElement btn_cookieAccept;

    @FindBy(xpath = "(//*[@id='idestflat'])[1]")
    private WebElement input_search;

    @FindBy(xpath = "(//*[@id='idestflat'])[2]")
    private WebElement input_popupSearch;

    @FindBy(xpath = "//*[@class='confirmAC icon-zoom close-layer']")
    private WebElement btn_searchPopupClose;

    @FindBy(xpath = "//*[@class='datepicker-input datepicker-input-start']/preceding-sibling::div")
    private WebElement input_startDate;

    @FindBy(xpath = "//*[@class='datepicker-input datepicker-input-end']/preceding-sibling::div")
    private WebElement input_endDate;

    @FindBy(id = "travellerSummary")
    private WebElement input_travellerSummary;

    @FindBy(id = "adultCount")
    private WebElement input_adultCount;

    @FindBy(name = "children")
    private WebElement input_childCount;

    @FindBy(xpath = "//*[@id='travellerLayer']//*[@class='submit']/button")
    private WebElement btn_applyTraveller;

    @FindBy(id = "submit")
    private WebElement btn_searchSubmit;

    @FindAll(@FindBy(xpath = "//*[@class='months-wrapper']/span"))
    private List<WebElement> list_calMonthYearElements;

    @FindBy(xpath = "//*[@class='month-button month-button-next icon-arrow-right-bold']")
    private WebElement btn_calNextMonth;

    @FindAll(@FindBy(xpath = "//*[@class='datepicker-list']/div"))
    private List<WebElement> list_calDateElements;

    /**
     * Method to enter values in search box of home page
     * @param place place
     * @param startDate start date
     * @param endDate end date
     * @param guestCount number of guest
     * @return to Hotel Selection page
     */
    public HotelSelectionPage searchPauschalreise(String place, String startDate, String endDate, String guestCount){

        try {
            //enter the search values
            btn_cookieAccept.click();
            input_search.clear();
            input_search.click();
            input_popupSearch.sendKeys(place, Keys.ENTER);
            Thread.sleep(1000);
            input_startDate.click();
            //select the dates in date picker
            selectStartMonthInDatePicker(startDate);
            String startDateVal = startDate.split(" ", 2)[0];
            selectDateInDatePicker(startDateVal);
            String endDateVal = endDate.split(" ", 2)[0];
            selectDateInDatePicker(endDateVal);
            selectTraveller(guestCount);
            btn_searchSubmit.click();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new HotelSelectionPage(driver);
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

    /**
     * Select the guests in the traveller search box
     * @param guest number of guests
     */
    public void selectTraveller(String guest){
        try {
            String existingTravellerSummary = input_travellerSummary.getAttribute("value");
            String adultCount = guest.split(",")[0].split(" ")[0];
            String childCount = guest.split(",")[1].trim().split(" ")[0];
            if (!guest.equalsIgnoreCase(existingTravellerSummary)){
                input_travellerSummary.click();
                jse.executeScript("arguments[0].setAttribute('value', '"+adultCount+"')", input_adultCount);
                jse.executeScript("arguments[0].setAttribute('value', '"+childCount+"')", input_childCount);
                btn_applyTraveller.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
