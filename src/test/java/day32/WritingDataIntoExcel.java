package day32;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WritingDataIntoExcel {

	public static void main(String[] args) throws IOException {

		FileOutputStream file = new FileOutputStream(System.getProperty("user.dir") + "\\testdata\\myfile.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFSheet sheet = workbook.createSheet();

		// Creating rows, cells and update data without loop
		/*XSSFRow row1 = sheet.createRow(0);

		row1.createCell(0).setCellValue("Bharath");
		row1.createCell(1).setCellValue("Naveen");
		row1.createCell(2).setCellValue("Akshaya");

		XSSFRow row2 = sheet.createRow(1);

		row2.createCell(0).setCellValue("Suresh");
		row2.createCell(1).setCellValue("Bharathy");
		row2.createCell(2).setCellValue("Chocky");*/
		
		Scanner sc = new Scanner(System.in);
		
		for(int r=0;r<=3;r++)
		{
			XSSFRow row = sheet.createRow(r);
			for(int c=0;c<3;c++)
			{
				System.out.println("Enter the value");
				String value = sc.next();
				row.createCell(c).setCellValue(value);
			}
		}
		workbook.write(file);
		workbook.close();
		file.close();
		System.out.println("Work is done!!");
	}

}
