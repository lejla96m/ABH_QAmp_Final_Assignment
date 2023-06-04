package com.qamp.placelab.ui.roads;

import com.qamp.placelab.ui.pages.*;
import com.qamp.placelab.ui.utilities.WebDriverSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import java.time.Duration;

public class RoadAnalysisReportTests {
    private WebDriver driver;
    private WebDriverWait wait;
    private LoginPage loginPage;
    private HomePage homePage;
    private RoadAnalysisReportPage roadAnalysisReportPage;
    private ProgressScreen progressScreen;
    private QueriesPage queriesPage;
    private String reportId;
    private final String road_analysis_path = "roads/road_analyses/";
    private static final String QUERIES_URL = "https://demo.placelab.com/queries";
    private static final String VALID_FILE_PATH = "C:\\Users\\W10\\QAMP\\workspace\\ABH_QAMP_Final_Assignment\\src\\test\\java\\com\\qamp\\placelab\\ui\\utilities\\files\\roads_sample.txt";
    private static final String EMPTY_FILE_PATH = "C:\\Users\\W10\\QAMP\\workspace\\ABH_QAMP_Final_Assignment\\src\\test\\java\\com\\qamp\\placelab\\ui\\utilities\\files\\empty_roads_sample.txt";
    private static final String INVALID_FILE_PATH = "C:\\Users\\W10\\QAMP\\workspace\\ABH_QAMP_Final_Assignment\\src\\test\\java\\com\\qamp\\placelab\\ui\\utilities\\files\\invalid_roads_sample.txt";

    @Parameters({"browser", "email", "password"})
    @BeforeTest
    public void setup(final String browser, final String email, final String password) {
        driver = WebDriverSetup.getWebDriver(browser);
        driver.get("https://demo.placelab.com/");
        driver.manage().window().maximize();

        this.loginPage = new LoginPage(driver);
        this.homePage = new HomePage(driver);
        this.roadAnalysisReportPage = new RoadAnalysisReportPage(driver);
        this.progressScreen = new ProgressScreen(driver);
        this.queriesPage = new QueriesPage(driver);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        loginPage.validateLoginPageContent();
        loginPage.enterCredentials(email, password);
        loginPage.validateAndClickLoginBtn();
        homePage.validateHomePageContent();
    }

    @Test(priority = 1)
    public void createValidRoadAnalysisReport() {
        homePage.navigateToCreateRoadAnalysisReport();

        roadAnalysisReportPage.validateNewRoadAnalysisReportPageContent();
        roadAnalysisReportPage.enterRoadAnalysisReportPageData(VALID_FILE_PATH);
        roadAnalysisReportPage.createReport();
    }

    @Test (priority = 2, dependsOnMethods = "createValidRoadAnalysisReport")
    public void checkRoadAnalysisReportStatus() {
        reportId = progressScreen.getReportId();
        wait.until(ExpectedConditions.urlToBe(QUERIES_URL));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Report is still loading or not created");
        }

        queriesPage.validateThatReportIsCreated(reportId);
        roadAnalysisReportPage.validateCreatedReportResultPage(reportId, road_analysis_path, "Road Analysis");
    }

    @Test (priority = 3, dependsOnMethods = "createValidRoadAnalysisReport")
    public void deleteRoadAnalysisReport() {
        driver.navigate().refresh();

        queriesPage.findCheckbox(reportId);
        queriesPage.deleteCreatedReport();
    }

    @Test (priority = 4)
    public void createEmptyFileRoadAnalysisReport () {
        homePage.navigateToCreateRoadAnalysisReport();

        roadAnalysisReportPage.validateNewRoadAnalysisReportPageContent();
        roadAnalysisReportPage.enterRoadAnalysisReportPageData(EMPTY_FILE_PATH);

        Assert.assertEquals(driver.findElement(By.id("road-query-alert-message")).getText(),
                "File is empty.",
                "Validate the alert message for trying to upload empty file.");

        Assert.assertFalse(driver.findElement(By.xpath("//*[@id=\"road-fileupload\"]/button")).isEnabled());
    }

    @Test (priority = 5, dependsOnMethods = "createEmptyFileRoadAnalysisReport")
    public void createInvalidFileRoadAnalysisReport () {
        roadAnalysisReportPage.validateNewRoadAnalysisReportPageContent();
        roadAnalysisReportPage.enterRoadAnalysisReportPageData(INVALID_FILE_PATH);
        roadAnalysisReportPage.createReport();
    }

    @Test (priority = 6, dependsOnMethods = "createInvalidFileRoadAnalysisReport")
    public void checkInvalidFileRoadAnalysisReportStatus () {
        reportId = progressScreen.getReportId();
        wait.until(ExpectedConditions.urlToBe(QUERIES_URL));
        queriesPage.validateThatReportIsCreated(reportId);
    }

    @Test (priority = 7, dependsOnMethods = "createInvalidFileRoadAnalysisReport")
    public void deleteInvalidFileRoadAnalysisReport () {
        driver.navigate().refresh();
        queriesPage.findCheckbox(reportId);
        queriesPage.deleteCreatedReport();
    }

    @AfterTest
    public void teardown() {
        homePage.logout();

        driver.close();
    }
}
