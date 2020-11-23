package Class1;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HW12 {

    @Test
    public void Temp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://darksky.net");

    /**
     * Testcase 1: Verify that the feels like temperature is in between the low and high temp values
     * at any zipcode
     * Steps:
     * 1. Launch darksky.net
     * 2. Capture the value of feels like temp and store in a variable (string)
     * 3. Capture high and low temps and store in two variables (strings)
     * 4. Convert strings to int
     * 5. Compare the feels like temp to the low and high temps
     */

        WebElement feels = driver.findElement(By.xpath("//span[@class='feels-like-text']" ));
        String feelsValue= feels.getText();
        String value = feelsValue.substring(0,2);
        int feelsVal = Integer.parseInt(value);
       // System.out.println(feelsVal);

        WebElement low = driver.findElement(By.xpath("//span[@class='low-temp-text']"));
        String val1 = low.getText();
        String l = val1.substring(0,2);
        int lowTemp = Integer.parseInt(l);
        // System.out.println(lowTemp);

        WebElement high = driver.findElement(By.xpath("//span[@class='high-temp-text']"));
        String val2 = high.getText();
        String h = val2.substring(0,2);
        int highTemp= Integer.parseInt(h);
       // System.out.println(highTemp);

        boolean res = (lowTemp <= feelsVal && feelsVal <= highTemp);
        Assert.assertTrue(res);
        Assert.assertTrue(true, "Temp not in range");
        driver.close();

      }
    @Test
    public void checkTemp() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver.exe");
        WebDriver driver1 = new ChromeDriver();
        driver1.get("https://darksky.net");

        /**
         * Testcase 3: Verify the temperature value converts as expected as per the unit selected.
         * 1. Capture the current temperature and store in a variable (String)
         * 2. Convert String into int
         * 3. Click the desired unit (F or C)
         * 4. Check if the temperature is correct (F --> C)
         * 5. Check if the temperature is correct (C --> F)
         */

        // Checking F --> C
        WebElement s = driver1.findElement(By.xpath("//input[@type='text']"));
        s.clear();
        s.sendKeys("11801");
        Thread.sleep(4000);
        WebElement c = driver1.findElement(By.xpath("//img[@alt='Search Button']"));
        c.click();
        Thread.sleep(2000);

        WebElement temp = driver1.findElement(By.xpath("//span[@class='summary swap']"));
        String t = temp.getText();
        String tempVal = t.substring(0, 2);
        double fTemp = Integer.parseInt(tempVal);
        System.out.println(fTemp);

        WebElement drop = driver1.findElement(By.xpath("//span[@class='label']//following-sibling::b[@class='button']"));
        drop.click();
        Thread.sleep(4000);

        WebElement selectC = driver1.findElement(By.xpath("//li[@data-index='3' and contains(@class,'last')]"));
        selectC.click();
        Thread.sleep(4000);

        WebElement temp1 = driver1.findElement(By.xpath("//span[@class='desc swap']//span[@class='summary swap']"));
        String t2 = temp1.getText();
        String tempVal1 = t2.substring(0, 2);
        int cTemp = Integer.parseInt(tempVal1);
        System.out.println(cTemp);

        int actualC = (int) Math.round((fTemp - 32) * 5 / 9);
        System.out.println(actualC);

        Assert.assertEquals(cTemp, actualC, "Incorrect Temperature");

        // Checking C --> F

        WebElement r = driver1.findElement(By.xpath("//input[@type='text']"));
        r.clear();
        r.sendKeys("10201");
        Thread.sleep(4000);
        WebElement a = driver1.findElement(By.xpath("//img[@alt='Search Button']"));
        a.click();
        Thread.sleep(2000);

        WebElement temp2 = driver1.findElement(By.xpath("//span[@class='desc swap']//span[@class='summary swap']"));
        String t3 = temp2.getText();
        String tempVal2 = t3.substring(0, 2);
        int cTempNew = Integer.parseInt(tempVal2);
        System.out.println("New C Temp: " + cTempNew);

        WebElement drop1 = driver1.findElement(By.xpath("//span[@class='label']//following-sibling::b[@class='button']"));
        Thread.sleep(4000);
        drop1.click();

        WebElement selectF = driver1.findElement(By.xpath("//ul//li[@data-index='0'][1]"));
        Thread.sleep(4000);
        selectF.click();
        Thread.sleep(4000);

        WebElement temp3 = driver1.findElement(By.xpath("//span[@class='desc swap']//span[@class='summary swap']"));
        String t4 = temp3.getText();
        String tempVal3 = t4.substring(0, 2);
        int fTempNew = Integer.parseInt(tempVal3);
        System.out.println("New F Temp: " + fTempNew);

        int actualF = (int) Math.round((cTempNew * 9 / 5) + 32);
        System.out.println(actualF);

        Assert.assertEquals(fTempNew, actualF, "Incorrect Temperature");
        driver1.close();
    }

    /**
     * Test Case 2: Verify the dates on the Blog Page appears in reverse chronological order
     * Steps:
     * 1. Launch darksky.com
     * 2. Click on Dark Sky API
     * 3. Click on blog post
     * 4. Capture the three dates present and store in variables (Strings)
     * 5. Convert Strings into Dates
     * 6. Compare the dates to see if they are in reverse chronological order
     */

    @Test
    public void checkChronological() throws InterruptedException, ParseException {
        System.setProperty("webdriver.chrome.driver", "./DriverExe/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://darksky.net");

        driver.findElement(By.linkText("Dark Sky API")).click();
        Thread.sleep(2000);
        driver.findElement(By.linkText("blog post")).click();

        WebElement d1 = driver.findElement(By.xpath("//time[@datetime='2020-03-31T13:00:00-04:00'][1]"));
        String date1 = d1.getText();
        System.out.println(date1);
        Thread.sleep(2000);

        WebElement d2 = driver.findElement(By.xpath("//time[text()='July 1, 2020']"));
        String date2 = d2.getText();
        System.out.println(date2);
        Thread.sleep(2000);

        WebElement d3 = driver.findElement(By.xpath("//time[text()='March 31, 2020']"));
        String date3 = d3.getText();
        System.out.println(date3);
        Thread.sleep(2000);

        SimpleDateFormat format = new SimpleDateFormat("MMMM dd, yyyy");

        ArrayList <Date> dateList = new ArrayList<>();
        ArrayList <Date> actualList = new ArrayList<>();

        Date D1 = format.parse(date1);
        dateList.add(D1);
        actualList.add(D1);
        System.out.println("Date 1: " + D1);

        Date D2 = format.parse(date2);
        dateList.add(D2);
        actualList.add(D2);
        System.out.println("Date 2: " + D2);

        Date D3 = format.parse(date3);
        dateList.add(D3);
        actualList.add(D3);
        System.out.println("Date 3: " + D3);

        actualList.sort(Collections.reverseOrder());

        Assert.assertEquals(dateList,actualList,"The dates are not in reverse chronological order");

        driver.close();
    }
}
