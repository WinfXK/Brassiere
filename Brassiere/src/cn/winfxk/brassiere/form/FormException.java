package cn.winfxk.brassiere.form;

import cn.winfxk.brassiere.BrassiereException;

/**
 * @author Winfxk
 */
public class FormException extends BrassiereException {
	public FormException() {
		super();
	}
	/**
	 *
	 */
	private static final long serialVersionUID = -2361922630328521067L;

	public FormException(String string) {
		super(string);
	}
}
