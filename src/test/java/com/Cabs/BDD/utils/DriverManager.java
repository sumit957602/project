package com.Cabs.BDD.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverManager {
    public static WebDriver driver;

    public static void initializeDriver() {
        try {
            System.setProperty("webdriver.chrome.driver", "C:\\Users\\Mohammad Talha\\Downloads\\chromedriver-win64\\chromedriver.exe");

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--disable-notifications");
            options.addArguments("--disable-popup-blocking");
            options.addArguments("--start-maximized");
            options.addArguments("--remote-allow-origins=*");

            driver = new ChromeDriver(options);
            driver.manage().window().maximize();

            System.out.println("✅ Browser initialized successfully");
        } catch (Exception e) {
            System.err.println("❌ Failed to initialize driver: " + e.getMessage());
            throw new RuntimeException("Driver initialization failed", e);
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            try {
                driver.quit();
                driver = null;
                System.out.println("✅ Browser closed successfully");
            } catch (Exception e) {
                System.err.println("Error closing driver: " + e.getMessage());
            }
        }
    }
}
