package cn.winfxk.brassiere.vip.my.privilege.cs;

import cn.nukkit.Player;
import cn.nukkit.form.response.FormResponse;
import cn.winfxk.brassiere.form.FormBase;
import cn.winfxk.brassiere.vip.VipForm;

/**
 * 从云端仓库下载一个物品到当前玩家的背包
 * 
 * @Createdate 2020/05/04 10:43:28
 * @author Winfxk
 */
public class downItem extends VipForm {
	private String Key;

	public downItem(Player player, FormBase upForm, String Key) {
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
