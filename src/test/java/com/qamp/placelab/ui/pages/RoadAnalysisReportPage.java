package com.qamp.placelab.ui.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.Random;

public class RoadAnalysisReportPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final Random random = new Random();
    private final static By CREATE_BTN = By.xpath("//*[@id=\"road-fileupload\"]/button");
    private final static By ACTUAL_REPORT_NAME = By.id("page-title-id");
    public static final By REPORT_NAME_HEADER = By.xpath("//*[@id=\"routes_analysis_info\"]/div[4]/div/table/tbody/tr[1]/td[1]");
    public static final By ANALYSIS_INFO_TABLE = By.id("routes_analysis_info");
    public static final By ACTUAL_REPORT_NAME_IN_TABLE = By.cssSelector("td[class='strong']");
    public static final By FILE_NAME_HEADER = By.xpath("//*[@id=\"routes_analysis_info\"]/div[4]/div/table/tbody/tr[2]/td[1]");
    public static final By FILE_NAME = By.xpath("//*[@id=\"routes_analysis_info\"]/div[4]/div/table/tbody/tr[2]/td[2]");
    public static final By REPORT_TYPE_HEADER = By.xpath("//*[@id=\"routes_analysis_info\"]/div[4]/div/table/tbody/tr[3]/td[1]");
    public static final By REPORT_TYPE = By.xpath("//*[@id=\"routes_analysis_info\"]/div[4]/div/table/tbody/tr[3]/td[2]");
    public static final By SIZE_HEADER = By.xpath("//*[@id=\"routes_analysis_info\"]/div[4]/div/table/tbody/tr[4]/td[1]");
    public static final By SIZE = By.xpath("//*[@id=\"routes_analysis_info\"]/div[4]/div/table/tbody/tr[4]/td[2]");


    public RoadAnalysisReportPage(final WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void validateNewRoadAnalysisReportPageContent() {
        Assert.assertTrue(driver.findElement(By.id("road_dialog")).isDisplayed());

        final String actualRoadAnalysisHeader = driver.findElement(By.xpath("//*[@id=\"road_dialog\"]/div[1]/div"))
                .getText().replace("drive_eta", "");

        Assert.assertEquals(actualRoadAnalysisHeader, "Create Road Analysis Report", "Validate the header of Road Analysis Report page");
        Assert.assertTrue(driver.findElement(By.id("upload_query_name")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"sourceData\"]/label")).isDisplayed());

        final String actualInputDataZoneName = driver.findElement(By.xpath("//*[@id=\"sourceData\"]/label"))
                .getText();

        Assert.assertEquals(actualInputDataZoneName, "Upload Input Data", "Validate the label for input data section");
        Assert.assertTrue(driver.findElement(By.id("newDropzonePreview")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"newDropzonePreview\"]/div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"newDropzonePreview\"]/div[1]/span/span[1]")).isDisplayed());

        final String actualMainTutorialMessage = driver.findElement(By.xpath("//*[@id=\"newDropzonePreview\"]/div[1]/span/span[1]"))
                .getText();

        Assert.assertEquals(actualMainTutorialMessage, "Drop files to attach, or", "Validate the tutorial message for file upload next to the Browse button");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"newDropzonePreview\"]/div[1]/span/span[2]")).isDisplayed());

        final String actualNameOfBrowseBtn = driver.findElement(By.xpath("//*[@id=\"newDropzonePreview\"]/div[1]/span/span[2]"))
                .getText();

        Assert.assertEquals(actualNameOfBrowseBtn, "Browse", "Validate the Browse button name");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"newDropzonePreview\"]/div[1]/span/label")).isDisplayed());

        final String actualTutorialFileFormatMessage = driver.findElement(By.xpath("//*[@id=\"newDropzonePreview\"]/div[1]/span/label"))
                .getText();

        Assert.assertEquals(actualTutorialFileFormatMessage, "Upload .txt, .csv, .wkt, .zip file with geometry specified as longitude latitude Sample file", "Validate the tutorial message about the file that should be uploaded");
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"road-fileupload\"]/button")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//*[@id=\"road_dialog\"]/div[4]/div/div[1]")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("file_size")).isDisplayed());
    }

    public void enterRoadAnalysisReportPageData (final String filePath) {

        driver.findElement(By.id("upload_query_name")).sendKeys("Automation Test Road Analysis Report " + random.nextInt(1, 1000));
        driver.findElement(By.id("road-browse-btn")).sendKeys(filePath);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Report is still uploading");
        }
    }

    public void createReport () {
        Assert.assertTrue(driver.findElement(CREATE_BTN).isDisplayed());
        wait.until(ExpectedConditions.elementToBeClickable(CREATE_BTN));

        driver.findElement(CREATE_BTN).click();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            System.out.println("Report is still loading or not created");
        }
    }

    public void validateCreatedReportResultPage (final String reportId, final String reportTypePath, final String reportType) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("status_" + reportId)));
        driver.get("https://demo.placelab.com/" + reportTypePath + reportId);
        wait.until(ExpectedConditions.urlToBe("https://demo.placelab.com/" + reportTypePath + reportId));

        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            System.out.println("Report is still loading or not created");
        }

        wait.until(ExpectedConditions.presenceOfElementLocated(ACTUAL_REPORT_NAME));
        wait.until(ExpectedConditions.presenceOfElementLocated(ANALYSIS_INFO_TABLE));

        Assert.assertTrue(driver.findElement(ACTUAL_REPORT_NAME).isDisplayed());
        Assert.assertTrue(driver.findElement(ANALYSIS_INFO_TABLE).isDisplayed());
        Assert.assertTrue(driver.findElement(REPORT_NAME_HEADER).isDisplayed());
        Assert.assertEquals(driver.findElement(REPORT_NAME_HEADER).getText(), "Report name", "Validate the report name header in analysis info table");
        Assert.assertTrue(driver.findElement(ACTUAL_REPORT_NAME_IN_TABLE).isDisplayed());
        Assert.assertTrue(driver.findElement(FILE_NAME_HEADER).isDisplayed());
        Assert.assertEquals(driver.findElement(FILE_NAME_HEADER).getText(), "File name", "Validate the file name header in the info table");
        Assert.assertTrue(driver.findElement(FILE_NAME).isDisplayed());
        Assert.assertTrue(driver.findElement(REPORT_TYPE_HEADER).isDisplayed());
        Assert.assertEquals(driver.findElement(REPORT_TYPE_HEADER).getText(), "Report type", "Validate the report type header inside info table");
        Assert.assertTrue(driver.findElement(REPORT_TYPE).isDisplayed());
        Assert.assertEquals(driver.findElement(REPORT_TYPE).getText(), reportType, "Validate the report type");
        Assert.assertTrue(driver.findElement(SIZE_HEADER).isDisplayed());
        Assert.assertEquals(driver.findElement(SIZE_HEADER).getText(), "Size", "Validate the report size header");
        Assert.assertTrue(driver.findElement(SIZE).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("routes_map_cluster")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("route_status")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.id("route_analysis_address_match")).isDisplayed());
    }
}
