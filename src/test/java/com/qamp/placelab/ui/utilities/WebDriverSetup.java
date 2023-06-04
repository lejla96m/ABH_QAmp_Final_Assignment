package com.qamp.placelab.ui.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class WebDriverSetup {

    public static WebDriver getWebDriver(final String browserName) {
        if (browserName.equals("edge")) {
            return getEdgeDriver();
        } else if (browserName.equals("chrome")) {
            return getChromeDriver();
        } else {
            throw new IllegalArgumentException("The browser " + browserName + " is not found.");
        }
    }

    private static WebDriver getEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        return new EdgeDriver();
    }

    private static WebDriver getChromeDriver() {
        WebDriverManager.chromedriver().setup();
        return new ChromeDriver();
    }
}
