package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import wrappers.GenericWrappers;

public class BookingPage {
    WebDriver driver;
    WebDriverWait wdWait;

    public BookingPage(WebDriver driver){
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wdWait = new WebDriverWait(driver, 60);
        GenericWrappers.waitForPageLoad(driver);
    }

    @FindBy(xpath = "//*[@class='value']")
    private WebElement bookedHotelName;

    /**
     * Method to verify hotel name is same as in Hotel Selection and Booking Page
     * @param hotelNameInHotelauswahl hotel name from Hotel Selection page
     * @return boolean
     */
    public boolean verifyBookedHotelName(String hotelNameInHotelauswahl){
        boolean bReturn = false;
        try {
            wdWait.until(ExpectedConditions.visibilityOf(bookedHotelName));
            if (bookedHotelName.getText().contains(hotelNameInHotelauswahl))
                bReturn = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bReturn;
    }

    /**
     * Method to close all opeened browsers after test execution completed
     */
    public void quitBrowser(){
        driver.quit();
    }
}
