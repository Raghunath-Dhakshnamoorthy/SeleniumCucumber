package pageObjects;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import sun.security.jgss.wrapper.GSSNameElement;
import wrappers.GenericWrappers;

import java.util.List;

public class HomePage {
    WebDriver driver;
    JavascriptExecutor jse;
    WebDriverWait wdWait;

    public HomePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        jse = (JavascriptExecutor) driver;
        wdWait = new WebDriverWait(driver, 30);
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

    public HotelSelectionPage searchPauschalreise(String place, String startDate, String endDate, String guestCount){

        try {
            wdWait.until(ExpectedConditions.elementToBeClickable(btn_cookieAccept));
            btn_cookieAccept.click();
            input_search.clear();
            input_search.click();
            input_popupSearch.sendKeys(place, Keys.ENTER);
//            Thread.sleep(1000);
//            btn_searchPopupClose.click();
            input_startDate.click();
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

    public void selectStartMonthInDatePicker(String date){
        String monthYear = date.split(" ", 2)[1];
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
    }

    public void selectDateInDatePicker(String dateVal){
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
    }

    public void selectTraveller(String guest){

        String existingTravellerSummary = input_travellerSummary.getAttribute("value");
        String adultCount = guest.split(",")[0].split(" ")[0];
        String childCount = guest.split(",")[1].trim().split(" ")[0];
        if (!guest.equalsIgnoreCase(existingTravellerSummary)){
            input_travellerSummary.click();
            jse.executeScript("arguments[0].setAttribute('value', '"+adultCount+"')", input_adultCount);
            jse.executeScript("arguments[0].setAttribute('value', '"+childCount+"')", input_childCount);
            btn_applyTraveller.click();
        }
    }
}
