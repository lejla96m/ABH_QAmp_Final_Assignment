package com.qamp.placelab.ui.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;

public class QueriesPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public QueriesPage(final WebDriver webDriver) {
        this.driver = webDriver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    }

    public void validateThatReportIsCreated(final String reportId) {
        driver.navigate().refresh();

        while (checkStatus(reportId).equals("label-running") || checkStatus(reportId).equals("label-created") || checkStatus(reportId).equals("label-finished")) {

            try {

                if (checkStatus(reportId).equals("label-ok")) {
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("status_" + reportId)));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("request_" + reportId)));

                    driver.findElement(By.cssSelector("a[id='request_" + reportId + "']"))
                            .click();

                /*    WebElement element = driver.findElement(By.id("request_" + reportId));
                    JavascriptExecutor executor = (JavascriptExecutor) driver;
                    executor.executeScript("arguments[0].click();", element); */

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        System.out.println("Report is still loading or not created");
                    }
                    wait.until(ExpectedConditions.urlToBe("https://demo.placelab.com/roads/road_analyses/" + reportId));

                    break;
                } else if (checkStatus(reportId).equals("label-error")) {
                    break;
                }
            } catch (StaleElementReferenceException e) {
                checkStatus(reportId);
            }
        }
    }

    public String checkStatus(final String reportId) {
        try {
            WebElement statusElement = new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.visibilityOfElementLocated(By.id("status_" + reportId)));
            return statusElement.findElement(By.tagName("span")).getAttribute("class");
        } catch (StaleElementReferenceException e) {
            driver.navigate().refresh();
        }
        return driver.findElement(By.id("status_" + reportId)).findElement(By.tagName("span")).getAttribute("class");
    }

    public void findCheckbox (final String reportId) {
        driver.get("https://demo.placelab.com/queries");
        Assert.assertEquals(driver.getCurrentUrl(), "https://demo.placelab.com/queries");

        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//tbody/tr/td/div/input[@type='checkbox']")));

        java.util.List<WebElement> checkboxes = driver.findElements(By.xpath("//tbody/tr/td/div/input[@type='checkbox']"));

        for (WebElement checkbox : checkboxes) {
            try {
                String inputAttribute = checkbox.getAttribute("value");

                if (inputAttribute.equals(reportId)) {
                    WebElement checkboxToSelect = checkbox.findElement(By.xpath(".."));
                    checkboxToSelect.click();
                }
            } catch (StaleElementReferenceException e) {
                checkboxes = driver.findElements(By.xpath("//tbody/tr/td/div/input[@type='checkbox']"));
            }
        }
    }

    public void deleteCreatedReport () {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("action-delete")));
        Assert.assertTrue(driver.findElement(By.id("action-delete")).isDisplayed());
        driver.findElement(By.id("action-delete")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("confirm-action-delete")));

        wait.until(ExpectedConditions.elementToBeClickable(By.id("confirm-link")));
        Assert.assertTrue(driver.findElement(By.id("confirm-link")).isDisplayed());
        driver.findElement(By.id("confirm-link")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("alert-place")));
        Assert.assertTrue(driver.findElement(By.id("alert-place")).isDisplayed());
    }

    public String getReportName (final String reportId) {
        return driver.findElement(By.id("request_" + reportId)).getText();
    }
}
