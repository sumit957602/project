package com.Cabs.BDD.utils;

import com.aventstack.extentreports.ExtentTest;
import java.util.HashMap;
import java.util.Map;

public class ExtentTestManager {
    private static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().getId());
    }

    public static synchronized ExtentTest startTest(String testName) {
        ExtentTest test = ExtentManager.getInstance().createTest(testName);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    public static synchronized ExtentTest startTest(String testName, String description) {
        ExtentTest test = ExtentManager.getInstance().createTest(testName, description);
        extentTestMap.put((int) Thread.currentThread().getId(), test);
        return test;
    }

    public static synchronized void endTest() {
        extentTestMap.remove((int) Thread.currentThread().getId());
    }
}
