package cn.winfxk.brassiere.tip;

import cn.nukkit.Player;
import cn.winfxk.brassiere.Activate;

/**
 * @Createdate 2020/04/23 00:53:50
 * @author Winfxk
 */
public class Tip {
	private Activate ac;
	private static Tip tip;
	private transient boolean isTipEnabled, isTopEnabled;
	private String Tip, Top;
	protected transient TipContent content;
	private TipThread tipThread;

	/**
	 * 底部管理器
	 * 
	 * @param activate
	 */
	public Tip(Activate activate) {
		ac = activate;
		tip = this;
		isTipEnabled = activate.getConfig().getBoolean("底部显示");
		isTopEnabled = activate.getConfig().getBoolean("头部显示频率");
		Tip = activate.getConfig().getString("Tip");
		Top = activate.getConfig().getString("Top");
		content = new TipContent(this);
		(tipThread = new TipThread(this)).start();
	}

	/**
	 * 对接接口
	 * 
	 * @return
	 */
	public Tip getInstance() {
		return tip;
	}

	/**
	 * 返回底部显示线程
	 * 
	 * @return
	 */
	public TipThread getTipThread() {
		return tipThread;
	}

	/**
	 * 获取文本格式化类
	 * 
	 * @return
	 */
	public TipContent getContent() {
		return content;
	}

	/**
	 * 获取底部默认会显示的文字
	 * 
	 * @return
	 */
	public String getTip() {
		return Tip;
	}

	/**
	 * 获取底部默认会显示的文字
	 * 
	 * @return
	 */
	public String getTipsString(Player player) {
		return content.getTipString(player);
	}

	/**
	 * 获取顶部将会显示的默认文字
	 * 
	 * @return
	 */
	public String getTopString(Player player) {
		return content.getTopString(player);
	}

	/**
	 * 获取顶部将会显示的默认文字
	 * 
	 * @return
	 */
	public String getTop() {
		return Top;
	}

	/**
	 * 判断头部显示是否启用
	 * 
	 * @return
	 */
	public boolean isTopEnabled() {
		return isTopEnabled;
	}

	/**
	 * 设置头部显示状态
	 * 
	 * @param isTopEnabled
	 */
	public boolean setTopEnabled() {
		return setEnabled(true);
	}

	/**
	 * 设置头部显示状态
	 * 
	 * @param isTopEnabled
	 */
	public boolean setTopEnabled(boolean isTopEnabled) {
		ac.getConfig().set("底部显示", this.isTopEnabled = isTopEnabled);
		return ac.getConfig().save();
	}

	/**
	 * 判断是否开启底部
	 * 
	 * @return
	 */
	public boolean isEnabled() {
		return isTipEnabled;
	}

	/**
	 * 设置底部为启动状态
	 * 
	 * @return
	 */
	public boolean setEnabled() {
		return setEnabled(true);
	}

	/**
	 * 设置底部是否启动
	 * 
	 * @param Enabled
	 * @return
	 */
	public boolean setEnabled(boolean Enabled) {
		ac.getConfig().set("底部显示", isTipEnabled = Enabled);
		return ac.getConfig().save();
	}

}
