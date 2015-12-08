package kr.co.ibksystem.closing_report_automation.core;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.AreaReference;
import org.apache.poi.ss.util.CellReference;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class ExcelWrapperTest {
	ExcelService xls = new ExcelService();

	@Before
	public void open() {
		try {
			xls.open("resources/templates/simple_template.xls");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testOpen() {
		assertTrue("Excel opened?", xls.isOpen());
		if (xls.isOpen())
			xls.close();
	}

	@Test
	public void getNames() {
		Name namedCell = xls.getNamedRange("QRESULT");
		assertNotNull("Named Range", namedCell);

		Map<String, int[]> placeholders = new HashMap<String, int[]>();
		// test codes
		// retrieve the cell at the named range and test its contents
		AreaReference aref = new AreaReference(namedCell.getRefersToFormula());
		CellReference[] crefs = aref.getAllReferencedCells();
		for (int i = 0; i < crefs.length; i++) {
			Sheet sheet = xls.getSheet(crefs[i].getSheetName());
			Row r = sheet.getRow(crefs[i].getRow());
			Cell c = r.getCell(crefs[i].getCol());
			// extract the cell contents based on cell type etc.
			placeholders.put(c.getStringCellValue(),
					new int[] { c.getRowIndex(), c.getColumnIndex() });
		}

		String[] placeholderNames = { "IFRS_ACCD", "IFRS_ACCD_NM",
				"UN_PYEX_AMT", "PRPD_EXP", "WNCR_RVSN_UNER_AMT",
				"WNCR_UNER_AMT", "WNCR_PRRC_INTS", "WNCR_PRLS_RVSN_AMT",
				"TERM_RCGN_VAPR_AMT", "RMRK" };

		for (String name : placeholderNames) {
			assertTrue("Has name[" + name + "]", placeholders.containsKey(name));
		}
	}

	@Ignore
	public void testWrite() {

	}

	@Ignore
	public void testWriteToSheet() {

	}

	@Ignore
	public void testSets() {
		ExcelService xls = new ExcelService();
		// xls.set
	}

	@Ignore
	public void testRead() {
		fail("Not yet implemented");
	}

}
