package com.Cabs.BDD.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import com.Cabs.BDD.utils.WaitUtils;

public class CabDetailsPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    // --- WebElements for Cab Details Pages ---
    // VERIFIED: Pages have <h1> with text like "Mini Cars", "Micro Cars", etc.
    @FindBy(xpath = "//h1[text()='Mini Cars']")
    private WebElement miniCarsHeader;
    @FindBy(xpath = "//h1[text()='Micro Cars']")
    private WebElement microCarsHeader;
    @FindBy(xpath = "//h1[text()='Sedan Cars']")
    private WebElement sedanCarsHeader;
    @FindBy(xpath = "//h1[text()='Suv Cars']")
    private WebElement suvCarsHeader;

    // VERIFIED: Back link has class="back".
    @FindBy(className = "back") // This will be passed to jsClick
    private WebElement backLink;

    public CabDetailsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // Helper for JavaScript clicks (used for back button)
    private void jsClick(WebElement element) {
        // Wait for the element to be present in the DOM before trying JS click.
        // This addresses NoSuchElementException for the back button.
        waitUtils.waitForElementToBePresent(By.xpath("//a[@class='back' and text()='Back']")); // Find by XPath directly for Back button
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // --- Page Load Verification Methods ---
    public boolean isMiniPageLoaded() {
        return waitUtils.isElementVisible(miniCarsHeader);
    }

    public boolean isMicroPageLoaded() {
        return waitUtils.isElementVisible(microCarsHeader);
    }

    public boolean isSedanPageLoaded() {
        return waitUtils.isElementVisible(sedanCarsHeader);
    }

    public boolean isSuvPageLoaded() {
        return waitUtils.isElementVisible(suvCarsHeader);
    }

    // --- Actions ---
    public void clickBackLink() {
        // Use the jsClick helper method, passing the @FindBy element.
        jsClick(backLink);
    }
}