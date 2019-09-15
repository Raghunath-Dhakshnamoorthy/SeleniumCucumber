package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wrappers.GenericWrappers;

import java.util.List;

public class HotelSelectionPage {
    WebDriver driver;
    WebDriverWait wdWait;

    public HotelSelectionPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wdWait = new WebDriverWait(driver, 30);
        GenericWrappers.waitForPageLoad(driver);
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

    @FindBy(xpath = "//*[@data-name='ab 4 Sternen']")
    private WebElement checkBox_4starRating;

    public void selectNewSearchDates(String newStartDate, String newEndDate){

        wdWait.until(ExpectedConditions.elementToBeClickable(input_startDate));
        input_startDate.click();
        selectStartMonthInDatePicker(newStartDate);
        String startDateVal = newStartDate.split(" ", 2)[0];
        selectDateInDatePicker(startDateVal);
        String endDateVal = newEndDate.split(" ", 2)[0];
        selectDateInDatePicker(endDateVal);
        btn_searchSubmit.click();
        GenericWrappers.waitForPageLoad(driver);
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
}
