package kr.co.ibksystem.closing_report_automation.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * DB Wrapper
 * 
 * @author SeungyoonLee <samsee@ibksystem.co.kr>
 *
 */
public class DBConnection {
	private static final String DB_DRIVER = "oracle.jdbc.OracleDriver";
	private static final String DB_IP = ""; // Your database server's IP
	private static final String DB_PORT = ""; // Your database server's PORT
	private static final String DB_SERVICE_NAME = ""; // Your database server's
														// SERVICE NAME
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@"
														+ DB_IP
														+ ":" + DB_PORT + ":" 
														+ DB_SERVICE_NAME;
	private static final String DB_USER = "";	// DB User
	private static final String DB_PASSWD = "";	// DB Password
	
	Connection connect = null;

	public DBConnection() {
		try {
			Class.forName(DB_DRIVER);
			connect = DriverManager.getConnection(DB_CONNECTION, DB_USER,
					DB_PASSWD);
		} catch (Exception ex) {
			throw new DBException(ex);
		}
	}

	public ResultSet query(String query) {
		Statement statement;
		ResultSet resultSet = null;
		try {
			statement = connect.createStatement();
			resultSet = statement.executeQuery(query);
			System.out.println("RUN_QUERY:" + query);
		} catch (SQLException qex) {
			throw new DBException(qex);
		}
		return resultSet;
	}

	public Map<String, String> getResultSetHeader(ResultSet result) {
		Map<String, String> colNameType = new HashMap<String, String>();
		int colCount;
		try {
			colCount = result.getMetaData().getColumnCount();
			for (int i = 0; i < colCount; i++) {
				String colName = result.getMetaData().getColumnName(i);
				String colType = result.getMetaData().getColumnTypeName(i);
				colNameType.put(colName, colType);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return colNameType;
	}

	public void close() {
		if (null != connect) {
			try {
				connect.close();
			} catch (SQLException qex) {
				throw new DBException(qex);
			}
		}
	}
}
