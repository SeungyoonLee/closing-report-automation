package kr.co.ibksystem.closing_report_automation.poi.sample;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelDatabase {
	public static void main(String[] args) throws Exception {
		Connection connect = null;
		XSSFWorkbook workbook = null;
		FileOutputStream out = null;
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			connect = DriverManager.getConnection(
					"jdbc:oracle:thin:@128.0.0.1:1571:IBSORNT", "TIBS_AP", "TIBS_AP");
			Statement statement = connect.createStatement();
			ResultSet resultSet = statement.executeQuery("select * from TB_IBS_ANTN_L_M");
			workbook = new XSSFWorkbook();
			XSSFSheet spreadsheet = workbook.createSheet("employe db");
			XSSFRow row = spreadsheet.createRow(1);
			XSSFCell cell;
			cell = row.createCell(1);
			cell.setCellValue("EMP ID");
			cell = row.createCell(2);
			cell.setCellValue("EMP NAME");
			cell = row.createCell(3);
			cell.setCellValue("DEG");
			cell = row.createCell(4);
			cell.setCellValue("SALARY");
			cell = row.createCell(5);
			cell.setCellValue("DEPT");
			int i = 2;
			while (resultSet.next()) {
				row = spreadsheet.createRow(i);
				cell = row.createCell(1);
				cell.setCellValue(resultSet.getDate("base_ymd"));
				cell = row.createCell(2);
				cell.setCellValue(resultSet.getString("ib_antn_cd"));
				cell = row.createCell(3);
				cell.setCellValue(resultSet.getString("ib_antn_row_cd"));
				cell = row.createCell(4);
				cell.setCellValue(resultSet.getString("ib_antn_clmn_cd"));
				cell = row.createCell(5);
				cell.setCellValue(resultSet.getString("base_ym"));
				i++;
			}
			out = new FileOutputStream(new File(
					"exceldatabase.xlsx"));
			workbook.write(out);
			out.close();
			System.out.println("exceldatabase.xlsx written successfully");
		} finally {
			if (null != connect)
				connect.close();
			if (null != out)
				out.close();
		}
	}
}
