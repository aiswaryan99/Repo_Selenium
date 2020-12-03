import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HW14 {

    @Test
    public static void verify() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://darksky.net");

        /**
         * TestCase 1: Verify the high and low temps on today's timeline
         * Steps:
         * 1. Launch darksky.net
         * 2. Scroll all the way to bottom
         * 3. Click on today to open today's temps
         * 4. Capture the values of both and change them into int variables
         * 5. Verify the values are the same in both cases
         */

        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement ele = driver.findElement(By.xpath("//span[contains(text(),'Today')]"));
        js.executeScript("scrollBy(0,700);");
        Thread.sleep(2000);
        ele.click();

        WebElement temp1 = driver.findElement(By.xpath("//span[@class='tempRange']//span[@class='minTemp'][1]"));
        String low1 = temp1.getText();
        String l = low1.replace(low1.substring(low1.length() - 1), "");
        int lowTemp1 = Integer.parseInt(l);
        System.out.println(lowTemp1);

        WebElement temp2 = driver.findElement(By.xpath("//span[@class='temp'][1]"));
        String low2 = temp2.getText();
        String l2 = low2.replace(low2.substring(low2.length() - 1), "");
        int lowTemp2 = Integer.parseInt(l2);
        System.out.println(lowTemp2);

        Assert.assertEquals(lowTemp1, lowTemp2, "Low temps are not equal!");

        WebElement temp3 = driver.findElement(By.xpath("//span[@class='maxTemp'][1]"));
        String high1 = temp3.getText();
        String h1 = high1.replace(high1.substring(high1.length() - 1), "");
        int highTemp1 = Integer.parseInt(h1);
        System.out.println(highTemp1);

        WebElement temp4 = driver.findElement(By.xpath("//span[@class='lowTemp swap']/span[@class='temp'][1]"));
        String high2 = temp4.getText();
        String h2 = high2.replace(high2.substring(high2.length() - 1), "");
        int highTemp2 = Integer.parseInt(h2);
        System.out.println(highTemp2);

        Assert.assertEquals(highTemp1, highTemp2, "High temps are not equal!");
    }

    @Test
    public static void numOfNights() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.hotels.com");

        /**
         * TestCase 2: Verify the number of nights on the briefcase
         * Steps:
         * 1. Launch hotels.com
         * 2. Input information
         *      checkin: tomorrow
         *      checkout: 7 days from checkin date
         * 3. capture the briefcase value
         * 4. compare the values
         */

        driver.findElement(By.id("qf-0q-destination")).clear();
        driver.findElement(By.id("qf-0q-destination")).sendKeys("amsterdam");
        Thread.sleep(2000);
        String toSelect = "Amsterdam, Netherlands (AMS-Schiphol)";

        By autoSuggestionsLocator = By.xpath("//div[@class='autosuggest-category-result']");
        List<WebElement> suggestions = driver.findElements(autoSuggestionsLocator);

        for (WebElement s : suggestions) {
            String sText = s.getText();
            if (toSelect.equalsIgnoreCase(sText)) {
                s.click();
                break;
            }
        }

        driver.findElement(By.xpath("//label[@data-input='qf-0q-localised-check-in']")).click();
        List<WebElement> date1 = driver.findElements(By.xpath("//td[starts-with(@data-date,'2020-11')]"));

        Format formatter;
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH,1);
        Date tomorrow = cal.getTime();
        formatter = new SimpleDateFormat("d");
        String userCheckIn = formatter.format(tomorrow);

        Thread.sleep(2000);

        for(WebElement date: date1){
            if(date.getText().equalsIgnoreCase(userCheckIn)){
                date.click();
                break;
            }
        }
        driver.findElement(By.xpath("//label[@data-input='qf-0q-localised-check-out']")).click();
        List<WebElement> dates = driver.findElements(By.xpath("//td[starts-with(@data-date,'2020-11')]"));

        Date now1 = new Date();
        Calendar cal2 = Calendar.getInstance();
        cal.setTime(now);
        cal.add(Calendar.DAY_OF_MONTH,8);
        Date after7Days = cal.getTime();
        formatter = new SimpleDateFormat("d");
        String userCheckOut = formatter.format(after7Days);
        Thread.sleep(2000);

        for(WebElement date: dates){
            if(date.getText().equalsIgnoreCase(userCheckOut)){
                date.click();
                break;
            }
        }
        Thread.sleep(2000);

        WebElement briefcase = driver.findElement(By.xpath("//span[@class='widget-query-num-nights']"));
        String nights = briefcase.getText();
        int expNights = Integer.parseInt(nights);
       // System.out.println(expNights);

        int in = Integer.parseInt(userCheckIn);
        int out = Integer.parseInt(userCheckOut);

        Date now2 = new Date();
        Calendar cal3 = Calendar.getInstance();
        cal3.setTime(now);
        formatter = new SimpleDateFormat("yyyy");
        Date year = cal3.getTime();
        String currentYear = formatter.format(year);
        int thisYear = Integer.parseInt(currentYear);
       // System.out.println(thisYear);

       LocalDate checkIn = LocalDate.ofYearDay(thisYear,in);
       LocalDate checkOut = LocalDate.ofYearDay(thisYear,out);

      long noOfNights = ChronoUnit.DAYS.between(checkIn,checkOut);
      //System.out.println(noOfNights);

      Assert.assertEquals(expNights,noOfNights,"They are not the same!");

        /**
         * TestCase 3: Enter user details as mentioned on homepage
         * Verify user details match after clicking on the search button
         * Steps:
         * 1. By the use of locators, fill in the user details
         * 2. Click on the search button
         * 3. Capture the user details using locators and save to variables
         * 4. Compare the values obtained to actual values
         */

        // Room 1 is already selected !
        WebElement roomEle = driver.findElement(By.className("query-rooms"));
        Select room = new Select(roomEle);
        room.selectByVisibleText("1");
        Thread.sleep(2000);
        Select select1 = new Select(driver.findElement(By.className("query-rooms")));
        WebElement opt1 = select1.getFirstSelectedOption();
        String room1 = opt1.getText();
//        System.out.println(room1);

        Thread.sleep(2000);
        WebElement adultEle = driver.findElement(By.name("q-room-0-adults"));
        Select adults = new Select(adultEle);
        adults.selectByVisibleText("1");
        Thread.sleep(2000);
        Select select2 = new Select(driver.findElement(By.name("q-room-0-adults")));
        WebElement opt2 = select2.getFirstSelectedOption();
        String adult1 = opt2.getText();
//        System.out.println(adult1);

        Thread.sleep(2000);
        WebElement childEle = driver.findElement(By.name("q-room-0-children"));
        Select child = new Select(childEle);
        child.selectByVisibleText("2");
        Thread.sleep(4000);
        Select select3 = new Select(driver.findElement(By.name("q-room-0-children")));
        WebElement opt3 = select3.getFirstSelectedOption();
        String child1 = opt3.getText();
//        System.out.println(child1);

        Thread.sleep(2000);
        WebElement ageEle = driver.findElement(By.name("q-room-0-child-0-age"));
        Select age1 = new Select(ageEle);
        age1.selectByVisibleText("1");
        Thread.sleep(2000);
        Select select4 = new Select(driver.findElement(By.name("q-room-0-child-0-age")));
        WebElement opt4 = select4.getFirstSelectedOption();
        String ageOne = opt4.getText();
//        System.out.println(ageOne);

        Thread.sleep(2000);
        WebElement ageEle2 = driver.findElement(By.name("q-room-0-child-1-age"));
        Select age2 = new Select(ageEle2);
        age2.selectByVisibleText("2");
        Thread.sleep(2000);
        Select select5 = new Select(driver.findElement(By.name("q-room-0-child-1-age")));
        WebElement opt5 = select5.getFirstSelectedOption();
        String ageTwo = opt5.getText();
//        System.out.println(ageTwo);

        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        Thread.sleep(4000);

        Select s1 = new Select(driver.findElement(By.className("query-rooms")));
        WebElement exp1 = s1.getFirstSelectedOption();
        String r1 = exp1.getText();
//        System.out.println("Expected values" + r1);

        Assert.assertEquals(room1,r1,"The input is not as the previous selected!");

        Thread.sleep(2000);
        Select s2 = new Select(driver.findElement(By.name("q-room-0-adults")));
        WebElement exp2 = s2.getFirstSelectedOption();
        String a1 = exp2.getText();
//        System.out.println(a1);

        Assert.assertEquals(adult1,a1,"The input is not as the previous selected!");


        Thread.sleep(2000);
        Select s3 = new Select(driver.findElement(By.name("q-room-0-children")));
        WebElement exp3 = s3.getFirstSelectedOption();
        String c1 = exp3.getText();
//        System.out.println(c1);

        Assert.assertEquals(child1,c1,"The input is not as the previous selected!");


        Thread.sleep(2000);
        Select s4 = new Select(driver.findElement(By.name("q-room-0-child-0-age")));
        WebElement exp4 = s4.getFirstSelectedOption();
        String oneAge = exp4.getText();
//        System.out.println(oneAge);

        Assert.assertEquals(ageOne,oneAge,"The input is not as the previous selected!");

        Thread.sleep(2000);
        Select s5 = new Select(driver.findElement(By.name("q-room-0-child-1-age")));
        WebElement exp5 = s5.getFirstSelectedOption();
        String twoAge = exp5.getText();
//        System.out.println(twoAge);

        Assert.assertEquals(ageTwo,twoAge,"The input is not as the previous selected!");

    }

}
