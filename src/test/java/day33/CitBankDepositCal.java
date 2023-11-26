package day33;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class CitBankDepositCal {

	public static void main(String[] args) throws IOException, InterruptedException {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.get(
				"https://www.cit.com/cit-bank/resources/calculators/certificate-of-deposit-calculator");
		driver.manage().window().maximize();

		String file = System.getProperty("user.dir") + "\\TestData\\CitBankcaldata.xlsx";

		int rows = ExcelUtils.getRowCount(file, "Sheet1");

		for (int r = 1; r <= rows; r++) {
			// read data from excel
			String InitialDepositAmount = ExcelUtils.getCellData(file, "Sheet1", r, 0);
			String Months = ExcelUtils.getCellData(file, "Sheet1", r, 1);
			String Rate = ExcelUtils.getCellData(file, "Sheet1", r, 2);
			String Compounding = ExcelUtils.getCellData(file, "Sheet1", r, 3);
			String ExpMaturityVaule = ExcelUtils.getCellData(file, "Sheet1", r, 4);
			
			// Pass data to app
			WebElement amount = driver.findElement(By.xpath("//*[@id=\"mat-input-0\"]"));
			WebElement period = driver.findElement(By.xpath("//*[@id=\"mat-input-1\"]"));
			WebElement rating = driver.findElement(By.xpath("//*[@id=\"mat-input-2\"]"));

			amount.clear();
			period.clear();
			rating.clear();
			Thread.sleep(3000);
			amount.sendKeys(InitialDepositAmount);
			period.sendKeys(Months);
			rating.sendKeys(Rate);
			
			WebElement compoundrp = driver.findElement(By.xpath("//mat-select[@id='mat-select-0']"));   //select class object compounddrp will find elelment by id
			compoundrp.click();
			
			List<WebElement> options=driver.findElements(By.xpath("//div[@id='mat-select-0-panel']//mat-option"));
			
			for(WebElement option:options)
			{
				if(option.getText().equals(Compounding))
					option.click();
			}
		
			/*JavascriptExecutor js=(JavascriptExecutor)driver;
			WebElement calculate_btn=driver.findElement(By.xpath("//*[@id=\"CIT-chart-submit\"]/div"));
			js.executeScript("arguments[0].click();",calculate_btn);  // clicking on calculate button
			*/
			driver.findElement(By.xpath("//button[@id='CIT-chart-submit']")).click();
			
			// Validating and update the result in excel

			String acttotal = driver.findElement(By.xpath("//span[@id='displayTotalValue']")).getText();
			
			System.out.println("act total is: " + acttotal);
			System.out.println("exp total is: " + ExpMaturityVaule);
			
			if(ExpMaturityVaule.equals(acttotal)) {			//if expected total = actual total then			
				
				ExcelUtils.setCellData(file, "Sheet1",r, 6,"Passed");	//setting passed in 6th column (index start with zero)
				ExcelUtils.fillGreenColor(file, "Sheet1",r, 6);	//filling the color in 6th column if passed then greeen or faile then red.
			}
			else
			{
				ExcelUtils.setCellData(file, "Sheet1",r, 6,"Failed");
				ExcelUtils.fillRedColor(file, "Sheet1",r, 6);
			}
		}
			System.out.println("calculation has been completed");
			driver.close();
	}

}
