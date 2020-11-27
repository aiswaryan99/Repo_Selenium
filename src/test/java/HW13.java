import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HW13 {

    @Test
    public static void mouse() throws InterruptedException {
        /**
         * TestCase 1: User should be able to click on the first yahoo notification
         * 1. Launch yahoo.com
         * 2. Move mouse to bell icon
         * 3. User should be able to click on the first notification
         */

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://yahoo.com");

        WebElement bellIcon = driver.findElement(By.id("ybarNotificationMenu"));
        Actions act = new Actions(driver);
        act.moveToElement(bellIcon).build().perform();

        WebElement not = driver.findElement(By.xpath("//div[@class='yns-panel']"));
        Thread.sleep(4000);
        not.click();
        String expUrl = "https://www.yahoo.com/huffpost/brianna-keilar-marco-rubio-biden-cabinet-000628672.html";
        String actual = driver.getCurrentUrl();

        Assert.assertEquals(actual,expUrl,"User is unable to click on first notification");
    }


    @Test
    public static void registration() throws InterruptedException {
        /**
         * TestCase 2: User should get error on invalid date of birth
         * 1. Launch facebook.com
         * 2. Click on 'Create New Account'
         * 3. Enter fname as First Name
         * 4. Enter lname as Last Name
         * 5. 'abcd@test.com' as email address
         * 6. 'abcd@1234' as new password
         * 7. Enter birthday as 'JAN 4 1998' as date of birth
         * 8. Click the 'Sign Up' button
         * 9. Verify user is seeing the error msg for gender
         * (Please choose a gender. You can change who can see this later.)
         */

        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://facebook.com");

        WebElement create = driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']"));
        create.click();
        Thread.sleep(2000);

        WebElement fname = driver.findElement(By.name("firstname"));
        fname.sendKeys("fname");
        WebElement lname = driver.findElement(By.name("lastname"));
        lname.sendKeys("lname");
        WebElement email = driver.findElement(By.xpath("//input[@aria-label='Mobile number or email']"));
        email.sendKeys("abcd@test.com");
        WebElement again= driver.findElement(By.xpath("//input[@aria-label='Re-enter email']"));
        again.sendKeys("abcd@test.com");
        WebElement password = driver.findElement(By.xpath("//input[@data-type='password']"));
        password.sendKeys("abcd@1234");

        WebElement monthEle= driver.findElement(By.id("month"));
        Select month = new Select(monthEle);
        month.selectByVisibleText("Jan");
        WebElement dayEle = driver.findElement(By.id("day"));
        Select day = new Select(dayEle);
        day.selectByValue("4");
        WebElement yearEle= driver.findElement(By.id("year"));
        Select year = new Select(yearEle);
        year.selectByVisibleText("1998");

        WebElement submit = driver.findElement(By.name("websubmit"));
        submit.click();

        WebElement popUp= driver.findElement(By.xpath("//div[text()='Please choose a gender. You can change who can see this later.']"));
        String msg= popUp.getText();

        boolean isPresent = popUp.isDisplayed();
        Assert.assertTrue(isPresent,"message is not displayed!");

    }
}
