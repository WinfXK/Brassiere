package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.winfxk.brassiere.form.FormBase;

/**
 * VIP的界面基础类
 * 
 * @Createdate 2020/05/02 07:33:06
 * @author Winfxk
 */
public abstract class VipForm extends FormBase {
	protected VipMag vm;
	protected String notVip;
	protected Vip vip;
	protected static final String t = "Vip";

	/**
	 * @param player 要显示界面的玩家对象
	 * @param upForm 上一个页面
	 */
	public VipForm(Player player, FormBase upForm) {
		super(player);
		vm = ac.getVipMag();
		try {
			this.upForm = upForm.clone();
		} catch (CloneNotSupportedException e) {
			this.upForm = upForm;
		}
		notVip = msg.getSon(t, "NotVip", player);
		vip = myPlayer.vip;
	}

	/**
	 * 获取当前VIP的特权
	 * 
	 * @return
	 */
	public String getVip() {
		return VipApi.isVip(player) ? VipApi.getVip(player).getName() : msg.getSon("Vip", "NotVip", player);
	}

	/**
	 * 获取一个文本
	 * 
	 * @param string
	 * @return
	 */
	public String getString(String string) {
		return string.equals(Back) || string.equals(Close) ? msg.getSon(t, string, this)
				: msg.getSun(t, Son, string, this);
	}
}
