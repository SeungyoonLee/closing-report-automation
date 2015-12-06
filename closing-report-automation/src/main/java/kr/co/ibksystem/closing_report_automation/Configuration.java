/**
 * 
 */
package kr.co.ibksystem.closing_report_automation;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 환경설정 로드/값 제공
 * @author 이승윤(samsee@ibksystem.co.kr)
 *
 */
public class Configuration {
	static final String REAL_ENV_PROPERTIES = "properties/ibcra.properties";
	static final String DEV_ENV_PROPERTIES = "properties/ibcra.local.properties";
	static Properties prop;
	
	static {
		prop = new Properties();
		
		InputStream input;
		String property_file_name = REAL_ENV_PROPERTIES;

		if (Boolean.valueOf(System.getProperty("dev")))
			property_file_name = DEV_ENV_PROPERTIES;
		
		try {
			input = new FileInputStream(property_file_name);
			prop.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String prop_name) {
		return prop.getProperty(prop_name);
	}

}