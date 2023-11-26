package day32;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

//File--->Workbook--->Sheets--->Rows--->Cells

public class ReadingExcel {

	public static void main(String[] args) throws IOException {

		FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\Testdata\\data1.xlsx");

		XSSFWorkbook workbook = new XSSFWorkbook(file);

		XSSFSheet sheet = workbook.getSheet("Sheet1");

		int TotalRows = sheet.getLastRowNum();
		short TotalCells = sheet.getRow(0).getLastCellNum();

		System.out.println("Total number of rows:" + TotalRows);
		System.out.println("Total number of cells:" + TotalCells);

		for (int r = 0; r <= TotalRows; r++) {
			XSSFRow CurrentRow = sheet.getRow(r);
			for (int c = 0; c < TotalCells; c++) {
				XSSFCell cell = CurrentRow.getCell(c);
				String value = cell.toString();
				System.out.print(value + "      ");
			}
			System.out.println();
		}
		workbook.close();
		file.close();
	}

}
