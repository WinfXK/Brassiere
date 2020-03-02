package cn.winfxk.brassiere;

/**
 * @author Winfxk
 */
public abstract class BrassiereException extends RuntimeException {
	public BrassiereException() {
		this("An unknown error occurred!Please send the error log to Winfxk@qq.com");
	}

	public BrassiereException(String Message) {
		super(Message);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
}
