package kr.co.ibksystem.closing_report_automation.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;

/**
 * Excel POI Library Wrapper
 * @author SeungyoonLee <samsee@ibksystem.co.kr>
 *
 */
public class ExcelService {
	Workbook wb = null;
	Sheet workingsheet = null;
	
	public void open(String filename) throws InvalidFormatException, IOException {
		wb = WorkbookFactory.create(new File(filename));
	}

	public boolean isOpen() {
		return null != wb;
	}

	public void close() {
		
	}
	
	public Name getNamedRange(String cellName) {
		// retrieve the named range
	    int namedCellIdx = wb.getNameIndex(cellName);
	    return wb.getNameAt(namedCellIdx);
	}

	public Sheet getSheet(String sheetName) {
		return wb.getSheet(sheetName);
	}

	public void writeResult(ResultSet result) {
		// TODO Auto-generated method stub
		
	}

	public void selectWorksheet(String worksheetName) {
		workingsheet = getSheet(worksheetName);
	}

	public void writeResultToRegion(ResultSet result, String regionName) {
		Name name = getNamedRange(regionName);
		Map<String, int[]> placeholder = null;
		try {
			placeholder = getPlaceholders(name);
		} catch (ExcelException e) {
			e.printStackTrace();
		}
		// first row
		int rowStart = ((int[]) (placeholder.values().toArray()[0]))[0];
		int rowWrite = rowStart;
		
		Set<String> columnNames = placeholder.keySet();

		try {
			// by row
			while (result.next()) {
				Row row = workingsheet.createRow(rowWrite);
				for (String colName : columnNames) { // by column
					String val = result.getString(colName);	// get value
					int colWrite = placeholder.get(colName)[1];
					Cell cell = row.createCell(colWrite);
					cell.setCellValue(val);
					System.out.println(colName + "(" + rowWrite + "," + colWrite + ")" + cell);
				}
				rowWrite++;
			}
		} catch (SQLException qex) {
			qex.printStackTrace();
		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Map<String, int[]> getPlaceholders(Name rangeName) throws ExcelException {
		Map<String, int[]> placeholders = new HashMap<String, int[]>();
		// test codes
		// retrieve the cell at the named range and test its contents
	    AreaReference aref = new AreaReference(rangeName.getRefersToFormula());
	    CellReference[] crefs = aref.getAllReferencedCells();
	    if (null == workingsheet) {
	    	throw new ExcelException("Worksheet is not selected. selectWorksheet first.");
	    }
	    for (int i=0; i<crefs.length; i++) {
	        Row r = workingsheet.getRow(crefs[i].getRow());
	        Cell c = r.getCell(crefs[i].getCol());
	        // extract the cell contents based on cell type etc.
	        System.out.println(c.getStringCellValue() + "->" + c.getStringCellValue().replaceAll("[{}]", ""));
	        placeholders.put(c.getStringCellValue().replaceAll("[{}]", ""), new int[]{c.getRowIndex(), c.getColumnIndex()});
	    }
	    return placeholders;
	}
	
	public void save() {
		try {
			FileOutputStream out = new FileOutputStream(new File("Writesheet.xlsx"));
			wb.write(out);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
