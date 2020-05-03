package cn.winfxk.brassiere.vip;

import cn.nukkit.Player;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * VIP的界面基础类
 * 
 * @Createdate 2020/05/02 07:33:06
 * @author Winfxk
 */
public abstract class VipForm extends FormBase {
	protected VipMag vm;
	protected static final String t = "Vip", Close = "Close", Back = "Back";
	protected FormBase upForm;

	/**
	 * @param player 要显示界面的玩家对象
	 * @param upForm 上一个页面
	 */
	public VipForm(Player player, FormBase upForm) {
		super(player);
		vm = ac.getVipMag();
		this.upForm = upForm;
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

	/**
	 * 添加返回或关闭按钮
	 * 
	 * @param form
	 * @return
	 */
	public SimpleForm addButton(SimpleForm form) {
		listKey.add(upForm == null ? "c" : "b");
		form.addButton(msg.getSon(t, upForm == null ? Close : Back, this));
		return form;
	}
}
