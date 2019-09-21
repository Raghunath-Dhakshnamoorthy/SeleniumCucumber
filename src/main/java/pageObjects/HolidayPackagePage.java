package pageObjects;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import wrappers.GenericWrappers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HolidayPackagePage {
    WebDriver driver;

    public HolidayPackagePage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        GenericWrappers.waitForPageLoad(driver);
        GenericWrappers.waitForAjaxLoad(driver, "hoteldetails");
    }

    @FindBy(xpath = "(//*[@id='departureTimeRange']//*[@role='slider'])[1]")
    private WebElement slider_departureFrom;

    @FindBy(xpath = "(//*[@id='departureTimeRange']//*[@role='slider'])[2]")
    private WebElement slider_departureTill;

    @FindBy(xpath = "(//*[@id='returnTimeRange']//*[@role='slider'])[1]")
    private WebElement slider_returnFrom;

    @FindBy(xpath = "(//*[@id='returnTimeRange']//*[@role='slider'])[2]")
    private WebElement slider_returnTill;

    @FindBy(xpath = "//*[@id='offerFilter-arrival']//*[starts-with(@class,'offerFilter-listItem')]")
    private WebElement checkbox_offerStartDate;

    @FindAll(@FindBy(xpath = "//*[@class='content']/div[@class='duration']"))
    private List<WebElement> list_allFlightDetails;

    @FindBy(xpath = "(//*[@class='flight-time']/span)[1]")
    private WebElement firstResultDepartureTime;

    @FindBy(xpath = "(//*[@class='flight-time']/span)[3]")
    private WebElement firstResultArrivalTime;

    @FindBy(xpath = "//*[@class='button-next link']")
    private WebElement btn_firstResultForBooking;

    /**
     * Method to move the departure and arrival time range slider in Holiday package page
     * @param departFromOffset offset value
     * @param departTillOffset offset value
     * @param arrivalFromOffset offset value
     * @param arrivalTillOffset offset value
     */
    public void moveTimeRangeSlider(int departFromOffset, int departTillOffset, int arrivalFromOffset, int arrivalTillOffset){
        try {
            //move to slider area
            slider_departureTill.sendKeys(Keys.PAGE_DOWN);
            Thread.sleep(1000);
            //using actions class move the slider
            Actions builder = new Actions(driver);
            builder.dragAndDropBy(slider_departureFrom, departFromOffset, 0).build().perform();
            //once the slider is moved, wait for page to load
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hoteldetails");
            Thread.sleep(2000);
            builder.dragAndDropBy(slider_departureTill, -departTillOffset, 0).build().perform();
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hoteldetails");
            Thread.sleep(2000);
            builder.dragAndDropBy(slider_returnFrom, arrivalFromOffset, 0).build().perform();
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hoteldetails");
            Thread.sleep(2000);
            builder.dragAndDropBy(slider_returnTill, -arrivalTillOffset, 0).build().perform();
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hoteldetails");
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method for retieving direct flight options in Holiday package page
     * @return count of direct flights
     */
    public int countDirectFlightOptions(){
        int directFlightCount = 0;
        try {
            //select the "Anreisedatum" checkbox and wait for page load
            checkbox_offerStartDate.click();
            GenericWrappers.waitForPageLoad(driver);
            GenericWrappers.waitForAjaxLoad(driver, "hoteldetails");
            Thread.sleep(2000);
            //in each offers, check for direct flight option
            for (WebElement eachFlight: list_allFlightDetails) {
                String flightDetail = eachFlight.getText();
                if (flightDetail.contains("Direktflug")){
                    directFlightCount++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directFlightCount;
    }

    /**
     * Method to verify whether first journey in the offers list falls within selected time range in Holiday package page
     * @param filterDepartFrom filtered time range
     * @param filterDepartTill filtered time range
     * @param filterArrivalFrom filtered time range
     * @param filterArrivalTill filtered time range
     * @return boolean
     */
    public boolean verifyFlightsWithinTimeRange(int filterDepartFrom, int filterDepartTill, int filterArrivalFrom, int filterArrivalTill){
        boolean bReturn = false;
        try {
            //get time value from webpage and split it
            int departFromTime = splitFlightTime(firstResultDepartureTime.getText()).get(0);
            int departTillTime = splitFlightTime(firstResultDepartureTime.getText()).get(1);
            int arrivalFromTime = splitFlightTime(firstResultArrivalTime.getText()).get(0);
            int arrivalTillTime = splitFlightTime(firstResultArrivalTime.getText()).get(1);
            //verify time values falls within filtered time range
            if (departFromTime >= filterDepartFrom && departTillTime <= filterDepartTill && arrivalFromTime >= filterArrivalFrom && arrivalTillTime <= filterArrivalTill)
                bReturn = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bReturn;
    }

    /**
     * Common method to split time as per results in Holiday package page
     * @param flightTime String value
     * @return List of integer with flight timings
     */
    public List<Integer> splitFlightTime(String flightTime){
        List<Integer> list_FromTillTime = new ArrayList<Integer>();
        String[] timeArray = flightTime.split("-");
        int fromTime = Integer.parseInt(timeArray[0].split(":")[0]);
        int tillTime = Integer.parseInt(timeArray[1].trim().split(":")[0]);
        list_FromTillTime.add(0, fromTime);
        list_FromTillTime.add(1, tillTime);
        return list_FromTillTime;
    }

    /**
     * Method to select the first offer in Holiday package page
     * @return to Booking page
     */
    public BookingPage selectTheFirstOfferForBooking(){
        try {
            //get current window handle
            String currentWindowHandle = driver.getWindowHandle();
            GenericWrappers.waitForAjaxLoad(driver, "hoteldetails");
            GenericWrappers.waitForElementToBeClickable(driver, btn_firstResultForBooking);
            Thread.sleep(2000);
            btn_firstResultForBooking.click();
            Set<String> windowHandles = driver.getWindowHandles();
            for (String eachHandle: windowHandles) {
                //verify and make selenium to navigate to correct window
                if (!eachHandle.equalsIgnoreCase(currentWindowHandle) && !eachHandle.equalsIgnoreCase(HotelSelectionPage.parentWindowHandle)) {
                    driver.switchTo().window(eachHandle);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new BookingPage(driver);
    }
}
