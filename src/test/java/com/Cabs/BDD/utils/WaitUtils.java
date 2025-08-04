package com.Cabs.BDD.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.By; // Import By class
import java.time.Duration;

public class WaitUtils {
    private WebDriver driver;
    private WebDriverWait wait;
    private final int DEFAULT_TIMEOUT = 3; // Current default timeout

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(DEFAULT_TIMEOUT));
    }

    public void waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    // NEW METHOD: Waits for an element to be present in the DOM (not necessarily visible)
    public WebElement waitForElementToBePresent(By locator) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public void waitForElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public boolean isElementVisible(WebElement element) {
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
            return element.isDisplayed();
        } catch (org.openqa.selenium.TimeoutException | org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public void waitForElementToDisappear(WebElement element) {
        try {
            wait.until(ExpectedConditions.invisibilityOf(element));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Warning: Element " + element + " did not disappear within " + DEFAULT_TIMEOUT + " seconds. It might be a persistent element.");
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Element is already gone, which is fine for invisibilityOf
        }
    }

    public void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrupted during sleep", e);
        }
    }
}