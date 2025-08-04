package com.Cabs.BDD.Hooks;

import io.cucumber.java.Before;
import io.cucumber.java.After;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Scenario;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.Cabs.BDD.utils.DriverManager;
import com.Cabs.BDD.utils.ExtentManager;
import com.Cabs.BDD.utils.ExtentTestManager;
import com.Cabs.BDD.utils.ScreenshotUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class TestHooks {
    private ExtentTest test;

    @BeforeAll
    public static void beforeAll() {
        System.out.println("🚀 BEFORE ALL: Initializing ExtentReports...");
        ExtentManager.getInstance();
        System.out.println("✅ BEFORE ALL: ExtentReports initialized successfully");
    }

    @Before
    public void setUp(Scenario scenario) {
        System.out.println("🚀 Starting scenario: " + scenario.getName());

        // Initialize ExtentTest for this scenario
        test = ExtentTestManager.startTest(scenario.getName());

        // Add scenario tags to ExtentTest
        if (!scenario.getSourceTagNames().isEmpty()) {
            test.assignCategory(scenario.getSourceTagNames().toArray(new String[0]));
        }

        // Initialize WebDriver
        DriverManager.initializeDriver();

        test.log(Status.INFO, "🚀 Browser initialized and scenario started");
        System.out.println("✅ Driver initialized successfully for: " + scenario.getName());
    }

    @After
    public void tearDown(Scenario scenario) {
        if (DriverManager.driver != null) {
            try {
                String scenarioName = scenario.getName().replaceAll(" ", "_");
                String screenshotPath = ScreenshotUtils.captureScreenshot(DriverManager.driver, scenarioName);

                if (scenario.isFailed()) {
                    // For Cucumber report
                    final byte[] screenshot = ((TakesScreenshot) DriverManager.driver)
                            .getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Failed Screenshot");

                    // For ExtentReports
                    if (test != null) {
                        test.log(Status.FAIL,
                                MarkupHelper.createLabel("❌ SCENARIO FAILED: " + scenario.getName(), ExtentColor.RED));
                        test.addScreenCaptureFromPath(screenshotPath, "Failure Screenshot");

                        // Add failure details if available
                        if (scenario.isFailed()) {
                            test.log(Status.FAIL, "Scenario failed. Please check the screenshot for details.");
                        }
                    }

                    System.out.println("❌ FAILED: " + scenario.getName());
                } else {
                    // For Cucumber report
                    final byte[] screenshot = ((TakesScreenshot) DriverManager.driver)
                            .getScreenshotAs(OutputType.BYTES);
                    scenario.attach(screenshot, "image/png", "Success Screenshot");

                    // For ExtentReports
                    if (test != null) {
                        test.log(Status.PASS, MarkupHelper.createLabel("✅ SCENARIO PASSED: " + scenario.getName(),
                                ExtentColor.GREEN));
                        test.addScreenCaptureFromPath(screenshotPath, "Success Screenshot");
                    }

                    System.out.println("✅ PASSED: " + scenario.getName());
                }

            } catch (Exception e) {
                System.err.println("Error during screenshot capture: " + e.getMessage());
                if (test != null) {
                    test.log(Status.WARNING, "Screenshot capture failed: " + e.getMessage());
                }
            } finally {
                DriverManager.quitDriver();
                ExtentTestManager.endTest();
                System.out.println("🔚 Driver closed successfully for: " + scenario.getName());
            }
        }
    }

    @AfterAll
    public static void afterAll() {
        System.out.println("🔚 AFTER ALL: Flushing ExtentReports...");
        ExtentManager.flush();
        System.out.println("✅ AFTER ALL: ExtentReports completed successfully");
        System.out.println("📊 Report Location: " + ExtentManager.getReportPath());
    }
}
