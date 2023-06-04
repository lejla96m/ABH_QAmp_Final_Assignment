package com.qamp.placelab.ui.pages;

import org.openqa.selenium.WebDriver;
import java.net.MalformedURLException;
import java.net.URL;

public class ProgressScreen {
    private final WebDriver driver;

    public ProgressScreen(final WebDriver webDriver) {
        this.driver = webDriver;
    }

    public String getReportId() {
        final String progressUrl = driver.getCurrentUrl();
        return extractPath(progressUrl)
                .replace("/progress/", "");
    }

    private static String extractPath(String urlString) {
        try {
            URL url = new URL(urlString);
            return url.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
