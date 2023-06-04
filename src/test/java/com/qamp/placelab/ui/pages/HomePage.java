package com.qamp.placelab.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class HomePage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final static String EXPECTED_USER_ROLE = "Group Admin";
    private final static By USER_ROLE = By.id("user-role");
    private final static String EXPECTED_HOMEPAGE_URL = "https://demo.placelab.com/dashboard/traffic";
    private final static By LOGO_NAVIGATION_BAR = By.cssSelector("body > div.navbar.navbar-fixed-top > div.navbar-inner > div > a");
    private final static By CREATE_REPORT_MENU = By.id("create-menu");
    private final static By TRAFFIC_DASHBOARD = By.id("traffic-dashboard-nav-item");
    private final static By REPORTS_OPTION = By.id("queries-nav-item");
    private final static By ROAD_ANALYSIS = By.id("roadanalysis");
    private final static By ROAD_ANALYSIS_NAME = By.xpath("//*[@id=\"roadanalysis\"]/a/label");
    private final static String EXPECTED_REPORT_NAME = "Road Analysis";
    private final static By DROPDOWN_MENU = By.id("actions-nav-item");
    private final static String EXPECTED_SIGN_OUT_BTN = "Sign out";
    private final static By SIGN_OUT_BTN = By.linkText("Sign out");

    public HomePage(final WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void validateHomePageContent() {
        final String actualHomePageUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualHomePageUrl, EXPECTED_HOMEPAGE_URL, "Validate Homepage URL");

        final boolean isLogoNavigationDisplayed = driver.findElement(LOGO_NAVIGATION_BAR).isDisplayed();
        Assert.assertTrue(isLogoNavigationDisplayed);

        final boolean isCreateReportMenuDisplayed = driver.findElement(CREATE_REPORT_MENU).isDisplayed();
        Assert.assertTrue(isCreateReportMenuDisplayed);

        final boolean isTrafficDashboardDisplayed = driver.findElement(TRAFFIC_DASHBOARD).isDisplayed();
        Assert.assertTrue(isTrafficDashboardDisplayed);

        final boolean isReportsOptionDisplayed = driver.findElement(REPORTS_OPTION).isDisplayed();
        Assert.assertTrue(isReportsOptionDisplayed);

        final boolean isUserRoleDisplayed = driver.findElement(USER_ROLE).isDisplayed();
        Assert.assertTrue(isUserRoleDisplayed);

        final String actualUserRole = driver.findElement(USER_ROLE).getText();
        Assert.assertEquals(actualUserRole, EXPECTED_USER_ROLE, "Validate a user role");
    }

    public void navigateToCreateRoadAnalysisReport() {
        final boolean isCreateReportMenuDisplayed = driver.findElement(CREATE_REPORT_MENU).isDisplayed();
        Assert.assertTrue(isCreateReportMenuDisplayed);

        driver.findElement(CREATE_REPORT_MENU).click();

        wait.until(ExpectedConditions.elementToBeClickable(ROAD_ANALYSIS));

        final boolean isRoadAnalysisDisplayed = driver.findElement(ROAD_ANALYSIS).isDisplayed();
        Assert.assertTrue(isRoadAnalysisDisplayed);

        final String actualReportName = driver.findElement(ROAD_ANALYSIS_NAME).getText();
        Assert.assertEquals(actualReportName, EXPECTED_REPORT_NAME, "Validate the name of the single place search report");

        driver.findElement(ROAD_ANALYSIS).click();
    }

    public void logout () {
        final boolean isDropdownDisplayed = driver.findElement(DROPDOWN_MENU).isDisplayed();
        Assert.assertTrue(isDropdownDisplayed);

        driver.findElement(DROPDOWN_MENU).click();

        final boolean isSignOutDisplayed = driver.findElement(SIGN_OUT_BTN).isDisplayed();
        Assert.assertTrue(isSignOutDisplayed);

        final String actualSignOutButtonText = driver.findElement(SIGN_OUT_BTN).getText();
        Assert.assertEquals(actualSignOutButtonText, EXPECTED_SIGN_OUT_BTN, "Validate the text of Sign Out button");

        driver.findElement(SIGN_OUT_BTN).click();
    }
}
