package com.Cabs.BDD.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By; // Import By for dynamic XPath
import org.openqa.selenium.JavascriptExecutor; // Import for JavaScript click
import com.Cabs.BDD.utils.WaitUtils;
import java.util.List;

public class ServicesPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    // --- WebElements specific to the Services Page (VERIFIED FROM YOUR HTML) ---
    // The heading "Type of Cab Services" is inside an <h3> within a <th> with id="type".
    @FindBy(id = "type")
    private WebElement servicesPageHeader;

    // Service name links: <a> tags inside <td> with specific classes
    @FindBy(xpath = "//td[@class='mini']/a")
    private WebElement miniNameLink;

    @FindBy(xpath = "//td[@class='micro']/a")
    private WebElement microNameLink;

    @FindBy(xpath = "//td[@class='sedan']/a")
    private WebElement sedanNameLink;

    @FindBy(xpath = "//td[@class='suv']/a")
    private WebElement suvNameLink;

    // Seating capacity labels: <h3> tags inside <td> with specific classes (e.g., mini1).
    // The text to check is "2 Seater", "3 Seater" etc.
    @FindBy(xpath = "//td[@class='mini1']/h3")
    private WebElement miniSeatingCapacityLabel;
    @FindBy(xpath = "//td[@class='micro1']/h3")
    private WebElement microSeatingCapacityLabel;
    @FindBy(xpath = "//td[@class='sedan1']/h3")
    private WebElement sedanSeatingCapacityLabel;
    @FindBy(xpath = "//td[@class='suv1']/h3")
    private WebElement suvSeatingCapacityLabel;

    // The Back link on detail pages (e.g., mini.html, micro.html, etc.)
    // We will find this element dynamically within the clickBackLink method
    // as it was causing NoSuchElementException even with @FindBy.


    public ServicesPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // Helper method for JavaScript clicks
    private void jsClick(WebElement element) {
        // Now, this method will rely on the caller to ensure element presence/visibility.
        // For backLink, we'll use waitForElementToBePresent before calling this.
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public boolean isServicesPageDisplayed() {
        try {
            return waitUtils.isElementVisible(servicesPageHeader);
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            System.err.println("Services page header not found or visible. Error: " + e.getMessage());
            return false;
        }
    }

    public boolean areServiceTypesInCorrectOrder(List<List<String>> expectedServices) {
        boolean allCorrect = true;
        for (List<String> serviceData : expectedServices) {
            String serviceName = serviceData.get(0);
            String expectedSeatingCapacityText = serviceData.get(1);

            WebElement actualNameElement = null;
            WebElement actualSeatingCapacityElement = null;

            switch (serviceName.toLowerCase()) {
                case "mini":
                    actualNameElement = miniNameLink;
                    actualSeatingCapacityElement = miniSeatingCapacityLabel;
                    break;
                case "micro":
                    actualNameElement = microNameLink;
                    actualSeatingCapacityElement = microSeatingCapacityLabel;
                    break;
                case "sedan":
                    actualNameElement = sedanNameLink;
                    actualSeatingCapacityElement = sedanSeatingCapacityLabel;
                    break;
                case "suv":
                    actualNameElement = suvNameLink;
                    actualSeatingCapacityElement = suvSeatingCapacityLabel;
                    break;
                default:
                    System.err.println("Unknown service type in expected data: " + serviceName);
                    allCorrect = false;
                    continue;
            }

            // Check name link visibility and text
            if (actualNameElement != null) {
                if (!waitUtils.isElementVisible(actualNameElement) || !actualNameElement.getText().trim().equalsIgnoreCase(serviceName)) {
                    System.err.println("Service Name Link mismatch or not visible for: '" + serviceName + "'. Actual: '" + (actualNameElement != null && actualNameElement.isDisplayed() ? actualNameElement.getText().trim() : "Not Visible/No Text") + "'");
                    allCorrect = false;
                }
            } else {
                 System.err.println("Service name link element (@FindBy) not found for: " + serviceName);
                 allCorrect = false;
            }

            // Check seating capacity label visibility and text
            if (actualSeatingCapacityElement != null) {
                if (!waitUtils.isElementVisible(actualSeatingCapacityElement) || !actualSeatingCapacityElement.getText().trim().equalsIgnoreCase(expectedSeatingCapacityText)) {
                    System.err.println("Seating Capacity Label mismatch or not visible for: '" + serviceName + "'. Expected: '" + expectedSeatingCapacityText + "'. Actual: '" + (actualSeatingCapacityElement != null && actualSeatingCapacityElement.isDisplayed() ? actualSeatingCapacityElement.getText().trim() : "Not Visible/No Text") + "'");
                    allCorrect = false;
                }
            } else {
                 System.err.println("Seating capacity label element (@FindBy) not found for: " + serviceName);
                 allCorrect = false;
            }
        }
        return allCorrect;
    }

    public void clickMiniLink() {
        waitUtils.waitForElementToBeClickable(miniNameLink);
        miniNameLink.click();
    }

    public void clickMicroLink() {
        waitUtils.waitForElementToBeClickable(microNameLink);
        microNameLink.click();
    }

    public void clickSedanLink() {
        waitUtils.waitForElementToBeClickable(sedanNameLink);
        sedanNameLink.click();
    }

    public void clickSuvLink() {
        waitUtils.waitForElementToBeClickable(suvNameLink);
        suvNameLink.click();
    }

    // UPDATED: Use waitForElementToBePresent and JavaScript click for the Back link.
    public void clickBackLink() {
        // The locator for Back button is: //a[@class='back' and text()='Back']
        // Wait for the element to be *present in the DOM* (less strict than visibility)
        WebElement presentBackLink = waitUtils.waitForElementToBePresent(By.xpath("//a[@class='back' and text()='Back']"));
        // Then, perform a JavaScript click on the element once it's present.
        jsClick(presentBackLink);
    }

    public void clickServiceLink(String linkName) {
        WebElement link = driver.findElement(By.xpath("//a[text()='" + linkName + "']"));
        waitUtils.waitForElementToBeClickable(link);
        link.click();
    }

    // Checks if the specific Cab Details page is displayed
    public boolean isCabDetailsPageDisplayed(String cabType) {
        try {
            // VERIFIED: Cab details pages use <h1 class="cabtype" align="center">CabType Cars</h1>
            // This locator is now accurate based on the HTML you provided.
            // Using waitForElementToBePresent is less strict than isElementVisible for page load checks.
            // If the element is present, we consider the page opened.
            WebElement cabDetailsHeader = waitUtils.waitForElementToBePresent(By.xpath("//h1[text()='" + cabType + " Cars']"));
            return cabDetailsHeader != null && cabDetailsHeader.isDisplayed(); // Check if present and displayed
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            System.err.println("Cab details page header not found or visible for: '" + cabType + " Cars'. Error: " + e.getMessage());
            return false;
        }
    }
}