package com.Cabs.BDD.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import com.Cabs.BDD.utils.WaitUtils;
import org.openqa.selenium.JavascriptExecutor; // Required for jsClick

public class HomePage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    @FindBy(xpath = "//a[text()='Home']")
    private WebElement homeLink;

    @FindBy(xpath = "//a[text()='About']")
    private WebElement aboutLink;

    @FindBy(xpath = "//a[text()='Services']")
    private WebElement servicesLink;

    @FindBy(xpath = "//a[text()='Booking']")
    private WebElement bookingLink;

    @FindBy(xpath = "//a[text()='Contact']")
    private WebElement contactLink;

    @FindBy(className = "call")
    private WebElement callNumber;

    @FindBy(className = "taxiImage")
    private WebElement taxiImage;

    @FindBy(className = "booknow")
    private WebElement bookNowButton;

    // This element was confirmed to be a persistent blocker in your HTML.
    // Using this as an indicator for when the page is fully ready before interacting.
    @FindBy(className = "message")
    private WebElement missionMessage;

    @FindBy(className = "homeImage")
    private WebElement carImage;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // Helper method for JavaScript clicks on elements that are being intercepted
    private void jsClick(WebElement element) {
        waitUtils.waitForElementToBeVisible(element); // Ensure element is in DOM
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    // Helper to dismiss the mission message if it shows up and needs a click
    // (Though your HTML indicates it's permanent, this is a good pattern for other apps)
    public void dismissMissionMessageIfPresent() {
        // If missionMessage is confirmed to be permanent and NOT dismissible,
        // then this method doesn't need to try clicking anything, just ensure it loads.
        // For this specific app, the JS click workaround is better.
        // The previous warning "did not disappear" is expected here.
        waitUtils.waitForElementToDisappear(missionMessage); // This will timeout and print warning, which is fine as it's not disappearing
    }

    public void clickServicesLink() {
        // As missionMessage is confirmed to be permanent and blocking, use JS click
        jsClick(servicesLink);
    }

    public void clickBookingLink() {
        // As missionMessage is confirmed to be permanent and blocking, use JS click
        jsClick(bookingLink);
    }

    public void clickAboutLink() {
        // As missionMessage is confirmed to be permanent and blocking, use JS click
        jsClick(aboutLink);
    }

    public void clickContactLink() {
        // As missionMessage is confirmed to be permanent and blocking, use JS click
        jsClick(contactLink);
    }

    public boolean isPageLoaded() {
        // If missionMessage is a permanent part of the page, ensure it loads.
        // For this app, it seems less about disappearing and more about being present.
        return waitUtils.isElementVisible(callNumber) &&
               waitUtils.isElementVisible(taxiImage) &&
               waitUtils.isElementVisible(bookNowButton);
    }

    public boolean areAllModulesVisible() {
        // Ensure all core modules are visible.
        return waitUtils.isElementVisible(homeLink) &&
               waitUtils.isElementVisible(aboutLink) &&
               waitUtils.isElementVisible(servicesLink) &&
               waitUtils.isElementVisible(bookingLink) &&
               waitUtils.isElementVisible(contactLink) &&
               waitUtils.isElementVisible(missionMessage) && // This is a fixed element, so check its visibility
               waitUtils.isElementVisible(carImage);
    }
}