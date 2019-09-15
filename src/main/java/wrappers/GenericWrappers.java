package wrappers;

import com.google.common.base.Function;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class GenericWrappers {

    public static void waitForPageLoad(WebDriver driver){
//        boolean bReturn = false;

        try {
            //can change the wait time as per need
            WebDriverWait wdWait = new WebDriverWait(driver, 600);

            //wait until loading completes within given time
            wdWait.until(new Function<WebDriver, Boolean>(){
                public Boolean apply(WebDriver driver){
                    System.out.println("Current Window State :"
                            +String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
                    return (Boolean) (((JavascriptExecutor) driver).executeScript("return (document.readyState == 'complete')"));
                }
            });
//            bReturn = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
//        return bReturn;
    }
}
