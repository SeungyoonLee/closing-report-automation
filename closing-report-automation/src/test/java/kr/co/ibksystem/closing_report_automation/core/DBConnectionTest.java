package kr.co.ibksystem.closing_report_automation.core;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DBConnectionTest {
	
	@Test
	public void testDBConnection() {
		DBConnection dbh = new DBConnection();
		ResultSet resultSet = dbh.query("select * from V$VERSION");
		String version = null;
		
		try {
			resultSet.next();
			version = resultSet.getString("BANNER");
		} catch (SQLException qex) {
			;
		}
		
		assertThat("Check DBMS Version", version, containsString("Oracle Database"));
		dbh.close();
	}
}
