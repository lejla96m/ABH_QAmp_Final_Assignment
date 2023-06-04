package com.qamp.placelab.ui.pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class LoginPage {
    private final WebDriver driver;
    private final static String EXPECTED_PAGE_TITLE = "PlaceLab";
    private final static By LOGIN_HEADER = By.cssSelector("div#login > p.headline");
    private final static By LOGIN_FORM = By.id("login_form");
    private final static By EMAIL_FIELD = By.id("email");
    private final static By PASSWORD_FIELD = By.id("password");
    private final static String EXPECTED_ERROR_MESSAGE = "Invalid credentials!";
    private final static By LOGIN_BTN = By.xpath("//*[@id=\"login_form\"]/input[4]");
    private final static By EMAIL_INPUT = By.id("email");
    private final static By PASSWORD_INPUT = By.id("password");
    private final static By FORGOT_PASSWORD_OPTION = By.linkText("Forgot your password?");
    private final static String EXPECTED_SENT_EMAIL_MESSAGE = "We have sent you a link to change your password";
    private final static By SENT_EMAIL_MESSAGE = By.xpath("//*[@id=\"login\"]/p");
    private final static By EMAIL_NOT_RECEIVED = By.linkText("Didn't get it?");

    public LoginPage(final WebDriver webDriver) {
        this.driver = webDriver;
    }

    public void validateLoginPageContent() {
        final String actualPageTitle = driver.getTitle();
        final String expectedHeaderText = "Turn your data into information, information into insight and insight into business decisions.";

        Assert.assertEquals(actualPageTitle, EXPECTED_PAGE_TITLE, "Validate the page title");

        final boolean isHeaderDisplayed = driver.findElement(LOGIN_HEADER).isDisplayed();
        Assert.assertTrue(isHeaderDisplayed);

        final String actualHeaderText = driver.findElement(LOGIN_HEADER).getText();
        Assert.assertEquals(actualHeaderText, expectedHeaderText, "Validate the header text");

        final boolean isLoginFormDisplayed = driver.findElement(LOGIN_FORM).isDisplayed();
        Assert.assertTrue(isLoginFormDisplayed);

        final boolean isEmailFieldDisplayed = driver.findElement(EMAIL_FIELD).isDisplayed();
        Assert.assertTrue(isEmailFieldDisplayed);

        final boolean isPasswordFieldDisplayed = driver.findElement(PASSWORD_FIELD).isDisplayed();
        Assert.assertTrue(isPasswordFieldDisplayed);
    }

    public void validateLoginPageErrorMessage () {
        final boolean isInvalidCredentialsMessageDisplayed = driver.findElement(By.className("error-area")).isDisplayed();
        Assert.assertTrue(isInvalidCredentialsMessageDisplayed);

        final String actualMessage = driver.findElement(By.className("error-area")).getText();
        Assert.assertEquals(actualMessage, EXPECTED_ERROR_MESSAGE, "Validate the error message when invalid credentials are provided");
    }

    public void validateAndClickLoginBtn() {
        final boolean isLoginBtnDisplayed = driver.findElement(LOGIN_BTN).isDisplayed();
        Assert.assertTrue(isLoginBtnDisplayed);

        driver.findElement(LOGIN_BTN).click();
    }

    public void enterCredentials(final String email, final String password) {
        driver.findElement(EMAIL_INPUT).sendKeys(email);
        driver.findElement(PASSWORD_INPUT).sendKeys(password);
    }

    public void passwordReset(final String email) {
        final boolean isForgotPasswordProvided = driver.findElement(FORGOT_PASSWORD_OPTION).isDisplayed();
        Assert.assertTrue(isForgotPasswordProvided);

        driver.findElement(FORGOT_PASSWORD_OPTION).click();

        final String headerText = driver.findElement(By.cssSelector("div#login > p.headline")).getText();
        new SoftAssert().assertEquals(headerText, "Change your password", "Validate the header text on Forgot your password page");

        driver.findElement(EMAIL_INPUT).isDisplayed();

        driver.findElement(EMAIL_INPUT).sendKeys(email);

        driver.findElement(By.xpath("//*[@id=\"login_form\"]/input[3]")).isDisplayed();

        driver.findElement(By.name("commit")).click();

        final String actualSentEmailMessage = driver.findElement(SENT_EMAIL_MESSAGE).getText();
        Assert.assertEquals(actualSentEmailMessage,EXPECTED_SENT_EMAIL_MESSAGE, "Validate a message after the email for the password reset is sent");

        final boolean ifEmailNotSent = driver.findElement(EMAIL_NOT_RECEIVED).isDisplayed();
        Assert.assertTrue(ifEmailNotSent);

        driver.findElement(EMAIL_NOT_RECEIVED).click();
    }
}
