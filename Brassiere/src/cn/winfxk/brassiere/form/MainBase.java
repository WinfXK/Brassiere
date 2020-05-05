package cn.winfxk.brassiere.form;

import cn.nukkit.Player;
import cn.winfxk.brassiere.tool.SimpleForm;

/**
 * @Createdate 2020/05/05 21:47:49
 * @author Winfxk
 */
public abstract class MainBase extends FormBase {
	protected FormBase upForm;
	protected String notTeam, notVip;
	protected static final String t = "Main";

	/**
	 * 主页基础页面
	 * 
	 * @param player 要显示页面的玩家对象
	 * @param form   上级目录
	 */
	public MainBase(Player player, FormBase form) {
		super(player);
		setK("{Player}", "{Money}", "{TeamID}", "{TeamName}", "{Player}", "{Money}", "{VipName}", "{VipID}",
				"{VipLevel}", "{VipExp}", "{VipTime}");
		upForm = form;
		notTeam = msg.getSon(t, "notTeam", player);
		notVip = msg.getSon(t, "notVip", player);
		reloadD();
	}

	/**
	 * 刷新数据
	 */
	public void reloadD() {
		setD(player.getName(), myPlayer.getMoney(), myPlayer.isTeam() ? notTeam : myPlayer.getTeam().getID(),
				myPlayer.isTeam() ? notTeam : myPlayer.getTeam().getName(),
				myPlayer.isVip() ? myPlayer.vip.getName() : notVip, myPlayer.isVip() ? myPlayer.vip.getID() : notVip,
				myPlayer.isVip() ? myPlayer.vip.getAlg().getLevel(player.getName()) : notVip);
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
