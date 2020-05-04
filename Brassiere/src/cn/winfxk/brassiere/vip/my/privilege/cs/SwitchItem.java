package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 玩家点击了一个物品，这是选择要对这个物品干什么
 * 
 * @Createdate 2020/05/04 10:53:11
 * @author Winfxk
 */
public class SwitchItem extends VipForm {
	private String Key;

	public SwitchItem(Player player, FormBase upForm, String Key) {
		super(player, upForm);
		this.Key = Key;
	}

	@Override
	public boolean MakeMain() {
		return false;
	}

	@Override
	public boolean disMain(FormResponse data) {
		return false;
	}
}
