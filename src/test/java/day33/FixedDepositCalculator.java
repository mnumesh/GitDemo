package day33;

import java.io.IOException;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FixedDepositCalculator {

	public static void main(String[] args) throws IOException, InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(
				"https://www.moneycontrol.com/fixed-income/calculator/state-bank-of-india-sbi/fixed-deposit-calculator-SBI-BSB001.html");
		driver.manage().window().maximize();

		String file = System.getProperty("user.dir") + "\\TestData\\caldata.xlsx";

		int rows = ExcelUtils.getRowCount(file, "Sheet1");

		for (int r = 1; r <= rows; r++) {
			// read data from excel
			String Principle = ExcelUtils.getCellData(file, "Sheet1", r, 0);
			String RateOfInterest = ExcelUtils.getCellData(file, "Sheet1", r, 1);
			String Period1 = ExcelUtils.getCellData(file, "Sheet1", r, 2);
			String Period2 = ExcelUtils.getCellData(file, "Sheet1", r, 3);
			String Frequency = ExcelUtils.getCellData(file, "Sheet1", r, 4);
			String ExpMaturityVaule = ExcelUtils.getCellData(file, "Sheet1", r, 5);

			// Pass data to app
			driver.findElement(By.id("principal")).sendKeys(Principle);
			driver.findElement(By.id("interest")).sendKeys(RateOfInterest);
			driver.findElement(By.id("tenure")).sendKeys(Period1);

			Select periodDrop = new Select(driver.findElement(By.id("tenurePeriod")));
			periodDrop.selectByVisibleText(Period2);

			Select FrequencyDrop = new Select(driver.findElement(By.id("frequency")));
			FrequencyDrop.selectByVisibleText(Frequency);
		
			JavascriptExecutor js=(JavascriptExecutor)driver;
			WebElement calculate_btn=driver.findElement(By.xpath("//div[@class='cal_div']//a[1]"));
			js.executeScript("arguments[0].click();",calculate_btn);  // clicking on calculate button
			
			// Validating and update the result in excel

			String ActMaturityVaule = driver.findElement(By.xpath("//*[@id=\"resp_matval\"]/strong")).getText();

			if (Double.parseDouble(ExpMaturityVaule) == Double.parseDouble(ActMaturityVaule))
			{
				System.out.println("Test Passed");
				ExcelUtils.setCellData(file, "Sheet1", r, 7, "Passed");
				ExcelUtils.fillGreenColor(file, "Sheet1", r,7);
			}
			else
			{
				System.out.println("Test failed");
				ExcelUtils.setCellData(file, "Sheet1", r, 7, "Failed");
				ExcelUtils.fillRedColor(file, "Sheet1", r, 7);	
			}
			Thread.sleep(3);

			WebElement clear_btn=driver.findElement(By.xpath("//img[@class='PL5']"));
			js.executeScript("arguments[0].click();",clear_btn);  // clicking on clear button	
		}
		Thread.sleep(3);
		driver.quit();

	}

}
