package kr.co.ibksystem.closing_report_automation.core;

/**
 * 예외 Wrapper
 * @author SeungyoonLee <samsee@ibksystem.co.kr>
 *
 */
public class DBException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1265807761516629387L;

	public DBException(Throwable thr) {
		super(thr);
	}

}
