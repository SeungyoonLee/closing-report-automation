package kr.co.ibksystem.closing_report_automation.core;

/**
 * 예외 Wrapper
 * @author 이승윤(a99354@gwmail.ibk.co.kr)
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
